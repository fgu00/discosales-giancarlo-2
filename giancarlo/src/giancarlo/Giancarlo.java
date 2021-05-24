/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package giancarlo;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.SocketAddress;
/**
 *
 * @author super
 */
public class Giancarlo {

    /**
     * @param args the command line arguments
     */
     public static ArrayList<utente>persone=new ArrayList();
    public static void main(String[] args) {
         ArrayList<Thread>log=new ArrayList();
        login acesso;
        try {
            ServerSocket server=new ServerSocket(20);
            System.out.println("server attivo");
            acesso=new login();
            while(true){
                 Socket client = server.accept();
                   PrintWriter out=new PrintWriter(client.getOutputStream(),true);
                    if(client!=null){
                        acesso.accedi(client);
                        Thread a=new Thread(acesso);
                        a.start();
                        log.add(a);
                        for (int i = 0; i < log.size(); i++) {
                            if(log.get(i).isAlive()==false){
                                log.remove(i);
                            }
                        }
                }
                System.out.println("33");
            }
        } catch (IOException ex) {
            Logger.getLogger(Giancarlo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

    
    

