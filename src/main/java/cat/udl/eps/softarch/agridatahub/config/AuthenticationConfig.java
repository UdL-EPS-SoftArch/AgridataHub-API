package cat.udl.eps.softarch.agridatahub.config;

import cat.udl.eps.softarch.agridatahub.domain.*;
import cat.udl.eps.softarch.agridatahub.repository.ProviderRepository;
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
  final ProviderRepository providerRepository;

  public AuthenticationConfig(BasicUserDetailsService basicUserDetailsService, UserRepository userRepository, ReuserRepository reuserRepository, ProviderRepository providerRepository) {

    this.basicUserDetailsService = basicUserDetailsService;
    this.userRepository = userRepository;
    this.reuserRepository = reuserRepository;
    this.providerRepository = providerRepository;
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

    if(!providerRepository.existsById("reuserDemo")){
      Provider provider = new Provider();
      provider.setEmail("providerDemo@agridata.hub");
      provider.setUsername("providerDemo");
      provider.setPassword(defaultPassword);
      provider.encodePassword();
      providerRepository.save(provider);
    }

  }
}
