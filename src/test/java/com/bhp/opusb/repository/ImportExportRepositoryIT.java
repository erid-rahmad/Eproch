package com.bhp.opusb.repository;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.util.ImportExportUtil;
import com.bhp.opusb.util.ImportExportUtil.CsvHeaderMeta;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

@SpringBootTest(classes = OpusWebApp.class, properties = {
  "jhipster.logging.use-json-format=false"
})
@Transactional
class ImportExportRepositoryIT {

  private static final Logger log = LoggerFactory.getLogger(ImportExportRepositoryIT.class);

  @Autowired
  private ImportExportRepository importExportRepository;

  @Test
  void assertThatCsvMasterHeaderParsed() {
    String baseTable = "m_verification";
    String header = "ad_organization_id@ad_organization.code";
    CsvHeaderMeta meta = ImportExportUtil.parseCsvHeader(baseTable, header);
    assertThat(meta.getBaseTable(), is(baseTable));
    assertThat(meta.getBaseColumn(), is("ad_organization_id"));
    assertThat(meta.getParentTable(), is("ad_organization"));
    assertThat(meta.getParentColumn(), is("code"));
  }

  @Test
  void assertThatCsvDetailHeaderParsed() {
    String baseTable = "m_verification";
    String header = "m_verification_line>c_currency_id@c_currency.code";
    CsvHeaderMeta meta = ImportExportUtil.parseCsvHeader(baseTable, header);
    assertThat(meta.getMasterTable(), is(baseTable));
    assertThat(meta.getBaseTable(), is("m_verification_line"));
    assertThat(meta.getBaseColumn(), is("c_currency_id"));
    assertThat(meta.getParentTable(), is("c_currency"));
    assertThat(meta.getParentColumn(), is("code"));
  }

  // @Test
  // @WithMockUser(
  //   username = "superuser",
  //   authorities = {"ROLE_USER", "ROLE_SYSTEM"}
  // )
  @Transactional
  void assertThatExportSqlBuilt() {
    String sql = importExportRepository.buildSql("m_verification",
        new String[] { "ad_organization_id@ad_organization.code", "ad_organization_id@ad_organization.name", "approved",
            "ap_reversed", "c_tax_category_id@c_tax_category.name", "c_tax_id@c_tax.name",
            "currency_id@c_currency.code", "data_submit", "date_acct", "date_approve", "date_reject", "date_submit",
            "date_trx", "description", "doc_type", "document_action", "document_no", "document_status", "due_date",
            "foreign_grand_total", "foreign_tax_amount", "grand_total", "invoice_ap", "invoice_date", "invoice_no",
            "match_po_currency_id@c_currency.code", "pay_amt", "pay_date", "pay_status", "processed", "receipt_no",
            "receipt_reversed", "tax_amount", "tax_date", "tax_invoice", "total_lines", "vendor_id@c_vendor.code",
            "vendor_location_id@c_vendor_location.tax_invoice_address", "vendor_to_id@c_vendor.code", "withholding_amt",
            "m_verification_line>ad_organization_id@ad_organization.code",
            "m_verification_line>ad_organization_id@ad_organization.name", "m_verification_line>ap_reversed",
            "m_verification_line>c_cost_center_id@c_cost_center.code",
            "m_verification_line>c_currency_id@c_currency.code", "m_verification_line>c_doc_type",
            "m_verification_line>c_doc_type_mr", "m_verification_line>conversion_rate",
            "m_verification_line>c_tax_category_id@c_tax_category.name", "m_verification_line>c_tax_id@c_tax.name",
            "m_verification_line>delivery_no", "m_verification_line>description", "m_verification_line>foreign_actual",
            "m_verification_line>foreign_tax_amount", "m_verification_line>foreign_total_lines",
            "m_verification_line>item_desc_1", "m_verification_line>item_desc_2", "m_verification_line>line_no",
            "m_verification_line>line_no_mr", "m_verification_line>line_no_po", "m_verification_line>match_type",
            "m_verification_line>order_no", "m_verification_line>order_suffix", "m_verification_line>pay_stat",
            "m_verification_line>po_no", "m_verification_line>price_actual",
            "m_verification_line>product_id@c_product.code", "m_verification_line>product_id@c_product.name",
            "m_verification_line>qty", "m_verification_line>receipt_no", "m_verification_line>receipt_reversed",
            "m_verification_line>receive_date", "m_verification_line>receive_no", "m_verification_line>taxable",
            "m_verification_line>tax_amount", "m_verification_line>total_lines",
            "m_verification_line>uom_id@c_unit_of_measure.code", "m_verification_line>verification_no" });
    
    log.info("Sql: {}", sql);
  }

}
