package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CPaymentTermMapperTest {

    private CPaymentTermMapper cPaymentTermMapper;

    @BeforeEach
    public void setUp() {
        cPaymentTermMapper = new CPaymentTermMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cPaymentTermMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cPaymentTermMapper.fromId(null)).isNull();
    }
}
