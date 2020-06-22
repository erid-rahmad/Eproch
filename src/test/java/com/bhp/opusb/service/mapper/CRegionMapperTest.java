package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CRegionMapperTest {

    private CRegionMapper cRegionMapper;

    @BeforeEach
    public void setUp() {
        cRegionMapper = new CRegionMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cRegionMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cRegionMapper.fromId(null)).isNull();
    }
}
