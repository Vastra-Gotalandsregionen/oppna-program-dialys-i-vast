package se.vgregion.dialys.i.vast.test;

import org.mockito.Mockito;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.servlet.http.HttpServletRequest;

@Configuration
@Import(JpaConfigTest.class)
@EnableJpaRepositories(value = {"se.vgregion.dialys.i.vast.repository"})
@ComponentScan("se.vgregion.dialys.i.vast.controller.domain")
@ComponentScan(basePackageClasses = se.vgregion.dialys.i.vast.service.JwtUtil.class)
@PropertySource("classpath:application-test.properties")
public class AppConfigTest {

    @Bean
    public HttpServletRequest httpServletRequest() {
        return Mockito.mock(HttpServletRequest.class);
    }

}
