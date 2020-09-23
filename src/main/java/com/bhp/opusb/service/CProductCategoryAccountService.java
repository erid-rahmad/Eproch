package com.bhp.opusb.service;

import com.bhp.opusb.domain.CProductCategoryAccount;
import com.bhp.opusb.repository.CProductCategoryAccountRepository;
import com.bhp.opusb.service.dto.CProductCategoryAccountDTO;
import com.bhp.opusb.service.mapper.CProductCategoryAccountMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CProductCategoryAccount}.
 */
@Service
@Transactional
public class CProductCategoryAccountService {

    private final Logger log = LoggerFactory.getLogger(CProductCategoryAccountService.class);

    private final CProductCategoryAccountRepository cProductCategoryAccountRepository;

    private final CProductCategoryAccountMapper cProductCategoryAccountMapper;

    public CProductCategoryAccountService(CProductCategoryAccountRepository cProductCategoryAccountRepository, CProductCategoryAccountMapper cProductCategoryAccountMapper) {
        this.cProductCategoryAccountRepository = cProductCategoryAccountRepository;
        this.cProductCategoryAccountMapper = cProductCategoryAccountMapper;
    }

    /**
     * Save a cProductCategoryAccount.
     *
     * @param cProductCategoryAccountDTO the entity to save.
     * @return the persisted entity.
     */
    public CProductCategoryAccountDTO save(CProductCategoryAccountDTO cProductCategoryAccountDTO) {
        log.debug("Request to save CProductCategoryAccount : {}", cProductCategoryAccountDTO);
        CProductCategoryAccount cProductCategoryAccount = cProductCategoryAccountMapper.toEntity(cProductCategoryAccountDTO);
        cProductCategoryAccount = cProductCategoryAccountRepository.save(cProductCategoryAccount);
        return cProductCategoryAccountMapper.toDto(cProductCategoryAccount);
    }

    /**
     * Get all the cProductCategoryAccounts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CProductCategoryAccountDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CProductCategoryAccounts");
        return cProductCategoryAccountRepository.findAll(pageable)
            .map(cProductCategoryAccountMapper::toDto);
    }

    /**
     * Get one cProductCategoryAccount by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CProductCategoryAccountDTO> findOne(Long id) {
        log.debug("Request to get CProductCategoryAccount : {}", id);
        return cProductCategoryAccountRepository.findById(id)
            .map(cProductCategoryAccountMapper::toDto);
    }

    /**
     * Delete the cProductCategoryAccount by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CProductCategoryAccount : {}", id);
        cProductCategoryAccountRepository.deleteById(id);
    }
}
