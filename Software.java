public class Software {
    private String id;
    private String nom;
    private int preu;
    
    public Software(String id, String nom, int preu) {
        this.id = id;
        this.nom = nom;
        this.preu = preu;
    }
    
    // Getters y setters
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getNom() {
        return nom;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public int getPreu() {
        return preu;
    }
    
    public void setPreu(int preu) {
        this.preu = preu;
    }
}
