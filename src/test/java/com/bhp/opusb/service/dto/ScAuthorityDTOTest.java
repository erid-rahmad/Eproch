package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class ScAuthorityDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ScAuthorityDTO.class);
        ScAuthorityDTO scAuthorityDTO1 = new ScAuthorityDTO();
        scAuthorityDTO1.setId(1L);
        ScAuthorityDTO scAuthorityDTO2 = new ScAuthorityDTO();
        assertThat(scAuthorityDTO1).isNotEqualTo(scAuthorityDTO2);
        scAuthorityDTO2.setId(scAuthorityDTO1.getId());
        assertThat(scAuthorityDTO1).isEqualTo(scAuthorityDTO2);
        scAuthorityDTO2.setId(2L);
        assertThat(scAuthorityDTO1).isNotEqualTo(scAuthorityDTO2);
        scAuthorityDTO1.setId(null);
        assertThat(scAuthorityDTO1).isNotEqualTo(scAuthorityDTO2);
    }
}
