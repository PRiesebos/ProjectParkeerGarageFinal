package nl.hanze.t12.mvc.model;

import java.util.Random;

import static java.lang.Math.round;

/**
 * Dit is de klase Simulator die erft van AbstractModel.
 */
public class Simulator extends AbstractModel {

	private static final String AD_HOC = "1";
	private static final String PASS = "2";
	private static final String SUBC = "3";
    private Thread thread = null;
	private Parkeergarage parkeergarage;
	private CarQueue entranceCarQueue;
    private CarQueue entrancePassQueue;
    private CarQueue entranceSubcQueue;
    private CarQueue paymentCarQueue;
    private CarQueue exitCarQueue;

    private int day = 0;
    private int hour = 0;
    private int minute = 0;

    private int tickPause = 100;

    int weekDayArrivals= 100; // average number of arriving cars per hour
    int weekendArrivals = 200; // average number of arriving cars per hour
    int weekDayPassArrivals= 50; // average number of arriving cars per hour
    int weekendPassArrivals = 5; // average number of arriving cars per hour

    int enterSpeed = 3; // number of cars that can enter per minute
    int paymentSpeed = 7; // number of cars that can pay per minute
    int exitSpeed = 5; // number of cars that can leave per minute

    /**
     * de constructor
     */
    public Simulator() {
        entranceCarQueue = new CarQueue();
        entrancePassQueue = new CarQueue();
        entranceSubcQueue = new CarQueue();
        paymentCarQueue = new CarQueue();
        exitCarQueue = new CarQueue();
        parkeergarage = new Parkeergarage(3,6,30);
    }

    public Parkeergarage getParkeergarage() {
        return parkeergarage;
    }

    /**
     * deze methode zorgt ervoor dat het in gang wordt gezet.
     */
    public void run() {
        if(thread==null){
            thread =new Thread (new Runnable() {

                  @Override
                  public void run() {
                       for (int i = 0; i < 10000; i++) {
                           tick();
                       }
                   }
            });
            thread.start();
        }
    }

    /**
     * Deze methode stopt de thread en kijkt eerst of er al een thread is gemaakt.
     */
    public void stop(){
       if(thread == null) {

       }else {
           thread.stop();
           thread = null;
       }

    }

    /**
     * De methode reset de hele simulatie.
     */
    public void ResetCar(){
        parkeergarage.ResetCars();

    }

    /**
     * Geeft de totale bijgehouden tijd terug zodra de simulatie start.
     * @return
     */
    public String getTime() {
        return "Tijd: Days "+day+" : Hours "+hour+" : Minutes "+minute;
    }

    /**
     *Geeft de totale aantal vrije plekken weer.
     * @return
     */
    public String getOverig(){

        return  "Aantral vrije plekken: "+parkeergarage.getNumberOfOpenSpots();
    }

    /**
     * Geeft de totale aantal auto's in de uitgang wachtrij terug.
     * @return
     */
    public String getWachtrijExit(){
        return "Auto's in wachtrij uitgang: "+exitCarQueue.carsInQueue();

    }

    /**
     *Geeft de totale aantal auto's in de ingang wachtrij terug.
     * @return
     */
    public String getWachtrijIngang() {
        return "Auto's in wachtrij ingang: " + entranceCarQueue.carsInQueue();

    }

    /**
     * Geeft de totale aantal ingeparkeerde auto's terug.
     * @return
     */
    public String getAantalAutos(){
        int max = 540;
        max -= parkeergarage.getNumberOfOpenSpots();
        return "Aantal geparkeerde autos: "+max;

    }

    /**
     * Deze methode is bedoeld om het aantal abonees terug te geven. Dit werkt op het moment niet.
     * @return
     */
    public String getAbonnementHouders() {

        return "Auto's van abonnement houders: " ;
    }


    /**
     * Laat auto's 10 minuten de parkeergarage binnen.
     */
    public void runTien() {
        if(thread==null){
            thread =new Thread (new Runnable() {

                @Override
                public void run() {
                    for (int i = 1; i <= 10; i++) {
                        tick();
                    }
                }
            });
            thread.start();
        } else if (Thread.currentThread().isAlive()){
            thread =new Thread (new Runnable() {

                @Override
                public void run() {
                    for (int i = 1; i <= 10; i++) {
                        tick();
                    }
                }
            });
            thread.start();
        }
    }
    /**
     * Laat auto's 100 minuten de parkeergarage binnen.
     */
    public void runHonderd() {
        if(thread==null){
            thread =new Thread (new Runnable() {

                @Override
                public void run() {
                    for (int i = 1; i < 100; i++) {
                        tick();
                    }
                }
            });
            thread.start();
        } else if (Thread.currentThread().isAlive()){
            thread =new Thread (new Runnable() {

                @Override
                public void run() {
                    for (int i = 1; i <= 100; i++) {
                        tick();
                    }
                }
            });
            thread.start();
        }
        }

    public void tick() {
    	advanceTime();
    	handleExit();
    	parkeergarage.tick();
        notifyViews();
    	// Pause.
        try {
            Thread.sleep(tickPause);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    	handleEntrance();
    }

    private void advanceTime(){
        // Advance the time by one minute.
        minute++;
        while (minute > 59) {
            minute -= 60;
            hour++;
        }
        while (hour > 23) {
            hour -= 24;
            day++;
        }
        while (day > 6) {
            day -= 7;
        }

    }

    private void handleEntrance(){
    	carsArriving();
    	carsEntering(entrancePassQueue);
    	carsEntering(entranceCarQueue);
    	carsEntering(entranceSubcQueue);
    }
    
    public void handleExit(){
        carsReadyToLeave();
        carsPaying();
        carsLeaving();
    }



    
    private void carsArriving(){
    	int numberOfCars=getNumberOfCars(weekDayArrivals, weekendArrivals);
        addArrivingCars(numberOfCars, AD_HOC);    	
    	numberOfCars=getNumberOfCars(weekDayPassArrivals, weekendPassArrivals);
        addArrivingCars(numberOfCars, PASS);
        numberOfCars=getNumberOfCars(weekDayPassArrivals, weekendPassArrivals);
        addArrivingCars(numberOfCars, SUBC);
    }

    private void carsEntering(CarQueue queue){
        int i=0;
        // Remove car from the front of the queue and assign to a parking space.
    	while (queue.carsInQueue()>0 && 
    			parkeergarage.getNumberOfOpenSpots()>0 &&
    			i<enterSpeed) {
            Car car = queue.removeCar();
            Location freeLocation = parkeergarage.getFirstFreeLocation(car.getHasToPay());
            parkeergarage.setCarAt(freeLocation, car);
            i++;
        }
    }
    
    private void carsReadyToLeave(){
        // Add leaving cars to the payment queue.
        Car car = parkeergarage.getFirstLeavingCar();
        while (car!=null) {
        	if (car.getHasToPay()){
	            car.setIsPaying(true);
	            paymentCarQueue.addCar(car);
        	}
        	else {
        		carLeavesSpot(car);
        	}
            car = parkeergarage.getFirstLeavingCar();
        }
    }

    private void carsPaying(){
        // Let cars pay.
    	int i=0;
    	while (paymentCarQueue.carsInQueue()>0 && i < paymentSpeed){
            Car car = paymentCarQueue.removeCar();
            // TODO Handle payment.
            carLeavesSpot(car);
            i++;
    	}
    }
    
    private void carsLeaving(){
        // Let cars leave.
    	int i=0;
    	while (exitCarQueue.carsInQueue()>0 && i < exitSpeed){
            exitCarQueue.removeCar();
            i++;
    	}	
    }

    public int getNumberInEntranceQueue() {
        return entranceCarQueue.carsInQueue();
    }
    
    private int getNumberOfCars(int weekDay, int weekend){
        Random random = new Random();

        // Get the average number of cars that arrive per hour.
        int averageNumberOfCarsPerHour = day < 5
                ? weekDay
                : weekend;

        // Calculate the number of cars that arrive this minute.
        double standardDeviation = averageNumberOfCarsPerHour * 0.3;
        double numberOfCarsPerHour = averageNumberOfCarsPerHour + random.nextGaussian() * standardDeviation;
        return (int) round(numberOfCarsPerHour / 60);
    }
    
    private void addArrivingCars(int numberOfCars, String type){
        // Add the cars to the back of the queue.
    	switch(type) {
    	case AD_HOC: 
            for (int i = 0; i < numberOfCars; i++) {
            	entranceCarQueue.addCar(new AdHocCar());
            }
            break;
    	case PASS:
            for (int i = 0; i < numberOfCars; i++) {
            	entrancePassQueue.addCar(new ParkingPassCar());
            }
            break;

            case SUBC:
                for (int i = 0; i < numberOfCars; i++) {
                    entranceSubcQueue.addCar(new Subscriber());
                }
                break;
    	}
    }
    
    private void carLeavesSpot(Car car){
    	parkeergarage.removeCarAt(car.getLocation());
        exitCarQueue.addCar(car);
    }

}
