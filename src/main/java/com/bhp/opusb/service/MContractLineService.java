package com.bhp.opusb.service;

import com.bhp.opusb.domain.MContractLine;
import com.bhp.opusb.repository.MContractLineRepository;
import com.bhp.opusb.service.dto.MContractLineDTO;
import com.bhp.opusb.service.mapper.MContractLineMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MContractLine}.
 */
@Service
@Transactional
public class MContractLineService {

    private final Logger log = LoggerFactory.getLogger(MContractLineService.class);

    private final MContractLineRepository mContractLineRepository;

    private final MContractLineMapper mContractLineMapper;

    public MContractLineService(MContractLineRepository mContractLineRepository, MContractLineMapper mContractLineMapper) {
        this.mContractLineRepository = mContractLineRepository;
        this.mContractLineMapper = mContractLineMapper;
    }

    /**
     * Save a mContractLine.
     *
     * @param mContractLineDTO the entity to save.
     * @return the persisted entity.
     */
    public MContractLineDTO save(MContractLineDTO mContractLineDTO) {
        log.debug("Request to save MContractLine : {}", mContractLineDTO);
        MContractLine mContractLine = mContractLineMapper.toEntity(mContractLineDTO);
        mContractLine = mContractLineRepository.save(mContractLine);
        return mContractLineMapper.toDto(mContractLine);
    }

    /**
     * Get all the mContractLines.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MContractLineDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MContractLines");
        return mContractLineRepository.findAll(pageable)
            .map(mContractLineMapper::toDto);
    }

    /**
     * Get one mContractLine by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MContractLineDTO> findOne(Long id) {
        log.debug("Request to get MContractLine : {}", id);
        return mContractLineRepository.findById(id)
            .map(mContractLineMapper::toDto);
    }

    /**
     * Delete the mContractLine by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MContractLine : {}", id);
        mContractLineRepository.deleteById(id);
    }
}
