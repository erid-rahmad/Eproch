package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MBiddingEvaluationMapperTest {

    private MBiddingEvaluationMapper mBiddingEvaluationMapper;

    @BeforeEach
    public void setUp() {
        mBiddingEvaluationMapper = new MBiddingEvaluationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mBiddingEvaluationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mBiddingEvaluationMapper.fromId(null)).isNull();
    }
}
