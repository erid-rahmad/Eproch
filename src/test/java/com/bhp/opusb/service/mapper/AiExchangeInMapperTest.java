package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AiExchangeInMapperTest {

    private AiExchangeInMapper aiExchangeInMapper;

    @BeforeEach
    public void setUp() {
        aiExchangeInMapper = new AiExchangeInMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(aiExchangeInMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(aiExchangeInMapper.fromId(null)).isNull();
    }
}
