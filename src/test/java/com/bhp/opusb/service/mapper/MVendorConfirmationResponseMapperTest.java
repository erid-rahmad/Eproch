package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MVendorConfirmationResponseMapperTest {

    private MVendorConfirmationResponseMapper mVendorConfirmationResponseMapper;

    @BeforeEach
    public void setUp() {
        mVendorConfirmationResponseMapper = new MVendorConfirmationResponseMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mVendorConfirmationResponseMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mVendorConfirmationResponseMapper.fromId(null)).isNull();
    }
}
