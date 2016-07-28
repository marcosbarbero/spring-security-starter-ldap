package com.marcosbarbero.security.ldap.config;

import com.marcosbarbero.security.ldap.config.properties.LdapProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.ldap.authentication.BindAuthenticator;
import org.springframework.security.ldap.authentication.LdapAuthenticationProvider;
import org.springframework.security.ldap.search.FilterBasedLdapUserSearch;
import org.springframework.security.ldap.userdetails.DefaultLdapAuthoritiesPopulator;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;

import javax.naming.ldap.LdapContext;
import java.util.Collections;
import java.util.HashMap;

/**
 * @author Marcos Barbero
 */
@Configuration
@ConditionalOnClass({LdapContext.class})
@EnableConfigurationProperties(LdapProperties.class)
public class LdapAutoConfiguration {

    @Autowired
    private LdapProperties ldapProperties;

    private LdapContextSource contextSource;

    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(this.ldapAuthenticationProvider());
    }

    @Bean
    public LdapAuthenticationProvider ldapAuthenticationProvider() {
        return new LdapAuthenticationProvider(bindAuthenticator(), ldapAuthoritiesPopulator());
    }

    @Bean
    public LdapContextSource contextSource() {
        if (contextSource == null) {
            contextSource = new LdapContextSource();
            contextSource.setUserDn(ldapProperties.getUserDn());
            contextSource.setPassword(ldapProperties.getPassword());
            contextSource.setUrls(ldapProperties.getUrls());
            contextSource.setBaseEnvironmentProperties(
                    Collections.unmodifiableMap(new HashMap<>()));
        }
        return contextSource;
    }

    private LdapAuthoritiesPopulator ldapAuthoritiesPopulator() {
        final DefaultLdapAuthoritiesPopulator authoritiesPopulator = new DefaultLdapAuthoritiesPopulator(
                contextSource(), ldapProperties.getGroupSearchBase());
        authoritiesPopulator.setGroupSearchFilter(ldapProperties.getGroupSearchFilter());
        authoritiesPopulator.setRolePrefix(ldapProperties.getRolePrefix());
        return authoritiesPopulator;
    }

    private BindAuthenticator bindAuthenticator() {
        BindAuthenticator ldapAuthenticator = new BindAuthenticator(contextSource());
        ldapAuthenticator.setUserSearch(filterBasedLdapUserSearch());
        ldapAuthenticator.setUserDnPatterns(ldapProperties.getDnPattern());
        return ldapAuthenticator;
    }

    private FilterBasedLdapUserSearch filterBasedLdapUserSearch() {
        return new FilterBasedLdapUserSearch(ldapProperties.getUserSearchBase(),
                ldapProperties.getUserSearchFilter(), contextSource());
    }
}
