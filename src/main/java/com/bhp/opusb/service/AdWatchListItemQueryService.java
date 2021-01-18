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

import com.bhp.opusb.domain.AdWatchListItem;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.AdWatchListItemRepository;
import com.bhp.opusb.service.dto.AdWatchListItemCriteria;
import com.bhp.opusb.service.dto.AdWatchListItemDTO;
import com.bhp.opusb.service.mapper.AdWatchListItemMapper;

/**
 * Service for executing complex queries for {@link AdWatchListItem} entities in the database.
 * The main input is a {@link AdWatchListItemCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AdWatchListItemDTO} or a {@link Page} of {@link AdWatchListItemDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AdWatchListItemQueryService extends QueryService<AdWatchListItem> {

    private final Logger log = LoggerFactory.getLogger(AdWatchListItemQueryService.class);

    private final AdWatchListItemRepository adWatchListItemRepository;

    private final AdWatchListItemMapper adWatchListItemMapper;

    public AdWatchListItemQueryService(AdWatchListItemRepository adWatchListItemRepository, AdWatchListItemMapper adWatchListItemMapper) {
        this.adWatchListItemRepository = adWatchListItemRepository;
        this.adWatchListItemMapper = adWatchListItemMapper;
    }

    /**
     * Return a {@link List} of {@link AdWatchListItemDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AdWatchListItemDTO> findByCriteria(AdWatchListItemCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AdWatchListItem> specification = createSpecification(criteria);
        return adWatchListItemMapper.toDto(adWatchListItemRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AdWatchListItemDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AdWatchListItemDTO> findByCriteria(AdWatchListItemCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AdWatchListItem> specification = createSpecification(criteria);
        return adWatchListItemRepository.findAll(specification, page)
            .map(adWatchListItemMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AdWatchListItemCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AdWatchListItem> specification = createSpecification(criteria);
        return adWatchListItemRepository.count(specification);
    }

    /**
     * Function to convert {@link AdWatchListItemCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AdWatchListItem> createSpecification(AdWatchListItemCriteria criteria) {
        Specification<AdWatchListItem> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AdWatchListItem_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), AdWatchListItem_.code));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), AdWatchListItem_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), AdWatchListItem_.description));
            }
            if (criteria.getServiceName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getServiceName(), AdWatchListItem_.serviceName));
            }
            if (criteria.getRestApiEndpoint() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRestApiEndpoint(), AdWatchListItem_.restApiEndpoint));
            }
            if (criteria.getWebsocketEndpoint() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWebsocketEndpoint(), AdWatchListItem_.websocketEndpoint));
            }
            if (criteria.getActionType() != null) {
                specification = specification.and(buildSpecification(criteria.getActionType(), AdWatchListItem_.actionType));
            }
            if (criteria.getActionUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getActionUrl(), AdWatchListItem_.actionUrl));
            }
            if (criteria.getFilterQuery() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFilterQuery(), AdWatchListItem_.filterQuery));
            }
            if (criteria.getAccentColor() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccentColor(), AdWatchListItem_.accentColor));
            }
            if (criteria.getIcon() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIcon(), AdWatchListItem_.icon));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), AdWatchListItem_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), AdWatchListItem_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(AdWatchListItem_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getAdMenuId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdMenuId(),
                    root -> root.join(AdWatchListItem_.adMenu, JoinType.LEFT).get(AdMenu_.id)));
            }
            if (criteria.getAdWatchListId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdWatchListId(),
                    root -> root.join(AdWatchListItem_.adWatchList, JoinType.LEFT).get(AdWatchList_.id)));
            }
        }
        return specification;
    }
}
