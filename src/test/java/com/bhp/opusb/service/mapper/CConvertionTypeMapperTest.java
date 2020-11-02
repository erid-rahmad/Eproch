package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CConvertionTypeMapperTest {

    private CConvertionTypeMapper cConvertionTypeMapper;

    @BeforeEach
    public void setUp() {
        cConvertionTypeMapper = new CConvertionTypeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cConvertionTypeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cConvertionTypeMapper.fromId(null)).isNull();
    }
}
