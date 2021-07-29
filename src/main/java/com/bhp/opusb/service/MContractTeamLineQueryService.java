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

import com.bhp.opusb.domain.MContractTeamLine;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MContractTeamLineRepository;
import com.bhp.opusb.service.dto.MContractTeamLineCriteria;
import com.bhp.opusb.service.dto.MContractTeamLineDTO;
import com.bhp.opusb.service.mapper.MContractTeamLineMapper;

/**
 * Service for executing complex queries for {@link MContractTeamLine} entities in the database.
 * The main input is a {@link MContractTeamLineCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MContractTeamLineDTO} or a {@link Page} of {@link MContractTeamLineDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MContractTeamLineQueryService extends QueryService<MContractTeamLine> {

    private final Logger log = LoggerFactory.getLogger(MContractTeamLineQueryService.class);

    private final MContractTeamLineRepository mContractTeamLineRepository;

    private final MContractTeamLineMapper mContractTeamLineMapper;

    public MContractTeamLineQueryService(MContractTeamLineRepository mContractTeamLineRepository, MContractTeamLineMapper mContractTeamLineMapper) {
        this.mContractTeamLineRepository = mContractTeamLineRepository;
        this.mContractTeamLineMapper = mContractTeamLineMapper;
    }

    /**
     * Return a {@link List} of {@link MContractTeamLineDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MContractTeamLineDTO> findByCriteria(MContractTeamLineCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MContractTeamLine> specification = createSpecification(criteria);
        return mContractTeamLineMapper.toDto(mContractTeamLineRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MContractTeamLineDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MContractTeamLineDTO> findByCriteria(MContractTeamLineCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MContractTeamLine> specification = createSpecification(criteria);
        return mContractTeamLineRepository.findAll(specification, page)
            .map(mContractTeamLineMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MContractTeamLineCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MContractTeamLine> specification = createSpecification(criteria);
        return mContractTeamLineRepository.count(specification);
    }

    /**
     * Function to convert {@link MContractTeamLineCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MContractTeamLine> createSpecification(MContractTeamLineCriteria criteria) {
        Specification<MContractTeamLine> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MContractTeamLine_.id));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MContractTeamLine_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MContractTeamLine_.active));
            }
            if (criteria.getPosition() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPosition(), MContractTeamLine_.position));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MContractTeamLine_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getContractTeamId() != null) {
                specification = specification.and(buildSpecification(criteria.getContractTeamId(),
                    root -> root.join(MContractTeamLine_.contractTeam, JoinType.LEFT).get(MContractTeam_.id)));
            }
            if (criteria.getAdUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdUserId(),
                    root -> root.join(MContractTeamLine_.adUser, JoinType.LEFT).get(AdUser_.id)));
            }
        }
        return specification;
    }
}
