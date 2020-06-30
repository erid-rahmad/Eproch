package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class ADReferenceListTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ADReferenceList.class);
        ADReferenceList aDReferenceList1 = new ADReferenceList();
        aDReferenceList1.setId(1L);
        ADReferenceList aDReferenceList2 = new ADReferenceList();
        aDReferenceList2.setId(aDReferenceList1.getId());
        assertThat(aDReferenceList1).isEqualTo(aDReferenceList2);
        aDReferenceList2.setId(2L);
        assertThat(aDReferenceList1).isNotEqualTo(aDReferenceList2);
        aDReferenceList1.setId(null);
        assertThat(aDReferenceList1).isNotEqualTo(aDReferenceList2);
    }
}
