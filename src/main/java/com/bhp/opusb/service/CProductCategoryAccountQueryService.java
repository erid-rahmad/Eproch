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

import com.bhp.opusb.domain.CProductCategoryAccount;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.CProductCategoryAccountRepository;
import com.bhp.opusb.service.dto.CProductCategoryAccountCriteria;
import com.bhp.opusb.service.dto.CProductCategoryAccountDTO;
import com.bhp.opusb.service.mapper.CProductCategoryAccountMapper;

/**
 * Service for executing complex queries for {@link CProductCategoryAccount} entities in the database.
 * The main input is a {@link CProductCategoryAccountCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CProductCategoryAccountDTO} or a {@link Page} of {@link CProductCategoryAccountDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CProductCategoryAccountQueryService extends QueryService<CProductCategoryAccount> {

    private final Logger log = LoggerFactory.getLogger(CProductCategoryAccountQueryService.class);

    private final CProductCategoryAccountRepository cProductCategoryAccountRepository;

    private final CProductCategoryAccountMapper cProductCategoryAccountMapper;

    public CProductCategoryAccountQueryService(CProductCategoryAccountRepository cProductCategoryAccountRepository, CProductCategoryAccountMapper cProductCategoryAccountMapper) {
        this.cProductCategoryAccountRepository = cProductCategoryAccountRepository;
        this.cProductCategoryAccountMapper = cProductCategoryAccountMapper;
    }

    /**
     * Return a {@link List} of {@link CProductCategoryAccountDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CProductCategoryAccountDTO> findByCriteria(CProductCategoryAccountCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CProductCategoryAccount> specification = createSpecification(criteria);
        return cProductCategoryAccountMapper.toDto(cProductCategoryAccountRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CProductCategoryAccountDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CProductCategoryAccountDTO> findByCriteria(CProductCategoryAccountCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CProductCategoryAccount> specification = createSpecification(criteria);
        return cProductCategoryAccountRepository.findAll(specification, page)
            .map(cProductCategoryAccountMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CProductCategoryAccountCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CProductCategoryAccount> specification = createSpecification(criteria);
        return cProductCategoryAccountRepository.count(specification);
    }

    /**
     * Function to convert {@link CProductCategoryAccountCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CProductCategoryAccount> createSpecification(CProductCategoryAccountCriteria criteria) {
        Specification<CProductCategoryAccount> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CProductCategoryAccount_.id));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), CProductCategoryAccount_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), CProductCategoryAccount_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(CProductCategoryAccount_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getAssetAcctId() != null) {
                specification = specification.and(buildSpecification(criteria.getAssetAcctId(),
                    root -> root.join(CProductCategoryAccount_.assetAcct, JoinType.LEFT).get(CElementValue_.id)));
            }
            if (criteria.getExpenseAcctId() != null) {
                specification = specification.and(buildSpecification(criteria.getExpenseAcctId(),
                    root -> root.join(CProductCategoryAccount_.expenseAcct, JoinType.LEFT).get(CElementValue_.id)));
            }
            if (criteria.getProductCategoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductCategoryId(),
                    root -> root.join(CProductCategoryAccount_.productCategory, JoinType.LEFT).get(CProductCategory_.id)));
            }
        }
        return specification;
    }
}
