package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CBudgetPlanTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CBudgetPlan.class);
        CBudgetPlan cBudgetPlan1 = new CBudgetPlan();
        cBudgetPlan1.setId(1L);
        CBudgetPlan cBudgetPlan2 = new CBudgetPlan();
        cBudgetPlan2.setId(cBudgetPlan1.getId());
        assertThat(cBudgetPlan1).isEqualTo(cBudgetPlan2);
        cBudgetPlan2.setId(2L);
        assertThat(cBudgetPlan1).isNotEqualTo(cBudgetPlan2);
        cBudgetPlan1.setId(null);
        assertThat(cBudgetPlan1).isNotEqualTo(cBudgetPlan2);
    }
}
