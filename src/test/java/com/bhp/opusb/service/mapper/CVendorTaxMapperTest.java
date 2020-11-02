package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CVendorTaxMapperTest {

    private CVendorTaxMapper cVendorTaxMapper;

    @BeforeEach
    public void setUp() {
        cVendorTaxMapper = new CVendorTaxMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cVendorTaxMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cVendorTaxMapper.fromId(null)).isNull();
    }
}
