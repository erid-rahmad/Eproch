package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MContractTaskNegotiationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MContractTaskNegotiationDTO.class);
        MContractTaskNegotiationDTO mContractTaskNegotiationDTO1 = new MContractTaskNegotiationDTO();
        mContractTaskNegotiationDTO1.setId(1L);
        MContractTaskNegotiationDTO mContractTaskNegotiationDTO2 = new MContractTaskNegotiationDTO();
        assertThat(mContractTaskNegotiationDTO1).isNotEqualTo(mContractTaskNegotiationDTO2);
        mContractTaskNegotiationDTO2.setId(mContractTaskNegotiationDTO1.getId());
        assertThat(mContractTaskNegotiationDTO1).isEqualTo(mContractTaskNegotiationDTO2);
        mContractTaskNegotiationDTO2.setId(2L);
        assertThat(mContractTaskNegotiationDTO1).isNotEqualTo(mContractTaskNegotiationDTO2);
        mContractTaskNegotiationDTO1.setId(null);
        assertThat(mContractTaskNegotiationDTO1).isNotEqualTo(mContractTaskNegotiationDTO2);
    }
}
