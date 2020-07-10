package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CPersonInChargeMapperTest {

    private CPersonInChargeMapper cPersonInChargeMapper;

    @BeforeEach
    public void setUp() {
        cPersonInChargeMapper = new CPersonInChargeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cPersonInChargeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cPersonInChargeMapper.fromId(null)).isNull();
    }
}
