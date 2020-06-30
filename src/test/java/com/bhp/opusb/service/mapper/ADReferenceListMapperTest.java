package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ADReferenceListMapperTest {

    private ADReferenceListMapper aDReferenceListMapper;

    @BeforeEach
    public void setUp() {
        aDReferenceListMapper = new ADReferenceListMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(aDReferenceListMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(aDReferenceListMapper.fromId(null)).isNull();
    }
}
