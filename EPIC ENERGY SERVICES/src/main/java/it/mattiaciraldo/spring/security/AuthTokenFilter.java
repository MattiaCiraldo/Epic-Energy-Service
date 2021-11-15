package it.mattiaciraldo.spring.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import it.mattiaciraldo.spring.service.UserDetailsServiceImpl;


/* Implementa il filtro OncePerRequestFilter
Normalmente si usa una Servlet per controllare, pre-elaborare e/o postelaborare richieste specifiche. 
Ma se occorre filtrare/modificare richieste comuni e/o risposte in base a condizioni specifiche, allora un Filtro è molto di più adatto.
I filtri utilizzano FilterChain per richiamare il filtro successivo nella catena o la risorsa alla fine della catena.
Un filtro consente di decidere se un codice specifico debba essere eseguito appena prima o dopo l'esecuzione del servlet:
code1   ===>   servlet execution (using chain.doFilter())   ===>    code2
Durante l'esecuzione del servlet, però, può esserci qualche altra richiesta a un altro servlet che ha lo stesso filtro. 
In questo caso, il filtro verrà eseguito di nuovo. 
Ogni volta che effettuiamo una richiesta internamente a qualche altra API nel progetto, la stessa autenticazione si ripeterebbe
poiché tutte le API hanno lo stesso filtro di sicurezza. OncePerRequestFilter impedisce questo comportamento.

Viene invocata dal framework ogni volta che il client effettua una request.
Ha il compito di estrarre eventuali dati relativi al token JWT ed elaborarli per permettere il processo di autenticazione
*/

public class AuthTokenFilter extends OncePerRequestFilter {
	@Autowired
	private JwtUtils jwtUtils;
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String jwt = parseJwt(request);
			if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
				String username = jwtUtils.getUserNameFromJwtToken(jwt);
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		} catch (Exception e) {
			logger.error("Cannot set user authentication: {}", e);
		}
		filterChain.doFilter(request, response);
	}

	private String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization");

		if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
			return headerAuth.substring(7, headerAuth.length());
		}
		return null;
	}
}
