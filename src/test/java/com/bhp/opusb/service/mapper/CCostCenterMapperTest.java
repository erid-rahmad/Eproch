package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CCostCenterMapperTest {

    private CCostCenterMapper cCostCenterMapper;

    @BeforeEach
    public void setUp() {
        cCostCenterMapper = new CCostCenterMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cCostCenterMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cCostCenterMapper.fromId(null)).isNull();
    }
}
