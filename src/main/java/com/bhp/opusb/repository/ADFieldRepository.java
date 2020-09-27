package com.bhp.opusb.repository;

import com.bhp.opusb.domain.ADField;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ADField entity.
 */
@Repository
public interface ADFieldRepository extends JpaRepository<ADField, Long>, JpaSpecificationExecutor<ADField> {

  @Modifying
  @Query(value = "DELETE FROM ad_field WHERE id = ?1", nativeQuery = true)
  void deleteById(Long id);
}
