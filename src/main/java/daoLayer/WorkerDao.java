package daoLayer;

import entities.UserrEntity;
import entities.WorkerEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;

public class WorkerDao extends AbstractDao<WorkerEntity> {
    public WorkerDao() {
        super(WorkerEntity.class);
    }

    public WorkerEntity getWorker(long insurance_num, String pos) {
        EntityManager em = JPAutil.getEMF().createEntityManager();
        try {
            return em.createQuery("SELECT w FROM WorkerEntity w WHERE w.insuranceNumber = :InsNum AND w.position = :Pos", WorkerEntity.class)
                    .setParameter("InsNum", insurance_num)
                    .setParameter("Pos", pos)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

}
