package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CLocationMapperTest {

    private CLocationMapper cLocationMapper;

    @BeforeEach
    public void setUp() {
        cLocationMapper = new CLocationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cLocationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cLocationMapper.fromId(null)).isNull();
    }
}
