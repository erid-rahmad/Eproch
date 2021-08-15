package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MPrequalificationResultMapperTest {

    private MPrequalificationResultMapper mPrequalificationResultMapper;

    @BeforeEach
    public void setUp() {
        mPrequalificationResultMapper = new MPrequalificationResultMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mPrequalificationResultMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mPrequalificationResultMapper.fromId(null)).isNull();
    }
}
