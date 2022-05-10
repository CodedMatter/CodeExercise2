/*Coding exercise: Availity receives enrollment files from various benefits
management and enrollment solutions (I.e. HR platforms, payroll platforms).
  Most of these files are typically in EDI format.  However, there are
some files in CSV format.  For the files in CSV format, write a program
in a language that makes sense to you that will read the content of the file
and separate enrollees by insurance company in its own file. Additionally,
sort the contents of each file by last and first name (ascending).  Lastly,
if there are duplicate User Ids for the same Insurance Company, then only
the record with the highest version should be included. The following data points
are included in the file:
 User Id (string)
 First Name (string)
 Last Name (string)
 Version (integer)
 Insurance Company (string)*/

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class Main {
    public static void main(String[] args){

        Main program = new Main();
        String filePath = "src/main/files/info.csv";
        File file = program.getFile(filePath);
        program.sortFileData(file);

    }

    // returns all the persons from the csv file
    public List<Person> getFileData(Scanner scanner) {
        List<Person> personList = new ArrayList<>();

        while (scanner.hasNext()){
            String nextLine = scanner.nextLine();
            String[] arr = nextLine.split(",");

            Person person = new Person(
                    /*user id*/ arr[0],
                    /*first name*/ arr[1],
                    /*last name*/ arr[2],
                    /*version*/ Integer.parseInt(arr[3]),
                    /*insurance*/ arr[4]);

            personList.add(person);
        }
        return personList;
    }

    // gets the csv file
    public File getFile(String filePath){
        return new File(filePath);
    }

    // sort data in csv file by Company,lastname. firstname ASC
    public void sortFileData(File file){
        try {
            Scanner scanner = new Scanner(file);
            List<Person> personList = getFileData(scanner);

            Collections.sort(personList, new Comparator<Person>() {
                @Override
                public int compare(Person o1, Person o2) {
                    // sort by company
                    if(o1.getInsuranceCompany().compareTo(o2.getInsuranceCompany()) == 0){
                        // sort by last name
                        if(o1.getLastName().compareTo(o2.getLastName()) == 0){
                            // sort by first name
                            return o1.getFirstName().compareTo(o2.getFirstName());
                        }
                        return o1.getLastName().compareTo(o2.getLastName());
                    }
                    return o1.getInsuranceCompany().compareTo(o2.getInsuranceCompany());
                }
            });

            PrintWriter printWriter = new PrintWriter(file);

            // remove duplicate ids before writing back to file
            removeDuplicateIds(personList);

            for(Person p : personList){
                printWriter.println(p.getCSVFormat());
            }
            printWriter.close();
        }
        catch (FileNotFoundException e){
            System.out.println("Couldn't find the file. Check filePath");
        }

    }

    private void removeDuplicateIds(List<Person> personList) {
        HashSet<Person> peopleToRemove = new HashSet<>();

        for(int i = 0; i < personList.size(); i++){
            String id = personList.get(i).getUserId();
            for(int j = i+1; j < personList.size(); j++){
                if(id.equals(personList.get(j).getUserId())){
                    if(personList.get(i).getVersion() < personList.get(j).getVersion()){
                        peopleToRemove.add(personList.get(i));
                    }else{
                        peopleToRemove.add(personList.get(j));
                    }
                }
            }
        }

        for(Person p : peopleToRemove){
            personList.remove(p);
        }
    }
}
