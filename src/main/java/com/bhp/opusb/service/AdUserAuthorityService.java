package com.bhp.opusb.service;

import java.util.Objects;
import java.util.Optional;

import com.bhp.opusb.domain.AdUserAuthority;
import com.bhp.opusb.domain.Authority;
import com.bhp.opusb.domain.ScAuthority;
import com.bhp.opusb.domain.User;
import com.bhp.opusb.repository.AdUserAuthorityRepository;
import com.bhp.opusb.repository.AdUserRepository;
import com.bhp.opusb.repository.ScAuthorityRepository;
import com.bhp.opusb.repository.UserRepository;
import com.bhp.opusb.service.dto.AdUserAuthorityDTO;
import com.bhp.opusb.service.mapper.AdUserAuthorityMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AdUserAuthority}.
 */
@Service
@Transactional
public class AdUserAuthorityService {

    private final Logger log = LoggerFactory.getLogger(AdUserAuthorityService.class);

    private final AdUserAuthorityRepository adUserAuthorityRepository;

    private final AdUserRepository adUserRepository;

    private final ScAuthorityRepository scAuthorityRepository;

    private final AdUserAuthorityMapper adUserAuthorityMapper;

    private final CacheManager cacheManager;

    public AdUserAuthorityService(AdUserAuthorityRepository adUserAuthorityRepository,
            AdUserAuthorityMapper adUserAuthorityMapper, AdUserRepository adUserRepository,
            ScAuthorityRepository scAuthorityRepository, CacheManager cacheManager) {
        this.adUserAuthorityRepository = adUserAuthorityRepository;
        this.adUserRepository = adUserRepository;
        this.scAuthorityRepository = scAuthorityRepository;
        this.adUserAuthorityMapper = adUserAuthorityMapper;
        this.cacheManager = cacheManager;
    }

    /**
     * Save a adUserAuthority.
     *
     * @param adUserAuthorityDTO the entity to save.
     * @return the persisted entity.
     */
    public AdUserAuthorityDTO save(AdUserAuthorityDTO adUserAuthorityDTO) {
        log.debug("Request to save AdUserAuthority : {}", adUserAuthorityDTO);
        AdUserAuthority adUserAuthority = adUserAuthorityMapper.toEntity(adUserAuthorityDTO);
        adUserAuthority = adUserAuthorityRepository.save(adUserAuthority);

        Optional<ScAuthority> authorityRecord = scAuthorityRepository.findById(adUserAuthority.getAuthority().getId());

        // Update jhi_user_authority table.
        adUserRepository.findById(adUserAuthority.getUser().getId())
            .ifPresent(user -> {
                if (authorityRecord.isPresent()) {
                    user.getUser().getAuthorities().add(authorityRecord.get().getAuthority());
                    clearUserCaches(user.getUser());
                }
            });

        return adUserAuthorityMapper.toDto(adUserAuthority);
    }

    /**
     * Get all the adUserAuthorities.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AdUserAuthorityDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AdUserAuthorities");
        return adUserAuthorityRepository.findAll(pageable)
            .map(adUserAuthorityMapper::toDto);
    }

    /**
     * Get one adUserAuthority by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AdUserAuthorityDTO> findOne(Long id) {
        log.debug("Request to get AdUserAuthority : {}", id);
        return adUserAuthorityRepository.findById(id)
            .map(adUserAuthorityMapper::toDto);
    }

    /**
     * Delete the adUserAuthority by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AdUserAuthority : {}", id);
        adUserAuthorityRepository.findById(id)
            .ifPresent(userAuthority -> {
                Authority authority = userAuthority.getAuthority().getAuthority();
                User user = userAuthority.getUser().getUser();
                user.getAuthorities().remove(authority);
                adUserAuthorityRepository.delete(userAuthority);
                clearUserCaches(user);
            });
    }
    
    private void clearUserCaches(User user) {
        Objects.requireNonNull(cacheManager.getCache(UserRepository.USERS_BY_LOGIN_CACHE)).evict(user.getLogin());
        if (user.getEmail() != null) {
            Objects.requireNonNull(cacheManager.getCache(UserRepository.USERS_BY_EMAIL_CACHE)).evict(user.getEmail());
        }
    }
}
