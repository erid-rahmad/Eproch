package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CCurrencyMapperTest {

    private CCurrencyMapper cCurrencyMapper;

    @BeforeEach
    public void setUp() {
        cCurrencyMapper = new CCurrencyMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cCurrencyMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cCurrencyMapper.fromId(null)).isNull();
    }
}
