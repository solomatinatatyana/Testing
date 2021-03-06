import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.testng.Assert.*;

public class SrvCreateTest {

    public void print(String priority, byte stabId, int mdmId, String fstname, String lstname, String middlename, String dul ){
        System.out.println("Priority: " + priority);
        System.out.println("StabId: " + stabId);
        System.out.println("MDMId: " + mdmId);
        System.out.println("First Name: " + fstname);
        System.out.println("Last Name: " + lstname);
        System.out.println("Middle Name: " + middlename);
        System.out.println("DUL: " + dul);
    }

    @DataProvider(name = "path")
    public Object[][] wrongData(){
        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document document = null;
        try {
            document = documentBuilder.parse("SrvCreate.xml");
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert document != null;
        NodeList nodeList = document.getElementsByTagName("OperationData");
        Object[][] res = new Object[nodeList.getLength()][1];
        for (int i = 0; i < nodeList.getLength(); i++) {

            res[i][0] = nodeList.item(i);

        }
        return res;
    }

    @Test(dataProvider = "path")
    public void checkContent(Node node){
        SoftAssert softAssert = new SoftAssert();
        /*DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document document = null;
        try {
            document = documentBuilder.parse(path);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        NodeList nodeList = document.getElementsByTagName("OperationData");
        SoftAssert softAssert = new SoftAssert();
        for (int i = 0; i < nodeList.getLength(); i++) {

            Node node = nodeList.item(i);*/
            System.out.println();

            System.out.println("текущий элемент " + node.getNodeName());

        if (Node.ELEMENT_NODE == node.getNodeType()) {
                Element element = (Element) node;

                String priority = element.getElementsByTagName("Priority").item(0).getTextContent();
                byte StabId = 0;
                int MDMId = 0;
                String firstName = element.getElementsByTagName("FirstName").item(0).getTextContent();
                String lastName = element.getElementsByTagName("LastName").item(0).getTextContent();
                String middleName = element.getElementsByTagName("MiddleName").item(0).getTextContent();
                String dul = element.getElementsByTagName("DUL").item(0).getTextContent();

                try {
                    StabId = Byte.parseByte(element.getElementsByTagName("StabId").item(0).getTextContent());
                    MDMId = Integer.parseInt(element.getElementsByTagName("MDMId").item(0).getTextContent());

                    softAssert.assertTrue(StabId > 0,
                                            "Текущее значение тега StabId " + StabId + " ,а должно быть больше 0");
                    softAssert.assertTrue(MDMId > 0,
                                            "Текущее значение тега MDMId " + MDMId + " ,а должно быть больше 0");
                    softAssert.assertTrue(firstName.matches(("^[a-zA-Z]+$")),
                                            "Текущее значение тега FirstName " + firstName +
                                                    ",а должно содержать только латинкские буквы");
                    softAssert.assertTrue(lastName.matches(("^[a-zA-Z]+$")),
                                            "Текущее значение тега LastName " + lastName +
                                                    " ,а должно содержать только латинкские буквы");
                    softAssert.assertTrue(middleName.matches(("^[a-zA-Z]+$")),
                                            "Текущее значение тега MiddleName " + middleName +
                                                    ",а должно содержать только латинкские буквы");

                    print(priority, StabId, MDMId, firstName, lastName, middleName, dul);
//
                } catch (NumberFormatException ex) {
                    System.out.println(ex.getMessage());
                    System.out.println("!!!!!!");
                    Assert.assertTrue((false),"Текущее значение тега StabId "
                                            + element.getElementsByTagName("StabId").item(0).getTextContent() +
                                            " выходит за пределы диапазона");

                    Assert.assertTrue((false), "Текущее значение тега MDMId "
                                            + element.getElementsByTagName("MDMId").item(0).getTextContent() +
                                            " выходит за пределы диапазона");

                }
            }
        softAssert.assertAll();
        }

    }