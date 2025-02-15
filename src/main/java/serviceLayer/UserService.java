package serviceLayer;

import daoLayer.CarDao;
import daoLayer.UserDao;
import entities.AccountEntity;
import entities.UserrEntity;
import jakarta.transaction.Transactional;

import javax.sound.midi.Soundbank;
import java.util.List;

public class UserService {
    private final UserDao userDao;


    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Transactional
    public UserrEntity getUser(long card_number) {
        return userDao.getUser(card_number);
    }

    @Transactional
    public void createUser(String name, String surname, String email, int car_id, long card_number, short conduct_summary) {
        UserrEntity user = new UserrEntity();
        user.setName(name);
        user.setSurname(surname);
        user.seteMail(email);
        user.setCarId(car_id);
        user.setCardNumber(card_number);
        user.setConductSummary(conduct_summary);
        if (this.getUser(card_number) == null) {
            userDao.persist(user);
            System.out.println("\nNew record has been added: User[car_id: " + car_id +
                                                ", card_number: " + card_number +
                                                ", conduct_summary: " + conduct_summary + "].");
        } else {
            System.err.println("\nUser[card_number: " + card_number + "] already exists.");
        }
    }

    @Transactional
    public void remove(UserrEntity user) {
        userDao.remove(user);
        System.out.println("\nRecord User[card_number: " + user.getCardNumber() + "] has been deleted.");

    }
    @Transactional
    public void removeById(int id) {
        UserrEntity user = userDao.get(id);
        userDao.remove(user);
        System.out.println("\nRecord User[card_number: " + user.getCardNumber() + "] has been deleted.");

    }

    @Transactional
    public UserrEntity changeEmail(int id, String newEmail) {
        String oldEmail = userDao.get(id).geteMail();
        UserrEntity ret = null;
        if (!oldEmail.equals(newEmail)) {
            ret = userDao.changeEmail(id, newEmail);
            System.out.println("\nUser[id: " + id + "] email changed from \"" + oldEmail + "\" to \"" + newEmail + "\".");
        } else {
            System.err.println("\nUser[id: " + id + "] Trying to change an old email " + "(" + oldEmail + ")" + " to an identical new one " + "(" + newEmail + ").");
        }
        return ret;
    }

    @Transactional
    public List<UserrEntity> getALlUsers() {
        return userDao.getAll();
    }

    @Transactional
    public void transactionCP4(int user_id, int car_id, CarDao carDao) {
        userDao.transactionCP4(user_id, car_id, carDao);
        System.out.println("Transaction of reserving the car[id: " + car_id + "] by user[id: " + user_id + "] has been performed");

    }
}

