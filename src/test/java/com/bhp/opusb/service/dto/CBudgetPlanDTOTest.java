package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CBudgetPlanDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CBudgetPlanDTO.class);
        CBudgetPlanDTO cBudgetPlanDTO1 = new CBudgetPlanDTO();
        cBudgetPlanDTO1.setId(1L);
        CBudgetPlanDTO cBudgetPlanDTO2 = new CBudgetPlanDTO();
        assertThat(cBudgetPlanDTO1).isNotEqualTo(cBudgetPlanDTO2);
        cBudgetPlanDTO2.setId(cBudgetPlanDTO1.getId());
        assertThat(cBudgetPlanDTO1).isEqualTo(cBudgetPlanDTO2);
        cBudgetPlanDTO2.setId(2L);
        assertThat(cBudgetPlanDTO1).isNotEqualTo(cBudgetPlanDTO2);
        cBudgetPlanDTO1.setId(null);
        assertThat(cBudgetPlanDTO1).isNotEqualTo(cBudgetPlanDTO2);
    }
}
