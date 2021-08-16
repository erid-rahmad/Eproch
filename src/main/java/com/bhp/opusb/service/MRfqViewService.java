package com.bhp.opusb.service;

import com.bhp.opusb.domain.MRfqView;
import com.bhp.opusb.repository.MRfqViewRepository;
import com.bhp.opusb.service.dto.MRfqViewDTO;
import com.bhp.opusb.service.mapper.MRfqViewMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MRfqView}.
 */
@Service
@Transactional
public class MRfqViewService {

    private final Logger log = LoggerFactory.getLogger(MRfqViewService.class);

    private final MRfqViewRepository mRfqViewRepository;

    private final MRfqViewMapper mRfqViewMapper;

    public MRfqViewService(MRfqViewRepository mRfqViewRepository, MRfqViewMapper mRfqViewMapper) {
        this.mRfqViewRepository = mRfqViewRepository;
        this.mRfqViewMapper = mRfqViewMapper;
    }

    /**
     * Save a mRfqView.
     *
     * @param mRfqViewDTO the entity to save.
     * @return the persisted entity.
     */
    public MRfqViewDTO save(MRfqViewDTO mRfqViewDTO) {
        log.debug("Request to save MRfqView : {}", mRfqViewDTO);
        MRfqView mRfqView = mRfqViewMapper.toEntity(mRfqViewDTO);
        mRfqView = mRfqViewRepository.save(mRfqView);
        return mRfqViewMapper.toDto(mRfqView);
    }

    /**
     * Get all the mRfqViews.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MRfqViewDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MRfqViews");
        return mRfqViewRepository.findAll(pageable)
            .map(mRfqViewMapper::toDto);
    }

    /**
     * Get one mRfqView by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MRfqViewDTO> findOne(Long id) {
        log.debug("Request to get MRfqView : {}", id);
        return mRfqViewRepository.findById(id)
            .map(mRfqViewMapper::toDto);
    }

    /**
     * Delete the mRfqView by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MRfqView : {}", id);
        mRfqViewRepository.deleteById(id);
    }
}
