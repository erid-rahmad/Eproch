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

import com.bhp.opusb.domain.MBiddingEvalTeamLine;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MBiddingEvalTeamLineRepository;
import com.bhp.opusb.service.dto.MBiddingEvalTeamLineCriteria;
import com.bhp.opusb.service.dto.MBiddingEvalTeamLineDTO;
import com.bhp.opusb.service.mapper.MBiddingEvalTeamLineMapper;

/**
 * Service for executing complex queries for {@link MBiddingEvalTeamLine} entities in the database.
 * The main input is a {@link MBiddingEvalTeamLineCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MBiddingEvalTeamLineDTO} or a {@link Page} of {@link MBiddingEvalTeamLineDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MBiddingEvalTeamLineQueryService extends QueryService<MBiddingEvalTeamLine> {

    private final Logger log = LoggerFactory.getLogger(MBiddingEvalTeamLineQueryService.class);

    private final MBiddingEvalTeamLineRepository mBiddingEvalTeamLineRepository;

    private final MBiddingEvalTeamLineMapper mBiddingEvalTeamLineMapper;

    public MBiddingEvalTeamLineQueryService(MBiddingEvalTeamLineRepository mBiddingEvalTeamLineRepository, MBiddingEvalTeamLineMapper mBiddingEvalTeamLineMapper) {
        this.mBiddingEvalTeamLineRepository = mBiddingEvalTeamLineRepository;
        this.mBiddingEvalTeamLineMapper = mBiddingEvalTeamLineMapper;
    }

    /**
     * Return a {@link List} of {@link MBiddingEvalTeamLineDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MBiddingEvalTeamLineDTO> findByCriteria(MBiddingEvalTeamLineCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MBiddingEvalTeamLine> specification = createSpecification(criteria);
        return mBiddingEvalTeamLineMapper.toDto(mBiddingEvalTeamLineRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MBiddingEvalTeamLineDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MBiddingEvalTeamLineDTO> findByCriteria(MBiddingEvalTeamLineCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MBiddingEvalTeamLine> specification = createSpecification(criteria);
        return mBiddingEvalTeamLineRepository.findAll(specification, page)
            .map(mBiddingEvalTeamLineMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MBiddingEvalTeamLineCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MBiddingEvalTeamLine> specification = createSpecification(criteria);
        return mBiddingEvalTeamLineRepository.count(specification);
    }

    /**
     * Function to convert {@link MBiddingEvalTeamLineCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MBiddingEvalTeamLine> createSpecification(MBiddingEvalTeamLineCriteria criteria) {
        Specification<MBiddingEvalTeamLine> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MBiddingEvalTeamLine_.id));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MBiddingEvalTeamLine_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MBiddingEvalTeamLine_.active));
            }
            if (criteria.getPosition() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPosition(), MBiddingEvalTeamLine_.position));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MBiddingEvalTeamLine_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getEvaluationTeamId() != null) {
                specification = specification.and(buildSpecification(criteria.getEvaluationTeamId(),
                    root -> root.join(MBiddingEvalTeamLine_.evaluationTeam, JoinType.LEFT).get(MBiddingEvaluationTeam_.id)));
            }
            if (criteria.getAdUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdUserId(),
                    root -> root.join(MBiddingEvalTeamLine_.adUser, JoinType.LEFT).get(AdUser_.id)));
            }
        }
        return specification;
    }
}
