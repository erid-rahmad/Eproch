package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class BusinessCategoryMapperTest {

    private BusinessCategoryMapper businessCategoryMapper;

    @BeforeEach
    public void setUp() {
        businessCategoryMapper = new BusinessCategoryMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(businessCategoryMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(businessCategoryMapper.fromId(null)).isNull();
    }
}
