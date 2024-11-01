package persistencia;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import modelo.Reaccion;

public class ReaccionJpaController {

    private final EntityManagerFactory emf;

    public ReaccionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public ReaccionJpaController() {
        emf = Persistence.createEntityManagerFactory("JavaWebLasHuequitas");
    }


    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Reaccion reaccion) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(reaccion);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public boolean hasUserReactedToResena(Long userId, Long resenaId) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createQuery("SELECT r FROM Reaccion r WHERE r.usuario.id = :userId AND r.resena.id = :resenaId");
            query.setParameter("userId", userId);
            query.setParameter("resenaId", resenaId);
            return !query.getResultList().isEmpty(); // Devuelve true si hay una reacción
        } finally {
            em.close();
        }
    }

}