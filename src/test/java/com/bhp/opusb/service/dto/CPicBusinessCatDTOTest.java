package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CPicBusinessCatDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CPicBusinessCatDTO.class);
        CPicBusinessCatDTO cPicBusinessCatDTO1 = new CPicBusinessCatDTO();
        cPicBusinessCatDTO1.setId(1L);
        CPicBusinessCatDTO cPicBusinessCatDTO2 = new CPicBusinessCatDTO();
        assertThat(cPicBusinessCatDTO1).isNotEqualTo(cPicBusinessCatDTO2);
        cPicBusinessCatDTO2.setId(cPicBusinessCatDTO1.getId());
        assertThat(cPicBusinessCatDTO1).isEqualTo(cPicBusinessCatDTO2);
        cPicBusinessCatDTO2.setId(2L);
        assertThat(cPicBusinessCatDTO1).isNotEqualTo(cPicBusinessCatDTO2);
        cPicBusinessCatDTO1.setId(null);
        assertThat(cPicBusinessCatDTO1).isNotEqualTo(cPicBusinessCatDTO2);
    }
}
