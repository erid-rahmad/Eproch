package com.bhp.opusb.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.bhp.opusb.config.Constants;
import com.bhp.opusb.domain.AdUser;
import com.bhp.opusb.domain.AdUserAuthority;
import com.bhp.opusb.domain.User;
import com.bhp.opusb.repository.AdUserAuthorityRepository;
import com.bhp.opusb.repository.AdUserRepository;
import com.bhp.opusb.repository.ScAuthorityRepository;
import com.bhp.opusb.repository.UserRepository;
import com.bhp.opusb.security.AuthoritiesConstants;
import com.bhp.opusb.security.SecurityUtils;
import com.bhp.opusb.service.dto.AdUserDTO;
import com.bhp.opusb.service.mapper.AdUserMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.security.RandomUtil;

/**
 * Service Implementation for managing {@link AdUser}.
 */
@Service
@Transactional
public class AdUserService {

    private final Logger log = LoggerFactory.getLogger(AdUserService.class);

    private final AdUserRepository adUserRepository;
    private final UserRepository userRepository;
    private final AdUserMapper adUserMapper;
    private final AdUserAuthorityRepository adUserAuthorityRepository;
    private final ScAuthorityRepository scAuthorityRepository;
    private final PasswordEncoder passwordEncoder;

    private final CacheManager cacheManager;

    public AdUserService(AdUserRepository adUserRepository, UserRepository userRepository, AdUserMapper adUserMapper,
            AdUserAuthorityRepository adUserAuthorityRepository, ScAuthorityRepository scAuthorityRepository,
            PasswordEncoder passwordEncoder, CacheManager cacheManager) {
        this.adUserRepository = adUserRepository;
        this.userRepository = userRepository;
        this.adUserMapper = adUserMapper;
        this.adUserAuthorityRepository = adUserAuthorityRepository;
        this.scAuthorityRepository = scAuthorityRepository;
        this.passwordEncoder = passwordEncoder;
        this.cacheManager = cacheManager;
    }

    /**
     * Save a adUser.
     *
     * @param adUserDTO the entity to save.
     * @return the persisted entity.
     */
    public AdUserDTO save(AdUserDTO adUserDTO) {
        log.debug("Request to save AdUser : {}", adUserDTO);
        final AdUser adUser = adUserMapper.toEntity(adUserDTO);

        if (adUserDTO.getId() == null) {
            User user = new User();
            user.setEmail(adUserDTO.getEmail());
            user.setLogin(adUserDTO.getUserLogin());
            user.setFirstName(adUserDTO.getFirstName());
            user.setLastName(adUserDTO.getLastName());
            user.setLangKey(Constants.DEFAULT_LANGUAGE); // default language

            // Any user that is created from the admin page doesn't need email verification.
            user.setActivated(true);

            String generatedPassword = SecurityUtils.generatePassword();
            String encryptedPassword = passwordEncoder.encode(generatedPassword);
            user.setPassword(encryptedPassword);
            user.setResetKey(RandomUtil.generateResetKey());
            user.setResetDate(Instant.now());
            userRepository.save(user);
            log.debug("User saved {}", user);

            adUser.setUser(user);
            if (adUser.getCVendor() != null) {
                adUser.vendor(true);
            }
        }
        
        adUserRepository.save(adUser);
        adUser.getUser().setEmail(adUserDTO.getEmail());

        // Add a USER authority for all authenticated users.
        List<String> authNames = new ArrayList<>(Arrays.asList(AuthoritiesConstants.USER));

        // Add a SUPPLIER authority if he/she is a vendor.
        if (Boolean.TRUE.equals(adUser.isVendor())) {
            authNames.add(AuthoritiesConstants.SUPPLIER);
        }

        scAuthorityRepository.findByAuthorityNameIn(authNames)
            .forEach(scAuthority -> {
                AdUserAuthority userAuthority = new AdUserAuthority().active(true)
                    .adOrganization(adUser.getAdOrganization())
                    .authority(scAuthority)
                    .user(adUser);

                adUserAuthorityRepository.save(userAuthority);

                // Add ROLE_SUPPLIER to jhi_user_authority.
                if (! adUser.getUser().getAuthorities().contains(scAuthority.getAuthority())) {
                    adUser.getUser().getAuthorities().add(scAuthority.getAuthority());
                }
            });

        clearUserCaches(adUser.getUser());
        return adUserMapper.toDto(adUser);
    }

    /**
     * Get all the adUsers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AdUserDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AdUsers");
        return adUserRepository.findAll(pageable)
            .map(adUserMapper::toDto);
    }

    /**
     * Get one adUser by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AdUserDTO> findOne(Long id) {
        log.debug("Request to get AdUser : {}", id);
        return adUserRepository.findById(id)
            .map(adUserMapper::toDto);
    }

    /**
     * Delete the adUser by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AdUser : {}", id);
        adUserRepository.findById(id).ifPresent(adUser -> {
            User user = adUser.getUser();
            adUserRepository.delete(adUser);
            userRepository.delete(user);
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
