package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AdCalloutMapperTest {

    private AdCalloutMapper adCalloutMapper;

    @BeforeEach
    public void setUp() {
        adCalloutMapper = new AdCalloutMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(adCalloutMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(adCalloutMapper.fromId(null)).isNull();
    }
}
