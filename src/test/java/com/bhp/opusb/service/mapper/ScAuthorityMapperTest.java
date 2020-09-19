package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ScAuthorityMapperTest {

    private ScAuthorityMapper scAuthorityMapper;

    @BeforeEach
    public void setUp() {
        scAuthorityMapper = new ScAuthorityMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(scAuthorityMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(scAuthorityMapper.fromId(null)).isNull();
    }
}
