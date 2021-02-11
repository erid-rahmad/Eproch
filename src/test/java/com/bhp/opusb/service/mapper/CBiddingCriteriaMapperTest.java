package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CBiddingCriteriaMapperTest {

    private CBiddingCriteriaMapper cBiddingCriteriaMapper;

    @BeforeEach
    public void setUp() {
        cBiddingCriteriaMapper = new CBiddingCriteriaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cBiddingCriteriaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cBiddingCriteriaMapper.fromId(null)).isNull();
    }
}
