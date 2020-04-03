package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PersonInChargeMapperTest {

    private PersonInChargeMapper personInChargeMapper;

    @BeforeEach
    public void setUp() {
        personInChargeMapper = new PersonInChargeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(personInChargeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(personInChargeMapper.fromId(null)).isNull();
    }
}
