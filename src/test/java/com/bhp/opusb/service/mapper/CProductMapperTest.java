package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CProductMapperTest {

    private CProductMapper cProductMapper;

    @BeforeEach
    public void setUp() {
        cProductMapper = new CProductMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cProductMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cProductMapper.fromId(null)).isNull();
    }
}
