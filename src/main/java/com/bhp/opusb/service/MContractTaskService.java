package com.bhp.opusb.service;

import com.bhp.opusb.domain.MContractTask;
import com.bhp.opusb.repository.MContractTaskRepository;
import com.bhp.opusb.service.dto.MContractTaskDTO;
import com.bhp.opusb.service.dto.MContractTaskSaveDTO;
import com.bhp.opusb.service.mapper.MContractTaskMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MContractTask}.
 */
@Service
@Transactional
public class MContractTaskService {

    private final Logger log = LoggerFactory.getLogger(MContractTaskService.class);

    private final MContractTaskRepository mContractTaskRepository;

    private final MContractTaskMapper mContractTaskMapper;
    private final MContractTaskReviewersService mContractTaskReviewersService;
    private final MContractTaskNegotiationService mContractTaskNegotiationService;

    public MContractTaskService(MContractTaskRepository mContractTaskRepository, MContractTaskMapper mContractTaskMapper, MContractTaskReviewersService mContractTaskReviewersService, MContractTaskNegotiationService mContractTaskNegotiationService) {
        this.mContractTaskRepository = mContractTaskRepository;
        this.mContractTaskMapper = mContractTaskMapper;
        this.mContractTaskReviewersService = mContractTaskReviewersService;
        this.mContractTaskNegotiationService = mContractTaskNegotiationService;
    }

    /**
     * Save a mContractTask.
     *
     * @param mContractTaskDTO the entity to save.
     * @return the persisted entity.
     */
    public MContractTaskDTO save(MContractTaskDTO mContractTaskDTO) {
        log.debug("Request to save MContractTask : {}", mContractTaskDTO);
        MContractTask mContractTask = mContractTaskMapper.toEntity(mContractTaskDTO);
        mContractTask = mContractTaskRepository.save(mContractTask);
        return mContractTaskMapper.toDto(mContractTask);
    }

    /**
     * Save a mContractTask.
     *
     * @return the persisted entity.
     */
    public MContractTaskDTO save(MContractTaskSaveDTO contractTaskSaveDTO) {
        log.debug("Request to save MContractTaskSaveDTO : {}", contractTaskSaveDTO);
        MContractTask mContractTask = mContractTaskRepository.save(mContractTaskMapper.toEntity(contractTaskSaveDTO.getContractTask()));
        contractTaskSaveDTO.getReviewers().forEach(mContractTaskReviewersDTO -> {
            mContractTaskReviewersDTO.setContractTaskId(mContractTask.getId());
            mContractTaskReviewersService.save(mContractTaskReviewersDTO);
        });
        contractTaskSaveDTO.getMassage().forEach(mContractTaskNegotiationDTO -> {
            mContractTaskNegotiationDTO.setContractTaskId(mContractTask.getId());
            mContractTaskNegotiationService.save(mContractTaskNegotiationDTO);
        });
        return mContractTaskMapper.toDto(mContractTask);
    }

    /**
     * Get all the mContractTasks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MContractTaskDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MContractTasks");
        return mContractTaskRepository.findAll(pageable)
            .map(mContractTaskMapper::toDto);
    }

    /**
     * Get one mContractTask by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MContractTaskDTO> findOne(Long id) {
        log.debug("Request to get MContractTask : {}", id);
        return mContractTaskRepository.findById(id)
            .map(mContractTaskMapper::toDto);
    }

    /**
     * Delete the mContractTask by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MContractTask : {}", id);
        mContractTaskRepository.deleteById(id);
    }
}
