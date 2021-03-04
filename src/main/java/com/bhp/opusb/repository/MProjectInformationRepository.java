package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MProjectInformation;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the MProjectInformation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MProjectInformationRepository extends JpaRepository<MProjectInformation, Long>, JpaSpecificationExecutor<MProjectInformation> {

    @Query(value = "SELECT a FROM MProjectInformation a WHERE a.bidding.id=?1" )
    List<MProjectInformation> findByBindId(long a);

}
