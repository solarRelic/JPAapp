package serviceLayer;

import daoLayer.AccountDao;
import entities.AccountEntity;
import jakarta.transaction.Transactional;

public class AccountService {
    private final AccountDao accountDao;


    public AccountService(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Transactional
    AccountEntity getAccount(String name, String surname, String email) {
        return accountDao.getAccount(name, surname, email);
    }

    @Transactional
    public void createAccount(String name, String surname, String e_mail) {
        AccountEntity account = new AccountEntity();
        account.setName(name);
        account.setSurname(surname);
        account.seteMail(e_mail);
        if (this.getAccount(name, surname, e_mail) == null) {
            accountDao.persist(account);
            System.out.println("New Account[: " +
                    ", name: " + name +
                    ", surname: " + surname +
                    ", e-mail: " + e_mail +
                    "] record has been added.");
        } else {
            System.err.printf("Account is already in the database.");
        }
    }


}
