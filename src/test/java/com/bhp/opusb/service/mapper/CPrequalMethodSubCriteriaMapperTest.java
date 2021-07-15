package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CPrequalMethodSubCriteriaMapperTest {

    private CPrequalMethodSubCriteriaMapper cPrequalMethodSubCriteriaMapper;

    @BeforeEach
    public void setUp() {
        cPrequalMethodSubCriteriaMapper = new CPrequalMethodSubCriteriaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cPrequalMethodSubCriteriaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cPrequalMethodSubCriteriaMapper.fromId(null)).isNull();
    }
}
