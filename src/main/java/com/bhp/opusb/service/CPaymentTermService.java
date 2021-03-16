package com.bhp.opusb.service;

import com.bhp.opusb.domain.CPaymentTerm;
import com.bhp.opusb.repository.CPaymentTermRepository;
import com.bhp.opusb.service.dto.CPaymentTermDTO;
import com.bhp.opusb.service.mapper.CPaymentTermMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CPaymentTerm}.
 */
@Service
@Transactional
public class CPaymentTermService {

    private final Logger log = LoggerFactory.getLogger(CPaymentTermService.class);

    private final CPaymentTermRepository cPaymentTermRepository;

    private final CPaymentTermMapper cPaymentTermMapper;

    public CPaymentTermService(CPaymentTermRepository cPaymentTermRepository, CPaymentTermMapper cPaymentTermMapper) {
        this.cPaymentTermRepository = cPaymentTermRepository;
        this.cPaymentTermMapper = cPaymentTermMapper;
    }

    /**
     * Save a cPaymentTerm.
     *
     * @param cPaymentTermDTO the entity to save.
     * @return the persisted entity.
     */
    public CPaymentTermDTO save(CPaymentTermDTO cPaymentTermDTO) {
        log.debug("Request to save CPaymentTerm : {}", cPaymentTermDTO);
        CPaymentTerm cPaymentTerm = cPaymentTermMapper.toEntity(cPaymentTermDTO);
        cPaymentTerm = cPaymentTermRepository.save(cPaymentTerm);
        return cPaymentTermMapper.toDto(cPaymentTerm);
    }

    /**
     * Get all the cPaymentTerms.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CPaymentTermDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CPaymentTerms");
        return cPaymentTermRepository.findAll(pageable)
            .map(cPaymentTermMapper::toDto);
    }

    /**
     * Get one cPaymentTerm by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CPaymentTermDTO> findOne(Long id) {
        log.debug("Request to get CPaymentTerm : {}", id);
        return cPaymentTermRepository.findById(id)
            .map(cPaymentTermMapper::toDto);
    }

    /**
     * Delete the cPaymentTerm by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CPaymentTerm : {}", id);
        cPaymentTermRepository.deleteById(id);
    }
}
