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

import com.bhp.opusb.domain.MShoppingCartItem;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MShoppingCartItemRepository;
import com.bhp.opusb.service.dto.MShoppingCartItemCriteria;
import com.bhp.opusb.service.dto.MShoppingCartItemDTO;
import com.bhp.opusb.service.mapper.MShoppingCartItemMapper;

/**
 * Service for executing complex queries for {@link MShoppingCartItem} entities in the database.
 * The main input is a {@link MShoppingCartItemCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MShoppingCartItemDTO} or a {@link Page} of {@link MShoppingCartItemDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MShoppingCartItemQueryService extends QueryService<MShoppingCartItem> {

    private final Logger log = LoggerFactory.getLogger(MShoppingCartItemQueryService.class);

    private final MShoppingCartItemRepository mShoppingCartItemRepository;

    private final MShoppingCartItemMapper mShoppingCartItemMapper;

    public MShoppingCartItemQueryService(MShoppingCartItemRepository mShoppingCartItemRepository, MShoppingCartItemMapper mShoppingCartItemMapper) {
        this.mShoppingCartItemRepository = mShoppingCartItemRepository;
        this.mShoppingCartItemMapper = mShoppingCartItemMapper;
    }

    /**
     * Return a {@link List} of {@link MShoppingCartItemDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MShoppingCartItemDTO> findByCriteria(MShoppingCartItemCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MShoppingCartItem> specification = createSpecification(criteria);
        return mShoppingCartItemMapper.toDto(mShoppingCartItemRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MShoppingCartItemDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MShoppingCartItemDTO> findByCriteria(MShoppingCartItemCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MShoppingCartItem> specification = createSpecification(criteria);
        return mShoppingCartItemRepository.findAll(specification, page)
            .map(mShoppingCartItemMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MShoppingCartItemCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MShoppingCartItem> specification = createSpecification(criteria);
        return mShoppingCartItemRepository.count(specification);
    }

    /**
     * Function to convert {@link MShoppingCartItemCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MShoppingCartItem> createSpecification(MShoppingCartItemCriteria criteria) {
        Specification<MShoppingCartItem> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MShoppingCartItem_.id));
            }
            if (criteria.getQuantity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantity(), MShoppingCartItem_.quantity));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MShoppingCartItem_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MShoppingCartItem_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MShoppingCartItem_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getMProductId() != null) {
                specification = specification.and(buildSpecification(criteria.getMProductId(),
                    root -> root.join(MShoppingCartItem_.mProduct, JoinType.LEFT).get(CProduct_.id)));
            }
            if (criteria.getMShoppingCartId() != null) {
                specification = specification.and(buildSpecification(criteria.getMShoppingCartId(),
                    root -> root.join(MShoppingCartItem_.mShoppingCart, JoinType.LEFT).get(MShoppingCart_.id)));
            }
        }
        return specification;
    }
}
