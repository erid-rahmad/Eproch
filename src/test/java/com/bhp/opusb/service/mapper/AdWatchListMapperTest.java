package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AdWatchListMapperTest {

    private AdWatchListMapper adWatchListMapper;

    @BeforeEach
    public void setUp() {
        adWatchListMapper = new AdWatchListMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(adWatchListMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(adWatchListMapper.fromId(null)).isNull();
    }
}
