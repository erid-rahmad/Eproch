package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MContractTaskNegotiationMapperTest {

    private MContractTaskNegotiationMapper mContractTaskNegotiationMapper;

    @BeforeEach
    public void setUp() {
        mContractTaskNegotiationMapper = new MContractTaskNegotiationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mContractTaskNegotiationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mContractTaskNegotiationMapper.fromId(null)).isNull();
    }
}
