package com.bhp.opusb.repository;

import java.util.List;

import com.bhp.opusb.domain.MAuctionParticipant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MAuctionParticipant entity.
 */
@Repository
public interface MAuctionParticipantRepository extends JpaRepository<MAuctionParticipant, Long>, JpaSpecificationExecutor<MAuctionParticipant> {

  List<MAuctionParticipant> findByAuction_Id(Long id);
}
