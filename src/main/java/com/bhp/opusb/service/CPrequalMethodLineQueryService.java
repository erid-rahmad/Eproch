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

import com.bhp.opusb.domain.CPrequalMethodLine;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.CPrequalMethodLineRepository;
import com.bhp.opusb.service.dto.CPrequalMethodLineCriteria;
import com.bhp.opusb.service.dto.CPrequalMethodLineDTO;
import com.bhp.opusb.service.mapper.CPrequalMethodLineMapper;

/**
 * Service for executing complex queries for {@link CPrequalMethodLine} entities in the database.
 * The main input is a {@link CPrequalMethodLineCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CPrequalMethodLineDTO} or a {@link Page} of {@link CPrequalMethodLineDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CPrequalMethodLineQueryService extends QueryService<CPrequalMethodLine> {

    private final Logger log = LoggerFactory.getLogger(CPrequalMethodLineQueryService.class);

    private final CPrequalMethodLineRepository cPrequalMethodLineRepository;

    private final CPrequalMethodLineMapper cPrequalMethodLineMapper;

    public CPrequalMethodLineQueryService(CPrequalMethodLineRepository cPrequalMethodLineRepository, CPrequalMethodLineMapper cPrequalMethodLineMapper) {
        this.cPrequalMethodLineRepository = cPrequalMethodLineRepository;
        this.cPrequalMethodLineMapper = cPrequalMethodLineMapper;
    }

    /**
     * Return a {@link List} of {@link CPrequalMethodLineDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CPrequalMethodLineDTO> findByCriteria(CPrequalMethodLineCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CPrequalMethodLine> specification = createSpecification(criteria);
        return cPrequalMethodLineMapper.toDto(cPrequalMethodLineRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CPrequalMethodLineDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CPrequalMethodLineDTO> findByCriteria(CPrequalMethodLineCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CPrequalMethodLine> specification = createSpecification(criteria);
        return cPrequalMethodLineRepository.findAll(specification, page)
            .map(cPrequalMethodLineMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CPrequalMethodLineCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CPrequalMethodLine> specification = createSpecification(criteria);
        return cPrequalMethodLineRepository.count(specification);
    }

    /**
     * Function to convert {@link CPrequalMethodLineCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CPrequalMethodLine> createSpecification(CPrequalMethodLineCriteria criteria) {
        Specification<CPrequalMethodLine> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CPrequalMethodLine_.id));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), CPrequalMethodLine_.type));
            }
            if (criteria.getWeight() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWeight(), CPrequalMethodLine_.weight));
            }
            if (criteria.getPassingGrade() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPassingGrade(), CPrequalMethodLine_.passingGrade));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), CPrequalMethodLine_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), CPrequalMethodLine_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(CPrequalMethodLine_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getPrequalMethodId() != null) {
                specification = specification.and(buildSpecification(criteria.getPrequalMethodId(),
                    root -> root.join(CPrequalMethodLine_.prequalMethod, JoinType.LEFT).get(CPrequalificationMethod_.id)));
            }
        }
        return specification;
    }
}
