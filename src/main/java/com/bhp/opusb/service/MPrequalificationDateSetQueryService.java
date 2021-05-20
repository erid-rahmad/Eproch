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

import com.bhp.opusb.domain.MPrequalificationDateSet;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MPrequalificationDateSetRepository;
import com.bhp.opusb.service.dto.MPrequalificationDateSetCriteria;
import com.bhp.opusb.service.dto.MPrequalificationDateSetDTO;
import com.bhp.opusb.service.mapper.MPrequalificationDateSetMapper;

/**
 * Service for executing complex queries for {@link MPrequalificationDateSet} entities in the database.
 * The main input is a {@link MPrequalificationDateSetCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MPrequalificationDateSetDTO} or a {@link Page} of {@link MPrequalificationDateSetDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MPrequalificationDateSetQueryService extends QueryService<MPrequalificationDateSet> {

    private final Logger log = LoggerFactory.getLogger(MPrequalificationDateSetQueryService.class);

    private final MPrequalificationDateSetRepository mPrequalificationDateSetRepository;

    private final MPrequalificationDateSetMapper mPrequalificationDateSetMapper;

    public MPrequalificationDateSetQueryService(MPrequalificationDateSetRepository mPrequalificationDateSetRepository, MPrequalificationDateSetMapper mPrequalificationDateSetMapper) {
        this.mPrequalificationDateSetRepository = mPrequalificationDateSetRepository;
        this.mPrequalificationDateSetMapper = mPrequalificationDateSetMapper;
    }

    /**
     * Return a {@link List} of {@link MPrequalificationDateSetDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MPrequalificationDateSetDTO> findByCriteria(MPrequalificationDateSetCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MPrequalificationDateSet> specification = createSpecification(criteria);
        return mPrequalificationDateSetMapper.toDto(mPrequalificationDateSetRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MPrequalificationDateSetDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MPrequalificationDateSetDTO> findByCriteria(MPrequalificationDateSetCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MPrequalificationDateSet> specification = createSpecification(criteria);
        return mPrequalificationDateSetRepository.findAll(specification, page)
            .map(mPrequalificationDateSetMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MPrequalificationDateSetCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MPrequalificationDateSet> specification = createSpecification(criteria);
        return mPrequalificationDateSetRepository.count(specification);
    }

    /**
     * Function to convert {@link MPrequalificationDateSetCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MPrequalificationDateSet> createSpecification(MPrequalificationDateSetCriteria criteria) {
        Specification<MPrequalificationDateSet> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MPrequalificationDateSet_.id));
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartDate(), MPrequalificationDateSet_.startDate));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), MPrequalificationDateSet_.endDate));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), MPrequalificationDateSet_.status));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MPrequalificationDateSet_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MPrequalificationDateSet_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MPrequalificationDateSet_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getBiddingScheduleId() != null) {
                specification = specification.and(buildSpecification(criteria.getBiddingScheduleId(),
                    root -> root.join(MPrequalificationDateSet_.biddingSchedule, JoinType.LEFT).get(MBiddingSchedule_.id)));
            }
        }
        return specification;
    }
}
