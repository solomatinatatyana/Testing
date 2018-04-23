import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ArrayList;
import java.util.List;

public class Client {
    private String lastName;
    private String firstName;
    private String middleName;
    private String birthday;
    private String primaryCity;
    private String amountUSD;
    private String gender;


    public Client(){ };

    public Client(String gender, String lastName, String firstName, String middleName, String birthDay, String primaryCity, String amountUSD) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.birthday = birthDay;
        this.primaryCity = primaryCity;
        this.amountUSD = amountUSD;
        this.gender = gender;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getPrimaryCity() {
        return primaryCity;
    }

    public String getAmountUSD() {
        return amountUSD;
    }

    public String getBirthday() {
        return birthday;
    }

    public static List<Client> readFromXml(String path){
        List<Client> result = new ArrayList<Client>();
        try{
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(path);

            Node root = document.getDocumentElement();
            System.out.println("List of Clients: ");
            System.out.println();

            System.out.println("Root element " + document.getDocumentElement().getNodeName());
            System.out.println("============================");

            NodeList nodeList = document.getElementsByTagName("Client");

            for (int i=0; i<nodeList.getLength();i++){
                Node node = nodeList.item(i);
                System.out.println();
                ///System.out.println("Текущий элемент " + node.getNodeName());

                if (Node.ELEMENT_NODE == node.getNodeType()){
                    Element element = (Element) node;
                    Client clients = new Client();
                    clients.lastName = element.getElementsByTagName("LastName").item(0).getTextContent();
                    clients.firstName = element.getElementsByTagName("FirstName").item(0).getTextContent();
                    clients.middleName = element.getElementsByTagName("MiddleName").item(0).getTextContent();
                    clients.birthday = element.getElementsByTagName("BirthDay").item(0).getTextContent();
                    clients.primaryCity = element.getElementsByTagName("PrimaryCity").item(0).getTextContent();
                    clients.amountUSD = element.getElementsByTagName("Amount").item(0).getTextContent();
                    clients.gender = element.getAttribute("gender");
                    result.add(clients);
                }
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return result;
    }

    public String toString() {

        String prinClient = "First Name: "+ firstName+ "\n" + "Last Name: " + lastName + "\n"+ "Middle Name: " +
                middleName + "\n" + "Birthday: " + birthday +  "\n" + "Primary City: " + primaryCity +  "\n" + "AmountUSD: "+
                amountUSD +  "\n" + "Gender: " + gender;
        return prinClient;
    }
}

