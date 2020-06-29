package com.bhp.opusb.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.bhp.opusb.domain.AdMenu;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.AdMenuRepository;
import com.bhp.opusb.service.dto.AdMenuCriteria;
import com.bhp.opusb.service.dto.AdMenuDTO;
import com.bhp.opusb.service.mapper.AdMenuMapper;

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

    private final AdMenuMapper adMenuMapper;

    public AdMenuQueryService(AdMenuRepository adMenuRepository, AdMenuMapper adMenuMapper) {
        this.adMenuRepository = adMenuRepository;
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
