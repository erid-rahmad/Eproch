package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ADOrganizationMapperTest {

    private ADOrganizationMapper aDOrganizationMapper;

    @BeforeEach
    public void setUp() {
        aDOrganizationMapper = new ADOrganizationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(aDOrganizationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(aDOrganizationMapper.fromId(null)).isNull();
    }
}
