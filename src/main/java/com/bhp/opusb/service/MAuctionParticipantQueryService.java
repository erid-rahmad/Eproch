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

import com.bhp.opusb.domain.MAuctionParticipant;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MAuctionParticipantRepository;
import com.bhp.opusb.service.dto.MAuctionParticipantCriteria;
import com.bhp.opusb.service.dto.MAuctionParticipantDTO;
import com.bhp.opusb.service.mapper.MAuctionParticipantMapper;

/**
 * Service for executing complex queries for {@link MAuctionParticipant} entities in the database.
 * The main input is a {@link MAuctionParticipantCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MAuctionParticipantDTO} or a {@link Page} of {@link MAuctionParticipantDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MAuctionParticipantQueryService extends QueryService<MAuctionParticipant> {

    private final Logger log = LoggerFactory.getLogger(MAuctionParticipantQueryService.class);

    private final MAuctionParticipantRepository mAuctionParticipantRepository;

    private final MAuctionParticipantMapper mAuctionParticipantMapper;

    public MAuctionParticipantQueryService(MAuctionParticipantRepository mAuctionParticipantRepository, MAuctionParticipantMapper mAuctionParticipantMapper) {
        this.mAuctionParticipantRepository = mAuctionParticipantRepository;
        this.mAuctionParticipantMapper = mAuctionParticipantMapper;
    }

    /**
     * Return a {@link List} of {@link MAuctionParticipantDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MAuctionParticipantDTO> findByCriteria(MAuctionParticipantCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MAuctionParticipant> specification = createSpecification(criteria);
        return mAuctionParticipantMapper.toDto(mAuctionParticipantRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MAuctionParticipantDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MAuctionParticipantDTO> findByCriteria(MAuctionParticipantCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MAuctionParticipant> specification = createSpecification(criteria);
        return mAuctionParticipantRepository.findAll(specification, page)
            .map(mAuctionParticipantMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MAuctionParticipantCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MAuctionParticipant> specification = createSpecification(criteria);
        return mAuctionParticipantRepository.count(specification);
    }

    /**
     * Function to convert {@link MAuctionParticipantCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MAuctionParticipant> createSpecification(MAuctionParticipantCriteria criteria) {
        Specification<MAuctionParticipant> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MAuctionParticipant_.id));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MAuctionParticipant_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MAuctionParticipant_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MAuctionParticipant_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getAuctionId() != null) {
                specification = specification.and(buildSpecification(criteria.getAuctionId(),
                    root -> root.join(MAuctionParticipant_.auction, JoinType.INNER).get(MAuction_.id)));
            }
            if (criteria.getAuctionActive() != null) {
                specification = specification.and(buildSpecification(criteria.getAuctionActive(),
                    root -> root.join(MAuctionParticipant_.auction, JoinType.INNER).get(MAuction_.active)));
            }
            if (criteria.getAuctionDocumentStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getAuctionDocumentStatus(),
                    root -> root.join(MAuctionParticipant_.auction, JoinType.INNER).get(MAuction_.documentStatus)));
            }
            if (criteria.getAuctionStartDate() != null) {
                specification = specification.and(buildSpecification(criteria.getAuctionStartDate(),
                    root -> root.join(MAuctionParticipant_.auction, JoinType.INNER)
                        .join(MAuction_.rule).get(MAuctionRule_.startDate)));
            }
            if (criteria.getUserUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserUserId(),
                    root -> root.join(MAuctionParticipant_.user, JoinType.LEFT).get(AdUser_.id)));
            }
            if (criteria.getVendorId() != null) {
                specification = specification.and(buildSpecification(criteria.getVendorId(),
                    root -> root.join(MAuctionParticipant_.vendor, JoinType.LEFT).get(CVendor_.id)));
            }
        }
        return specification;
    }
}
