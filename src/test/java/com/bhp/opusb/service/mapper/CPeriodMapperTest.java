package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CPeriodMapperTest {

    private CPeriodMapper cPeriodMapper;

    @BeforeEach
    public void setUp() {
        cPeriodMapper = new CPeriodMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cPeriodMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cPeriodMapper.fromId(null)).isNull();
    }
}
