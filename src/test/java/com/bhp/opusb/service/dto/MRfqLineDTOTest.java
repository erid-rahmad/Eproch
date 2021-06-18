package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MRfqLineDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MRfqLineDTO.class);
        MRfqLineDTO mRfqLineDTO1 = new MRfqLineDTO();
        mRfqLineDTO1.setId(1L);
        MRfqLineDTO mRfqLineDTO2 = new MRfqLineDTO();
        assertThat(mRfqLineDTO1).isNotEqualTo(mRfqLineDTO2);
        mRfqLineDTO2.setId(mRfqLineDTO1.getId());
        assertThat(mRfqLineDTO1).isEqualTo(mRfqLineDTO2);
        mRfqLineDTO2.setId(2L);
        assertThat(mRfqLineDTO1).isNotEqualTo(mRfqLineDTO2);
        mRfqLineDTO1.setId(null);
        assertThat(mRfqLineDTO1).isNotEqualTo(mRfqLineDTO2);
    }
}
