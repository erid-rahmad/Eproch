package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MBiddingSubmissionLine;
import com.bhp.opusb.domain.MSubmissionSubItem;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the MSubmissionSubItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MSubmissionSubItemRepository extends JpaRepository<MSubmissionSubItem, Long>, JpaSpecificationExecutor<MSubmissionSubItem> {

    @Query(value = "SELECT a FROM MSubmissionSubItem a WHERE a.mBiddingSubmissionLine.id=?1" )
    List<MSubmissionSubItem> findbyheader(long a);
}
