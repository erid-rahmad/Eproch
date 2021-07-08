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

import com.bhp.opusb.domain.MWarningLetter;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MWarningLetterRepository;
import com.bhp.opusb.service.dto.MWarningLetterCriteria;
import com.bhp.opusb.service.dto.MWarningLetterDTO;
import com.bhp.opusb.service.mapper.MWarningLetterMapper;

/**
 * Service for executing complex queries for {@link MWarningLetter} entities in the database.
 * The main input is a {@link MWarningLetterCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MWarningLetterDTO} or a {@link Page} of {@link MWarningLetterDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MWarningLetterQueryService extends QueryService<MWarningLetter> {

    private final Logger log = LoggerFactory.getLogger(MWarningLetterQueryService.class);

    private final MWarningLetterRepository mWarningLetterRepository;

    private final MWarningLetterMapper mWarningLetterMapper;

    public MWarningLetterQueryService(MWarningLetterRepository mWarningLetterRepository, MWarningLetterMapper mWarningLetterMapper) {
        this.mWarningLetterRepository = mWarningLetterRepository;
        this.mWarningLetterMapper = mWarningLetterMapper;
    }

    /**
     * Return a {@link List} of {@link MWarningLetterDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MWarningLetterDTO> findByCriteria(MWarningLetterCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MWarningLetter> specification = createSpecification(criteria);
        return mWarningLetterMapper.toDto(mWarningLetterRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MWarningLetterDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MWarningLetterDTO> findByCriteria(MWarningLetterCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MWarningLetter> specification = createSpecification(criteria);
        return mWarningLetterRepository.findAll(specification, page)
            .map(mWarningLetterMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MWarningLetterCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MWarningLetter> specification = createSpecification(criteria);
        return mWarningLetterRepository.count(specification);
    }

    /**
     * Function to convert {@link MWarningLetterCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MWarningLetter> createSpecification(MWarningLetterCriteria criteria) {
        Specification<MWarningLetter> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MWarningLetter_.id));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MWarningLetter_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MWarningLetter_.active));
            }
            if (criteria.getReportDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getReportDate(), MWarningLetter_.reportDate));
            }
            if (criteria.getWarningType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWarningType(), MWarningLetter_.warningType));
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartDate(), MWarningLetter_.startDate));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), MWarningLetter_.endDate));
            }
            if (criteria.getLocation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLocation(), MWarningLetter_.location));
            }
            if (criteria.getDateTrx() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateTrx(), MWarningLetter_.dateTrx));
            }
            if (criteria.getDocumentNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentNo(), MWarningLetter_.documentNo));
            }
            if (criteria.getDocumentAction() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentAction(), MWarningLetter_.documentAction));
            }
            if (criteria.getDocumentStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentStatus(), MWarningLetter_.documentStatus));
            }
            if (criteria.getApproved() != null) {
                specification = specification.and(buildSpecification(criteria.getApproved(), MWarningLetter_.approved));
            }
            if (criteria.getProcessed() != null) {
                specification = specification.and(buildSpecification(criteria.getProcessed(), MWarningLetter_.processed));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MWarningLetter_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getVendorId() != null) {
                specification = specification.and(buildSpecification(criteria.getVendorId(),
                    root -> root.join(MWarningLetter_.vendor, JoinType.LEFT).get(CVendor_.id)));
            }
        }
        return specification;
    }
}
