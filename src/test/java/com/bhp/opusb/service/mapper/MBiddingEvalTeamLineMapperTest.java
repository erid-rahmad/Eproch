package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MBiddingEvalTeamLineMapperTest {

    private MBiddingEvalTeamLineMapper mBiddingEvalTeamLineMapper;

    @BeforeEach
    public void setUp() {
        mBiddingEvalTeamLineMapper = new MBiddingEvalTeamLineMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mBiddingEvalTeamLineMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mBiddingEvalTeamLineMapper.fromId(null)).isNull();
    }
}
