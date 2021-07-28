package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MContractTaskDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MContractTaskDTO.class);
        MContractTaskDTO mContractTaskDTO1 = new MContractTaskDTO();
        mContractTaskDTO1.setId(1L);
        MContractTaskDTO mContractTaskDTO2 = new MContractTaskDTO();
        assertThat(mContractTaskDTO1).isNotEqualTo(mContractTaskDTO2);
        mContractTaskDTO2.setId(mContractTaskDTO1.getId());
        assertThat(mContractTaskDTO1).isEqualTo(mContractTaskDTO2);
        mContractTaskDTO2.setId(2L);
        assertThat(mContractTaskDTO1).isNotEqualTo(mContractTaskDTO2);
        mContractTaskDTO1.setId(null);
        assertThat(mContractTaskDTO1).isNotEqualTo(mContractTaskDTO2);
    }
}
