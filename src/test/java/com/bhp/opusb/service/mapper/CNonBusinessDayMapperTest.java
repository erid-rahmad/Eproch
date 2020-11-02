package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CNonBusinessDayMapperTest {

    private CNonBusinessDayMapper cNonBusinessDayMapper;

    @BeforeEach
    public void setUp() {
        cNonBusinessDayMapper = new CNonBusinessDayMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cNonBusinessDayMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cNonBusinessDayMapper.fromId(null)).isNull();
    }
}
