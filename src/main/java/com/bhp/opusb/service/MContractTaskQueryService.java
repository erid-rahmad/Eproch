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

import com.bhp.opusb.domain.MContractTask;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MContractTaskRepository;
import com.bhp.opusb.service.dto.MContractTaskCriteria;
import com.bhp.opusb.service.dto.MContractTaskDTO;
import com.bhp.opusb.service.mapper.MContractTaskMapper;

/**
 * Service for executing complex queries for {@link MContractTask} entities in the database.
 * The main input is a {@link MContractTaskCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MContractTaskDTO} or a {@link Page} of {@link MContractTaskDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MContractTaskQueryService extends QueryService<MContractTask> {

    private final Logger log = LoggerFactory.getLogger(MContractTaskQueryService.class);

    private final MContractTaskRepository mContractTaskRepository;

    private final MContractTaskMapper mContractTaskMapper;

    public MContractTaskQueryService(MContractTaskRepository mContractTaskRepository, MContractTaskMapper mContractTaskMapper) {
        this.mContractTaskRepository = mContractTaskRepository;
        this.mContractTaskMapper = mContractTaskMapper;
    }

    /**
     * Return a {@link List} of {@link MContractTaskDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MContractTaskDTO> findByCriteria(MContractTaskCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MContractTask> specification = createSpecification(criteria);
        return mContractTaskMapper.toDto(mContractTaskRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MContractTaskDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MContractTaskDTO> findByCriteria(MContractTaskCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MContractTask> specification = createSpecification(criteria);
        return mContractTaskRepository.findAll(specification, page)
            .map(mContractTaskMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MContractTaskCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MContractTask> specification = createSpecification(criteria);
        return mContractTaskRepository.count(specification);
    }

    /**
     * Function to convert {@link MContractTaskCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MContractTask> createSpecification(MContractTaskCriteria criteria) {
        Specification<MContractTask> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MContractTask_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), MContractTask_.name));
            }
            if (criteria.getDueDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDueDate(), MContractTask_.dueDate));
            }
            if (criteria.getDocumentAction() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentAction(), MContractTask_.documentAction));
            }
            if (criteria.getDocumentStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentStatus(), MContractTask_.documentStatus));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MContractTask_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MContractTask_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MContractTask_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getContractId() != null) {
                specification = specification.and(buildSpecification(criteria.getContractId(),
                    root -> root.join(MContractTask_.contract, JoinType.LEFT).get(MContract_.id)));
            }
            if (criteria.getTaskId() != null) {
                specification = specification.and(buildSpecification(criteria.getTaskId(),
                    root -> root.join(MContractTask_.task, JoinType.LEFT).get(CTask_.id)));
            }
        }
        return specification;
    }
}
