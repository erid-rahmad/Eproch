package com.bhp.opusb.domain.enumeration;

/**
*   Vendor Document Type
*/
public enum VendorDocumentType{
    NEW_VENDOR ("SMT") , // new vendor status
    APPROVED_VENDOR("APV") , // approved vendor
    APB("ABP") ,
    REJECTED("RJT"); // rejected status

    private final String value;

    private VendorDocumentType(String value){
        this.value= value;
    }

    public String getValue(){
        return this.value;
    }

}