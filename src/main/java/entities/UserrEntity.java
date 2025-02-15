package entities;

import jakarta.persistence.*;

@Entity
@jakarta.persistence.Table(name = "userr", schema = "public", catalog = "batueego")
public class UserrEntity extends AccountEntity{
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
    @Column(name = "car_id")
    private int carId;

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    @Basic
    @Column(name = "card_number")
    private long cardNumber;

    public long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(long cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Basic
    @Column(name = "conduct_summary")
    private short conductSummary;

    public short getConductSummary() {
        return conductSummary;
    }

    public void setConductSummary(short conductSummary) {
        this.conductSummary = conductSummary;
    }

}
