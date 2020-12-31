package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MBrandMapperTest {

    private MBrandMapper mBrandMapper;

    @BeforeEach
    public void setUp() {
        mBrandMapper = new MBrandMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mBrandMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mBrandMapper.fromId(null)).isNull();
    }
}
