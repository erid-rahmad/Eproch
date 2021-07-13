package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CPrequalMethodLineMapperTest {

    private CPrequalMethodLineMapper cPrequalMethodLineMapper;

    @BeforeEach
    public void setUp() {
        cPrequalMethodLineMapper = new CPrequalMethodLineMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cPrequalMethodLineMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cPrequalMethodLineMapper.fromId(null)).isNull();
    }
}
