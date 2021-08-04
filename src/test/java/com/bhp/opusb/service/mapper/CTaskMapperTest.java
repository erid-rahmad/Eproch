package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CTaskMapperTest {

    private CTaskMapper cTaskMapper;

    @BeforeEach
    public void setUp() {
        cTaskMapper = new CTaskMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cTaskMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cTaskMapper.fromId(null)).isNull();
    }
}
