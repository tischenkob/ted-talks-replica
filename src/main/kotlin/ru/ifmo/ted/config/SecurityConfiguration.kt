package ru.ifmo.ted.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import ru.ifmo.ted.security.XmlUserDetailsService
import java.io.File
import javax.sql.DataSource

@Configuration
@EnableWebSecurity
class SecurityConfiguration(val dataSource: DataSource) : WebSecurityConfigurerAdapter() {

    private var xmlFile: File = ClassPathResource("security/user-auth.xml").file

    @Bean
    fun passwordEncoder(): PasswordEncoder = NoOpPasswordEncoder.getInstance()

    @Bean
    override fun userDetailsService(): UserDetailsService = XmlUserDetailsService(xmlFile)

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth!!.userDetailsService(userDetailsService())
            .passwordEncoder(passwordEncoder())
    }

    override fun configure(http: HttpSecurity?) {
        http!!.authorizeRequests()
            .antMatchers("/login", "/api/talks", "/logout").permitAll()
            .antMatchers("/api/talks/*/attend").hasAnyRole("USER", "ADMIN")
            .antMatchers("/api/people/**", "/api/talks/*/requests").hasRole("ADMIN")
            .anyRequest().authenticated()
            .and().formLogin()
            .and().httpBasic()
            .and().csrf().disable()
    }

}
