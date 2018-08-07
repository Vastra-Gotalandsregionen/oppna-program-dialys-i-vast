package se.vgregion.dialys.i.vast.service;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.Test;
import se.vgregion.dialys.i.vast.jpa.requisitions.Mottagning;
import se.vgregion.dialys.i.vast.jpa.requisitions.User;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class JwtUtilTest {

    @Test
    public void main() {
        JwtUtil.secret = "hejsan";

        User user = new User();
        user.setName("name");
        user.setUserName("userName");
        user.setMottagnings(new HashSet<>(Arrays.asList(new Mottagning(1,"Hiertat"))));
        user.setName("John Doe");
        user.setAdmin(true);
        user.setPharmaceut(true);
        user.setSjukskoterska(true);

        String token = JwtUtil.createToken(user);

        System.out.println(token);

        DecodedJWT auth = JwtUtil.verify(token);

        for (String key : auth.getClaims().keySet()) {
            Claim v = auth.getClaim(key);
            System.out.println(" " + key + " = " + v.asString());
        }
        System.out.println(auth.getSubject());
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
        user.setPharmaceut(false);

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
        System.out.println(auth.getSubject());
    }

    @Test
    public void foo() {

    }

}
