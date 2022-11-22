package ua.kiev.prog.case2;

import ua.kiev.prog.shared.Client;
import ua.kiev.prog.shared.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        try (Connection conn = ConnectionFactory.getConnection()) {
            // remove this
            try {
                try (Statement st = conn.createStatement()) {
                    st.execute("DROP TABLE IF EXISTS Clients");
                    //st.execute("CREATE TABLE Clients (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, name VARCHAR(20) NOT NULL, age INT)");
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            Scanner sc = new Scanner(System.in);
            ClientDAOImpl2 dao = new ClientDAOImpl2(conn, "Clients");
            dao.createTable(Client.class);

            while (true) {
                System.out.println("1: add client");
                System.out.println("2: view clients");
                System.out.print("-> ");

                String s = sc.nextLine();
                switch (s) {
                    case "1":
                        System.out.print("Enter client name: ");
                        String name = sc.nextLine();
                        System.out.print("Enter client age: ");
                        String sAge = sc.nextLine();
                        int age = Integer.parseInt(sAge);

                        dao.add(new Client(name, age));
                        break;
                    case "2":
                        List<Client> list = dao.getAll(Client.class, "name");
                        for (Client client : list) {
                            System.out.println(client);
                        }
                        break;
                    default:
                        return;
                }
            }
        }
    }
}
