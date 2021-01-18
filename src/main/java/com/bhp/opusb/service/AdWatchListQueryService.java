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

import com.bhp.opusb.domain.AdWatchList;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.AdWatchListRepository;
import com.bhp.opusb.service.dto.AdWatchListCriteria;
import com.bhp.opusb.service.dto.AdWatchListDTO;
import com.bhp.opusb.service.mapper.AdWatchListMapper;

/**
 * Service for executing complex queries for {@link AdWatchList} entities in the database.
 * The main input is a {@link AdWatchListCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AdWatchListDTO} or a {@link Page} of {@link AdWatchListDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AdWatchListQueryService extends QueryService<AdWatchList> {

    private final Logger log = LoggerFactory.getLogger(AdWatchListQueryService.class);

    private final AdWatchListRepository adWatchListRepository;

    private final AdWatchListMapper adWatchListMapper;

    public AdWatchListQueryService(AdWatchListRepository adWatchListRepository, AdWatchListMapper adWatchListMapper) {
        this.adWatchListRepository = adWatchListRepository;
        this.adWatchListMapper = adWatchListMapper;
    }

    /**
     * Return a {@link List} of {@link AdWatchListDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AdWatchListDTO> findByCriteria(AdWatchListCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AdWatchList> specification = createSpecification(criteria);
        return adWatchListMapper.toDto(adWatchListRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AdWatchListDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AdWatchListDTO> findByCriteria(AdWatchListCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AdWatchList> specification = createSpecification(criteria);
        return adWatchListRepository.findAll(specification, page)
            .map(adWatchListMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AdWatchListCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AdWatchList> specification = createSpecification(criteria);
        return adWatchListRepository.count(specification);
    }

    /**
     * Function to convert {@link AdWatchListCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AdWatchList> createSpecification(AdWatchListCriteria criteria) {
        Specification<AdWatchList> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AdWatchList_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), AdWatchList_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), AdWatchList_.description));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), AdWatchList_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), AdWatchList_.active));
            }
            if (criteria.getAdWatchListItemId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdWatchListItemId(),
                    root -> root.join(AdWatchList_.adWatchListItems, JoinType.LEFT).get(AdWatchListItem_.id)));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(AdWatchList_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
        }
        return specification;
    }
}
