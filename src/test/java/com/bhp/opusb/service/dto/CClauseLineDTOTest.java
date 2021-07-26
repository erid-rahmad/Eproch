package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CClauseLineDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CClauseLineDTO.class);
        CClauseLineDTO cClauseLineDTO1 = new CClauseLineDTO();
        cClauseLineDTO1.setId(1L);
        CClauseLineDTO cClauseLineDTO2 = new CClauseLineDTO();
        assertThat(cClauseLineDTO1).isNotEqualTo(cClauseLineDTO2);
        cClauseLineDTO2.setId(cClauseLineDTO1.getId());
        assertThat(cClauseLineDTO1).isEqualTo(cClauseLineDTO2);
        cClauseLineDTO2.setId(2L);
        assertThat(cClauseLineDTO1).isNotEqualTo(cClauseLineDTO2);
        cClauseLineDTO1.setId(null);
        assertThat(cClauseLineDTO1).isNotEqualTo(cClauseLineDTO2);
    }
}
