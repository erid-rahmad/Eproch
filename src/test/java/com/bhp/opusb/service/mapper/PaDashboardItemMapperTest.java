package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PaDashboardItemMapperTest {

    private PaDashboardItemMapper paDashboardItemMapper;

    @BeforeEach
    public void setUp() {
        paDashboardItemMapper = new PaDashboardItemMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(paDashboardItemMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(paDashboardItemMapper.fromId(null)).isNull();
    }
}
