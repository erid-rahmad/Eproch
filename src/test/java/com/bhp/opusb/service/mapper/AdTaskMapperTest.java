package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AdTaskMapperTest {

    private AdTaskMapper adTaskMapper;

    @BeforeEach
    public void setUp() {
        adTaskMapper = new AdTaskMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(adTaskMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(adTaskMapper.fromId(null)).isNull();
    }
}
