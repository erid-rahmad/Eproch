package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MProposalPriceLineMapperTest {

    private MProposalPriceLineMapper mProposalPriceLineMapper;

    @BeforeEach
    public void setUp() {
        mProposalPriceLineMapper = new MProposalPriceLineMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mProposalPriceLineMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mProposalPriceLineMapper.fromId(null)).isNull();
    }
}
