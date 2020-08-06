package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CVendorBankAcctMapperTest {

    private CVendorBankAcctMapper cVendorBankAcctMapper;

    @BeforeEach
    public void setUp() {
        cVendorBankAcctMapper = new CVendorBankAcctMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cVendorBankAcctMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cVendorBankAcctMapper.fromId(null)).isNull();
    }
}
