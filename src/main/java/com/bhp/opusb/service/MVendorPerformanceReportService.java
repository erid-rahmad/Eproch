package com.bhp.opusb.service;

import com.bhp.opusb.domain.MContract;
import com.bhp.opusb.domain.MVendorPerformanceReport;
import com.bhp.opusb.repository.MVendorPerformanceReportRepository;
import com.bhp.opusb.service.dto.MContractDTO;
import com.bhp.opusb.service.dto.MVendorPerformanceReportDTO;
import com.bhp.opusb.service.dto.MVendorPerformanceReportDetailDTO;
import com.bhp.opusb.service.mapper.MContractMapper;
import com.bhp.opusb.service.mapper.MVendorPerformanceReportMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Service Implementation for managing {@link MVendorPerformanceReport}.
 */
@Service
@Transactional
public class MVendorPerformanceReportService {

    private final Logger log = LoggerFactory.getLogger(MVendorPerformanceReportService.class);

    private final MVendorPerformanceReportRepository mVendorPerformanceReportRepository;

    private final MVendorPerformanceReportMapper mVendorPerformanceReportMapper;
    private final MContractMapper mContractMapper;

    public MVendorPerformanceReportService(MVendorPerformanceReportRepository mVendorPerformanceReportRepository,
    MVendorPerformanceReportMapper mVendorPerformanceReportMapper, MContractMapper mContractMapper) {
        this.mVendorPerformanceReportRepository = mVendorPerformanceReportRepository;
        this.mVendorPerformanceReportMapper = mVendorPerformanceReportMapper;
        this.mContractMapper = mContractMapper;
    }

    /**
     * Save a mVendorPerformanceReport.
     *
     * @param mVendorPerformanceReportDTO the entity to save.
     * @return the persisted entity.
     */
    public MVendorPerformanceReportDTO save(MVendorPerformanceReportDTO mVendorPerformanceReportDTO) {
        log.debug("Request to save MVendorPerformanceReport : {}", mVendorPerformanceReportDTO);
        MVendorPerformanceReport mVendorPerformanceReport = mVendorPerformanceReportMapper.toEntity(mVendorPerformanceReportDTO);
        mVendorPerformanceReport = mVendorPerformanceReportRepository.save(mVendorPerformanceReport);
        return mVendorPerformanceReportMapper.toDto(mVendorPerformanceReport);
    }

    /**
     * Get all the mVendorPerformanceReports.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MVendorPerformanceReportDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MVendorPerformanceReports");
        return mVendorPerformanceReportRepository.findAll(pageable)
            .map(mVendorPerformanceReportMapper::toDto);
    }

    /**
     * Get one mVendorPerformanceReport by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MVendorPerformanceReportDTO> findOne(Long id) {
        log.debug("Request to get MVendorPerformanceReport : {}", id);
        return mVendorPerformanceReportRepository.findById(id)
            .map(mVendorPerformanceReportMapper::toDto);
    }

    /**
     * Delete the mVendorPerformanceReport by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MVendorPerformanceReport : {}", id);
        mVendorPerformanceReportRepository.deleteById(id);
    }

    /**
     * Retrieve the report detail of the selected vendor
     * 
     * @param vendorId the id of the vendor.
     * @param interval the time period of the report to be retrieved.
     * @return performance report detail of selected vendor
     */
    public MVendorPerformanceReportDetailDTO retrieveDetail(Long vendorId, String interval) {
        if(StringUtils.isEmpty(interval)) interval="1y";

        String[] split = interval.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");

        Calendar c = Calendar.getInstance();
        c.add(split[1].equalsIgnoreCase("m")?Calendar.MONTH: Calendar.YEAR, -Integer.parseInt(split[0]));

        java.sql.Date date = new java.sql.Date(c.getTime().getTime());
        
        MVendorPerformanceReportDetailDTO rep = new MVendorPerformanceReportDetailDTO();
        rep.setPoCount(mVendorPerformanceReportRepository.getPoCount(vendorId, date));
        rep.setPoSpend(mVendorPerformanceReportRepository.getPoSpend(vendorId, date));
        rep.setInvoiceCount(mVendorPerformanceReportRepository.getInvoiceCount(vendorId, date));
        rep.setInvoiceSpend(mVendorPerformanceReportRepository.getPoCount(vendorId, date));
        try{
            rep.setInvoiceWithoutException(rep.getInvoiceSpend().multiply(new BigDecimal("100")).divide(rep.getInvoiceCount(), 2, RoundingMode.HALF_UP) );
        }
        catch(Exception e){
            e.printStackTrace();
            rep.setInvoiceWithoutException(new BigDecimal("0"));
        }
        rep.setAwardedSpending(mVendorPerformanceReportRepository.getAwardedSpend(vendorId, date));
        rep.setAwardedSavings(new BigDecimal("0"));
        rep.setEventInvited(mVendorPerformanceReportRepository.getEventInvited(vendorId, date));
        rep.setActiveContracts(mVendorPerformanceReportRepository.getActiveContractCount(vendorId));
        rep.setEventParticipation(mVendorPerformanceReportRepository.getParticipationCount(vendorId));

        if(rep.getEventInvited().compareTo(0)==0) rep.setParticipant(new BigDecimal("0"));
        else rep.setParticipant(new BigDecimal(rep.getEventParticipation().toString())
            .multiply(new BigDecimal("100"))
            .divide(new BigDecimal(rep.getEventInvited().toString()), 2, RoundingMode.HALF_UP));
        
        List<Object[]> contracts = mVendorPerformanceReportRepository.getActiveContracts(vendorId);
        List<MContractDTO> dtos = new ArrayList<>();
        for(Object[] x: contracts){
            MContractDTO dto = new MContractDTO();
            dto.setName((String)x[0]);
            dto.setPrice((BigDecimal)x[3]);
            dto.setStartDate(((java.sql.Date)x[1]).toLocalDate());
            dto.setExpirationDate(((java.sql.Date)x[2]).toLocalDate());
            
            dtos.add(dto);
        }
        rep.setActiveContractObj(dtos);

        List<Object[]> perfs = mVendorPerformanceReportRepository.getProjectPerformances(vendorId, date);
        List<Map<String,Object>> perfDtos = new ArrayList<>();

        for(Object[] x: perfs){
            Map<String,Object> perfDto = new HashMap<>();
            perfDto.put("name", x[0]);
            perfDto.put("documentNo", x[1]);
            perfDto.put("pic", x[2]);
            perfDto.put("startDate", ((java.sql.Date)x[3]).toLocalDate());
            perfDto.put("totalScore",x[4]);

            perfDtos.add(perfDto);
        }
        rep.setPerformanceProjectAnalysis(perfDtos);

        return rep;
    }

    public List<VendorPerformanceDashboard> getTop5QualityRating(){
        List<VendorPerformanceDashboard> vpdb = new ArrayList<>();

        List<Object[]> list = mVendorPerformanceReportRepository.getAllVendorPerformance();
        for(Object[] x: list){
            VendorPerformanceDashboard vpd = new VendorPerformanceDashboard();
            vpd.setVendorId(((BigInteger)x[0]).longValue());
            vpd.setVendorName((String)x[1]);
            vpd.setOrdered((BigDecimal)x[2]);
            vpd.setReturned(new BigDecimal("0"));
            vpd.setAvailability(new BigDecimal("0"));
            vpd.setDefectRate(new BigDecimal("0"));
            vpd.setQualityScore((BigDecimal)x[3]);

            vpdb.add(vpd);
        }

        return vpdb;
    }

    public class VendorPerformanceDashboard {
        private Long vendorId;
        private String vendorName;
        private BigDecimal ordered;
        private BigDecimal returned, availability, defectRate, qualityScore;
        public Long getVendorId() {
            return vendorId;
        }
        public BigDecimal getQualityScore() {
            return qualityScore;
        }
        public void setQualityScore(BigDecimal qualityScore) {
            this.qualityScore = qualityScore;
        }
        public BigDecimal getDefectRate() {
            return defectRate;
        }
        public void setDefectRate(BigDecimal defectRate) {
            this.defectRate = defectRate;
        }
        public BigDecimal getAvailability() {
            return availability;
        }
        public void setAvailability(BigDecimal availability) {
            this.availability = availability;
        }
        public BigDecimal getReturned() {
            return returned;
        }
        public void setReturned(BigDecimal returned) {
            this.returned = returned;
        }
        public BigDecimal getOrdered() {
            return ordered;
        }
        public void setOrdered(BigDecimal ordered) {
            this.ordered = ordered;
        }
        public String getVendorName() {
            return vendorName;
        }
        public void setVendorName(String vendorName) {
            this.vendorName = vendorName;
        }
        public void setVendorId(Long vendorId) {
            this.vendorId = vendorId;
        }

        
    }
}
