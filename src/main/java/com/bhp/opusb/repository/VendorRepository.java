package com.bhp.opusb.repository;

import com.bhp.opusb.domain.Vendor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Vendor entity.
 */
@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long>, JpaSpecificationExecutor<Vendor> {

    @Query(value = "select distinct vendor from Vendor vendor left join fetch vendor.businessCategories",
        countQuery = "select count(distinct vendor) from Vendor vendor")
    Page<Vendor> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct vendor from Vendor vendor left join fetch vendor.businessCategories")
    List<Vendor> findAllWithEagerRelationships();

    @Query("select vendor from Vendor vendor left join fetch vendor.businessCategories where vendor.id =:id")
    Optional<Vendor> findOneWithEagerRelationships(@Param("id") Long id);
}
