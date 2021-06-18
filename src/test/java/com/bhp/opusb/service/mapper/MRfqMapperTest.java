package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MRfqMapperTest {

    private MRfqMapper mRfqMapper;

    @BeforeEach
    public void setUp() {
        mRfqMapper = new MRfqMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mRfqMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mRfqMapper.fromId(null)).isNull();
    }
}
