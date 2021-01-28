package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PaDashboardMapperTest {

    private PaDashboardMapper paDashboardMapper;

    @BeforeEach
    public void setUp() {
        paDashboardMapper = new PaDashboardMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(paDashboardMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(paDashboardMapper.fromId(null)).isNull();
    }
}
