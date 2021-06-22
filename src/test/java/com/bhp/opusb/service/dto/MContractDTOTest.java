package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MContractDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MContractDTO.class);
        MContractDTO mContractDTO1 = new MContractDTO();
        mContractDTO1.setId(1L);
        MContractDTO mContractDTO2 = new MContractDTO();
        assertThat(mContractDTO1).isNotEqualTo(mContractDTO2);
        mContractDTO2.setId(mContractDTO1.getId());
        assertThat(mContractDTO1).isEqualTo(mContractDTO2);
        mContractDTO2.setId(2L);
        assertThat(mContractDTO1).isNotEqualTo(mContractDTO2);
        mContractDTO1.setId(null);
        assertThat(mContractDTO1).isNotEqualTo(mContractDTO2);
    }
}
