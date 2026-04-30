import Accounts.Client;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DataManager {

    Gson gson = new Gson();
    public void saveData(ArrayList<Client> clients) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try(FileWriter writer = new FileWriter("clients.json")){
            gson.toJson(clients,writer);
            System.out.println("File created and written.");
        }catch(IOException e){
            e.printStackTrace();
            System.out.println("Error saving data");
        }
    }

    public ArrayList<Client> loadData(){
        ArrayList<Client> clients = new ArrayList<>();
        try (FileReader reader = new FileReader("clients.json")) {
            Client c = gson.fromJson(reader, Client.class);  // Convert JSON back to object
            clients.add(c);
            System.out.println(c.getClientID()+ " " + c.getName() + " " + c.getAccounts() + " ");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return(clients);
    }
}
