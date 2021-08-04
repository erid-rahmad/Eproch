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

import com.bhp.opusb.domain.CTask;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.CTaskRepository;
import com.bhp.opusb.service.dto.CTaskCriteria;
import com.bhp.opusb.service.dto.CTaskDTO;
import com.bhp.opusb.service.mapper.CTaskMapper;

/**
 * Service for executing complex queries for {@link CTask} entities in the database.
 * The main input is a {@link CTaskCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CTaskDTO} or a {@link Page} of {@link CTaskDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CTaskQueryService extends QueryService<CTask> {

    private final Logger log = LoggerFactory.getLogger(CTaskQueryService.class);

    private final CTaskRepository cTaskRepository;

    private final CTaskMapper cTaskMapper;

    public CTaskQueryService(CTaskRepository cTaskRepository, CTaskMapper cTaskMapper) {
        this.cTaskRepository = cTaskRepository;
        this.cTaskMapper = cTaskMapper;
    }

    /**
     * Return a {@link List} of {@link CTaskDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CTaskDTO> findByCriteria(CTaskCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CTask> specification = createSpecification(criteria);
        return cTaskMapper.toDto(cTaskRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CTaskDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CTaskDTO> findByCriteria(CTaskCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CTask> specification = createSpecification(criteria);
        return cTaskRepository.findAll(specification, page)
            .map(cTaskMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CTaskCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CTask> specification = createSpecification(criteria);
        return cTaskRepository.count(specification);
    }

    /**
     * Function to convert {@link CTaskCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CTask> createSpecification(CTaskCriteria criteria) {
        Specification<CTask> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CTask_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CTask_.name));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), CTask_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), CTask_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(CTask_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
        }
        return specification;
    }
}
