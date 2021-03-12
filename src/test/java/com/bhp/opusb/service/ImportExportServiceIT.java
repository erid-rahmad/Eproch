package com.bhp.opusb.service;

import static org.junit.Assert.assertThat;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.enumeration.ADColumnType;
import com.bhp.opusb.repository.ADOrganizationRepository;
import com.bhp.opusb.repository.CCurrencyRepository;
import com.bhp.opusb.service.dto.ImportCsvColumn;
import com.bhp.opusb.service.dto.ImportParameterDTO;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(classes = OpusWebApp.class)
@Transactional
class ImportExportServiceIT {

  @Autowired
  private ImportExportService importExportService;

  @Autowired
  private ADOrganizationRepository adOrganizationRepository;

  @Autowired
  private CCurrencyRepository cCurrencyRepository;

  @Test
  @WithMockUser(
    username = "superuser",
    authorities = {"ROLE_USER", "ROLE_SYSTEM", "C_CURRENCY_WRITE", "AD_ORGANIZATION_WRITE"}
  )
  @Transactional
  void assertThatImported10Currencies() {
    InputStream input = getClass().getResourceAsStream("/data/currencies.csv");
    ImportParameterDTO parameterDTO = new ImportParameterDTO();
    Map<String, ImportCsvColumn> fieldsMap = new HashMap<>();
    fieldsMap.put("0", new ImportCsvColumn("code", ADColumnType.STRING));
    fieldsMap.put("1", new ImportCsvColumn("symbol", ADColumnType.STRING));
    fieldsMap.put("2", new ImportCsvColumn("name", ADColumnType.STRING));
    fieldsMap.put("4", new ImportCsvColumn("ad_organization_id@ad_organization.code", ADColumnType.STRING));
    fieldsMap.put("5", new ImportCsvColumn("ad_organization_id@ad_organization.name", ADColumnType.STRING));

    parameterDTO.setTableName("c_currency");
    parameterDTO.setMaxRows(10);
    parameterDTO.setFieldsMap(fieldsMap);

    importExportService.importCsv(parameterDTO, input);

    assertThat(adOrganizationRepository.count(), Is.is(1L));
    assertThat(cCurrencyRepository.count(), Is.is(10L));
  }
  
  @Test
  @WithMockUser(
    username = "superuser",
    authorities = {"ROLE_USER", "ROLE_SYSTEM", "AD_USER_WRITE", "JHI_USER_WRITE", "C_VENDOR_WRITE", "AD_ORGANIZATION_WRITE"})
  @Transactional
  void assertThatImported10Users() {
    InputStream input = getClass().getResourceAsStream("/data/users.csv");
    ImportParameterDTO parameterDTO = new ImportParameterDTO();
    Map<String, ImportCsvColumn> fieldsMap = new HashMap<>();
    fieldsMap.put("0", new ImportCsvColumn("user_id@jhi_user.login", ADColumnType.STRING));
    fieldsMap.put("1", new ImportCsvColumn("user_id@jhi_user.password_hash", ADColumnType.STRING));
    fieldsMap.put("2", new ImportCsvColumn("c_vendor_id@c_vendor.code", ADColumnType.STRING));
    fieldsMap.put("4", new ImportCsvColumn("vendor", ADColumnType.BOOLEAN));
    fieldsMap.put("5", new ImportCsvColumn("user_id@jhi_user.activated", ADColumnType.BOOLEAN));
    fieldsMap.put("7", new ImportCsvColumn("user_id@jhi_user.first_name", ADColumnType.STRING));
    fieldsMap.put("8", new ImportCsvColumn("user_id@jhi_user.email", ADColumnType.STRING));
    fieldsMap.put("9", new ImportCsvColumn("ad_organization_id@ad_organization.code", ADColumnType.STRING));
    fieldsMap.put("10", new ImportCsvColumn("ad_organization_id@ad_organization.name", ADColumnType.STRING));
    fieldsMap.put("11", new ImportCsvColumn("c_vendor_id@c_vendor.name", ADColumnType.STRING));
    fieldsMap.put("12", new ImportCsvColumn("c_vendor_id@c_vendor.location", ADColumnType.STRING));
    fieldsMap.put("13", new ImportCsvColumn("c_vendor_id@c_vendor.type", ADColumnType.STRING));
    fieldsMap.put("14", new ImportCsvColumn("c_vendor_id@c_vendor.payment_category", ADColumnType.STRING));
    fieldsMap.put("15", new ImportCsvColumn("c_vendor_id@c_vendor.active", ADColumnType.BOOLEAN));
    fieldsMap.put("16", new ImportCsvColumn("c_vendor_id@c_vendor.document_action", ADColumnType.STRING));
    fieldsMap.put("17", new ImportCsvColumn("c_vendor_id@c_vendor.document_status", ADColumnType.STRING));

    parameterDTO.setTableName("ad_user");
    parameterDTO.setMaxRows(10);
    parameterDTO.setFieldsMap(fieldsMap);

    importExportService.importCsv(parameterDTO, input);

    // assertThat(adOrganizationRepository.count(), Is.is(1L));
    // assertThat(cCurrencyRepository.count(), Is.is(10L));
  }
  
}
