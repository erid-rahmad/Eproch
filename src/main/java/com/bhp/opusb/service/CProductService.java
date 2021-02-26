package com.bhp.opusb.service;

import java.util.Objects;
import java.util.Optional;

import com.bhp.opusb.config.ApplicationProperties;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CElementValue;
import com.bhp.opusb.domain.CProduct;
import com.bhp.opusb.domain.CProductCategory;
import com.bhp.opusb.domain.CProductCategoryAccount;
import com.bhp.opusb.domain.CProductClassification;
import com.bhp.opusb.repository.CElementValueRepository;
import com.bhp.opusb.repository.CProductCategoryAccountRepository;
import com.bhp.opusb.repository.CProductCategoryRepository;
import com.bhp.opusb.repository.CProductRepository;
import com.bhp.opusb.service.dto.CProductDTO;
import com.bhp.opusb.service.mapper.CProductMapper;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CProduct}.
 */
@Service
@Transactional
public class CProductService {

    private final Logger log = LoggerFactory.getLogger(CProductService.class);

    private final ADOrganizationService adOrganizationService;
    private final CProductRepository cProductRepository;
    private final CProductCategoryRepository cProductCategoryRepository;
    private final CProductCategoryAccountRepository cProductCategoryAccountRepository;
    private final CElementValueRepository cElementValueRepository;

    private final CProductMapper cProductMapper;

    private final ApplicationProperties properties;

    public CProductService(ADOrganizationService adOrganizationService, CProductRepository cProductRepository,
            CProductCategoryRepository cProductCategoryRepository,
            CProductCategoryAccountRepository cProductCategoryAccountRepository,
            CElementValueRepository cElementValueRepository, CProductMapper cProductMapper,
            ApplicationProperties applicationProperties) {
        this.adOrganizationService = adOrganizationService;
        this.cProductRepository = cProductRepository;
        this.cProductCategoryRepository = cProductCategoryRepository;
        this.cProductCategoryAccountRepository = cProductCategoryAccountRepository;
        this.cElementValueRepository = cElementValueRepository;
        this.cProductMapper = cProductMapper;
        this.properties = applicationProperties;
    }

    /**
     * Save a cProduct.
     *
     * @param cProductDTO the entity to save.
     * @return the persisted entity.
     */
    public CProductDTO save(CProductDTO cProductDTO) {
        log.debug("Request to save CProduct : {}", cProductDTO);
        CProduct cProduct = cProductMapper.toEntity(cProductDTO);
        cProduct = cProductRepository.save(cProduct);
        return cProductMapper.toDto(cProduct);
    }

    /**
     * Initialize product, category, and subcategory from marketplace.
     *
     * @param cProductDTO the entity to save.
     * @return the persisted entity.
     */
    public CProductDTO save(String productCode, String productName, String categoryCode, String categoryName, String subcategoryCode, String subcategoryName) {
        log.debug("Request to initialize categories.");
        ADOrganization org = adOrganizationService.getDefaultOrganization();
        Optional<CElementValue> assetAccount = cElementValueRepository.findById(properties.getDefaultProductAssetAccountId());

        if (!assetAccount.isPresent()) {
            throw new BadRequestAlertException("There is no asset account with the given ID", "cElementValue", "idnotexists");
        }

        Optional<CElementValue> expenseAccount = cElementValueRepository.findById(properties.getDefaultProductExpenseAccountId());

        if (!expenseAccount.isPresent()) {
            throw new BadRequestAlertException("There is no expense account with the given ID", "cElementValue", "idnotexists");
        }

        // Add top level category.
        CProductCategory category = cProductCategoryRepository.findFirstByCode(categoryCode)
            .orElseGet(() -> {
                CProductCategory cat = new CProductCategory();
                cat.active(true)
                    .adOrganization(org)
                    .code(categoryCode)
                    .name(categoryName);

                cProductCategoryRepository.save(cat);

                CProductCategoryAccount categoryAccount = buildCategoryAccount(org, cat, assetAccount.get(), expenseAccount.get());
                cProductCategoryAccountRepository.save(categoryAccount);

                return cat;
            });

        // Add sub category.
        CProductCategory subcategory = cProductCategoryRepository.findFirstByCode(subcategoryCode)
            .orElseGet(() -> {
                CProductCategory cat = new CProductCategory();
                cat.active(true)
                    .adOrganization(org)
                    .code(subcategoryCode)
                    .name(subcategoryName)
                    .parentCategory(category);

                cProductCategoryRepository.save(cat);

                CProductCategoryAccount categoryAccount = buildCategoryAccount(org, cat, assetAccount.get(), expenseAccount.get());
                cProductCategoryAccountRepository.save(categoryAccount);

                return cat;
            });

        if (!Objects.equals(subcategory.getParentCategory(), category)) {
            category.getCProductCategories().clear();
            category.addCProductCategory(subcategory);
        }

        CProduct cProduct = cProductRepository.findFirstByCode(productCode)
            .orElseGet(() -> {
                CProduct prod = new CProduct();
                prod.active(true)
                    .adOrganization(org)
                    .code(productCode)
                    .name(productName)
                    .productCategory(category)
                    .productSubCategory(subcategory)
                    .assetAcct(assetAccount.get())
                    .expenseAcct(expenseAccount.get());

                return cProductRepository.save(prod);
            });

        if (!Objects.equals(cProduct.getProductCategory(), category)) {
            cProduct.productCategory(category).productSubCategory(subcategory);
        }

        return cProductMapper.toDto(cProduct);
    }

    /**
     * Get all the cProducts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CProductDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CProducts");
        return cProductRepository.findAll(pageable)
            .map(cProductMapper::toDto);
    }

    /**
     * Get one cProduct by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CProductDTO> findOne(Long id) {
        log.debug("Request to get CProduct : {}", id);
        return cProductRepository.findById(id)
            .map(cProductMapper::toDto);
    }

    /**
     * Delete the cProduct by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CProduct : {}", id);
        cProductRepository.deleteById(id);
    }

    public CProductClassification getDefaultClassification() {
        CProductClassification classification = new CProductClassification();
        classification.setId(properties.getDefaultProductClassificationId());
        return classification;
    }

    public CProductCategory getDefaultCategory() {
        CProductCategory category = new CProductCategory();
        category.setId(properties.getDefaultProductCategoryId());
        return category;
    }

    public CProductCategory getDefaultSubCategory() {
        CProductCategory category = new CProductCategory();
        category.setId(properties.getDefaultProductSubCategoryId());
        return category;
    }

    public CElementValue getDefaultAssetAccount() {
        CElementValue account = new CElementValue();
        account.setId(properties.getDefaultProductAssetAccountId());
        return account;
    }

    public CElementValue getDefaultExpenseAccount() {
        CElementValue account = new CElementValue();
        account.setId(properties.getDefaultProductExpenseAccountId());
        return account;
    }

    public String getDefaultType() {
        return properties.getDefaultProductType();
    }

    private CProductCategoryAccount buildCategoryAccount(ADOrganization org, CProductCategory category, CElementValue assetAccount, CElementValue expenseAccount) {
        CProductCategoryAccount categoryAssetAccount = new CProductCategoryAccount();
        categoryAssetAccount.active(true)
            .adOrganization(org)
            .assetAcct(assetAccount)
            .expenseAcct(expenseAccount)
            .productCategory(category);

        return categoryAssetAccount;
    }
}
