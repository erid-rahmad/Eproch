package com.bhp.opusb.repository;

import java.util.Optional;

import com.bhp.opusb.domain.CProductCategory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CProductCategory entity.
 */
@Repository
public interface CProductCategoryRepository extends JpaRepository<CProductCategory, Long>, JpaSpecificationExecutor<CProductCategory> {

  Optional<CProductCategory> findFirstByCode(String code);
}
