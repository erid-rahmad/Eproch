package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MBiddingEvaluationTeamMapperTest {

    private MBiddingEvaluationTeamMapper mBiddingEvaluationTeamMapper;

    @BeforeEach
    public void setUp() {
        mBiddingEvaluationTeamMapper = new MBiddingEvaluationTeamMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mBiddingEvaluationTeamMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mBiddingEvaluationTeamMapper.fromId(null)).isNull();
    }
}
