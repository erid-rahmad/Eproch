package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MContractDocumentTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MContractDocument.class);
        MContractDocument mContractDocument1 = new MContractDocument();
        mContractDocument1.setId(1L);
        MContractDocument mContractDocument2 = new MContractDocument();
        mContractDocument2.setId(mContractDocument1.getId());
        assertThat(mContractDocument1).isEqualTo(mContractDocument2);
        mContractDocument2.setId(2L);
        assertThat(mContractDocument1).isNotEqualTo(mContractDocument2);
        mContractDocument1.setId(null);
        assertThat(mContractDocument1).isNotEqualTo(mContractDocument2);
    }
}
