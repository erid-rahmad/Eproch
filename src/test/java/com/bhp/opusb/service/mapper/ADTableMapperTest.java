package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ADTableMapperTest {

    private ADTableMapper aDTableMapper;

    @BeforeEach
    public void setUp() {
        aDTableMapper = new ADTableMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(aDTableMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(aDTableMapper.fromId(null)).isNull();
    }
}
