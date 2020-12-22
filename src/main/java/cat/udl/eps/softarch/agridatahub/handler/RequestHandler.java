package cat.udl.eps.softarch.agridatahub.handler;

import cat.udl.eps.softarch.agridatahub.domain.Provider;
import cat.udl.eps.softarch.agridatahub.domain.Request;
import cat.udl.eps.softarch.agridatahub.domain.Reuser;
import cat.udl.eps.softarch.agridatahub.exception.ForbiddenException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeDelete;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Component
@RepositoryEventHandler
public class RequestHandler {

    final Logger logger = LoggerFactory.getLogger(Request.class);

    @HandleBeforeCreate
    public void handleRequestPreCreate(Request request){

        logger.info("Before creating: {}", request.toString());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Reuser: {}", authentication.getAuthorities());
        Date createdAt = new Date();
        request.setCreationDate(createdAt);
        try {
            request.setRequestedBy((Reuser)authentication.getPrincipal());
        } catch (Exception ex){
            throw new ForbiddenException();
        }
    }

    @HandleBeforeDelete
    public void handleRequestPreDelete(Request request) {

        logger.info("Before delete: {}", request.toString());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Reuser: {}", authentication.getAuthorities());

        Reuser current_reuser = (Reuser) authentication.getPrincipal();

        if (!current_reuser.getId().equals(request.getRequestedBy().getId())){
            throw new ForbiddenException();
        }
    }

    @HandleBeforeSave
    public void handleRequestPreUpdate(Request request) {
        logger.info("Before update: {}", request.toString());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Username: {}", authentication.getAuthorities());

        Reuser curr_provider = ((Reuser)authentication.getPrincipal());

        if (!curr_provider.getId().equals(request.getRequestedBy().getId())){
            throw new ForbiddenException();
        }
    }
}
