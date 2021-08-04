package com.bhp.opusb.repository;

import java.util.Optional;
import java.util.Collection;
import java.util.List;

import com.bhp.opusb.domain.CVendor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CVendor entity.
 */
@Repository
public interface CVendorRepository extends JpaRepository<CVendor, Long>, JpaSpecificationExecutor<CVendor> {

  Optional<CVendor> findFirstByCode(String code);

  @Modifying
  @Query("UPDATE CVendor v SET v.documentAction = :action, v.documentStatus = :status, v.approved = true, v.processed = true WHERE v.id = :id")
  void updateDocumentStatus(@Param("id") long id, @Param("action") String action, @Param("status") String status);

  Optional<CVendor> findFirstByName(String name);

  @Query(value = "SELECT COUNT(1) FROM c_vendor vdr WHERE DATE_PART('day', NOW() - vdr.date_trx) < 31 AND active = true "
      +" AND COALESCE(NULLIF(?1, ''), vdr.document_status) = vdr.document_status", nativeQuery = true)
  Long getNewVendor(String status);

  @Query(value = "select cv.code \"vendor_code\", '' \"vendor_site_code\", ju.first_name, '' \"middle_name\", ju.last_name, '' \"title\", ju.email,"
      +"'' \"area_code\", au.phone, cv.date_trx, cv.document_status "
      +"from c_vendor cv "
      +"inner join c_vendor_location cvl ON cv.id = cvl.vendor_id "
      +"inner join c_location cl on cl.id = cvl.location_id "
      +"inner join c_city cc on cc.id = cl.city_id "
      +"inner join ad_user au on au.c_vendor_id = cv.id "
      +"inner join jhi_user ju on ju.id = au.user_id "
      +"where cv.id = ?1", nativeQuery = true)
  List<Object[]> getSupplierContact(Long id);

  @Query(value = "select cv.code \"vendor_code\", '' \"vendor_site_code\", cl.address_1, cl.address_2, cl.address_3, cl.address_4,"
      +"cc2.code \"country_code\", cl.postal_code, cv.phone, '' \"area_code\", cv.email, cv.ad_organization_id, cv.document_status "
      +"from c_vendor cv "
      +"inner join c_vendor_location cvl ON cv.id = cvl.vendor_id "
      +"inner join c_location cl on cl.id = cvl.location_id "
      +"inner join c_city cc on cc.id = cl.city_id "
      +"inner join c_country cc2 on cc2.id = cc.country_id "
      +"where cv.id = ?1", nativeQuery = true)
  List<Object[]> getSupplierDetail(Long id);
}
