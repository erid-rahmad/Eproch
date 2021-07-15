package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MPrequalificationInvitationMapperTest {

    private MPrequalificationInvitationMapper mPrequalificationInvitationMapper;

    @BeforeEach
    public void setUp() {
        mPrequalificationInvitationMapper = new MPrequalificationInvitationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mPrequalificationInvitationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mPrequalificationInvitationMapper.fromId(null)).isNull();
    }
}
