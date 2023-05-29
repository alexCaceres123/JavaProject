import java.sql.*;  

public class Conexio {
    private static String HOST = "127.0.0.1";
    private static int PORT = 3306;
    private static String USERNAME = "root";
    private static String PASSWORD = "";
    private static String DATABASE = "botigaproject";
    private Statement stmt;
    private Connection conn;

    public Conexio(){

        try{  
            Class.forName("com.mysql.jdbc.Driver");  
            this.conn = DriverManager.getConnection("jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE, USERNAME, PASSWORD);  
            this.stmt = conn.createStatement();

        }catch(Exception e){ 
            System.out.println(e);
        };                
    }

    public ResultSet execQuery(String query){
        try{  
            ResultSet rs = this.stmt.executeQuery(query);  
            return rs;
        }catch(Exception e){ 
            System.out.println(e);
        };
        return null;
    };

    public void execInsert(String query){
        try{  
            this.stmt.executeUpdate(query);  
        }catch(Exception e){ 
            System.out.println(e);
        };
    };

    public void execUpdate(String query){
        try{
            this.stmt.executeUpdate(query);   
        }catch(Exception e){
        }
    }

    public void close(){
        try{  
            this.conn.close(); 
        }catch(Exception e){ 
            System.out.println(e);
        };
    };
}
