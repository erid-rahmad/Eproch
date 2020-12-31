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

import com.bhp.opusb.domain.MProductPrice;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MProductPriceRepository;
import com.bhp.opusb.service.dto.MProductPriceCriteria;
import com.bhp.opusb.service.dto.MProductPriceDTO;
import com.bhp.opusb.service.mapper.MProductPriceMapper;

/**
 * Service for executing complex queries for {@link MProductPrice} entities in the database.
 * The main input is a {@link MProductPriceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MProductPriceDTO} or a {@link Page} of {@link MProductPriceDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MProductPriceQueryService extends QueryService<MProductPrice> {

    private final Logger log = LoggerFactory.getLogger(MProductPriceQueryService.class);

    private final MProductPriceRepository mProductPriceRepository;

    private final MProductPriceMapper mProductPriceMapper;

    public MProductPriceQueryService(MProductPriceRepository mProductPriceRepository, MProductPriceMapper mProductPriceMapper) {
        this.mProductPriceRepository = mProductPriceRepository;
        this.mProductPriceMapper = mProductPriceMapper;
    }

    /**
     * Return a {@link List} of {@link MProductPriceDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MProductPriceDTO> findByCriteria(MProductPriceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MProductPrice> specification = createSpecification(criteria);
        return mProductPriceMapper.toDto(mProductPriceRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MProductPriceDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MProductPriceDTO> findByCriteria(MProductPriceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MProductPrice> specification = createSpecification(criteria);
        return mProductPriceRepository.findAll(specification, page)
            .map(mProductPriceMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MProductPriceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MProductPrice> specification = createSpecification(criteria);
        return mProductPriceRepository.count(specification);
    }

    /**
     * Function to convert {@link MProductPriceCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MProductPrice> createSpecification(MProductPriceCriteria criteria) {
        Specification<MProductPrice> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MProductPrice_.id));
            }
            if (criteria.getMinQty() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMinQty(), MProductPrice_.minQty));
            }
            if (criteria.getMaxQty() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMaxQty(), MProductPrice_.maxQty));
            }
            if (criteria.getPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrice(), MProductPrice_.price));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MProductPrice_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MProductPrice_.active));
            }
        }
        return specification;
    }
}
