package com.bhp.opusb.service;

import com.bhp.opusb.domain.MContract;
import com.bhp.opusb.repository.MContractRepository;
import com.bhp.opusb.service.dto.MContractDTO;
import com.bhp.opusb.service.mapper.MContractMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MContract}.
 */
@Service
@Transactional
public class MContractService {

    private final Logger log = LoggerFactory.getLogger(MContractService.class);

    private final MContractRepository mContractRepository;

    private final MContractMapper mContractMapper;

    public MContractService(MContractRepository mContractRepository, MContractMapper mContractMapper) {
        this.mContractRepository = mContractRepository;
        this.mContractMapper = mContractMapper;
    }

    /**
     * Save a mContract.
     *
     * @param mContractDTO the entity to save.
     * @return the persisted entity.
     */
    public MContractDTO save(MContractDTO mContractDTO) {
        log.debug("Request to save MContract : {}", mContractDTO);
        MContract mContract = mContractMapper.toEntity(mContractDTO);
        mContract = mContractRepository.save(mContract);
        return mContractMapper.toDto(mContract);
    }

    /**
     * Get all the mContracts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MContractDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MContracts");
        return mContractRepository.findAll(pageable)
            .map(mContractMapper::toDto);
    }

    /**
     * Get one mContract by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MContractDTO> findOne(Long id) {
        log.debug("Request to get MContract : {}", id);
        return mContractRepository.findById(id)
            .map(mContractMapper::toDto);
    }

    /**
     * Delete the mContract by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MContract : {}", id);
        mContractRepository.deleteById(id);
    }
}
