package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CClauseLineTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CClauseLine.class);
        CClauseLine cClauseLine1 = new CClauseLine();
        cClauseLine1.setId(1L);
        CClauseLine cClauseLine2 = new CClauseLine();
        cClauseLine2.setId(cClauseLine1.getId());
        assertThat(cClauseLine1).isEqualTo(cClauseLine2);
        cClauseLine2.setId(2L);
        assertThat(cClauseLine1).isNotEqualTo(cClauseLine2);
        cClauseLine1.setId(null);
        assertThat(cClauseLine1).isNotEqualTo(cClauseLine2);
    }
}
