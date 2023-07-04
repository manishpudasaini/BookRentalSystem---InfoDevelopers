package com.bookrentalsystem.bks.service.jwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    //this is the secret key 256bit key which is used to validate the user if the user is who he claim is or not
    private final String SECRETE_KEY= "4226452948404D635166546A576E5A7234753777217A25432A462D4A614E6452";


    //generate token from username - by passing username token is created
    public String generateToken(String username){
        Map<String,Object> claims = new HashMap<>();
        return createToken(claims,username);
    }

    //this is used to createToken
    private String createToken(Map<String, Object> claims, String username) {
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(getSignedKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    //this is used to decode the secrete key
    private Key getSignedKey() {
        byte[] keyByte = Decoders.BASE64.decode(SECRETE_KEY);
        return Keys.hmacShaKeyFor(keyByte);
    }

    //method to extract username
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    //this method is used to check if the token is expired or not
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }


    //this method is used to extract expiration date of token
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);

    }

    //from all claims extract single claims
    public <T> T extractClaim(String token, Function<Claims,T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    //extract all claims
    private Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignedKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    //method to validate token
    public boolean isTokenValidate(String token, UserDetails userDetails){
        final String userName = extractUsername(token);
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }
}
