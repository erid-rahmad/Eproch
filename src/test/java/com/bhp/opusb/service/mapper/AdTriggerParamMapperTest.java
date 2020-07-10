package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AdTriggerParamMapperTest {

    private AdTriggerParamMapper adTriggerParamMapper;

    @BeforeEach
    public void setUp() {
        adTriggerParamMapper = new AdTriggerParamMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(adTriggerParamMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(adTriggerParamMapper.fromId(null)).isNull();
    }
}
