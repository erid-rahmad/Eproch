package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MContractMessageMapperTest {

    private MContractMessageMapper mContractMessageMapper;

    @BeforeEach
    public void setUp() {
        mContractMessageMapper = new MContractMessageMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mContractMessageMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mContractMessageMapper.fromId(null)).isNull();
    }
}
