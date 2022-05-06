package ru.somarov.berte.conf.config

import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.proc.SecurityContext
import com.nimbusds.jose.proc.SingleKeyJWSKeySelector
import com.nimbusds.jwt.proc.DefaultJWTProcessor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.security.authentication.*
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator
import org.springframework.security.oauth2.core.OAuth2TokenValidator
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter
import org.springframework.security.oauth2.server.resource.web.server.ServerBearerTokenAuthenticationConverter
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.util.pattern.PathPatternParser
import reactor.core.publisher.Mono
import ru.somarov.berte.conf.properties.AppProps
import ru.somarov.berte.core.security.DefaultUserDetailsService
import ru.somarov.berte.persistence.PersistenceFacade
import java.security.SecureRandom
import java.util.*

/**
 * Configuration of Spring security
 *
 * @version 1.0.0
 * @since 1.0.0
 * @author alexandr.omarov
 *
 */

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
class SecurityConfig(val props: AppProps) {

    @Bean
    fun securityWebFilterChain(http: ServerHttpSecurity, authenticationManagerResolver: ReactiveAuthenticationManagerResolver<ServerWebExchange>): SecurityWebFilterChain {
        return http.csrf().disable() // CSRF
            .cors().configurationSource( // CORS
                UrlBasedCorsConfigurationSource(PathPatternParser()).also { source ->
                    source.setCorsConfigurations(
                    Collections.singletonMap("/**", CorsConfiguration().also {
                        val cors = props.security.cors
                        it.allowedOrigins = cors.origins; it.allowedMethods = cors.methods
                        it.allowedHeaders = cors.headers; it.exposedHeaders = cors.exposedHeaders
                        it.allowCredentials = cors.allowCreds; it.maxAge = cors.age })) })
            .and() // Authn
            .httpBasic().disable()
            .formLogin().disable()
            .oauth2ResourceServer().authenticationManagerResolver(authenticationManagerResolver).bearerTokenConverter(authenticationConverter()).and()
            .logout()
            .and() // Authz
            .authorizeExchange()
            .pathMatchers(*props.security.open.map { it }.toTypedArray()).permitAll()
            .pathMatchers("/**").authenticated()
            .and().build()
    }

    // Only provider used in filters
    @Bean
    fun jwtProvider(authoritiesConverter: Converter<Jwt, Collection<GrantedAuthority>>, validators: List<OAuth2TokenValidator<Jwt>>): JwtAuthenticationProvider {
        val processor = DefaultJWTProcessor<SecurityContext>()
        processor.jwsKeySelector = SingleKeyJWSKeySelector(JWSAlgorithm.RS512, props.security.jwt.keys.public)
        val decoder = NimbusJwtDecoder(processor)
        val provider = JwtAuthenticationProvider(decoder)
        val converter = JwtAuthenticationConverter()
        decoder.setJwtValidator(DelegatingOAuth2TokenValidator(validators))
        converter.setJwtGrantedAuthoritiesConverter(authoritiesConverter)
        provider.setJwtAuthenticationConverter(converter)
        return provider
    }

    // Provider used in login endpoint
    @Bean
    fun daoProvider(persistenceFacade: PersistenceFacade): DaoAuthenticationProvider {
        return DaoAuthenticationProvider().also { it.setUserDetailsService(DefaultUserDetailsService(persistenceFacade)) }
    }

    // Auth managers, Password encoders, auth converters for filter
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder(11, SecureRandom())
    }

    @Bean
    fun reactiveAuthenticationManagerAdapter(authenticationManager: AuthenticationManager): ReactiveAuthenticationManager {
        return ReactiveAuthenticationManagerAdapter(authenticationManager)
    }

    @Bean
    fun resolver(authenticationManager: ReactiveAuthenticationManager): ReactiveAuthenticationManagerResolver<ServerWebExchange> {
        return ReactiveAuthenticationManagerResolver { Mono.just(authenticationManager) }
    }

    @Bean
    fun authenticationManager(providers: List<AuthenticationProvider>): AuthenticationManager {
        return ProviderManager(providers)
    }

    @Bean
    fun authenticationConverter(): ServerAuthenticationConverter {
        return ServerBearerTokenAuthenticationConverter()
    }

    @Bean
    fun authoritiesConverter(): JwtGrantedAuthoritiesConverter {
        return JwtGrantedAuthoritiesConverter().also { it.setAuthorityPrefix("ROLE_") }
    }

}