package daoLayer;

import entities.AccountEntity;
import entities.UserrEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

public class AccountDao extends AbstractDao<AccountEntity> {
    public AccountDao() {
        super(AccountEntity.class);
    }

    public AccountEntity getAccount(String name, String surname, String email) {
        EntityManager em = JPAutil.getEMF().createEntityManager();
        try {
            return em.createQuery("SELECT a FROM AccountEntity a WHERE a.name = :Name AND " +
                                                                        "a.surname = :Surname AND " +
                                                                        "a.eMail = :Email", AccountEntity.class)
                    .setParameter("Name", name)
                    .setParameter("Surname", surname)
                    .setParameter("Email", email)
                    .getSingleResult();
        } catch (
        NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
}
