package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PermissionMapperTest {

    private PermissionMapper permissionMapper;

    @BeforeEach
    public void setUp() {
        permissionMapper = new PermissionMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(permissionMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(permissionMapper.fromId(null)).isNull();
    }
}
