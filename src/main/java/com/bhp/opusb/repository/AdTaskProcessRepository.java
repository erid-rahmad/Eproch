package com.bhp.opusb.repository;

import java.util.List;

import com.bhp.opusb.domain.AdTask;
import com.bhp.opusb.domain.AdTaskProcess;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AdTaskProcess entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdTaskProcessRepository extends JpaRepository<AdTaskProcess, Long>, JpaSpecificationExecutor<AdTaskProcess> {
  List<AdTaskProcess> findByAdTask(AdTask adTask);
}
