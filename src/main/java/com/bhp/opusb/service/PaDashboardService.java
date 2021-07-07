package com.bhp.opusb.service;

import com.bhp.opusb.domain.*;
import com.bhp.opusb.repository.*;
import com.bhp.opusb.service.dto.DashboardMyDocument;
import com.bhp.opusb.service.dto.PaDashboardDTO;
import com.bhp.opusb.service.mapper.PaDashboardMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.event.ListDataEvent;
import java.math.BigInteger;
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
