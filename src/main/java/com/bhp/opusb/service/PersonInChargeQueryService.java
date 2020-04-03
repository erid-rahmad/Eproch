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

import com.bhp.opusb.domain.PersonInCharge;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.PersonInChargeRepository;
import com.bhp.opusb.service.dto.PersonInChargeCriteria;
import com.bhp.opusb.service.dto.PersonInChargeDTO;
import com.bhp.opusb.service.mapper.PersonInChargeMapper;

/**
 * Service for executing complex queries for {@link PersonInCharge} entities in the database.
 * The main input is a {@link PersonInChargeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PersonInChargeDTO} or a {@link Page} of {@link PersonInChargeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PersonInChargeQueryService extends QueryService<PersonInCharge> {

    private final Logger log = LoggerFactory.getLogger(PersonInChargeQueryService.class);

    private final PersonInChargeRepository personInChargeRepository;

    private final PersonInChargeMapper personInChargeMapper;

    public PersonInChargeQueryService(PersonInChargeRepository personInChargeRepository, PersonInChargeMapper personInChargeMapper) {
        this.personInChargeRepository = personInChargeRepository;
        this.personInChargeMapper = personInChargeMapper;
    }

    /**
     * Return a {@link List} of {@link PersonInChargeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PersonInChargeDTO> findByCriteria(PersonInChargeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PersonInCharge> specification = createSpecification(criteria);
        return personInChargeMapper.toDto(personInChargeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PersonInChargeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PersonInChargeDTO> findByCriteria(PersonInChargeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PersonInCharge> specification = createSpecification(criteria);
        return personInChargeRepository.findAll(specification, page)
            .map(personInChargeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PersonInChargeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PersonInCharge> specification = createSpecification(criteria);
        return personInChargeRepository.count(specification);
    }

    /**
     * Function to convert {@link PersonInChargeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PersonInCharge> createSpecification(PersonInChargeCriteria criteria) {
        Specification<PersonInCharge> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PersonInCharge_.id));
            }
            if (criteria.getPosition() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPosition(), PersonInCharge_.position));
            }
            if (criteria.getPhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhone(), PersonInCharge_.phone));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(PersonInCharge_.user, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getVendorId() != null) {
                specification = specification.and(buildSpecification(criteria.getVendorId(),
                    root -> root.join(PersonInCharge_.vendor, JoinType.LEFT).get(Vendor_.id)));
            }
        }
        return specification;
    }
}
