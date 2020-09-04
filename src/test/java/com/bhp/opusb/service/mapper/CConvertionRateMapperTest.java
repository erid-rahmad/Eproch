package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CConvertionRateMapperTest {

    private CConvertionRateMapper cConvertionRateMapper;

    @BeforeEach
    public void setUp() {
        cConvertionRateMapper = new CConvertionRateMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cConvertionRateMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cConvertionRateMapper.fromId(null)).isNull();
    }
}
