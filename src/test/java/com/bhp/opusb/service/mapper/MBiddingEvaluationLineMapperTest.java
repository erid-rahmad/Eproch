package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MBiddingEvaluationLineMapperTest {

    private MBiddingEvaluationLineMapper mBiddingEvaluationLineMapper;

    @BeforeEach
    public void setUp() {
        mBiddingEvaluationLineMapper = new MBiddingEvaluationLineMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mBiddingEvaluationLineMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mBiddingEvaluationLineMapper.fromId(null)).isNull();
    }
}
