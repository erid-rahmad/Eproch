package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CWarehouseMapperTest {

    private CWarehouseMapper cWarehouseMapper;

    @BeforeEach
    public void setUp() {
        cWarehouseMapper = new CWarehouseMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cWarehouseMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cWarehouseMapper.fromId(null)).isNull();
    }
}
