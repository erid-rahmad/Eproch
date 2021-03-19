package com.bhp.opusb.repository;

import java.util.List;

import com.bhp.opusb.domain.MProjectInformation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MProjectInformation entity.
 */
@Repository
public interface MProjectInformationRepository extends JpaRepository<MProjectInformation, Long>, JpaSpecificationExecutor<MProjectInformation> {

    @Query(value = "SELECT a FROM MProjectInformation a WHERE a.bidding.id=?1" )
    List<MProjectInformation> findByBindId(long a);

}
