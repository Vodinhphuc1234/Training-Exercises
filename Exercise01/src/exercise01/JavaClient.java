package exercise01;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

/**
 *
 * @author vodinhphuc
 */
public class JavaClient {

    public static void main(String[] arg) throws TException, IOException {
        TTransport transport = new TSocket("localhost", 9090);

        transport.open();

        TProtocol protocol = new TBinaryProtocol(transport);
        ProfileService.Client client = new ProfileService.Client(protocol);

        perform(client);

        transport.close();
    }

    private static void perform(ProfileService.Client client) throws TException {
        System.out.println("Choose to test");
        System.out.println("1. Get");
        System.out.println("2. GetAll");
        System.out.println("3. MultiGet");
        System.out.println("4. Update");
        System.out.println("5. Create");
        System.out.println("6. FindUser");
        System.out.println("Any key. Delete");

        Scanner scanner = new Scanner(System.in);
        int option = Integer.valueOf(scanner.nextLine());

        switch (option){
                case 1: {
            System.out.println("Input: id ");
            int id = Integer.valueOf(scanner.nextLine());

            User user = client.get(id);

            System.out.println(user.toString());
            break;
        }
        case 2: {
            List<User> users = client.getAll();

            for (User user : users) {
                System.out.println(user.toString());
            }
            break;
        }
        case 3: {
            List<Integer> ids = new ArrayList<>();
            
            System.out.println("Input ids, 0 to end");
            
            int id = Integer.valueOf(scanner.nextLine());
            
            while (id >0){
                ids.add(id);
                id = Integer.valueOf(scanner.nextLine());
            }
            
            List<User> users = client.multiGet(ids);

            for (User user : users) {
                System.out.println(user.toString());
            }
            break;
            
        }
        case 4: {
            System.out.println("Input: id -> name -> username -> password");
            int id = Integer.valueOf(scanner.next());
            String name = scanner.nextLine();
            String username = scanner.nextLine();
            String password = scanner.nextLine();

            User user = new User(name, username, password, true);

            System.out.println(client.update(id, user));
            
            break;
        } 
        case 5: {
            System.out.println("Input: name -> username -> password");
            
            String name = scanner.nextLine();
            String username = scanner.nextLine();
            String password = scanner.nextLine();

            User user = new User(name, username, password, true);

            System.out.println(client.create(user));
            
            break;
        } 
        case 6: {
            System.out.println("Input: username -> password");
            
            
            String username = scanner.nextLine();
            String password = scanner.nextLine();

            User user = client.findUser(username, password);

            System.out.println(user.toString());
            
            break;
        } 
        default: {
            System.out.println("Input: id ");
            int id = Integer.valueOf(scanner.nextLine());

            System.out.println(client.remove(id));
        }
    }
}
}
