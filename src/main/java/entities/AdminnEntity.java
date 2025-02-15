package entities;

import jakarta.persistence.*;

@Entity
@jakarta.persistence.Table(name = "adminn", schema = "public", catalog = "batueego")
public class AdminnEntity extends AccountEntity{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @jakarta.persistence.Column(name = "account_id")
    private int accountId;

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    @Basic
    @Column(name = "work_experience")
    private short workExperience;

    public short getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(short workExperience) {
        this.workExperience = workExperience;
    }

}
