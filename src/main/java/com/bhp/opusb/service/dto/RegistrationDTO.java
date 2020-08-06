package com.bhp.opusb.service.dto;

import java.util.List;
import java.util.Map;

import com.bhp.opusb.domain.CCity;
import com.bhp.opusb.domain.enumeration.VendorType;

public class RegistrationDTO {
    LoginDetail loginDetail;
    CompanyProfile companyProfile;
    List<Long> businesses;
    List<CRegistrationDocumentDTO> mainDocument;
    List<CRegistrationDocumentDTO> additionalDocument;
    List<CPersonInChargeDTO> contacts;
    List<CPersonInChargeDTO> functionaries;
    List<CVendorBankAcctDTO> payments;
    List<CVendorTaxDTO> taxRates;
    Map<String, Boolean> taxInformations;

    public class LoginDetail{
        String login;
        String email;
        String password;

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        
    }

    public class CompanyProfile{
        String name;
        VendorType type;
        Boolean branch;
        String phone;
        String fax;
        String email;
        String website;
        Long npwp;
        String npwpName;

        String npwpAddress;
        Long npwpCountry;
        Long npwpRegion;
        Long npwpCity;
        String npwpPostalCode;
        String address;
        Long country;
        Long region;
        Long city;
        String postalCode;

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

        public Long getNpwp() {
            return npwp;
        }

        public void setNpwp(Long npwp) {
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

        public Long getNpwpCountry() {
            return npwpCountry;
        }

        public void setNpwpCountry(Long npwpCountry) {
            this.npwpCountry = npwpCountry;
        }

        public Long getNpwpRegion() {
            return npwpRegion;
        }

        public void setNpwpRegion(Long npwpRegion) {
            this.npwpRegion = npwpRegion;
        }

        public Long getNpwpCity() {
            return npwpCity;
        }

        public void setNpwpCity(Long npwpCity) {
            this.npwpCity = npwpCity;
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

        public Long getCountry() {
            return country;
        }

        public void setCountry(Long country) {
            this.country = country;
        }

        public Long getRegion() {
            return region;
        }

        public void setRegion(Long region) {
            this.region = region;
        }

        public Long getCity() {
            return city;
        }

        public void setCity(Long city) {
            this.city = city;
        }

        public String getPostalCode() {
            return postalCode;
        }

        public void setPostalCode(String postalCode) {
            this.postalCode = postalCode;
        }
    }

    public LoginDetail getLoginDetail() {
        return loginDetail;
    }

    public void setLoginDetail(LoginDetail loginDetail) {
        this.loginDetail = loginDetail;
    }

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

    public List<CRegistrationDocumentDTO> getMainDocument() {
        return mainDocument;
    }

    public void setMainDocument(List<CRegistrationDocumentDTO> mainDocument) {
        this.mainDocument = mainDocument;
    }

    public List<CRegistrationDocumentDTO> getAdditionalDocument() {
        return additionalDocument;
    }

    public void setAdditionalDocument(List<CRegistrationDocumentDTO> additionalDocument) {
        this.additionalDocument = additionalDocument;
    }

    public List<CPersonInChargeDTO> getContacts() {
        return contacts;
    }

    public void setContacts(List<CPersonInChargeDTO> contacts) {
        this.contacts = contacts;
    }

    public List<CPersonInChargeDTO> getFunctionaries() {
        return functionaries;
    }

    public void setFunctionaries(List<CPersonInChargeDTO> functionaries) {
        this.functionaries = functionaries;
    }

    public List<CVendorBankAcctDTO> getPayments() {
        return payments;
    }

    public void setPayments(List<CVendorBankAcctDTO> payments) {
        this.payments = payments;
    }

    public List<CVendorTaxDTO> getTaxRates() {
        return taxRates;
    }

    public void setTaxRates(List<CVendorTaxDTO> taxRates) {
        this.taxRates = taxRates;
    }

    public Map<String, Boolean> getTaxInformations() {
        return taxInformations;
    }

    public void setTaxInformations(Map<String, Boolean> taxInformations) {
        this.taxInformations = taxInformations;
    }

    

    
}