package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CVendorGroupMapperTest {

    private CVendorGroupMapper cVendorGroupMapper;

    @BeforeEach
    public void setUp() {
        cVendorGroupMapper = new CVendorGroupMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cVendorGroupMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cVendorGroupMapper.fromId(null)).isNull();
    }
}
