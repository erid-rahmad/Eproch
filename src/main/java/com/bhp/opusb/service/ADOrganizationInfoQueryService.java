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

import com.bhp.opusb.domain.ADOrganizationInfo;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.ADOrganizationInfoRepository;
import com.bhp.opusb.service.dto.ADOrganizationInfoCriteria;
import com.bhp.opusb.service.dto.ADOrganizationInfoDTO;
import com.bhp.opusb.service.mapper.ADOrganizationInfoMapper;

/**
 * Service for executing complex queries for {@link ADOrganizationInfo} entities in the database.
 * The main input is a {@link ADOrganizationInfoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ADOrganizationInfoDTO} or a {@link Page} of {@link ADOrganizationInfoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ADOrganizationInfoQueryService extends QueryService<ADOrganizationInfo> {

    private final Logger log = LoggerFactory.getLogger(ADOrganizationInfoQueryService.class);

    private final ADOrganizationInfoRepository aDOrganizationInfoRepository;

    private final ADOrganizationInfoMapper aDOrganizationInfoMapper;

    public ADOrganizationInfoQueryService(ADOrganizationInfoRepository aDOrganizationInfoRepository, ADOrganizationInfoMapper aDOrganizationInfoMapper) {
        this.aDOrganizationInfoRepository = aDOrganizationInfoRepository;
        this.aDOrganizationInfoMapper = aDOrganizationInfoMapper;
    }

    /**
     * Return a {@link List} of {@link ADOrganizationInfoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ADOrganizationInfoDTO> findByCriteria(ADOrganizationInfoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ADOrganizationInfo> specification = createSpecification(criteria);
        return aDOrganizationInfoMapper.toDto(aDOrganizationInfoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ADOrganizationInfoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ADOrganizationInfoDTO> findByCriteria(ADOrganizationInfoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ADOrganizationInfo> specification = createSpecification(criteria);
        return aDOrganizationInfoRepository.findAll(specification, page)
            .map(aDOrganizationInfoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ADOrganizationInfoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ADOrganizationInfo> specification = createSpecification(criteria);
        return aDOrganizationInfoRepository.count(specification);
    }

    /**
     * Function to convert {@link ADOrganizationInfoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ADOrganizationInfo> createSpecification(ADOrganizationInfoCriteria criteria) {
        Specification<ADOrganizationInfo> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ADOrganizationInfo_.id));
            }
            if (criteria.getAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress(), ADOrganizationInfo_.address));
            }
            if (criteria.getTaxId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTaxId(), ADOrganizationInfo_.taxId));
            }
            if (criteria.getBankAccount() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBankAccount(), ADOrganizationInfo_.bankAccount));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), ADOrganizationInfo_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), ADOrganizationInfo_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(ADOrganizationInfo_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
        }
        return specification;
    }
}
