package com.bhp.opusb.service;

import com.bhp.opusb.domain.*;
import com.bhp.opusb.repository.*;
import com.bhp.opusb.service.dto.DashboardMyDocument;
import com.bhp.opusb.service.dto.DashboardSpendByCostCenter;
import com.bhp.opusb.service.dto.PaDashboardDTO;
import com.bhp.opusb.service.mapper.PaDashboardMapper;
import com.bhp.opusb.util.MapperJSONUtil;
import liquibase.pro.packaged.O;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.event.ListDataEvent;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

/**
 * Service Implementation for managing {@link PaDashboard}.
 */
@Service
@Transactional
public class PaDashboardService {

    private final Logger log = LoggerFactory.getLogger(PaDashboardService.class);

    private final PaDashboardRepository paDashboardRepository;

    private final PaDashboardMapper paDashboardMapper;
    private final MBiddingRepository mBiddingRepository;
    private final MContractRepository mContractRepository;
    private final MPurchaseOrderRepository mPurchaseOrderRepository;
    private final MRequisitionRepository mRequisitionRepository;
    private final MRfqRepository mRfqRepository;





    public PaDashboardService(PaDashboardRepository paDashboardRepository, PaDashboardMapper paDashboardMapper, MBiddingRepository mBiddingRepository, MContractRepository mContractRepository, MPurchaseOrderRepository mPurchaseOrderRepository, MRequisitionRepository mRequisitionRepository, MRfqRepository mRfqRepository) {
        this.paDashboardRepository = paDashboardRepository;
        this.paDashboardMapper = paDashboardMapper;
        this.mBiddingRepository = mBiddingRepository;
        this.mContractRepository = mContractRepository;
        this.mPurchaseOrderRepository = mPurchaseOrderRepository;
        this.mRequisitionRepository = mRequisitionRepository;
        this.mRfqRepository = mRfqRepository;
    }

    /**
     * Save a paDashboard.
     *
     * @param paDashboardDTO the entity to save.
     * @return the persisted entity.
     */
    public PaDashboardDTO save(PaDashboardDTO paDashboardDTO) {
        log.debug("Request to save PaDashboard : {}", paDashboardDTO);
        PaDashboard paDashboard = paDashboardMapper.toEntity(paDashboardDTO);
        paDashboard = paDashboardRepository.save(paDashboard);
        return paDashboardMapper.toDto(paDashboard);
    }

    /**
     * Get all the paDashboards.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PaDashboardDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PaDashboards");
        return paDashboardRepository.findAll(pageable)
            .map(paDashboardMapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<DashboardMyDocument> findAllMyDocument() {
        List<MBidding> bidding = mBiddingRepository.findAll();
        List<MContract> contracts =mContractRepository.findAll();
        List<MPurchaseOrder> purchaseOrders = mPurchaseOrderRepository.findAll();
        List<MRequisition> requisitions = mRequisitionRepository.findAll();
        List<MRfq> quotation= mRfqRepository.findAll();

        List<DashboardMyDocument> myDocuments = new ArrayList<>();

        bidding.forEach(mBidding -> {
            DashboardMyDocument dashboardMyDocument = new DashboardMyDocument();
            dashboardMyDocument.setDocumentNo(mBidding.getDocumentNo());
            dashboardMyDocument.setStatus(mBidding.getDocumentStatus());
            dashboardMyDocument.setTitle("Bidding");
            dashboardMyDocument.setDate(mBidding.getLastModifiedDate());
            myDocuments.add(dashboardMyDocument);
        });

        contracts.forEach(mBidding -> {
            DashboardMyDocument dashboardMyDocument = new DashboardMyDocument();
            dashboardMyDocument.setDocumentNo(mBidding.getDocumentNo());
            dashboardMyDocument.setStatus(mBidding.getDocumentStatus());
            dashboardMyDocument.setTitle("Contracts");
            dashboardMyDocument.setDate(mBidding.getLastModifiedDate());
            myDocuments.add(dashboardMyDocument);
        });

        purchaseOrders.forEach(mBidding -> {
            DashboardMyDocument dashboardMyDocument = new DashboardMyDocument();
            dashboardMyDocument.setDocumentNo(mBidding.getDocumentNo());
            dashboardMyDocument.setStatus(mBidding.getDocumentStatus());
            dashboardMyDocument.setTitle("Purchase Orders");
            dashboardMyDocument.setDate(mBidding.getLastModifiedDate());
            myDocuments.add(dashboardMyDocument);
        });

        requisitions.forEach(mBidding -> {
            DashboardMyDocument dashboardMyDocument = new DashboardMyDocument();
            dashboardMyDocument.setDocumentNo(mBidding.getDocumentNo());
            dashboardMyDocument.setStatus(mBidding.getDocumentStatus());
            dashboardMyDocument.setTitle("Requisitions");
            dashboardMyDocument.setDate(mBidding.getLastModifiedDate());
            myDocuments.add(dashboardMyDocument);
        });

        quotation.forEach(mBidding -> {
            DashboardMyDocument dashboardMyDocument = new DashboardMyDocument();
            dashboardMyDocument.setDocumentNo(mBidding.getDocumentNo());
            dashboardMyDocument.setStatus(mBidding.getDocumentStatus());
            dashboardMyDocument.setTitle("Quotation");
            dashboardMyDocument.setDate(mBidding.getLastModifiedDate());
            myDocuments.add(dashboardMyDocument);
        });

        return myDocuments;
    }


    public void ContractCompliancebyBusinessCategory() {
        List<MContract> mContracts = mContractRepository.findAll();
        List<String> busnisCategory = new ArrayList<>();
        Map<String, BigInteger> map = new HashMap<>();
        mContracts.forEach(mContract -> {
            String bc = mContract.getBidding().getQuotation().getBusinessCategory().getName();
            if (!busnisCategory.contains(bc) && bc!=null) {
                mContract.getBidding().getQuotation().getBusinessCategory().getName();
            }
        });
    }

    public Map<String, Object> ContractSpendByCostCenter() {
       List<MPurchaseOrder> purchaseOrders =mPurchaseOrderRepository.findAll();
       Map<String,List<MPurchaseOrder>> map = new HashMap<>();
       List<String> costCenterList = new ArrayList<>();
        List<String> header = new ArrayList<>();
       purchaseOrders.forEach(mPurchaseOrder -> {

           if (!costCenterList.contains(mPurchaseOrder.getCostCenter().getName())){
               costCenterList.add(mPurchaseOrder.getCostCenter().getName());
           }


           ZoneId z = ZoneId.of( "Asia/Tokyo" );
           LocalDate myDate = mPurchaseOrder.getCreatedDate().atZone(z).toLocalDate(); ;
           List<MPurchaseOrder> purchaseOrders_ = new ArrayList<>();
           purchaseOrders_.add(mPurchaseOrder);
           try {
               purchaseOrders_.addAll(map.get(String.valueOf( myDate.getYear())
                   +String.valueOf( myDate.getMonth())));
           }catch (Exception e){}
           map.put(String.valueOf( myDate.getYear())+String.valueOf( myDate.getMonth()),purchaseOrders_);
       });

       List<DashboardSpendByCostCenter> dashboardSpendByCostCenters =new ArrayList<>();

       costCenterList.forEach(s -> {
           DashboardSpendByCostCenter dashboardSpendByCostCenter = new DashboardSpendByCostCenter();
           dashboardSpendByCostCenter.setName(s);
           dashboardSpendByCostCenter.setType("line");
           List<BigDecimal> cost = new ArrayList<>();

           for (int i = 0; i < 13; i++) {

               LocalDate date = LocalDate.now().minusMonths(i);
               if (!header.contains(String.valueOf( date.getYear())
                   +String.valueOf( date.getMonth()))){
                   header.add(String.valueOf( date.getYear())
                       +String.valueOf( date.getMonth()));
               }
               final BigDecimal[] costIn = {BigDecimal.ZERO};
               List<MPurchaseOrder> purchaseOrders_ =map.get(String.valueOf( date.getYear())
                   +String.valueOf( date.getMonth()));

               log.info("this data1 {}",purchaseOrders_);

               if (purchaseOrders_!=null) {
                   log.info("this data2 {}");
                   purchaseOrders_.forEach(mPurchaseOrder -> {
                       log.info("this data3 {}",mPurchaseOrder);
                       if (mPurchaseOrder.getCostCenter().getName().contains(s)){
                           costIn[0] = costIn[0].add(mPurchaseOrder.getGrandTotal());
                           log.info("cost1 {}", costIn[0]);
                           log.info("cost4 {}",(mPurchaseOrder.getGrandTotal()));
                       }
                   });
                   log.info("cost2 {}", costIn[0]);
                   cost.add(costIn[0]);
               }else {cost.add(BigDecimal.ZERO);}
           }
           dashboardSpendByCostCenter.setData(cost);
           dashboardSpendByCostCenters.add(dashboardSpendByCostCenter);
       });


       log.info("this map {}", MapperJSONUtil.prettyLog(map));
       log.info("this data list dashbord {}",dashboardSpendByCostCenters);

       Map<String,Object> sent =new HashMap<>();
       sent.put("Data",dashboardSpendByCostCenters);
       sent.put("Header",header);
       return sent;
    }

    /**
     * Get one paDashboard by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PaDashboardDTO> findOne(Long id) {
        log.debug("Request to get PaDashboard : {}", id);
        return paDashboardRepository.findById(id)
            .map(paDashboardMapper::toDto);
    }

    /**
     * Delete the paDashboard by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PaDashboard : {}", id);
        paDashboardRepository.deleteById(id);
    }
}
