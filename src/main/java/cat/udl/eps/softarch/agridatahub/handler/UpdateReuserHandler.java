package cat.udl.eps.softarch.agridatahub.handler;

import cat.udl.eps.softarch.agridatahub.domain.Reuser;
import cat.udl.eps.softarch.agridatahub.exception.ForbiddenException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler
public class UpdateReuserHandler {
    private final Logger logger = LoggerFactory.getLogger(Reuser.class);

    @HandleBeforeSave
    public void handleReuserPreUpdate(Reuser reuser) {
        logger.info("Before delete: {}", reuser.toString());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Username: {}", authentication.getAuthorities());

        Reuser curr_reuser = ((Reuser)authentication.getPrincipal());

        if (!curr_reuser.getId().equals(reuser.getId())){
            throw new ForbiddenException();
        }
    }
}
