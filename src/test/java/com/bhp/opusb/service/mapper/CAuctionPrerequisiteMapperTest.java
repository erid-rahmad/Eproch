package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CAuctionPrerequisiteMapperTest {

    private CAuctionPrerequisiteMapper cAuctionPrerequisiteMapper;

    @BeforeEach
    public void setUp() {
        cAuctionPrerequisiteMapper = new CAuctionPrerequisiteMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cAuctionPrerequisiteMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cAuctionPrerequisiteMapper.fromId(null)).isNull();
    }
}
