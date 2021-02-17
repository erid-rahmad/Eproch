package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CEventTypeMapperTest {

    private CEventTypeMapper cEventTypeMapper;

    @BeforeEach
    public void setUp() {
        cEventTypeMapper = new CEventTypeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cEventTypeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cEventTypeMapper.fromId(null)).isNull();
    }
}
