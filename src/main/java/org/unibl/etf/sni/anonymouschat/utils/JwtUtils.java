package org.unibl.etf.sni.anonymouschat.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.unibl.etf.sni.anonymouschat.models.dtos.JwtUser;
import org.unibl.etf.sni.anonymouschat.models.enums.Role;

import java.security.Key;
import java.util.Date;

public final class JwtUtils {

    private static final long JwtExpirationTime = 900_000; // 15 minutes
    //private static final long JwtRefreshExpirationTime = 3_600_000; // 1 hour
    private static final String AuthorizationSecret = "413F4428472B4B6250655368566D597133743677397A244326452948404D635166546A576E5A7234753778214125442A472D4A614E645267556B587032733576";

    private static JwtUtils instance = null;

    public static JwtUtils getInstance(){
        if(instance == null)
            instance = new JwtUtils();
        return instance;
    }

    public String generateJwt(JwtUser user){
        return Jwts.builder()
                .setId(user.getId().toString())
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .claim("role", user.getRole().name())
                .setExpiration(new Date(System.currentTimeMillis() + JwtExpirationTime))
                .signWith(getSignInKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public Claims parseJwt(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public JwtUser extractUserDetailsFrom(String token){
        Claims claims = parseJwt(token);
        return new JwtUser(Integer.valueOf(claims.getId()), claims.getSubject(),null, Role.valueOf(claims.get("role", String.class)));
    }

    public Date extractExpirationTime(String token){
        return parseJwt(token).getExpiration();
    }

    public boolean isTokenExpired(String token){
        return extractExpirationTime(token).before(new Date());
    }

    public boolean isTokenValid(String fullToken, Integer id){
        String token = fullToken.substring(7);
        final JwtUser jwtUser = extractUserDetailsFrom(token);
        return jwtUser.getId().equals(id) && !isTokenExpired(token);
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(AuthorizationSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
