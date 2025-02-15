package serviceLayer;

import daoLayer.AdminDao;
import daoLayer.UserDao;
import entities.AdminnEntity;
import entities.UserrEntity;
import jakarta.transaction.Transactional;

import java.util.List;

public class AdminService {

    private final AdminDao adminDao;

    public AdminService(AdminDao adminDao) {
        this.adminDao = adminDao;
    }


    @Transactional
    public AdminnEntity getAdmin(String name, String surname, String email) {
        return adminDao.getAdmin(name, surname, email);
    }

    @Transactional
    public void createAdmin(String name, String surname, String email, short work_exp) {
        AdminnEntity admin= new AdminnEntity();
        admin.setName(name);
        admin.setSurname(surname);
        admin.seteMail(email);
        if (this.getAdmin(name, surname, email) == null) {
            adminDao.persist(admin);
            System.out.println("\nNew record has been added: Admin[name: " + name +
                    ", surname: " + surname +
                    ", email: " + email +
                    ", work experience: " + work_exp + "].");
        } else {
            System.err.println("\nAdmin[name : " + name + ", surname: " + surname + "] already exists.");
        }
    }

    @Transactional
    public void remove(AdminnEntity admin) {
        adminDao.remove(admin);
        System.out.println("\nRecord Admin[name : " + admin.getName() + ", surname: " + admin.getSurname() + "] has been deleted.");

    }
    @Transactional
    public void removeById(int id) {
        AdminnEntity admin = adminDao.get(id);
        adminDao.remove(admin);
        System.out.println("\nRecord Admin[name : " + admin.getName() + ", surname: " + admin.getSurname() + "] has been deleted.");
    }

    @Transactional
    public List<AdminnEntity> getALlAdmins() {
        return adminDao.getAll();
    }
}
