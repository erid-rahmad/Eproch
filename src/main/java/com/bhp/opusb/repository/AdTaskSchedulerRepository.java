package com.bhp.opusb.repository;

import com.bhp.opusb.domain.AdTaskScheduler;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AdTaskScheduler entity.
 */
@Repository
public interface AdTaskSchedulerRepository extends JpaRepository<AdTaskScheduler, Long>, JpaSpecificationExecutor<AdTaskScheduler> {

  @Query(
    "SELECT new AdTaskScheduler(active, trigger, cronExpression, periodicCount, periodicUnit) "
    + "FROM AdTaskScheduler "
    + "WHERE id = ?1")
    AdTaskScheduler findOneById(Long id);
}
