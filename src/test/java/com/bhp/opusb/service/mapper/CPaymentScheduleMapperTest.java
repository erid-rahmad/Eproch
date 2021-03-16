package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CPaymentScheduleMapperTest {

    private CPaymentScheduleMapper cPaymentScheduleMapper;

    @BeforeEach
    public void setUp() {
        cPaymentScheduleMapper = new CPaymentScheduleMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cPaymentScheduleMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cPaymentScheduleMapper.fromId(null)).isNull();
    }
}
