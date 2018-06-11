package se.vgregion.dialys.i.vast.test;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import se.vgregion.dialys.i.vast.jpa.requisitions.User;
import se.vgregion.dialys.i.vast.repository.UserRepository;
import se.vgregion.dialys.i.vast.service.LdapLoginService;

import javax.security.auth.login.FailedLoginException;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class LdapLoginServiceIntegrationTest {

    @Autowired
    private LdapLoginService ldapLoginService;

    @Autowired
    private UserRepository userRepository;

    @Ignore
    @Test
    public void main() throws FailedLoginException {
        User user = ldapLoginService.findUser("userName");
        System.out.println(user);
    }

    @Test
    public void findDatabaseUsersInLdap() throws FailedLoginException {
        List<User> users = userRepository.findAll();
        List<String> result = new ArrayList<>();
        for (User user : users) {
            if (user.getUserName() != null && ldapLoginService.findUser(user.getUserName()) != null) {
                result.add(user.getUserName());
            }
        }
        System.out.println(result);
    }



}
