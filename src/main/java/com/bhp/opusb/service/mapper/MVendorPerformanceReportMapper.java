package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MVendorPerformanceReportDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MVendorPerformanceReport} and its DTO {@link MVendorPerformanceReportDTO}.
 */
@Mapper(componentModel = "spring", uses = {CVendorMapper.class, CBusinessCategoryMapper.class})
public interface MVendorPerformanceReportMapper extends EntityMapper<MVendorPerformanceReportDTO, MVendorPerformanceReport> {

    @Mapping(source = "vendor.id", target = "vendorId")
    @Mapping(source = "vendor.name", target = "vendorName")
    @Mapping(source = "businessCategory.id", target = "businessCategoryId")
    @Mapping(source = "businessCategory.name", target = "businessCategoryName")
    MVendorPerformanceReportDTO toDto(MVendorPerformanceReport mVendorPerformanceReport);

    @Mapping(source = "vendorId", target = "vendor")
    @Mapping(source = "businessCategoryId", target = "businessCategory")
    MVendorPerformanceReport toEntity(MVendorPerformanceReportDTO mVendorPerformanceReportDTO);

    default MVendorPerformanceReport fromId(Long id) {
        if (id == null) {
            return null;
        }
        MVendorPerformanceReport mVendorPerformanceReport = new MVendorPerformanceReport();
        mVendorPerformanceReport.setId(id);
        return mVendorPerformanceReport;
    }
}
