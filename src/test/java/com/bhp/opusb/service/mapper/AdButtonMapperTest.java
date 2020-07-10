package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AdButtonMapperTest {

    private AdButtonMapper adButtonMapper;

    @BeforeEach
    public void setUp() {
        adButtonMapper = new AdButtonMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(adButtonMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(adButtonMapper.fromId(null)).isNull();
    }
}
