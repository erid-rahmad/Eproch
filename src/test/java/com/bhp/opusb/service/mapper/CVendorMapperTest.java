package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CVendorMapperTest {

    private CVendorMapper cVendorMapper;

    @BeforeEach
    public void setUp() {
        cVendorMapper = new CVendorMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cVendorMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cVendorMapper.fromId(null)).isNull();
    }
}
