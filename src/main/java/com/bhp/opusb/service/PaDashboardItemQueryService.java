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

import com.bhp.opusb.domain.PaDashboardItem;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.PaDashboardItemRepository;
import com.bhp.opusb.service.dto.PaDashboardItemCriteria;
import com.bhp.opusb.service.dto.PaDashboardItemDTO;
import com.bhp.opusb.service.mapper.PaDashboardItemMapper;

/**
 * Service for executing complex queries for {@link PaDashboardItem} entities in the database.
 * The main input is a {@link PaDashboardItemCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PaDashboardItemDTO} or a {@link Page} of {@link PaDashboardItemDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PaDashboardItemQueryService extends QueryService<PaDashboardItem> {

    private final Logger log = LoggerFactory.getLogger(PaDashboardItemQueryService.class);

    private final PaDashboardItemRepository paDashboardItemRepository;

    private final PaDashboardItemMapper paDashboardItemMapper;

    public PaDashboardItemQueryService(PaDashboardItemRepository paDashboardItemRepository, PaDashboardItemMapper paDashboardItemMapper) {
        this.paDashboardItemRepository = paDashboardItemRepository;
        this.paDashboardItemMapper = paDashboardItemMapper;
    }

    /**
     * Return a {@link List} of {@link PaDashboardItemDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PaDashboardItemDTO> findByCriteria(PaDashboardItemCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PaDashboardItem> specification = createSpecification(criteria);
        return paDashboardItemMapper.toDto(paDashboardItemRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PaDashboardItemDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PaDashboardItemDTO> findByCriteria(PaDashboardItemCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PaDashboardItem> specification = createSpecification(criteria);
        return paDashboardItemRepository.findAll(specification, page)
            .map(paDashboardItemMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PaDashboardItemCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PaDashboardItem> specification = createSpecification(criteria);
        return paDashboardItemRepository.count(specification);
    }

    /**
     * Function to convert {@link PaDashboardItemCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PaDashboardItem> createSpecification(PaDashboardItemCriteria criteria) {
        Specification<PaDashboardItem> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PaDashboardItem_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), PaDashboardItem_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), PaDashboardItem_.description));
            }
            if (criteria.getColumnNo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getColumnNo(), PaDashboardItem_.columnNo));
            }
            if (criteria.getRowNo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRowNo(), PaDashboardItem_.rowNo));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), PaDashboardItem_.type));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), PaDashboardItem_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), PaDashboardItem_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(PaDashboardItem_.adOrganization, JoinType.INNER).get(ADOrganization_.id)));
            }
            if (criteria.getAdWatchListId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdWatchListId(),
                    root -> root.join(PaDashboardItem_.adWatchList, JoinType.LEFT).get(AdWatchList_.id)));
            }
            if (criteria.getPaDashboardId() != null) {
                specification = specification.and(buildSpecification(criteria.getPaDashboardId(),
                    root -> root.join(PaDashboardItem_.paDashboard, JoinType.INNER).get(PaDashboard_.id)));
            }
        }
        return specification;
    }
}
