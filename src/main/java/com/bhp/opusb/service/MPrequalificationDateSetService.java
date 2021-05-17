package com.bhp.opusb.service;

import com.bhp.opusb.domain.MBidding;
import com.bhp.opusb.domain.MPrequalificationDateSet;
import com.bhp.opusb.domain.view.MinMaxView;
import com.bhp.opusb.repository.CEventTypelineRepository;
import com.bhp.opusb.repository.MBiddingRepository;
import com.bhp.opusb.repository.MPrequalificationDateSetRepository;
import com.bhp.opusb.service.dto.MPrequalificationDateSetDTO;
import com.bhp.opusb.service.mapper.MPrequalificationDateSetMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

/**
 * Service Implementation for managing {@link MPrequalificationDateSet}.
 */
@Service
@Transactional
public class MPrequalificationDateSetService {

    private final Logger log = LoggerFactory.getLogger(MPrequalificationDateSetService.class);

    private final CEventTypelineRepository cEventTypelineRepository;

    private final MPrequalificationDateSetRepository mPrequalificationDateSetRepository;
    private final MBiddingRepository mBiddingRepository;

    private final MPrequalificationDateSetMapper mPrequalificationDateSetMapper;

    public MPrequalificationDateSetService(CEventTypelineRepository cEventTypelineRepository,
            MPrequalificationDateSetRepository mPrequalificationDateSetRepository,
            MBiddingRepository mBiddingRepository, MPrequalificationDateSetMapper mPrequalificationDateSetMapper) {
        this.cEventTypelineRepository = cEventTypelineRepository;
        this.mPrequalificationDateSetRepository = mPrequalificationDateSetRepository;
        this.mBiddingRepository = mBiddingRepository;
        this.mPrequalificationDateSetMapper = mPrequalificationDateSetMapper;
    }

    /**
     * Save a mPrequalificationDateSet.
     *
     * @param mPrequalificationDateSetDTO the entity to save.
     * @return the persisted entity.
     */
    public MPrequalificationDateSetDTO save(MPrequalificationDateSetDTO mPrequalificationDateSetDTO) {
        log.debug("Request to save MPrequalificationDateSet : {}", mPrequalificationDateSetDTO);
        MPrequalificationDateSet mPrequalificationDateSet = mPrequalificationDateSetMapper.toEntity(mPrequalificationDateSetDTO);
        mPrequalificationDateSet = mPrequalificationDateSetRepository.save(mPrequalificationDateSet);

        String status = mPrequalificationDateSetDTO.getStatus();
        MBidding mBidding = mBiddingRepository.findFirstByBiddingScheduleId(mPrequalificationDateSetDTO.getBiddingScheduleId());
        MinMaxView sequences = cEventTypelineRepository.findMinMaxSequence(mBidding.getEventType().getId());
        Integer currentSequence = mPrequalificationDateSetDTO.getSequence();

        if (Objects.equals(currentSequence, sequences.getMin()) && status.equals("P")) {
            mBidding.setBiddingStatus("P");
        } else if (Objects.equals(currentSequence, sequences.getMax()) && status.equals("F")) {
            mBidding.setBiddingStatus("F");
        }

        return mPrequalificationDateSetMapper.toDto(mPrequalificationDateSet);
    }

    /**
     * Get all the mPrequalificationDateSets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MPrequalificationDateSetDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MPrequalificationDateSets");
        return mPrequalificationDateSetRepository.findAll(pageable)
            .map(mPrequalificationDateSetMapper::toDto);
    }

    /**
     * Get one mPrequalificationDateSet by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MPrequalificationDateSetDTO> findOne(Long id) {
        log.debug("Request to get MPrequalificationDateSet : {}", id);
        return mPrequalificationDateSetRepository.findById(id)
            .map(mPrequalificationDateSetMapper::toDto);
    }

    /**
     * Delete the mPrequalificationDateSet by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MPrequalificationDateSet : {}", id);
        mPrequalificationDateSetRepository.deleteById(id);
    }
}
