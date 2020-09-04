package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CProductGroupAccountMapperTest {

    private CProductGroupAccountMapper cProductGroupAccountMapper;

    @BeforeEach
    public void setUp() {
        cProductGroupAccountMapper = new CProductGroupAccountMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cProductGroupAccountMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cProductGroupAccountMapper.fromId(null)).isNull();
    }
}
