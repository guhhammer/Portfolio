package search;

import java.rmi.Naming;
import java.util.Scanner;

/** Administrator console: shows the search history and reads or changes the search timeout. */
public class Manager {

    public static void main(String[] args) {
        String server = "localhost:" + Server.RMI_PORT;
        try {
            IManager manager = (IManager) Naming.lookup("rmi://" + server + "/ManagerConsole");
            System.out.print("\n\nManager is connected.\n\n");

            Scanner scanner = new Scanner(System.in);
            String menu = "Manager console:\n"
                        + "\n\tOption (1): see history."
                        + "\n\tOption (2): see timeout."
                        + "\n\tOption (3): update timeout."
                        + "\n\tOption (4): quit.\n\n";

            boolean on = true;
            while (on) {
                System.out.print(menu);
                System.out.print("\nEnter your option:\t");
                int choice = Integer.parseInt(scanner.nextLine().trim());
                if (choice == 1) {
                    System.out.print(manager.seeSearchHistory());
                } else if (choice == 2) {
                    System.out.print("\nTimeout: " + manager.getTimeout() + " ms\n\n\n");
                } else if (choice == 3) {
                    System.out.print("\nEnter the new timeout (ms):\t");
                    manager.setTimeout(Integer.parseInt(scanner.nextLine().trim()));
                } else {
                    System.out.print("\n\nQuitting...\n\n");
                    on = false;
                }
                Thread.sleep(2000);
            }
        } catch (Exception e) {
            System.err.println("Manager stopped: " + e);
        }
    }
}
