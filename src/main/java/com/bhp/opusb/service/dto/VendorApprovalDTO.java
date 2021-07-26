package com.bhp.opusb.service.dto;

public class VendorApprovalDTO {
	private Long vendorId;
	private Boolean approve;
	
	public VendorApprovalDTO(Long vendorId, Boolean approve) {
		this.vendorId= vendorId;
		this.approve= approve;
	}
	
	public VendorApprovalDTO() {}

	public Long getVendorId() {
		return vendorId;
	}

	public void setVendorId(Long vendorId) {
		this.vendorId = vendorId;
	}

	public Boolean getApprove() {
		return approve;
	}

	public void setApprove(Boolean approve) {
		this.approve = approve;
	}
	
	
}
