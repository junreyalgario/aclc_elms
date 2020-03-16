package aclc_lms;

/**
 *
 * @author Junrey Algario
 */
public class UserModel {
    private String pId;
    private String employeeId;
    private String fName;
    private String mName;
    private String lName;
    private String gender;
    private String dob;
    private String contactNo;
    private String address;
    private String department;
    public boolean isAuthSuccess = false;

    public String getFullName() {
        return fName +" "+ lName;
    }
    
    public String getpId() {
        return pId;
    }

    public void setPid(String pId) {
        this.pId = pId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
    
    
}
