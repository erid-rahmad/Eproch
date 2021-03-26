package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CBudgetPlanLineDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CBudgetPlanLineDTO.class);
        CBudgetPlanLineDTO cBudgetPlanLineDTO1 = new CBudgetPlanLineDTO();
        cBudgetPlanLineDTO1.setId(1L);
        CBudgetPlanLineDTO cBudgetPlanLineDTO2 = new CBudgetPlanLineDTO();
        assertThat(cBudgetPlanLineDTO1).isNotEqualTo(cBudgetPlanLineDTO2);
        cBudgetPlanLineDTO2.setId(cBudgetPlanLineDTO1.getId());
        assertThat(cBudgetPlanLineDTO1).isEqualTo(cBudgetPlanLineDTO2);
        cBudgetPlanLineDTO2.setId(2L);
        assertThat(cBudgetPlanLineDTO1).isNotEqualTo(cBudgetPlanLineDTO2);
        cBudgetPlanLineDTO1.setId(null);
        assertThat(cBudgetPlanLineDTO1).isNotEqualTo(cBudgetPlanLineDTO2);
    }
}
