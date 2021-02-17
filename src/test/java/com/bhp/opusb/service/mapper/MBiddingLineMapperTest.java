package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MBiddingLineMapperTest {

    private MBiddingLineMapper mBiddingLineMapper;

    @BeforeEach
    public void setUp() {
        mBiddingLineMapper = new MBiddingLineMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mBiddingLineMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mBiddingLineMapper.fromId(null)).isNull();
    }
}
