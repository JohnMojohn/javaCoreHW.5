import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static List<Employee> parseCSV(String[] columnMapping, String fileName) {
        List<Employee> list = null;
        try (CSVReader csvreader = new CSVReader(new FileReader(fileName))) {
            ColumnPositionMappingStrategy<Employee> strategy = new ColumnPositionMappingStrategy<>();
            strategy.setType(Employee.class);
            strategy.setColumnMapping(columnMapping);
            CsvToBean<Employee> csv = new CsvToBeanBuilder<Employee>(csvreader)
                    .withMappingStrategy(strategy)
                    .build();
            list = csv.parse();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static String listToJson(List<Employee> list) {
        Type listType = new TypeToken<List<Type>>() {
        }.getType();
        System.out.println(listType);
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        System.out.println(gson.toJson(list));
        String json = gson.toJson(list, listType);

        return json;
    }

    public static void writreString(String json, String fileName) {
        try (FileWriter file = new
                FileWriter(fileName)) {
            file.write(json);
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public static List<Employee>  parseXML(String fileName) throws IOException, SAXException, ParserConfigurationException {
//        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//        DocumentBuilder builder = factory.newDocumentBuilder();
//        Document doc = builder.parse(new File(fileName));
//        Node root = doc.getDocumentElement();
//        System.out.println("Корневой элемент: " + root.getNodeName());
//        return read(root);
//    }

//    public static List<Employee> read(Node root) throws IOException, ParserConfigurationException, SAXException {
//        List<Employee> employeeList = new ArrayList<>();
//        NodeList nodeList = root.getChildNodes();
//        List emp = new ArrayList();
//        Employee emp1 = new Employee();
//        for (int i = 0; i < nodeList.getLength(); i++) {
//            Node node_ = nodeList.item(i);
//            if (Node.ELEMENT_NODE == node_.getNodeType()) {
//                System.out.println("Текущий узел: " + node_.getNodeName());
//                if (!node_.getNodeName().equals("employee")) {
//                    emp.add(node_.getTextContent());
//                    System.out.println(emp);
//                    if (emp.size() == 5) {
//                        emp1 = new Employee(Long.parseLong(emp.get(0).toString()), (String) emp.get(1), (String) emp.get(2), (String) emp.get(3), Integer.parseInt(emp.get(4).toString()));
//                        employeeList.add(emp1);
//                        System.out.println(employeeList);
//                    }
////                    employeeList.add(emp1);
//                }
//                    read(node_);
//                }
//            }
//        return employeeList;
//    }


    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {

        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        String fileName = "data.csv";
        List<Employee> list = parseCSV(columnMapping, fileName);
        String json = listToJson(list);
        writreString(json, "data.json");
//            parseXML("data.xml");
//            listToJson(parseXML("data.xml"));
//            writreString(listToJson(parseXML("data.xml")),"data1.json");
    }
}

