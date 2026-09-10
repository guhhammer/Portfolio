package chat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

/** Saves and loads the accounts in databank.txt, one line per account:
 *  {name} {password} {ip} {port} {[[from,message],...]} */
public class Databank {

    public static final String FILE = "databank.txt";

    public static void save(List<Account> accounts) throws FileNotFoundException, InterruptedException {
        Server.mutex.acquire();
        try (PrintWriter writer = new PrintWriter(FILE)) {
            for (Account a : accounts) {
                writer.println(String.format("{%s} {%s} {%s} {%d} {%s}",
                        a.getName(), a.getPassword(), a.getIpAddress(), a.getPort(), a.messageData()));
            }
        } finally {
            Server.mutex.release();
        }
    }

    public static void load() throws FileNotFoundException {
        File data = new File(FILE);
        if (!data.isFile()) { return; }
        try (Scanner scan = new Scanner(data)) {
            while (scan.hasNextLine()) {
                String line = scan.nextLine().trim();
                if (line.isEmpty()) { continue; }
                String[] s = line.split("} \\{");
                s[0] = s[0].replace("{", "");
                s[s.length - 1] = s[s.length - 1].replace("}", "");

                Account account = new Account(s[0], s[1], s[2], Integer.parseInt(s[3]));
                Server.insertAccount(account);

                String[] messages = s[s.length - 1].split(",");
                for (int i = 0; i < messages.length; i++) {
                    messages[i] = messages[i].replace("[", "").replace("]", "");
                }
                if (messages.length > 1) {
                    for (int k = 0; k + 1 < messages.length; k += 2) {
                        account.addMessage(messages[k], messages[k + 1]);
                    }
                }
            }
        }
    }
}
