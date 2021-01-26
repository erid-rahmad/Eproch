package com.bhp.opusb.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;
import java.util.Optional;

import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.AiExchangeIn;
import com.bhp.opusb.domain.CCurrency;
import com.bhp.opusb.domain.CLocator;
import com.bhp.opusb.domain.CProduct;
import com.bhp.opusb.domain.CTax;
import com.bhp.opusb.domain.CTaxCategory;
import com.bhp.opusb.domain.CUnitOfMeasure;
import com.bhp.opusb.domain.CVendor;
import com.bhp.opusb.domain.CWarehouse;
import com.bhp.opusb.domain.MMatchPO;
import com.bhp.opusb.domain.MVerification;
import com.bhp.opusb.domain.enumeration.AiStatus;
import com.bhp.opusb.domain.enumeration.CTaxTransactionType;
import com.bhp.opusb.repository.AiExchangeInRepository;
import com.bhp.opusb.repository.CCurrencyRepository;
import com.bhp.opusb.repository.CLocatorRepository;
import com.bhp.opusb.repository.CProductRepository;
import com.bhp.opusb.repository.CTaxCategoryRepository;
import com.bhp.opusb.repository.CTaxRepository;
import com.bhp.opusb.repository.CUnitOfMeasureRepository;
import com.bhp.opusb.repository.CVendorRepository;
import com.bhp.opusb.repository.CWarehouseRepository;
import com.bhp.opusb.repository.MMatchPORepository;
import com.bhp.opusb.repository.MVerificationLineRepository;
import com.bhp.opusb.service.dto.MMatchPODTO;
import com.bhp.opusb.service.dto.jde.PoReceiverFileDTO;
import com.bhp.opusb.service.mapper.MMatchPOMapper;
import com.bhp.opusb.util.DocumentUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MMatchPO}.
 */
@Service
@Transactional
public class MMatchPOService {

    private final Logger log = LoggerFactory.getLogger(MMatchPOService.class);

    private final MMatchPORepository mMatchPORepository;
    private final CCurrencyRepository cCurrencyRepository;
    private final CTaxCategoryRepository cTaxCategoryRepository;
    private final CTaxRepository cTaxRepository;
    private final CUnitOfMeasureRepository cUnitOfMeasureRepository;
    private final CVendorRepository cVendorRepository;
    private final CLocatorRepository cLocatorRepository;
    private final CWarehouseRepository cWarehouseRepository;
    private final CProductRepository cProductRepository;
    private final MVerificationLineRepository mVerificationLineRepository;

    private final MMatchPOMapper mMatchPOMapper;

    @Autowired
    private CCostCenterService cCostCenterService;

    @Autowired
    private ADOrganizationService adOrganizationService;

    @Autowired
    private CProductService cProductService;

    @Autowired
    private AiExchangeInRepository aiExchangeInRepository;

    @Autowired
    private ObjectMapper jsonMapper;

    public MMatchPOService(MMatchPORepository mMatchPORepository, CCurrencyRepository cCurrencyRepository,
            CTaxCategoryRepository cTaxCategoryRepository, CTaxRepository cTaxRepository, CUnitOfMeasureRepository cUnitOfMeasureRepository,
            CVendorRepository cVendorRepository, CLocatorRepository cLocatorRepository,
            CWarehouseRepository cWarehouseRepository, CProductRepository cProductRepository,
            MVerificationLineRepository mVerificationLineRepository, MMatchPOMapper mMatchPOMapper) {
        this.mMatchPORepository = mMatchPORepository;
        this.cCurrencyRepository = cCurrencyRepository;
        this.cTaxCategoryRepository = cTaxCategoryRepository;
        this.cTaxRepository = cTaxRepository;
        this.cUnitOfMeasureRepository = cUnitOfMeasureRepository;
        this.cVendorRepository = cVendorRepository;
        this.cLocatorRepository = cLocatorRepository;
        this.cWarehouseRepository = cWarehouseRepository;
        this.cProductRepository = cProductRepository;
        this.mVerificationLineRepository = mVerificationLineRepository;
        this.mMatchPOMapper = mMatchPOMapper;
    }

    /**
     * Save a mMatchPO.
     *
     * @param mMatchPODTO the entity to save.
     * @return the persisted entity.
     */
    public MMatchPODTO save(MMatchPODTO mMatchPODTO) {
        log.debug("Request to save MMatchPO : {}", mMatchPODTO);
        MMatchPO mMatchPO = mMatchPOMapper.toEntity(mMatchPODTO);
        mMatchPO = mMatchPORepository.save(mMatchPO);
        return mMatchPOMapper.toDto(mMatchPO);
    }

    /**
     * Synchronize the JDE receiver file (table F43121) record.
     * @param message
     * @return
     * @throws JsonProcessingException
     */
    public MMatchPODTO synchronize(String message) throws JsonProcessingException {
        log.debug("Request to synchronize MMatchPO : {}", message);
        PoReceiverFileDTO payload = jsonMapper.readValue(message, PoReceiverFileDTO.class);
        
        String matchType = payload.getMatchType();
        String orgCode = payload.getOrgCode();
        String docType = payload.getDocTypePo();
        String poNo = payload.getPoNo();
        int receiptNo = Integer.parseInt(payload.getReceiptNo());
        int lineNoPo = payload.getLineNoPo();
        int lineNoMr = payload.getLineNoMr();
        String orderSuffix = payload.getOrderSuffix();
        ADOrganization org = adOrganizationService.findOrCreate(orgCode);

        // Should delete the reversed match PO.
        if (matchType.equals("4")) {
            mMatchPORepository
                .findReversedLine(orgCode, docType, poNo, BigDecimal.valueOf(receiptNo), lineNoPo, lineNoMr, orderSuffix)
                .ifPresent(matchPo -> {
                    mVerificationLineRepository.getFirstReversedLine(org, docType, poNo, BigDecimal.valueOf(receiptNo), lineNoPo, lineNoMr, orderSuffix)
                        .ifPresent(line -> {
                            line.receiptReversed(true);

                            MVerification header = line.getVerification();

                            if (Boolean.FALSE.equals(header.isReceiptReversed()) && ! header.getDocumentStatus().equals(DocumentUtil.STATUS_VOID)) {
                                header.receiptReversed(true);
                            }
                        });

                    log.debug("Deleting match PO record due to reverse event : {}", matchPo);
                    mMatchPORepository.delete(matchPo);
                });

            // Immediatelly return the DTO with the composite primary keys.
            // These keys are required to update the PRLRT (last_run_time) field in the
            // respective table.
            MMatchPODTO dto = new MMatchPODTO();
            dto.setAdOrganizationCode(orgCode);
            dto.setcDocType(docType);
            dto.setPoNo(poNo);
            dto.setReceiptNo(String.valueOf(receiptNo));
            dto.setLineNoPo(lineNoPo);
            dto.setLineNoMr(lineNoMr);
            dto.setOrderSuffix(orderSuffix);
            dto.setmMatchType(matchType);
            return dto;
        }

        Optional<MMatchPO> record = mMatchPORepository.findByKeys(matchType, orgCode, docType, poNo,
                String.valueOf(receiptNo), lineNoPo, lineNoMr, orderSuffix);

        MMatchPO mMatchPO;

        if (record.isPresent()) {
            mMatchPO = record.get();
            updateEntity(mMatchPO, payload, org);

            if (mMatchPO.getCVendor() != null) {
                aiExchangeInRepository.findFirstByEntityTypeAndEntityIdAndStatus(MMatchPO.class.getName(), mMatchPO.getId(), AiStatus.PARTIAL)
                    .ifPresent(data -> 
                        data.payload(message)
                            .status(AiStatus.SUCCESS)
                    );
            }
        } else {
            mMatchPO = buildEntity(payload, org);
            mMatchPO = mMatchPORepository.save(mMatchPO);

            if (mMatchPO.getCVendor() == null) {
                AiExchangeIn exchangeIn = new AiExchangeIn()
                    .entityId(mMatchPO.getId())
                    .entityType(MMatchPO.class.getName())
                    .payload(message)
                    .status(AiStatus.PARTIAL);

                aiExchangeInRepository.save(exchangeIn);
            }
        }
        return mMatchPOMapper.toDto(mMatchPO);
    }

    /**
     * Get all the mMatchPOS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MMatchPODTO> findAll(Pageable pageable) {
        log.debug("Request to get all MMatchPOS");
        return mMatchPORepository.findAll(pageable)
            .map(mMatchPOMapper::toDto);
    }

    /**
     * Get one mMatchPO by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MMatchPODTO> findOne(Long id) {
        log.debug("Request to get MMatchPO : {}", id);
        return mMatchPORepository.findById(id)
            .map(mMatchPOMapper::toDto);
    }

    /**
     * Delete the mMatchPO by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MMatchPO : {}", id);
        mMatchPORepository.deleteById(id);
    }

    private void updateEntity(MMatchPO entity, PoReceiverFileDTO payload, ADOrganization org) {
        log.debug("Updating the existing MMatchPO : {}", entity);
        entity.cConversionRate(payload.getConversionRate())
            .deliveryNo(payload.getDeliveryNo())
            .foreignActual(payload.getForeignActual())
            .foreignTaxAmount(payload.getForeignTaxAmount())
            .foreignTotalLines(payload.getForeignTotalLines())
            .openAmount(payload.getOpenAmount())
            .openForeignAmount(payload.getOpenForeignAmount())
            .openQty(payload.getOpenQty())
            .priceActual(payload.getPriceActual())
            .qty(payload.getQty())
            .taxAmount(payload.getTaxAmount())
            .taxable(stringToBoolean(payload.getTaxable()))
            .totalLines(payload.getTotalLines())
            .itemDesc1(payload.getItemDesc1())
            .itemDesc2(payload.getItemDesc2())

            // Lookup to master data.
            .cCurrency(buildCurrency(payload, org))
            .cTaxCategory(buildTaxCategory(payload, org))
            .cTax(buildTax(payload, entity.getCTaxCategory(), org))
            .cUom(buildUnitOfMeasure(payload, org))
            .mProduct(buildProduct(payload, entity.getCUom(), org))
            .cVendor(buildVendor(String.valueOf(payload.getAddressNo())))
            .mWarehouse(buildWarehouse(payload, org))
            .mLocator(buildLocator(payload, entity.getMWarehouse(), org));
    }

    private MMatchPO buildEntity(PoReceiverFileDTO payload, ADOrganization org) {
        log.debug("Creating a new MMatchPO");
        MMatchPO entity = new MMatchPO();

        entity.cConversionRate(payload.getConversionRate())
            .cDocType(payload.getDocTypePo())
            .cDocTypeMr(payload.getDocTypeMr())
            .poDate(payload.getPoDate())
            .receiptDate(payload.getReceiptDate())
            .dateAccount(payload.getDateAccount())
            .deliveryNo(payload.getDeliveryNo())
            .description(payload.getDescription())
            .foreignActual(payload.getForeignActual())
            .foreignTaxAmount(payload.getForeignTaxAmount())
            .foreignTotalLines(payload.getForeignTotalLines())
            .lineNoMr(payload.getLineNoMr())
            .lineNoPo(payload.getLineNoPo())
            .mMatchType(payload.getMatchType())
            .openAmount(payload.getOpenAmount())
            .openForeignAmount(payload.getOpenForeignAmount())
            .openQty(payload.getOpenQty())
            .orderSuffix(payload.getOrderSuffix())
            .poNo(nullableToString(payload.getPoNo()))
            .priceActual(payload.getPriceActual())
            .qty(payload.getQty())
            .receiptNo(payload.getReceiptNo())
            .taxAmount(payload.getTaxAmount())
            .taxable(stringToBoolean(payload.getTaxable()))
            .totalLines(payload.getTotalLines())
            .itemDesc1(payload.getItemDesc1())
            .itemDesc2(payload.getItemDesc2())

            // Lookup to master data.
            .adOrganization(org)
            .cCostCenter(cCostCenterService.getDefaultCostCenter())
            .cCurrency(buildCurrency(payload, org))
            .cTaxCategory(buildTaxCategory(payload, org))
            .cTax(buildTax(payload, entity.getCTaxCategory(), org))
            .cUom(buildUnitOfMeasure(payload, org))
            .cVendor(buildVendor(String.valueOf(payload.getAddressNo())))
            .mProduct(buildProduct(payload, entity.getCUom(), org))
            .mWarehouse(buildWarehouse(payload, org))
            .mLocator(buildLocator(payload, entity.getMWarehouse(), org));

        return entity;
    }

    private CCurrency buildCurrency(PoReceiverFileDTO payload, final ADOrganization org) {
        return Optional.ofNullable(payload.getCurrencyCode())
            .map(code -> cCurrencyRepository.findFirstByCode(code)
                .orElseGet(() -> cCurrencyRepository.save(
                    new CCurrency()
                    .active(true)
                    .adOrganization(org)
                    .code(code)
                    .name(code)
                    .symbol(code)
                )
            ))
            .orElseGet(() -> null);
    }

    private CTaxCategory buildTaxCategory(PoReceiverFileDTO payload, final ADOrganization org) {
        return Optional.ofNullable(payload.getTaxCategory()).map(name -> {
            final String value = name.trim();
            if (value.length() == 0) {
                return null;
            }

            return cTaxCategoryRepository.findFirstByNameAndAdOrganization(value, org)
                .orElseGet(() -> cTaxCategoryRepository.save(
                    new CTaxCategory()
                    .active(true)
                    .adOrganization(org)
                    .name(value)
                    .description(value)
                    .isWithholding(true)
                ));
            })
            .orElseGet(() -> null);
    }

    private CTax buildTax(PoReceiverFileDTO payload, CTaxCategory taxCategory, final ADOrganization org) {
        return Optional.ofNullable(payload.getTax()).map(name -> {
            final String value = name.trim();
            if (value.length() == 0) {
                return null;
            }

            return cTaxRepository.findFirstByNameAndTaxCategoryAndAdOrganization(value, taxCategory, org)
                .orElseGet(() -> cTaxRepository.save(
                    new CTax()
                    .active(true)
                    .adOrganization(org)
                    .name(value)
                    .description(value)
                    .taxCategory(taxCategory)
                    .transactionType(CTaxTransactionType.BOTH)
                ));
            })
            .orElseGet(() -> null);
    }

    private CUnitOfMeasure buildUnitOfMeasure(PoReceiverFileDTO payload, final ADOrganization org) {
        return Optional.ofNullable(payload.getUom())
            .map(code -> cUnitOfMeasureRepository.findFirstByCodeAndAdOrganizationId(code, org.getId())
                .orElseGet(() -> cUnitOfMeasureRepository.save(
                    new CUnitOfMeasure()
                    .active(true)
                    .adOrganization(org)
                    .code(code)
                    .name(code)
                    .symbol(code)
                )
            ))
            .orElseGet(() -> null);
    }

    private CWarehouse buildWarehouse(PoReceiverFileDTO payload, final ADOrganization org) {
        return Optional.ofNullable(payload.getWarehouse())
            .map(code -> {
                final String value = code.trim();
                if (value.length() == 0) {
                    return null;
                }

                return cWarehouseRepository.findFirstByCodeAndAdOrganizationId(value, org.getId())
                    .orElseGet(() -> cWarehouseRepository.save(
                        new CWarehouse()
                        .active(true)
                        .adOrganization(org)
                        .code(value)
                        .name(value)
                    ));
            })
            .orElseGet(() -> null);
    }

    private CLocator buildLocator(PoReceiverFileDTO payload, CWarehouse warehouse, final ADOrganization org) {
        return Optional.ofNullable(payload.getLocator())
            .map(code -> {
                final String value = code.trim();
                if (value.length() == 0) {
                    return null;
                }

                return cLocatorRepository.findFirstByCodeAndWarehouseIdAndAdOrganizationId(code, warehouse.getId(), org.getId())
                    .orElseGet(() -> cLocatorRepository.save(
                        new CLocator()
                        .active(true)
                        .adOrganization(org)
                        .code(code)
                        .warehouse(warehouse)
                    ));
            })
            .orElseGet(() -> null);
    }

    private CVendor buildVendor(String code) {
        return cVendorRepository.findFirstByCode(code).orElse(null);
    }

    private CProduct buildProduct(PoReceiverFileDTO payload, CUnitOfMeasure uom, final ADOrganization org) {
        final String code = payload.getItemCode();

        final String name = Optional.ofNullable(payload.getProductDesc1())
            .map(String::trim)
            .orElseGet(() -> null);

        final String description = Optional.ofNullable(payload.getProductDesc2())
            .map(String::trim)
            .orElseGet(() -> null);

        return cProductRepository.findFirstByNameAndAdOrganizationId(name, org.getId())
            .orElseGet(() -> cProductRepository.save(
                new CProduct()
                .active(true)
                .adOrganization(org)
                .code(code)
                .name(name)
                .description(description)
                .type(cProductService.getDefaultType())
                .assetAcct(cProductService.getDefaultAssetAccount())
                .expenseAcct(cProductService.getDefaultExpenseAccount())
                .productCategory(cProductService.getDefaultCategory())
                .productClassification(cProductService.getDefaultClassification())
                .uom(uom)
            ));
    }

    private BigDecimal toBigDecimal(Map<String, Object> payload, String fieldName) {
        final Object value = payload.get(fieldName);
        if (value == null) {
            return null;
        }
        try {
            if (value instanceof BigDecimal) {
                return (BigDecimal) value;
            } else if (value instanceof String) {
                return new BigDecimal((String) value);
            } else if (value instanceof BigInteger) {
                return new BigDecimal((BigInteger) value);
            } else if (value instanceof Integer) {
                return new BigDecimal((int) value);
            } else if (value instanceof Long) {
                return new BigDecimal((long) value);
            } else if (value instanceof Double) {
                return BigDecimal.valueOf((double) value);
            } else {
                log.warn("Value ({}) cannot be converted to BigDecimal", value);
            }
        } catch (NumberFormatException e) {
            log.warn("Failed to convert the value into a BigDecimal");
        }

        return new BigDecimal("0");
    }

    private String nullableToString(Object value) {
        if (value == null) {
            return null;
        }
        return String.valueOf(value);
    }

    private boolean stringToBoolean(String value) {
        return "Y".equals(value);
    }
}
