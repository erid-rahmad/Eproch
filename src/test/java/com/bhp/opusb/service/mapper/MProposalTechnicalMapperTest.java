package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MProposalTechnicalMapperTest {

    private MProposalTechnicalMapper mProposalTechnicalMapper;

    @BeforeEach
    public void setUp() {
        mProposalTechnicalMapper = new MProposalTechnicalMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mProposalTechnicalMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mProposalTechnicalMapper.fromId(null)).isNull();
    }
}
