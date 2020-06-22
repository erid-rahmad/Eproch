package com.bhp.opusb.service;

import com.bhp.opusb.domain.CCurrency;
import com.bhp.opusb.repository.CCurrencyRepository;
import com.bhp.opusb.service.dto.CCurrencyDTO;
import com.bhp.opusb.service.mapper.CCurrencyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CCurrency}.
 */
@Service
@Transactional
public class CCurrencyService {

    private final Logger log = LoggerFactory.getLogger(CCurrencyService.class);

    private final CCurrencyRepository cCurrencyRepository;

    private final CCurrencyMapper cCurrencyMapper;

    public CCurrencyService(CCurrencyRepository cCurrencyRepository, CCurrencyMapper cCurrencyMapper) {
        this.cCurrencyRepository = cCurrencyRepository;
        this.cCurrencyMapper = cCurrencyMapper;
    }

    /**
     * Save a cCurrency.
     *
     * @param cCurrencyDTO the entity to save.
     * @return the persisted entity.
     */
    public CCurrencyDTO save(CCurrencyDTO cCurrencyDTO) {
        log.debug("Request to save CCurrency : {}", cCurrencyDTO);
        CCurrency cCurrency = cCurrencyMapper.toEntity(cCurrencyDTO);
        cCurrency = cCurrencyRepository.save(cCurrency);
        return cCurrencyMapper.toDto(cCurrency);
    }

    /**
     * Get all the cCurrencies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CCurrencyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CCurrencies");
        return cCurrencyRepository.findAll(pageable)
            .map(cCurrencyMapper::toDto);
    }

    /**
     * Get one cCurrency by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CCurrencyDTO> findOne(Long id) {
        log.debug("Request to get CCurrency : {}", id);
        return cCurrencyRepository.findById(id)
            .map(cCurrencyMapper::toDto);
    }

    /**
     * Delete the cCurrency by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CCurrency : {}", id);
        cCurrencyRepository.deleteById(id);
    }
}
