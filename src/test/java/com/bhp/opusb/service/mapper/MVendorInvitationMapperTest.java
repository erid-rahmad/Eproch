package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MVendorInvitationMapperTest {

    private MVendorInvitationMapper mVendorInvitationMapper;

    @BeforeEach
    public void setUp() {
        mVendorInvitationMapper = new MVendorInvitationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mVendorInvitationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mVendorInvitationMapper.fromId(null)).isNull();
    }
}
