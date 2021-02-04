package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PaDashboardPreferenceMapperTest {

    private PaDashboardPreferenceMapper paDashboardPreferenceMapper;

    @BeforeEach
    public void setUp() {
        paDashboardPreferenceMapper = new PaDashboardPreferenceMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(paDashboardPreferenceMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(paDashboardPreferenceMapper.fromId(null)).isNull();
    }
}
