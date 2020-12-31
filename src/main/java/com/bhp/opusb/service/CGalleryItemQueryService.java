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

import com.bhp.opusb.domain.CGalleryItem;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.CGalleryItemRepository;
import com.bhp.opusb.service.dto.CGalleryItemCriteria;
import com.bhp.opusb.service.dto.CGalleryItemDTO;
import com.bhp.opusb.service.mapper.CGalleryItemMapper;

/**
 * Service for executing complex queries for {@link CGalleryItem} entities in the database.
 * The main input is a {@link CGalleryItemCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CGalleryItemDTO} or a {@link Page} of {@link CGalleryItemDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CGalleryItemQueryService extends QueryService<CGalleryItem> {

    private final Logger log = LoggerFactory.getLogger(CGalleryItemQueryService.class);

    private final CGalleryItemRepository cGalleryItemRepository;

    private final CGalleryItemMapper cGalleryItemMapper;

    public CGalleryItemQueryService(CGalleryItemRepository cGalleryItemRepository, CGalleryItemMapper cGalleryItemMapper) {
        this.cGalleryItemRepository = cGalleryItemRepository;
        this.cGalleryItemMapper = cGalleryItemMapper;
    }

    /**
     * Return a {@link List} of {@link CGalleryItemDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CGalleryItemDTO> findByCriteria(CGalleryItemCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CGalleryItem> specification = createSpecification(criteria);
        return cGalleryItemMapper.toDto(cGalleryItemRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CGalleryItemDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CGalleryItemDTO> findByCriteria(CGalleryItemCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CGalleryItem> specification = createSpecification(criteria);
        return cGalleryItemRepository.findAll(specification, page)
            .map(cGalleryItemMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CGalleryItemCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CGalleryItem> specification = createSpecification(criteria);
        return cGalleryItemRepository.count(specification);
    }

    /**
     * Function to convert {@link CGalleryItemCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CGalleryItem> createSpecification(CGalleryItemCriteria criteria) {
        Specification<CGalleryItem> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CGalleryItem_.id));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), CGalleryItem_.type));
            }
            if (criteria.getSequence() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSequence(), CGalleryItem_.sequence));
            }
            if (criteria.getPreview() != null) {
                specification = specification.and(buildSpecification(criteria.getPreview(), CGalleryItem_.preview));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), CGalleryItem_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), CGalleryItem_.active));
            }
            if (criteria.getCAttachmentId() != null) {
                specification = specification.and(buildSpecification(criteria.getCAttachmentId(),
                    root -> root.join(CGalleryItem_.cAttachment, JoinType.LEFT).get(CAttachment_.id)));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(CGalleryItem_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getCGalleryId() != null) {
                specification = specification.and(buildSpecification(criteria.getCGalleryId(),
                    root -> root.join(CGalleryItem_.cGallery, JoinType.LEFT).get(CGallery_.id)));
            }
        }
        return specification;
    }
}
