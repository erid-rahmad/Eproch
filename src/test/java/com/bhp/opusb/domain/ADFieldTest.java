package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class ADFieldTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ADField.class);
        ADField aDField1 = new ADField();
        aDField1.setId(1L);
        ADField aDField2 = new ADField();
        aDField2.setId(aDField1.getId());
        assertThat(aDField1).isEqualTo(aDField2);
        aDField2.setId(2L);
        assertThat(aDField1).isNotEqualTo(aDField2);
        aDField1.setId(null);
        assertThat(aDField1).isNotEqualTo(aDField2);
    }
}
