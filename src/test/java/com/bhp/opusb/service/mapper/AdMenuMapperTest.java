package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AdMenuMapperTest {

    private AdMenuMapper adMenuMapper;

    @BeforeEach
    public void setUp() {
        adMenuMapper = new AdMenuMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(adMenuMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(adMenuMapper.fromId(null)).isNull();
    }
}
