package com.bhp.opusb.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CCurrency;
import com.bhp.opusb.domain.CDocumentType;
import com.bhp.opusb.domain.CProduct;
import com.bhp.opusb.domain.CUnitOfMeasure;
import com.bhp.opusb.domain.CVendor;
import com.bhp.opusb.domain.MBrand;
import com.bhp.opusb.domain.MProductCatalog;
import com.bhp.opusb.repository.CCurrencyRepository;
import com.bhp.opusb.repository.CDocumentTypeRepository;
import com.bhp.opusb.repository.CProductRepository;
import com.bhp.opusb.repository.CUnitOfMeasureRepository;
import com.bhp.opusb.repository.CVendorRepository;
import com.bhp.opusb.repository.MBrandRepository;
import com.bhp.opusb.repository.MProductCatalogRepository;
import com.bhp.opusb.service.dto.MProductCatalogDTO;
import com.bhp.opusb.service.dto.marketplace.BhinnekaItemFilterDTO;
import com.bhp.opusb.service.mapper.MProductCatalogMapper;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service Implementation for managing {@link MProductCatalog}.
 */
@Service
@Transactional
public class MProductCatalogService {

    private final Logger log = LoggerFactory.getLogger(MProductCatalogService.class);

    private final MProductCatalogRepository mProductCatalogRepository;

    private final CDocumentTypeRepository cDocumentTypeRepository;
    private final CVendorRepository cVendorRepository;
    private final CUnitOfMeasureRepository cUnitOfMeasureRepository;
    private final CCurrencyRepository cCurrencyRepository;
    private final CProductRepository cProductRepository;
    private final MBrandRepository mBrandRepository;

    private final CProductService cProductService;

    private final MProductCatalogMapper mProductCatalogMapper;
    private final ADOrganization organization;
    private final ADOrganizationService adOrganizationService;
    private final MProductPriceService mProductPriceService;
    private final CGalleryService cGalleryService;
    private final ObjectMapper jsonMapper;

    public MProductCatalogService(MProductCatalogRepository mProductCatalogRepository,
            MProductCatalogMapper mProductCatalogMapper, ObjectMapper jsonMapper,
            ADOrganizationService adOrganizationService, MProductPriceService mProductPriceService, CGalleryService cGalleryService,
            CDocumentTypeRepository cDocumentTypeRepository, MBrandRepository mBrandRepository, CVendorRepository cVendorRepository,
            CUnitOfMeasureRepository cUnitOfMeasureRepository, CCurrencyRepository cCurrencyRepository, CProductRepository cProductRepository, CProductService cProductService) {
        this.mProductCatalogRepository = mProductCatalogRepository;
        this.mProductCatalogMapper = mProductCatalogMapper;
        this.jsonMapper = jsonMapper;
        this.adOrganizationService = adOrganizationService;
        this.mProductPriceService = mProductPriceService;
        this.cGalleryService = cGalleryService;
        this.cDocumentTypeRepository = cDocumentTypeRepository;
        this.mBrandRepository = mBrandRepository;
        this.cVendorRepository = cVendorRepository;
        this.cUnitOfMeasureRepository = cUnitOfMeasureRepository;
        this.cCurrencyRepository = cCurrencyRepository;
        this.cProductRepository = cProductRepository;
        this.cProductService = cProductService;

        organization = adOrganizationService.getDefaultOrganization();
    }

    /**
     * Save a mProductCatalog.
     *
     * @param mProductCatalogDTO the entity to save.
     * @return the persisted entity.
     */
    public MProductCatalogDTO save(MProductCatalogDTO mProductCatalogDTO) {
        log.debug("Request to save MProductCatalog : {}", mProductCatalogDTO);
        MProductCatalog mProductCatalog = mProductCatalogMapper.toEntity(mProductCatalogDTO);
        mProductCatalog = mProductCatalogRepository.save(mProductCatalog);
        return mProductCatalogMapper.toDto(mProductCatalog);
    }

    /**
     * Get all the mProductCatalogs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MProductCatalogDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MProductCatalogs");
        return mProductCatalogRepository.findAll(pageable)
            .map(mProductCatalogMapper::toDto);
    }

    /**
     * Get one mProductCatalog by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MProductCatalogDTO> findOne(Long id) {
        log.debug("Request to get MProductCatalog : {}", id);
        return mProductCatalogRepository.findById(id)
            .map(mProductCatalogMapper::toDto);
    }

    /**
     * Delete the mProductCatalog by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MProductCatalog : {}", id);
        mProductCatalogRepository.deleteById(id);
    }

    public int importBhinnekaCatalog(MultipartFile file) {
        int importedRows = 0;
        BhinnekaItemFilterDTO data = null;

        try {
            data = jsonMapper.readValue(file.getInputStream(), BhinnekaItemFilterDTO.class);

            if (data != null && data.getFilter() != null && data.getFilter().getResult() != null) {
                mProductCatalogRepository.saveAll(
                    data.getFilter().getResult().stream()
                        .map(item -> mProductCatalogMapper.toEntity(item.toDto()))
                        .collect(Collectors.toList())
                );
                importedRows = data.getFilter().getResult().size();
            }
        } catch (JsonParseException e) {
            log.warn("Failed when parsing the uploaded JSON file", e);
        } catch (JsonMappingException e) {
            log.warn("Failed when mapping the uploaded JSON file", e);
        } catch (IOException e) {
            log.warn("Failed when processing the uploaded JSON file", e);
        }

        return importedRows;
    }

    public void saveAll(List<MProductCatalogDTO> mProductCatalogDTOs) {
        log.debug("Request to save all Product Catalog DTO from import from csv : {}", mProductCatalogDTOs);
        //List<MProductCatalog> productCatalogs = mProductCatalogDTOs.stream().map(c -> mProductCatalogMapper.toEntity(c)).collect(Collectors.toList());
        List<MProductCatalog> entities = new ArrayList<>();

        for(MProductCatalogDTO dto : mProductCatalogDTOs) {
            MProductCatalog entity = new MProductCatalog();
            entity.active(true)
                .name(dto.getName())
                .description(dto.getDescription())
                .shortDescription(dto.getShortDescription())
                .height(dto.getHeight())
                .length(dto.getLength())
                .width(dto.getWidth())
                .weight(dto.getWeight())
                .price(dto.getPrice())
                .expiredDate(dto.getExpiredDate())
                .documentAction("APR")
                .documentStatus("DRF")
                .approved(false)
                .processed(true)
                .sku(dto.getSku())
                .preOrder(dto.isPreOrder())
                .preOrderDuration(dto.getPreOrderDuration())
                .warranty(dto.getWarranty())
                .sold(dto.isSold())
                .stockAvailable(dto.getStockAvailable())

                .adOrganization(organization)
                .cDocumentType(buildDocumentType(dto.getCDocumentTypeName()))
                .cVendor(buildVendor(dto.getCVendorName()))
                .cUom(buildUnitOfMeasure(dto.getCUomName()))
                .cCurrency(buildCurrency(dto.getCCurrencyName()))
                .mProduct(buildProduct(dto.getMProductShortName(), buildUnitOfMeasure(dto.getCUomName())))
                .mBrand(buildBrand(dto.getMBrandName()));

            entities.add(entity);
        }

        mProductCatalogRepository.saveAll(entities);
    }

    private CDocumentType buildDocumentType(String name) {
        if (name == null) {
            return null;
        }

        return cDocumentTypeRepository.findFirstByName(name)
            .orElseGet(() -> {
                final CDocumentType entity = new CDocumentType();
                entity.active(true)
                    .adOrganization(organization)
                    .name(name);

                return cDocumentTypeRepository.save(entity);
            });
    }

    private CVendor buildVendor(String name) {
        if (name == null) {
            return null;
        }

        return cVendorRepository.findFirstByName(name)
            .orElseGet(() -> {
                final CVendor entity = new CVendor();
                entity.active(true)
                    .adOrganization(organization)
                    .type("CMP")
                    .paymentCategory("RED")
                    .location("DMS")
                    .documentAction("APV")
                    .documentStatus("SMT")
                    .branch(false)
                    .approved(false)
                    .processed(true)
                    .dateTrx(java.time.LocalDate.now())
                    .documentType(buildDocumentType("Supplier Registration"))
                    .name(name);

                return cVendorRepository.save(entity);
            });
    }

    private CUnitOfMeasure buildUnitOfMeasure(String name) {
        if (name == null) {
            return null;
        }

        return cUnitOfMeasureRepository.findFirstByName(name)
            .orElseGet(() -> {
                final CUnitOfMeasure entity = new CUnitOfMeasure();
                entity.active(true)
                    .adOrganization(organization)
                    .code(name)
                    .symbol(name)
                    .name(name);

                return cUnitOfMeasureRepository.save(entity);
            });
    }

    private CCurrency buildCurrency(String name) {
        if (name == null) {
            return null;
        }

        return cCurrencyRepository.findFirstByName(name)
            .orElseGet(() -> {
                final CCurrency entity = new CCurrency();
                entity.active(true)
                    .adOrganization(organization)
                    .code(name)
                    .symbol(name)
                    .name(name);

                return cCurrencyRepository.save(entity);
            });
    }

    private CProduct buildProduct(String name, CUnitOfMeasure uom) {
        if (name == null) {
            return null;
		}

        return cProductRepository.findFirstByName(name)
            .orElseGet(() -> {
                final CProduct entity = new CProduct();
                entity.active(true)
                    .code(name)
                    .name(name)
                    .adOrganization(organization)
                    .type(cProductService.getDefaultType())
                    .uom(uom)
                    .assetAcct(cProductService.getDefaultAssetAccount())
                    .expenseAcct(cProductService.getDefaultExpenseAccount())
                    .productCategory(cProductService.getDefaultCategory())
                    .productSubCategory(cProductService.getDefaultSubCategory())
                    .productClassification(cProductService.getDefaultClassification());

                return cProductRepository.save(entity);
            });
    }

    private MBrand buildBrand(String name) {
        if (name == null) {
            return null;
        }

        return mBrandRepository.findFirstByName(name)
            .orElseGet(() -> {
                final MBrand entity = new MBrand();
                entity.active(true)
                    .adOrganization(organization)
                    .name(name);

                return mBrandRepository.save(entity);
            });
	}


}
