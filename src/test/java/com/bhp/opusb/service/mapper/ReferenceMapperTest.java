package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ReferenceMapperTest {

    private ReferenceMapper referenceMapper;

    @BeforeEach
    public void setUp() {
        referenceMapper = new ReferenceMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(referenceMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(referenceMapper.fromId(null)).isNull();
    }
}
