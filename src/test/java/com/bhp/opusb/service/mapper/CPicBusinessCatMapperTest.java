package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CPicBusinessCatMapperTest {

    private CPicBusinessCatMapper cPicBusinessCatMapper;

    @BeforeEach
    public void setUp() {
        cPicBusinessCatMapper = new CPicBusinessCatMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cPicBusinessCatMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cPicBusinessCatMapper.fromId(null)).isNull();
    }
}
