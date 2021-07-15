package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MPrequalificationInvitationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MPrequalificationInvitationDTO.class);
        MPrequalificationInvitationDTO mPrequalificationInvitationDTO1 = new MPrequalificationInvitationDTO();
        mPrequalificationInvitationDTO1.setId(1L);
        MPrequalificationInvitationDTO mPrequalificationInvitationDTO2 = new MPrequalificationInvitationDTO();
        assertThat(mPrequalificationInvitationDTO1).isNotEqualTo(mPrequalificationInvitationDTO2);
        mPrequalificationInvitationDTO2.setId(mPrequalificationInvitationDTO1.getId());
        assertThat(mPrequalificationInvitationDTO1).isEqualTo(mPrequalificationInvitationDTO2);
        mPrequalificationInvitationDTO2.setId(2L);
        assertThat(mPrequalificationInvitationDTO1).isNotEqualTo(mPrequalificationInvitationDTO2);
        mPrequalificationInvitationDTO1.setId(null);
        assertThat(mPrequalificationInvitationDTO1).isNotEqualTo(mPrequalificationInvitationDTO2);
    }
}
