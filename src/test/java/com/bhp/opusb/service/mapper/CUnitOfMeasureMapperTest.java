package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CUnitOfMeasureMapperTest {

    private CUnitOfMeasureMapper cUnitOfMeasureMapper;

    @BeforeEach
    public void setUp() {
        cUnitOfMeasureMapper = new CUnitOfMeasureMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cUnitOfMeasureMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cUnitOfMeasureMapper.fromId(null)).isNull();
    }
}
