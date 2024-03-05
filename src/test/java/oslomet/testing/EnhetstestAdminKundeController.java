package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.AdminKundeController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestAdminKundeController {

    @InjectMocks
    private AdminKundeController adminKundeController;

    @Mock
    private AdminRepository repository;

    @Mock
    private Sikkerhet sjekk;

    @Test
    public void hentAlle_IkkeLoggetInn() {
        when(sjekk.loggetInn()).thenReturn(null);
        List<Kunde> resultat = adminKundeController.hentAlle();
        assertNull(resultat);
    }

    @Test
    public void hentAlle_LoggetInn() {
        List<Kunde> kunder = new ArrayList<>();
        kunder.add(new Kunde());
        kunder.add(new Kunde());
        when(sjekk.loggetInn()).thenReturn("123456789");
        when(repository.hentAlleKunder()).thenReturn(kunder);
        List<Kunde> resultat = adminKundeController.hentAlle();
        assertEquals(kunder, resultat);
    }

    @Test
    public void lagreKunde_IkkeLoggetInn() {
        when(sjekk.loggetInn()).thenReturn(null);
        String resultat = adminKundeController.lagreKunde(new Kunde());
        assertEquals("Ikke logget inn", resultat);
    }

    @Test
    public void lagreKunde_LoggetInn() {
        when(sjekk.loggetInn()).thenReturn("123456789");
        when(repository.registrerKunde(any(Kunde.class))).thenReturn("Success");
        String resultat = adminKundeController.lagreKunde(new Kunde());
        assertEquals("Success", resultat);
    }

    @Test
    public void endreKunde_IkkeLoggetInn() {
        when(sjekk.loggetInn()).thenReturn(null);
        String resultat = adminKundeController.endre(new Kunde());
        assertEquals("Ikke logget inn", resultat);
    }

    @Test
    public void endreKunde_LoggetInn() {
        when(sjekk.loggetInn()).thenReturn("123456789");
        when(repository.endreKundeInfo(any(Kunde.class))).thenReturn("Success");
        String resultat = adminKundeController.endre(new Kunde());
        assertEquals("Success", resultat);
    }

    @Test
    public void slettKunde_IkkeLoggetInn() {
        when(sjekk.loggetInn()).thenReturn(null);
        String resultat = adminKundeController.slett("123456");
        assertEquals("Ikke logget inn", resultat);
    }

    @Test
    public void slettKunde_LoggetInn() {
        when(sjekk.loggetInn()).thenReturn("123456789");
        when(repository.slettKunde(anyString())).thenReturn("Success");
        String resultat = adminKundeController.slett("123456");
        assertEquals("Success", resultat);
    }
}
