package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.BankController;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Models.Transaksjon;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestBankController {

    @InjectMocks
    private BankController bankController;

    @Mock
    private BankRepository repository;

    @Mock
    private Sikkerhet sjekk;

    @Test
    public void hentKundeInfo_loggetInn() {
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");
        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.hentKundeInfo(anyString())).thenReturn(enKunde);
        Kunde resultat = bankController.hentKundeInfo();
        assertEquals(enKunde, resultat);
    }

    @Test
    public void hentKundeInfo_IkkeloggetInn() {
        when(sjekk.loggetInn()).thenReturn(null);
        Kunde resultat = bankController.hentKundeInfo();
        assertNull(resultat);
    }

    @Test
    public void hentKonti_LoggetInn()  {
        List<Konto> konti = new ArrayList<>();
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);
        Konto konto2 = new Konto("105010123456", "12345678901",
                1000, "Lønnskonto", "NOK", null);
        konti.add(konto1);
        konti.add(konto2);
        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.hentKonti(anyString())).thenReturn(konti);
        List<Konto> resultat = bankController.hentKonti();
        assertEquals(konti, resultat);
    }

    @Test
    public void hentKonti_IkkeLoggetInn()  {
        when(sjekk.loggetInn()).thenReturn(null);
        List<Konto> resultat = bankController.hentKonti();
        assertNull(resultat);
    }

    @Test
    public void hentTransaksjoner_LoggetInn() {
        String kontoNr = "1234567890";
        String fraDato = "2024-01-01";
        String tilDato = "2024-12-31";
        Konto konto = new Konto();
        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.hentTransaksjoner(anyString(), anyString(), anyString())).thenReturn(konto);
        Konto resultat = bankController.hentTransaksjoner(kontoNr, fraDato, tilDato);
        assertEquals(konto, resultat);
    }

    @Test
    public void hentTransaksjoner_IkkeLoggetInn() {
        when(sjekk.loggetInn()).thenReturn(null);
        Konto resultat = bankController.hentTransaksjoner("1234567890", "2024-01-01", "2024-12-31");
        assertNull(resultat);
    }

    @Test
    public void hentSaldi_LoggetInn() {
        List<Konto> konti = new ArrayList<>();
        konti.add(new Konto());
        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.hentSaldi(anyString())).thenReturn(konti);
        List<Konto> resultat = bankController.hentSaldi();
        assertEquals(konti, resultat);
    }

    @Test
    public void hentSaldi_IkkeLoggetInn() {
        when(sjekk.loggetInn()).thenReturn(null);
        List<Konto> resultat = bankController.hentSaldi();
        assertNull(resultat);
    }

    @Test
    public void registrerBetaling_LoggetInn() {
        Transaksjon betaling = new Transaksjon("1234567890", "01010110523", "2024-02-28", 500);
        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.registrerBetaling(any(Transaksjon.class))).thenReturn("OK");
        String resultat = bankController.registrerBetaling(betaling);
        assertEquals("OK", resultat);
    }

    @Test
    public void registrerBetaling_IkkeLoggetInn() {
        when(sjekk.loggetInn()).thenReturn(null);
        String resultat = bankController.registrerBetaling(new Transaksjon("1234567890", "01010110523", "2024-02-28", 500));
        assertNull(resultat);
    }

    @Test
    public void hentBetalinger_LoggetInn() {
        List<Transaksjon> betalinger = new ArrayList<>();
        betalinger.add(new Transaksjon("1234567890", "01010110523", "2024-02-28", 500));
        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.hentBetalinger(anyString())).thenReturn(betalinger);
        List<Transaksjon> resultat = bankController.hentBetalinger();
        assertEquals(betalinger, resultat);
    }

    @Test
    public void hentBetalinger_IkkeLoggetInn() {
        when(sjekk.loggetInn()).thenReturn(null);
        List<Transaksjon> resultat = bankController.hentBetalinger();
        assertNull(resultat);
    }

    @Test
    public void utforBetaling_LoggetInn() {
        int txID = 123;
        List<Transaksjon> betalinger = new ArrayList<>();
        betalinger.add(new Transaksjon("1234567890", "01010110523", "2024-02-28", 500));
        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.utforBetaling(anyInt())).thenReturn("OK");
        when(repository.hentBetalinger(anyString())).thenReturn(betalinger);
        List<Transaksjon> resultat = bankController.utforBetaling(txID);
        assertEquals(betalinger, resultat);
    }

    @Test
    public void utforBetaling_IkkeLoggetInn() {
        when(sjekk.loggetInn()).thenReturn(null);
        List<Transaksjon> resultat = bankController.utforBetaling(123);
        assertNull(resultat);
    }

    @Test
    public void endreKundeInfo_LoggetInn() {
        Kunde kunde = new Kunde("01010110523", "Lene", "Jensen", "Askerveien 22", "3270", "Asker", "22224444", "HeiHei");
        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.endreKundeInfo(any(Kunde.class))).thenReturn("OK");
        String resultat = bankController.endre(kunde);
        assertEquals("OK", resultat);
    }

    @Test
    public void endreKundeInfo_IkkeLoggetInn() {
        when(sjekk.loggetInn()).thenReturn(null);
        String resultat = bankController.endre(new Kunde());
        assertNull(resultat);
    }
}

