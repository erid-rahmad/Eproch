package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MVerificationTaxMapperTest {

    private MVerificationTaxMapper mVerificationTaxMapper;

    @BeforeEach
    public void setUp() {
        mVerificationTaxMapper = new MVerificationTaxMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mVerificationTaxMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mVerificationTaxMapper.fromId(null)).isNull();
    }
}
