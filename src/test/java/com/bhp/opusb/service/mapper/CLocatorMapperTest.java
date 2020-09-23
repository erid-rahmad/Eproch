package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CLocatorMapperTest {

    private CLocatorMapper cLocatorMapper;

    @BeforeEach
    public void setUp() {
        cLocatorMapper = new CLocatorMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cLocatorMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cLocatorMapper.fromId(null)).isNull();
    }
}
