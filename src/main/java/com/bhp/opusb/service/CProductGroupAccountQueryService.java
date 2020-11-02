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

import com.bhp.opusb.domain.CProductGroupAccount;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.CProductGroupAccountRepository;
import com.bhp.opusb.service.dto.CProductGroupAccountCriteria;
import com.bhp.opusb.service.dto.CProductGroupAccountDTO;
import com.bhp.opusb.service.mapper.CProductGroupAccountMapper;

/**
 * Service for executing complex queries for {@link CProductGroupAccount} entities in the database.
 * The main input is a {@link CProductGroupAccountCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CProductGroupAccountDTO} or a {@link Page} of {@link CProductGroupAccountDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CProductGroupAccountQueryService extends QueryService<CProductGroupAccount> {

    private final Logger log = LoggerFactory.getLogger(CProductGroupAccountQueryService.class);

    private final CProductGroupAccountRepository cProductGroupAccountRepository;

    private final CProductGroupAccountMapper cProductGroupAccountMapper;

    public CProductGroupAccountQueryService(CProductGroupAccountRepository cProductGroupAccountRepository, CProductGroupAccountMapper cProductGroupAccountMapper) {
        this.cProductGroupAccountRepository = cProductGroupAccountRepository;
        this.cProductGroupAccountMapper = cProductGroupAccountMapper;
    }

    /**
     * Return a {@link List} of {@link CProductGroupAccountDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CProductGroupAccountDTO> findByCriteria(CProductGroupAccountCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CProductGroupAccount> specification = createSpecification(criteria);
        return cProductGroupAccountMapper.toDto(cProductGroupAccountRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CProductGroupAccountDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CProductGroupAccountDTO> findByCriteria(CProductGroupAccountCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CProductGroupAccount> specification = createSpecification(criteria);
        return cProductGroupAccountRepository.findAll(specification, page)
            .map(cProductGroupAccountMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CProductGroupAccountCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CProductGroupAccount> specification = createSpecification(criteria);
        return cProductGroupAccountRepository.count(specification);
    }

    /**
     * Function to convert {@link CProductGroupAccountCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CProductGroupAccount> createSpecification(CProductGroupAccountCriteria criteria) {
        Specification<CProductGroupAccount> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CProductGroupAccount_.id));
            }
            if (criteria.getDepreciation() != null) {
                specification = specification.and(buildSpecification(criteria.getDepreciation(), CProductGroupAccount_.depreciation));
            }
            if (criteria.getLifeYear() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLifeYear(), CProductGroupAccount_.lifeYear));
            }
            if (criteria.getLifeMonth() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLifeMonth(), CProductGroupAccount_.lifeMonth));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), CProductGroupAccount_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), CProductGroupAccount_.active));
            }
            if (criteria.getAssetAccountId() != null) {
                specification = specification.and(buildSpecification(criteria.getAssetAccountId(),
                    root -> root.join(CProductGroupAccount_.assetAccount, JoinType.LEFT).get(CElementValue_.id)));
            }
            if (criteria.getDepreciationAccountId() != null) {
                specification = specification.and(buildSpecification(criteria.getDepreciationAccountId(),
                    root -> root.join(CProductGroupAccount_.depreciationAccount, JoinType.LEFT).get(CElementValue_.id)));
            }
            if (criteria.getProductGroupId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductGroupId(),
                    root -> root.join(CProductGroupAccount_.productGroup, JoinType.LEFT).get(CProductGroup_.id)));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(CProductGroupAccount_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
        }
        return specification;
    }
}
