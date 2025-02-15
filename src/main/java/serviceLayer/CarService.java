package serviceLayer;

import daoLayer.CarDao;
import entities.CarEntity;
import entities.UserrEntity;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;

public class CarService {
    private final CarDao carDao;

    public CarService(CarDao carDao) {
        this.carDao = carDao;
    }

    @Transactional
    public CarEntity getCar(String spz, String vin) {
        return carDao.getCar(spz, vin);
    }

    @Transactional
    public void createCar(int location_id, String spz, String vin, String brand, String model, boolean availability, BigDecimal fare) {
        CarEntity car = new CarEntity();
        car.setLocationId(location_id);
        car.setSpz(spz);
        car.setVin(vin);
        car.setBrand(brand);
        car.setModel(model);
        car.setAvailability(availability);
        car.setFare(fare);
        if (this.getCar(spz, vin) == null) {
            carDao.persist(car);
            System.out.println("\nNew record has been added: Car[location_id : " + location_id +
                    ", spz: " + spz +
                    ", vin: " + vin +
                    ", brand: " + brand +
                    ", model: " + model +
                    ", availability: " + availability +
                    ", fare: " + fare + "].");
        } else {
            System.err.println("\nCar[spz: " + spz + ", vin: " + vin + "] already exists.\n");
        }
    }

    @Transactional
    public void remove(CarEntity car) {
        carDao.remove(car);
        System.out.println("Record Car[spz: + " + car.getSpz() + ", vin: " + car.getVin() + "] has been deleted.");

    }
    @Transactional
    public void removeById(int id) {
        CarEntity car = carDao.get(id);
        carDao.remove(car);
        System.out.println("Record Car[spz: + " + car.getSpz() + ", vin: " + car.getVin() + "] has been deleted.");
    }

    @Transactional
    public CarEntity changeAvailability(int id) {
        boolean oldAvailability = carDao.get(id).getAvailability();
        CarEntity ret = carDao.changeAvailability(id);
        System.out.println("\nCar[id: " + id + ", spz: " + ret.getSpz() + ", vin: " + ret.getVin() + "] availability changed from \"" + oldAvailability + "\" to \"" + ret.getAvailability() + "\".");
        return ret;
    }
}
