package sm.dswTaller.ms.tallerAutomotriz.utils;

import sm.dswTaller.ms.tallerAutomotriz.model.Usuario;
import io.jsonwebtoken.Claims;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

//Generar, firmar, validar y extraer información del JWT (JSON Web Token)

@Service
public class JwtUtil {
    @Value("5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437")
    private String secretKey;
    
    @Value("3600000") // igual a 1 hora
    private long jwtExpiration;
    
    public String generateToken(Usuario usuario) {
        Map<String, Object> claims = new HashMap<>();
            claims.put("personaId", usuario.getPersona().getIdPersona());
            claims.put("email", usuario.getNombreUsuario());
            claims.put("names", usuario.getPersona().getNombres());
            claims.put("rol", usuario.getRol().getRol());


        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
            .signWith(getSigningKey(), SignatureAlgorithm.HS256)
            .compact();
    }
    
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    
    public boolean isTokenValid(String token, Usuario usuario) {
        final String username = extractUsername(token);
        return (username.equals(usuario.getNombreUsuario())) && !isTokenExpired(token);
    }
    
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    
    private Claims extractAllClaims(String token) {
        return Jwts.parser()            .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
    }
    
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}