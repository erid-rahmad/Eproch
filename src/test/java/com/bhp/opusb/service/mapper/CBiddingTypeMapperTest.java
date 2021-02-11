package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CBiddingTypeMapperTest {

    private CBiddingTypeMapper cBiddingTypeMapper;

    @BeforeEach
    public void setUp() {
        cBiddingTypeMapper = new CBiddingTypeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cBiddingTypeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cBiddingTypeMapper.fromId(null)).isNull();
    }
}
