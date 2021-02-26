package com.bhp.opusb.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

@NoRepositoryBean
public interface GenericDocumentRepository<T, I> extends JpaRepository<T, I>, JpaSpecificationExecutor<T> {
  
  /**
   * Count the records between the specified range of date.
   * @param startDate
   * @param endDate
   * @return
   */
  long countByDateTrxBetween(LocalDate startDate, LocalDate endDate);

  @Query("SELECT max(CAST(t.documentNo AS long)) FROM #{#entityName} t WHERE t.dateTrx BETWEEN :startDate AND :endDate")
  Long getMaxDocumentNoWithinDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
