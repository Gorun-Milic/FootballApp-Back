package com.gorun.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gorun.demo.configuration.JwtUtil;
import com.gorun.demo.configuration.MyUserDetailsService;
import com.gorun.demo.dto.AuthenticationRequest;
import com.gorun.demo.dto.AuthenticationResponce;

@Controller
public class AuthController {
	
	@Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil tokenUtil;

    @Autowired
    private MyUserDetailsService myUserDetailsService;
	
    
    @CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/authenticate")
	@ResponseBody
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
    	System.out.println("Uasdasdasdasdasdasdasdadasd");
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getName(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
        
        final UserDetails userDetails = myUserDetailsService.loadUserByUsername(authenticationRequest.getName());
        System.out.println("GGGGGGGGGGGGGGGGGEEEEEEEEEEEEEEE");
        final String jwt = tokenUtil.generateToken(userDetails);
        
        return ResponseEntity.ok(new AuthenticationResponce(jwt));
    }

}
