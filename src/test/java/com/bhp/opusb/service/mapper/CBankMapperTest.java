package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CBankMapperTest {

    private CBankMapper cBankMapper;

    @BeforeEach
    public void setUp() {
        cBankMapper = new CBankMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cBankMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cBankMapper.fromId(null)).isNull();
    }
}
