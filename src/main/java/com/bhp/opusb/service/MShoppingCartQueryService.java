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

import com.bhp.opusb.domain.MShoppingCart;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MShoppingCartRepository;
import com.bhp.opusb.service.dto.MShoppingCartCriteria;
import com.bhp.opusb.service.dto.MShoppingCartDTO;
import com.bhp.opusb.service.mapper.MShoppingCartMapper;

/**
 * Service for executing complex queries for {@link MShoppingCart} entities in the database.
 * The main input is a {@link MShoppingCartCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MShoppingCartDTO} or a {@link Page} of {@link MShoppingCartDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MShoppingCartQueryService extends QueryService<MShoppingCart> {

    private final Logger log = LoggerFactory.getLogger(MShoppingCartQueryService.class);

    private final MShoppingCartRepository mShoppingCartRepository;

    private final MShoppingCartMapper mShoppingCartMapper;

    public MShoppingCartQueryService(MShoppingCartRepository mShoppingCartRepository, MShoppingCartMapper mShoppingCartMapper) {
        this.mShoppingCartRepository = mShoppingCartRepository;
        this.mShoppingCartMapper = mShoppingCartMapper;
    }

    /**
     * Return a {@link List} of {@link MShoppingCartDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MShoppingCartDTO> findByCriteria(MShoppingCartCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MShoppingCart> specification = createSpecification(criteria);
        return mShoppingCartMapper.toDto(mShoppingCartRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MShoppingCartDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MShoppingCartDTO> findByCriteria(MShoppingCartCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MShoppingCart> specification = createSpecification(criteria);
        return mShoppingCartRepository.findAll(specification, page)
            .map(mShoppingCartMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MShoppingCartCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MShoppingCart> specification = createSpecification(criteria);
        return mShoppingCartRepository.count(specification);
    }

    /**
     * Function to convert {@link MShoppingCartCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MShoppingCart> createSpecification(MShoppingCartCriteria criteria) {
        Specification<MShoppingCart> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MShoppingCart_.id));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MShoppingCart_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MShoppingCart_.active));
            }
            if (criteria.getMShoppingCartItemId() != null) {
                specification = specification.and(buildSpecification(criteria.getMShoppingCartItemId(),
                    root -> root.join(MShoppingCart_.mShoppingCartItems, JoinType.LEFT).get(MShoppingCartItem_.id)));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MShoppingCart_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getAdUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdUserId(),
                    root -> root.join(MShoppingCart_.adUser, JoinType.LEFT).get(AdUser_.id)));
            }
        }
        return specification;
    }
}
