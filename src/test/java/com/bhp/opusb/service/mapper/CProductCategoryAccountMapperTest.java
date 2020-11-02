package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CProductCategoryAccountMapperTest {

    private CProductCategoryAccountMapper cProductCategoryAccountMapper;

    @BeforeEach
    public void setUp() {
        cProductCategoryAccountMapper = new CProductCategoryAccountMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cProductCategoryAccountMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cProductCategoryAccountMapper.fromId(null)).isNull();
    }
}
