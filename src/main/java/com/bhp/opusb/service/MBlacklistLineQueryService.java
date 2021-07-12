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

import com.bhp.opusb.domain.MBlacklistLine;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MBlacklistLineRepository;
import com.bhp.opusb.service.dto.MBlacklistLineCriteria;
import com.bhp.opusb.service.dto.MBlacklistLineDTO;
import com.bhp.opusb.service.mapper.MBlacklistLineMapper;

/**
 * Service for executing complex queries for {@link MBlacklistLine} entities in the database.
 * The main input is a {@link MBlacklistLineCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MBlacklistLineDTO} or a {@link Page} of {@link MBlacklistLineDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MBlacklistLineQueryService extends QueryService<MBlacklistLine> {

    private final Logger log = LoggerFactory.getLogger(MBlacklistLineQueryService.class);

    private final MBlacklistLineRepository mBlacklistLineRepository;

    private final MBlacklistLineMapper mBlacklistLineMapper;

    public MBlacklistLineQueryService(MBlacklistLineRepository mBlacklistLineRepository, MBlacklistLineMapper mBlacklistLineMapper) {
        this.mBlacklistLineRepository = mBlacklistLineRepository;
        this.mBlacklistLineMapper = mBlacklistLineMapper;
    }

    /**
     * Return a {@link List} of {@link MBlacklistLineDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MBlacklistLineDTO> findByCriteria(MBlacklistLineCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MBlacklistLine> specification = createSpecification(criteria);
        return mBlacklistLineMapper.toDto(mBlacklistLineRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MBlacklistLineDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MBlacklistLineDTO> findByCriteria(MBlacklistLineCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MBlacklistLine> specification = createSpecification(criteria);
        return mBlacklistLineRepository.findAll(specification, page)
            .map(mBlacklistLineMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MBlacklistLineCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MBlacklistLine> specification = createSpecification(criteria);
        return mBlacklistLineRepository.count(specification);
    }

    /**
     * Function to convert {@link MBlacklistLineCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MBlacklistLine> createSpecification(MBlacklistLineCriteria criteria) {
        Specification<MBlacklistLine> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MBlacklistLine_.id));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MBlacklistLine_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MBlacklistLine_.active));
            }
            if (criteria.getBlacklistId() != null) {
                specification = specification.and(buildSpecification(criteria.getBlacklistId(),
                    root -> root.join(MBlacklistLine_.blacklist, JoinType.LEFT).get(MBlacklist_.id)));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MBlacklistLine_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getPicId() != null) {
                specification = specification.and(buildSpecification(criteria.getPicId(),
                    root -> root.join(MBlacklistLine_.pic, JoinType.LEFT).get(AdUser_.id)));
            }
            if (criteria.getFunctionaryId() != null) {
                specification = specification.and(buildSpecification(criteria.getFunctionaryId(),
                    root -> root.join(MBlacklistLine_.functionary, JoinType.LEFT).get(CFunctionary_.id)));
            }
        }
        return specification;
    }
}
