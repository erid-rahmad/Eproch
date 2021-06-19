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

import com.bhp.opusb.domain.MContractDocument;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MContractDocumentRepository;
import com.bhp.opusb.service.dto.MContractDocumentCriteria;
import com.bhp.opusb.service.dto.MContractDocumentDTO;
import com.bhp.opusb.service.mapper.MContractDocumentMapper;

/**
 * Service for executing complex queries for {@link MContractDocument} entities in the database.
 * The main input is a {@link MContractDocumentCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MContractDocumentDTO} or a {@link Page} of {@link MContractDocumentDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MContractDocumentQueryService extends QueryService<MContractDocument> {

    private final Logger log = LoggerFactory.getLogger(MContractDocumentQueryService.class);

    private final MContractDocumentRepository mContractDocumentRepository;

    private final MContractDocumentMapper mContractDocumentMapper;

    public MContractDocumentQueryService(MContractDocumentRepository mContractDocumentRepository, MContractDocumentMapper mContractDocumentMapper) {
        this.mContractDocumentRepository = mContractDocumentRepository;
        this.mContractDocumentMapper = mContractDocumentMapper;
    }

    /**
     * Return a {@link List} of {@link MContractDocumentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MContractDocumentDTO> findByCriteria(MContractDocumentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MContractDocument> specification = createSpecification(criteria);
        return mContractDocumentMapper.toDto(mContractDocumentRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MContractDocumentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MContractDocumentDTO> findByCriteria(MContractDocumentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MContractDocument> specification = createSpecification(criteria);
        return mContractDocumentRepository.findAll(specification, page)
            .map(mContractDocumentMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MContractDocumentCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MContractDocument> specification = createSpecification(criteria);
        return mContractDocumentRepository.count(specification);
    }

    /**
     * Function to convert {@link MContractDocumentCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MContractDocument> createSpecification(MContractDocumentCriteria criteria) {
        Specification<MContractDocument> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MContractDocument_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), MContractDocument_.name));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MContractDocument_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MContractDocument_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MContractDocument_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getContractId() != null) {
                specification = specification.and(buildSpecification(criteria.getContractId(),
                    root -> root.join(MContractDocument_.contract, JoinType.LEFT).get(MContract_.id)));
            }
            if (criteria.getAttachmentId() != null) {
                specification = specification.and(buildSpecification(criteria.getAttachmentId(),
                    root -> root.join(MContractDocument_.attachment, JoinType.LEFT).get(CAttachment_.id)));
            }
        }
        return specification;
    }
}
