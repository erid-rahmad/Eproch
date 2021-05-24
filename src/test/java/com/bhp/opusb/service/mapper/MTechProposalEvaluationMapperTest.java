package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MTechProposalEvaluationMapperTest {

    private MTechProposalEvaluationMapper mTechProposalEvaluationMapper;

    @BeforeEach
    public void setUp() {
        mTechProposalEvaluationMapper = new MTechProposalEvaluationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mTechProposalEvaluationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mTechProposalEvaluationMapper.fromId(null)).isNull();
    }
}
