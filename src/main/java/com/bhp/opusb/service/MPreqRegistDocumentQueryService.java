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

import com.bhp.opusb.domain.MPreqRegistDocument;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MPreqRegistDocumentRepository;
import com.bhp.opusb.service.dto.MPreqRegistDocumentCriteria;
import com.bhp.opusb.service.dto.MPreqRegistDocumentDTO;
import com.bhp.opusb.service.mapper.MPreqRegistDocumentMapper;

/**
 * Service for executing complex queries for {@link MPreqRegistDocument} entities in the database.
 * The main input is a {@link MPreqRegistDocumentCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MPreqRegistDocumentDTO} or a {@link Page} of {@link MPreqRegistDocumentDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MPreqRegistDocumentQueryService extends QueryService<MPreqRegistDocument> {

    private final Logger log = LoggerFactory.getLogger(MPreqRegistDocumentQueryService.class);

    private final MPreqRegistDocumentRepository mPreqRegistDocumentRepository;

    private final MPreqRegistDocumentMapper mPreqRegistDocumentMapper;

    public MPreqRegistDocumentQueryService(MPreqRegistDocumentRepository mPreqRegistDocumentRepository, MPreqRegistDocumentMapper mPreqRegistDocumentMapper) {
        this.mPreqRegistDocumentRepository = mPreqRegistDocumentRepository;
        this.mPreqRegistDocumentMapper = mPreqRegistDocumentMapper;
    }

    /**
     * Return a {@link List} of {@link MPreqRegistDocumentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MPreqRegistDocumentDTO> findByCriteria(MPreqRegistDocumentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MPreqRegistDocument> specification = createSpecification(criteria);
        return mPreqRegistDocumentMapper.toDto(mPreqRegistDocumentRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MPreqRegistDocumentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MPreqRegistDocumentDTO> findByCriteria(MPreqRegistDocumentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MPreqRegistDocument> specification = createSpecification(criteria);
        return mPreqRegistDocumentRepository.findAll(specification, page)
            .map(mPreqRegistDocumentMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MPreqRegistDocumentCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MPreqRegistDocument> specification = createSpecification(criteria);
        return mPreqRegistDocumentRepository.count(specification);
    }

    /**
     * Function to convert {@link MPreqRegistDocumentCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MPreqRegistDocument> createSpecification(MPreqRegistDocumentCriteria criteria) {
        Specification<MPreqRegistDocument> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MPreqRegistDocument_.id));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MPreqRegistDocument_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MPreqRegistDocument_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MPreqRegistDocument_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getRegistrationId() != null) {
                specification = specification.and(buildSpecification(criteria.getRegistrationId(),
                    root -> root.join(MPreqRegistDocument_.registration, JoinType.LEFT).get(MPrequalRegistration_.id)));
            }
            if (criteria.getSiupDocumentId() != null) {
                specification = specification.and(buildSpecification(criteria.getSiupDocumentId(),
                    root -> root.join(MPreqRegistDocument_.siupDocument, JoinType.LEFT).get(CAttachment_.id)));
            }
            if (criteria.getSpdaDocumentId() != null) {
                specification = specification.and(buildSpecification(criteria.getSpdaDocumentId(),
                    root -> root.join(MPreqRegistDocument_.spdaDocument, JoinType.LEFT).get(CAttachment_.id)));
            }
        }
        return specification;
    }
}
