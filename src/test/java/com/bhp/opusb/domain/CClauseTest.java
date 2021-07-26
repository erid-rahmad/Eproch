package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CClauseTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CClause.class);
        CClause cClause1 = new CClause();
        cClause1.setId(1L);
        CClause cClause2 = new CClause();
        cClause2.setId(cClause1.getId());
        assertThat(cClause1).isEqualTo(cClause2);
        cClause2.setId(2L);
        assertThat(cClause1).isNotEqualTo(cClause2);
        cClause1.setId(null);
        assertThat(cClause1).isNotEqualTo(cClause2);
    }
}
