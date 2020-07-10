package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AdTaskSchedulerMapperTest {

    private AdTaskSchedulerMapper adTaskSchedulerMapper;

    @BeforeEach
    public void setUp() {
        adTaskSchedulerMapper = new AdTaskSchedulerMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(adTaskSchedulerMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(adTaskSchedulerMapper.fromId(null)).isNull();
    }
}
