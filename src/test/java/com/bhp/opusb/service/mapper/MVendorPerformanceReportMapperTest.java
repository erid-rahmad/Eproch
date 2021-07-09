package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MVendorPerformanceReportMapperTest {

    private MVendorPerformanceReportMapper mVendorPerformanceReportMapper;

    @BeforeEach
    public void setUp() {
        mVendorPerformanceReportMapper = new MVendorPerformanceReportMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mVendorPerformanceReportMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mVendorPerformanceReportMapper.fromId(null)).isNull();
    }
}
