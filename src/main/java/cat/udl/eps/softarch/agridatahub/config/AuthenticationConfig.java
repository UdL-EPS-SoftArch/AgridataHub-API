package cat.udl.eps.softarch.agridatahub.config;

import cat.udl.eps.softarch.agridatahub.domain.*;
import cat.udl.eps.softarch.agridatahub.repository.ReuserRepository;
import cat.udl.eps.softarch.agridatahub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;

@Configuration
public class AuthenticationConfig extends GlobalAuthenticationConfigurerAdapter {

  @Value("${default-password}")
  String defaultPassword;

  final BasicUserDetailsService basicUserDetailsService;
  final UserRepository userRepository;
  final ReuserRepository reuserRepository;

  public AuthenticationConfig(BasicUserDetailsService basicUserDetailsService, UserRepository userRepository, ReuserRepository reuserRepository) {

    this.basicUserDetailsService = basicUserDetailsService;
    this.userRepository = userRepository;
    this.reuserRepository = reuserRepository;
  }

  @Override
  public void init(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(basicUserDetailsService)
            .passwordEncoder(User.passwordEncoder);

    // Sample User
    if (!userRepository.existsById("demo")) {
      User user = new User();
      user.setEmail("demo@agridata.hub");
      user.setUsername("demo");
      user.setPassword(defaultPassword);
      user.encodePassword();
      userRepository.save(user);
    }

    if(!reuserRepository.existsById("reuserDemo")){
      Reuser reuser = new Reuser();
      reuser.setEmail("reuserDemo@agridata.hub");
      reuser.setUsername("reuserDemo");
      reuser.setPassword(defaultPassword);
      reuser.encodePassword();
      reuserRepository.save(reuser);
    }
    if(!reuserRepository.existsById("reuser2")){
      Reuser reuser = new Reuser();
      reuser.setEmail("reuser2@agridata.hub");
      reuser.setUsername("reuser2");
      reuser.setPassword(defaultPassword);
      reuser.encodePassword();
      reuserRepository.save(reuser);
    }
  }
}
