package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CCityMapperTest {

    private CCityMapper cCityMapper;

    @BeforeEach
    public void setUp() {
        cCityMapper = new CCityMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cCityMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cCityMapper.fromId(null)).isNull();
    }
}
