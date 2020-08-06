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

import com.bhp.opusb.domain.CVendorBankAcct;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.CVendorBankAcctRepository;
import com.bhp.opusb.service.dto.CVendorBankAcctCriteria;
import com.bhp.opusb.service.dto.CVendorBankAcctDTO;
import com.bhp.opusb.service.mapper.CVendorBankAcctMapper;

/**
 * Service for executing complex queries for {@link CVendorBankAcct} entities in the database.
 * The main input is a {@link CVendorBankAcctCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CVendorBankAcctDTO} or a {@link Page} of {@link CVendorBankAcctDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CVendorBankAcctQueryService extends QueryService<CVendorBankAcct> {

    private final Logger log = LoggerFactory.getLogger(CVendorBankAcctQueryService.class);

    private final CVendorBankAcctRepository cVendorBankAcctRepository;

    private final CVendorBankAcctMapper cVendorBankAcctMapper;

    public CVendorBankAcctQueryService(CVendorBankAcctRepository cVendorBankAcctRepository, CVendorBankAcctMapper cVendorBankAcctMapper) {
        this.cVendorBankAcctRepository = cVendorBankAcctRepository;
        this.cVendorBankAcctMapper = cVendorBankAcctMapper;
    }

    /**
     * Return a {@link List} of {@link CVendorBankAcctDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CVendorBankAcctDTO> findByCriteria(CVendorBankAcctCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CVendorBankAcct> specification = createSpecification(criteria);
        return cVendorBankAcctMapper.toDto(cVendorBankAcctRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CVendorBankAcctDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CVendorBankAcctDTO> findByCriteria(CVendorBankAcctCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CVendorBankAcct> specification = createSpecification(criteria);
        return cVendorBankAcctRepository.findAll(specification, page)
            .map(cVendorBankAcctMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CVendorBankAcctCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CVendorBankAcct> specification = createSpecification(criteria);
        return cVendorBankAcctRepository.count(specification);
    }

    /**
     * Function to convert {@link CVendorBankAcctCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CVendorBankAcct> createSpecification(CVendorBankAcctCriteria criteria) {
        Specification<CVendorBankAcct> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CVendorBankAcct_.id));
            }
            if (criteria.getAccountNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccountNo(), CVendorBankAcct_.accountNo));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CVendorBankAcct_.name));
            }
            if (criteria.getBranch() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBranch(), CVendorBankAcct_.branch));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), CVendorBankAcct_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), CVendorBankAcct_.active));
            }
            if (criteria.getVendorId() != null) {
                specification = specification.and(buildSpecification(criteria.getVendorId(),
                    root -> root.join(CVendorBankAcct_.vendor, JoinType.LEFT).get(CVendor_.id)));
            }
            if (criteria.getBankId() != null) {
                specification = specification.and(buildSpecification(criteria.getBankId(),
                    root -> root.join(CVendorBankAcct_.bank, JoinType.LEFT).get(CBank_.id)));
            }
            if (criteria.getCurrencyId() != null) {
                specification = specification.and(buildSpecification(criteria.getCurrencyId(),
                    root -> root.join(CVendorBankAcct_.currency, JoinType.LEFT).get(CCurrency_.id)));
            }
            if (criteria.getFileId() != null) {
                specification = specification.and(buildSpecification(criteria.getFileId(),
                    root -> root.join(CVendorBankAcct_.file, JoinType.LEFT).get(CAttachment_.id)));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(CVendorBankAcct_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
        }
        return specification;
    }
}
