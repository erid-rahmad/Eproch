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

import com.bhp.opusb.domain.MContractTeam;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MContractTeamRepository;
import com.bhp.opusb.service.dto.MContractTeamCriteria;
import com.bhp.opusb.service.dto.MContractTeamDTO;
import com.bhp.opusb.service.mapper.MContractTeamMapper;

/**
 * Service for executing complex queries for {@link MContractTeam} entities in the database.
 * The main input is a {@link MContractTeamCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MContractTeamDTO} or a {@link Page} of {@link MContractTeamDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MContractTeamQueryService extends QueryService<MContractTeam> {

    private final Logger log = LoggerFactory.getLogger(MContractTeamQueryService.class);

    private final MContractTeamRepository mContractTeamRepository;

    private final MContractTeamMapper mContractTeamMapper;

    public MContractTeamQueryService(MContractTeamRepository mContractTeamRepository, MContractTeamMapper mContractTeamMapper) {
        this.mContractTeamRepository = mContractTeamRepository;
        this.mContractTeamMapper = mContractTeamMapper;
    }

    /**
     * Return a {@link List} of {@link MContractTeamDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MContractTeamDTO> findByCriteria(MContractTeamCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MContractTeam> specification = createSpecification(criteria);
        return mContractTeamMapper.toDto(mContractTeamRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MContractTeamDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MContractTeamDTO> findByCriteria(MContractTeamCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MContractTeam> specification = createSpecification(criteria);
        return mContractTeamRepository.findAll(specification, page)
            .map(mContractTeamMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MContractTeamCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MContractTeam> specification = createSpecification(criteria);
        return mContractTeamRepository.count(specification);
    }

    /**
     * Function to convert {@link MContractTeamCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MContractTeam> createSpecification(MContractTeamCriteria criteria) {
        Specification<MContractTeam> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MContractTeam_.id));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), MContractTeam_.status));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MContractTeam_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MContractTeam_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MContractTeam_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getContractId() != null) {
                specification = specification.and(buildSpecification(criteria.getContractId(),
                    root -> root.join(MContractTeam_.contract, JoinType.LEFT).get(MContract_.id)));
            }
        }
        return specification;
    }
}
