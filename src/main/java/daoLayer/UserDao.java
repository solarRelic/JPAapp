package daoLayer;

import entities.CarEntity;
import entities.UserrEntity;
import entities.WorkerEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import serviceLayer.CarService;

public class UserDao extends AbstractDao<UserrEntity> {
    public UserDao() {
        super(UserrEntity.class);
    }

    public UserrEntity getUser(long card_number) {
        EntityManager em = JPAutil.getEMF().createEntityManager();
        try {
            return em.createQuery("SELECT u FROM UserrEntity u WHERE u.cardNumber = :cardNum", UserrEntity.class)
                    .setParameter("cardNum", card_number)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public UserrEntity changeEmail(Integer id, String newEmail) {
        EntityManager em = JPAutil.getEMF().createEntityManager();
        em.getTransaction().begin();
        UserrEntity userToChangeEmail = super.get(id);
        userToChangeEmail.seteMail(newEmail);
        super.update(userToChangeEmail);
        em.getTransaction().commit();
        em.close();
        return userToChangeEmail;
    }

//    START TRANSACTION ISOLATION LEVEL REPEATABLE READ;
//    -- Uživatel zkontroluje dostupnost
//    SELECT * FROM Car WHERE car_id = 1 AND availability = TRUE;
//    -- Uživatel provede rezervaci (aktualizuje dostupnost)
//    UPDATE Car SET availability = FALSE WHERE car_id = 1;
//    UPDATE Userr SET car_id = 1 WHERE account_id = 2;
//    COMMIT;
    @Transactional
    public void transactionCP4(int user_id, int car_id, CarDao carDao) {
        EntityManager em = JPAutil.getEMF().createEntityManager();
        em.getTransaction().begin();
        CarEntity car = carDao.get(car_id);
        UserrEntity user = super.get(user_id);
        car.setAvailability(false);
        user.setCarId(car_id);
        em.getTransaction().commit();
    }
}
