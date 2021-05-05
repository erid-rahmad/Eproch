package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MProposalAdministrationMapperTest {

    private MProposalAdministrationMapper mProposalAdministrationMapper;

    @BeforeEach
    public void setUp() {
        mProposalAdministrationMapper = new MProposalAdministrationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mProposalAdministrationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mProposalAdministrationMapper.fromId(null)).isNull();
    }
}
