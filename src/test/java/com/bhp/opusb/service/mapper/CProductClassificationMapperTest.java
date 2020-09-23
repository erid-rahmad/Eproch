package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CProductClassificationMapperTest {

    private CProductClassificationMapper cProductClassificationMapper;

    @BeforeEach
    public void setUp() {
        cProductClassificationMapper = new CProductClassificationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cProductClassificationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cProductClassificationMapper.fromId(null)).isNull();
    }
}
