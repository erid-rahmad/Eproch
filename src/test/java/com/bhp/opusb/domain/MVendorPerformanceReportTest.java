package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MVendorPerformanceReportTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MVendorPerformanceReport.class);
        MVendorPerformanceReport mVendorPerformanceReport1 = new MVendorPerformanceReport();
        mVendorPerformanceReport1.setId(1L);
        MVendorPerformanceReport mVendorPerformanceReport2 = new MVendorPerformanceReport();
        mVendorPerformanceReport2.setId(mVendorPerformanceReport1.getId());
        assertThat(mVendorPerformanceReport1).isEqualTo(mVendorPerformanceReport2);
        mVendorPerformanceReport2.setId(2L);
        assertThat(mVendorPerformanceReport1).isNotEqualTo(mVendorPerformanceReport2);
        mVendorPerformanceReport1.setId(null);
        assertThat(mVendorPerformanceReport1).isNotEqualTo(mVendorPerformanceReport2);
    }
}
