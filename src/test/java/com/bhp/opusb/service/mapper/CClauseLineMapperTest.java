package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CClauseLineMapperTest {

    private CClauseLineMapper cClauseLineMapper;

    @BeforeEach
    public void setUp() {
        cClauseLineMapper = new CClauseLineMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cClauseLineMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cClauseLineMapper.fromId(null)).isNull();
    }
}
