package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MProposalPriceSubItemMapperTest {

    private MProposalPriceSubItemMapper mProposalPriceSubItemMapper;

    @BeforeEach
    public void setUp() {
        mProposalPriceSubItemMapper = new MProposalPriceSubItemMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mProposalPriceSubItemMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mProposalPriceSubItemMapper.fromId(null)).isNull();
    }
}
