package com.bhp.opusb.service;

import com.bhp.opusb.domain.ADClient;
import com.bhp.opusb.repository.ADClientRepository;
import com.bhp.opusb.service.dto.ADClientDTO;
import com.bhp.opusb.service.mapper.ADClientMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ADClient}.
 */
@Service
@Transactional
public class ADClientService {

    private final Logger log = LoggerFactory.getLogger(ADClientService.class);

    private final ADClientRepository aDClientRepository;

    private final ADClientMapper aDClientMapper;

    public ADClientService(ADClientRepository aDClientRepository, ADClientMapper aDClientMapper) {
        this.aDClientRepository = aDClientRepository;
        this.aDClientMapper = aDClientMapper;
    }

    /**
     * Save a aDClient.
     *
     * @param aDClientDTO the entity to save.
     * @return the persisted entity.
     */
    public ADClientDTO save(ADClientDTO aDClientDTO) {
        log.debug("Request to save ADClient : {}", aDClientDTO);
        ADClient aDClient = aDClientMapper.toEntity(aDClientDTO);
        aDClient = aDClientRepository.save(aDClient);
        return aDClientMapper.toDto(aDClient);
    }

    /**
     * Get all the aDClients.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ADClientDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ADClients");
        return aDClientRepository.findAll(pageable)
            .map(aDClientMapper::toDto);
    }

    /**
     * Get one aDClient by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ADClientDTO> findOne(Long id) {
        log.debug("Request to get ADClient : {}", id);
        return aDClientRepository.findById(id)
            .map(aDClientMapper::toDto);
    }

    /**
     * Delete the aDClient by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ADClient : {}", id);
        aDClientRepository.deleteById(id);
    }
}
