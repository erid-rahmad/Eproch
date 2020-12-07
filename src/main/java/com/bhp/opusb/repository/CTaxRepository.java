package com.bhp.opusb.repository;

import java.util.Optional;

import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CTax;
import com.bhp.opusb.domain.CTaxCategory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CTax entity.
 */
@Repository
public interface CTaxRepository extends JpaRepository<CTax, Long>, JpaSpecificationExecutor<CTax> {
  
  Optional<CTax> findFirstByNameAndTaxCategoryAndAdOrganization(String name, CTaxCategory taxCategory, ADOrganization org);
}
