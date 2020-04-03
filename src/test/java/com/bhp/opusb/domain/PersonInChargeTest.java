package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class PersonInChargeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonInCharge.class);
        PersonInCharge personInCharge1 = new PersonInCharge();
        personInCharge1.setId(1L);
        PersonInCharge personInCharge2 = new PersonInCharge();
        personInCharge2.setId(personInCharge1.getId());
        assertThat(personInCharge1).isEqualTo(personInCharge2);
        personInCharge2.setId(2L);
        assertThat(personInCharge1).isNotEqualTo(personInCharge2);
        personInCharge1.setId(null);
        assertThat(personInCharge1).isNotEqualTo(personInCharge2);
    }
}
