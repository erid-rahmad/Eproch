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

import com.bhp.opusb.domain.COrganizationInfo;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.COrganizationInfoRepository;
import com.bhp.opusb.service.dto.COrganizationInfoCriteria;
import com.bhp.opusb.service.dto.COrganizationInfoDTO;
import com.bhp.opusb.service.mapper.COrganizationInfoMapper;

/**
 * Service for executing complex queries for {@link COrganizationInfo} entities in the database.
 * The main input is a {@link COrganizationInfoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link COrganizationInfoDTO} or a {@link Page} of {@link COrganizationInfoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class COrganizationInfoQueryService extends QueryService<COrganizationInfo> {

    private final Logger log = LoggerFactory.getLogger(COrganizationInfoQueryService.class);

    private final COrganizationInfoRepository cOrganizationInfoRepository;

    private final COrganizationInfoMapper cOrganizationInfoMapper;

    public COrganizationInfoQueryService(COrganizationInfoRepository cOrganizationInfoRepository, COrganizationInfoMapper cOrganizationInfoMapper) {
        this.cOrganizationInfoRepository = cOrganizationInfoRepository;
        this.cOrganizationInfoMapper = cOrganizationInfoMapper;
    }

    /**
     * Return a {@link List} of {@link COrganizationInfoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<COrganizationInfoDTO> findByCriteria(COrganizationInfoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<COrganizationInfo> specification = createSpecification(criteria);
        return cOrganizationInfoMapper.toDto(cOrganizationInfoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link COrganizationInfoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<COrganizationInfoDTO> findByCriteria(COrganizationInfoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<COrganizationInfo> specification = createSpecification(criteria);
        return cOrganizationInfoRepository.findAll(specification, page)
            .map(cOrganizationInfoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(COrganizationInfoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<COrganizationInfo> specification = createSpecification(criteria);
        return cOrganizationInfoRepository.count(specification);
    }

    /**
     * Function to convert {@link COrganizationInfoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<COrganizationInfo> createSpecification(COrganizationInfoCriteria criteria) {
        Specification<COrganizationInfo> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), COrganizationInfo_.id));
            }
            if (criteria.getAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress(), COrganizationInfo_.address));
            }
            if (criteria.getTaxId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTaxId(), COrganizationInfo_.taxId));
            }
            if (criteria.getBankAccount() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBankAccount(), COrganizationInfo_.bankAccount));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), COrganizationInfo_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), COrganizationInfo_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(COrganizationInfo_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getParentOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getParentOrganizationId(),
                    root -> root.join(COrganizationInfo_.parentOrganization, JoinType.LEFT).get(COrganization_.id)));
            }
        }
        return specification;
    }
}
