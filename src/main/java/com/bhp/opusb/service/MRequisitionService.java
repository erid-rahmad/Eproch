package com.bhp.opusb.service;

import com.bhp.opusb.domain.MRequisition;
import com.bhp.opusb.repository.MRequisitionRepository;
import com.bhp.opusb.service.dto.MRequisitionDTO;
import com.bhp.opusb.service.mapper.MRequisitionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MRequisition}.
 */
@Service
@Transactional
public class MRequisitionService {

    private final Logger log = LoggerFactory.getLogger(MRequisitionService.class);

    private final MRequisitionRepository mRequisitionRepository;

    private final MRequisitionMapper mRequisitionMapper;

    private final UserService userService;

    public MRequisitionService(MRequisitionRepository mRequisitionRepository, MRequisitionMapper mRequisitionMapper, UserService userService) {
        this.mRequisitionRepository = mRequisitionRepository;
        this.mRequisitionMapper = mRequisitionMapper;
        this.userService = userService;
    }

    /**
     * Save a mRequisition.
     *
     * @param mRequisitionDTO the entity to save.
     * @return the persisted entity.
     */
    public MRequisitionDTO save(MRequisitionDTO mRequisitionDTO) {
        log.debug("Request to save MRequisition : {}", mRequisitionDTO);
        MRequisition mRequisition = mRequisitionMapper.toEntity(mRequisitionDTO);
        mRequisition = mRequisitionRepository.save(mRequisition);
        return mRequisitionMapper.toDto(mRequisition);
    }

    /**
     * Get all the mRequisitions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MRequisitionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MRequisitions");
        return mRequisitionRepository.findAll(pageable)
            .map(mRequisitionMapper::toDto);
    }

    /**
     * Get one mRequisition by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MRequisitionDTO> findOne(Long id) {
        log.debug("Request to get MRequisition : {}", id);
        return mRequisitionRepository.findById(id)
            .map(mRequisitionMapper::toDto);
    }

    /**
     * Delete the mRequisition by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MRequisition : {}", id);
        mRequisitionRepository.deleteById(id);
    }

    /**
     * TODO Use a generic method to update the document status for every entities.
     * TODO Use the workflow engine for maintaining the flow state.
     */
    public void updateDocumentStatus(MRequisitionDTO mRequisitionDTO) {
        log.debug("Request to update MRequisition's document status : {}", mRequisitionDTO);
        MRequisition mRequisition = mRequisitionMapper.toEntity(mRequisitionDTO);
        String action = mRequisition.getDocumentAction();
        String status = mRequisition.getDocumentStatus();
        System.out.println("========================================================================================= "+action + "-" + status);

        mRequisitionRepository.updateDocumentStatus(mRequisition.getId(), action, status);
        //userService.sendActivationEmail(mRequisition);
    }
}
