package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MContractClauseDocumentTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MContractClauseDocument.class);
        MContractClauseDocument mContractClauseDocument1 = new MContractClauseDocument();
        mContractClauseDocument1.setId(1L);
        MContractClauseDocument mContractClauseDocument2 = new MContractClauseDocument();
        mContractClauseDocument2.setId(mContractClauseDocument1.getId());
        assertThat(mContractClauseDocument1).isEqualTo(mContractClauseDocument2);
        mContractClauseDocument2.setId(2L);
        assertThat(mContractClauseDocument1).isNotEqualTo(mContractClauseDocument2);
        mContractClauseDocument1.setId(null);
        assertThat(mContractClauseDocument1).isNotEqualTo(mContractClauseDocument2);
    }
}
