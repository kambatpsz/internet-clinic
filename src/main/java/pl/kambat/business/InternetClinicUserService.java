package pl.kambat.business;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kambat.business.dao.InternetClinicUserDAO;
import pl.kambat.domain.InternetClinicUser;
import pl.kambat.domain.exception.NotDataFoundException;

import java.util.Optional;

@Service
@AllArgsConstructor
public class InternetClinicUserService {

    private final InternetClinicUserDAO internetClinicUserDAO;

    public InternetClinicUser getLoggedUser() {
        String userLoggedLogin = getLoggedInternetClinicUserLogin();
        return getUserByLogin(userLoggedLogin);
    }

    public String getLoggedInternetClinicUserLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userLogin = "";
        if (authentication != null) {
            userLogin = authentication.getName();
        }
        return userLogin;
    }

    @Transactional
    private InternetClinicUser getUserByLogin(String userLogin) {
        Optional<InternetClinicUser> internetClinicUser = internetClinicUserDAO.findByLogin(userLogin);
        return internetClinicUser.orElseThrow(() -> new NotDataFoundException(
                "Could not find user by login:[%s]: ".formatted(userLogin))
        );
    }
}
