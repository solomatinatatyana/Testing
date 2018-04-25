import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
            NodeList nodeList = document.getElementsByTagName("Client");

            for (int i=0; i<nodeList.getLength();i++){
                Node node = nodeList.item(i);
                System.out.println();

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
                middleName + "\n" + "Birthday: " + birthday +  "\n" + "Primary City: " + primaryCity +  "\n" + "Amount: "+
                amountUSD +  "\n" + "Gender: " + gender + "\n"+ "\n";
        return prinClient;
    }

    public static List<Client> getByGender(String gender,String path){
        List<Client> list= new ArrayList<Client>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document doc = null;
        try {
            doc= builder.parse(path);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        XPath xPath = XPathFactory.newInstance().newXPath();
        try {
            NodeList nd = (NodeList)xPath.evaluate(".//Client[@gender = '" + gender + "']",
                    doc.getDocumentElement(),XPathConstants.NODESET);
            for (int i=0; i < nd.getLength();i++){
                Node node = nd.item(i);
                System.out.println();

                if (Node.ELEMENT_NODE == node.getNodeType()){
                    Element element = (Element) node;
                    Client client = new Client();
                    client.lastName = element.getElementsByTagName("LastName").item(0).getTextContent();
                    client.firstName = element.getElementsByTagName("FirstName").item(0).getTextContent();
                    client.middleName = element.getElementsByTagName("MiddleName").item(0).getTextContent();
                    client.birthday = element.getElementsByTagName("BirthDay").item(0).getTextContent();
                    client.primaryCity = element.getElementsByTagName("PrimaryCity").item(0).getTextContent();
                    client.amountUSD = element.getElementsByTagName("Amount").item(0).getTextContent();
                    client.gender = element.getAttribute("gender");

                    list.add(client);
                }
            }

        } catch (XPathExpressionException e) {
            System.out.println(e.getMessage());

        }
        return list;
    }

    public static List<Client> getSpecific(String gender, String currency, Date date, String path) throws ParseException {

        List<Client> listWithCurr= new ArrayList<Client>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document doc = null;
        try {
            doc= builder.parse(path);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        XPath xPath = XPathFactory.newInstance().newXPath();
        try {
            SimpleDateFormat ft= new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
            String data = ft.format(date);
            NodeList nd = (NodeList)xPath.evaluate(".//Client[@gender = '" + gender + "'" +
                            "and number(translate(BirthDay,' / ','')) > " + data + " ]" +
                            "                                   /Amount[@curr = '"+ currency +"']//parent::Client",
                    doc.getDocumentElement(),XPathConstants.NODESET);

            for (int i=0; i < nd.getLength();i++){
                Node node = nd.item(i);
                System.out.println();

                if (Node.ELEMENT_NODE == node.getNodeType()){
                    Element element = (Element) node;
                    Client client = new Client();
                    client.lastName = element.getElementsByTagName("LastName").item(0).getTextContent();
                    client.firstName = element.getElementsByTagName("FirstName").item(0).getTextContent();
                    client.middleName = element.getElementsByTagName("MiddleName").item(0).getTextContent();
                    client.birthday = element.getElementsByTagName("BirthDay").item(0).getTextContent();
                    client.primaryCity = element.getElementsByTagName("PrimaryCity").item(0).getTextContent();
                    client.amountUSD = element.getElementsByTagName("Amount").item(0).getTextContent();
                    client.gender = element.getAttribute("gender");

                    listWithCurr.add(client);
                }
            }

        } catch (XPathExpressionException e) {
            System.out.println(e.getMessage());

        }
        return listWithCurr;
    }


    public static void getTheReachestInDollars(String path) throws IOException {

        //String str = "http://api.fixer.io/latest?base="+currencyBase+"&symbols="+currencyTarget+"";;

        URL url = new URL("http://api.fixer.io/latest?base=RUB&symbols=USD");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        int status = con.getResponseCode();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        System.out.println(content.toString());

        //Gson gson = new GsonBuilder().setPrettyPrinting().create();
        //String json = gson.toJson(content);
        //System.out.println(json);
        //con.disconnect();


        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document doc = null;
        try {
            doc= builder.parse(path);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        XPath xPath = XPathFactory.newInstance().newXPath();
        try {
            NodeList nd = (NodeList)xPath.evaluate("",
                    doc.getDocumentElement(),XPathConstants.NODESET);
            for (int i=0; i < nd.getLength();i++){
                Node node = nd.item(i);
                System.out.println();

                if (Node.ELEMENT_NODE == node.getNodeType()){
                    Element element = (Element) node;
                    Client client = new Client();
                    client.lastName = element.getElementsByTagName("LastName").item(0).getTextContent();
                    client.firstName = element.getElementsByTagName("FirstName").item(0).getTextContent();
                    client.middleName = element.getElementsByTagName("MiddleName").item(0).getTextContent();
                    client.birthday = element.getElementsByTagName("BirthDay").item(0).getTextContent();
                    client.primaryCity = element.getElementsByTagName("PrimaryCity").item(0).getTextContent();
                    client.amountUSD = element.getElementsByTagName("Amount").item(0).getTextContent();
                    client.gender = element.getAttribute("gender");


                }
            }

        } catch (XPathExpressionException e) {
            System.out.println(e.getMessage());

        }
        //return reachestClient;
        return;
    }

}