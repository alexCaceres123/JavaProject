import java.sql.*;

public class Magatzem {
    private Conexio conexio;

    public Magatzem(Conexio conexio){
        this.conexio = conexio;
    }

    //GETTERS

    public void getAllStock(){
        System.out.println("\nHARDWARE");
        System.out.println("-----------------");
        this.getHardwareStock();
        System.out.println("\nSOFTWARE");
        System.out.println("-----------------");
        this.getSoftwareStock();
    }

    public void getHardwareStock(){
        try{
            ResultSet rs = this.conexio.execQuery("select * from hardware;");
            
            System.out.println("");
            while(rs.next()){
                String id = rs.getString("id");
                String name = rs.getString("nom");
                float preu = rs.getFloat("preu");
                System.out.println("ID: " + id + " MODEL: " + name + " PREU: " + preu);
            }
        }catch(Exception e){};
    }

    public void getSoftwareStock(){
        try{
            ResultSet rs = this.conexio.execQuery("select * from software;");

            System.out.println("");
            while(rs.next()){
                String id = rs.getString("id");
                String name = rs.getString("nom");
                float preu = rs.getFloat("preu");
                System.out.println("ID: " + id + " MODEL: " + name + " PREU: " + preu);
            }
        }catch(Exception e){};
    }

    public void getTargetes(){
        try{
            ResultSet rs = this.conexio.execQuery("select * from hardware where tipus = 'targeta';");
            System.out.println("");
            while(rs.next()){
                String id = rs.getString("id");
                String name = rs.getString("nom");
                float preu = rs.getFloat("preu");
                System.out.println("ID: " + id + " MODEL: " + name + " PREU: " + preu);
            }
        }catch(Exception e){};
    }

    public void getRAMs(){
        try{
            ResultSet rs = this.conexio.execQuery("select * from hardware where tipus = 'ram';");
            System.out.println("");
            while(rs.next()){
                String id = rs.getString("id");
                String name = rs.getString("nom");
                float preu = rs.getFloat("preu");
                System.out.println("ID: " + id + " MODEL: " + name + " PREU: " + preu);
            }
        }catch(Exception e){};
    }

    public void getDiscs(){
        try{
            ResultSet rs = this.conexio.execQuery("select * from hardware where tipus = 'disc';");
            
            System.out.println("");
            while(rs.next()){
                String id = rs.getString("id");
                String name = rs.getString("nom");
                float preu = rs.getFloat("preu");
                System.out.println("ID: " + id + " MODEL: " + name + " PREU: " + preu);
            }
        }catch(Exception e){};
    }

    public void getProcessadors(){
        try{
            ResultSet rs = this.conexio.execQuery("select * from hardware where tipus = 'processador';");
            
            System.out.println("");
            while(rs.next()){
                String id = rs.getString("id");
                String name = rs.getString("nom");
                float preu = rs.getFloat("preu");
                System.out.println("ID: " + id + " MODEL: " + name + " PREU: " + preu);
            }
        }catch(Exception e){};
    }

    public int getCountAllOrdinadors(){
        int count = 1;            

        try{
            ResultSet rs = this.conexio.execQuery("select * from ordinadors");
            while(rs.next()){
                count++;
            }
        }catch(Exception e){};
        
        return count++;
    }


    //FUNCIONALITATS

    public Ordinador valHardwareAndSoftware(String targeta, String processador, String disc, String ram, String sistema){
        boolean valTargeta = false;
        boolean valProcessador = false;
        boolean valDisc = false;
        boolean valRAM = false;
        boolean valSistema = false;

        String nomTargeta = "";
        String nomDisc = "";
        String nomProcessador = "";
        String nomRam = "";
        String nomSistema = "";
        int preuTotal = 0;

        Ordinador newOrdinador = new Ordinador("", "", 0, "", "", "", "", "");
        

        try{
            ResultSet rs = this.conexio.execQuery("select * from hardware where tipus = 'targeta';");
            
            while(rs.next()){
                String id = rs.getString("id");
                String nom = rs.getString("nom");
                String preu = rs.getString("preu");

                if(id.equals(targeta)){
                    nomTargeta = nom;
                    preuTotal += Integer.parseInt(preu);
                    valTargeta = true;
                }
            }

            rs = this.conexio.execQuery("select * from hardware where tipus = 'processador';");

            while(rs.next()){
                String id = rs.getString("id");
                String nom = rs.getString("nom");
                String preu = rs.getString("preu");

                if(id.equals(processador)){
                    nomProcessador = nom;
                    preuTotal += Integer.parseInt(preu);
                    valProcessador = true;
                }
            }

            rs = this.conexio.execQuery("select * from hardware where tipus = 'disc';");

            while(rs.next()){
                String id = rs.getString("id");
                String nom = rs.getString("nom");
                String preu = rs.getString("preu");

                if(id.equals(disc)){
                    nomDisc = nom;
                    preuTotal += Integer.parseInt(preu);
                    valDisc = true;
                }
            }

            rs = this.conexio.execQuery("select * from hardware where tipus = 'ram';");

            while(rs.next()){
                String id = rs.getString("id");
                String nom = rs.getString("nom");
                String preu = rs.getString("preu");

                if(id.equals(ram)){
                    nomRam = nom;
                    preuTotal += Integer.parseInt(preu);
                    valRAM = true;
                }
            }

            rs = this.conexio.execQuery("select * from software;");

            while(rs.next()){
                String id = rs.getString("id");
                String nom = rs.getString("nom");
                String preu = rs.getString("preu");

                if(id.equals(sistema)){
                    nomSistema = nom;
                    preuTotal += Integer.parseInt(preu);
                    valSistema = true;
                }
            }
        }catch(Exception e){};
        
        if(valTargeta && valProcessador && valRAM && valDisc && valSistema){
            newOrdinador = this.setOrdinador(nomTargeta, nomDisc, nomProcessador, nomSistema, nomRam, preuTotal);
            return newOrdinador;
        }else{
            return newOrdinador;
        }
    }

    public Ordinador setOrdinador(String nomTargeta, String nomDisc, String nomProcessador, String nomSistema, String nomRam, int PreuTotal){
        String idOrdinador = Integer.toString(this.getCountAllOrdinadors());
        String nomOrdinador = String.format("Ordinador %s", idOrdinador);
        Ordinador newOrdinador = new Ordinador(idOrdinador, nomOrdinador, PreuTotal, nomTargeta, nomRam, nomProcessador, nomDisc, nomSistema);
        newOrdinador.addOrdinadorBDD(this.conexio);

        System.out.println("\nAQUEST ES EL TEU ORDINADOR");
        System.out.println("-------------------------------");
        System.out.println(newOrdinador.getNom() + " " + newOrdinador.getPreutotal() + " " + newOrdinador.getGrafica() + " " + newOrdinador.getRam() + " " + newOrdinador.getProcessador() + " " + newOrdinador.getDisc() + " " + newOrdinador.getSoftware());
        return newOrdinador;
    }
}
