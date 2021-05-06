package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MProposalAdministrationFileMapperTest {

    private MProposalAdministrationFileMapper mProposalAdministrationFileMapper;

    @BeforeEach
    public void setUp() {
        mProposalAdministrationFileMapper = new MProposalAdministrationFileMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mProposalAdministrationFileMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mProposalAdministrationFileMapper.fromId(null)).isNull();
    }
}
