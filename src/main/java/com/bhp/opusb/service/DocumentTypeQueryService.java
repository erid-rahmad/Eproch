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

import com.bhp.opusb.domain.DocumentType;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.DocumentTypeRepository;
import com.bhp.opusb.service.dto.DocumentTypeCriteria;
import com.bhp.opusb.service.dto.DocumentTypeDTO;
import com.bhp.opusb.service.mapper.DocumentTypeMapper;

/**
 * Service for executing complex queries for {@link DocumentType} entities in the database.
 * The main input is a {@link DocumentTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DocumentTypeDTO} or a {@link Page} of {@link DocumentTypeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DocumentTypeQueryService extends QueryService<DocumentType> {

    private final Logger log = LoggerFactory.getLogger(DocumentTypeQueryService.class);

    private final DocumentTypeRepository documentTypeRepository;

    private final DocumentTypeMapper documentTypeMapper;

    public DocumentTypeQueryService(DocumentTypeRepository documentTypeRepository, DocumentTypeMapper documentTypeMapper) {
        this.documentTypeRepository = documentTypeRepository;
        this.documentTypeMapper = documentTypeMapper;
    }

    /**
     * Return a {@link List} of {@link DocumentTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DocumentTypeDTO> findByCriteria(DocumentTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DocumentType> specification = createSpecification(criteria);
        return documentTypeMapper.toDto(documentTypeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DocumentTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DocumentTypeDTO> findByCriteria(DocumentTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DocumentType> specification = createSpecification(criteria);
        return documentTypeRepository.findAll(specification, page)
            .map(documentTypeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DocumentTypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DocumentType> specification = createSpecification(criteria);
        return documentTypeRepository.count(specification);
    }

    /**
     * Function to convert {@link DocumentTypeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DocumentType> createSpecification(DocumentTypeCriteria criteria) {
        Specification<DocumentType> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DocumentType_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), DocumentType_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), DocumentType_.description));
            }
            if (criteria.getHasExpirationDate() != null) {
                specification = specification.and(buildSpecification(criteria.getHasExpirationDate(), DocumentType_.hasExpirationDate));
            }
            if (criteria.getMandatoryBusinessCategories() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMandatoryBusinessCategories(), DocumentType_.mandatoryBusinessCategories));
            }
            if (criteria.getAdditionalBusinessCategories() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAdditionalBusinessCategories(), DocumentType_.additionalBusinessCategories));
            }
            if (criteria.getMandatoryForCompany() != null) {
                specification = specification.and(buildSpecification(criteria.getMandatoryForCompany(), DocumentType_.mandatoryForCompany));
            }
            if (criteria.getMandatoryForProfessional() != null) {
                specification = specification.and(buildSpecification(criteria.getMandatoryForProfessional(), DocumentType_.mandatoryForProfessional));
            }
            if (criteria.getAdditionalForCompany() != null) {
                specification = specification.and(buildSpecification(criteria.getAdditionalForCompany(), DocumentType_.additionalForCompany));
            }
            if (criteria.getAdditionalForProfessional() != null) {
                specification = specification.and(buildSpecification(criteria.getAdditionalForProfessional(), DocumentType_.additionalForProfessional));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), DocumentType_.active));
            }
            if (criteria.getDocumentTypeBusinessCategoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getDocumentTypeBusinessCategoryId(),
                    root -> root.join(DocumentType_.documentTypeBusinessCategories, JoinType.LEFT).get(DocumentTypeBusinessCategory_.id)));
            }
        }
        return specification;
    }
}
