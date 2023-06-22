package com.bookrentalsystem.bks.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
//spring jpa audit annotation
//this annotation enables the auditing in jpa -  annotation configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JpaAuditingConfiguration {

    //helps to aware the application's current auditor.
    //this is some kind of user mostly.
    @Bean
    public AuditorAware<String> auditorProvider(){

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

//        Set<String> roles = authentication.getAuthorities().stream()
//                .map(r -> r.getAuthority()).collect(Collectors.toSet());
//        Optional<String> roles = authentication.getAuthorities().stream()
//                .map(r -> r.getAuthority()).findFirst();

        return new AuditingConfigUserDetail();
    }
}
