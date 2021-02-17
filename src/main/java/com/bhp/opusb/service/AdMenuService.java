package com.bhp.opusb.service;

import com.bhp.opusb.domain.AdMenu;
import com.bhp.opusb.repository.AdMenuRepository;
import com.bhp.opusb.service.dto.AdMenuDTO;
import com.bhp.opusb.service.mapper.AdMenuMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link AdMenu}.
 */
@Service
@Transactional
public class AdMenuService {

    private final Logger log = LoggerFactory.getLogger(AdMenuService.class);

    private final AdMenuRepository adMenuRepository;

    private final AdMenuMapper adMenuMapper;

    public AdMenuService(AdMenuRepository adMenuRepository, AdMenuMapper adMenuMapper) {
        this.adMenuRepository = adMenuRepository;
        this.adMenuMapper = adMenuMapper;
    }

    /**
     * Save a adMenu.
     *
     * @param adMenuDTO the entity to save.
     * @return the persisted entity.
     */
    @CacheEvict(cacheNames = "com.bhp.opusb.domain.AdMenu.adMenus", allEntries = true)
    public AdMenuDTO save(AdMenuDTO adMenuDTO) {
        log.debug("Request to save AdMenu : {}", adMenuDTO);
        AdMenu adMenu = adMenuMapper.toEntity(adMenuDTO);
        adMenu = adMenuRepository.save(adMenu);
        return adMenuMapper.toDto(adMenu);
    }

    @CacheEvict(cacheNames = "com.bhp.opusb.domain.AdMenu.adMenus", allEntries = true)
    public List<AdMenuDTO> saveAll(List<AdMenuDTO> adMenuDTOs) {
        List<AdMenu> adMenus = adMenuDTOs.stream()
            .map(dto -> adMenuMapper.toEntity(dto))
            .collect(Collectors.toList());
        
        return adMenuRepository.saveAll(adMenus).stream()
            .map(entity -> adMenuMapper.toDto(entity))
            .collect(Collectors.toList());
    }

    /**
     * Get all the adMenus.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AdMenuDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AdMenus");
        return adMenuRepository.findAll(pageable)
            .map(adMenuMapper::toDto);
    }

    /**
     * Get one adMenu by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AdMenuDTO> findOne(Long id) {
        log.debug("Request to get AdMenu : {}", id);
        return adMenuRepository.findById(id)
            .map(adMenuMapper::toDto);
    }

    /**
     * Get the fullpath to the specific submenu ID.
     * @param id The ID of the target menu.
     */
    @Transactional(readOnly = true)
    public String getFullPath(Long id) {
        final StringBuilder fullPath = new StringBuilder();
        boolean hasParent = false;

        Optional<AdMenu> record = adMenuRepository.findById(id);
        if (record.isPresent()) {
            AdMenu menu = record.get();

            fullPath.append(menu.getPath());
            hasParent = menu.getParentMenu() != null;

            while (hasParent) {
                menu = menu.getParentMenu();
                fullPath.insert(0, menu.getPath() + "/");
                hasParent = menu.getParentMenu() != null;
            }
        }

        return fullPath.toString();
    }

    /**
     * Delete the adMenu by id.
     *
     * @param id the id of the entity.
     */
    @CacheEvict(cacheNames = "com.bhp.opusb.domain.AdMenu.adMenus", allEntries = true)
    public void delete(Long id) {
        log.debug("Request to delete AdMenu : {}", id);
        adMenuRepository.deleteById(id);
    }
}
