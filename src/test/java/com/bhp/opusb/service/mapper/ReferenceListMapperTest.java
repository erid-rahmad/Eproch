package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ReferenceListMapperTest {

    private ReferenceListMapper referenceListMapper;

    @BeforeEach
    public void setUp() {
        referenceListMapper = new ReferenceListMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(referenceListMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(referenceListMapper.fromId(null)).isNull();
    }
}
