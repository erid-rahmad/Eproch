package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MBrandDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MBrandDTO.class);
        MBrandDTO mBrandDTO1 = new MBrandDTO();
        mBrandDTO1.setId(1L);
        MBrandDTO mBrandDTO2 = new MBrandDTO();
        assertThat(mBrandDTO1).isNotEqualTo(mBrandDTO2);
        mBrandDTO2.setId(mBrandDTO1.getId());
        assertThat(mBrandDTO1).isEqualTo(mBrandDTO2);
        mBrandDTO2.setId(2L);
        assertThat(mBrandDTO1).isNotEqualTo(mBrandDTO2);
        mBrandDTO1.setId(null);
        assertThat(mBrandDTO1).isNotEqualTo(mBrandDTO2);
    }
}
