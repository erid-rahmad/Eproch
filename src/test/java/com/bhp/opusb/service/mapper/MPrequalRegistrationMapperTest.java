package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MPrequalRegistrationMapperTest {

    private MPrequalRegistrationMapper mPrequalRegistrationMapper;

    @BeforeEach
    public void setUp() {
        mPrequalRegistrationMapper = new MPrequalRegistrationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mPrequalRegistrationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mPrequalRegistrationMapper.fromId(null)).isNull();
    }
}
