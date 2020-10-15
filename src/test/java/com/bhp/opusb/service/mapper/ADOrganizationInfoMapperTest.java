package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ADOrganizationInfoMapperTest {

    private ADOrganizationInfoMapper aDOrganizationInfoMapper;

    @BeforeEach
    public void setUp() {
        aDOrganizationInfoMapper = new ADOrganizationInfoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(aDOrganizationInfoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(aDOrganizationInfoMapper.fromId(null)).isNull();
    }
}
