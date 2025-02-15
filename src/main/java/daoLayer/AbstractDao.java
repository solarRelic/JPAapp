package daoLayer;

import jakarta.persistence.*;

import java.util.List;

public abstract class AbstractDao<T> {

//    @PersistenceContext
//    private EntityManager em;
    private Class<T> curClass;


    public AbstractDao(Class<T> curClass) {
        this.curClass = curClass;
    }

    public T get(int id) {
        EntityManager em = JPAutil.getEMF().createEntityManager();
        T ret = em.find(curClass, id);
        em.close();
        return ret;
    }

    public List<T> getAll() {
        EntityManager em = JPAutil.getEMF().createEntityManager();
        List<T> ret = em.createQuery("SELECT e FROM " + curClass.getName() + " AS e", curClass)
                        .getResultList();
        em.close();
        return ret;
    }

    public void persist(T entity) {
        EntityManager em = JPAutil.getEMF().createEntityManager();

        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();

        em.close();
    }

    public T update (T entity) {
        EntityManager em = JPAutil.getEMF().createEntityManager();
        EntityTransaction et = em.getTransaction();

        et.begin();
        T ret = em.merge(entity);
        et.commit();

        return ret;
    }

    public void remove(T entity) {
        EntityManager em = JPAutil.getEMF().createEntityManager();
        EntityTransaction et = em.getTransaction();

        et.begin();
        em.remove(em.contains(entity) ? entity : em.merge(entity));
        et.commit();
    }

//    public void removeById(int id) {
//        EntityManager em = JPAutil.getEMF().createEntityManager();
//        EntityTransaction et = em.getTransaction();
//
//        et.begin();
//        T entityToRemove = this.get(id);
//        if (entityToRemove != null) {
//            em.remove(entityToRemove);
//        }
//        et.commit();
//    }
}
