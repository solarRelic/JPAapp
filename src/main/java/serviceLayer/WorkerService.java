package serviceLayer;

import daoLayer.WorkerDao;
import entities.CarEntity;
import entities.UserrEntity;
import entities.WorkerEntity;
import jakarta.transaction.Transactional;

import javax.sound.midi.Soundbank;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WorkerService {
    private WorkerDao workerDao;


    public WorkerService(WorkerDao workerDao) {
        this.workerDao = workerDao;
    }

    @Transactional
    public WorkerEntity getWorker(long insurance_num, String pos) {
        return workerDao.getWorker(insurance_num, pos);
    }

    @Transactional
    public void create(int account_id, long insurance_num, String position, BigDecimal hourly_rate, Integer senior_id) {
        WorkerEntity worker = new WorkerEntity();
        worker.setAccountId(account_id);
        worker.setInsuranceNumber(insurance_num);
        worker.setPosition(position);
        worker.setHourlyRate(hourly_rate);
        worker.setSeniorId(senior_id);
        if (this.getWorker(insurance_num, position) == null) {
            workerDao.persist(worker);
            String senior = (senior_id == null) ? "no senior" : String.valueOf(senior_id);
            System.out.println("\nNew record has been added: Worker[adminInstructor_id: " + account_id +
                                                            ", insurance_number: " + insurance_num +
                                                            ", positoin: " + position +
                                                            ", hourly rate: " + hourly_rate +
                                                            ", senior: " + senior + "].");
        } else {
            System.err.println("\nWorker[insurance number: " + insurance_num +
                                        ", position: " + position + "] already exists.");
        }
    }

    @Transactional
    public void remove(WorkerEntity worker) {
        workerDao.remove(worker);
        System.out.println("\nRecord Worker[insurance number: " + worker.getInsuranceNumber() +
                                            ", position: " + worker.getPosition() + "] has been deleted.");
    }
    @Transactional
    public void removeById(int id) {
        WorkerEntity worker = workerDao.get(id);
        workerDao.remove(worker);
        System.out.println("\nRecord Worker[id: " + id + ", insurance number: " + worker.getInsuranceNumber() + ", position: " + worker.getPosition() + "] has been deleted.");

    }

    @Transactional
    public void addCarToMaintain(WorkerEntity worker, CarEntity car) {
        Set<CarEntity> maintenacedCars = worker.getMaintainedCars();
        List<String> brandsMaintenaced = new ArrayList<>();
        maintenacedCars.forEach(x -> brandsMaintenaced.add(x.getBrand()));
        if (brandsMaintenaced.contains(car.getBrand())) {
            System.err.println("Worker[id: " + worker.getWorkerId() + "] already has Car[id: " + car.getCarId() + "] in its maintenance.");
            return;
        }
        worker.addCar(car);
        workerDao.update(worker);
        System.out.println("Car[id: " + car.getCarId() + ", brand: " + car.getBrand() + "] has been added to the list of maintained cars of Worker[id: " + worker.getWorkerId() + "].");
    }

    @Transactional
    public void removeCarFromMaintained(WorkerEntity worker, CarEntity car) {
        Set<CarEntity> maintenacedCars = worker.getMaintainedCars();
        List<String> brandsMaintenaced = new ArrayList<>();
        maintenacedCars.forEach(x -> brandsMaintenaced.add(x.getBrand()));
        if (brandsMaintenaced.contains(car.getBrand())) {
            worker.removeCar(car);
            workerDao.update(worker);
            System.out.println("Car[id: " + car.getCarId() + ", brand: " + car.getBrand() + "] has been removed from the list of maintained cars of Worker[id: " + worker.getWorkerId() + "].");
        } else if (worker.getMaintainedCars().isEmpty()) {
            System.err.println("Couldn't remove Car[id: " + car.getCarId() + ", brand: " + car.getBrand() + "] from the list of maintained cars of Worker[id: " + worker.getWorkerId() + "]. Set of worker's maintained cars is empty.");
        } else {
            System.err.println("Couldn't remove Car[id: " + car.getCarId() + ", brand: " + car.getBrand() + "] from the list of maintained cars of Worker[id: " + worker.getWorkerId() + "]. Set of worker's maintained cars does not have the car.");
        }
    }

}
