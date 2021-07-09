package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MVendorPerformanceReportDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MVendorPerformanceReportDTO.class);
        MVendorPerformanceReportDTO mVendorPerformanceReportDTO1 = new MVendorPerformanceReportDTO();
        mVendorPerformanceReportDTO1.setId(1L);
        MVendorPerformanceReportDTO mVendorPerformanceReportDTO2 = new MVendorPerformanceReportDTO();
        assertThat(mVendorPerformanceReportDTO1).isNotEqualTo(mVendorPerformanceReportDTO2);
        mVendorPerformanceReportDTO2.setId(mVendorPerformanceReportDTO1.getId());
        assertThat(mVendorPerformanceReportDTO1).isEqualTo(mVendorPerformanceReportDTO2);
        mVendorPerformanceReportDTO2.setId(2L);
        assertThat(mVendorPerformanceReportDTO1).isNotEqualTo(mVendorPerformanceReportDTO2);
        mVendorPerformanceReportDTO1.setId(null);
        assertThat(mVendorPerformanceReportDTO1).isNotEqualTo(mVendorPerformanceReportDTO2);
    }
}
