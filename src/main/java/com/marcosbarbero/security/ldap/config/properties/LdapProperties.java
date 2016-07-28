package com.marcosbarbero.security.ldap.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author marcos.barbero
 */
@Data
@ConfigurationProperties(prefix = "ldap")
public class LdapProperties {

    private String userDn;
    private String password;
    private String[] urls;
    private String[] dnPattern;
    private String userSearchBase;
    private String userSearchFilter;
    private String groupSearchBase;
    private String groupSearchFilter;
    private String rolePrefix;

}