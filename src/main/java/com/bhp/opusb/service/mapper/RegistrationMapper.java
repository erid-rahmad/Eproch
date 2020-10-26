package com.bhp.opusb.service.mapper;

import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CAttachment;
import com.bhp.opusb.domain.CCity;
import com.bhp.opusb.domain.CLocation;
import com.bhp.opusb.domain.CVendor;
import com.bhp.opusb.service.dto.RegistrationDTO.CompanyProfile;

public class RegistrationMapper {

  private final ADOrganization organization;

  public RegistrationMapper(ADOrganization organization) {
    this.organization = organization;
  }

  public CVendor toVendor(CompanyProfile companyProfile) {
    CVendor vendor = new CVendor();

    if (companyProfile.getFileId() != null) {
      CAttachment taxIdAttachment = new CAttachment();
      taxIdAttachment.setId(companyProfile.getFileId());
      vendor.setTaxIdFile(taxIdAttachment);
    }

    vendor.active(true)
      .adOrganization(organization)
      .approvalStatus("PENDING")
      .branch(companyProfile.getBranch())
      .email(companyProfile.getEmail())
      .fax(companyProfile.getFax())
      .name(companyProfile.getName())
      .paymentCategory("RED")
      .phone(companyProfile.getPhone())
      .type(companyProfile.getType())
      .location(companyProfile.getLocation())
      .tin(companyProfile.getTin())
      .website(companyProfile.getWebsite())
      .taxIdName(companyProfile.getNpwpName())
      .taxIdNo(companyProfile.getNpwp());

    return vendor;
  }

  public CLocation toLocation(CompanyProfile companyProfile) {
    CLocation location = new CLocation();
    CCity city = new CCity();

    city.setId(companyProfile.getCityId());
    location.active(true)
      .adOrganization(organization)
      .city(city)
      .streetAddress(companyProfile.getAddress())
      .postalCode(companyProfile.getPostalCode());

    return location;
  }

  public CLocation toTaxLocation(CompanyProfile companyProfile) {
    CLocation location = new CLocation();
    CCity city = new CCity();

    city.setId(companyProfile.getNpwpCityId());
    location.active(true)
      .adOrganization(organization)
      .city(city)
      .streetAddress(companyProfile.getNpwpAddress())
      .postalCode(companyProfile.getNpwpPostalCode());

    return location;
  }
}
