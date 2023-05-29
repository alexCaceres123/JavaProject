public class Comanda {
    private String id;
    private String nomclient;
    private String cognomClient;
    private String idClient;
    private String nomordinador;
    private int preu;

    public Comanda(String id, String nomclient, String cognomClient, String idClient, String nomordinador, int preu) {
        this.id = id;
        this.nomclient = nomclient;
        this.nomordinador = nomordinador;
        this.preu = preu;
        this.cognomClient = cognomClient;
        this.idClient = idClient;
    }
    
    // Getters y setters
    
    public String getId() {
        return id;
    }

    public String getcognomClient() {
        return cognomClient;
    }

    public String getIdClient() {
        return idClient;
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
            String query = String.format("INSERT INTO comandes (id, nomclient, cognom, idClient, nomordinador, preu) VALUES ('%s', '%s', '%s', '%s', 'Ordinador %s', '%s');", this.id, this.nomclient, this.cognomClient, this.idClient, this.nomordinador, Integer.toString(this.preu));
            conexio.execInsert(query);
        }catch(Exception e){};
    }
}
