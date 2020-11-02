package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MVerificationMapperTest {

    private MVerificationMapper mVerificationMapper;

    @BeforeEach
    public void setUp() {
        mVerificationMapper = new MVerificationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mVerificationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mVerificationMapper.fromId(null)).isNull();
    }
}
