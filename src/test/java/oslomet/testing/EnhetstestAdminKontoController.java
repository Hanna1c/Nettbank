package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.AdminKontoController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestAdminKontoController {
    @InjectMocks
    private AdminKontoController adminKontoController;

    @Mock
    private AdminRepository repository;

    @Mock
    private Sikkerhet sjekk;

    @Test
    public void hentAlleKonti_IkkeLoggetInn() {
        when(sjekk.loggetInn()).thenReturn(null);
        List<Konto> resultat = adminKontoController.hentAlleKonti();
        assertNull(resultat);
    }

    @Test
    public void hentAlleKonti_LoggetInn() {
        List<Konto> konti = new ArrayList<>();
        konti.add(new Konto());
        konti.add(new Konto());
        when(sjekk.loggetInn()).thenReturn("123456789");
        when(repository.hentAlleKonti()).thenReturn(konti);
        List<Konto> resultat = adminKontoController.hentAlleKonti();
        assertEquals(konti, resultat);
    }

    @Test
    public void registrerKonto_IkkeLoggetInn() {
        when(sjekk.loggetInn()).thenReturn(null);
        String resultat = adminKontoController.registrerKonto(new Konto());
        assertEquals("Ikke innlogget", resultat);
    }

    @Test
    public void registrerKonto_LoggetInn() {
        when(sjekk.loggetInn()).thenReturn("123456789");
        when(repository.registrerKonto(any(Konto.class))).thenReturn("Success");
        String resultat = adminKontoController.registrerKonto(new Konto());
        assertEquals("Success", resultat);
    }

    @Test
    public void endreKonto_IkkeLoggetInn() {
        when(sjekk.loggetInn()).thenReturn(null);
        String resultat = adminKontoController.endreKonto(new Konto());
        assertEquals("Ikke innlogget", resultat);
    }

    @Test
    public void endreKonto_LoggetInn() {
        when(sjekk.loggetInn()).thenReturn("123456789");
        when(repository.endreKonto(any(Konto.class))).thenReturn("Success");
        String resultat = adminKontoController.endreKonto(new Konto());
        assertEquals("Success", resultat);
    }

    @Test
    public void slettKonto_IkkeLoggetInn() {
        when(sjekk.loggetInn()).thenReturn(null);
        String resultat = adminKontoController.slettKonto("123456");
        assertEquals("Ikke innlogget", resultat);
    }

    @Test
    public void slettKonto_LoggetInn() {
        when(sjekk.loggetInn()).thenReturn("123456789");
        when(repository.slettKonto(anyString())).thenReturn("Success");
        String resultat = adminKontoController.slettKonto("123456");

        assertEquals("Success", resultat);
    }
}
