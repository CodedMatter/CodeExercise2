public class Person {
    private String userId;
    private String firstName;
    private String lastName;
    private int version;
    private String insuranceCompany;

    public Person(String userId, String firstName, String lastName, int version, String insurance) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.version = version;
        this.insuranceCompany = insurance;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getInsuranceCompany() {
        return insuranceCompany;
    }

    public void setInsuranceCompany(String insuranceCompany) {
        this.insuranceCompany = insuranceCompany;
    }

    @Override
    public String toString() {
        return  "User Id: " + userId + "\n" +
                "First Name: " + firstName + "\n" +
                "Last Name: " + lastName + "\n" +
                "Version: " + version +  "\n" +
                "Insurance: " + insuranceCompany + "\n";
    }

    public String getCSVFormat(){
        return getUserId() + "," + getFirstName() + "," +
                getLastName() + "," + getVersion() + "," + getInsuranceCompany();
    }
}
