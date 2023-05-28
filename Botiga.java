import java.util.Scanner;
import java.sql.*;  

public class Botiga {
    private Magatzem magatzem;
    static Scanner sc = new Scanner(System.in);
    private static String HOST = "127.0.0.1";
    private static int PORT = 3306;
    private static String USERNAME = "root";
    private static String PASSWORD = "";
    private static String DATABASE = "botigaproject";
    private Statement stmt;
    private static Connection conn;

    public Botiga(Magatzem magatzem){
        this.magatzem = magatzem;

        try{  
            Class.forName("com.mysql.jdbc.Driver");  
            conn = DriverManager.getConnection("jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE, USERNAME, PASSWORD);  
            stmt = conn.createStatement();  

        }catch(Exception e){ 
            System.out.println(e);
        };     
           
    }

    //GETTERS

    public int getMenu(){
        boolean endWhile = true;
        String res = "";
        while(endWhile){
            System.out.println("--------------------------------------");
            System.out.println("TRIA UNA OPCIÓ:\n");
            System.out.println("1 CREAR I COMPRAR UN ORDINADOR:");
            System.out.println("2 VEURE TOT EL STOCK:");
            System.out.println("3 VEURE STOCK HARDWARE:");
            System.out.println("4 VEURE STOCK SOFTWARE:");
            System.out.println("5 ORDINADORS VENGUTS:");
            System.out.println("6 CLIENTS DE LA BOTIGA:");
            System.out.println("7 ARREGLAR ORDINADOR:\n");

            System.out.print("OPCIÓ: ");  
            res = sc.nextLine();
  
            if(res.matches("[0-9]+") && Integer.parseInt(res) <= 7 && Integer.parseInt(res) > 0){
                endWhile = false;
            }
        }
        return Integer.parseInt(res);
    }

    public void getAllClients(){
        try{
            ResultSet rs = this.execQuery("select * from clients;");
            System.out.println("");
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("nom");
                System.out.println("ID: " + id + " NAME: " + name);
            }

        }catch(Exception e){};
    }

    public void getAllComandes(){
        try{
            ResultSet rs = this.execQuery("select * from comandes;");
            
            System.out.println("");
            while(rs.next()){
                String id = rs.getString("id");
                String name = rs.getString("nomclient");
                String nomOrdinador = rs.getString("nomordinador");
                String preu = rs.getString("preu");
                System.out.println("ID: " + id + " CLIENT: " + name + " ORDINADOR: " + nomOrdinador + " PREU: " + preu);
            }
        }catch(Exception e){};
    }

    public String getCountAllComandes(){
        int count = 0;            

        try{
            ResultSet rs = this.execQuery("select * from comandes");
            while(rs.next()){
                count++;
            }
        }catch(Exception e){};
        
        return Integer.toString(count++);
    }

    public String getCountAllClients(){
        int count = 1;            

        try{
            ResultSet rs = this.execQuery("select * from clients");
            while(rs.next()){
                count++;
            }
        }catch(Exception e){};
        
        return Integer.toString(count++);
    }

    public void getAllStock(){
        this.magatzem.getAllStock(this.stmt);
    }

    public void getHardwareStock(){
        this.magatzem.getHardwareStock(this.stmt);
    }

    public void getSoftwareStock(){
        this.magatzem.getSoftwareStock(this.stmt);
    }

    //FUNCIONALITATS

    public void comprarOrdinador(){
        boolean endWhile = true;
        String resposta = "";
        Ordinador OrdinadorComprat = new Ordinador("", "", 0, "", "", "", "", "");
        Client clientCompra = new Client("", "", "", "");

        while(endWhile){
            System.out.print("\nTENS COMPTE A LA NOSTRE BOTIGA [SI/NO]?");
            resposta = sc.nextLine();

            if(resposta.toLowerCase().equals("si") || resposta.toLowerCase().equals("no")){
                endWhile = false;
            }
        }


        if(resposta.equals("si")){
            int count = 1;
            boolean val = false;


            while(count < 3){
                clientCompra = this.iniciarSessio();
                if(clientCompra.getId().equals(" ")){
                    val = true;
                    break;
                }
                count++;
            }

            if(val){
                System.out.println("\nS'HA INICIAT SESSIÓ");
                OrdinadorComprat = this.createAndAddOrdinador();
                System.out.print("\nCOMPRAR [SI/NO]");
                String resp = sc.nextLine().toLowerCase();
                
                if(resp.equals("si")){
                    System.out.println("\nGRACIES PER LA TEVA COMPRA");
                    this.creacioComanda(OrdinadorComprat, clientCompra);
                }
            }else{
                System.out.println("\nNO S'HA POGUT INICIAR SESSIO");
            }
        }
        else{
            clientCompra = this.registrarClient();
            OrdinadorComprat = this.createAndAddOrdinador();
            System.out.print("\nCOMPRAR [SI/NO]");
            String resp = sc.nextLine().toLowerCase();
                
            if(resp.equals("si")){
                System.out.println("\nGRACIES PER LA TEVA COMPRA");
                this.creacioComanda(OrdinadorComprat, clientCompra);
            }
        }
    }

    public void creacioComanda(Ordinador ordinador, Client client){
        System.out.println("\nComanda");
        Comanda comanda = new Comanda(this.getCountAllComandes(), client.getNom(), ordinador.getId(), ordinador.getPreutotal());
        comanda.addComandaBDD(stmt);
    }

    public Ordinador createAndAddOrdinador(){
        boolean endWhile = true;
        Ordinador newOrdinador = new Ordinador("", "", 0, "", "", "", "", "");

        while(endWhile){
            System.out.println("\nCREACIÓ DE L'ORDINADOR (ESPECIFICA LES ID)");
            System.out.println("--------------------------------------------");
            this.magatzem.getTargetes(stmt);
            System.out.print("\nTARGETA GRÀFICA: ");
            String targeta = sc.nextLine();
    
            this.magatzem.getProcessadors(stmt);
            System.out.print("\nPROCESSADOR: ");
            String processador = sc.nextLine();
    
            this.magatzem.getDiscs(stmt);
            System.out.print("\nDISC: ");
            String disc = sc.nextLine();
    
            this.magatzem.getRAMs(stmt);
            System.out.print("\nRAM: ");
            String ram = sc.nextLine();
    
            this.magatzem.getSoftwareStock(stmt);
            System.out.print("\nSISTEMA: ");
            String sistema = sc.nextLine();

            newOrdinador = this.magatzem.valHardwareAndSoftware(targeta, processador, disc, ram, sistema, stmt);
            if(!newOrdinador.getId().equals("")){
                endWhile = false;
            }else{
                System.out.println("\nNO HAS ESPECÍFICAT BE LES ID");
            }
        }

        return newOrdinador;
    }

    public Client iniciarSessio(){
        System.out.println("\nINICIA SESSIÓ");
        System.out.println("---------------------");
        System.out.print("NOM: ");
        String nomClient = sc.nextLine();
        System.out.print("COGNOM: ");
        String cognomClient = sc.nextLine();
        System.out.print("CONTRASENYA: ");
        String contrasenyaClient = sc.nextLine();

        Client newClient = new Client("", "", "", "");

        try{
            ResultSet rs = this.execQuery("select * from clients;");

            while(rs.next()){
                String name = rs.getString("nom");
                String cognom = rs.getString("cognom");
                String contrasenya = rs.getString("contrasenya");

                if(nomClient.equals(name) && cognomClient.equals(cognom) && contrasenyaClient.equals(contrasenya)){
                    newClient = new Client(" ", name, cognom, contrasenya);
                    return newClient;
                }
            }

        }catch(Exception e){};

        return newClient;
    }

    public Client registrarClient(){
        System.out.println("\nCREACIÓ DEL COMPTE");
        System.out.println("---------------------");
        System.out.print("NOM: ");
        String nomClient = sc.nextLine();
        System.out.print("COGNOM: ");
        String CognomClient = sc.nextLine();
        System.out.print("CONTRASENYA: ");
        String contrasenyaClient = sc.nextLine();
        String idClient = this.getCountAllClients();
        Client newClient = new Client(idClient, nomClient, CognomClient, contrasenyaClient);
        newClient.addClientBDD(stmt);    
        return newClient;
    }

    public void arreglarOrdinador(){
        System.out.println("Arregaldr Ordinador");
    }

    //QUERY FUNCTIONS

    public ResultSet execQuery(String query){
        try{  
            ResultSet rs = stmt.executeQuery(query);  
            return rs;
        }catch(Exception e){ 
            System.out.println(e);
        };
        return null;
    };

    public void close(){
        try{  
            conn.close(); 
        }catch(Exception e){ 
            System.out.println(e);
        };
    };
}