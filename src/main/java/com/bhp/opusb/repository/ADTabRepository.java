package com.bhp.opusb.repository;

import java.util.Optional;

import com.bhp.opusb.domain.ADTab;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ADTab entity.
 */
@Repository
public interface ADTabRepository extends JpaRepository<ADTab, Long>, JpaSpecificationExecutor<ADTab> {

  Optional<ADTab> findFirstByAdWindow_idAndParentTabIsNull(Long windowId);
}
