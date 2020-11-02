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

import com.bhp.opusb.domain.CAttachment;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.CAttachmentRepository;
import com.bhp.opusb.service.dto.CAttachmentCriteria;
import com.bhp.opusb.service.dto.CAttachmentDTO;
import com.bhp.opusb.service.mapper.CAttachmentMapper;

/**
 * Service for executing complex queries for {@link CAttachment} entities in the database.
 * The main input is a {@link CAttachmentCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CAttachmentDTO} or a {@link Page} of {@link CAttachmentDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CAttachmentQueryService extends QueryService<CAttachment> {

    private final Logger log = LoggerFactory.getLogger(CAttachmentQueryService.class);

    private final CAttachmentRepository cAttachmentRepository;

    private final CAttachmentMapper cAttachmentMapper;

    public CAttachmentQueryService(CAttachmentRepository cAttachmentRepository, CAttachmentMapper cAttachmentMapper) {
        this.cAttachmentRepository = cAttachmentRepository;
        this.cAttachmentMapper = cAttachmentMapper;
    }

    /**
     * Return a {@link List} of {@link CAttachmentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CAttachmentDTO> findByCriteria(CAttachmentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CAttachment> specification = createSpecification(criteria);
        return cAttachmentMapper.toDto(cAttachmentRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CAttachmentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CAttachmentDTO> findByCriteria(CAttachmentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CAttachment> specification = createSpecification(criteria);
        return cAttachmentRepository.findAll(specification, page)
            .map(cAttachmentMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CAttachmentCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CAttachment> specification = createSpecification(criteria);
        return cAttachmentRepository.count(specification);
    }

    /**
     * Function to convert {@link CAttachmentCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CAttachment> createSpecification(CAttachmentCriteria criteria) {
        Specification<CAttachment> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CAttachment_.id));
            }
            if (criteria.getFileName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFileName(), CAttachment_.fileName));
            }
            if (criteria.getMimeType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMimeType(), CAttachment_.mimeType));
            }
            if (criteria.getDocumentType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentType(), CAttachment_.documentType));
            }
            if (criteria.getUploadDir() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUploadDir(), CAttachment_.uploadDir));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), CAttachment_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), CAttachment_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(CAttachment_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
        }
        return specification;
    }
}
