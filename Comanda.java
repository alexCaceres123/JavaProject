public class Comanda {
    private String id;
    private String nomclient;
    private String nomordinador;
    private int preu;

    public Comanda(String id, String nomclient, String nomordinador, int preu) {
        this.id = id;
        this.nomclient = nomclient;
        this.nomordinador = nomordinador;
        this.preu = preu;
    }
    
    // Getters y setters
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getNomclient() {
        return nomclient;
    }
    
    public void setNomclient(String nomclient) {
        this.nomclient = nomclient;
    }
    
    public String getNomordinador() {
        return nomordinador;
    }
    
    public void setNomordinador(String nomordinador) {
        this.nomordinador = nomordinador;
    }
    
    public int getPreu() {
        return preu;
    }
    
    public void setPreu(int preu) {
        this.preu = preu;
    }

    public void addComandaBDD(Conexio conexio){
        try{
            String query = String.format("INSERT INTO comandes (id, nomclient, nomordinador, preu) VALUES ('%s', '%s', 'Ordinador %s', '%s');", this.id, this.nomclient, this.nomordinador, Integer.toString(this.preu));
            conexio.execInsert(query);
        }catch(Exception e){};
    }
}
