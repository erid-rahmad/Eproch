package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class COrganizationMapperTest {

    private COrganizationMapper cOrganizationMapper;

    @BeforeEach
    public void setUp() {
        cOrganizationMapper = new COrganizationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cOrganizationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cOrganizationMapper.fromId(null)).isNull();
    }
}
