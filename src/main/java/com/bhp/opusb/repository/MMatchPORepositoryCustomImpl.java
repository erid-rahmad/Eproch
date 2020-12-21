package com.bhp.opusb.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import com.bhp.opusb.domain.CVendor_;
import com.bhp.opusb.domain.MMatchPO;
import com.bhp.opusb.domain.MMatchPO_;
import com.bhp.opusb.domain.MVerification;
import com.bhp.opusb.domain.MVerificationLine;
import com.bhp.opusb.domain.MVerificationLine_;
import com.bhp.opusb.domain.MVerification_;
import com.bhp.opusb.service.dto.MMatchPOCriteria;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import io.github.jhipster.service.filter.LocalDateFilter;

public class MMatchPORepositoryCustomImpl implements MMatchPORepositoryCustom {

  private final EntityManager entityManager;

  public MMatchPORepositoryCustomImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public Page<MMatchPO> findNonInvoicedMatchPOs(MMatchPOCriteria criteria, Pageable page) {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<MMatchPO> mainQuery = criteriaBuilder.createQuery(MMatchPO.class);
    Root<MMatchPO> rootMatchPo = mainQuery.from(MMatchPO.class);
    Subquery<MVerificationLine> subQuery = mainQuery.subquery(MVerificationLine.class);
    Root<MVerificationLine> rootVerificationLine = subQuery.from(MVerificationLine.class);
    Join<MVerificationLine, MVerification> join = rootVerificationLine.join(MVerificationLine_.verification);
    Predicate[] subQueryPredicates = buildSubqueryPredicates(criteriaBuilder, rootMatchPo, rootVerificationLine, join);

    CriteriaQuery<Long> mainCountQuery = criteriaBuilder.createQuery(Long.class);
    Root<MMatchPO> rootCountMatchPo = mainCountQuery.from(MMatchPO.class);
    Subquery<MVerificationLine> subCountQuery = mainCountQuery.subquery(MVerificationLine.class);
    Root<MVerificationLine> rootCountVerificationLine = subCountQuery.from(MVerificationLine.class);
    Join<MVerificationLine, MVerification> countJoin = rootCountVerificationLine.join(MVerificationLine_.verification);
    Predicate[] subCountQueryPredicates = buildSubqueryPredicates(criteriaBuilder, rootCountMatchPo, rootCountVerificationLine, countJoin);

    subQuery.select(rootVerificationLine);
    subQuery.where(subQueryPredicates);
    subCountQuery.select(rootCountVerificationLine);
    subCountQuery.where(subCountQueryPredicates);

    mainQuery.where(buildMatchPoPredicates(criteriaBuilder, criteria, rootMatchPo, subQuery));
    mainCountQuery.where(buildMatchPoPredicates(criteriaBuilder, criteria, rootCountMatchPo, subCountQuery));
    mainCountQuery.select(criteriaBuilder.count(rootCountMatchPo));

    TypedQuery<MMatchPO> query = entityManager.createQuery(mainQuery);
    Long totalCount = entityManager.createQuery(mainCountQuery).getSingleResult();
    Long offset = page.getOffset();
    query.setFirstResult(offset.intValue());
    query.setMaxResults(page.getPageSize());
    query.getResultList();
    List<MMatchPO> matchPOs = query.getResultList();
    return new PageImpl<>(matchPOs, page, totalCount);
  }

  private Predicate[] buildSubqueryPredicates(CriteriaBuilder criteriaBuilder, Root<MMatchPO> wrapper, Root<MVerificationLine> subqueryRoot, Join<MVerificationLine, MVerification> join) {
    List<Predicate> predicates = new ArrayList<>(8);
    In<String> appliedVerifications = criteriaBuilder.in(join.get(MVerification_.verificationStatus))
      .value("DRF").value("SMT").value("APV");

    predicates.add(appliedVerifications);
    predicates.add(criteriaBuilder.equal(wrapper.get(MMatchPO_.adOrganization), subqueryRoot.get(MVerificationLine_.adOrganization)));
    predicates.add(criteriaBuilder.equal(wrapper.get(MMatchPO_.cDocType), subqueryRoot.get(MVerificationLine_.cDocType)));
    predicates.add(criteriaBuilder.equal(wrapper.get(MMatchPO_.poNo), subqueryRoot.get(MVerificationLine_.poNo)));
    predicates.add(criteriaBuilder.equal(wrapper.get(MMatchPO_.receiptNo), subqueryRoot.get(MVerificationLine_.receiveNo)));
    predicates.add(criteriaBuilder.equal(wrapper.get(MMatchPO_.lineNoPo), subqueryRoot.get(MVerificationLine_.lineNoPo)));
    predicates.add(criteriaBuilder.equal(wrapper.get(MMatchPO_.lineNoMr), subqueryRoot.get(MVerificationLine_.lineNoMr)));
    predicates.add(criteriaBuilder.equal(wrapper.get(MMatchPO_.orderSuffix), subqueryRoot.get(MVerificationLine_.orderSuffix)));
    return predicates.toArray(new Predicate[] {});
  }

  private Predicate[] buildMatchPoPredicates(CriteriaBuilder criteriaBuilder, MMatchPOCriteria criteria, Root<MMatchPO> root, Subquery<MVerificationLine> subQuery) {
    List<Predicate> queryPredicates = new ArrayList<>(8);

    if (criteria.getCVendorId() != null) {
      queryPredicates.add(criteriaBuilder.equal(
        root.join(MMatchPO_.cVendor, JoinType.INNER).get(CVendor_.id),
        criteria.getCVendorId().getEquals()
      ));
    }
    if (criteria.getPoNo() != null) {
      queryPredicates.add(criteriaBuilder.equal(
        root.get(MMatchPO_.poNo), criteria.getPoNo().getEquals()
      ));
    }
    if (criteria.getReceiptNo() != null) {
      queryPredicates.add(criteriaBuilder.equal(
        root.get(MMatchPO_.receiptNo), criteria.getReceiptNo().getEquals()
      ));
    }
    if (criteria.getDeliveryNo() != null) {
      queryPredicates.add(criteriaBuilder.equal(
        root.get(MMatchPO_.deliveryNo), criteria.getDeliveryNo().getEquals()
      ));
    }
    if (criteria.getmMatchType() != null) {
      queryPredicates.add(criteriaBuilder.equal(
        root.get(MMatchPO_.mMatchType), criteria.getmMatchType().getEquals()
      ));
    }
    if (criteria.getReceiptDate() != null) {
      LocalDateFilter receiptDateFilter = criteria.getReceiptDate();
      
      if (receiptDateFilter.getGreaterThanOrEqual() != null) {
        queryPredicates.add(criteriaBuilder.greaterThanOrEqualTo(
          root.get(MMatchPO_.receiptDate), criteria.getReceiptDate().getGreaterThanOrEqual()
        ));
      }
      if (receiptDateFilter.getLessThanOrEqual() != null) {
        queryPredicates.add(criteriaBuilder.lessThanOrEqualTo(
          root.get(MMatchPO_.receiptDate), criteria.getReceiptDate().getLessThanOrEqual()
        ));
      }
    }

    queryPredicates.add(criteriaBuilder.not(criteriaBuilder.exists(subQuery)));
    return queryPredicates.toArray(new Predicate[] {});
  }
}
