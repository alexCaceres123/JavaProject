public class Client {
    private String id;
    private String nom;
    private String cognom;
    private String contrasenya;
    
    public Client(String id, String nom, String cognom, String contrasenya) {
        this.id = id;
        this.nom = nom;
        this.cognom = cognom;
        this.contrasenya = contrasenya;
    }
    
    // Getters y setters
    
    public String getId() {
        return this.id;
    }

    public String getCognom() {
        return this.cognom;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getNom() {
        return this.nom;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public String getContrasenya() {
        return this.contrasenya;
    }

    public void addClientBDD(Conexio conexio){
        try{
            String query = String.format("INSERT INTO clients (id, nom, cognom, contrasenya) VALUES ('%s', '%s', '%s', '%s');", this.id, this.nom, this.cognom, this.contrasenya);
            conexio.execInsert(query);
        }catch(Exception e){};
    }
}

