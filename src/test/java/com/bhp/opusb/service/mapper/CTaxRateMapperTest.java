package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CTaxRateMapperTest {

    private CTaxRateMapper cTaxRateMapper;

    @BeforeEach
    public void setUp() {
        cTaxRateMapper = new CTaxRateMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cTaxRateMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cTaxRateMapper.fromId(null)).isNull();
    }
}
