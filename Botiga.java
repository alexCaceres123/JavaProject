import java.util.Scanner;
import java.sql.*;  

public class Botiga {
    private Conexio conexio;
    private Magatzem magatzem;
    static Scanner sc = new Scanner(System.in);

    public Botiga(Conexio conexio, Magatzem magatzem){
        this.conexio = conexio;
        this.magatzem = magatzem;
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
            System.out.println("7 REPARAR ORDINADOR:\n");
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
            ResultSet rs = this.conexio.execQuery("select * from clients;");
            System.out.println("");
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("nom");
                String cognom = rs.getString("cognom");
                System.out.println("ID: " + id + " NAME: " + name + " COGNOM: " + cognom);
            }

        }catch(Exception e){};
    }

    public void getAllComandes(){
        try{
            ResultSet rs = this.conexio.execQuery("select * from comandes;");
            
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
            ResultSet rs = this.conexio.execQuery("select * from comandes");
            while(rs.next()){
                count++;
            }
        }catch(Exception e){};
        
        return Integer.toString(count++);
    }

    public String getCountAllClients(){
        int count = 1;            

        try{
            ResultSet rs = this.conexio.execQuery("select * from clients");
            while(rs.next()){
                count++;
            }
        }catch(Exception e){};
        
        return Integer.toString(count++);
    }

    public void getAllStock(){
        this.magatzem.getAllStock();
    }

    public void getHardwareStock(){
        this.magatzem.getHardwareStock();
    }

    public void getSoftwareStock(){
        this.magatzem.getSoftwareStock();
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
        Comanda comanda = new Comanda(this.getCountAllComandes(), client.getNom(), client.getCognom(), client.getId(), ordinador.getId(), ordinador.getPreutotal());
        comanda.addComandaBDD(this.conexio);
    }

    public Ordinador createAndAddOrdinador(){
        boolean endWhile = true;
        Ordinador newOrdinador = new Ordinador("", "", 0, "", "", "", "", "");

        while(endWhile){
            System.out.println("\nCREACIÓ DE L'ORDINADOR (ESPECIFICA LES ID)");
            System.out.println("--------------------------------------------");
            this.magatzem.getTargetes();
            System.out.print("\nTARGETA GRÀFICA: ");
            String targeta = sc.nextLine();
    
            this.magatzem.getProcessadors();
            System.out.print("\nPROCESSADOR: ");
            String processador = sc.nextLine();
    
            this.magatzem.getDiscs();
            System.out.print("\nDISC: ");
            String disc = sc.nextLine();
    
            this.magatzem.getRAMs();
            System.out.print("\nRAM: ");
            String ram = sc.nextLine();
    
            this.magatzem.getSoftwareStock();
            System.out.print("\nSISTEMA: ");
            String sistema = sc.nextLine();

            newOrdinador = this.magatzem.valHardwareAndSoftware(targeta, processador, disc, ram, sistema);
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
            ResultSet rs = this.conexio.execQuery("select * from clients;");

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
        newClient.addClientBDD(this.conexio);    
        return newClient;
    }

    public void arreglarOrdinador(){
        try{
            this.magatzem.getOrdinadors();

            int x = this.magatzem.getCountAllOrdinadors();
            System.out.println(x);
            boolean valWhile = true;
            String idOrdinador = "";

            while(valWhile){
                System.out.print("\nEspecífica la ID de l'ordinador: ");
                idOrdinador = sc.nextLine();
                
                if(idOrdinador.matches("[0-9]+") && Integer.parseInt(idOrdinador) <= x && Integer.parseInt(idOrdinador) > 0){
                    valWhile = false;
                }
            }

            String query = String.format("select * from ordinadors where id = '%s';", idOrdinador);
            ResultSet rs2 = this.conexio.execQuery(query);
            
            String id = "";
            String name = "";
            String grafica = "";
            String ram = "";
            String processador = "";
            String disc = "";
            String software = "";
            int preuTotal = 0;

            while(rs2.next()){
                id = rs2.getString("id");
                name = rs2.getString("nom");
                grafica = rs2.getString("grafica");
                ram = rs2.getString("ram");
                processador = rs2.getString("processador");
                disc = rs2.getString("disc");
                software = rs2.getString("software");
                preuTotal = rs2.getInt("preutotal");
            }

            boolean endWhile = true;
            String op = "";

            while(endWhile){
                System.out.println("\nQuin component vols cambiar: ");
                System.out.println("---------------------------------");
                System.out.println("[1] Grafica");
                System.out.println("[2] ram");
                System.out.println("[3] processador");
                System.out.println("[4] disc");

                System.out.print("\nOpcio: ");
                op = sc.nextLine();

                if(op.matches("[0-9]+") && Integer.parseInt(op) <= 4 && Integer.parseInt(op) > 0){
                    endWhile = false;
                }
            }

            Ordinador newOrd = new Ordinador(id, name, preuTotal, grafica, ram, processador, disc, software);
            boolean endWhile2 = true;
            String finalQuery = "";

            while(endWhile2){

                if(op.equals("1")){
                    Hardware newComp = this.cambiarComponent("TARGETA");
                    newOrd.setGrafica(newComp.getNom());
                    finalQuery = String.format("update ordinadors set %s = '%s'", "grafica", newOrd.getGrafica());
                    if(!newComp.getId().equals("")){
                        endWhile2 = false;
                    }
                }
                else if(op.equals("2")){
                    Hardware newComp = this.cambiarComponent("RAM");
                    newOrd.setRam(newComp.getNom());
                    finalQuery = String.format("update ordinadors set %s = '%s'", "ram", newOrd.getRam());

                    if(!newComp.getId().equals("")){
                        endWhile2 = false;
                    }
                }
                else if(op.equals("3")){
                    Hardware newComp = this.cambiarComponent("PROCESSADOR");
                    newOrd.setProcessador(newComp.getNom());
                    finalQuery = String.format("update ordinadors set %s = '%s'", "processador", newOrd.getProcessador());

                    if(!newComp.getId().equals("")){
                        endWhile2 = false;
                    }
                }
                else if(op.equals("4")){
                    Hardware newComp = this.cambiarComponent("DISC");
                    newOrd.setDisc(newComp.getNom());
                    finalQuery = String.format("update ordinadors set %s = '%s'", "disc", newOrd.getDisc());

                    if(!newComp.getId().equals("")){
                        endWhile2 = false;
                    }
                }
            }

            this.conexio.execUpdate(finalQuery);

        }catch(Exception e){};
    }

    public Hardware cambiarComponent(String component){

        ResultSet rs;
        String id = "";
        String name = "";
        int preu = 0;
        String queryH = "";

        System.out.println(String.format("\n%s EN STOCK [ESPECIFICA LA ID]", component));
        System.out.println("---------------------");

        try{
            ResultSet x = this.conexio.execQuery(String.format("select * from hardware where tipus = '%s'", component.toLowerCase()));

            while(x.next()){
                String idd= x.getString("id");
                String namee = x.getString("nom");
                float preuu = x.getFloat("preu");
                System.out.println("ID: " + idd + " MODEL: " + namee + " PREU: " + preuu);
            }
        }catch(Exception e){}
        
        System.out.print(String.format("\nEscull un %s: ",component));
        String idComponent = sc.nextLine();
        System.out.println(idComponent);

        try{
            queryH = String.format("select * from hardware where id = '%s';", idComponent.toLowerCase());
            rs = this.conexio.execQuery(queryH);
    
            while(rs.next()){
                id = rs.getString("id");
                name = rs.getString("nom");
                preu = rs.getInt("preu");
            }

        }catch(Exception e){}

        return new Hardware(id, name, preu);
    }
}