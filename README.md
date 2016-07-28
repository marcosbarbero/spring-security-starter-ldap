Spring Security LDAP AutoConfiguration
---
Boot starter for LDAP AutoConfiguration

Usage
---
This project is available on maven central and can be used add the following dependency

```
<dependency>
    <groupId>com.marcosbarbero.core</groupId>
    <artifactId>spring-security-starter-ldap</artifactId>
    <version>1.0.0.RELEASE</version>
</dependency>
```

After that configure the following properties on application.yml(properties)
```
# Ldap
ldap:
  userDn: $userDn
  password: $password
  urls:
   - "ldap://$host:$port"
  dnPattern:
   - "uid={0},ou=people"
   - "cn={0},ou=people"
  user-search-base: $userSearchBase
  user-search-filter: $userSearchFilter
  group-search-base: $groupSearchBase
  group-search-filter: $groupSearchFilter
  role-prefix: $rolePrefix
```
