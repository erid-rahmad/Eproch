package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MPrequalificationScheduleMapperTest {

    private MPrequalificationScheduleMapper mPrequalificationScheduleMapper;

    @BeforeEach
    public void setUp() {
        mPrequalificationScheduleMapper = new MPrequalificationScheduleMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mPrequalificationScheduleMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mPrequalificationScheduleMapper.fromId(null)).isNull();
    }
}
