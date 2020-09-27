package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AdCalloutTargetMapperTest {

    private AdCalloutTargetMapper adCalloutTargetMapper;

    @BeforeEach
    public void setUp() {
        adCalloutTargetMapper = new AdCalloutTargetMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(adCalloutTargetMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(adCalloutTargetMapper.fromId(null)).isNull();
    }
}
