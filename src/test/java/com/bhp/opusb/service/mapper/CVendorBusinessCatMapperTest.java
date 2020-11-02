package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CVendorBusinessCatMapperTest {

    private CVendorBusinessCatMapper cVendorBusinessCatMapper;

    @BeforeEach
    public void setUp() {
        cVendorBusinessCatMapper = new CVendorBusinessCatMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cVendorBusinessCatMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cVendorBusinessCatMapper.fromId(null)).isNull();
    }
}
