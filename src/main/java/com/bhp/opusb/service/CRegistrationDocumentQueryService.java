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

import com.bhp.opusb.domain.CRegistrationDocument;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.CRegistrationDocumentRepository;
import com.bhp.opusb.service.dto.CRegistrationDocumentCriteria;
import com.bhp.opusb.service.dto.CRegistrationDocumentDTO;
import com.bhp.opusb.service.mapper.CRegistrationDocumentMapper;

/**
 * Service for executing complex queries for {@link CRegistrationDocument} entities in the database.
 * The main input is a {@link CRegistrationDocumentCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CRegistrationDocumentDTO} or a {@link Page} of {@link CRegistrationDocumentDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CRegistrationDocumentQueryService extends QueryService<CRegistrationDocument> {

    private final Logger log = LoggerFactory.getLogger(CRegistrationDocumentQueryService.class);

    private final CRegistrationDocumentRepository cRegistrationDocumentRepository;

    private final CRegistrationDocumentMapper cRegistrationDocumentMapper;

    public CRegistrationDocumentQueryService(CRegistrationDocumentRepository cRegistrationDocumentRepository, CRegistrationDocumentMapper cRegistrationDocumentMapper) {
        this.cRegistrationDocumentRepository = cRegistrationDocumentRepository;
        this.cRegistrationDocumentMapper = cRegistrationDocumentMapper;
    }

    /**
     * Return a {@link List} of {@link CRegistrationDocumentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CRegistrationDocumentDTO> findByCriteria(CRegistrationDocumentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CRegistrationDocument> specification = createSpecification(criteria);
        return cRegistrationDocumentMapper.toDto(cRegistrationDocumentRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CRegistrationDocumentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CRegistrationDocumentDTO> findByCriteria(CRegistrationDocumentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CRegistrationDocument> specification = createSpecification(criteria);
        return cRegistrationDocumentRepository.findAll(specification, page)
            .map(cRegistrationDocumentMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CRegistrationDocumentCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CRegistrationDocument> specification = createSpecification(criteria);
        return cRegistrationDocumentRepository.count(specification);
    }

    /**
     * Function to convert {@link CRegistrationDocumentCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CRegistrationDocument> createSpecification(CRegistrationDocumentCriteria criteria) {
        Specification<CRegistrationDocument> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CRegistrationDocument_.id));
            }
            if (criteria.getDocumentNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentNo(), CRegistrationDocument_.documentNo));
            }
            if (criteria.getExpirationDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExpirationDate(), CRegistrationDocument_.expirationDate));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), CRegistrationDocument_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), CRegistrationDocument_.active));
            }
            if (criteria.getTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getTypeId(),
                    root -> root.join(CRegistrationDocument_.type, JoinType.LEFT).get(CRegistrationDocType_.id)));
            }
            if (criteria.getFileId() != null) {
                specification = specification.and(buildSpecification(criteria.getFileId(),
                    root -> root.join(CRegistrationDocument_.file, JoinType.LEFT).get(CAttachment_.id)));
            }
            if (criteria.getVendorId() != null) {
                specification = specification.and(buildSpecification(criteria.getVendorId(),
                    root -> root.join(CRegistrationDocument_.vendor, JoinType.LEFT).get(CVendor_.id)));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(CRegistrationDocument_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
        }
        return specification;
    }
}
