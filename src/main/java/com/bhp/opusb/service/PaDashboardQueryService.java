package com.bhp.opusb.service;

import java.math.*;
import java.util.*;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.bhp.opusb.domain.PaDashboard;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.PaDashboardRepository;
import com.bhp.opusb.service.dto.DashboardChartDTO;
import com.bhp.opusb.service.dto.PaDashboardCriteria;
import com.bhp.opusb.service.dto.PaDashboardDTO;
import com.bhp.opusb.service.mapper.PaDashboardMapper;

/**
 * Service for executing complex queries for {@link PaDashboard} entities in the database.
 * The main input is a {@link PaDashboardCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PaDashboardDTO} or a {@link Page} of {@link PaDashboardDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PaDashboardQueryService extends QueryService<PaDashboard> {

    private final Logger log = LoggerFactory.getLogger(PaDashboardQueryService.class);

    private final PaDashboardRepository paDashboardRepository;

    private final PaDashboardMapper paDashboardMapper;

    public PaDashboardQueryService(PaDashboardRepository paDashboardRepository, PaDashboardMapper paDashboardMapper) {
        this.paDashboardRepository = paDashboardRepository;
        this.paDashboardMapper = paDashboardMapper;
    }

    /**
     * Return a {@link List} of {@link PaDashboardDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PaDashboardDTO> findByCriteria(PaDashboardCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PaDashboard> specification = createSpecification(criteria);
        return paDashboardMapper.toDto(paDashboardRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PaDashboardDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PaDashboardDTO> findByCriteria(PaDashboardCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PaDashboard> specification = createSpecification(criteria);
        return paDashboardRepository.findAll(specification, page)
            .map(paDashboardMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PaDashboardCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PaDashboard> specification = createSpecification(criteria);
        return paDashboardRepository.count(specification);
    }

    @Transactional(readOnly = true)
    public List<DashboardChartDTO> getSpendByCostCtr() {
        return convertToChartData(paDashboardRepository.getSpendByCostCtr());
    }

    @Transactional(readOnly = true)
    public List<DashboardChartDTO> getProdPurchaseAmount() {
        return convertToChartData(paDashboardRepository.getProdPurchaseAmount());
    }

    private List<DashboardChartDTO> convertToChartData(List<Object[]> listObject)
    {
        List<DashboardChartDTO> listData = new ArrayList<>();
        for(Object[] x: listObject){
            DashboardChartDTO dto = new DashboardChartDTO();
            dto.setLegendLabel((String)x[0]);
            dto.setxAxisLabel((String)x[1]);
            dto.setDataValue((BigDecimal)x[2]);
            
            listData.add(dto);
        }

        return listData;
    }

    /**
     * Function to convert {@link PaDashboardCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PaDashboard> createSpecification(PaDashboardCriteria criteria) {
        Specification<PaDashboard> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PaDashboard_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), PaDashboard_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), PaDashboard_.description));
            }
            if (criteria.getMinColumns() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMinColumns(), PaDashboard_.minColumns));
            }
            if (criteria.getMaxColumns() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMaxColumns(), PaDashboard_.maxColumns));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), PaDashboard_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), PaDashboard_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(PaDashboard_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
        }
        return specification;
    }
}
