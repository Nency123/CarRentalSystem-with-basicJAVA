import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

 class Car {
    
    private String carId;
    private String brand;
    private String model;
    private double basePricePerDay;
    private boolean isAvailable;

   public Car(String carId, String brand, String model, double basePricePerDay){
     this.carId = carId;
     this.brand = brand;
     this.model = model;
     this.basePricePerDay = basePricePerDay;
     this.isAvailable = true;
   }

   public String getCarId(){
     return brand;
   }
   public String getBrand(){
     return brand;
   }
   public String getModel(){
     return model;
   }
   public double calculatePrice(int rentalDays){
     return basePricePerDay * rentalDays;
   }
   public boolean isAvailable(){
     return isAvailable;
   }
   public void rent(){
     isAvailable = false;
   }
   public void returnCar(){
    isAvailable = true;
    }
}

class Rental {
    private Car car;
    private Customer customer;
    private int days;

    public Rental(Car car,Customer customer, int days){
       this.car = car;
       this.customer = customer;
       this.days = days;

    }
    public Car getCar(){
        return car;
    }
    public Customer getCustomer(){
        return customer;
    }
    public int getDays(){
        return days;
    }
}
 class Customer {
    private String name;
    private String customerId;

    public Customer(String customerId,String name){
        this.customerId = customerId;
        this.name = name;
    }

    public String getCustomerId(){
       
        return customerId;
    }
    public String getname(){
        return name;
    }
    
}

public class CarRentalSystem {
//List<datatype> - here we take classname as datatype bcoz we need all properties of particular class.
  private List<Car> cars;
  private List<Customer> customers;
  private List<Rental> rentals;

  public CarRentalSystem(){
       cars = new ArrayList<>();
       customers = new ArrayList<>();
       rentals = new ArrayList<>();
  }
  public void addCar(Car car){
    cars.add(car);
  }
  public void addCustomer(Customer customer){
    customers.add(customer);
  }
  
  public void rentCar(Car car, Customer customer,int rentalDays){
    if(car.isAvailable()){
      car.rent();
      rentals.add(new Rental(car, customer, 0));
    }
    else{
      System.out.println("car is not available for rent");
    }
  }

  public void returnCar(Car car){
    // car ni details mali gai ne returnCar method call thai gai
      car.returnCar();
      //Rental 
      Rental rentToRemove = null;
      // list ma car find karavi foreach ni help thi
      for(Rental rental : rentals){
        // rent thayeli car mali gai
        if(rental.getCar() == car){
          //rentToRemove list ma lavya
          rentToRemove = rental;//now rental is null.search thayeli car no object akho set thai jase.
          break;
        }
      }
      // if rentToRemove is not null then it has car object
      if(rentToRemove != null){
        // rentToRemove ne list mathi remove karavanu
        rentals.remove(rentToRemove);
      } else {
        System.out.println("car was not return");
      }
  }
  public void menu(){
    System.out.println("===== Car rental System =====");
    
    do {
      System.out.println("press 1 --> rent a car");
       System.out.println("press 2 --> return a car");
       System.out.println("press 3 --> exit"); 
       Scanner sc = new Scanner(System.in);
       System.out.println("enter your choise");
       int ch=Integer.parseInt(sc.nextLine());
     
       switch(ch){
        case 1: 
            
      System.out.println("== Rent a car ==");
      System.out.println("enter your name");
      String customerName = sc.nextLine();
      System.out.println("available cars");
      for(Car car: cars){
        if(car.isAvailable()){
          System.out.println(car.getCarId() + " -- " + car.getBrand() + " -- " +car.getModel());
        }
      }
      System.out.println("enter the car Id that you want to rent");
      String carId = sc.nextLine();
      System.out.println("Enter the number of days ");
      int rentalDays = sc.nextInt();
      sc.nextLine();
      Customer cu = new Customer("CUS" + (customers.size()+1), customerName);
      addCustomer(cu);

      Car selectedCar = null;
      for(Car car : cars){
        if(car.getCarId().equals(carId) && car.isAvailable()){
          selectedCar = car;
          break;
        }
      }
      if(selectedCar != null){
        Double totalPrice = selectedCar.calculatePrice(rentalDays);
        System.out.println("====== Rental Information ======"); 
        System.out.println("customer id is: " + cu.getCustomerId());
        System.out.println("customer name is: " + cu.getname());
        System.out.println("car: " + selectedCar);
        System.out.println("rental days : " +rentalDays);
        System.out.printf(" total price: $%.2f%n",totalPrice);

        System.out.println("confirm rental Y/N");
        String Confirm = sc.nextLine();

        if(Confirm.equalsIgnoreCase("Y")){
              rentCar(selectedCar, cu, rentalDays);
              System.out.println("car rented sucessfully");
        }
        else{
          System.out.println("rented cancel");
        }
       }
      else{
        System.out.println("invalid input or selection");
      }
      case 2:
       System.out.println("===== return a car =====");
       System.out.println("Enter a carId that you want to return");
       carId = sc.nextLine();
       
       Car carToReturn = null;
       for(Car car : cars){
        if(car.getCarId().equals(carId) && !car.isAvailable()){
           carToReturn = car;
           break;
        }
       }
       if(carToReturn != null){
         Customer customer = null;
         for(Rental rental : rentals){
          if(rental.getCar() == carToReturn){
            customer = rental.getCustomer();
            break;
          }
         }
         if(customer != null){
          returnCar(carToReturn);
          System.out.println("car returned sucessfully"+customer.getname());
        }
         else{
          System.out.println("car is not rented or missing");
         }
       }
       else{
        System.out.println("invalid userId and car");
       }

      case 3:
          System.exit(0);
    }
   } while (true);
    
  }
    public static void main(String[] args){
        CarRentalSystem crs = new CarRentalSystem();
        
        Car car1 = new Car("CS01", "Toyota", "RAW", 120.00);
        Car car2 = new Car("CS02","Honda","CR-V",150.00);
        Car car3 = new Car("CS03" , "Hyndai", "elentra" , 125.00);
  
        crs.addCar(car1);
        crs.addCar(car2);
        crs.addCar(car3);

        crs.menu();
      }

    }
