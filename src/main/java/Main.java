import daoLayer.*;
import entities.*;
import serviceLayer.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {

//init
        AccountDao accountDao = new AccountDao();
        AccountService accountService = new AccountService(accountDao);

        UserDao userDao = new UserDao();
        UserService userService = new UserService(userDao);

        AdminDao adminDao = new AdminDao();
        AdminService adminService = new AdminService(adminDao);

        WorkerDao workerDao = new WorkerDao();
        WorkerService workerService = new WorkerService(workerDao);

        CarDao carDao = new CarDao();
        CarService carService = new CarService(carDao);

/*
Create new records ----------------------------------------------------------------------------------------------------
 */

    // INHERITANCE:

        // -- user creation

        usersInfoPrintout(userService);
        String name = "name";
        String surname = "surname";
        String email = "namsur@gmail.com";
        int car_id = 1392;
        long card_number = 9999222274654444L;
        short conduct_summary = (short) 6666;
        userService.createUser(name, surname, email, car_id, card_number, conduct_summary);
        UserrEntity user = userService.getUser(card_number);
        usersInfoPrintout(userService);
//
//        // -- admin creation
//        System.out.println("\n\n---------------------------------------------------------------------------------------------------creating admin accounts---------------------------------------------------------------------------------------------------");
        adminsInfoPrintout(adminService);
        String name1 = "adminName";
        String surname1 = "admingSurname";
        String email1 = "adm1@gmail.com";
        Short work_experience = (short) 5;
        adminService.createAdmin(name1, surname1, email1, work_experience);
        AdminnEntity admin = adminService.getAdmin(name1, surname1, email1);
        adminsInfoPrintout(adminService);


    // MANY-TO-MANY:
        // new worker "Driver"
        int adminInstructor_id = 5;
        long insurance_num = 10000000000L;
        String position = "Driver";
        BigDecimal hourly_rate = BigDecimal.valueOf(280.0);
        Integer senior_id = null;
        workerService.create(adminInstructor_id, insurance_num, position, hourly_rate, senior_id);
        WorkerEntity driverWorker = workerService.getWorker(insurance_num, position);
//
//        // new worker "Loader"
//        int adminInstructor_id1 = 6;
        long insurance_num1 = 20000000000L;
        String position1 = "Loader";
//        BigDecimal hourly_rate1 = BigDecimal.valueOf(480.0);
//        Integer senior_id1 = driverWorker.getWorkerId();
//        workerService.create(adminInstructor_id1, insurance_num1, position1, hourly_rate1, senior_id1);
        WorkerEntity loaderWorker = workerService.getWorker(insurance_num1, position1);
//
        // new car "Ferrari Daytona"
        int location_id = 99;
        String spz = "DBS0DBS";
        String vin = "xxxy24tk9sm7mb5sq";
        String brand = "Ferrari";
        String model = "Daytona";
        boolean availability = true;
        BigDecimal fare = BigDecimal.valueOf(15);
        carService.createCar(location_id, spz, vin, brand, model, availability, fare);
        CarEntity ferrariDaytona = carService.getCar(spz, vin);
//
        System.out.println("\n\n---------------------------------------------------------------------------------------------------Cars maintained by worker [before adding]---------------------------------------------------------------------------------------------------");
        maintainedCarsOfWorkerPrintout(loaderWorker);
        System.out.println("Adding " + ferrariDaytona.getBrand() + " to " + loaderWorker.getPosition());
        workerService.addCarToMaintain(loaderWorker, ferrariDaytona);
        System.out.println("\n\n---------------------------------------------------------------------------------------------------Cars maintained by worker [after adding]---------------------------------------------------------------------------------------------------");
        maintainedCarsOfWorkerPrintout(loaderWorker);
//
        carService.remove(ferrariDaytona);
        System.out.println("\n\n---------------------------------------------------------------------------------------------------Cars maintained by worker [before removing]---------------------------------------------------------------------------------------------------");
        System.out.println("Removing " + ferrariDaytona.getBrand() + " from " + loaderWorker.getPosition());
        maintainedCarsOfWorkerPrintout(loaderWorker);
        if (ferrariDaytona != null) {
            workerService.removeCarFromMaintained(loaderWorker, ferrariDaytona);
        }
        System.out.println("\n\n---------------------------------------------------------------------------------------------------Cars maintained by worker [after removing]---------------------------------------------------------------------------------------------------");
        maintainedCarsOfWorkerPrintout(loaderWorker);

        // TRANSACTION FROM CP4
//        int user_id = 2;
//        int car_id = 1;
//        if (!carDao.get(car_id).getAvailability()) {
//            carService.changeAvailability(car_id);
//        }
//        userService.transactionCP4(user_id, car_id, carDao);

/*
Edit existing records ----------------------------------------------------------------------------------------------------
 */

//        int newUserId = 9;
//        String newEmail = "jesus@gmail.com";
//        userService.changeEmail(newUserId, newEmail);
//
//        carService.changeAvailability(ferrariDaytona.getCarId());
/*
Delete existing records ----------------------------------------------------------------------------------------------------
 */

        System.out.println("\n\n---------------------------------------------------------------------------------------------------deleting user accounts---------------------------------------------------------------------------------------------------");
        usersInfoPrintout(userService);
        if (user != null) {
            userService.remove(user);
        }
        usersInfoPrintout(userService);
//
        System.out.println("\n\n---------------------------------------------------------------------------------------------------deleting admin accounts---------------------------------------------------------------------------------------------------");
        adminsInfoPrintout(adminService);
        if (admin != null) {
            adminService.remove(admin);
        }
        adminsInfoPrintout(adminService);

        if (loaderWorker != null) {
            workerService.remove(loaderWorker);
        }
        if (driverWorker != null) {

            workerService.remove(driverWorker);
        }
        if (ferrariDaytona != null) {
            carService.remove(ferrariDaytona);
        }
    }

    public static void usersInfoPrintout(UserService userService) {
        List<UserrEntity> allUsers = userService.getALlUsers();
        System.out.println("\nExisting users' info: ");
        System.out.printf("%-5s %-10s %-15s %-20s %-10s %-20s %-20s\n", "id", "name", "surname", "e-mail", "car_id", "card_number", "conduct_summary");
        System.out.printf("%-5s %-10s %-15s %-20s %-10s %-20s %-20s\n", "----", "----------", "---------------", "--------------------", "--------", "--------------------" ,"--------------------");
        for (UserrEntity user : allUsers) {
            System.out.printf("%-5d %-10s %-15s %-20s %-10d %-20d %-20d\n",
                    user.getAccountId(),
                    user.getName(),
                    user.getSurname(),
                    user.geteMail(),
                    user.getCarId(),
                    user.getCardNumber(),
                    user.getConductSummary());
        }
    }

    public static void adminsInfoPrintout(AdminService adminService) {
        List<AdminnEntity> allAdmins = adminService.getALlAdmins();
        System.out.println("\nExisting admins' info: ");
        System.out.printf("%-5s %-10s %-15s %-20s %-15s\n", "id", "name", "surname", "e-mail", "work experience");
        System.out.printf("%-5s %-10s %-15s %-20s %-15s\n", "----", "----------", "---------------", "--------------------", "--------------------");
        for (AdminnEntity admin: allAdmins) {
            System.out.printf("%-5d %-10s %-15s %-20s %-15d\n",
                    admin.getAccountId(),
                    admin.getName(),
                    admin.getSurname(),
                    admin.geteMail(),
                    admin.getWorkExperience());
        }
    }

    public static void maintainedCarsOfWorkerPrintout(WorkerEntity worker) {
        if (worker.getMaintainedCars().isEmpty()) {
            System.out.println("Worker [id: " + worker.getWorkerId() + ", insurance_number: " + worker.getInsuranceNumber() + ", position: " + worker.getPosition() + "] does not maintain any cars.");
        } else {
            System.out.print("Worker [id: " + worker.getWorkerId() + ", insurance_number: " + worker.getInsuranceNumber() + ", position: " + worker.getPosition() + "]. List of maintenanced cars: ");
            for (CarEntity car : worker.getMaintainedCars()) {
                System.out.printf("%s ", car.getBrand());
            }
            System.out.println();
        }
    }

}
