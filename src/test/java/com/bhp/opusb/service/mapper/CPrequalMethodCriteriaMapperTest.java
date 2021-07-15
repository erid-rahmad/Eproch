package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CPrequalMethodCriteriaMapperTest {

    private CPrequalMethodCriteriaMapper cPrequalMethodCriteriaMapper;

    @BeforeEach
    public void setUp() {
        cPrequalMethodCriteriaMapper = new CPrequalMethodCriteriaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cPrequalMethodCriteriaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cPrequalMethodCriteriaMapper.fromId(null)).isNull();
    }
}
