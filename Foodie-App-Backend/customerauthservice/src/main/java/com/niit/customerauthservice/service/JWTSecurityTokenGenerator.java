package com.niit.customerauthservice.service;

import com.niit.customerauthservice.domain.Customer;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Service
public class JWTSecurityTokenGenerator implements  SecurityTokenGenerator{
    @Override
    public Map<String, String> generateToken(Customer customer) {
        String token= Jwts.builder().setSubject(customer.getEmailId()).
                setIssuedAt(new Date()).signWith(SignatureAlgorithm.HS256,"Password").compact();
        Map<String,String> map=new HashMap<>();
        map.put("token",token);
        map.put("message","Customer Logged in Success");
        return map;
    }


}
