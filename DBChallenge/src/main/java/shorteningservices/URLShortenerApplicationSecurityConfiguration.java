package shorteningservices;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
public class URLShortenerApplicationSecurityConfiguration  extends WebSecurityConfigurerAdapter{
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
		.mvcMatchers("/api/**").permitAll();
//		.mvcMatchers("/h2-console/**").permitAll()// (after other mvcMatchers)
//		.mvcMatchers("/api/customers/**").hasRole("ADMIN")
//		.mvcMatchers("/api/ServicePoint").hasRole("ADMIN")
//		.mvcMatchers("/api/invoices").hasRole("ADMIN") didnt work because login page did not recognize the user input
//		.anyRequest().authenticated().and()   //permit any type of request
//		.formLogin().and()                    // you now get a popup instead of a dedicated page
//		.httpBasic();
//		http.csrf().disable(); // after .httpBasic();
//		http.headers().frameOptions().disable(); // after .httpBasic();
		
		
	    http
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
	    return new BCryptPasswordEncoder();
	}
}
