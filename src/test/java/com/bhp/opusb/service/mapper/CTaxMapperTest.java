package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CTaxMapperTest {

    private CTaxMapper cTaxMapper;

    @BeforeEach
    public void setUp() {
        cTaxMapper = new CTaxMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cTaxMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cTaxMapper.fromId(null)).isNull();
    }
}
