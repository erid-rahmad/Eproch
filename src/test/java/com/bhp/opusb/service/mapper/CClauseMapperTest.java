package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CClauseMapperTest {

    private CClauseMapper cClauseMapper;

    @BeforeEach
    public void setUp() {
        cClauseMapper = new CClauseMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cClauseMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cClauseMapper.fromId(null)).isNull();
    }
}
