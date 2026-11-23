package model;

import java.io.*;
import java.util.List; 
import java.util.ArrayList;

public class GestoreProfilo {
	private static final String FILE = "profili.dat";

    
    public static void salvaProfilo(ProfiloUtente profilo) {

        List<ProfiloUtente> profili = caricaTutti();

        // rimuove se già esiste stesso nickname
        profili.removeIf(p -> p.getNickname().equals(profilo.getNickname()));

        profili.add(profilo);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE))) {
            oos.writeObject(profili);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<ProfiloUtente> caricaTutti() {

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE))) {
            return (List<ProfiloUtente>) ois.readObject();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
