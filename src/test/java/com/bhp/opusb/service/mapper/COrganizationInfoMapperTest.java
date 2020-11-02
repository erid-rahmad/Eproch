package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class COrganizationInfoMapperTest {

    private COrganizationInfoMapper cOrganizationInfoMapper;

    @BeforeEach
    public void setUp() {
        cOrganizationInfoMapper = new COrganizationInfoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cOrganizationInfoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cOrganizationInfoMapper.fromId(null)).isNull();
    }
}
