package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CBiddingSubCriteriaLineMapperTest {

    private CBiddingSubCriteriaLineMapper cBiddingSubCriteriaLineMapper;

    @BeforeEach
    public void setUp() {
        cBiddingSubCriteriaLineMapper = new CBiddingSubCriteriaLineMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cBiddingSubCriteriaLineMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cBiddingSubCriteriaLineMapper.fromId(null)).isNull();
    }
}
