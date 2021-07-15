package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CPrequalMethodLineTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CPrequalMethodLine.class);
        CPrequalMethodLine cPrequalMethodLine1 = new CPrequalMethodLine();
        cPrequalMethodLine1.setId(1L);
        CPrequalMethodLine cPrequalMethodLine2 = new CPrequalMethodLine();
        cPrequalMethodLine2.setId(cPrequalMethodLine1.getId());
        assertThat(cPrequalMethodLine1).isEqualTo(cPrequalMethodLine2);
        cPrequalMethodLine2.setId(2L);
        assertThat(cPrequalMethodLine1).isNotEqualTo(cPrequalMethodLine2);
        cPrequalMethodLine1.setId(null);
        assertThat(cPrequalMethodLine1).isNotEqualTo(cPrequalMethodLine2);
    }
}
