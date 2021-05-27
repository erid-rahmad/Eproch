package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MVendorConfirmationLineMapperTest {

    private MVendorConfirmationLineMapper mVendorConfirmationLineMapper;

    @BeforeEach
    public void setUp() {
        mVendorConfirmationLineMapper = new MVendorConfirmationLineMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mVendorConfirmationLineMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mVendorConfirmationLineMapper.fromId(null)).isNull();
    }
}
