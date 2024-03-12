package pl.kambat.business;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.kambat.business.dao.InternetClinicUserDAO;
import pl.kambat.domain.InternetClinicUser;
import pl.kambat.domain.exception.NotDataFoundException;
import pl.kambat.util.DataFixtures;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InternetClinicUserServiceTest {

    @Mock
    private InternetClinicUserDAO internetClinicUserDAO;

    @InjectMocks
    private InternetClinicUserService internetClinicUserService;

    @Test
    void getLoggedUser_UserExists_ReturnsUser() {
        // given
        String userLogin = "mirek";
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn(userLogin);

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);

        InternetClinicUser mockUser = DataFixtures.someInternetClinicUser1();
        when(internetClinicUserDAO.findByLogin(userLogin)).thenReturn(Optional.of(mockUser));

        // when
        InternetClinicUser result = internetClinicUserService.getLoggedUser();

        // then
        assertEquals(mockUser, result);
    }

    @Test
    void getLoggedUser_UserDoesNotExist_ThrowsException() {
        // given
        String userLogin = "nonExistentUser";
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn(userLogin);

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);

        when(internetClinicUserDAO.findByLogin(userLogin)).thenReturn(Optional.empty());

        // when then
        NotDataFoundException exception = assertThrows(
                NotDataFoundException.class,
                () -> internetClinicUserService.getLoggedUser()
        );

        assertEquals("Could not find user by login:[%s]: ".formatted(userLogin), exception.getMessage());
    }
}