package se.vgregion.dialys.i.vast.service;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.Test;
import se.vgregion.dialys.i.vast.jpa.requisitions.Mottagning;
import se.vgregion.dialys.i.vast.jpa.requisitions.User;

import java.util.HashSet;
import java.util.Set;

public class JwtUtilTest {

    @Test
    public void main() {
        JwtUtil.secret = "hejsan";
        String token = JwtUtil.createToken(
                "foo",
                "bar",
                true,
                true,
                true,
                null);

        System.out.println(token);

        DecodedJWT auth = JwtUtil.verify(token);
        /*for (String aud : auth.getAudience()) {
            System.out.println(" " + aud);
        }*/
        for (String key : auth.getClaims().keySet()) {
            Claim v = auth.getClaim(key);
            System.out.println(" " + key + " = " + v.asString());
        }
        System.out.println(auth);
    }

    @Test
    public void tokenWithUserObject() {
        JwtUtil.secret = "hejsan";
        User user = new User();
        user.setPassWord("foo");
        user.setUserName("un");
        user.setPasswordEncryptionFlag(true);
        user.setName("name");
        user.setAdmin(true);
        user.setSjukskoterska(true);

        Set<Mottagning> mottagnings = new HashSet<>();
        Mottagning m1 = new Mottagning();
        m1.setId(1);
        m1.setNamn("namn");
        m1.setStatus("Aktiv");
        mottagnings.add(m1);

        user.setMottagnings(mottagnings);
        String token = JwtUtil.createToken(user);

        System.out.println(token);

        DecodedJWT auth = JwtUtil.verify(token);

        for (String key : auth.getClaims().keySet()) {
            Claim v = auth.getClaim(key);
            System.out.println(" " + key + " = " + v.asString());
        }
        System.out.println(auth);
    }

}
