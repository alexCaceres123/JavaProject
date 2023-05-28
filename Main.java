import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Magatzem magatzem = new Magatzem();
        Botiga botiga = new Botiga(magatzem);
        boolean endWhile = true;

        while(endWhile){
            int res = botiga.getMenu();

            if(res == 1){
                botiga.comprarOrdinador();
            }
            else if(res == 2){
                botiga.getAllStock();
            }
            else if(res == 3){
                botiga.getHardwareStock();
            }
            else if (res == 4){
                botiga.getSoftwareStock();
            }  
            else if (res == 5){
                botiga.getAllComandes();
            }
            else if(res == 6){
                botiga.getAllClients();
            }
            else if(res == 7){
                botiga.arreglarOrdinador();
            }
            
            System.out.print("\nSortir de l'aplicacio [SI/NO]: ");
            String resposta = sc.nextLine();

            if(resposta.toLowerCase().equals("si")){
                endWhile = false;
            }
        }
        
        sc.close();
    }
}