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

import com.bhp.opusb.domain.MAuctionTeam;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MAuctionTeamRepository;
import com.bhp.opusb.service.dto.MAuctionTeamCriteria;
import com.bhp.opusb.service.dto.MAuctionTeamDTO;
import com.bhp.opusb.service.mapper.MAuctionTeamMapper;

/**
 * Service for executing complex queries for {@link MAuctionTeam} entities in the database.
 * The main input is a {@link MAuctionTeamCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MAuctionTeamDTO} or a {@link Page} of {@link MAuctionTeamDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MAuctionTeamQueryService extends QueryService<MAuctionTeam> {

    private final Logger log = LoggerFactory.getLogger(MAuctionTeamQueryService.class);

    private final MAuctionTeamRepository mAuctionTeamRepository;

    private final MAuctionTeamMapper mAuctionTeamMapper;

    public MAuctionTeamQueryService(MAuctionTeamRepository mAuctionTeamRepository, MAuctionTeamMapper mAuctionTeamMapper) {
        this.mAuctionTeamRepository = mAuctionTeamRepository;
        this.mAuctionTeamMapper = mAuctionTeamMapper;
    }

    /**
     * Return a {@link List} of {@link MAuctionTeamDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MAuctionTeamDTO> findByCriteria(MAuctionTeamCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MAuctionTeam> specification = createSpecification(criteria);
        return mAuctionTeamMapper.toDto(mAuctionTeamRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MAuctionTeamDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MAuctionTeamDTO> findByCriteria(MAuctionTeamCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MAuctionTeam> specification = createSpecification(criteria);
        return mAuctionTeamRepository.findAll(specification, page)
            .map(mAuctionTeamMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MAuctionTeamCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MAuctionTeam> specification = createSpecification(criteria);
        return mAuctionTeamRepository.count(specification);
    }

    /**
     * Function to convert {@link MAuctionTeamCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MAuctionTeam> createSpecification(MAuctionTeamCriteria criteria) {
        Specification<MAuctionTeam> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MAuctionTeam_.id));
            }
            if (criteria.getRole() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRole(), MAuctionTeam_.role));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MAuctionTeam_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MAuctionTeam_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MAuctionTeam_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getUserUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserUserId(),
                    root -> root.join(MAuctionTeam_.user, JoinType.LEFT).get(AdUser_.id)));
            }
            if (criteria.getAuctionId() != null) {
                specification = specification.and(buildSpecification(criteria.getAuctionId(),
                    root -> root.join(MAuctionTeam_.auction, JoinType.LEFT).get(MAuction_.id)));
            }
        }
        return specification;
    }
}
