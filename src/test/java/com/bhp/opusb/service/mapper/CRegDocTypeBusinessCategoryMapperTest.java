package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CRegDocTypeBusinessCategoryMapperTest {

    private CRegDocTypeBusinessCategoryMapper cRegDocTypeBusinessCategoryMapper;

    @BeforeEach
    public void setUp() {
        cRegDocTypeBusinessCategoryMapper = new CRegDocTypeBusinessCategoryMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cRegDocTypeBusinessCategoryMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cRegDocTypeBusinessCategoryMapper.fromId(null)).isNull();
    }
}
