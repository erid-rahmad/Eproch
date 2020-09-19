package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ScAccessMapperTest {

    private ScAccessMapper scAccessMapper;

    @BeforeEach
    public void setUp() {
        scAccessMapper = new ScAccessMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(scAccessMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(scAccessMapper.fromId(null)).isNull();
    }
}
