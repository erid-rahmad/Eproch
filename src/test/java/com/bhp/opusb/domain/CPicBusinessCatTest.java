package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CPicBusinessCatTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CPicBusinessCat.class);
        CPicBusinessCat cPicBusinessCat1 = new CPicBusinessCat();
        cPicBusinessCat1.setId(1L);
        CPicBusinessCat cPicBusinessCat2 = new CPicBusinessCat();
        cPicBusinessCat2.setId(cPicBusinessCat1.getId());
        assertThat(cPicBusinessCat1).isEqualTo(cPicBusinessCat2);
        cPicBusinessCat2.setId(2L);
        assertThat(cPicBusinessCat1).isNotEqualTo(cPicBusinessCat2);
        cPicBusinessCat1.setId(null);
        assertThat(cPicBusinessCat1).isNotEqualTo(cPicBusinessCat2);
    }
}
