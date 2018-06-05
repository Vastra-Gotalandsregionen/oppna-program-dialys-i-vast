package se.vgregion.dialys.i.vast.test;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import se.vgregion.dialys.i.vast.jpa.requisitions.User;
import se.vgregion.dialys.i.vast.service.LdapLoginService;

import javax.security.auth.login.FailedLoginException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class LdapLoginServiceIntegrationTest {

    @Autowired
    private LdapLoginService ldapLoginService;

    @Ignore
    @Test
    public void main() throws FailedLoginException {
        User user = ldapLoginService.findUser("userName");
        System.out.println(user.getName());
    }

}
