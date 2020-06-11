package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class ADFieldGroupDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ADFieldGroupDTO.class);
        ADFieldGroupDTO aDFieldGroupDTO1 = new ADFieldGroupDTO();
        aDFieldGroupDTO1.setId(1L);
        ADFieldGroupDTO aDFieldGroupDTO2 = new ADFieldGroupDTO();
        assertThat(aDFieldGroupDTO1).isNotEqualTo(aDFieldGroupDTO2);
        aDFieldGroupDTO2.setId(aDFieldGroupDTO1.getId());
        assertThat(aDFieldGroupDTO1).isEqualTo(aDFieldGroupDTO2);
        aDFieldGroupDTO2.setId(2L);
        assertThat(aDFieldGroupDTO1).isNotEqualTo(aDFieldGroupDTO2);
        aDFieldGroupDTO1.setId(null);
        assertThat(aDFieldGroupDTO1).isNotEqualTo(aDFieldGroupDTO2);
    }
}
