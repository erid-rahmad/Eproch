package com.bhp.opusb.repository;

import com.bhp.opusb.domain.CQuestionCategory;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CQuestionCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CQuestionCategoryRepository extends JpaRepository<CQuestionCategory, Long>, JpaSpecificationExecutor<CQuestionCategory> {
}
