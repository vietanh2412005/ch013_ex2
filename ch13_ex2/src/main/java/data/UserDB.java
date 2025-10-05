package data;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import model.User;

public class UserDB {

    // ---------------------------
    // 1️⃣ Thêm user
    // ---------------------------
    public static void insert(User user) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {
            trans.begin();
            em.persist(user);
            trans.commit();
        } catch (Exception e) {
            System.out.println("❌ Insert error: " + e.getMessage());
            if (trans.isActive()) trans.rollback();
        } finally {
            em.close();
        }
    }

    // ---------------------------
    // 2️⃣ Cập nhật user
    // ---------------------------
    public static void update(User user) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {
            trans.begin();
            em.merge(user);
            trans.commit();
        } catch (Exception e) {
            System.out.println("❌ Update error: " + e.getMessage());
            if (trans.isActive()) trans.rollback();
        } finally {
            em.close();
        }
    }

    // ---------------------------
    // 3️⃣ Xóa user
    // ---------------------------
    public static void delete(User user) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {
            trans.begin();
            // ⚠ dùng id vì bảng thật dùng cột "id"
            User managed = em.find(User.class, user.getId());
            if (managed != null) {
                em.remove(managed);
            }
            trans.commit();
        } catch (Exception e) {
            System.out.println("❌ Delete error: " + e.getMessage());
            if (trans.isActive()) trans.rollback();
        } finally {
            em.close();
        }
    }

    // ---------------------------
    // 4️⃣ Tìm user theo email
    // ---------------------------
    public static User selectUserByEmail(String email) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String qString = "SELECT u FROM User u WHERE u.email = :email";
        TypedQuery<User> q = em.createQuery(qString, User.class);
        q.setParameter("email", email);

        try {
            return q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            System.out.println("❌ Select by email error: " + e.getMessage());
            return null;
        } finally {
            em.close();
        }
    }

    // ---------------------------
    // 5️⃣ Kiểm tra email tồn tại
    // ---------------------------
    public static boolean emailExists(String email) {
        return selectUserByEmail(email) != null;
    }

    // ---------------------------
    // 6️⃣ Lấy danh sách toàn bộ user
    // ---------------------------
    public static List<User> selectUsers() {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String qString = "SELECT u FROM User u ORDER BY u.id"; // ⚠ dùng 'id' đúng cột trong entity

        try {
            TypedQuery<User> q = em.createQuery(qString, User.class);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("❌ Select users error: " + e.getMessage());
            return null;
        } finally {
            em.close();
        }
    }
}
