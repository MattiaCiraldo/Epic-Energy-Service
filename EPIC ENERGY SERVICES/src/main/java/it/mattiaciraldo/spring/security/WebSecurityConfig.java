package it.mattiaciraldo.spring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import it.mattiaciraldo.spring.service.UserDetailsServiceImpl;


/* WebSecurityConfig
Grazie alle annotazioni @Configuration e @EnableWebSecurity, abilita le funzioni di sicurezza del framework e ne configura i parametri di funzionamento.
• Istruisce il sistema sul service da impiegare per manipolare i dati dello user
• Registra il filter che ha il compito di estrarre e processare il token JWT dalle request
• Definisce il sistema di encoding delle password
In questo esempio per semplicità si utilizza un encoder base (NoOpPasswordEncoder) che gestisce le password in chiaro
*/

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	UserDetailsServiceImpl userDetailsService;
	@Autowired
	private AuthEntryPointUnauthorizedJwt unauthorizedHandler;

	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}
	
	/* Tenta di ottenere un AuthenticationManager 
	In caso di override, è necessario utilizzare AuthenticationManagerBuilder per specificare l'AuthenticationManager
	*/
	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(this.passwordEncoder());
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		// Questa implementazione non è sicura, verrà modificata in seguito
		return new BCryptPasswordEncoder();
	}

	
	/* Per abilitare la sicurezza HTTP in Spring, è necessario estendere WebSecurityConfigurerAdapter 
	fornendo una configurazione predefinita
	*/
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable()
			.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.authorizeRequests().antMatchers("/**").permitAll()
			.anyRequest().authenticated();
		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
	}
}
