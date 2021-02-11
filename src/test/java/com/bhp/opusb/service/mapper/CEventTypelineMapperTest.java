package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CEventTypelineMapperTest {

    private CEventTypelineMapper cEventTypelineMapper;

    @BeforeEach
    public void setUp() {
        cEventTypelineMapper = new CEventTypelineMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cEventTypelineMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cEventTypelineMapper.fromId(null)).isNull();
    }
}
