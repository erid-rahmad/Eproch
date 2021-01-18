package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AdWatchListItemMapperTest {

    private AdWatchListItemMapper adWatchListItemMapper;

    @BeforeEach
    public void setUp() {
        adWatchListItemMapper = new AdWatchListItemMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(adWatchListItemMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(adWatchListItemMapper.fromId(null)).isNull();
    }
}
