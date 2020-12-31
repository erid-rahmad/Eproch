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

import com.bhp.opusb.domain.CGallery;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.CGalleryRepository;
import com.bhp.opusb.service.dto.CGalleryCriteria;
import com.bhp.opusb.service.dto.CGalleryDTO;
import com.bhp.opusb.service.mapper.CGalleryMapper;

/**
 * Service for executing complex queries for {@link CGallery} entities in the database.
 * The main input is a {@link CGalleryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CGalleryDTO} or a {@link Page} of {@link CGalleryDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CGalleryQueryService extends QueryService<CGallery> {

    private final Logger log = LoggerFactory.getLogger(CGalleryQueryService.class);

    private final CGalleryRepository cGalleryRepository;

    private final CGalleryMapper cGalleryMapper;

    public CGalleryQueryService(CGalleryRepository cGalleryRepository, CGalleryMapper cGalleryMapper) {
        this.cGalleryRepository = cGalleryRepository;
        this.cGalleryMapper = cGalleryMapper;
    }

    /**
     * Return a {@link List} of {@link CGalleryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CGalleryDTO> findByCriteria(CGalleryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CGallery> specification = createSpecification(criteria);
        return cGalleryMapper.toDto(cGalleryRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CGalleryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CGalleryDTO> findByCriteria(CGalleryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CGallery> specification = createSpecification(criteria);
        return cGalleryRepository.findAll(specification, page)
            .map(cGalleryMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CGalleryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CGallery> specification = createSpecification(criteria);
        return cGalleryRepository.count(specification);
    }

    /**
     * Function to convert {@link CGalleryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CGallery> createSpecification(CGalleryCriteria criteria) {
        Specification<CGallery> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CGallery_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CGallery_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), CGallery_.description));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), CGallery_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), CGallery_.active));
            }
            if (criteria.getCGalleryItemId() != null) {
                specification = specification.and(buildSpecification(criteria.getCGalleryItemId(),
                    root -> root.join(CGallery_.cGalleryItems, JoinType.LEFT).get(CGalleryItem_.id)));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(CGallery_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
        }
        return specification;
    }
}
