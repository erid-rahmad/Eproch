package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CCalendarMapperTest {

    private CCalendarMapper cCalendarMapper;

    @BeforeEach
    public void setUp() {
        cCalendarMapper = new CCalendarMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cCalendarMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cCalendarMapper.fromId(null)).isNull();
    }
}
