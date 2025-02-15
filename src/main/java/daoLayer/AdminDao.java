package daoLayer;

import entities.AccountEntity;
import entities.AdminnEntity;
import entities.UserrEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

public class AdminDao extends AbstractDao<AdminnEntity> {
    public AdminDao() {
        super(AdminnEntity.class);
    }

    public AdminnEntity getAdmin(String name, String surname, String email) {
        EntityManager em = JPAutil.getEMF().createEntityManager();
        try {
            return em.createQuery("SELECT a FROM AdminnEntity a WHERE a.name = :name AND a.surname = :surname AND a.eMail = :email", AdminnEntity.class)
                    .setParameter("name", name)
                    .setParameter("surname", surname)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
}

