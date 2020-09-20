package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ScAccessTypeMapperTest {

    private ScAccessTypeMapper scAccessTypeMapper;

    @BeforeEach
    public void setUp() {
        scAccessTypeMapper = new ScAccessTypeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(scAccessTypeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(scAccessTypeMapper.fromId(null)).isNull();
    }
}
