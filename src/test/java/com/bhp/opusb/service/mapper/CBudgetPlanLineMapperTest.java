package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CBudgetPlanLineMapperTest {

    private CBudgetPlanLineMapper cBudgetPlanLineMapper;

    @BeforeEach
    public void setUp() {
        cBudgetPlanLineMapper = new CBudgetPlanLineMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cBudgetPlanLineMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cBudgetPlanLineMapper.fromId(null)).isNull();
    }
}
