package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MShoppingCartMapperTest {

    private MShoppingCartMapper mShoppingCartMapper;

    @BeforeEach
    public void setUp() {
        mShoppingCartMapper = new MShoppingCartMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mShoppingCartMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mShoppingCartMapper.fromId(null)).isNull();
    }
}
