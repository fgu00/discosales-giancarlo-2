/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package giancarlo;
import static giancarlo.Giancarlo.persone;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.rmi.transport.Transport;
/**
 *
 * @author super
 */
public class login  implements Runnable{
   Scanner sc=new Scanner(System.in);
    private Socket accedi;
    private int posizione;
    private utente a;
    private boolean log=false;
    private PrintWriter out;
    private BufferedReader in;
    private ArrayList<utente>utenti=new ArrayList();
    private gestione_canali gc=new gestione_canali();
    private InputStream i;
    private ObjectInputStream o;
    private OutputStream oi;
    private ObjectOutputStream oo;

    public login() {
        log=false;
    }
    public void accedi(Socket clientsocket) throws IOException{
          accedi=clientsocket;
          System.out.println(accedi.getInetAddress());
          
            out=new PrintWriter(accedi.getOutputStream(),true);
            in=new BufferedReader(new InputStreamReader(accedi.getInputStream()));
//             i = accedi.getInputStream();
//             o = new ObjectInputStream(i);
              oi = accedi.getOutputStream();
             oo = new ObjectOutputStream(oi);
            log=true;
    }
    public void interazioni() throws IOException{
        boolean ciclo=true;
        while(ciclo==true){
            String richiesta=in.readLine();
            System.out.println(richiesta);
            String[]m=richiesta.split(":");
            int n=Integer.parseInt(m[0]);
            switch(n){
                case 0:
                String nome=m[1];
                String password=m[2];
                String mail=m[3];
                this.a=new utente(nome,password,mail,"b");
                boolean acesso=false; 
                while(!acesso){
                 Properties p=new Properties();
                 p.put("mail.smtp.auth", "true");
                 p.put("mail.smpt.starttls.enable", mail);
                 //Session s=new Session;
                }
                break;
                case 1:
                    //da fare quando sara implementato il salvattaggio
                    System.out.println("jdjdjd");
                    Boolean esiste=false;
                    String nomeu=m[1];
                    String passwordu=m[2];
                    for (int j = 0; j < utenti.size(); j++) {
                        if(nomeu.equals(utenti.get(j).getNome())&&passwordu.equals(utenti.get(j).getPassword())){
                         // oo.writeObject(utenti.get(j));
                            System.out.println(j);
                          a=utenti.get(j);
                          posizione=j;
                          esiste=true;
                          break;
                        }
                    }
                    if(esiste==true){
                        oo.writeObject("1");
                        oo.writeObject(utenti.get(posizione));
                        System.out.println("u");
                    }else{
                        String m1="0";
                        oo.writeObject(m1);
                        oo.flush();
                        System.out.println("mlwdw");
                    }
                    break;
                case 2:
                    //per creare un nuovo canale
                    String nome2=m[1];
                    canale nuovo=new canale(nome2);
                    gc.aggiungicanale(nuovo);
                    a.new_canale(nuovo);
                    break;
                case 3:
                    //accedi ad un canale
                    String id=m[1];
                    gc.accedi(accedi);
                    gc.accedi_canale(Integer.parseInt(id));
                    break;
                case 4:
                      //uscire
                    utenti.set(posizione, a);
                    ciclo=false;
                    i.close();
                    o.close();
                    oo.close();
                    oi.close();
            } 
        }
    }
       
    
    
    
    public boolean accesso_eseguito(){
        return log;
    }
    public utente getUtente(){
        return a;  
    }

    @Override
    public void run() {
       try { 
           interazioni();
       } catch (IOException ex) {
           Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
       }
    }
    
} 

