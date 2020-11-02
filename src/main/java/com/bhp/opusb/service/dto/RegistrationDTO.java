package com.bhp.opusb.service.dto;

import java.util.List;
import java.util.Map;

import com.bhp.opusb.domain.enumeration.VendorType;

public class RegistrationDTO {
    CompanyProfile companyProfile;
    List<Long> businesses;
    List<CRegistrationDocumentDTO> mainDocuments;
    List<CRegistrationDocumentDTO> additionalDocuments;
    List<AdUserDTO> contacts;
    List<CFunctionaryDTO> functionaries;
    List<CVendorBankAcctDTO> payments;
    List<CVendorTaxDTO> taxes;
    Map<String, Boolean> taxInformations;

    public CompanyProfile getCompanyProfile() {
        return companyProfile;
    }

    public void setCompanyProfile(CompanyProfile companyProfile) {
        this.companyProfile = companyProfile;
    }

    public List<Long> getBusinesses() {
        return businesses;
    }

    public void setBusinesses(List<Long> businesses) {
        this.businesses = businesses;
    }

    public List<CRegistrationDocumentDTO> getMainDocuments() {
        return mainDocuments;
    }

    public void setMainDocuments(List<CRegistrationDocumentDTO> mainDocuments) {
        this.mainDocuments = mainDocuments;
    }

    public List<CRegistrationDocumentDTO> getAdditionalDocuments() {
        return additionalDocuments;
    }

    public void setAdditionalDocuments(List<CRegistrationDocumentDTO> additionalDocuments) {
        this.additionalDocuments = additionalDocuments;
    }

    public List<AdUserDTO> getContacts() {
        return contacts;
    }

    public void setContacts(List<AdUserDTO> contacts) {
        this.contacts = contacts;
    }

    public List<CFunctionaryDTO> getFunctionaries() {
        return functionaries;
    }

    public void setFunctionaries(List<CFunctionaryDTO> functionaries) {
        this.functionaries = functionaries;
    }

    public List<CVendorBankAcctDTO> getPayments() {
        return payments;
    }

    public void setPayments(List<CVendorBankAcctDTO> payments) {
        this.payments = payments;
    }

    public List<CVendorTaxDTO> getTaxes() {
        return taxes;
    }

    public void setTaxes(List<CVendorTaxDTO> taxes) {
        this.taxes = taxes;
    }

    public Map<String, Boolean> getTaxInformations() {
        return taxInformations;
    }

    public void setTaxInformations(Map<String, Boolean> taxInformations) {
        this.taxInformations = taxInformations;
    }

    public class CompanyProfile {
        private String name;
        private VendorType type;
        private Boolean branch;
        private String phone;
        private String fax;
        private String email;
        private String website;
        private String npwp;
        private String npwpName;
        private String npwpAddress;
        private Long npwpCityId;
        private String npwpPostalCode;
        private String address;
        private Long cityId;
        private String postalCode;
        private Long fileId;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public VendorType getType() {
            return type;
        }

        public void setType(VendorType type) {
            this.type = type;
        }

        public Boolean getBranch() {
            return branch;
        }

        public void setBranch(Boolean branch) {
            this.branch = branch;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getFax() {
            return fax;
        }

        public void setFax(String fax) {
            this.fax = fax;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getWebsite() {
            return website;
        }

        public void setWebsite(String website) {
            this.website = website;
        }

        public String getNpwp() {
            return npwp;
        }

        public void setNpwp(String npwp) {
            this.npwp = npwp;
        }

        public String getNpwpName() {
            return npwpName;
        }

        public void setNpwpName(String npwpName) {
            this.npwpName = npwpName;
        }

        public String getNpwpAddress() {
            return npwpAddress;
        }

        public void setNpwpAddress(String npwpAddress) {
            this.npwpAddress = npwpAddress;
        }

        public Long getNpwpCityId() {
            return npwpCityId;
        }

        public void setNpwpCityId(Long npwpCityId) {
            this.npwpCityId = npwpCityId;
        }

        public String getNpwpPostalCode() {
            return npwpPostalCode;
        }

        public void setNpwpPostalCode(String npwpPostalCode) {
            this.npwpPostalCode = npwpPostalCode;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public Long getCityId() {
            return cityId;
        }

        public void setCityId(Long cityId) {
            this.cityId = cityId;
        }

        public String getPostalCode() {
            return postalCode;
        }

        public void setPostalCode(String postalCode) {
            this.postalCode = postalCode;
        }

        public Long getFileId() {
            return fileId;
        }

        public void setFileId(Long fileId) {
            this.fileId = fileId;
        }
    }

}