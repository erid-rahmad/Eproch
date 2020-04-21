package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ADClientMapperTest {

    private ADClientMapper aDClientMapper;

    @BeforeEach
    public void setUp() {
        aDClientMapper = new ADClientMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(aDClientMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(aDClientMapper.fromId(null)).isNull();
    }
}
