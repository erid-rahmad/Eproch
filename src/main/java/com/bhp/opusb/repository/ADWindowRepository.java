package com.bhp.opusb.repository;

import com.bhp.opusb.domain.ADWindow;
import com.bhp.opusb.domain.view.NamedEntityView;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ADWindow entity.
 */
@Repository
public interface ADWindowRepository extends JpaRepository<ADWindow, Long>, JpaSpecificationExecutor<ADWindow> {
  
  NamedEntityView findFirstById(Long id);
}
