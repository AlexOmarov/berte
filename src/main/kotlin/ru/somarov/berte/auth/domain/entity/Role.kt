package ru.somarov.berte.auth.domain.entity

import org.springframework.data.relational.core.mapping.Table
import org.springframework.security.oauth2.core.oidc.OidcIdToken
import org.springframework.security.oauth2.core.oidc.OidcUserInfo
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority

@Table("security_role")
class Role(code: String, idToken: OidcIdToken, userInfo: OidcUserInfo): OidcUserAuthority(code, idToken, userInfo)