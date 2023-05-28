import java.sql.*;

public class Magatzem {

    //GETTERS

    public void getAllStock(Statement stmt){
        System.out.println("\nHARDWARE");
        System.out.println("-----------------");
        this.getHardwareStock(stmt);
        System.out.println("\nSOFTWARE");
        System.out.println("-----------------");
        this.getSoftwareStock(stmt);
    }

    public void getHardwareStock(Statement stmt){
        try{
            ResultSet rs = this.execQuery("select * from hardware;", stmt);
            
            System.out.println("");
            while(rs.next()){
                String id = rs.getString("id");
                String name = rs.getString("nom");
                float preu = rs.getFloat("preu");
                System.out.println("ID: " + id + " MODEL: " + name + " PREU: " + preu);
            }
        }catch(Exception e){};
    }

    public void getSoftwareStock(Statement stmt){
        try{
            ResultSet rs = this.execQuery("select * from software;", stmt);

            System.out.println("");
            while(rs.next()){
                String id = rs.getString("id");
                String name = rs.getString("nom");
                float preu = rs.getFloat("preu");
                System.out.println("ID: " + id + " MODEL: " + name + " PREU: " + preu);
            }
        }catch(Exception e){};
    }

    public void getTargetes(Statement stmt){
        try{
            ResultSet rs = this.execQuery("select * from hardware where tipus = 'targeta';", stmt);
            System.out.println("");
            while(rs.next()){
                String id = rs.getString("id");
                String name = rs.getString("nom");
                float preu = rs.getFloat("preu");
                System.out.println("ID: " + id + " MODEL: " + name + " PREU: " + preu);
            }
        }catch(Exception e){};
    }

    public void getRAMs(Statement stmt){
        try{
            ResultSet rs = this.execQuery("select * from hardware where tipus = 'ram';", stmt);
            System.out.println("");
            while(rs.next()){
                String id = rs.getString("id");
                String name = rs.getString("nom");
                float preu = rs.getFloat("preu");
                System.out.println("ID: " + id + " MODEL: " + name + " PREU: " + preu);
            }
        }catch(Exception e){};
    }

    public void getDiscs(Statement stmt){
        try{
            ResultSet rs = this.execQuery("select * from hardware where tipus = 'disc';", stmt);
            
            System.out.println("");
            while(rs.next()){
                String id = rs.getString("id");
                String name = rs.getString("nom");
                float preu = rs.getFloat("preu");
                System.out.println("ID: " + id + " MODEL: " + name + " PREU: " + preu);
            }
        }catch(Exception e){};
    }

    public void getProcessadors(Statement stmt){
        try{
            ResultSet rs = this.execQuery("select * from hardware where tipus = 'processador';", stmt);
            
            System.out.println("");
            while(rs.next()){
                String id = rs.getString("id");
                String name = rs.getString("nom");
                float preu = rs.getFloat("preu");
                System.out.println("ID: " + id + " MODEL: " + name + " PREU: " + preu);
            }
        }catch(Exception e){};
    }

    public int getCountAllOrdinadors(Statement stmt){
        int count = 1;            

        try{
            ResultSet rs = this.execQuery("select * from ordinadors", stmt);
            while(rs.next()){
                count++;
            }
        }catch(Exception e){};
        
        return count++;
    }


    //FUNCIONALITATS

    public Ordinador valHardwareAndSoftware(String targeta, String processador, String disc, String ram, String sistema, Statement stmt){
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
            ResultSet rs = this.execQuery("select * from hardware where tipus = 'targeta';", stmt);
            
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

            rs = this.execQuery("select * from hardware where tipus = 'processador';", stmt);

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

            rs = this.execQuery("select * from hardware where tipus = 'disc';", stmt);

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

            rs = this.execQuery("select * from hardware where tipus = 'ram';", stmt);

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

            rs = this.execQuery("select * from software;", stmt);

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
            newOrdinador = this.setOrdinador(nomTargeta, nomDisc, nomProcessador, nomSistema, nomRam, preuTotal, stmt);
            return newOrdinador;
        }else{
            return newOrdinador;
        }
    }

    public Ordinador setOrdinador(String nomTargeta, String nomDisc, String nomProcessador, String nomSistema, String nomRam, int PreuTotal, Statement stmt){
        String idOrdinador = Integer.toString(this.getCountAllOrdinadors(stmt));
        String nomOrdinador = String.format("Ordinador %s", idOrdinador);
        Ordinador newOrdinador = new Ordinador(idOrdinador, nomOrdinador, PreuTotal, nomTargeta, nomRam, nomProcessador, nomDisc, nomSistema);
        newOrdinador.addOrdinadorBDD(stmt);

        System.out.println("\nAQUEST ES EL TEU ORDINADOR");
        System.out.println("-------------------------------");
        System.out.println(newOrdinador.getNom() + " " + newOrdinador.getPreutotal() + " " + newOrdinador.getGrafica() + " " + newOrdinador.getRam() + " " + newOrdinador.getProcessador() + " " + newOrdinador.getDisc() + " " + newOrdinador.getSoftware());
        return newOrdinador;
    }


    //QUERY FUNCTIONS

    public ResultSet execQuery(String query, Statement stmt){
        try{  
            ResultSet rs = stmt.executeQuery(query);  
            return rs;
        }catch(Exception e){ 
            System.out.println(e);
        };
        return null;
    };
}
