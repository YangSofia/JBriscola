package model;
import java.io.Serializable;

public class ProfiloUtente implements Serializable{
	private static final long serialVersionUID = 1L;
	private String nickname;
    private String avatarPath;

    private int partiteGiocate;
    private int vinte;
    private int perse;
    
    public ProfiloUtente(String nickname) {
    	this.nickname = nickname;
    }
 // GETTER
    public String getNickname() { return nickname; }
    public String getAvatarPath() { return avatarPath; }
    public int getPartiteGiocate() { return partiteGiocate; }
    public int getVinte() { return vinte; }
    public int getPerse() { return perse; }

    // SETTER
    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    // LOGICA
    public void registraVittoria() {
        partiteGiocate++;
        vinte++;
    }

    public void registraSconfitta() {
        partiteGiocate++;
        perse++;
    }
    
}
