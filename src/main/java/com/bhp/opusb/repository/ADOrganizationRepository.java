package com.bhp.opusb.repository;

import java.util.Optional;

import com.bhp.opusb.domain.ADOrganization;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ADOrganization entity.
 */
@Repository
public interface ADOrganizationRepository extends JpaRepository<ADOrganization, Long>, JpaSpecificationExecutor<ADOrganization> {

  Optional<ADOrganization> findFirstByCode(String code);
}
