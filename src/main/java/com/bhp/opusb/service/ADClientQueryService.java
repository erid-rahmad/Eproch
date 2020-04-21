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

import com.bhp.opusb.domain.ADClient;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.ADClientRepository;
import com.bhp.opusb.service.dto.ADClientCriteria;
import com.bhp.opusb.service.dto.ADClientDTO;
import com.bhp.opusb.service.mapper.ADClientMapper;

/**
 * Service for executing complex queries for {@link ADClient} entities in the database.
 * The main input is a {@link ADClientCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ADClientDTO} or a {@link Page} of {@link ADClientDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ADClientQueryService extends QueryService<ADClient> {

    private final Logger log = LoggerFactory.getLogger(ADClientQueryService.class);

    private final ADClientRepository aDClientRepository;

    private final ADClientMapper aDClientMapper;

    public ADClientQueryService(ADClientRepository aDClientRepository, ADClientMapper aDClientMapper) {
        this.aDClientRepository = aDClientRepository;
        this.aDClientMapper = aDClientMapper;
    }

    /**
     * Return a {@link List} of {@link ADClientDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ADClientDTO> findByCriteria(ADClientCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ADClient> specification = createSpecification(criteria);
        return aDClientMapper.toDto(aDClientRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ADClientDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ADClientDTO> findByCriteria(ADClientCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ADClient> specification = createSpecification(criteria);
        return aDClientRepository.findAll(specification, page)
            .map(aDClientMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ADClientCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ADClient> specification = createSpecification(criteria);
        return aDClientRepository.count(specification);
    }

    /**
     * Function to convert {@link ADClientCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ADClient> createSpecification(ADClientCriteria criteria) {
        Specification<ADClient> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ADClient_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ADClient_.name));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), ADClient_.code));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), ADClient_.description));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), ADClient_.active));
            }
        }
        return specification;
    }
}
