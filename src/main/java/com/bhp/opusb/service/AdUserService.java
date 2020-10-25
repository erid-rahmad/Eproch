package com.bhp.opusb.service;

import java.time.Instant;
import java.util.Optional;

import com.bhp.opusb.config.Constants;
import com.bhp.opusb.domain.AdUser;
import com.bhp.opusb.domain.User;
import com.bhp.opusb.repository.AdUserRepository;
import com.bhp.opusb.repository.UserRepository;
import com.bhp.opusb.security.SecurityUtils;
import com.bhp.opusb.service.dto.AdUserDTO;
import com.bhp.opusb.service.mapper.AdUserMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final PasswordEncoder passwordEncoder;

    public AdUserService(AdUserRepository adUserRepository, UserRepository userRepository, AdUserMapper adUserMapper, PasswordEncoder passwordEncoder) {
        this.adUserRepository = adUserRepository;
        this.userRepository = userRepository;
        this.adUserMapper = adUserMapper;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Save a adUser.
     *
     * @param adUserDTO the entity to save.
     * @return the persisted entity.
     */
    public AdUserDTO save(AdUserDTO adUserDTO) {
        log.debug("Request to save AdUser : {}", adUserDTO);
        AdUser adUser = adUserMapper.toEntity(adUserDTO);
        
        if (adUserDTO.getId() == null) {
            User user = new User();
            user.setEmail(adUserDTO.getEmail());
            user.setLogin(adUserDTO.getUserLogin());
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
        }
        
        adUser = adUserRepository.save(adUser);
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
        });
    }
}
