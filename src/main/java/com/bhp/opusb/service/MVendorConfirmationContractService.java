package com.bhp.opusb.service;

import com.bhp.opusb.domain.AdUser;
import com.bhp.opusb.domain.MBidding;
import com.bhp.opusb.domain.MVendorConfirmationContract;
import com.bhp.opusb.domain.MVendorConfirmationLine;
import com.bhp.opusb.repository.AdUserRepository;
import com.bhp.opusb.repository.MVendorConfirmationContractRepository;
import com.bhp.opusb.repository.MVendorConfirmationLineRepository;
import com.bhp.opusb.service.dto.MVendorConfirmationContractDTO;
import com.bhp.opusb.service.mapper.MVendorConfirmationContractMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link MVendorConfirmationContract}.
 */
@Service
@Transactional
public class MVendorConfirmationContractService {

    private final Logger log = LoggerFactory.getLogger(MVendorConfirmationContractService.class);

    private final MVendorConfirmationContractRepository mVendorConfirmationContractRepository;
    private final MVendorConfirmationLineRepository mVendorConfirmationLineRepository;
    private final AdUserRepository adUserRepository;

    private final MVendorConfirmationContractMapper mVendorConfirmationContractMapper;

    private final MailService mailService;

    public MVendorConfirmationContractService(MVendorConfirmationContractRepository mVendorConfirmationContractRepository,
     MVendorConfirmationContractMapper mVendorConfirmationContractMapper, MailService mailService,
     MVendorConfirmationLineRepository mVendorConfirmationLineRepository, AdUserRepository adUserRepository
     ) {
        this.mVendorConfirmationContractRepository = mVendorConfirmationContractRepository;
        this.mVendorConfirmationContractMapper = mVendorConfirmationContractMapper;
        this.mVendorConfirmationLineRepository = mVendorConfirmationLineRepository;
        this.adUserRepository = adUserRepository;
        this.mailService = mailService;
    }

    /**
     * Save a mVendorConfirmationContract.
     *
     * @param mVendorConfirmationContractDTO the entity to save.
     * @return the persisted entity.
     */
    public MVendorConfirmationContractDTO save(MVendorConfirmationContractDTO mVendorConfirmationContractDTO) {
        log.debug("Request to save MVendorConfirmationContract : {}", mVendorConfirmationContractDTO);
        MVendorConfirmationContract mVendorConfirmationContract = mVendorConfirmationContractMapper.toEntity(mVendorConfirmationContractDTO);
        mVendorConfirmationContract.setActive(true);
        mVendorConfirmationContract = mVendorConfirmationContractRepository.save(mVendorConfirmationContract);
        
        return mVendorConfirmationContractMapper.toDto(mVendorConfirmationContract);
    }

    /**
     * Save a mVendorConfirmationContract.
     *
     * @param mVendorConfirmationContractDTO the entity to save.
     * @return the persisted entity.
     */
    public void publish(MVendorConfirmationContractDTO mVendorConfirmationContractDTO) {
        log.debug("Request to publish MVendorConfirmationContract : {}", mVendorConfirmationContractDTO);
        MVendorConfirmationContract mVendorConfirmationContract = mVendorConfirmationContractMapper.toEntity(mVendorConfirmationContractDTO);
        mVendorConfirmationContract.setActive(false);
        mVendorConfirmationContract.setPublishDate(LocalDate.now());
        
        // updates the line status to published as well
        MVendorConfirmationLine mvcl = mVendorConfirmationLineRepository.findById(mVendorConfirmationContractDTO.getVendorConfirmationLineId()).get();
        mvcl.setStatus("P");

        MBidding mb = mvcl.getVendorConfirmation().getBidding();
        mb.setBiddingStatus("F");

        String emailBody = "Contract for bidding " + mvcl.getVendorConfirmation().getBidding().getName() + " is available for confirmation.";

        List<AdUser> pics = adUserRepository.findBycVendor(mvcl.getVendor());
        if(pics.size()==0) log.debug("No pics found for vendor {}", mvcl.getVendor());
        for(AdUser pic: pics){
            log.debug("Sending vendor contract notification to {}", pic.getUser().getEmail());
            mailService.sendEmail(pic.getUser().getEmail(), "Contract Available", emailBody, false, false);
        }

        mVendorConfirmationLineRepository.save(mvcl);
        mVendorConfirmationContractRepository.save(mVendorConfirmationContract);
    }

    /**
     * Get all the mVendorConfirmationContracts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MVendorConfirmationContractDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MVendorConfirmationContracts");
        return mVendorConfirmationContractRepository.findAll(pageable)
            .map(mVendorConfirmationContractMapper::toDto);
    }

    /**
     * Get one mVendorConfirmationContract by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MVendorConfirmationContractDTO> findOne(Long id) {
        log.debug("Request to get MVendorConfirmationContract : {}", id);
        return mVendorConfirmationContractRepository.findById(id)
            .map(mVendorConfirmationContractMapper::toDto);
    }

    /**
     * Delete the mVendorConfirmationContract by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MVendorConfirmationContract : {}", id);
        mVendorConfirmationContractRepository.deleteById(id);
    }
}
