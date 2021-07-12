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

import com.bhp.opusb.domain.MBlacklist;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MBlacklistRepository;
import com.bhp.opusb.service.dto.MBlacklistCriteria;
import com.bhp.opusb.service.dto.MBlacklistDTO;
import com.bhp.opusb.service.mapper.MBlacklistMapper;

/**
 * Service for executing complex queries for {@link MBlacklist} entities in the database.
 * The main input is a {@link MBlacklistCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MBlacklistDTO} or a {@link Page} of {@link MBlacklistDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MBlacklistQueryService extends QueryService<MBlacklist> {

    private final Logger log = LoggerFactory.getLogger(MBlacklistQueryService.class);

    private final MBlacklistRepository mBlacklistRepository;

    private final MBlacklistMapper mBlacklistMapper;

    public MBlacklistQueryService(MBlacklistRepository mBlacklistRepository, MBlacklistMapper mBlacklistMapper) {
        this.mBlacklistRepository = mBlacklistRepository;
        this.mBlacklistMapper = mBlacklistMapper;
    }

    /**
     * Return a {@link List} of {@link MBlacklistDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MBlacklistDTO> findByCriteria(MBlacklistCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MBlacklist> specification = createSpecification(criteria);
        return mBlacklistMapper.toDto(mBlacklistRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MBlacklistDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MBlacklistDTO> findByCriteria(MBlacklistCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MBlacklist> specification = createSpecification(criteria);
        return mBlacklistRepository.findAll(specification, page)
            .map(mBlacklistMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MBlacklistCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MBlacklist> specification = createSpecification(criteria);
        return mBlacklistRepository.count(specification);
    }

    /**
     * Function to convert {@link MBlacklistCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MBlacklist> createSpecification(MBlacklistCriteria criteria) {
        Specification<MBlacklist> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MBlacklist_.id));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MBlacklist_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MBlacklist_.active));
            }
            if (criteria.getReportDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getReportDate(), MBlacklist_.reportDate));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), MBlacklist_.type));
            }
            if (criteria.getDateTrx() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateTrx(), MBlacklist_.dateTrx));
            }
            if (criteria.getDocumentNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentNo(), MBlacklist_.documentNo));
            }
            if (criteria.getDocumentAction() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentAction(), MBlacklist_.documentAction));
            }
            if (criteria.getDocumentStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentStatus(), MBlacklist_.documentStatus));
            }
            if (criteria.getApproved() != null) {
                specification = specification.and(buildSpecification(criteria.getApproved(), MBlacklist_.approved));
            }
            if (criteria.getProcessed() != null) {
                specification = specification.and(buildSpecification(criteria.getProcessed(), MBlacklist_.processed));
            }
            if (criteria.getAttachmentId() != null) {
                specification = specification.and(buildSpecification(criteria.getAttachmentId(),
                    root -> root.join(MBlacklist_.attachment, JoinType.LEFT).get(CAttachment_.id)));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MBlacklist_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getVendorId() != null) {
                specification = specification.and(buildSpecification(criteria.getVendorId(),
                    root -> root.join(MBlacklist_.vendor, JoinType.LEFT).get(CVendor_.id)));
            }
            if (criteria.getBusinessCategoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getBusinessCategoryId(),
                    root -> root.join(MBlacklist_.businessCategory, JoinType.LEFT).get(CBusinessCategory_.id)));
            }
            if (criteria.getSubBusinessCategoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getSubBusinessCategoryId(),
                    root -> root.join(MBlacklist_.subBusinessCategory, JoinType.LEFT).get(CBusinessCategory_.id)));
            }
        }
        return specification;
    }
}
