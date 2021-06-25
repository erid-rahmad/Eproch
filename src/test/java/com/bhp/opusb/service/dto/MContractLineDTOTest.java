package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MContractLineDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MContractLineDTO.class);
        MContractLineDTO mContractLineDTO1 = new MContractLineDTO();
        mContractLineDTO1.setId(1L);
        MContractLineDTO mContractLineDTO2 = new MContractLineDTO();
        assertThat(mContractLineDTO1).isNotEqualTo(mContractLineDTO2);
        mContractLineDTO2.setId(mContractLineDTO1.getId());
        assertThat(mContractLineDTO1).isEqualTo(mContractLineDTO2);
        mContractLineDTO2.setId(2L);
        assertThat(mContractLineDTO1).isNotEqualTo(mContractLineDTO2);
        mContractLineDTO1.setId(null);
        assertThat(mContractLineDTO1).isNotEqualTo(mContractLineDTO2);
    }
}
