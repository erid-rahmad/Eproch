package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ADReferenceMapperTest {

    private ADReferenceMapper aDReferenceMapper;

    @BeforeEach
    public void setUp() {
        aDReferenceMapper = new ADReferenceMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(aDReferenceMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(aDReferenceMapper.fromId(null)).isNull();
    }
}
