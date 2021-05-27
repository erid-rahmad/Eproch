package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MBiddingEvalResultLineMapperTest {

    private MBiddingEvalResultLineMapper mBiddingEvalResultLineMapper;

    @BeforeEach
    public void setUp() {
        mBiddingEvalResultLineMapper = new MBiddingEvalResultLineMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mBiddingEvalResultLineMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mBiddingEvalResultLineMapper.fromId(null)).isNull();
    }
}
