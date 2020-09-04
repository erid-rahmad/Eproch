package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CConvertionTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CConvertionType.class);
        CConvertionType cConvertionType1 = new CConvertionType();
        cConvertionType1.setId(1L);
        CConvertionType cConvertionType2 = new CConvertionType();
        cConvertionType2.setId(cConvertionType1.getId());
        assertThat(cConvertionType1).isEqualTo(cConvertionType2);
        cConvertionType2.setId(2L);
        assertThat(cConvertionType1).isNotEqualTo(cConvertionType2);
        cConvertionType1.setId(null);
        assertThat(cConvertionType1).isNotEqualTo(cConvertionType2);
    }
}
