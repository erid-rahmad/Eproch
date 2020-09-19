package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AdFormMapperTest {

    private AdFormMapper adFormMapper;

    @BeforeEach
    public void setUp() {
        adFormMapper = new AdFormMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(adFormMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(adFormMapper.fromId(null)).isNull();
    }
}
