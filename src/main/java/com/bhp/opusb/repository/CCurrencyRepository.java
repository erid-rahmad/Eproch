package com.bhp.opusb.repository;

import java.util.Optional;

import com.bhp.opusb.domain.CCurrency;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CCurrency entity.
 */
@Repository
public interface CCurrencyRepository extends JpaRepository<CCurrency, Long>, JpaSpecificationExecutor<CCurrency> {

  Optional<CCurrency> findFirstByCode(String code);
}
