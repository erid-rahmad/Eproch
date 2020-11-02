package com.bhp.opusb.service;

import com.bhp.opusb.domain.CBank;
import com.bhp.opusb.repository.CBankRepository;
import com.bhp.opusb.service.dto.CBankDTO;
import com.bhp.opusb.service.mapper.CBankMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CBank}.
 */
@Service
@Transactional
public class CBankService {

    private final Logger log = LoggerFactory.getLogger(CBankService.class);

    private final CBankRepository cBankRepository;

    private final CBankMapper cBankMapper;

    public CBankService(CBankRepository cBankRepository, CBankMapper cBankMapper) {
        this.cBankRepository = cBankRepository;
        this.cBankMapper = cBankMapper;
    }

    /**
     * Save a cBank.
     *
     * @param cBankDTO the entity to save.
     * @return the persisted entity.
     */
    public CBankDTO save(CBankDTO cBankDTO) {
        log.debug("Request to save CBank : {}", cBankDTO);
        CBank cBank = cBankMapper.toEntity(cBankDTO);
        cBank = cBankRepository.save(cBank);
        return cBankMapper.toDto(cBank);
    }

    /**
     * Get all the cBanks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CBankDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CBanks");
        return cBankRepository.findAll(pageable)
            .map(cBankMapper::toDto);
    }

    /**
     * Get one cBank by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CBankDTO> findOne(Long id) {
        log.debug("Request to get CBank : {}", id);
        return cBankRepository.findById(id)
            .map(cBankMapper::toDto);
    }

    /**
     * Delete the cBank by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CBank : {}", id);
        cBankRepository.deleteById(id);
    }
}
