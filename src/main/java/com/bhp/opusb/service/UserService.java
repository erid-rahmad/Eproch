package com.bhp.opusb.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.bhp.opusb.config.Constants;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.AdUser;
import com.bhp.opusb.domain.AdUserAuthority;
import com.bhp.opusb.domain.Authority;
import com.bhp.opusb.domain.CBusinessCategory;
import com.bhp.opusb.domain.CPicBusinessCat;
import com.bhp.opusb.domain.CVendor;
import com.bhp.opusb.domain.User;
import com.bhp.opusb.repository.AdUserAuthorityRepository;
import com.bhp.opusb.repository.AdUserRepository;
import com.bhp.opusb.repository.AuthorityRepository;
import com.bhp.opusb.repository.CPicBusinessCatRepository;
import com.bhp.opusb.repository.ScAuthorityRepository;
import com.bhp.opusb.repository.UserRepository;
import com.bhp.opusb.security.AuthoritiesConstants;
import com.bhp.opusb.security.SecurityUtils;
import com.bhp.opusb.service.dto.AdUserDTO;
import com.bhp.opusb.service.dto.UserDTO;
import com.bhp.opusb.service.mapper.AdUserMapper;
import com.bhp.opusb.service.mapper.CBusinessCategoryMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.security.RandomUtil;
import io.vavr.collection.Stream;

/**
 * Service class for managing users.
 */
@Service
@Transactional
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthorityRepository authorityRepository;
    private final ScAuthorityRepository scAuthorityRepository;
    private final AdUserAuthorityRepository adUserAuthorityRepository;

    private final CacheManager cacheManager;

    private final AdUserRepository adUserRepository;
    private final AdUserMapper adUserMapper;
    private final CBusinessCategoryMapper cBusinessCategoryMapper;

    private final CPicBusinessCatRepository cPicBusinessCatRepository;
    private final MailService mailService;

    public UserService(UserRepository userRepository, AdUserRepository adUserRepository,
            PasswordEncoder passwordEncoder, AuthorityRepository authorityRepository, ScAuthorityRepository scAuthorityRepository,
            AdUserAuthorityRepository adUserAuthorityRepository, CacheManager cacheManager, AdUserMapper adUserMapper,
            CBusinessCategoryMapper cBusinessCategoryMapper, CPicBusinessCatRepository cPicBusinessCatRepository,
            MailService mailService) {

        this.userRepository = userRepository;
        this.adUserRepository = adUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityRepository = authorityRepository;
        this.scAuthorityRepository = scAuthorityRepository;
        this.adUserAuthorityRepository = adUserAuthorityRepository;
        this.cacheManager = cacheManager;
        this.adUserMapper = adUserMapper;
        this.cBusinessCategoryMapper = cBusinessCategoryMapper;
        this.cPicBusinessCatRepository = cPicBusinessCatRepository;
        this.mailService = mailService;
    }

    public void sendActivationEmail(CVendor vendor) {
        List<AdUser> users = adUserRepository.findBycVendor(vendor);
        Stream.ofAll(users).map(AdUser::getUser).forEach(mailService::sendActivationEmail);
    }

    public Optional<User> activateRegistration(String key) {
        log.debug("Activating user for activation key {}", key);
        return userRepository.findOneByActivationKey(key)
            .map(user -> {
                adUserRepository.findById(user.getId())
                    .ifPresent(adUser -> {
                        adUser.active(true);
                        scAuthorityRepository.findByAuthorityName(AuthoritiesConstants.SUPPLIER)
                            .ifPresent(scAuthority -> {
                                AdUserAuthority userAuthority = new AdUserAuthority().active(true)
                                    .adOrganization(adUser.getAdOrganization())
                                    .authority(scAuthority)
                                    .user(adUser);

                                adUserAuthorityRepository.save(userAuthority);

                                // Add ROLE_SUPPLIER to jhi_user_authority.
                                user.getAuthorities().add(scAuthority.getAuthority());
                            });
                    });

                log.debug("Activated user: {}", user);

                // activate given user for the registration key.
                user.setActivated(true);
                user.setActivationKey(null);
                this.clearUserCaches(user);

                return user;
            });
    }

    public Optional<User> completePasswordReset(String newPassword, String key) {
        log.debug("Reset user password for reset key {}", key);
        return userRepository.findOneByResetKey(key)
            .filter(user -> user.getResetDate().isAfter(Instant.now().minusSeconds(86400)))
            .map(user -> {
                user.setPassword(passwordEncoder.encode(newPassword));
                user.setResetKey(null);
                user.setResetDate(null);
                this.clearUserCaches(user);
                return user;
            });
    }

    public Optional<User> requestPasswordReset(String mail) {
        return userRepository.findOneByEmailIgnoreCase(mail)
            .filter(User::getActivated)
            .map(user -> {
                user.setResetKey(RandomUtil.generateResetKey());
                user.setResetDate(Instant.now());
                this.clearUserCaches(user);
                return user;
            });
    }

    public User registerUser(AdUserDTO adUser, CVendor vendor, ADOrganization organization) {
        User newUser = null;

        userRepository.findOneByLogin(adUser.getUserLogin()).ifPresent(existingUser -> {
            boolean removed = removeNonActivatedUser(existingUser);
            if (!removed) {
                throw new UsernameAlreadyUsedException();
            }
        });
        userRepository.findOneByEmailIgnoreCase(adUser.getEmail()).ifPresent(existingUser -> {
            boolean removed = removeNonActivatedUser(existingUser);
            if (!removed) {
                throw new EmailAlreadyUsedException();
            }
        });

        String fullName = adUser.getName().trim();
        int spaceIndex = fullName.indexOf(" ");
        newUser = new User();

        if (spaceIndex > 0) {
            newUser.setFirstName(fullName.substring(0, spaceIndex));
            newUser.setLastName(fullName.substring(spaceIndex + 1));
        } else {
            newUser.setFirstName(fullName);
        }

        if (adUser.getPassword() == null) {
            adUser.setPassword(SecurityUtils.generatePassword());
        }

        String encryptedPassword = passwordEncoder.encode(adUser.getPassword());
        newUser.setLogin(adUser.getUserLogin());
        newUser.setLangKey("en");
        // new user gets initially a generated password
        newUser.setPassword(encryptedPassword);

        if (adUser.getEmail() != null) {
            newUser.setEmail(adUser.getEmail());
        }

        // new user is not active
        newUser.setActivated(false);
        // new user gets registration key
        newUser.setActivationKey(RandomUtil.generateActivationKey());
        Set<Authority> authorities = new HashSet<>();
        authorityRepository.findById(AuthoritiesConstants.USER).ifPresent(authorities::add);
        newUser.setAuthorities(authorities);
        userRepository.save(newUser);
        this.clearUserCaches(newUser);
        log.debug("Created Information for User: {}", newUser);

        // Create and save the UserExtra entity
        AdUser pic = adUserMapper.toEntity(adUser)
            .active(newUser.getActivated())
            .adOrganization(organization)
            .phone(adUser.getPhone())
            .position(adUser.getPosition())
            .user(newUser)
            .vendor(true)
            .cVendor(vendor);

        adUserRepository.save(pic);
        log.debug("PIC saved: {}", pic);

        List<CPicBusinessCat> categories = adUser.getBusinessCategoryIds()
            .stream()
            .map((id -> {
                CPicBusinessCat picBusinessCategory = new CPicBusinessCat();
                CBusinessCategory category = cBusinessCategoryMapper.fromId(id);

                return picBusinessCategory
                    .active(true)
                    .adOrganization(organization)
                    .pic(pic)
                    .businessCategory(category);
            }))
            .collect(Collectors.toList());

        cPicBusinessCatRepository.saveAll(categories);
        log.debug("Created Information for PIC's business sector: {}", categories);

        return newUser;
    }

    private boolean removeNonActivatedUser(User existingUser) {
        if (existingUser.getActivated()) {
             return false;
        }
        userRepository.delete(existingUser);
        userRepository.flush();
        this.clearUserCaches(existingUser);
        return true;
    }

    public User createUser(UserDTO userDTO) {
        User user = new User();
        user.setLogin(userDTO.getLogin().toLowerCase());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        if (userDTO.getEmail() != null) {
            user.setEmail(userDTO.getEmail().toLowerCase());
        }
        user.setImageUrl(userDTO.getImageUrl());
        if (userDTO.getLangKey() == null) {
            user.setLangKey(Constants.DEFAULT_LANGUAGE); // default language
        } else {
            user.setLangKey(userDTO.getLangKey());
        }
        String encryptedPassword = passwordEncoder.encode(RandomUtil.generatePassword());
        user.setPassword(encryptedPassword);
        user.setResetKey(RandomUtil.generateResetKey());
        user.setResetDate(Instant.now());
        user.setActivated(true);
        if (userDTO.getAuthorities() != null) {
            Set<Authority> authorities = userDTO.getAuthorities().stream()
                .map(authorityRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
            user.setAuthorities(authorities);
        }
        userRepository.save(user);
        this.clearUserCaches(user);
        log.debug("Created Information for User: {}", user);
        return user;
    }

    /**
     * Update basic information (first name, last name, email, language) for the current user.
     *
     * @param firstName first name of user.
     * @param lastName  last name of user.
     * @param email     email id of user.
     * @param langKey   language key.
     * @param imageUrl  image URL of user.
     */
    public void updateUser(String firstName, String lastName, String email, String langKey, String imageUrl) {
        SecurityUtils.getCurrentUserLogin()
            .flatMap(userRepository::findOneByLogin)
            .ifPresent(user -> {
                user.setFirstName(firstName);
                user.setLastName(lastName);
                if (email != null) {
                    user.setEmail(email.toLowerCase());
                }
                user.setLangKey(langKey);
                user.setImageUrl(imageUrl);
                this.clearUserCaches(user);
                log.debug("Changed Information for User: {}", user);
            });
    }

    /**
     * Update all information for a specific user, and return the modified user.
     *
     * @param userDTO user to update.
     * @return updated user.
     */
    public Optional<UserDTO> updateUser(UserDTO userDTO) {
        return Optional.of(userRepository
            .findById(userDTO.getId()))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(user -> {
                this.clearUserCaches(user);
                user.setLogin(userDTO.getLogin().toLowerCase());
                user.setFirstName(userDTO.getFirstName());
                user.setLastName(userDTO.getLastName());
                if (userDTO.getEmail() != null) {
                    user.setEmail(userDTO.getEmail().toLowerCase());
                }
                user.setImageUrl(userDTO.getImageUrl());
                user.setActivated(userDTO.isActivated());
                user.setLangKey(userDTO.getLangKey());
                Set<Authority> managedAuthorities = user.getAuthorities();
                managedAuthorities.clear();
                userDTO.getAuthorities().stream()
                    .map(authorityRepository::findById)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .forEach(managedAuthorities::add);
                this.clearUserCaches(user);
                log.debug("Changed Information for User: {}", user);
                return user;
            })
            .map(UserDTO::new);
    }

    public void deleteUser(String login) {
        userRepository.findOneByLogin(login).ifPresent(user -> {
            userRepository.delete(user);
            this.clearUserCaches(user);
            log.debug("Deleted User: {}", user);
        });
    }

    public void changePassword(String currentClearTextPassword, String newPassword) {
        SecurityUtils.getCurrentUserLogin()
            .flatMap(userRepository::findOneByLogin)
            .ifPresent(user -> {
                String currentEncryptedPassword = user.getPassword();
                if (!passwordEncoder.matches(currentClearTextPassword, currentEncryptedPassword)) {
                    throw new InvalidPasswordException();
                }
                String encryptedPassword = passwordEncoder.encode(newPassword);
                user.setPassword(encryptedPassword);
                this.clearUserCaches(user);
                log.debug("Changed password for User: {}", user);
            });
    }

    public void changePassword(Long userId, String newPassword) {
        userRepository.findById(userId).ifPresent(user -> {
            String encryptedPassword = passwordEncoder.encode(newPassword);
            user.setPassword(encryptedPassword);
            this.clearUserCaches(user);
            log.debug("Changed password for User: {}", user);
        });
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> getAllManagedUsers(Pageable pageable) {
        return userRepository.findAllByLoginNot(pageable, Constants.ANONYMOUS_USER).map(UserDTO::new);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthoritiesByLogin(String login) {
        return userRepository.findOneWithAuthoritiesByLogin(login);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findOneWithAuthoritiesByEmailIgnoreCase(email);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities(Long id) {
        return userRepository.findOneWithAuthoritiesById(id);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities() {
        return SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneWithAuthoritiesByLogin);
    }

    /**
     * Not activated users should be automatically deleted after 3 days.
     * <p>
     * This is scheduled to get fired everyday, at 01:00 (am).
     */
    // @Scheduled(cron = "0 0 1 * * ?")
    public void removeNotActivatedUsers() {
        userRepository
            .findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(Instant.now().minus(3, ChronoUnit.DAYS))
            .forEach(user -> {
                log.debug("Deleting not activated user {}", user.getLogin());
                userRepository.delete(user);
                this.clearUserCaches(user);
            });
    }

    /**
     * Gets a list of all the authorities.
     * @return a list of all the authorities.
     */
    public List<String> getAuthorities() {
        return authorityRepository.findAll().stream().map(Authority::getName).collect(Collectors.toList());
    }


    private void clearUserCaches(User user) {
        Objects.requireNonNull(cacheManager.getCache(UserRepository.USERS_BY_LOGIN_CACHE)).evict(user.getLogin());
        if (user.getEmail() != null) {
            Objects.requireNonNull(cacheManager.getCache(UserRepository.USERS_BY_EMAIL_CACHE)).evict(user.getEmail());
        }
    }
}
