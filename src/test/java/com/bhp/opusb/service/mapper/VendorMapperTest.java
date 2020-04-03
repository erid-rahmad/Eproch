package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class VendorMapperTest {

    private VendorMapper vendorMapper;

    @BeforeEach
    public void setUp() {
        vendorMapper = new VendorMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(vendorMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(vendorMapper.fromId(null)).isNull();
    }
}
