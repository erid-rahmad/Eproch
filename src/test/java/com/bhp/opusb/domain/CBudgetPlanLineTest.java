package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CBudgetPlanLineTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CBudgetPlanLine.class);
        CBudgetPlanLine cBudgetPlanLine1 = new CBudgetPlanLine();
        cBudgetPlanLine1.setId(1L);
        CBudgetPlanLine cBudgetPlanLine2 = new CBudgetPlanLine();
        cBudgetPlanLine2.setId(cBudgetPlanLine1.getId());
        assertThat(cBudgetPlanLine1).isEqualTo(cBudgetPlanLine2);
        cBudgetPlanLine2.setId(2L);
        assertThat(cBudgetPlanLine1).isNotEqualTo(cBudgetPlanLine2);
        cBudgetPlanLine1.setId(null);
        assertThat(cBudgetPlanLine1).isNotEqualTo(cBudgetPlanLine2);
    }
}
