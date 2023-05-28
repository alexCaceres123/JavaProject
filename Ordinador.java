import java.sql.*;  

public class Ordinador {
    private String id;
    private String nom;
    private int preutotal;
    private String grafica;
    private String ram;
    private String processador;
    private String disc;
    private String software;
    
    public Ordinador(String id, String nom, int preutotal, String grafica, String ram, String processador, String disc, String software) {
        this.id = id;
        this.nom = nom;
        this.preutotal = preutotal;
        this.grafica = grafica;
        this.ram = ram;
        this.processador = processador;
        this.disc = disc;
        this.software = software;
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
    
    public int getPreutotal() {
        return preutotal;
    }
    
    public void setPreutotal(int preutotal) {
        this.preutotal = preutotal;
    }
    
    public String getGrafica() {
        return grafica;
    }
    
    public void setGrafica(String grafica) {
        this.grafica = grafica;
    }
    
    public String getRam() {
        return ram;
    }
    
    public void setRam(String ram) {
        this.ram = ram;
    }
    
    public String getProcessador() {
        return processador;
    }
    
    public void setProcessador(String processador) {
        this.processador = processador;
    }
    
    public String getDisc() {
        return disc;
    }
    
    public void setDisc(String disc) {
        this.disc = disc;
    }
    
    public String getSoftware() {
        return software;
    }
    
    public void setSoftware(String software) {
        this.software = software;
    }

    public void addOrdinadorBDD(Statement stmt){
        try{
            String query = String.format("INSERT INTO ordinadors (id, nom, preutotal, grafica, ram, processador, disc, software)VALUES ('%s', '%s', %d, '%s', '%s', '%s', '%s', '%s');", this.id, this.nom, this.preutotal, this.grafica, this.ram, this.processador, this.disc, this.software);
            this.execInsert(query, stmt);
        }catch(Exception e){};
    }

    public void execInsert(String query, Statement stmt){
        try{  
            stmt.executeUpdate(query);  
        }catch(Exception e){ 
            System.out.println(e);
        };
    };
}
