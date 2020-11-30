package com.bhp.opusb.service;

import com.bhp.opusb.domain.AiExchangeIn;
import com.bhp.opusb.repository.AiExchangeInRepository;
import com.bhp.opusb.service.dto.AiExchangeInDTO;
import com.bhp.opusb.service.mapper.AiExchangeInMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AiExchangeIn}.
 */
@Service
@Transactional
public class AiExchangeInService {

    private final Logger log = LoggerFactory.getLogger(AiExchangeInService.class);

    private final AiExchangeInRepository aiExchangeInRepository;

    private final AiExchangeInMapper aiExchangeInMapper;

    public AiExchangeInService(AiExchangeInRepository aiExchangeInRepository, AiExchangeInMapper aiExchangeInMapper) {
        this.aiExchangeInRepository = aiExchangeInRepository;
        this.aiExchangeInMapper = aiExchangeInMapper;
    }

    /**
     * Save a aiExchangeIn.
     *
     * @param aiExchangeInDTO the entity to save.
     * @return the persisted entity.
     */
    @Async
    public AiExchangeInDTO save(AiExchangeInDTO aiExchangeInDTO) {
        log.debug("Request to save AiExchangeIn : {}", aiExchangeInDTO);
        AiExchangeIn aiExchangeIn = aiExchangeInMapper.toEntity(aiExchangeInDTO);
        aiExchangeIn = aiExchangeInRepository.save(aiExchangeIn);
        return aiExchangeInMapper.toDto(aiExchangeIn);
    }

    /**
     * Get all the aiExchangeIns.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AiExchangeInDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AiExchangeIns");
        return aiExchangeInRepository.findAll(pageable)
            .map(aiExchangeInMapper::toDto);
    }

    /**
     * Get one aiExchangeIn by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AiExchangeInDTO> findOne(Long id) {
        log.debug("Request to get AiExchangeIn : {}", id);
        return aiExchangeInRepository.findById(id)
            .map(aiExchangeInMapper::toDto);
    }

    /**
     * Delete the aiExchangeIn by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AiExchangeIn : {}", id);
        aiExchangeInRepository.deleteById(id);
    }
}
