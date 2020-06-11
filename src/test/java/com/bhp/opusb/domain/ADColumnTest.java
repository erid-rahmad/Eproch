package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class ADColumnTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ADColumn.class);
        ADColumn aDColumn1 = new ADColumn();
        aDColumn1.setId(1L);
        ADColumn aDColumn2 = new ADColumn();
        aDColumn2.setId(aDColumn1.getId());
        assertThat(aDColumn1).isEqualTo(aDColumn2);
        aDColumn2.setId(2L);
        assertThat(aDColumn1).isNotEqualTo(aDColumn2);
        aDColumn1.setId(null);
        assertThat(aDColumn1).isNotEqualTo(aDColumn2);
    }
}
