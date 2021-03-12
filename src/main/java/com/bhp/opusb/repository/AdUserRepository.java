package com.bhp.opusb.repository;

import java.util.List;
import java.util.Optional;

import com.bhp.opusb.domain.AdUser;
import com.bhp.opusb.domain.CVendor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AdUser entity.
 */
@Repository
public interface AdUserRepository extends JpaRepository<AdUser, Long>, JpaSpecificationExecutor<AdUser> {

  Optional<AdUser> findByUserLogin(String login);
  List<AdUser> findBycVendor(CVendor cVendor);
  List<AdUser> findBycVendorIdAndActiveTrue(Long id);
}
