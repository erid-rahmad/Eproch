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

import com.bhp.opusb.domain.SupportingDocument;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.SupportingDocumentRepository;
import com.bhp.opusb.service.dto.SupportingDocumentCriteria;
import com.bhp.opusb.service.dto.SupportingDocumentDTO;
import com.bhp.opusb.service.mapper.SupportingDocumentMapper;

/**
 * Service for executing complex queries for {@link SupportingDocument} entities in the database.
 * The main input is a {@link SupportingDocumentCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SupportingDocumentDTO} or a {@link Page} of {@link SupportingDocumentDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SupportingDocumentQueryService extends QueryService<SupportingDocument> {

    private final Logger log = LoggerFactory.getLogger(SupportingDocumentQueryService.class);

    private final SupportingDocumentRepository supportingDocumentRepository;

    private final SupportingDocumentMapper supportingDocumentMapper;

    public SupportingDocumentQueryService(SupportingDocumentRepository supportingDocumentRepository, SupportingDocumentMapper supportingDocumentMapper) {
        this.supportingDocumentRepository = supportingDocumentRepository;
        this.supportingDocumentMapper = supportingDocumentMapper;
    }

    /**
     * Return a {@link List} of {@link SupportingDocumentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SupportingDocumentDTO> findByCriteria(SupportingDocumentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SupportingDocument> specification = createSpecification(criteria);
        return supportingDocumentMapper.toDto(supportingDocumentRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SupportingDocumentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SupportingDocumentDTO> findByCriteria(SupportingDocumentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SupportingDocument> specification = createSpecification(criteria);
        return supportingDocumentRepository.findAll(specification, page)
            .map(supportingDocumentMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SupportingDocumentCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SupportingDocument> specification = createSpecification(criteria);
        return supportingDocumentRepository.count(specification);
    }

    /**
     * Function to convert {@link SupportingDocumentCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SupportingDocument> createSpecification(SupportingDocumentCriteria criteria) {
        Specification<SupportingDocument> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SupportingDocument_.id));
            }
            if (criteria.getDocumentNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentNo(), SupportingDocument_.documentNo));
            }
            if (criteria.getExpirationDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExpirationDate(), SupportingDocument_.expirationDate));
            }
            if (criteria.getTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getTypeId(),
                    root -> root.join(SupportingDocument_.type, JoinType.LEFT).get(DocumentType_.id)));
            }
            if (criteria.getVendorId() != null) {
                specification = specification.and(buildSpecification(criteria.getVendorId(),
                    root -> root.join(SupportingDocument_.vendor, JoinType.LEFT).get(Vendor_.id)));
            }
        }
        return specification;
    }
}
