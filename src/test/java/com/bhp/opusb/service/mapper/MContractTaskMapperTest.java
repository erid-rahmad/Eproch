package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MContractTaskMapperTest {

    private MContractTaskMapper mContractTaskMapper;

    @BeforeEach
    public void setUp() {
        mContractTaskMapper = new MContractTaskMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mContractTaskMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mContractTaskMapper.fromId(null)).isNull();
    }
}
