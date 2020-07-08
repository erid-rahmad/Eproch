package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CBusinessCategoryMapperTest {

    private CBusinessCategoryMapper cBusinessCategoryMapper;

    @BeforeEach
    public void setUp() {
        cBusinessCategoryMapper = new CBusinessCategoryMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cBusinessCategoryMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cBusinessCategoryMapper.fromId(null)).isNull();
    }
}
