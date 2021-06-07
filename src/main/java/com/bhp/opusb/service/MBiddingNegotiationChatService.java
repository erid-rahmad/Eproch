package com.bhp.opusb.service;

import com.bhp.opusb.domain.MBiddingNegotiation;
import com.bhp.opusb.domain.MBiddingNegotiationChat;
import com.bhp.opusb.domain.MBiddingNegotiationLine;
import com.bhp.opusb.repository.MBiddingNegotiationChatRepository;
import com.bhp.opusb.repository.MBiddingNegotiationLineRepository;
import com.bhp.opusb.repository.MBiddingNegotiationRepository;
import com.bhp.opusb.service.dto.MBiddingNegotiationChatDTO;
import com.bhp.opusb.service.mapper.MBiddingNegotiationChatMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MBiddingNegotiationChat}.
 */
@Service
@Transactional
public class MBiddingNegotiationChatService {

    private final Logger log = LoggerFactory.getLogger(MBiddingNegotiationChatService.class);

    private final MBiddingNegotiationChatRepository mBiddingNegotiationChatRepository;
    private final MBiddingNegotiationLineRepository mBiddingNegotiationLineRepository;
    private final MBiddingNegotiationRepository mBiddingNegotiationRepository;

    private final MBiddingNegotiationChatMapper mBiddingNegotiationChatMapper;

    public MBiddingNegotiationChatService(MBiddingNegotiationChatRepository mBiddingNegotiationChatRepository, 
    MBiddingNegotiationChatMapper mBiddingNegotiationChatMapper,
    MBiddingNegotiationLineRepository mBiddingNegotiationLineRepository,
    MBiddingNegotiationRepository mBiddingNegotiationRepository) {
        this.mBiddingNegotiationChatRepository = mBiddingNegotiationChatRepository;
        this.mBiddingNegotiationChatMapper = mBiddingNegotiationChatMapper;
        this.mBiddingNegotiationLineRepository = mBiddingNegotiationLineRepository;
        this.mBiddingNegotiationRepository = mBiddingNegotiationRepository;
    }

    /**
     * Save a mBiddingNegotiationChat.
     *
     * @param mBiddingNegotiationChatDTO the entity to save.
     * @return the persisted entity.
     */
    public MBiddingNegotiationChatDTO save(MBiddingNegotiationChatDTO mBiddingNegotiationChatDTO) {
        log.debug("Request to save MBiddingNegotiationChat : {}", mBiddingNegotiationChatDTO);

        MBiddingNegotiationChat mBiddingNegotiationChat = mBiddingNegotiationChatMapper.toEntity(mBiddingNegotiationChatDTO);
        mBiddingNegotiationChat = mBiddingNegotiationChatRepository.save(mBiddingNegotiationChat);

        MBiddingNegotiationLine mbnl = mBiddingNegotiationLineRepository.findById(mBiddingNegotiationChatDTO.getNegotiationLineId()).get();
        if("in progress".contentEquals(mbnl.getNegotiationStatus())){
            mbnl.setNegotiationStatus("in progress");
            mBiddingNegotiationLineRepository.save(mbnl);

            MBiddingNegotiation mbn = mBiddingNegotiationRepository.findById(mbnl.getNegotiation().getId()).get();
            mbn.setBiddingStatus("P");
            mBiddingNegotiationRepository.save(mbn);
        }
        
        return mBiddingNegotiationChatMapper.toDto(mBiddingNegotiationChat);
    }

    /**
     * Get all the mBiddingNegotiationChats.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MBiddingNegotiationChatDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MBiddingNegotiationChats");
        return mBiddingNegotiationChatRepository.findAll(pageable)
            .map(mBiddingNegotiationChatMapper::toDto);
    }

    /**
     * Get one mBiddingNegotiationChat by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MBiddingNegotiationChatDTO> findOne(Long id) {
        log.debug("Request to get MBiddingNegotiationChat : {}", id);
        return mBiddingNegotiationChatRepository.findById(id)
            .map(mBiddingNegotiationChatMapper::toDto);
    }

    /**
     * Delete the mBiddingNegotiationChat by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MBiddingNegotiationChat : {}", id);
        mBiddingNegotiationChatRepository.deleteById(id);
    }
}
