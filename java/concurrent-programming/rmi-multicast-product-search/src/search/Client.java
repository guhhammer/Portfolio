package search;

import java.rmi.Naming;
import java.util.Scanner;

/** Customer console: looks up the RMI object and searches for products by name. */
public class Client {

    public static void main(String[] args) {
        String server = "localhost:" + Server.RMI_PORT;
        try {
            IConsumer client = (IConsumer) Naming.lookup("rmi://" + server + "/ClientConsole");
            System.out.print("\n\nClient is connected.\n\n");

            String menu = "\n\nClient console:\n"
                        + "\n\tOption (1): search for a product."
                        + "\n\tOption (2): quit.\n\n";
            Scanner scanner = new Scanner(System.in);

            boolean on = true;
            while (on) {
                System.out.print(menu);
                System.out.print("\nEnter your option:\t");
                int choice = Integer.parseInt(scanner.nextLine().trim());
                if (choice == 1) {
                    System.out.print("\nEnter the search term:\n");
                    String query = scanner.nextLine();
                    System.out.print(client.searchProduct(query));
                    Thread.sleep(2500);
                } else {
                    System.out.print("\n\nQuitting...\n\n");
                    Thread.sleep(2000);
                    on = false;
                }
            }
        } catch (Exception e) {
            System.err.println("Client stopped: " + e);
        }
    }
}
