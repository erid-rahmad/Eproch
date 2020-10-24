package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AdUserMapperTest {

    private AdUserMapper adUserMapper;

    @BeforeEach
    public void setUp() {
        adUserMapper = new AdUserMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(adUserMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(adUserMapper.fromId(null)).isNull();
    }
}
