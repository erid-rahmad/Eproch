package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AdTaskApplicationMapperTest {

    private AdTaskApplicationMapper adTaskApplicationMapper;

    @BeforeEach
    public void setUp() {
        adTaskApplicationMapper = new AdTaskApplicationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(adTaskApplicationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(adTaskApplicationMapper.fromId(null)).isNull();
    }
}
