package com.bhp.opusb.service;

import com.bhp.opusb.domain.MAuctionContent;
import com.bhp.opusb.repository.MAuctionContentRepository;
import com.bhp.opusb.service.dto.MAuctionContentDTO;
import com.bhp.opusb.service.mapper.MAuctionContentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MAuctionContent}.
 */
@Service
@Transactional
public class MAuctionContentService {

    private final Logger log = LoggerFactory.getLogger(MAuctionContentService.class);

    private final MAuctionContentRepository mAuctionContentRepository;

    private final MAuctionContentMapper mAuctionContentMapper;

    public MAuctionContentService(MAuctionContentRepository mAuctionContentRepository, MAuctionContentMapper mAuctionContentMapper) {
        this.mAuctionContentRepository = mAuctionContentRepository;
        this.mAuctionContentMapper = mAuctionContentMapper;
    }

    /**
     * Save a mAuctionContent.
     *
     * @param mAuctionContentDTO the entity to save.
     * @return the persisted entity.
     */
    public MAuctionContentDTO save(MAuctionContentDTO mAuctionContentDTO) {
        log.debug("Request to save MAuctionContent : {}", mAuctionContentDTO);
        MAuctionContent mAuctionContent = mAuctionContentMapper.toEntity(mAuctionContentDTO);
        mAuctionContent = mAuctionContentRepository.save(mAuctionContent);
        return mAuctionContentMapper.toDto(mAuctionContent);
    }

    /**
     * Get all the mAuctionContents.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MAuctionContentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MAuctionContents");
        return mAuctionContentRepository.findAll(pageable)
            .map(mAuctionContentMapper::toDto);
    }

    /**
     * Get one mAuctionContent by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MAuctionContentDTO> findOne(Long id) {
        log.debug("Request to get MAuctionContent : {}", id);
        return mAuctionContentRepository.findById(id)
            .map(mAuctionContentMapper::toDto);
    }

    /**
     * Delete the mAuctionContent by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MAuctionContent : {}", id);
        mAuctionContentRepository.deleteById(id);
    }
}
