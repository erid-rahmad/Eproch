package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MBiddingSubmissionLine;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * Spring Data  repository for the MBiddingSubmissionLine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MBiddingSubmissionLineRepository extends JpaRepository<MBiddingSubmissionLine, Long>, JpaSpecificationExecutor<MBiddingSubmissionLine> {
//    List<MBiddingSubmissionLine> findBymBiddingSubmission(Long id);

    @Query(value = "SELECT a FROM MBiddingSubmissionLine a WHERE a.mBiddingSubmission.id=?1" )
    Set<MBiddingSubmissionLine> findbyheaders(long a);

    @Query(value = "SELECT a FROM MBiddingSubmissionLine a WHERE a.mBiddingSubmission.id=?1" )
    List<MBiddingSubmissionLine> findbyheader(long a);
}
