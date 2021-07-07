package com.bhp.opusb.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.*;

import javax.sql.DataSource;

import com.bhp.opusb.config.ApplicationProperties;
import com.bhp.opusb.config.ApplicationProperties.Document;
import com.bhp.opusb.domain.*;
import com.bhp.opusb.repository.*;
import com.bhp.opusb.service.dto.MPurchaseOrderDTO;
import com.bhp.opusb.service.dto.MPurchaseOrderLineDTO;
import com.bhp.opusb.service.dto.MRequisitionLineDTO;
import com.bhp.opusb.service.mapper.MPurchaseOrderLineMapper;
import com.bhp.opusb.service.mapper.MPurchaseOrderMapper;
import com.bhp.opusb.service.mapper.MRequisitionLineMapper;
import com.bhp.opusb.util.DocumentUtil;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

/**
 * Service Implementation for managing {@link MPurchaseOrder}.
 */
@Service
@Transactional
public class MPurchaseOrderService {

    private final Logger log = LoggerFactory.getLogger(MPurchaseOrderService.class);

    private final DataSource dataSource;
    private final MPurchaseOrderRepository mPurchaseOrderRepository;
    private final MPurchaseOrderLineRepository mPurchaseOrderLineRepository;
    private final MRequisitionRepository mRequisitionRepository;
    private final MRequisitionLineRepository mRequisitionLineRepository;
    private final CDocumentTypeRepository cDocumentTypeRepository;
    private final CPaymentTermRepository cPaymentTermRepository;
    private final CProductRepository cProductRepository;


    private final CVendorTaxRepository cVendorTaxRepository;

    private final MPurchaseOrderMapper mPurchaseOrderMapper;
    private final MRequisitionLineMapper mRequisitionLineMapper;
    private final MPurchaseOrderLineMapper mPurchaseOrderLineMapper;
    private final Document document;

    private final MBiddingRepository mBiddingRepository;

    public MPurchaseOrderService(ApplicationProperties applicationProperties,
                                 MPurchaseOrderRepository mPurchaseOrderRepository, CDocumentTypeRepository cDocumentTypeRepository,
                                 CPaymentTermRepository cPaymentTermRepository,
                                 MRequisitionRepository mRequisitionRepository, MRequisitionLineRepository mRequisitionLineRepository, MPurchaseOrderLineRepository mPurchaseOrderLineRepository,
                                 CVendorTaxRepository cVendorTaxRepository, MPurchaseOrderMapper mPurchaseOrderMapper, MPurchaseOrderLineMapper mPurchaseOrderLineMapper,
                                 MBiddingRepository mBiddingRepository, MRequisitionLineMapper mRequisitionLineMapper, DataSource dataSource, CProductRepository cProductRepository) {
        this.mPurchaseOrderRepository = mPurchaseOrderRepository;
        this.mPurchaseOrderLineRepository = mPurchaseOrderLineRepository;
        this.mRequisitionRepository = mRequisitionRepository;
        this.mRequisitionLineRepository = mRequisitionLineRepository;
        this.cDocumentTypeRepository = cDocumentTypeRepository;
        this.cVendorTaxRepository = cVendorTaxRepository;
        this.mPurchaseOrderMapper = mPurchaseOrderMapper;
        this.mRequisitionLineMapper = mRequisitionLineMapper;
        this.mPurchaseOrderLineMapper = mPurchaseOrderLineMapper;
        this.dataSource = dataSource;
        this.mBiddingRepository = mBiddingRepository;
        this.cPaymentTermRepository = cPaymentTermRepository;

        document = applicationProperties.getDocuments().get("purchaseOrder");
        this.cProductRepository = cProductRepository;
    }

    /**
     * Save a mPurchaseOrder.
     *
     * @param mPurchaseOrderDTO the entity to save.
     * @return the persisted entity.
     */
    public MPurchaseOrderDTO save(MPurchaseOrderDTO mPurchaseOrderDTO) {
        log.debug("Request to save MPurchaseOrder : {}", mPurchaseOrderDTO);

        MPurchaseOrder mPurchaseOrder = mPurchaseOrderMapper.toEntity(mPurchaseOrderDTO);

        if (mPurchaseOrder.getDocumentNo() == null) {
            mPurchaseOrder.setDocumentNo(initDocumentNumber());
        }

        if (mPurchaseOrder.getDocumentType() == null) {
            cDocumentTypeRepository.findFirstByName(document.getDocumentType())
                .ifPresent(mPurchaseOrder::setDocumentType);
        }

        if (mPurchaseOrder.getPaymentTerm() == null) {
            cPaymentTermRepository.findFirstByCode("3M").ifPresent(mPurchaseOrder::setPaymentTerm);
        }

        mPurchaseOrder = mPurchaseOrderRepository.save(mPurchaseOrder);
        return mPurchaseOrderMapper.toDto(mPurchaseOrder);
    }

    /**
     * Generate mPurchaseOrders from requisition.
     *
     * @param mPurchaseOrderDTO the entity to save.
     * @return the persisted entity.
     */
    public List<MPurchaseOrderDTO> saveFromRequisition(MPurchaseOrderDTO mPurchaseOrderDTO) {
        log.debug("Request to generate MPurchaseOrder : {}", mPurchaseOrderDTO);

        // Map of vendorId and MPurchaseOrder entity.
        // Temporarily map the MPurchaseOrder entity for each vendor.
        Map<Long, MPurchaseOrder> purchaseOrders = new HashMap<>();
        List<MRequisitionLineDTO> requisitionLines = mPurchaseOrderDTO.getRequisitionLines();
        List<MPurchaseOrderLine> purchaseOrderLines = new ArrayList<>(requisitionLines.size());

        for (MRequisitionLineDTO mRequisitionLineDTO : requisitionLines) {
            MRequisitionLine mRequisitionLine = mRequisitionLineMapper.toEntity(mRequisitionLineDTO);

            MPurchaseOrder mPurchaseOrder = purchaseOrders.computeIfAbsent(mRequisitionLineDTO.getVendorId(),
                    key -> initPurchaseOrder(mPurchaseOrderDTO, mRequisitionLine));

            MPurchaseOrderLine mPurchaseOrderLine = initPurchaseOrderLine(mPurchaseOrder, mRequisitionLine);
            final BigDecimal grandTotal = mPurchaseOrder.getGrandTotal().add(mPurchaseOrderLine.getOrderAmount());

            mPurchaseOrder.setGrandTotal(grandTotal);
            purchaseOrderLines.add(mPurchaseOrderLine);
        }

        List<MPurchaseOrderDTO> result = mPurchaseOrderMapper.toDto(mPurchaseOrderRepository.saveAll(purchaseOrders.values()));
        mPurchaseOrderLineRepository.saveAll(purchaseOrderLines);
        mRequisitionLineRepository.saveAll(mRequisitionLineMapper.toEntity(requisitionLines));
        return result;
    }

    /**
     * First initialization of MPurchaseOrder entity.
     * @param mPurchaseOrderDTO
     * @param mRequisitionLine
     * @return
     */
    private MPurchaseOrder initPurchaseOrder(MPurchaseOrderDTO mPurchaseOrderDTO, MRequisitionLine mRequisitionLine) {
        MPurchaseOrder mPurchaseOrder = mPurchaseOrderMapper.toEntity(mPurchaseOrderDTO);
        Optional<CVendorTax> vendorTaxInfo = cVendorTaxRepository.findFirstByVendor(mRequisitionLine.getVendor());
        boolean taxable = false;

        if (vendorTaxInfo.isPresent()) {
            taxable = Boolean.TRUE.equals(vendorTaxInfo.get().isTaxableEmployers());
        }

        // Set the default Purchase Order document type.
        if (mPurchaseOrder.getDocumentType() == null) {
            cDocumentTypeRepository.findFirstByName(document.getDocumentType())
                .ifPresent(mPurchaseOrder::setDocumentType);
        }

        // Set the requestor of the purchase order.
        mRequisitionRepository.findById(mRequisitionLine.getRequisition().getId())
                .ifPresent(mRequisition
                    -> mPurchaseOrder
                        .costCenter(mRequisition.getCostCenter())
                        .setCreatedBy(mRequisition.getCreatedBy()));

        return mPurchaseOrder.active(true)
            .documentNo(initDocumentNumber())
            .grandTotal(new BigDecimal(0))
            .tax(taxable)
            .vendor(mRequisitionLine.getVendor());
    }

    private String initDocumentNumber() {
        return DocumentUtil.buildDocumentNumber(document.getDocumentNumberPrefix(), mPurchaseOrderRepository);
    }

    /**
     * Build Purchase Order from Vendor Confirmation
     *
     * @return persisted DTO
     */
    public MPurchaseOrderDTO generatePurchaseOrderFromVendor(MPurchaseOrderDTO mpoDto) {
        MPurchaseOrderDTO savedDto = save(mpoDto);
        List<MPurchaseOrderLine> mpols = new ArrayList<>();
        for(MPurchaseOrderLineDTO lines: mpoDto.getPoLines()){
            lines.setPurchaseOrderId(savedDto.getId());
            mpols.add(mPurchaseOrderLineMapper.toEntity(lines));
        }
        MBidding bidding = mBiddingRepository.findById(mpoDto.getBiddingId()).get();
        if(!"F".contentEquals(bidding.getBiddingStatus())){
            bidding.setBiddingStatus("F");
            mBiddingRepository.save(bidding);
        }
        mPurchaseOrderLineRepository.saveAll(mpols);
        return savedDto;
    }

    /**
     * Build MPurchaseOrderLine from an MRequisitionLineDTO.
     * @param
     * @return
     */
    private MPurchaseOrderLine initPurchaseOrderLine(MPurchaseOrder mPurchaseOrder, MRequisitionLine mRequisitionLine) {
        final MPurchaseOrderLine mPurchaseOrderLine = new MPurchaseOrderLine();
        final BigDecimal orderAmount = mRequisitionLine.getQuantityOrdered().multiply(mRequisitionLine.getUnitPrice());

        mPurchaseOrderLine.active(true)
            .adOrganization(mPurchaseOrder.getAdOrganization())
            .costCenter(mPurchaseOrder.getCostCenter())
            .datePromised(mPurchaseOrder.getDatePromised())
            .dateTrx(mPurchaseOrder.getDateTrx())
            .orderAmount(orderAmount)
            .product(mRequisitionLine.getProduct())
            .purchaseOrder(mPurchaseOrder)
            .quantity(mRequisitionLine.getQuantityOrdered())
            .remark(mRequisitionLine.getRemark())
            .requisition(mRequisitionLine.getRequisition())
            .unitPrice(mRequisitionLine.getUnitPrice())
            .uom(mRequisitionLine.getUom())
            .vendor(mRequisitionLine.getVendor())
            .warehouse(mPurchaseOrder.getWarehouse());

        return mPurchaseOrderLine;
    }

    /**
     * Get all the mPurchaseOrders.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MPurchaseOrderDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MPurchaseOrders");
        return mPurchaseOrderRepository.findAll(pageable)
            .map(mPurchaseOrderMapper::toDto);
    }

    @Transactional(readOnly = true)
    public  List<MPurchaseOrderLineDTO> findAllForDashbord() {
       List<MPurchaseOrder> mPurchaseOrder = mPurchaseOrderRepository.findByDocumentStatus("APV");
       List<CProduct> cProducts = cProductRepository.findAll();
       Map<String,BigDecimal> map = new HashMap<>();
       List<MPurchaseOrderLine> data = new ArrayList<>();
       mPurchaseOrder.forEach(mPurchaseOrder1 -> {
           data.addAll(mPurchaseOrderLineRepository.mPOlinebyidpr(mPurchaseOrder1.getId()));
       });

       ArrayList<String> listProduct =new ArrayList<>();

       data.forEach(mPurchaseOrderLine -> {
           BigDecimal pas = map.get(mPurchaseOrderLine.getProduct().getName());
            if (!listProduct.contains(mPurchaseOrderLine.getProduct().getName())){
                listProduct.add(mPurchaseOrderLine.getProduct().getName());
            }
           if (pas==null){
               map.put(mPurchaseOrderLine.getProduct().getName(),
                   mPurchaseOrderLine.getOrderAmount());
           }else { map.put(mPurchaseOrderLine.getProduct().getName(),
               mPurchaseOrderLine.getOrderAmount().add(pas));}
       });
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String json = objectMapper.writeValueAsString(map);
            System.out.println(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        List<MPurchaseOrderLineDTO> mPurchaseOrderLineDTOS = new ArrayList<>();

        listProduct.forEach(s -> {
            MPurchaseOrderLineDTO mPurchaseOrderLineDTO = new MPurchaseOrderLineDTO();
            mPurchaseOrderLineDTO.setProductName(s);
            mPurchaseOrderLineDTO.setOrderAmount(map.get(s));
            mPurchaseOrderLineDTOS.add(mPurchaseOrderLineDTO);

        });
       return mPurchaseOrderLineDTOS;
    }
    /**
     * Get one mPurchaseOrder by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MPurchaseOrderDTO> findOne(Long id) {
        log.debug("Request to get MPurchaseOrder : {}", id);
        return mPurchaseOrderRepository.findById(id)
            .map(mPurchaseOrderMapper::toDto);
    }

    /**
     * Delete the mPurchaseOrder by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MPurchaseOrder : {}", id);
        mPurchaseOrderRepository.deleteById(id);
    }

    public JasperPrint exportPurchaseOrder(Long poNo) throws FileNotFoundException, JRException, SQLException {
        File file = ResourceUtils.getFile("classpath:templates/report/purchase-order.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("poNo", poNo);
        return JasperFillManager.fillReport(jasperReport, parameters, dataSource.getConnection());

    }

    /**
     * TODO Use a generic method to update the document status for every entities.
     * TODO Use the workflow engine for maintaining the flow state.
     */
    public void updateDocumentStatus(MPurchaseOrderDTO mPurchaseOrderDTO) {
        log.debug("Request to update MRequisition's document status : {}", mPurchaseOrderDTO);
        MPurchaseOrder mPurchaseOrder = mPurchaseOrderMapper.toEntity(mPurchaseOrderDTO);
        String action = mPurchaseOrder.getDocumentAction();
        String status = mPurchaseOrder.getDocumentStatus();
        boolean approved = false;
        boolean processed = false;

        if (DocumentUtil.isApprove(mPurchaseOrder.getDocumentStatus())) {
            approved = true;
            processed = true;
        } else if (DocumentUtil.isReject(mPurchaseOrder.getDocumentStatus())) {
            processed = true;
        }

        mPurchaseOrderRepository.updateDocumentStatus(mPurchaseOrder.getId(), action, status, approved, processed);
    }
}
