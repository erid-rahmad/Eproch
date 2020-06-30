package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class ADReferenceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ADReference.class);
        ADReference aDReference1 = new ADReference();
        aDReference1.setId(1L);
        ADReference aDReference2 = new ADReference();
        aDReference2.setId(aDReference1.getId());
        assertThat(aDReference1).isEqualTo(aDReference2);
        aDReference2.setId(2L);
        assertThat(aDReference1).isNotEqualTo(aDReference2);
        aDReference1.setId(null);
        assertThat(aDReference1).isNotEqualTo(aDReference2);
    }
}
