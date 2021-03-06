package cat.udl.eps.softarch.agridatahub.handler;

import cat.udl.eps.softarch.agridatahub.domain.Provider;
import cat.udl.eps.softarch.agridatahub.exception.ForbiddenException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.core.annotation.HandleBeforeDelete;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler
public class ProviderEventHandler {
    final Logger logger = LoggerFactory.getLogger(Provider.class);

    @HandleBeforeDelete
    public void handleProviderPreDelete(Provider provider) {
        logger.info("Before delete: {}", provider.toString());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Username: {}", authentication.getAuthorities());

        Provider curr_provider = ((Provider)authentication.getPrincipal());

        if (!curr_provider.getId().equals(provider.getId())){
            throw new ForbiddenException();
        }
    }

    @HandleBeforeSave
    public void handleProviderPreUpdate(Provider provider) {
        logger.info("Before delete: {}", provider.toString());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Username: {}", authentication.getAuthorities());

        Provider curr_provider = ((Provider)authentication.getPrincipal());

        if (!curr_provider.getId().equals(provider.getId())){
            throw new ForbiddenException();
        }
    }
}
