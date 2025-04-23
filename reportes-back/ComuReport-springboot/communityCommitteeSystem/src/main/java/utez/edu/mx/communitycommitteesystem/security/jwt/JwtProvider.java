package utez.edu.mx.communitycommitteesystem.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import utez.edu.mx.communitycommitteesystem.security.model.UserDetailsImpl;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class JwtProvider {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private long expiration;
    private final String TOKEN_HEADER = "Authorization";
    private final String TOKEN_TYPE = "Bearer ";
    private static final Logger logger = LogManager.getLogger(JwtProvider.class);

    public String generateToken(Authentication auth) {
        UserDetails user = (UserDetailsImpl) auth.getPrincipal();
        logger.info("Roles " + user.getAuthorities());
        Claims claims = Jwts.claims().setSubject(user.getUsername());
        claims.put("roles", user.getAuthorities());
        claims.put("uuid", ((UserDetailsImpl) user).getUuid());


        Date tokenCreateTime = new Date();
        Date tokenValidity = new Date(
                tokenCreateTime.getTime() + expiration * 1000
        );
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(tokenValidity)
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims parseJwtClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Claims resolveClaims(HttpServletRequest req) {
        try {
            String token = resolveToken(req);
            if (token != null)
                return parseJwtClaims(token);
            return null;
        } catch (ExpiredJwtException e) {
            logger.error(e);
            throw e;
        } catch (Exception e) {
            logger.error(e);
            throw e;
        }
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader(TOKEN_HEADER);
        if (bearerToken != null && bearerToken.startsWith(TOKEN_TYPE))
            return bearerToken.replace(TOKEN_TYPE, "");
        return null;
    }

    public boolean validateClaims(Claims claims, String token){
        try{
            parseJwtClaims(token);
            return claims.getExpiration().after(new Date());
        }catch (MalformedJwtException | UnsupportedJwtException | ExpiredJwtException e){
            logger.error(e);
        } catch (Exception e){
            logger.error(e);
        }
        return false;
    }

    public String resolveClaimsJUuid(HttpServletRequest req) {
        try {
            Claims claims =  resolveClaims(req);
            if (claims != null)
                return (String) claims.get("uuid");
            return null;
        } catch (ExpiredJwtException e) {
            logger.error(e);
            throw e;
        } catch (Exception e) {
            logger.error(e);
            throw e;
        }
    }
    public String resolveClaimsJRole(HttpServletRequest req) {
        try {
            Claims claims =  resolveClaims(req);
            if (claims != null) {
                List<Map<String, String>> authorityList = (List<Map<String, String>>) claims.get("roles");
                return authorityList.get(0).get("authority");
            }
            return null;
        } catch (Exception e) {
            logger.error(e);
            throw e;
        }
    }

}
