package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CBiddingSubCriteriaMapperTest {

    private CBiddingSubCriteriaMapper cBiddingSubCriteriaMapper;

    @BeforeEach
    public void setUp() {
        cBiddingSubCriteriaMapper = new CBiddingSubCriteriaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cBiddingSubCriteriaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cBiddingSubCriteriaMapper.fromId(null)).isNull();
    }
}
