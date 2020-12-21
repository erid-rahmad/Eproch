package com.bhp.opusb.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
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
import com.bhp.opusb.service.dto.MMatchPODTO;
import com.bhp.opusb.service.mapper.MMatchPOMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.lang3.StringUtils;
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
            MMatchPOMapper mMatchPOMapper) {
        this.mMatchPORepository = mMatchPORepository;
        this.cCurrencyRepository = cCurrencyRepository;
        this.cTaxCategoryRepository = cTaxCategoryRepository;
        this.cTaxRepository = cTaxRepository;
        this.cUnitOfMeasureRepository = cUnitOfMeasureRepository;
        this.cVendorRepository = cVendorRepository;
        this.cLocatorRepository = cLocatorRepository;
        this.cWarehouseRepository = cWarehouseRepository;
        this.cProductRepository = cProductRepository;
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

    public MMatchPODTO synchronize(String message) throws JsonProcessingException {
        log.debug("Request to synchronize MMatchPO : {}", message);

        @SuppressWarnings("unchecked")
        Map<String, Object> payload = jsonMapper.readValue(message, Map.class);
        
        String matchType = (String) payload.get("PRMATC");
        String orgCode = (String) payload.get("PRKCOO");
        String docType = (String) payload.get("PRDCTO");
        String poNo = String.valueOf(payload.get("PRDOCO"));
        int receiptNo = (int) payload.get("PRDOC");
        int lineNoPo = (int) payload.get("PRLNID");
        int lineNoMr = (int) payload.get("PRNLIN");
        String orderSuffix = (String) payload.get("PRSFXO");

        // Should delete the reversed match PO.
        if (matchType.equals("4")) {
            Optional<MMatchPO> record = mMatchPORepository.findReversedLine(orgCode, docType, poNo, BigDecimal.valueOf(receiptNo), lineNoPo, lineNoMr, orderSuffix);

            if (record.isPresent()) {
                log.debug("Deleting match PO record due to reverse event : {}", record.get());
                mMatchPORepository.delete(record.get());
            }

            // Immediatelly return the DTO with the primary keys.
            // These keys are required to update the PRLRT (last_run_time) field in the respective table.
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

        Optional<MMatchPO> record = mMatchPORepository.findByKeys(matchType, orgCode, docType, poNo, String.valueOf(receiptNo), lineNoPo, lineNoMr, orderSuffix);
        ADOrganization org = adOrganizationService.findOrCreate(orgCode);
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

    private void updateEntity(MMatchPO entity, Map<String, Object> payload, ADOrganization org) {
        log.debug("Updating the existing MMatchPO : {}", entity);
        entity.cConversionRate(toBigDecimal(payload, "PRCRR"))
            .deliveryNo(nullableToString(payload.get("PRSHPN")))
            .foreignActual(toBigDecimal(payload, "PRFRRC"))
            .foreignTaxAmount(toBigDecimal(payload, "PRCTAM"))
            .foreignTotalLines(toBigDecimal(payload, "PRFREC"))
            .openAmount(toBigDecimal(payload, "PRAOPN"))
            .openForeignAmount(toBigDecimal(payload, "PRFAP"))
            .openQty(toBigDecimal(payload, "PRUOPN"))
            .priceActual(toBigDecimal(payload, "PRPRRC"))
            .qty(toBigDecimal(payload, "PRUREC"))
            .taxAmount(toBigDecimal(payload, "PRSTAM"))
            .taxable(stringToBoolean((String) payload.get("PRTX")))
            .totalLines(toBigDecimal(payload, "PRAREC"))
            .itemDesc1(nullableToString(payload.get("PRLITM")))
            .itemDesc2(nullableToString(payload.get("PRAITM")))

            // Lookup to master data.
            .cCurrency(buildCurrency(payload, org))
            .cTaxCategory(buildTaxCategory(payload, org))
            .cTax(buildTax(payload, entity.getCTaxCategory(), org))
            .cUom(buildUnitOfMeasure(payload, org))
            .mProduct(buildProduct(payload, entity.getCUom(), org))
            .cVendor(buildVendor(String.valueOf(payload.get("PRAN8"))))
            .mWarehouse(buildWarehouse(payload, org))
            .mLocator(buildLocator(payload, entity.getMWarehouse(), org));
    }

    private MMatchPO buildEntity(Map<String, Object> payload, ADOrganization org) {
        log.debug("Creating a new MMatchPO");
        MMatchPO entity = new MMatchPO();

        entity.cConversionRate(toBigDecimal(payload, "PRCRR"))
            .cDocType((String) payload.get("PRDCTO"))
            .cDocTypeMr((String) payload.get("PRDCT"))
            .poDate(LocalDate.parse((String) payload.get("PRTRDJ")))
            .receiptDate(LocalDate.parse((String) payload.get("PRRCDJ")))
            .dateAccount(LocalDate.parse((String) payload.get("PRDGL")))
            .deliveryNo(nullableToString(payload.get("PRSHPN")))
            .description( StringUtils.trimToNull((String) payload.get("PRVRMK")) )
            .foreignActual(toBigDecimal(payload, "PRFRRC"))
            .foreignTaxAmount(toBigDecimal(payload, "PRCTAM"))
            .foreignTotalLines(toBigDecimal(payload, "PRFREC"))
            .lineNoMr((int) payload.get("PRNLIN"))
            .lineNoPo((int) payload.get("PRLNID"))
            .mMatchType((String) payload.get("PRMATC"))
            .openAmount(toBigDecimal(payload, "PRAOPN"))
            .openForeignAmount(toBigDecimal(payload, "PRFAP"))
            .openQty(toBigDecimal(payload, "PRUOPN"))
            .orderSuffix((String) payload.get("PRSFXO"))
            .poNo(nullableToString(payload.get("PRDOCO")))
            .priceActual(toBigDecimal(payload, "PRPRRC"))
            .qty(toBigDecimal(payload, "PRUREC"))
            .receiptNo(nullableToString(payload.get("PRDOC")))
            .taxAmount(toBigDecimal(payload, "PRSTAM"))
            .taxable(stringToBoolean((String) payload.get("PRTX")))
            .totalLines(toBigDecimal(payload, "PRAREC"))
            .itemDesc1(nullableToString(payload.get("PRLITM")))
            .itemDesc2(nullableToString(payload.get("PRAITM")))

            // Lookup to master data.
            .adOrganization(org)
            .cCostCenter(cCostCenterService.getDefaultCostCenter())
            .cCurrency(buildCurrency(payload, org))
            .cTaxCategory(buildTaxCategory(payload, org))
            .cTax(buildTax(payload, entity.getCTaxCategory(), org))
            .cUom(buildUnitOfMeasure(payload, org))
            .cVendor(buildVendor(String.valueOf(payload.get("PRAN8"))))
            .mProduct(buildProduct(payload, entity.getCUom(), org))
            .mWarehouse(buildWarehouse(payload, org))
            .mLocator(buildLocator(payload, entity.getMWarehouse(), org));

        return entity;
    }

    private CCurrency buildCurrency(Map<String, Object> payload, final ADOrganization org) {
        final String code = (String) payload.get("PRCRCD");

        if (code == null) {
            return null;
        }

        return cCurrencyRepository.findFirstByCode(code)
            .orElseGet(() -> {
                final CCurrency currency = new CCurrency();
                currency.active(true)
                    .adOrganization(org)
                    .code(code)
                    .name(code)
                    .symbol(code);

                return cCurrencyRepository.save(currency);
            });
    }

    private CTaxCategory buildTaxCategory(Map<String, Object> payload, final ADOrganization org) {
        final String name = StringUtils.trimToNull((String) payload.get("PREXR1"));

        if (name == null) {
            return null;
        }

        return cTaxCategoryRepository.findFirstByNameAndAdOrganization(name, org)
            .orElseGet(() -> {
                final CTaxCategory taxCategory = new CTaxCategory();
                taxCategory.active(true)
                    .adOrganization(org)
                    .name(name)
                    .description(name)
                    .isWithholding(true);

                return cTaxCategoryRepository.save(taxCategory);
            });
    }

    private CTax buildTax(Map<String, Object> payload, CTaxCategory taxCategory, final ADOrganization org) {
        final String name = StringUtils.trimToNull((String) payload.get("PRTXA1"));
        
        if (name == null) {
            return null;
        }

        return cTaxRepository.findFirstByNameAndTaxCategoryAndAdOrganization(name, taxCategory, org)
            .orElseGet(() -> {
                final CTax tax = new CTax()
                    .active(true)
                    .adOrganization(org)
                    .name(name)
                    .description(name)
                    .taxCategory(taxCategory)
                    .transactionType(CTaxTransactionType.BOTH);
                return cTaxRepository.save(tax);
            });
    }

    private CUnitOfMeasure buildUnitOfMeasure(Map<String, Object> payload, final ADOrganization org) {
        final String code = (String) payload.get("PRUOM");

        return cUnitOfMeasureRepository.findFirstByCodeAndAdOrganizationId(code, org.getId())
            .orElseGet(() -> {
                final CUnitOfMeasure uom = new CUnitOfMeasure();
                uom.active(true)
                    .adOrganization(org)
                    .code(code)
                    .name(code)
                    .symbol(code);

                return cUnitOfMeasureRepository.save(uom);
            });
    }

    private CWarehouse buildWarehouse(Map<String, Object> payload, final ADOrganization org) {
        final String code = StringUtils.trimToNull((String) payload.get("PRMCU"));

        if (code == null) {
            return null;
        }

        return cWarehouseRepository.findFirstByCodeAndAdOrganizationId(code, org.getId())
            .orElseGet(() -> {
                final CWarehouse warehouse = new CWarehouse();
                warehouse.active(true)
                    .adOrganization(org)
                    .code(code)
                    .name(code);

                return cWarehouseRepository.save(warehouse);
            });
    }

    private CLocator buildLocator(Map<String, Object> payload, CWarehouse warehouse, final ADOrganization org) {
        final String code = StringUtils.trimToNull((String) payload.get("PRLOCN"));

        if (code == null) {
            return null;
        }

        return cLocatorRepository.findFirstByCodeAndWarehouseIdAndAdOrganizationId(code, warehouse.getId(), org.getId())
            .orElseGet(() -> {
                final CLocator locator = new CLocator();
                locator.active(true)
                    .adOrganization(org)
                    .code(code)
                    .warehouse(warehouse);

                return cLocatorRepository.save(locator);
            });
    }

    private CVendor buildVendor(String code) {
        return cVendorRepository.findFirstByCode(code).orElse(null);
    }

    private CProduct buildProduct(Map<String, Object> payload, CUnitOfMeasure uom, final ADOrganization org) {
        final String code = String.valueOf(payload.get("PRITM"));
        final String name = StringUtils.trimToNull((String) payload.get("PDDSC1"));
        final String description = StringUtils.trimToNull((String) payload.get("PDDSC2"));

        return cProductRepository.findFirstByNameAndAdOrganizationId(name, org.getId())
            .orElseGet(() -> {
                final CProduct product = new CProduct();
                product.active(true)
                    .adOrganization(org)
                    .code(code)
                    .name(name)
                    .description(description)
                    .type(cProductService.getDefaultType())
                    .assetAcct(cProductService.getDefaultAssetAccount())
                    .expenseAcct(cProductService.getDefaultExpenseAccount())
                    .productCategory(cProductService.getDefaultCategory())
                    .productClassification(cProductService.getDefaultClassification())
                    .uom(uom);

                return cProductRepository.save(product);
            });
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
