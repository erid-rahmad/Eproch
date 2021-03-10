package com.bhp.opusb.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
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

  @Modifying(clearAutomatically = true)
  @Query("UPDATE #{#entityName} e " +
    "SET e.documentAction = :action, " +
    "e.documentStatus = :status, " +
    "e.approved = :approved, " +
    "e.processed = :processed " +
    "WHERE e.id = :id"
  )
  int updateDocumentStatus(@Param("id") long id, @Param("action") String action, @Param("status") String status,
      @Param("approved") boolean approved, @Param("processed") boolean processed);
}
