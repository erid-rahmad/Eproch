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

import com.bhp.opusb.domain.ADWindow;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.ADWindowRepository;
import com.bhp.opusb.service.dto.ADWindowCriteria;
import com.bhp.opusb.service.dto.ADWindowDTO;
import com.bhp.opusb.service.mapper.ADWindowMapper;

/**
 * Service for executing complex queries for {@link ADWindow} entities in the database.
 * The main input is a {@link ADWindowCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ADWindowDTO} or a {@link Page} of {@link ADWindowDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ADWindowQueryService extends QueryService<ADWindow> {

    private final Logger log = LoggerFactory.getLogger(ADWindowQueryService.class);

    private final ADWindowRepository aDWindowRepository;

    private final ADWindowMapper aDWindowMapper;

    public ADWindowQueryService(ADWindowRepository aDWindowRepository, ADWindowMapper aDWindowMapper) {
        this.aDWindowRepository = aDWindowRepository;
        this.aDWindowMapper = aDWindowMapper;
    }

    /**
     * Return a {@link List} of {@link ADWindowDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ADWindowDTO> findByCriteria(ADWindowCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ADWindow> specification = createSpecification(criteria);
        return aDWindowMapper.toDto(aDWindowRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ADWindowDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ADWindowDTO> findByCriteria(ADWindowCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ADWindow> specification = createSpecification(criteria);
        return aDWindowRepository.findAll(specification, page)
            .map(aDWindowMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ADWindowCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ADWindow> specification = createSpecification(criteria);
        return aDWindowRepository.count(specification);
    }

    /**
     * Function to convert {@link ADWindowCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ADWindow> createSpecification(ADWindowCriteria criteria) {
        Specification<ADWindow> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ADWindow_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ADWindow_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), ADWindow_.description));
            }
            if (criteria.getTitleLogic() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitleLogic(), ADWindow_.titleLogic));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), ADWindow_.type));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), ADWindow_.active));
            }
            if (criteria.getADTabId() != null) {
                specification = specification.and(buildSpecification(criteria.getADTabId(),
                    root -> root.join(ADWindow_.aDTabs, JoinType.LEFT).get(ADTab_.id)));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(ADWindow_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
        }
        return specification;
    }
}
