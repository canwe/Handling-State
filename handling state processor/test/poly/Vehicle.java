package poly;

public class Vehicle {

    int wheels = 4;

    public void displayDetails() {
        System.out.println("displayDetails in Vehicle");
    }

}

class Truck extends Vehicle {

    int wheels = 10;

    public void displayDetails() {
        System.out.println("displayDetails in Truck");
    }

}                                                  

class Tanker extends Truck {
    
    int wheels = 6;

    public Tanker() {
        System.out.println("super.wheels is " + super.wheels);
        System.out.println("wheels in Vehicle is " + ((Vehicle) this).wheels);
        ((Vehicle) this).displayDetails();
    }

    public void displayDetails() {
        super.displayDetails();
        System.out.println("displayDetails in Tanker");
    }

    public static void main(String[] args) {
        Tanker t = new Tanker();
        ((Vehicle)t).displayDetails();
    }
}
