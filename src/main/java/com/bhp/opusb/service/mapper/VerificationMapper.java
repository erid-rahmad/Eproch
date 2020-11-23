package com.bhp.opusb.service.mapper;

import java.time.LocalDate;

import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.AdUser;
import com.bhp.opusb.domain.CCurrency;
import com.bhp.opusb.domain.CVendor;
import com.bhp.opusb.domain.MVerification;
import com.bhp.opusb.service.dto.VerificationDTO.Verification;

public class VerificationMapper {

    private final ADOrganization organization;

    public VerificationMapper(ADOrganization organization) {
      this.organization = organization;
    }

    public MVerification toMVerification(Verification verification) {
        MVerification mVerification = new MVerification();

        if (verification.getCurrencyId() != null) {
            CCurrency cCurrency = new CCurrency();
            cCurrency.setId(verification.getCurrencyId());
            mVerification.setCurrency(cCurrency);
        }

        if (verification.getVendorId() != null) {
            CVendor cVendor= new CVendor();
            cVendor.setId(verification.getVendorId());
            mVerification.setVendor(cVendor);
        }

        if (verification.getPicUserId() != null) {
            AdUser adUser = new AdUser();
            adUser.setId(verification.getPicUserId());
            mVerification.setPic(adUser);
        }

        if(verification.getId() != null){
            mVerification.setId(verification.getId());
            mVerification.verificationNo(verification.getVerificationNo())
                .verificationDate(verification.getVerificationDate());
        }else{
            mVerification.verificationNo(String.valueOf(randomTimestamp()))
                .verificationDate(LocalDate.now());
        }

        mVerification.active(true)
            .adOrganization(organization)
            .verificationStatus("Draft")
            .description(verification.getDescription())
            .invoiceNo(verification.getInvoiceNo())
            .invoiceDate(verification.getInvoiceDate())
            .taxInvoice(verification.getTaxInvoice())
            .taxDate(verification.getTaxDate())
            .totalLines(verification.getTotalLines())
            .taxAmount(verification.getTaxAmount())
            .grandTotal(verification.getGrandTotal())
            .foreignGrandTotal(verification.getForeignGrandTotal())
            .foreignTaxAmount(verification.getForeignTaxAmount())
            .dataSubmit(verification.getDataSubmit())
            .dateAcct(verification.getDateAcct());

        return mVerification;
    }

    public static long randomTimestamp() {
        long timestamp = System.currentTimeMillis();
        return timestamp;
    }

}
