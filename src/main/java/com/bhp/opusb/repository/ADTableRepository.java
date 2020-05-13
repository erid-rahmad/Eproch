package com.bhp.opusb.repository;

import java.util.List;

import com.bhp.opusb.domain.ADTable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ADTable entity.
 */
@Repository
public interface ADTableRepository extends JpaRepository<ADTable, Long>, JpaSpecificationExecutor<ADTable> {

  List<ADTable> findByName(String name);
}
