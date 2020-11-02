package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CCountryMapperTest {

    private CCountryMapper cCountryMapper;

    @BeforeEach
    public void setUp() {
        cCountryMapper = new CCountryMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cCountryMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cCountryMapper.fromId(null)).isNull();
    }
}
