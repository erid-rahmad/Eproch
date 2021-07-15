package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MPrequalificationEventMapperTest {

    private MPrequalificationEventMapper mPrequalificationEventMapper;

    @BeforeEach
    public void setUp() {
        mPrequalificationEventMapper = new MPrequalificationEventMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mPrequalificationEventMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mPrequalificationEventMapper.fromId(null)).isNull();
    }
}
