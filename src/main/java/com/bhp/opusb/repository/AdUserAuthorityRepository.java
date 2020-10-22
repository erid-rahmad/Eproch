package com.bhp.opusb.repository;

import com.bhp.opusb.domain.AdUserAuthority;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AdUserAuthority entity.
 */
@Repository
public interface AdUserAuthorityRepository extends JpaRepository<AdUserAuthority, Long>, JpaSpecificationExecutor<AdUserAuthority> {

  @Modifying
  @Query(value = "DELETE FROM ad_user_authority WHERE id = ?1", nativeQuery = true)
  void deleteById(Long id);
}
