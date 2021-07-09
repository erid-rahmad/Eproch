package com.bhp.opusb.repository;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import com.bhp.opusb.domain.MContract;
import com.bhp.opusb.domain.MVendorPerformanceReport;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the MVendorPerformanceReport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MVendorPerformanceReportRepository
        extends JpaRepository<MVendorPerformanceReport, Long>, JpaSpecificationExecutor<MVendorPerformanceReport> {
    @Query(value = "select cast(coalesce(sum(mv.total_lines),0) as numeric(21, 2)) from m_verification mv where mv.vendor_id = ?1 "
            + "and invoice_date >= ?2", nativeQuery = true)
    BigDecimal getInvoiceCount(Long vendorId, Date date);

    @Query(value = "select cast(coalesce(sum(mv.total_lines),0) as numeric(21, 2)) from m_verification mv where mv.vendor_id = ?1 "
            + "and invoice_date >= ?2 and mv.pay_status='P'", nativeQuery = true)
    BigDecimal getInvoiceSpend(Long vendorId, Date date);

    @Query(value = "select cast(coalesce(sum(mpo.grand_total),0) as numeric(21, 2)) from m_purchase_order mpo where mpo.vendor_id = ?1 "
            + "and mpo.date_trx >= ?2", nativeQuery = true)
    BigDecimal getPoCount(Long vendorId, Date date);

    @Query(value = "select cast(coalesce(sum(mpo.grand_total),0) as numeric(21, 2)) from m_purchase_order mpo where mpo.vendor_id = ?1 "
            + "and mpo.date_trx >= ?2 and mpo.confirmed = true", nativeQuery = true)
    BigDecimal getPoSpend(Long vendorId, Date date);

    @Query(value = "select cast(coalesce(sum(mc.price),0) as numeric(21, 2)) from m_contract mc where mc.vendor_id = ?1 and mc.start_date >= ?2", nativeQuery = true)
    BigDecimal getAwardedSpend(Long vendorId, Date date);

    @Query(value = "select count(mvi.id) from m_vendor_invitation mvi where bidding_id in "
        + "(select mbs.bidding_id from m_bidding_submission mbs where mbs.vendor_id=?1) "
        + "and mvi.created_date >= ?2", nativeQuery = true)
    Integer getEventInvited(Long vendorId, Date date);

    @Query(value = "select count(mc.id) from m_contract mc where mc.vendor_id = ?1 and current_date between start_date and expiration_date", nativeQuery = true)
    Integer getActiveContractCount(Long vendorId);

    @Query(value = "select count(mpbm.id) from m_pre_bid_meeting mpbm where mpbm.bidding_schedule_id in "
        + "(select mbs.bidding_schedule_id from m_bidding_submission mbs where mbs.vendor_id=?1)", nativeQuery = true)
    Integer getParticipationCount(Long vendorId);

    @Query(value = "select mc.name, mc.start_date, mc.expiration_date, mc.price from m_contract mc where mc.vendor_id = ?1 and current_date between start_date and expiration_date", nativeQuery = true)
    List<Object[]> getActiveContracts(Long vendorId);

    @Query(value = "select mc.\"name\", mc.document_no, ju.login as pic, mc.start_date, coalesce(avg(mve.score),0) total_score "
        +"from m_contract mc "
        +"left join m_bidding mb on mb.id = mc.bidding_id "
        +"left join m_rfq rfq on rfq.id = mb.quotation_id "
        +"left join m_vendor_evaluation mve on mve.contract_id = mc.id "
        +"left join jhi_user ju on mc.pic_user_id = ju.id "
        +"where mc.vendor_id = ?1 and mc.start_date >= ?2 "
        +"group by mc.\"name\", mc.document_no, ju.login, mc.start_date "
        +"order by mc.start_date", nativeQuery = true)
    List<Object[]> getProjectPerformances(Long vendorId, Date date);

    @Query(value = "select mc.vendor_id, cv.\"name\", cast(coalesce(sum(mpo.grand_total),0) as numeric(21,2)) ordered, "
        +"cast(coalesce(avg(mve.score),0) as numeric(21,2)) quality_score "
        +"from m_contract mc "
        +"left join m_purchase_order mpo on mpo.vendor_id = mc.vendor_id "
        +"left join m_vendor_evaluation mve on mve.contract_id = mc.id "
        +"inner join c_vendor cv on cv.id = mc.vendor_id "
        +"group by mc.vendor_id, cv.\"name\" order by quality_score desc", nativeQuery = true)
    List<Object[]> getAllVendorPerformance();

    @Query(value = "select mc.vendor_id, cv.\"name\", cast(coalesce(sum(mpo.grand_total),0) as numeric(21,2)) ordered, "
        +"cast(coalesce(avg(mve.score),0) as numeric(21,2)) quality_score "
        +"from m_contract mc "
        +"left join m_purchase_order mpo on mpo.vendor_id = mc.vendor_id "
        +"left join m_vendor_evaluation mve on mve.contract_id = mc.id "
        +"inner join c_vendor cv on cv.id = mc.vendor_id "
        +"where mc.ad_organization_id = ?1 "
        +"group by mc.vendor_id, cv.\"name\" order by quality_score desc", nativeQuery = true)
    List<Object[]> getAllVendorPerformance(Long adOrganizationId);
}
