package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CBudgetPlanMapperTest {

    private CBudgetPlanMapper cBudgetPlanMapper;

    @BeforeEach
    public void setUp() {
        cBudgetPlanMapper = new CBudgetPlanMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cBudgetPlanMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cBudgetPlanMapper.fromId(null)).isNull();
    }
}
