package com.bhp.opusb.repository;

import java.util.List;

import com.bhp.opusb.domain.MAuctionSubmissionItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MAuctionSubmissionItem entity.
 */
@Repository
public interface MAuctionSubmissionItemRepository extends JpaRepository<MAuctionSubmissionItem, Long>, JpaSpecificationExecutor<MAuctionSubmissionItem> {

  @Modifying
  @Query("DELETE FROM MAuctionSubmissionItem i WHERE i.auctionItem.id NOT IN (:itemIds) AND i.auctionSubmission.id = :submissionId")
  void deleteByAuctionItemIdNotIn(@Param("itemIds") List<Long> itemIds, @Param("submissionId") Long submissionId);
}
