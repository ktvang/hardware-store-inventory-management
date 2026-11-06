package com.hardwarestore.config;
// Imports Spring classes used to configure the application
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Imports Spring Security classes for login and access control
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.web.SecurityFilterChain;

// Tells Spring that this class contains application configuration
@Configuration
public class SecurityConfig {

    // Creates the demo user accounts for our application.
    // These accounts are stored in memory instead of MySQL.
    // This is being used for our class project prototype.
    @Bean
    public UserDetailsService userDetailsService() {

        // Creates an employee account with a username and password.
        // The EMPLOYEE role allows access to employee features.
        var employee = User.withUsername("employee")
                .password("{noop}employee123")
                .roles("EMPLOYEE")
                .build();

        // Creates an administrator account.
        // The ADMIN role gives access to admin features.
        // The EMPLOYEE role also allows the admin to use employee features.
        var admin = User.withUsername("admin")
                .password("{noop}admin123")
                .roles("ADMIN", "EMPLOYEE")
                .build();

        // Returns both accounts so Spring Security can use them for login.
        return new InMemoryUserDetailsManager(employee, admin);
    }

    // Configures which pages users can access and how login works.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            // Controls which users are allowed to access different website pages.
            .authorizeHttpRequests(auth -> auth

                // Allows anyone to access the customer homepage, login page,
                // CSS files, and error page without logging in.
                .requestMatchers("/", "/login", "/css/**", "/error").permitAll()

                // Only administrators can access admin pages.
                .requestMatchers("/admin/**").hasRole("ADMIN")

                // Employees and administrators can access employee pages.
                .requestMatchers("/employee/**").hasAnyRole("EMPLOYEE", "ADMIN")

                // Requires users to log in before accessing any other protected pages.
                .anyRequest().authenticated()
            )

            // Configures the website's login form.
            .formLogin(form -> form

                // Uses our custom login page instead of Spring's default login page.
                .loginPage("/login")

                // Sends users to the employee page after a successful login.
                .defaultSuccessUrl("/employee", true)

                // Allows anyone to access the login page.
                .permitAll()
            )

            // Configures what happens when a user logs out.
            .logout(logout -> logout

                // Sends the user back to the public homepage after logout.
                .logoutSuccessUrl("/")

                // Allows anyone to use the logout function.
                .permitAll()
            );

        // Builds and returns the security configuration.
        return http.build();
    }
}