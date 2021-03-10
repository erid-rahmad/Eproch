package com.bhp.opusb.service;

import com.bhp.opusb.domain.AiExchangeOut;
import com.bhp.opusb.repository.AiExchangeOutRepository;
import com.bhp.opusb.service.dto.AiExchangeOutDTO;
import com.bhp.opusb.service.mapper.AiExchangeOutMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AiExchangeOut}.
 */
@Service
@Transactional
public class AiExchangeOutService {

    private final Logger log = LoggerFactory.getLogger(AiExchangeOutService.class);

    private final AiExchangeOutRepository aiExchangeOutRepository;

    private final AiExchangeOutMapper aiExchangeOutMapper;

    public AiExchangeOutService(AiExchangeOutRepository aiExchangeOutRepository, AiExchangeOutMapper aiExchangeOutMapper) {
        this.aiExchangeOutRepository = aiExchangeOutRepository;
        this.aiExchangeOutMapper = aiExchangeOutMapper;
    }

    /**
     * Save a aiExchangeOut.
     *
     * @param aiExchangeOutDTO the entity to save.
     * @return the persisted entity.
     */
    public AiExchangeOutDTO save(AiExchangeOutDTO aiExchangeOutDTO) {
        log.debug("Request to save AiExchangeOut : {}", aiExchangeOutDTO);
        AiExchangeOut aiExchangeOut = aiExchangeOutMapper.toEntity(aiExchangeOutDTO);
        aiExchangeOut = aiExchangeOutRepository.save(aiExchangeOut);
        return aiExchangeOutMapper.toDto(aiExchangeOut);
    }

    /**
     * Get all the aiExchangeOuts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AiExchangeOutDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AiExchangeOuts");
        return aiExchangeOutRepository.findAll(pageable)
            .map(aiExchangeOutMapper::toDto);
    }

    /**
     * Get one aiExchangeOut by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AiExchangeOutDTO> findOne(Long id) {
        log.debug("Request to get AiExchangeOut : {}", id);
        return aiExchangeOutRepository.findById(id)
            .map(aiExchangeOutMapper::toDto);
    }

    /**
     * Delete the aiExchangeOut by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AiExchangeOut : {}", id);
        aiExchangeOutRepository.deleteById(id);
    }
}
