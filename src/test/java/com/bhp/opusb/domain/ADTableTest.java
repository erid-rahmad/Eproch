package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class ADTableTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ADTable.class);
        ADTable aDTable1 = new ADTable();
        aDTable1.setId(1L);
        ADTable aDTable2 = new ADTable();
        aDTable2.setId(aDTable1.getId());
        assertThat(aDTable1).isEqualTo(aDTable2);
        aDTable2.setId(2L);
        assertThat(aDTable1).isNotEqualTo(aDTable2);
        aDTable1.setId(null);
        assertThat(aDTable1).isNotEqualTo(aDTable2);
    }
}
