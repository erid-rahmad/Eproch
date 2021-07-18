package com.bhp.opusb.repository;

import java.util.List;

import com.bhp.opusb.domain.CPrequalificationEventLine;
import com.bhp.opusb.domain.view.MinMaxView;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CPrequalificationEventLine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CPrequalificationEventLineRepository extends JpaRepository<CPrequalificationEventLine, Long>, JpaSpecificationExecutor<CPrequalificationEventLine> {

    List<CPrequalificationEventLine> findByPrequalificationEvent_IdOrderBySequence(Long id);

    @Query("SELECT MAX(l.sequence) AS maxSequence, MIN(l.sequence) AS minSequence FROM CPrequalificationEventLine l INNER JOIN l.prequalificationEvent AS e WHERE e.id = ?1")
    MinMaxView findMinMaxSequence(Long eventId);
}
