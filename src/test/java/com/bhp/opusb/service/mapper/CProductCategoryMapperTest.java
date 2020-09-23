package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CProductCategoryMapperTest {

    private CProductCategoryMapper cProductCategoryMapper;

    @BeforeEach
    public void setUp() {
        cProductCategoryMapper = new CProductCategoryMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cProductCategoryMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cProductCategoryMapper.fromId(null)).isNull();
    }
}
