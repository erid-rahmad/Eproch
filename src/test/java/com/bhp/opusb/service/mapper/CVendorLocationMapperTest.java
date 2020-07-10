package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CVendorLocationMapperTest {

    private CVendorLocationMapper cVendorLocationMapper;

    @BeforeEach
    public void setUp() {
        cVendorLocationMapper = new CVendorLocationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cVendorLocationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cVendorLocationMapper.fromId(null)).isNull();
    }
}
