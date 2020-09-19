package com.bhp.opusb.service;

import java.util.Optional;

import com.bhp.opusb.domain.Authority;
import com.bhp.opusb.domain.ScAuthority;
import com.bhp.opusb.repository.AuthorityRepository;
import com.bhp.opusb.repository.ScAuthorityRepository;
import com.bhp.opusb.service.dto.ScAuthorityDTO;
import com.bhp.opusb.service.mapper.ScAuthorityMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ScAuthority}.
 */
@Service
@Transactional
public class ScAuthorityService {

    private final Logger log = LoggerFactory.getLogger(ScAuthorityService.class);

    private final ScAuthorityRepository scAuthorityRepository;

    private final AuthorityRepository authorityRepository;

    private final ScAuthorityMapper scAuthorityMapper;

    public ScAuthorityService(ScAuthorityRepository scAuthorityRepository, AuthorityRepository authorityRepository,
            ScAuthorityMapper scAuthorityMapper) {
        this.scAuthorityRepository = scAuthorityRepository;
        this.authorityRepository = authorityRepository;
        this.scAuthorityMapper = scAuthorityMapper;
    }

    /**
     * Save a scAuthority.
     *
     * @param scAuthorityDTO the entity to save.
     * @return the persisted entity.
     */
    public ScAuthorityDTO save(ScAuthorityDTO scAuthorityDTO) {
        log.debug("Request to save ScAuthority : {}", scAuthorityDTO);
        ScAuthority scAuthority = scAuthorityMapper.toEntity(scAuthorityDTO);
        
        if (scAuthority.getId() == null) {
            Authority authority = authorityRepository.save(scAuthority.getAuthority());
            scAuthority.setAuthority(authority);
        }

        scAuthority = scAuthorityRepository.save(scAuthority);
        return scAuthorityMapper.toDto(scAuthority);
    }

    /**
     * Get all the scAuthorities.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ScAuthorityDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ScAuthorities");
        return scAuthorityRepository.findAll(pageable)
            .map(scAuthorityMapper::toDto);
    }

    /**
     * Get one scAuthority by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ScAuthorityDTO> findOne(Long id) {
        log.debug("Request to get ScAuthority : {}", id);
        return scAuthorityRepository.findById(id)
            .map(scAuthorityMapper::toDto);
    }

    /**
     * Delete the scAuthority by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ScAuthority : {}", id);
        scAuthorityRepository.deleteById(id);
    }
}
