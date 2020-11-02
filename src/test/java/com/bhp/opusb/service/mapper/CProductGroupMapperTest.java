package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CProductGroupMapperTest {

    private CProductGroupMapper cProductGroupMapper;

    @BeforeEach
    public void setUp() {
        cProductGroupMapper = new CProductGroupMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cProductGroupMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cProductGroupMapper.fromId(null)).isNull();
    }
}
