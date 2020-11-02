package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CElementValueMapperTest {

    private CElementValueMapper cElementValueMapper;

    @BeforeEach
    public void setUp() {
        cElementValueMapper = new CElementValueMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cElementValueMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cElementValueMapper.fromId(null)).isNull();
    }
}
