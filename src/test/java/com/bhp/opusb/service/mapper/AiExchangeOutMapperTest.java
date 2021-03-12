package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AiExchangeOutMapperTest {

    private AiExchangeOutMapper aiExchangeOutMapper;

    @BeforeEach
    public void setUp() {
        aiExchangeOutMapper = new AiExchangeOutMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(aiExchangeOutMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(aiExchangeOutMapper.fromId(null)).isNull();
    }
}
