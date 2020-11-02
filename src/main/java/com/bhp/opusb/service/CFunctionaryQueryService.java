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

import com.bhp.opusb.domain.CFunctionary;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.CFunctionaryRepository;
import com.bhp.opusb.service.dto.CFunctionaryCriteria;
import com.bhp.opusb.service.dto.CFunctionaryDTO;
import com.bhp.opusb.service.mapper.CFunctionaryMapper;

/**
 * Service for executing complex queries for {@link CFunctionary} entities in the database.
 * The main input is a {@link CFunctionaryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CFunctionaryDTO} or a {@link Page} of {@link CFunctionaryDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CFunctionaryQueryService extends QueryService<CFunctionary> {

    private final Logger log = LoggerFactory.getLogger(CFunctionaryQueryService.class);

    private final CFunctionaryRepository cFunctionaryRepository;

    private final CFunctionaryMapper cFunctionaryMapper;

    public CFunctionaryQueryService(CFunctionaryRepository cFunctionaryRepository, CFunctionaryMapper cFunctionaryMapper) {
        this.cFunctionaryRepository = cFunctionaryRepository;
        this.cFunctionaryMapper = cFunctionaryMapper;
    }

    /**
     * Return a {@link List} of {@link CFunctionaryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CFunctionaryDTO> findByCriteria(CFunctionaryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CFunctionary> specification = createSpecification(criteria);
        return cFunctionaryMapper.toDto(cFunctionaryRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CFunctionaryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CFunctionaryDTO> findByCriteria(CFunctionaryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CFunctionary> specification = createSpecification(criteria);
        return cFunctionaryRepository.findAll(specification, page)
            .map(cFunctionaryMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CFunctionaryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CFunctionary> specification = createSpecification(criteria);
        return cFunctionaryRepository.count(specification);
    }

    /**
     * Function to convert {@link CFunctionaryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CFunctionary> createSpecification(CFunctionaryCriteria criteria) {
        Specification<CFunctionary> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CFunctionary_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CFunctionary_.name));
            }
            if (criteria.getPosition() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPosition(), CFunctionary_.position));
            }
            if (criteria.getPhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhone(), CFunctionary_.phone));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), CFunctionary_.email));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), CFunctionary_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), CFunctionary_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(CFunctionary_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getVendorId() != null) {
                specification = specification.and(buildSpecification(criteria.getVendorId(),
                    root -> root.join(CFunctionary_.vendor, JoinType.LEFT).get(CVendor_.id)));
            }
        }
        return specification;
    }
}
