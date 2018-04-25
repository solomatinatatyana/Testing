

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ClientTest {
    public static String path = "client.xml";

    public static void main(String[] args) {
        //String path1 = "C:\\Program Files\\maven\\apache-maven-3.5.0-bin\\" +
        //        "apache-maven-3.5.0\\bin\\clnt\\src\\main\\resources\\client1.xml";
        //Client.readFromXml(path);
        //List<Client> clients = ClientTest<Client>();

        List<Client> clients1 = Client.readFromXml(path);
        System.out.println(clients1.get(1).toString());
        //List<Client> clients = Client.
        List<Client> clientGen = Client.getByGender("female",path);
        System.out.println(clientGen.toString());


    }
}
