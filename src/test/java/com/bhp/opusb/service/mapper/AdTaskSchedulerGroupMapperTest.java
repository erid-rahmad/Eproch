package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AdTaskSchedulerGroupMapperTest {

    private AdTaskSchedulerGroupMapper adTaskSchedulerGroupMapper;

    @BeforeEach
    public void setUp() {
        adTaskSchedulerGroupMapper = new AdTaskSchedulerGroupMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(adTaskSchedulerGroupMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(adTaskSchedulerGroupMapper.fromId(null)).isNull();
    }
}
