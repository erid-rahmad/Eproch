package com.bhp.opusb.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.JoinType;

// for static metamodels
import com.bhp.opusb.domain.ADOrganization_;
import com.bhp.opusb.domain.ADWindow_;
import com.bhp.opusb.domain.AdForm_;
import com.bhp.opusb.domain.AdMenu;
import com.bhp.opusb.domain.AdMenu_;
import com.bhp.opusb.repository.AdMenuRepository;
import com.bhp.opusb.service.dto.AdMenuCriteria;
import com.bhp.opusb.service.dto.AdMenuDTO;
import com.bhp.opusb.service.dto.ScAccessCriteria;
import com.bhp.opusb.service.dto.ScAccessDTO;
import com.bhp.opusb.service.mapper.AdMenuMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;
import io.github.jhipster.service.filter.StringFilter;
import io.vavr.collection.Stream;

/**
 * Service for executing complex queries for {@link AdMenu} entities in the database.
 * The main input is a {@link AdMenuCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AdMenuDTO} or a {@link Page} of {@link AdMenuDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AdMenuQueryService extends QueryService<AdMenu> {

    private final Logger log = LoggerFactory.getLogger(AdMenuQueryService.class);

    private final AdMenuRepository adMenuRepository;
    private final ScAccessQueryService scAccessQueryService;

    private final AdMenuMapper adMenuMapper;

    public AdMenuQueryService(
        AdMenuRepository adMenuRepository, ScAccessQueryService scAccessQueryService,
        AdMenuMapper adMenuMapper
    ) {
        this.adMenuRepository = adMenuRepository;
        this.scAccessQueryService = scAccessQueryService;
        this.adMenuMapper = adMenuMapper;
    }

    /**
     * Return a {@link List} of {@link AdMenuDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AdMenuDTO> findByCriteria(AdMenuCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AdMenu> specification = createSpecification(criteria);
        return adMenuMapper.toDto(adMenuRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AdMenuDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AdMenuDTO> findByCriteria(AdMenuCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AdMenu> specification = createSpecification(criteria);
        return adMenuRepository.findAll(specification, page)
            .map(adMenuMapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<AdMenuDTO> findAllParentMenus() {
        log.debug("find all parent menus");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        final List<String> authorities = Stream.ofAll(authentication.getAuthorities())
            .map(GrantedAuthority::getAuthority).toJavaList();

        ScAccessCriteria authorityCriteria = new ScAccessCriteria();
        StringFilter nameFilter = new StringFilter();
        nameFilter.setIn(authorities);
        authorityCriteria.setAuthorityName(nameFilter);

        // TODO Tune the repository to eagerly load the sc_access records.
        final List<ScAccessDTO> accesses = scAccessQueryService.findByCriteria(authorityCriteria);
        final Set<Long> accessibleFormId = new HashSet<>();
        final Set<Long> accessibleWindowId = new HashSet<>();

        for (ScAccessDTO access : accesses) {
            if (access.getFormId() != null) {
                accessibleFormId.add(access.getFormId());
            } else if (access.getWindowId() != null) {
                accessibleWindowId.add(access.getWindowId());
            }
        }

        // TODO Tune the repository to eagerly load the ad_menu records.
        final List<AdMenuDTO> menus = adMenuMapper.toDto(adMenuRepository.findByParentMenuIsNull(Sort.by("sequence").ascending()));
        return filterMenu(menus, accessibleFormId, accessibleWindowId);
    }

    private List<AdMenuDTO> filterMenu(List<AdMenuDTO> menus, Set<Long> accessibleFormId,
            Set<Long> accessibleWindowId) {

        List<AdMenuDTO> filteredMenus = new ArrayList<>();
        for (AdMenuDTO menu : menus) {
            if (menu.getAction() == null || isIdAccessible(menu.getAdFormId(), accessibleFormId)
                    || isIdAccessible(menu.getAdWindowId(), accessibleWindowId)) {

                if (menu.hasSubmenus()) {
                    menu.setAdMenus(filterMenu(menu.getAdMenus(), accessibleFormId, accessibleWindowId));
                    if (menu.hasSubmenus()) {
                        filteredMenus.add(menu);
                    }
                } else {
                    filteredMenus.add(menu);
                }
            }
        }
        return filteredMenus;
    }

    private boolean isIdAccessible(Long id, Set<Long> accessibleIds) {
        return id != null && accessibleIds.contains(id);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AdMenuCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AdMenu> specification = createSpecification(criteria);
        return adMenuRepository.count(specification);
    }

    /**
     * Function to convert {@link AdMenuCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AdMenu> createSpecification(AdMenuCriteria criteria) {
        Specification<AdMenu> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AdMenu_.id));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), AdMenu_.uid));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), AdMenu_.name));
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValue(), AdMenu_.value));
            }
            if (criteria.getTranslationKey() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTranslationKey(), AdMenu_.translationKey));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), AdMenu_.description));
            }
            if (criteria.getPath() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPath(), AdMenu_.path));
            }
            if (criteria.getAction() != null) {
                specification = specification.and(buildSpecification(criteria.getAction(), AdMenu_.action));
            }
            if (criteria.getIcon() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIcon(), AdMenu_.icon));
            }
            if (criteria.getRedirect() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRedirect(), AdMenu_.redirect));
            }
            if (criteria.getSequence() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSequence(), AdMenu_.sequence));
            }
            if (criteria.getAlwaysShow() != null) {
                specification = specification.and(buildSpecification(criteria.getAlwaysShow(), AdMenu_.alwaysShow));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), AdMenu_.active));
            }
            if (criteria.getAdMenuId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdMenuId(),
                    root -> root.join(AdMenu_.adMenus, JoinType.LEFT).get(AdMenu_.id)));
            }
            if (criteria.getAdWindowId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdWindowId(),
                    root -> root.join(AdMenu_.adWindow, JoinType.LEFT).get(ADWindow_.id)));
            }
            if (criteria.getAdFormId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdFormId(),
                    root -> root.join(AdMenu_.adForm, JoinType.LEFT).get(AdForm_.id)));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(AdMenu_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getParentMenuId() != null) {
                specification = specification.and(buildSpecification(criteria.getParentMenuId(),
                    root -> root.join(AdMenu_.parentMenu, JoinType.LEFT).get(AdMenu_.id)));
            }
        }
        return specification;
    }
}
