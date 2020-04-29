package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ADFieldMapperTest {

    private ADFieldMapper aDFieldMapper;

    @BeforeEach
    public void setUp() {
        aDFieldMapper = new ADFieldMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(aDFieldMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(aDFieldMapper.fromId(null)).isNull();
    }
}
