

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ClientTest {

    public static String path = "client.xml";

    public static void main(String[] args) throws ParseException, IOException {
        //Client.readFromXml(path);
        //List<Client> clients = ClientTest<Client>();

        //List<Client> clients1 = Client.readFromXml(path);
        //System.out.println(clients1.get(1).toString());
        //List<Client> clients = Client.
        //List<Client> clientGen = Client.getByGender("female",path);
        //System.out.println(clientGen.toString());

        Date date = new Date("1975/07/12");

        //List<Client> clientCurr = Client.getSpecific("male", "USD", date, path);
        //System.out.println(date);
        //System.out.println(clientCurr.toString());
        Client.getTheReachestInDollars(path);
    }
}
