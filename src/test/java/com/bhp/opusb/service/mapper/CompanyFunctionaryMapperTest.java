package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CompanyFunctionaryMapperTest {

    private CompanyFunctionaryMapper companyFunctionaryMapper;

    @BeforeEach
    public void setUp() {
        companyFunctionaryMapper = new CompanyFunctionaryMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(companyFunctionaryMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(companyFunctionaryMapper.fromId(null)).isNull();
    }
}
