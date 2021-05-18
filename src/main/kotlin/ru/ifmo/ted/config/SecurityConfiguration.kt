package ru.ifmo.ted.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import ru.ifmo.ted.security.XmlUserDetailsService
import javax.sql.DataSource

@Configuration
@EnableWebSecurity
class SecurityConfiguration(val dataSource: DataSource) : WebSecurityConfigurerAdapter() {
    private val xmlPath = "/security/user-auth.xml"

    @Bean
    fun passwordEncoder(): PasswordEncoder = NoOpPasswordEncoder.getInstance()

    @Bean
    override fun userDetailsService(): UserDetailsService = XmlUserDetailsService(xmlPath)

    override fun configure(http: HttpSecurity?) {
        http!!.authorizeRequests()
            .antMatchers("/login", "/api/events", "/logout").permitAll()
            .antMatchers("/api/events/**").hasRole("ADMIN")
            .anyRequest().authenticated()
            .and().formLogin()
            .and().httpBasic()
            .and().csrf().disable()
    }

}
