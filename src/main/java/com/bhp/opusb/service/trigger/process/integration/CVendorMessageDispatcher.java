package com.bhp.opusb.service.trigger.process.integration;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.bhp.opusb.config.ApplicationProperties;
import com.bhp.opusb.domain.AiExchangeOut;
import com.bhp.opusb.domain.CVendor;
import com.bhp.opusb.domain.MVerification;
import com.bhp.opusb.domain.enumeration.AiStatus;
import com.bhp.opusb.repository.AiExchangeOutRepository;
import com.bhp.opusb.repository.CVendorRepository;
import com.bhp.opusb.service.dto.CVendorDTO;
import com.bhp.opusb.service.dto.ProcessResult;
import com.bhp.opusb.service.dto.TriggerResult;
import com.bhp.opusb.service.trigger.ProcessTrigger;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service("cVendorMessageDispatcher")
public class CVendorMessageDispatcher implements ProcessTrigger {

  private final Logger logger = LoggerFactory.getLogger(CVendorMessageDispatcher.class);

  public static final String BEAN_NAME = "cVendorMessageDispatcher";
  public static final String KEY_PAYLOAD = "payload";

  private final RestTemplate restTemplate = new RestTemplate();
  private final ObjectMapper objectMapper;
  private final ApplicationProperties properties;
  private final CVendorRepository cVendorRepository;
  private final AiExchangeOutRepository aiExchangeOutRepository;
  //private final CVendorLocationMapper cVendorLocationMapper;

  public CVendorMessageDispatcher(ObjectMapper objectMapper, ApplicationProperties properties,
      CVendorRepository cVendorRepository, AiExchangeOutRepository aiExchangeOutRepository
      //CVendorLocationRepository cVendorLocationRepository, CVendorLocationMapper cVendorLocationMapper,
      ) {
    this.objectMapper = objectMapper;
    this.properties = properties;
    this.cVendorRepository = cVendorRepository;
    //this.cVendorLocationRepository = cVendorLocationRepository;
    this.aiExchangeOutRepository = aiExchangeOutRepository;
    //this.cVendorLocationMapper = cVendorLocationMapper;
  }

  @Override
  public TriggerResult run(Map<String, Object> params) {
    final CVendorDTO payload = (CVendorDTO) params.get(KEY_PAYLOAD);
    boolean success = true;
    String response = null;

    if (payload != null) {
      String message = null;

      try {
        message = objectMapper.writeValueAsString(payload);
        response = dispatchMessage(message);

        List<Object[]> supplierContractDto = cVendorRepository.getSupplierContact(payload.getId());
        System.out.println(supplierContractDto.size());
        for(Object[] x : supplierContractDto){
          VendorSupplierContact vsc = new VendorSupplierContact();
          vsc.setVendorCode((String)(x[0]==null?"":x[0]));
          vsc.setVendorSiteCode("JKT");//(String)x[1]);
          vsc.setFirstName((String)(x[2]==null?"":x[2]));
          vsc.setMiddleName("-");//(String)x[3]);
          vsc.setLastName((String)(x[4]==null?"":x[4]));
          vsc.setTitle("Mr.");//(String)x[5]);
          vsc.setEmailAddress((String)(x[6]==null?"":x[6]));
          vsc.setAreaCode("021");//(String)x[7]);
          vsc.setPhone((String)(x[8]==null?"":x[8]));
          vsc.setCreationDate(((java.sql.Date)x[9])==null?LocalDate.now():((java.sql.Date)x[9]).toLocalDate());
          vsc.setStatus("NEW");

          dispatchMessageContact(objectMapper.writeValueAsString(vsc));
        }

        List<Object[]> supplierDetailDto = cVendorRepository.getSupplierDetail(payload.getId());
        System.out.println(supplierDetailDto.size());
        for(Object[] x : supplierDetailDto){
          VendorSupplierDetail vsd = new VendorSupplierDetail();
          vsd.setVendorCode((String)x[0]);
          vsd.setVendorSiteCode("JKT");//(String)x[1]);
          vsd.setAddress1((String)(x[2]==null?"-":x[2]));
          vsd.setAddress2((String)(x[3]==null?"-":x[3]));
          vsd.setAddress3((String)(x[4]==null?"-":x[4]));
          vsd.setAddress4((String)(x[5]==null?"-":x[5]));
          vsd.setCountry((String)(x[6]==null?"ID":x[6]));
          vsd.setZip((String)(x[7]==null?"-":x[7]));
          vsd.setPhone((String)(x[8]==null?"-":x[8]));
          vsd.setAreaCode("021");//(String)x[9]);
          vsd.setEmail((String)(x[10]==null?"-":x[10]));
          vsd.setOrganizationId(((BigInteger)x[11]).longValueExact());
          vsd.setStatus("NEW");
          vsd.setPaySiteFlag("Y");
          vsd.setPurchasingSiteFlag("Y");
          vsd.setRfqOnlySiteFlag("N");

          dispatchMessageDetail(objectMapper.writeValueAsString(vsd));
        }
      } catch (JsonProcessingException | RestClientException e) {
        logger.warn("Failed dispatching supplier data. {}. {}", e.getLocalizedMessage(), payload);

        aiExchangeOutRepository.save(new AiExchangeOut()
          .entityType(CVendor.class.getName())
          .entityId(payload.getId())
          .payload(message)
          .status(AiStatus.ERROR));

        success = false;
      }

      return new ProcessResult().add("success", success).add("response", success ? response : payload);
    }

    return new ProcessResult().add("error", true).add("message", "There is no payload specified");
  }


  private String dispatchMessage(String message) throws RestClientException {
    final String url = properties.getIntegration().getEndpoint().getVendorIntegrationUrl();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("ai-exchange-out", CVendor.class.getSimpleName());

    logger.debug("Dispatching message to external system. body: {}", message);
    HttpEntity<String> request = new HttpEntity<>(message, headers);
    final ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

    logger.debug("Message dispatched. statusCode: {} - {}", response.getStatusCodeValue(), response.getStatusCode());
    return response.getBody();
  }

  private String dispatchMessageContact(String message) throws RestClientException {
    final String url = properties.getIntegration().getEndpoint().getVendorIntegrationContactUrl();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("ai-exchange-out", VendorSupplierContact.class.getSimpleName());

    logger.debug("Dispatching message to external system. body: {}", message);
    HttpEntity<String> request = new HttpEntity<>(message, headers);
    final ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

    logger.debug("Message dispatched. statusCode: {} - {}", response.getStatusCodeValue(), response.getStatusCode());
    return response.getBody();
  }

  private String dispatchMessageDetail(String message) throws RestClientException {
    final String url = properties.getIntegration().getEndpoint().getVendorIntegrationDetailUrl();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("ai-exchange-out", VendorSupplierDetail.class.getSimpleName());

    logger.debug("Dispatching message to external system. body: {}", message);
    HttpEntity<String> request = new HttpEntity<>(message, headers);
    final ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

    logger.debug("Message dispatched. statusCode: {} - {}", response.getStatusCodeValue(), response.getStatusCode());
    return response.getBody();
  }

  // DTO for integration
  public static class VendorSupplierContact {
    @JsonProperty("VENDOR_CODE")
    private String vendorCode;
    @JsonProperty("VENDOR_SITE_CODE")
    private String vendorSiteCode;
    @JsonProperty("FIRST_NAME")
    private String firstName;
    @JsonProperty("MIDDLE_NAME")
    private String middleName;
    @JsonProperty("LAST_NAME")
    private String lastName;
    @JsonProperty("TITLE")
    private String title;
    @JsonProperty("EMAIL_ADDRESS")
    private String emailAddress;
    @JsonProperty("AREA_CODE")
    private String areaCode;
    @JsonProperty("PHONE")
    private String phone;
    @JsonProperty("CREATION_DATE")
    private LocalDate creationDate;
    @JsonProperty("STATUS")
    private String status;

    public String getVendorCode() {
      return vendorCode;
    }
    public String getStatus() {
      return status;
    }
    public void setStatus(String status) {
      this.status = status;
    }
    public LocalDate getCreationDate() {
      return creationDate;
    }
    public void setCreationDate(LocalDate creationDate) {
      this.creationDate = creationDate;
    }
    public String getPhone() {
      return phone;
    }
    public void setPhone(String phone) {
      this.phone = phone;
    }
    public String getAreaCode() {
      return areaCode;
    }
    public void setAreaCode(String areaCode) {
      this.areaCode = areaCode;
    }
    public String getEmailAddress() {
      return emailAddress;
    }
    public void setEmailAddress(String emailAddress) {
      this.emailAddress = emailAddress;
    }
    public String getTitle() {
      return title;
    }
    public void setTitle(String title) {
      this.title = title;
    }
    public String getLastName() {
      return lastName;
    }
    public void setLastName(String lastName) {
      this.lastName = lastName;
    }
    public String getMiddleName() {
      return middleName;
    }
    public void setMiddleName(String middleName) {
      this.middleName = middleName;
    }
    public String getFirstName() {
      return firstName;
    }
    public void setFirstName(String firstName) {
      this.firstName = firstName;
    }
    public String getVendorSiteCode() {
      return vendorSiteCode;
    }
    public void setVendorSiteCode(String vendorSiteCode) {
      this.vendorSiteCode = vendorSiteCode;
    }
    public void setVendorCode(String vendorCode) {
      this.vendorCode = vendorCode;
    }
  }

  public static class VendorSupplierDetail {
    @JsonProperty("VENDOR_CODE")
    private String vendorCode;
    @JsonProperty("VENDOR_SITE_CODE")
    private String vendorSiteCode;
    @JsonProperty("ADDRESS_LINE1")
    private String address1;
    @JsonProperty("ADDRESS_LINE2")
    private String address2;
    @JsonProperty("ADDRESS_LINE3")
    private String address3;
    @JsonProperty("ADDRESS_LINE4")
    private String address4;
    @JsonProperty("COUNTRY")
    private String country;
    @JsonProperty("ZIP")
    private String zip;
    @JsonProperty("PHONE")
    private String phone;
    @JsonProperty("AREA_CODE")
    private String areaCode;
    @JsonProperty("EMAIL_ADDRESS")
    private String email;
    @JsonProperty("ORG_ID")
    private Long organizationId;
    @JsonProperty("PURCHASING_SITE_FLAG")
    private String purchasingSiteFlag;
    @JsonProperty("PAY_SITE_FLAG")
    private String paySiteFlag;
    @JsonProperty("RFQ_ONLY_SITE_FLAG")
    private String rfqOnlySiteFlag;
    @JsonProperty("STATUS")
    private String status;
    
    public String getVendorCode() {
      return vendorCode;
    }
    public String getStatus() {
      return status;
    }
    public void setStatus(String status) {
      this.status = status;
    }
    public String getRfqOnlySiteFlag() {
      return rfqOnlySiteFlag;
    }
    public void setRfqOnlySiteFlag(String rfqOnlySiteFlag) {
      this.rfqOnlySiteFlag = rfqOnlySiteFlag;
    }
    public String getPaySiteFlag() {
      return paySiteFlag;
    }
    public void setPaySiteFlag(String paySiteFlag) {
      this.paySiteFlag = paySiteFlag;
    }
    public String getPurchasingSiteFlag() {
      return purchasingSiteFlag;
    }
    public void setPurchasingSiteFlag(String purchasingSiteFlag) {
      this.purchasingSiteFlag = purchasingSiteFlag;
    }
    public Long getOrganizationId() {
      return organizationId;
    }
    public void setOrganizationId(Long organizationId) {
      this.organizationId = organizationId;
    }
    public String getEmail() {
      return email;
    }
    public void setEmail(String email) {
      this.email = email;
    }
    public String getAreaCode() {
      return areaCode;
    }
    public void setAreaCode(String areaCode) {
      this.areaCode = areaCode;
    }
    public String getPhone() {
      return phone;
    }
    public void setPhone(String phone) {
      this.phone = phone;
    }
    public String getZip() {
      return zip;
    }
    public void setZip(String zip) {
      this.zip = zip;
    }
    public String getCountry() {
      return country;
    }
    public void setCountry(String country) {
      this.country = country;
    }
    public String getAddress4() {
      return address4;
    }
    public void setAddress4(String address4) {
      this.address4 = address4;
    }
    public String getAddress3() {
      return address3;
    }
    public void setAddress3(String address3) {
      this.address3 = address3;
    }
    public String getAddress2() {
      return address2;
    }
    public void setAddress2(String address2) {
      this.address2 = address2;
    }
    public String getAddress1() {
      return address1;
    }
    public void setAddress1(String address1) {
      this.address1 = address1;
    }
    public String getVendorSiteCode() {
      return vendorSiteCode;
    }
    public void setVendorSiteCode(String vendorSiteCode) {
      this.vendorSiteCode = vendorSiteCode;
    }
    public void setVendorCode(String vendorCode) {
      this.vendorCode = vendorCode;
    }
    
  }

}
