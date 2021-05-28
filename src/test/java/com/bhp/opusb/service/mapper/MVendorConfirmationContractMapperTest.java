package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MVendorConfirmationContractMapperTest {

    private MVendorConfirmationContractMapper mVendorConfirmationContractMapper;

    @BeforeEach
    public void setUp() {
        mVendorConfirmationContractMapper = new MVendorConfirmationContractMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mVendorConfirmationContractMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mVendorConfirmationContractMapper.fromId(null)).isNull();
    }
}
