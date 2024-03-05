package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpSession;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Sikkerhet.Sikkerhet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestSikkerhet {

    @InjectMocks
    private Sikkerhet sikkerhetsController;

    @Mock
    private BankRepository repository;

    @Mock
    private MockHttpSession session;

    @Test
    public void test_sjekkLoggInn() {
        lenient().when(repository.sjekkLoggInn(anyString(), anyString())).thenReturn("OK");
        String resultat = sikkerhetsController.sjekkLoggInn("12345678901", "HeiHeiHei");
        assertEquals("OK", resultat);
    }

    @Test
    public void test_loggInnAdmin_LoggetInnSomAdmin() {
        lenient().when(session.getAttribute("Innlogget")).thenReturn("Admin");
        String resultat = sikkerhetsController.loggInnAdmin("Admin", "Admin");
        assertEquals("Logget inn", resultat);
    }

    @Test
    public void test_loggInnAdmin_FeilBruker() {
        String resultat = sikkerhetsController.loggInnAdmin("FeilBruker", "Admin");
        assertEquals("Ikke logget inn", resultat);
    }

    @Test
    public void test_loggInnAdmin_FeilPassord() {
        String resultat = sikkerhetsController.loggInnAdmin("Admin", "FeilPassord");
        assertEquals("Ikke logget inn", resultat);
    }

    @Test
    public void test_loggUt() {
        sikkerhetsController.loggUt();
        assertEquals(null, session.getAttribute("Innlogget"));
    }

    @Test
    public void test_loggetInn_LoggetInn() {
        lenient().when(session.getAttribute("Innlogget")).thenReturn("12345678901");
        String resultat = sikkerhetsController.loggetInn();
        assertEquals("12345678901", resultat);
    }

    @Test
    public void test_loggetInn_IkkeLoggetInn() {
        String resultat = sikkerhetsController.loggetInn();
        assertNull(resultat);
    }
}

