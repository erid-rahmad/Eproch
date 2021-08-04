package com.bhp.opusb.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bhp.opusb.domain.CVendor;
import com.bhp.opusb.repository.CVendorRepository;
import com.bhp.opusb.service.dto.AdUserCriteria;
import com.bhp.opusb.service.dto.AdUserDTO;
import com.bhp.opusb.service.dto.CFunctionaryCriteria;
import com.bhp.opusb.service.dto.CFunctionaryDTO;
import com.bhp.opusb.service.dto.CRegistrationDocumentCriteria;
import com.bhp.opusb.service.dto.CRegistrationDocumentDTO;
import com.bhp.opusb.service.dto.CVendorBankAcctCriteria;
import com.bhp.opusb.service.dto.CVendorBankAcctDTO;
import com.bhp.opusb.service.dto.CVendorBusinessCatCriteria;
import com.bhp.opusb.service.dto.CVendorBusinessCatDTO;
import com.bhp.opusb.service.dto.CVendorLocationCriteria;
import com.bhp.opusb.service.dto.CVendorLocationDTO;
import com.bhp.opusb.service.dto.CVendorTaxCriteria;
import com.bhp.opusb.service.dto.CVendorTaxDTO;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import io.github.jhipster.service.filter.LongFilter;

@Service
public class VendorPDFService {
	
	private static Logger log = LoggerFactory.getLogger(VendorPDFService.class); 

	private final CVendorRepository cVendorRepository;
	private final CVendorLocationQueryService cVendorLocationQueryService;
	private final CVendorBusinessCatQueryService cVendorBusinessCatQueryService;
	private final CRegistrationDocumentQueryService cRegistractionDocumentQueryService;
	private final AdUserQueryService adUserQueryService;
	private final CFunctionaryQueryService cFunctionaryQueryService;
	private final CVendorBankAcctQueryService cVendorBankAcctQueryService;
	private final CVendorTaxQueryService cVendorTaxQueryService;
	
	public VendorPDFService(
			CVendorRepository cVendorRepository,
			CVendorLocationQueryService cVendorLocationQueryService,
			CVendorBusinessCatQueryService cVendorBusinessCatQueryService,
			CRegistrationDocumentQueryService cRegistractionDocumentQueryService,
			AdUserQueryService adUserQueryService,
			CFunctionaryQueryService cFunctionaryQueryService,
			CVendorBankAcctQueryService cVendorBankAcctQueryService,
			CVendorTaxQueryService cVendorTaxQueryService) {
		this.cVendorRepository= cVendorRepository;
		this.cVendorLocationQueryService= cVendorLocationQueryService;
		this.cVendorBusinessCatQueryService= cVendorBusinessCatQueryService;
		this.cRegistractionDocumentQueryService= cRegistractionDocumentQueryService;
		this.adUserQueryService= adUserQueryService;
		this.cFunctionaryQueryService = cFunctionaryQueryService;
		this.cVendorBankAcctQueryService = cVendorBankAcctQueryService;
		this.cVendorTaxQueryService= cVendorTaxQueryService;
	}
	
	public ByteArrayInputStream vendorSummaryPdf(Long vendorId) throws Exception {
		log.debug("START CREATING PDF");
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		generatePdf(out, vendorId);
		
		return new ByteArrayInputStream(out.toByteArray());
	}
	
	private void generatePdf(ByteArrayOutputStream stream, Long vendorId) throws Exception {
		Document document= new Document(new Rectangle(595 , 842 ), 0, 0, 20, 10);
		
		PdfWriter.getInstance(document, stream);
		document.open();
		
		createDocumentTitle(document);
		createVendorDetail(vendorId, document);
		createAddressTable(vendorId, document);
		createBusinessClasificationTable(vendorId, document);
		createSupportingDocumentTable(vendorId, document);
		createPICTable(vendorId, document);
		createFunctionariesQuery(vendorId, document);
		createPaymentInformationTable(vendorId, document);
		createTaxTable(vendorId, document);
		
		document.close();
	}
	
	private void createDocumentTitle(Document document) throws Exception{
		Font font= FontFactory.getFont(FontFactory.TIMES_BOLD, 14, BaseColor.BLACK);
		Paragraph p= new Paragraph("Suplier Profile", font);
		p.setAlignment(Element.ALIGN_CENTER);
		document.add(p);
		document.add(Chunk.NEWLINE);
	}
	
	private void createVendorDetail(Long vendorId, Document document) throws Exception{
		PdfPTable table = new PdfPTable(4);
		
		Optional<CVendor> oVendor = cVendorRepository.findById(vendorId);
		if(!oVendor.isPresent())
			throw new EntityNotFoundException(String.format("vendor with id %s is not exists", String.valueOf(vendorId)));
		
		CVendor vendor= oVendor.get();
		
		Function<String, String> formatLocation= location-> {
			
			if(Objects.isNull(location)) return "";
			switch(location) {
			case "DMS" :
				return "Domestic" ;
			case "Domestic" :
				return location;
			default :
				return "International";
			}
		};
		
		Stream.of(
				"Organization", 	getString(vendor.getAdOrganization().getName()),
				"Supplier Code", 	getString(vendor.getCode()),
				"Name", 			getString(vendor.getName()),
				"Active",			vendor.isActive()? "Active" : "InActive",
				"Supplier Type",	getString(vendor.getType()),
				"Location",			getString(formatLocation.apply(vendor.getLocation())),
				"NPWP",				getString(vendor.getTaxIdNo()),
				"NPWP Name", 		getString(vendor.getTaxIdName()),
				"Website",			Objects.isNull(vendor.getWebsite()) ? "" : vendor.getWebsite(),
				"Payment Category",	getString(vendor.getPaymentCategory()),
				"Date Trx",			getString(vendor.getDateTrx().toString()),
				"",					""
				).forEach(s->{
					table.addCell(createCell(s, Rectangle.BOTTOM));
				});
		
		document.add(table);
		document.add(Chunk.NEWLINE);
	}
	
	private void createAddressTable(Long vendorId, Document document) throws Exception {
		
		CVendorLocationCriteria criteria= new CVendorLocationCriteria();
		LongFilter filter= new LongFilter();
		filter.setEquals(vendorId);
		criteria.setVendorId(filter);
		
		List<CVendorLocationDTO> location= this.cVendorLocationQueryService.findByCriteria(criteria);
		
		addParagraph(document, "Address Information");
		
		PdfPTable table = new PdfPTable(4);
		
		Stream.of("Organization", "Address", "City", "Country")
			.forEach(s->{
				table.addCell(createHeaderCell(s));
			});
		location.stream().forEach(loc-> {
			Stream.of(getString(loc.getAdOrganizationName()), getString(loc.getLocationName()), 
					getString(loc.getCityName()), getString(loc.getCountryName()))
				.forEach(s -> table.addCell(createCell(s, null)));
			
		});
		
		document.add(table);
		document.add(Chunk.NEWLINE);
	}
	
	private void createBusinessClasificationTable(Long vendorId, Document document) throws Exception{
		
		CVendorBusinessCatCriteria criteria= new CVendorBusinessCatCriteria();
		LongFilter filter = new LongFilter();
		filter.setEquals(vendorId);
		criteria.setVendorId(filter);
		
		List<CVendorBusinessCatDTO> businessCategory= cVendorBusinessCatQueryService.findByCriteria(criteria);
		
		addParagraph(document, "Business Criteria");
		PdfPTable table= new PdfPTable(4);
		
		Stream.of("Organization", "Business Classification", "Business Category", "Sub Business Category")
			.forEach(s -> table.addCell(createHeaderCell(s)));
		
		businessCategory.stream().forEach(b-> {
			log.debug(b.toString());
			Stream.of(getString(b.getAdOrganizationName()), getString(b.getBusinessClassificationName()), 
					getString(b.getBusinessCategoryName()),
					getString(b.getSubBusinessCategoryName()))
				.forEach(s-> table.addCell(createCell(s, null)));
		});
		
		document.add(table);
		document.add(Chunk.NEWLINE);
	}
	
	private void createSupportingDocumentTable(Long vendorId, Document document) throws Exception {
		CRegistrationDocumentCriteria criteria= new CRegistrationDocumentCriteria();
		LongFilter filter= new LongFilter();
		filter.setEquals(vendorId);
		criteria.setVendorId(filter);
		
		List<CRegistrationDocumentDTO> doc= cRegistractionDocumentQueryService.findByCriteria(criteria);
		
		addParagraph(document, "Supporting Documents");
		PdfPTable table= new PdfPTable(3);
		
		Stream.of("Document Type", "Document No", "Expiration Date")
			.forEach(s->table.addCell(createHeaderCell(s)));
		doc.stream().forEach(d -> {
			log.info(d.toString());
			Stream.of(getString(d.getTypeName()), getString(d.getDocumentNo()), 
					getString(d.getExpirationDate()))
				.forEach(ds-> table.addCell(createCell(ds, null)));
		});
		
		document.add(table);
		document.add(Chunk.NEWLINE);
	}
	
	private void createPICTable(Long vendorId, Document document) throws Exception{
		AdUserCriteria criteria= new AdUserCriteria();
		LongFilter filter= new LongFilter();
		filter.setEquals(vendorId);
		criteria.setCVendorId(filter);
		
		List<AdUserDTO> userPic= adUserQueryService.findByCriteria(criteria);
		addParagraph(document, "PIC");
		PdfPTable table= new PdfPTable(5);
		
		Stream.of("First Name", "Last Name", "Position", "Phone", "Email")
			.forEach(h -> table.addCell(createHeaderCell(h)));
		
		userPic.stream().forEach(u -> {
			Stream.of(getString(u.getFirstName()), getString(u.getLastName()), getString(u.getPhone()),
					getString(u.getPosition()), getString(u.getEmail()))
				.forEach(s -> table.addCell(createCell(s, null)));
		});
		
		document.add(table);
		document.add(Chunk.NEWLINE);
	}
	
	private void createFunctionariesQuery(Long vendorId, Document document) throws Exception {
		CFunctionaryCriteria criteria= new CFunctionaryCriteria();
		LongFilter filter= new LongFilter();
		filter.setEquals(vendorId);
		criteria.setVendorId(filter);
		
		List<CFunctionaryDTO> functionary= cFunctionaryQueryService.findByCriteria(criteria);
		addParagraph(document, "Functionary");
		PdfPTable table= new PdfPTable(4);
		
		Stream.of("Name", "Position", "Phone", "Email")
			.forEach(h -> table.addCell(createHeaderCell(h)));
		
		functionary.stream().forEach(f -> {
			Stream.of(getString(f.getName()), getString(f.getPosition()), 
					getString(f.getPhone()), getString(f.getEmail()))
				.forEach(fs -> table.addCell(createCell(fs, null)));
		});
		
		document.add(table);
		document.add(Chunk.NEWLINE);
	}
	
	private void createPaymentInformationTable(Long vendorId, Document document) throws Exception {
		CVendorBankAcctCriteria criteria= new CVendorBankAcctCriteria();
		LongFilter filter= new LongFilter();
		filter.setEquals(vendorId);
		criteria.setVendorId(filter);
		
		List<CVendorBankAcctDTO> bank= cVendorBankAcctQueryService.findByCriteria(criteria);
		addParagraph(document, "Payment Information");
		PdfPTable table= new PdfPTable(5);
		
		Stream.of("Bank", "Branch", "Currency", "Account No", "Account Name")
			.forEach(h -> table.addCell(createHeaderCell(h)));
		
		bank.stream().forEach(bk-> {
			
			Stream.of(getString(bk.getBankName()), getString(bk.getBranch()), getString(bk.getCurrencyName()),
					getString(bk.getAccountNo()), getString(bk.getAccountName()))
				.forEach(bs-> table.addCell(createCell(bs, null)));
		});
		
		document.add(table);
		document.add(Chunk.NEWLINE);
	}
	
	private void createTaxTable(Long vendorId, Document document) throws Exception {
		CVendorTaxCriteria criteria= new CVendorTaxCriteria();
		LongFilter filter= new LongFilter();
		filter.setEquals(vendorId);
		
		criteria.setVendorId(filter);
		
		List<CVendorTaxDTO> tax= cVendorTaxQueryService.findByCriteria(criteria);
		addParagraph(document, "Tax Information");
		PdfPTable table= new PdfPTable(3);
		
		Stream.of("Tax Category", "Is Faktur", "Is PKP")
			.forEach(h -> table.addCell(createHeaderCell(h)));
		
		tax.stream().forEach(tx -> {
			Stream.of(getString(tx.getTaxCategoryName()), getString(tx.isEInvoice() ? "Yes" : "No"),
					getString(tx.isTaxableEmployers() ? "Yes" : "No"))
				.forEach(ts -> table.addCell(createCell(ts, null)));
		});
		
		document.add(table);
		document.add(Chunk.NEWLINE);
	}
	
	private String getString(Object string) {
		Optional<Object> s = Optional.ofNullable(string);
		if(s.isPresent()) {
			return s.get().toString();
		}else {
			return new String("");
		}
	}
	
	private PdfPCell createCell(String text, Integer border) {
		PdfPCell cell= createCell(new Phrase(text), border);
		return cell;
	}
	
	private PdfPCell createCell (Phrase phrase, Integer border) {
		PdfPCell cell = new PdfPCell(phrase);
		cell.setPadding(5);
		if(!Objects.isNull(border)) cell.setBorder(border);
		cell.setVerticalAlignment(Element.ALIGN_LEFT); 
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		return cell;
	}
	
	private PdfPCell createHeaderCell(String text) {
		
		Font headerFont= FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		PdfPCell cell = this.createCell(new Phrase(text, headerFont), null);
		cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		
		return cell;
	}
	
	
	private String formatDate(Date date) {
		SimpleDateFormat format= new SimpleDateFormat("dd-MM-YYYY");
		return format.format(date);
	}
	
	private void addParagraph(Document document, String text) throws Exception{
		
		Font font= FontFactory.getFont(FontFactory.TIMES_BOLD, 12, BaseColor.BLACK);
		Paragraph p= new Paragraph(text, font);
		p.setAlignment(Element.ALIGN_CENTER);
		document.add(p);
		document.add(Chunk.NEWLINE);
	}
}
