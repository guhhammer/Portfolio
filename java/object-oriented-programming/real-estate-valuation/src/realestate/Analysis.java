package realestate;

/** Polymorphism exercise: the same call behaves differently for a House, an Apartment and a generic Property.
 *  Object-Oriented Programming course, PUCPR (2018). */
public class Analysis {

    public static double analyse(Property p, double percent) {
        if (p instanceof House) {
            System.out.print("House: ");
            return p.appreciate(percent);
        } else if (p instanceof Apartment) {
            System.out.print("Apartment: ");
            return p.depreciate(percent);
        } else {
            System.out.print("Property: ");
            return p.appreciate(percent);
        }
    }

    public static void main(String[] args) {
        House x = new House(100.0);
        Apartment z = new Apartment(400.0);

        System.out.println("L1: " + x.appreciate(20));
        System.out.println("L2: " + x.depreciate(20));
        System.out.println("L3: " + (z.appreciate(20) + z.depreciate(20)));

        System.out.println("L4: " + analyse(x, 10));
        System.out.println("L5: " + analyse(z, 30));
        Property w = new House(200.0);
        System.out.println("L6: " + analyse(w, 50));

        Property y = new Apartment(200.0);
        System.out.println("L7: " + analyse(y, 50));

        w = x;
        System.out.println("L8: " + analyse(w, 50));
        w = z;
        System.out.println("L9: " + analyse(w, 50));
        w = y;
        System.out.println("L10: " + analyse(w, 50));
    }
}
