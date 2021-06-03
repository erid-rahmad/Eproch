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

import com.bhp.opusb.domain.MAuctionContent;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MAuctionContentRepository;
import com.bhp.opusb.service.dto.MAuctionContentCriteria;
import com.bhp.opusb.service.dto.MAuctionContentDTO;
import com.bhp.opusb.service.mapper.MAuctionContentMapper;

/**
 * Service for executing complex queries for {@link MAuctionContent} entities in the database.
 * The main input is a {@link MAuctionContentCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MAuctionContentDTO} or a {@link Page} of {@link MAuctionContentDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MAuctionContentQueryService extends QueryService<MAuctionContent> {

    private final Logger log = LoggerFactory.getLogger(MAuctionContentQueryService.class);

    private final MAuctionContentRepository mAuctionContentRepository;

    private final MAuctionContentMapper mAuctionContentMapper;

    public MAuctionContentQueryService(MAuctionContentRepository mAuctionContentRepository, MAuctionContentMapper mAuctionContentMapper) {
        this.mAuctionContentRepository = mAuctionContentRepository;
        this.mAuctionContentMapper = mAuctionContentMapper;
    }

    /**
     * Return a {@link List} of {@link MAuctionContentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MAuctionContentDTO> findByCriteria(MAuctionContentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MAuctionContent> specification = createSpecification(criteria);
        return mAuctionContentMapper.toDto(mAuctionContentRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MAuctionContentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MAuctionContentDTO> findByCriteria(MAuctionContentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MAuctionContent> specification = createSpecification(criteria);
        return mAuctionContentRepository.findAll(specification, page)
            .map(mAuctionContentMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MAuctionContentCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MAuctionContent> specification = createSpecification(criteria);
        return mAuctionContentRepository.count(specification);
    }

    /**
     * Function to convert {@link MAuctionContentCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MAuctionContent> createSpecification(MAuctionContentCriteria criteria) {
        Specification<MAuctionContent> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MAuctionContent_.id));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MAuctionContent_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MAuctionContent_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MAuctionContent_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
        }
        return specification;
    }
}
