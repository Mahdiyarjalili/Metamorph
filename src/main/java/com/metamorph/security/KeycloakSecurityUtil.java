 /*package com.metamorph.security;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KeycloakSecurityUtil {

  Keycloak keycloak;
  @Value("http://localhost:8080/auth/")
  private String serverUrl;
  @Value("metamorph-dev")
  private String realm;
  @Value("metamorph-dev")
  private String clientId;
  @Value("password")
  private String grantType;
  @Value("mahdiyar")
  private String username;
  @Value("mahdiyar")
  private String password;

  public Keycloak getKeycloakInstance() {

    if (keycloak == null) {
      keycloak = KeycloakBuilder.builder()
          .serverUrl(serverUrl)
          .realm(realm)
          .clientId(clientId)
          .grantType(grantType)
          .username(username)
          .password(password)
          .build();
    }
    return keycloak;
  }
}
*/