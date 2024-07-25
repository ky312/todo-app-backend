package com.kylearning.todoapp.resources;

import com.kylearning.todoapp.entities.JwtResponse;
import com.kylearning.todoapp.entities.Login;
import com.kylearning.todoapp.entities.User;
import com.kylearning.todoapp.services.JwtService;
import com.kylearning.todoapp.services.UserService;
import com.kylearning.todoapp.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody Login loginUser) throws Exception {
        authenticate(loginUser.getUsername(), loginUser.getPassword());

        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginUser.getUsername());

        final String token = jwtUtil.generateToken(userDetails);

        return  new ResponseEntity<>(new JwtResponse(token), HttpStatus.OK);
    }

    private void authenticate(String username, String password) throws Exception {
        try{
            authManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (BadCredentialsException ex){
            throw new Exception("Bad Credentials!");
        } catch (DisabledException ex) {
            throw new Exception("User Disabled!");
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<JwtResponse> signup(@RequestBody User singupUser) {
        String token=null;
        User user = userService.createUser(singupUser);
        if(user != null) {
            final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
            token = jwtUtil.generateToken(userDetails);
        }
        return  new ResponseEntity<>(new JwtResponse(token), HttpStatus.OK);
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null){
            new SecurityContextLogoutHandler().logout(httpServletRequest, httpServletResponse, auth);
        }
        return new ResponseEntity<>("Successfully logout!", HttpStatus.OK);
    }
}
