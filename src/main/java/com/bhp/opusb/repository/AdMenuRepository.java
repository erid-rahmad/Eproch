package com.bhp.opusb.repository;

import java.util.List;

import com.bhp.opusb.domain.AdMenu;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AdMenu entity.
 */
@Repository
public interface AdMenuRepository extends JpaRepository<AdMenu, Long>, JpaSpecificationExecutor<AdMenu> {

  @EntityGraph(attributePaths = { "adMenus", "adWindow", "adForm" })
  List<AdMenu> findByParentMenuIsNullAndActiveTrue(Sort sort);
}
