package com.bhp.opusb.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface GenericDocumentRepository<T, I> extends JpaRepository<T, I>, JpaSpecificationExecutor<T> {
  
  /**
   * Count the records between the specified range of date.
   * @param startDate
   * @param endDate
   * @return
   */
  long countByDateTrxBetween(LocalDate startDate, LocalDate endDate);
}
