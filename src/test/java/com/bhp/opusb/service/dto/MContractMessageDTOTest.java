package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MContractMessageDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MContractMessageDTO.class);
        MContractMessageDTO mContractMessageDTO1 = new MContractMessageDTO();
        mContractMessageDTO1.setId(1L);
        MContractMessageDTO mContractMessageDTO2 = new MContractMessageDTO();
        assertThat(mContractMessageDTO1).isNotEqualTo(mContractMessageDTO2);
        mContractMessageDTO2.setId(mContractMessageDTO1.getId());
        assertThat(mContractMessageDTO1).isEqualTo(mContractMessageDTO2);
        mContractMessageDTO2.setId(2L);
        assertThat(mContractMessageDTO1).isNotEqualTo(mContractMessageDTO2);
        mContractMessageDTO1.setId(null);
        assertThat(mContractMessageDTO1).isNotEqualTo(mContractMessageDTO2);
    }
}
