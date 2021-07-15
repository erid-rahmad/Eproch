package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MPrequalificationInformationMapperTest {

    private MPrequalificationInformationMapper mPrequalificationInformationMapper;

    @BeforeEach
    public void setUp() {
        mPrequalificationInformationMapper = new MPrequalificationInformationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mPrequalificationInformationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mPrequalificationInformationMapper.fromId(null)).isNull();
    }
}
