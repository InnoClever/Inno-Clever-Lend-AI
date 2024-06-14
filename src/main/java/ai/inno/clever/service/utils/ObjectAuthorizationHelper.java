package ai.inno.clever.service.utils;


import ai.inno.clever.security.AuthoritiesConstants;
import ai.inno.clever.service.UserService;
import ai.inno.clever.service.dto.AdminUserDTO;
import ai.inno.clever.service.dto.core.AdherentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotAuthorizedException;

@Service
public class ObjectAuthorizationHelper {

    @Autowired
    UserService userService;


    public void checkAccess(AdherentDTO adherent) throws NotAuthorizedException {
        AdminUserDTO loggedInUser =  userService.getLoggedInUser();
        if(loggedInUser.getAuthorities().contains(AuthoritiesConstants.RBRO_USER) || loggedInUser.getAuthorities().contains(
                AuthoritiesConstants.USER)){
            if(!adherent.getOwnerId().equalsIgnoreCase(loggedInUser.getId())){
                throw new NotAuthorizedException("Cannot access this object!");
            }
        }
    }
}
