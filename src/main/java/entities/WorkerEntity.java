package entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Entity
@jakarta.persistence.Table(name = "worker", schema = "public", catalog = "batueego")
public class WorkerEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @jakarta.persistence.Column(name = "worker_id")
    private int workerId;

    public int getWorkerId() {
        return workerId;
    }

    public void setWorkerId(int workerId) {
        this.workerId = workerId;
    }

    @Basic
    @Column(name = "account_id")
    private int accountId;

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    @Basic
    @Column(name = "insurance_number")
    private long insuranceNumber;

    public long getInsuranceNumber() {
        return insuranceNumber;
    }

    public void setInsuranceNumber(long insuranceNumber) {
        this.insuranceNumber = insuranceNumber;
    }

    @Basic
    @Column(name = "position")
    private String position;

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Basic
    @Column(name = "hourly_rate")
    private BigDecimal hourlyRate;

    public BigDecimal getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(BigDecimal hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    @Basic
    @Column(name = "senior_id")
    private Integer seniorId;

    public Integer getSeniorId() {
        return seniorId;
    }

    public void setSeniorId(Integer seniorId) {
        this.seniorId = seniorId;
    }

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(
            name = "workermaintainscar",
            joinColumns = @JoinColumn(name = "worker_id"),
            inverseJoinColumns = @JoinColumn(name = "car_id"))
    private Set<CarEntity> maintainedCars = new HashSet<>();
    public void addCar(CarEntity car) {
        maintainedCars.add(car);
        car.getMaintainers().add(this);
        System.out.println(car.getBrand() + " has been added to the list of maintenaced cars of Worker[id: " + this.getWorkerId() + "].");
    }
    public void removeCar(CarEntity car) {
        maintainedCars.remove(car);
        car.getMaintainers().remove(this);
        System.out.println(car.getBrand() + " has been removed.");
    }
    public Set<CarEntity> getMaintainedCars() {
        return maintainedCars;
    }

}
