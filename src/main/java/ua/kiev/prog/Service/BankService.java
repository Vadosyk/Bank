package ua.kiev.prog.Service;

import ua.kiev.prog.Entity.Account;
import ua.kiev.prog.Entity.Client;
import ua.kiev.prog.Entity.Transaction;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class BankService {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("MavenBank");
    EntityManager em = emf.createEntityManager();

    public void openAccount(String name, String phone){

        em.getTransaction().begin();

        Client c1 = new Client();
        c1.setName(name);
        c1.setPhone(phone);

        Account a1 = new Account();
        a1.setEUR(0);
        a1.setUSD(0);
        a1.setUAH(0);
        em.persist(a1);

        c1.setAccount(a1);
        em.persist(c1);

        em.getTransaction().commit();

        em.close();
        emf.close();

    }


    public void depositAccount(String name, String currency, int sum){

        Query query = null;

        query = em.createQuery("SELECT c FROM Client c WHERE c.name = :name", Client.class);
        query.setParameter("name", name);

        Client cl = (Client) query.getSingleResult();
        Account ac = cl.getAccount();
        if(currency.equals("USD"))
            ac.setUSD(ac.getUSD()+sum);
        if(currency.equals("EUR"))
            ac.setEUR(ac.getEUR()+sum);
        if(currency.equals("UAH"))
            ac.setUAH(ac.getUAH()+sum);

        em.getTransaction().begin();

        em.merge(ac);


        Transaction tr = new Transaction();
        tr.setAccount(ac);
        tr.setCurrency(null);

        if (sum > 0){
            tr.setCurrency(currency);
            tr.setTransactionName("DEPOSIT "+sum);
            em.persist(tr);
        }

        em.getTransaction().commit();

        em.close();
        emf.close();
    }


    public void withdrawAccount(String name, String currency, int sum){

        Query query = null;

        query = em.createQuery("SELECT c FROM Client c WHERE c.name = :name", Client.class);
        query.setParameter("name", name);

        Client cl = (Client) query.getSingleResult();
        Account ac = cl.getAccount();
        if(currency.equals("USD")){
            if(ac.getUSD()>=sum){
                ac.setUSD(ac.getUSD()-sum);
            }
        }
        if(currency.equals("EUR")){
            if(ac.getEUR()>=sum){
                ac.setEUR(ac.getEUR()-sum);
            }
        }
        if(currency.equals("UAH")){
            if(ac.getUAH()>=sum){
                ac.setUAH(ac.getUAH()-sum);
            }
        }

        em.getTransaction().begin();

        em.merge(ac);

        Transaction tr = new Transaction();
        tr.setAccount(ac);
        tr.setCurrency(null);

        if (sum > 0){
            tr.setCurrency(currency);
            tr.setTransactionName("WITHDROWAL "+sum);
            em.persist(tr);
        }
        em.getTransaction().commit();

        em.close();
        emf.close();
    }

    public void accountStatement(String name){

        Query query = null;

        query = em.createQuery("SELECT c FROM Client c WHERE c.name = :name", Client.class);
        query.setParameter("name", name);
        Client c = (Client) query.getSingleResult();


        List <Transaction> trList = c.getAccount().getTransactions();
        System.out.println("Statement of client "+name+":");
        for (Transaction tr : trList){
            System.out.println("Transaction ID :"+tr.getId()+" Name of Transaction :"+tr.getTransactionName()+" Currency :"+tr.getCurrency());

        }

        em.close();
        emf.close();

    }

    public void accountBalance(String name){

        int courseUSD = 24;
        int courseEUR = 28;

        Query query = null;

        query = em.createQuery("SELECT c FROM Client c WHERE c.name = :name", Client.class);
        query.setParameter("name", name);
        Client c = (Client) query.getSingleResult();

        double uahUSD = c.getAccount().getUSD()*courseUSD;
        double uahEUR = c.getAccount().getEUR()*courseEUR;
        System.out.println("Balance of "+name+" :");
        System.out.println("USD : "+c.getAccount().getUSD()+" Equivalent in UAH : "+uahUSD);
        System.out.println("EUR : "+c.getAccount().getEUR()+" Equivalent in UAH : "+uahEUR);
        System.out.println("UAH : "+c.getAccount().getUAH()+" Equivalent in UAH : "+c.getAccount().getUAH());
        double total = uahUSD+uahEUR+c.getAccount().getUAH();
        System.out.println("Total : " +total);

        em.close();
        emf.close();

    }

}
