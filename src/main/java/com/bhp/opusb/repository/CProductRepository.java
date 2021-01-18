package com.bhp.opusb.repository;

import java.util.Optional;

import com.bhp.opusb.domain.CProduct;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CProduct entity.
 */
@Repository
public interface CProductRepository extends JpaRepository<CProduct, Long>, JpaSpecificationExecutor<CProduct> {

  Optional<CProduct> findFirstByCode(String code);
  Optional<CProduct> findFirstByNameAndAdOrganizationId(String code, Long orgId);
}
