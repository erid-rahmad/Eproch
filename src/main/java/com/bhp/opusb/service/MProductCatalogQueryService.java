package com.bhp.opusb.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import com.bhp.opusb.config.ApplicationProperties;
// for static metamodels
import com.bhp.opusb.domain.ADOrganization_;
import com.bhp.opusb.domain.CCurrency_;
import com.bhp.opusb.domain.CDocumentType_;
import com.bhp.opusb.domain.CGallery_;
import com.bhp.opusb.domain.CProduct_;
import com.bhp.opusb.domain.CUnitOfMeasure_;
import com.bhp.opusb.domain.CVendor_;
import com.bhp.opusb.domain.MBrand_;
import com.bhp.opusb.domain.MProductCatalog;
import com.bhp.opusb.domain.MProductCatalog_;
import com.bhp.opusb.domain.MProductPrice_;
import com.bhp.opusb.repository.MProductCatalogRepository;
import com.bhp.opusb.service.dto.BhinnekaProductFeedDTO;
import com.bhp.opusb.service.dto.MProductCatalogCriteria;
import com.bhp.opusb.service.dto.MProductCatalogDTO;
import com.bhp.opusb.service.mapper.MProductCatalogMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;
import reactor.core.publisher.Mono;

/**
 * Service for executing complex queries for {@link MProductCatalog} entities in the database.
 * The main input is a {@link MProductCatalogCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MProductCatalogDTO} or a {@link Page} of {@link MProductCatalogDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MProductCatalogQueryService extends QueryService<MProductCatalog> {

    private final Logger log = LoggerFactory.getLogger(MProductCatalogQueryService.class);

    private final ApplicationProperties properties;
    private final MProductCatalogRepository mProductCatalogRepository;
    private final BhinnekaProductService bhinnekaProductService;

    private final MProductCatalogMapper mProductCatalogMapper;

    public MProductCatalogQueryService(ApplicationProperties properties, MProductCatalogRepository mProductCatalogRepository, BhinnekaProductService bhinnekaProductService, MProductCatalogMapper mProductCatalogMapper) {
        this.properties = properties;
        this.mProductCatalogRepository = mProductCatalogRepository;
        this.bhinnekaProductService = bhinnekaProductService;
        this.mProductCatalogMapper = mProductCatalogMapper;
    }

    /**
     * Return a {@link List} of {@link MProductCatalogDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MProductCatalogDTO> findByCriteria(MProductCatalogCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MProductCatalog> specification = createSpecification(criteria);
        return mProductCatalogMapper.toDto(mProductCatalogRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MProductCatalogDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MProductCatalogDTO> findByCriteria(MProductCatalogCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MProductCatalog> specification = createSpecification(criteria);
        return mProductCatalogRepository.findAll(specification, page)
            .map(mProductCatalogMapper::toDto);
    }

    /**
     * 
     * @param criteria
     * @param page
     * @return
     */
    public Page<MProductCatalogDTO> findInMarketplace(MProductCatalogCriteria criteria, Pageable page) {
        Page<MProductCatalogDTO> mainCatalog = findByCriteria(criteria, page);

        if (properties.getIntegration().getMarketplace().getBhinneka().getProductFeedUrl() != null) {
            Mono<BhinnekaProductFeedDTO> bhinnekaFeed = bhinnekaProductService.findByCriteria();
            Page<MProductCatalogDTO> bhinnekaCatalog = bhinnekaFeed.map(feed -> feed.map(page)).block();

            if (bhinnekaCatalog != null) {
                final List<MProductCatalogDTO> mainList = mainCatalog.getContent();
                final List<MProductCatalogDTO> bhinnekaList = bhinnekaCatalog.getContent();
                mainList.addAll(bhinnekaList);
                return new PageImpl<>(mainList, page, mainCatalog.getTotalElements() + bhinnekaCatalog.getTotalElements());
            }
        }

        return mainCatalog;
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MProductCatalogCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MProductCatalog> specification = createSpecification(criteria);
        return mProductCatalogRepository.count(specification);
    }

    /**
     * Function to convert {@link MProductCatalogCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MProductCatalog> createSpecification(MProductCatalogCriteria criteria) {
        Specification<MProductCatalog> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MProductCatalog_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), MProductCatalog_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), MProductCatalog_.description));
            }
            if (criteria.getShortDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getShortDescription(), MProductCatalog_.shortDescription));
            }
            if (criteria.getHeight() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHeight(), MProductCatalog_.height));
            }
            if (criteria.getLength() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLength(), MProductCatalog_.length));
            }
            if (criteria.getWidth() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWidth(), MProductCatalog_.width));
            }
            if (criteria.getWeight() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWeight(), MProductCatalog_.weight));
            }
            if (criteria.getPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrice(), MProductCatalog_.price));
            }
            if (criteria.getExpiredDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExpiredDate(), MProductCatalog_.expiredDate));
            }
            if (criteria.getDocumentAction() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentAction(), MProductCatalog_.documentAction));
            }
            if (criteria.getDocumentStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentStatus(), MProductCatalog_.documentStatus));
            }
            if (criteria.getApproved() != null) {
                specification = specification.and(buildSpecification(criteria.getApproved(), MProductCatalog_.approved));
            }
            if (criteria.getProcessed() != null) {
                specification = specification.and(buildSpecification(criteria.getProcessed(), MProductCatalog_.processed));
            }
            if (criteria.getRejectedReason() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRejectedReason(), MProductCatalog_.rejectedReason));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MProductCatalog_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MProductCatalog_.active));
            }
            if (criteria.getCGalleryId() != null) {
                specification = specification.and(buildSpecification(criteria.getCGalleryId(),
                    root -> root.join(MProductCatalog_.cGallery, JoinType.LEFT).get(CGallery_.id)));
            }
            if (criteria.getMProductPriceId() != null) {
                specification = specification.and(buildSpecification(criteria.getMProductPriceId(),
                    root -> root.join(MProductCatalog_.mProductPrices, JoinType.LEFT).get(MProductPrice_.id)));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MProductCatalog_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getCDocumentTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getCDocumentTypeId(),
                    root -> root.join(MProductCatalog_.cDocumentType, JoinType.LEFT).get(CDocumentType_.id)));
            }
            if (criteria.getCCurrencyId() != null) {
                specification = specification.and(buildSpecification(criteria.getCCurrencyId(),
                    root -> root.join(MProductCatalog_.cCurrency, JoinType.LEFT).get(CCurrency_.id)));
            }
            if (criteria.getCUomId() != null) {
                specification = specification.and(buildSpecification(criteria.getCUomId(),
                    root -> root.join(MProductCatalog_.cUom, JoinType.LEFT).get(CUnitOfMeasure_.id)));
            }
            if (criteria.getCVendorId() != null) {
                specification = specification.and(buildSpecification(criteria.getCVendorId(),
                    root -> root.join(MProductCatalog_.cVendor, JoinType.LEFT).get(CVendor_.id)));
            }
            if (criteria.getMBrandId() != null) {
                specification = specification.and(buildSpecification(criteria.getMBrandId(),
                    root -> root.join(MProductCatalog_.mBrand, JoinType.LEFT).get(MBrand_.id)));
            }
            if (criteria.getMProductId() != null) {
                specification = specification.and(buildSpecification(criteria.getMProductId(),
                    root -> root.join(MProductCatalog_.mProduct, JoinType.LEFT).get(CProduct_.id)));
            }
        }
        return specification;
    }
}
