package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CClauseDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CClauseDTO.class);
        CClauseDTO cClauseDTO1 = new CClauseDTO();
        cClauseDTO1.setId(1L);
        CClauseDTO cClauseDTO2 = new CClauseDTO();
        assertThat(cClauseDTO1).isNotEqualTo(cClauseDTO2);
        cClauseDTO2.setId(cClauseDTO1.getId());
        assertThat(cClauseDTO1).isEqualTo(cClauseDTO2);
        cClauseDTO2.setId(2L);
        assertThat(cClauseDTO1).isNotEqualTo(cClauseDTO2);
        cClauseDTO1.setId(null);
        assertThat(cClauseDTO1).isNotEqualTo(cClauseDTO2);
    }
}
