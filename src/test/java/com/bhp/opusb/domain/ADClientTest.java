package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class ADClientTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ADClient.class);
        ADClient aDClient1 = new ADClient();
        aDClient1.setId(1L);
        ADClient aDClient2 = new ADClient();
        aDClient2.setId(aDClient1.getId());
        assertThat(aDClient1).isEqualTo(aDClient2);
        aDClient2.setId(2L);
        assertThat(aDClient1).isNotEqualTo(aDClient2);
        aDClient1.setId(null);
        assertThat(aDClient1).isNotEqualTo(aDClient2);
    }
}
