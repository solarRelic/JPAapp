package daoLayer;

import entities.AdminnEntity;
import entities.CarEntity;
import entities.UserrEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;

public class CarDao extends AbstractDao<CarEntity> {
    public CarDao() {
        super(CarEntity.class);
    }

    public CarEntity getCar(String spz, String vin) {
        EntityManager em = JPAutil.getEMF().createEntityManager();
        try {
            return em.createQuery("SELECT c FROM CarEntity c WHERE c.spz = :spz AND c.vin = :vin", CarEntity.class)
                    .setParameter("spz", spz)
                    .setParameter("vin", vin)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public CarEntity changeAvailability(Integer id) {
        EntityManager em = JPAutil.getEMF().createEntityManager();
        em.getTransaction().begin();
        CarEntity carAvailabilityToChange = super.get(id);
        boolean oldAvailability = carAvailabilityToChange.getAvailability();
        carAvailabilityToChange.setAvailability(!oldAvailability);
        super.update(carAvailabilityToChange);
        em.getTransaction().commit();
        em.close();
        return carAvailabilityToChange;
    }

}

