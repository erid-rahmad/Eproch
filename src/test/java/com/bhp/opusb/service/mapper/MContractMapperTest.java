package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MContractMapperTest {

    private MContractMapper mContractMapper;

    @BeforeEach
    public void setUp() {
        mContractMapper = new MContractMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mContractMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mContractMapper.fromId(null)).isNull();
    }
}
