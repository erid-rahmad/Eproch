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

import com.bhp.opusb.domain.MContractTaskReviewers;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MContractTaskReviewersRepository;
import com.bhp.opusb.service.dto.MContractTaskReviewersCriteria;
import com.bhp.opusb.service.dto.MContractTaskReviewersDTO;
import com.bhp.opusb.service.mapper.MContractTaskReviewersMapper;

/**
 * Service for executing complex queries for {@link MContractTaskReviewers} entities in the database.
 * The main input is a {@link MContractTaskReviewersCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MContractTaskReviewersDTO} or a {@link Page} of {@link MContractTaskReviewersDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MContractTaskReviewersQueryService extends QueryService<MContractTaskReviewers> {

    private final Logger log = LoggerFactory.getLogger(MContractTaskReviewersQueryService.class);

    private final MContractTaskReviewersRepository mContractTaskReviewersRepository;

    private final MContractTaskReviewersMapper mContractTaskReviewersMapper;

    public MContractTaskReviewersQueryService(MContractTaskReviewersRepository mContractTaskReviewersRepository, MContractTaskReviewersMapper mContractTaskReviewersMapper) {
        this.mContractTaskReviewersRepository = mContractTaskReviewersRepository;
        this.mContractTaskReviewersMapper = mContractTaskReviewersMapper;
    }

    /**
     * Return a {@link List} of {@link MContractTaskReviewersDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MContractTaskReviewersDTO> findByCriteria(MContractTaskReviewersCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MContractTaskReviewers> specification = createSpecification(criteria);
        return mContractTaskReviewersMapper.toDto(mContractTaskReviewersRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MContractTaskReviewersDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MContractTaskReviewersDTO> findByCriteria(MContractTaskReviewersCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MContractTaskReviewers> specification = createSpecification(criteria);
        return mContractTaskReviewersRepository.findAll(specification, page)
            .map(mContractTaskReviewersMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MContractTaskReviewersCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MContractTaskReviewers> specification = createSpecification(criteria);
        return mContractTaskReviewersRepository.count(specification);
    }

    /**
     * Function to convert {@link MContractTaskReviewersCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MContractTaskReviewers> createSpecification(MContractTaskReviewersCriteria criteria) {
        Specification<MContractTaskReviewers> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MContractTaskReviewers_.id));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MContractTaskReviewers_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MContractTaskReviewers_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MContractTaskReviewers_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getContractTaskId() != null) {
                specification = specification.and(buildSpecification(criteria.getContractTaskId(),
                    root -> root.join(MContractTaskReviewers_.contractTask, JoinType.LEFT).get(MContractTask_.id)));
            }
            if (criteria.getPicId() != null) {
                specification = specification.and(buildSpecification(criteria.getPicId(),
                    root -> root.join(MContractTaskReviewers_.pic, JoinType.LEFT).get(AdUser_.id)));
            }
        }
        return specification;
    }
}
