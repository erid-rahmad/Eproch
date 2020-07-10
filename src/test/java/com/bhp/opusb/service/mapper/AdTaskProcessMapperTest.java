package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AdTaskProcessMapperTest {

    private AdTaskProcessMapper adTaskProcessMapper;

    @BeforeEach
    public void setUp() {
        adTaskProcessMapper = new AdTaskProcessMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(adTaskProcessMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(adTaskProcessMapper.fromId(null)).isNull();
    }
}
