package com.bhp.opusb.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class AuthenticatedUser extends User {

  private static final long serialVersionUID = 1L;

  private final long vendorId;

  private final Collection<Long> organizations;

  private final Collection<String> accesses;
  
  public AuthenticatedUser(String username, String password, Collection<? extends GrantedAuthority> authorities,
    Collection<Long> organizations, Collection<String> accesses, long vendorId) {
    super(username, password, authorities);
    this.vendorId = vendorId;
    this.organizations = organizations;
    this.accesses = accesses;
  }

  public long getVendorId() {
    return vendorId;
  }

  public Collection<Long> getOrganizations() {
    return organizations;
  }

  public Collection<String> getAccesses() {
    return accesses;
  }

}
