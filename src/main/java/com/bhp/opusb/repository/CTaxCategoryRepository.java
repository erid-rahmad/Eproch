package com.bhp.opusb.repository;

import java.util.Optional;

import com.bhp.opusb.domain.CTaxCategory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CTaxCategory entity.
 */
@Repository
public interface CTaxCategoryRepository extends JpaRepository<CTaxCategory, Long>, JpaSpecificationExecutor<CTaxCategory> {

  Optional<CTaxCategory> findFirstByNameAndDescriptionAndAdOrganizationId(String name, String description, Long orgId);
}
