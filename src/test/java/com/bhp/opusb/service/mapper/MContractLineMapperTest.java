package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MContractLineMapperTest {

    private MContractLineMapper mContractLineMapper;

    @BeforeEach
    public void setUp() {
        mContractLineMapper = new MContractLineMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mContractLineMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mContractLineMapper.fromId(null)).isNull();
    }
}
