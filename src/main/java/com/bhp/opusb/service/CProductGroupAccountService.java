package com.bhp.opusb.service;

import com.bhp.opusb.domain.CProductGroupAccount;
import com.bhp.opusb.repository.CProductGroupAccountRepository;
import com.bhp.opusb.service.dto.CProductGroupAccountDTO;
import com.bhp.opusb.service.mapper.CProductGroupAccountMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CProductGroupAccount}.
 */
@Service
@Transactional
public class CProductGroupAccountService {

    private final Logger log = LoggerFactory.getLogger(CProductGroupAccountService.class);

    private final CProductGroupAccountRepository cProductGroupAccountRepository;

    private final CProductGroupAccountMapper cProductGroupAccountMapper;

    public CProductGroupAccountService(CProductGroupAccountRepository cProductGroupAccountRepository, CProductGroupAccountMapper cProductGroupAccountMapper) {
        this.cProductGroupAccountRepository = cProductGroupAccountRepository;
        this.cProductGroupAccountMapper = cProductGroupAccountMapper;
    }

    /**
     * Save a cProductGroupAccount.
     *
     * @param cProductGroupAccountDTO the entity to save.
     * @return the persisted entity.
     */
    public CProductGroupAccountDTO save(CProductGroupAccountDTO cProductGroupAccountDTO) {
        log.debug("Request to save CProductGroupAccount : {}", cProductGroupAccountDTO);
        CProductGroupAccount cProductGroupAccount = cProductGroupAccountMapper.toEntity(cProductGroupAccountDTO);
        cProductGroupAccount = cProductGroupAccountRepository.save(cProductGroupAccount);
        return cProductGroupAccountMapper.toDto(cProductGroupAccount);
    }

    /**
     * Get all the cProductGroupAccounts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CProductGroupAccountDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CProductGroupAccounts");
        return cProductGroupAccountRepository.findAll(pageable)
            .map(cProductGroupAccountMapper::toDto);
    }

    /**
     * Get one cProductGroupAccount by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CProductGroupAccountDTO> findOne(Long id) {
        log.debug("Request to get CProductGroupAccount : {}", id);
        return cProductGroupAccountRepository.findById(id)
            .map(cProductGroupAccountMapper::toDto);
    }

    /**
     * Delete the cProductGroupAccount by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CProductGroupAccount : {}", id);
        cProductGroupAccountRepository.deleteById(id);
    }
}
