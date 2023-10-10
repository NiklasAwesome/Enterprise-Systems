package se.liu.ida.tdp024.account.data.impl.db.facade;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.impl.db.entity.AccountDB;
import se.liu.ida.tdp024.account.data.impl.db.util.EMF;

public class AccountEntityFacadeDB implements AccountEntityFacade {

    @Override
    public long create(String bankKey, String personKey, String accountType) {
        EntityManager em = EMF.getEntityManager();

        try {
            em.getTransaction().begin();

            Account account = new AccountDB();

            account.setBankKey(bankKey);
            account.setPersonKey(personKey);
            account.setAccountType(accountType);
            account.setHoldnings(0);

            em.persist(account);
            em.getTransaction().commit();

            return account.getID();

        } catch (Exception e) {
            return 0;

        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }

    @Override
    public Account find(long id) {
        EntityManager em = EMF.getEntityManager();

        try {
            return em.find(AccountDB.class, id);

        } catch (Exception e) {
            System.out.println("Error in find(): " + e.toString());
            return null;

        } finally {
            em.close();
        }
    }

    @Override
    public List<Account> findAll() {
        EntityManager em = EMF.getEntityManager();

        try {
            Query query = em.createQuery("SELECT t FROM AccountDB t");
            return query.getResultList();
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }

    }

}
