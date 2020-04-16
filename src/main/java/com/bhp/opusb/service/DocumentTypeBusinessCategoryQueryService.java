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

import com.bhp.opusb.domain.DocumentTypeBusinessCategory;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.DocumentTypeBusinessCategoryRepository;
import com.bhp.opusb.service.dto.DocumentTypeBusinessCategoryCriteria;

/**
 * Service for executing complex queries for {@link DocumentTypeBusinessCategory} entities in the database.
 * The main input is a {@link DocumentTypeBusinessCategoryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DocumentTypeBusinessCategory} or a {@link Page} of {@link DocumentTypeBusinessCategory} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DocumentTypeBusinessCategoryQueryService extends QueryService<DocumentTypeBusinessCategory> {

    private final Logger log = LoggerFactory.getLogger(DocumentTypeBusinessCategoryQueryService.class);

    private final DocumentTypeBusinessCategoryRepository documentTypeBusinessCategoryRepository;

    public DocumentTypeBusinessCategoryQueryService(DocumentTypeBusinessCategoryRepository documentTypeBusinessCategoryRepository) {
        this.documentTypeBusinessCategoryRepository = documentTypeBusinessCategoryRepository;
    }

    /**
     * Return a {@link List} of {@link DocumentTypeBusinessCategory} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DocumentTypeBusinessCategory> findByCriteria(DocumentTypeBusinessCategoryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DocumentTypeBusinessCategory> specification = createSpecification(criteria);
        return documentTypeBusinessCategoryRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link DocumentTypeBusinessCategory} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DocumentTypeBusinessCategory> findByCriteria(DocumentTypeBusinessCategoryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DocumentTypeBusinessCategory> specification = createSpecification(criteria);
        return documentTypeBusinessCategoryRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DocumentTypeBusinessCategoryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DocumentTypeBusinessCategory> specification = createSpecification(criteria);
        return documentTypeBusinessCategoryRepository.count(specification);
    }

    /**
     * Function to convert {@link DocumentTypeBusinessCategoryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DocumentTypeBusinessCategory> createSpecification(DocumentTypeBusinessCategoryCriteria criteria) {
        Specification<DocumentTypeBusinessCategory> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DocumentTypeBusinessCategory_.id));
            }
            if (criteria.getMandatory() != null) {
                specification = specification.and(buildSpecification(criteria.getMandatory(), DocumentTypeBusinessCategory_.mandatory));
            }
            if (criteria.getAdditional() != null) {
                specification = specification.and(buildSpecification(criteria.getAdditional(), DocumentTypeBusinessCategory_.additional));
            }
            if (criteria.getDocumentTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getDocumentTypeId(),
                    root -> root.join(DocumentTypeBusinessCategory_.documentType, JoinType.LEFT).get(DocumentType_.id)));
            }
            if (criteria.getBusinessCategoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getBusinessCategoryId(),
                    root -> root.join(DocumentTypeBusinessCategory_.businessCategory, JoinType.LEFT).get(BusinessCategory_.id)));
            }
        }
        return specification;
    }
}
