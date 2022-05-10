import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class MainTest {
    Main testProgram = new Main();
    File file;

    Scanner scanner;

    @BeforeEach
    void setUp(){
        // create test data
        List<Person> personList = new ArrayList<>();
        personList.add(new Person("1","brian","rodriguez",1,"acompany"));
        personList.add(new Person("2","henry","john",1,"ccompany"));
        personList.add(new Person("3","ben","chris",1,"acompany"));
        personList.add(new Person("4","sasha","john",1,"bcompany"));
        personList.add(new Person("6","dany","ino",2,"ccompany"));
        personList.add(new Person("6","unknown","idk",1,"dcompany"));
        personList.add(new Person("7","other","others",1,"dcompany"));
        personList.add(new Person("8","apple","rodriguez",1,"acompany"));

        // create test file and add test data
        String filePath = "src/test/java/testfile/test.csv";

        file = new File(filePath);

        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(file);

            for(Person p : personList){
                printWriter.println(p.getCSVFormat());
            }
            printWriter.close();

            // run sorting method on file
            testProgram.sortFileData(file);

            scanner = new Scanner(file);
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    // are there ordered by company

    @Test
    void orderedByCompany(){
        List<Person> personList = testProgram.getFileData(scanner);
        Assertions.assertEquals("acompany", personList.get(0).getInsuranceCompany(), "Company should be acompany");
        Assertions.assertEquals("dcompany", personList.get(personList.size()-1).getInsuranceCompany(), "Company should be dcompany");
    }

    // order by company, last name
    @Test
    void orderedByCompanyAndLastName(){
        List<Person> personList = testProgram.getFileData(scanner);
        Assertions.assertEquals("chris",personList.get(0).getLastName(),"Last name 'chris' should be first");
        Assertions.assertEquals("others",personList.get(personList.size()-1).getLastName(),"Last name 'other' should be first");
    }

    // order by company, lastname, first name
    @Test
    void orderedByCompanyLastThenFirstName(){
        List<Person> personList = testProgram.getFileData(scanner);
        Assertions.assertEquals("apple",personList.get(1).getFirstName(),"First name 'apple' should be before 'brian'");
        Assertions.assertEquals("brian",personList.get(2).getFirstName(),"First name 'brian' should be before 'apple'");
    }

    // are duplicate ids removed by higher version
    @Test
    void removeDuplicateIds(){
        List<Person> personList = testProgram.getFileData(scanner);
        // id 6 with version 1 should be removed
        List<Integer> indexOfPersonIdSix = new ArrayList<>();

        for(int i = 0; i < personList.size(); i++){
            if(personList.get(i).getUserId().equals("6")){
                indexOfPersonIdSix.add(i);
            }
        }
        Assertions.assertEquals(1,indexOfPersonIdSix.size(),"There should only be one id 6 in list");
        Assertions.assertEquals("dany",personList.get(indexOfPersonIdSix.get(0)).getFirstName(), "The remaining person with id of six should be first name 'dany");

    }

}