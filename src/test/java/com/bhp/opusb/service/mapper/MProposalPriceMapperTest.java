package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MProposalPriceMapperTest {

    private MProposalPriceMapper mProposalPriceMapper;

    @BeforeEach
    public void setUp() {
        mProposalPriceMapper = new MProposalPriceMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mProposalPriceMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mProposalPriceMapper.fromId(null)).isNull();
    }
}
