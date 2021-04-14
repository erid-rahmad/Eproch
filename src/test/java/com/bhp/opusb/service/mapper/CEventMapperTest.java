package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CEventMapperTest {

    private CEventMapper cEventMapper;

    @BeforeEach
    public void setUp() {
        cEventMapper = new CEventMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cEventMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cEventMapper.fromId(null)).isNull();
    }
}
