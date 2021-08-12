package com.bhp.opusb.repository;
import com.bhp.opusb.domain.PaDashboard;
import com.bhp.opusb.service.dto.DashboardChartDTO;

import java.util.Optional;
import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PaDashboard entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaDashboardRepository extends JpaRepository<PaDashboard, Long>, JpaSpecificationExecutor<PaDashboard> {

    @Query(value = "" 
    + "WITH xTblCostCtr AS ( "
    + "    SELECT DISTINCT "
    + "        cost_center_id, trx_month "
    + "   FROM m_purchase_order "
    + "   CROSS JOIN generate_series( "
    + "        make_date(CAST (EXTRACT(YEAR FROM NOW()) AS INT), 1, 1), "
    + "         make_date(CAST (EXTRACT(YEAR FROM NOW()) AS INT), 12, 1), "
    + "        '1 month' "
    + "    ) trx_month "
    + "    WHERE active = true "
    + "        AND document_status = 'APV' "
    + "        AND EXTRACT(YEAR FROM date_trx) = EXTRACT(YEAR FROM NOW()) "
    + "    ), "
    + "    xTblPO AS ( "
    + "        SELECT PO.cost_center_id, EXTRACT(MONTH FROM PO.date_trx) trx_month, SUM(grand_total) totalSpend "
    + "        FROM  m_purchase_order PO "
    + "        WHERE PO.active = true "
    + "            AND PO.document_status = 'APV' "
    + "            AND EXTRACT(YEAR FROM PO.date_trx) = EXTRACT(YEAR FROM NOW()) "
    + "        GROUP BY PO.cost_center_id, EXTRACT(MONTH FROM PO.date_trx) "
    + "    ) "
    + "    SELECT CC.name AS legendLabel, TO_CHAR(HD.trx_month, 'FMMon') || ' ' || TO_CHAR(HD.trx_month, 'FMYYYY') AS xAxisLabel,  "
    + "        COALESCE(PO.totalSpend, 0.0) dataValue "
    + "    FROM xTblCostCtr HD "
    + "   INNER JOIN c_cost_center CC ON HD.cost_center_id = CC.id "
    + "    LEFT JOIN xTblPO PO ON HD.cost_center_id = PO.cost_center_id "
    + "                        AND EXTRACT(MONTH FROM HD.trx_month) = PO.trx_month "
    + "    WHERE 1=1 "
    + "   ORDER BY CC.name, HD.trx_month", nativeQuery = true)
    List<Object[]> getSpendByCostCtr();

    @Query(value = ""
    + " WITH xTblProdAmount AS ( "
    + " 	SELECT "
    + " 		product_id, SUM(order_amount) totalAmount "
    + " 	FROM m_purchase_order_line POD "
    + " 	WHERE EXISTS(SELECT 1 FROM m_purchase_order PO WHERE PO.id = POD.purchase_order_id "
    + " 						AND PO.active = true AND PO.document_status = 'APV' "
    + " 						AND EXTRACT(YEAR FROM PO.date_trx) = EXTRACT(YEAR FROM NOW()) ) "
    + " 	GROUP BY product_id "
    + " ) "
    + " SELECT '' AS legendLabel, P.name AS xAxisLabel, "
    + " 	   PA.totalAmount AS dataValue "
    + " FROM xTblProdAmount PA "
    + " INNER JOIN c_product P ON P.id = PA.product_id "
    + "	WHERE 1=1 "
    + " ORDER BY PA.totalAmount DESC ", nativeQuery = true)
    List<Object[]> getProdPurchaseAmount();

}
