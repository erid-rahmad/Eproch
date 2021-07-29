package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MContractTeamMapperTest {

    private MContractTeamMapper mContractTeamMapper;

    @BeforeEach
    public void setUp() {
        mContractTeamMapper = new MContractTeamMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mContractTeamMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mContractTeamMapper.fromId(null)).isNull();
    }
}
