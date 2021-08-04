package com.bhp.opusb.web.rest;

import java.io.ByteArrayInputStream;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bhp.opusb.service.VendorPDFService;

@RestController
@RequestMapping(value="/api/c-vendors/pdf")
public class CVendorPdfResource{

	private final VendorPDFService vendorPdfService;
	
	public CVendorPdfResource(VendorPDFService vendorPdfService) {
		this.vendorPdfService= vendorPdfService;
	}
	
	@GetMapping(value="/{id}", produces= MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<InputStreamResource> getVendorPdf(@PathVariable Long id) throws Exception {
		
		ByteArrayInputStream pdfResource= vendorPdfService.vendorSummaryPdf(id);
		
		HttpHeaders headers= new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=vendor_detail.pdf");
		
		ResponseEntity<InputStreamResource> res= ResponseEntity
				.ok()
				.headers(headers)
				.contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(pdfResource));

		pdfResource.close();

		return res;
				
	}
	
	//126085711336281
}
