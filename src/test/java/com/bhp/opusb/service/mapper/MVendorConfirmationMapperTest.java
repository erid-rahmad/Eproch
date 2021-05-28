package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MVendorConfirmationMapperTest {

    private MVendorConfirmationMapper mVendorConfirmationMapper;

    @BeforeEach
    public void setUp() {
        mVendorConfirmationMapper = new MVendorConfirmationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mVendorConfirmationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mVendorConfirmationMapper.fromId(null)).isNull();
    }
}
