package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MContractTeamLineMapperTest {

    private MContractTeamLineMapper mContractTeamLineMapper;

    @BeforeEach
    public void setUp() {
        mContractTeamLineMapper = new MContractTeamLineMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mContractTeamLineMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mContractTeamLineMapper.fromId(null)).isNull();
    }
}
