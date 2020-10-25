package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AdUserAuthorityMapperTest {

    private AdUserAuthorityMapper adUserAuthorityMapper;

    @BeforeEach
    public void setUp() {
        adUserAuthorityMapper = new AdUserAuthorityMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(adUserAuthorityMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(adUserAuthorityMapper.fromId(null)).isNull();
    }
}
