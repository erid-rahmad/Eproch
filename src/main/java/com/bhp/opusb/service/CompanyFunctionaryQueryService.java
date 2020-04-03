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

import com.bhp.opusb.domain.CompanyFunctionary;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.CompanyFunctionaryRepository;
import com.bhp.opusb.service.dto.CompanyFunctionaryCriteria;
import com.bhp.opusb.service.dto.CompanyFunctionaryDTO;
import com.bhp.opusb.service.mapper.CompanyFunctionaryMapper;

/**
 * Service for executing complex queries for {@link CompanyFunctionary} entities in the database.
 * The main input is a {@link CompanyFunctionaryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CompanyFunctionaryDTO} or a {@link Page} of {@link CompanyFunctionaryDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CompanyFunctionaryQueryService extends QueryService<CompanyFunctionary> {

    private final Logger log = LoggerFactory.getLogger(CompanyFunctionaryQueryService.class);

    private final CompanyFunctionaryRepository companyFunctionaryRepository;

    private final CompanyFunctionaryMapper companyFunctionaryMapper;

    public CompanyFunctionaryQueryService(CompanyFunctionaryRepository companyFunctionaryRepository, CompanyFunctionaryMapper companyFunctionaryMapper) {
        this.companyFunctionaryRepository = companyFunctionaryRepository;
        this.companyFunctionaryMapper = companyFunctionaryMapper;
    }

    /**
     * Return a {@link List} of {@link CompanyFunctionaryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CompanyFunctionaryDTO> findByCriteria(CompanyFunctionaryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CompanyFunctionary> specification = createSpecification(criteria);
        return companyFunctionaryMapper.toDto(companyFunctionaryRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CompanyFunctionaryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CompanyFunctionaryDTO> findByCriteria(CompanyFunctionaryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CompanyFunctionary> specification = createSpecification(criteria);
        return companyFunctionaryRepository.findAll(specification, page)
            .map(companyFunctionaryMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CompanyFunctionaryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CompanyFunctionary> specification = createSpecification(criteria);
        return companyFunctionaryRepository.count(specification);
    }

    /**
     * Function to convert {@link CompanyFunctionaryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CompanyFunctionary> createSpecification(CompanyFunctionaryCriteria criteria) {
        Specification<CompanyFunctionary> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CompanyFunctionary_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CompanyFunctionary_.name));
            }
            if (criteria.getPosition() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPosition(), CompanyFunctionary_.position));
            }
            if (criteria.getPhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhone(), CompanyFunctionary_.phone));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), CompanyFunctionary_.email));
            }
            if (criteria.getVendorId() != null) {
                specification = specification.and(buildSpecification(criteria.getVendorId(),
                    root -> root.join(CompanyFunctionary_.vendor, JoinType.LEFT).get(Vendor_.id)));
            }
        }
        return specification;
    }
}
