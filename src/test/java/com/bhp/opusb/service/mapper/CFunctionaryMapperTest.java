package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CFunctionaryMapperTest {

    private CFunctionaryMapper cFunctionaryMapper;

    @BeforeEach
    public void setUp() {
        cFunctionaryMapper = new CFunctionaryMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cFunctionaryMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cFunctionaryMapper.fromId(null)).isNull();
    }
}
