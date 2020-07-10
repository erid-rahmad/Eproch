package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AdTriggerMapperTest {

    private AdTriggerMapper adTriggerMapper;

    @BeforeEach
    public void setUp() {
        adTriggerMapper = new AdTriggerMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(adTriggerMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(adTriggerMapper.fromId(null)).isNull();
    }
}
