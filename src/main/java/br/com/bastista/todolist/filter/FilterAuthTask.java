package br.com.bastista.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.bastista.todolist.user.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterAuthTask extends OncePerRequestFilter {

    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        var path = request.getServletPath();
        if (path.equals("/tasks/")) {
            var tempAut = request.getHeader("Authorization");
            var userPassword = tempAut.substring("Basic".length()).trim();

            byte[] autDecode = Base64.getDecoder().decode(userPassword);
            var authString = new String(autDecode);
            var credentials = authString.split(":");
            var username = credentials[0];
            var password = credentials[1];

            var user = this.userRepository.findByUsername(username);
            if (user == null) {
                response.sendError(401);
            } else {

                var passVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());

                if (passVerify.verified) {

                    request.setAttribute("idUser", user.getId());
                    filterChain.doFilter(request, response);
                } else {
                    response.sendError(401);
                }
            }
        } else {
            filterChain.doFilter(request, response);
        }

    }

}