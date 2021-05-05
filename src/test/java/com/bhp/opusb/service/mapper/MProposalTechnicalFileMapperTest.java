package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MProposalTechnicalFileMapperTest {

    private MProposalTechnicalFileMapper mProposalTechnicalFileMapper;

    @BeforeEach
    public void setUp() {
        mProposalTechnicalFileMapper = new MProposalTechnicalFileMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mProposalTechnicalFileMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mProposalTechnicalFileMapper.fromId(null)).isNull();
    }
}
