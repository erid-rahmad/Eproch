package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class ReferenceListTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReferenceList.class);
        ReferenceList referenceList1 = new ReferenceList();
        referenceList1.setId(1L);
        ReferenceList referenceList2 = new ReferenceList();
        referenceList2.setId(referenceList1.getId());
        assertThat(referenceList1).isEqualTo(referenceList2);
        referenceList2.setId(2L);
        assertThat(referenceList1).isNotEqualTo(referenceList2);
        referenceList1.setId(null);
        assertThat(referenceList1).isNotEqualTo(referenceList2);
    }
}
