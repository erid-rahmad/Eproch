package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ADTabMapperTest {

    private ADTabMapper aDTabMapper;

    @BeforeEach
    public void setUp() {
        aDTabMapper = new ADTabMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(aDTabMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(aDTabMapper.fromId(null)).isNull();
    }
}
