package aclc_lms;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.TableModel;
import net.proteanit.sql.DbUtils;

public class Model {
    
    private final Db db = new Db();
    private final Helper helper;
    

    public Model() {
        helper = new Helper(Model.class.getName());
    }
    // USER ACCOUNT FUNCTIONS
    
    public UserModel auth(String username, String password) {
        String sql = "SELECT * FROM employee WHERE employee_id = '"+ username +"' AND password = '"+ password +"'";
        UserModel user = new UserModel();
        ResultSet resultSet = db.fetchData(sql);
            try {
                if (resultSet.next()) {     
                    
                    user.setPid(resultSet.getString("p_id"));
                    user.setEmployeeId(resultSet.getString("employee_id"));
                    user.setfName(resultSet.getString("f_name"));
                    user.setlName(resultSet.getString("l_name"));
                    user.setmName(resultSet.getString("m_name"));
                    user.setGender(resultSet.getString("gender"));
                    user.setDob(resultSet.getString("dob"));
                    user.setContactNo(resultSet.getString("contact_no"));
                    user.setAddress(resultSet.getString("address"));
                    user.setDepartment(resultSet.getString("department"));
                    user.isAuthSuccess = true;
                }
            } catch (SQLException ex) {
                helper.logException("Login failed! An error has occured.", null);
            }
        return user;
    }
    
    public boolean updateProfile(String contact, String address, String pId) {
        String sql = "UPDATE employee SET contact_no = '"+ contact +"', address = '"+ address +"' WHERE p_id = "+ pId;
        db.executeSql(sql);
        return true;
    }
    
    public boolean changePassword(String pId, String password, String newPassword) {
        if (searchUser(pId, password)) {
            String sql = "UPDATE employee SET password = '"+ newPassword +"' WHERE p_id = "+ pId;
            db.executeSql(sql);
            return true;
        } else {
            return false;
        }
    }
    
    public boolean searchUser(String employeeId, String password) {
        String sql = "SELECT * FROM employee WHERE p_id = "+ employeeId +" AND password = '"+ password +"'";
        ResultSet resultSet = db.fetchData(sql);
        try {
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException ex) {
            helper.logException("ERROR searching user", sql);
        }
        return false;
    }
    
    public String getEmployeeContactNo(String employeeId) {
        String sql = "SELECT contact_no FROM employee WHERE employee_id = '"+ employeeId +"'";
        try {
            ResultSet resultSet = db.fetchData(sql);
            if (resultSet.next()) {
                return resultSet.getString("contact_no");
            }
            return "";
        } catch (SQLException ex) {
            helper.logException("Failed to fetch employee contact number", sql);
            return "";
        }
    }
    
    // Get table model
    public TableModel getEmployeeTableModel(String where) {
        String sql = "SELECT p_id, employee_id, department, CONCAT(f_name, ' ', l_name) as fullname, gender, dob  as date_of_birth, contact_no, address FROM employee"+ where;
        return DbUtils.resultSetToTableModel(getResultSet(sql));
    }
    
    public TableModel getLeaveTableModel(String AND) {
        String sql = "SELECT leave_request.leave_request_id, employee.employee_id, employee.department, CONCAT(employee.f_name, ' ', employee.l_name) as fullname, leave_type.leave_name, leave_request.start_date, leave_request.end_date FROM leave_type JOIN leave_request ON leave_type.leave_type_id = leave_request.leave_type_id JOIN employee ON leave_request.p_id = employee.p_id WHERE leave_request.seen = 0 "+ AND;
        return DbUtils.resultSetToTableModel(getResultSet(sql));
    }
    
    public TableModel getLeaveTableReportModel(String AND) {
        String sql = "SELECT leave_request.leave_request_id, employee.employee_id, employee.department, CONCAT(employee.f_name, ' ', employee.l_name) as fullname, leave_type.leave_name, leave_request.start_date, leave_request.end_date, leave_request.reason, leave_request.command, leave_request.status FROM leave_type JOIN leave_request ON leave_type.leave_type_id = leave_request.leave_type_id JOIN employee ON leave_request.p_id = employee.p_id WHERE leave_request.seen = 1 "+ AND;
        return DbUtils.resultSetToTableModel(getResultSet(sql));
    }
    
    public TableModel getEmployeeLeaveTableReportModel(String AND) {
        String sql = "SELECT leave_request.leave_request_id, leave_type.leave_name, leave_request.start_date, leave_request.end_date, leave_request.reason, leave_request.command, leave_request.status FROM leave_type JOIN leave_request ON leave_type.leave_type_id = leave_request.leave_type_id JOIN employee ON leave_request.p_id = employee.p_id WHERE leave_request.seen = 1 "+ AND;
        return DbUtils.resultSetToTableModel(getResultSet(sql));
    }
    
    public TableModel getMessageTblModel(String WHERE) {
        String sql = "SELECT message.message_id, message.message, message.sent_date, CONCAT(employee.f_name, ' ', employee.l_name) as fullname FROM message JOIN employee ON message.reciever = employee.p_id "+ WHERE;
        return DbUtils.resultSetToTableModel(getResultSet(sql));
    }
    
    public ResultSet getLeaveRequest(String leaveId) {
        String sql = "SELECT *, CONCAT(f_name, ' ', l_name) as fullname FROM leave_type JOIN leave_request ON leave_type.leave_type_id = leave_request.leave_type_id JOIN employee ON leave_request.p_id = employee.p_id WHERE leave_request.leave_request_id = "+ leaveId;
        return db.fetchData(sql);
    }
    
    public ResultSet getLastLeave(String employeeId) {
        String sql = "SELECT * FROM employee JOIN leave_request ON employee.p_id = leave_request.p_id JOIN leave_type ON leave_request.leave_type_id = leave_type.leave_type_id WHERE leave_request.p_id = "+ employeeId +" ORDER BY leave_request_id DESC LIMIT 1";
        return db.fetchData(sql);
    }
    
    public ResultSet getAllLeaveType() {
        String sql = "SELECT leave_name FROM leave_type";
        return db.fetchData(sql);
    }
    
    public ResultSet getEmployee(String employeeId) {
        String sql = "SELECT * FROM employee WHERE p_id = "+ employeeId;
        return db.fetchData(sql);
    }
    
    public ResultSet getResultSet(String sql) {
        return db.fetchData(sql);
    }
    
    public String generateEmployeeId(String department) {
        try {
            String sql = "SELECT employee_id FROM employee WHERE department = '"+ department +"' ORDER BY p_id DESC LIMIT 1";
            ResultSet resultSet = getResultSet(sql);
            if (resultSet.next()) {
                String lastEmployeeId = resultSet.getString("employee_id");
                return lastEmployeeId.substring(0,2) + Integer.toString(Integer.parseInt(lastEmployeeId.substring(2,lastEmployeeId.length())) + 1);
            } else {
                return department.equals("College") ? "CL1001" : "SH1001";
            }
        } catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public String getLeaveTypeId(String leave_name) {
        String sql = "SELECT leave_type_id FROM leave_type WHERE leave_name = '"+ leave_name +"'";
        ResultSet resultSet = db.fetchData(sql);
        try {
            if (resultSet.next()) {
                return resultSet.getString("leave_type_id");
            }
        } catch (SQLException ex) {
            helper.logException("Failed to fetch leave name", sql);
        }
        return null;
    }
    
    // Insert, update query
    public void query(String sql) {
        db.executeSql(sql);
    }
    
    public Stack getLeaveTypeComboBoxModel() {
        Stack<String> leaveTypeSet = new Stack<>();
        ResultSet leaveTypeResultSet = getAllLeaveType();
        try {
            while (leaveTypeResultSet.next()) {
                leaveTypeSet.push(leaveTypeResultSet.getString("leave_name"));
            }
            return leaveTypeSet;
        } catch (SQLException ex) {
            helper.logException(ex.toString(), "Failed to load leave type. An error has occured.");
            return null;
        }
    }
   
    public TableModel updateStartEndDateColumn(TableModel tblModel, int[] colIndex) {
        int tblRowCount = tblModel.getRowCount();
        for (int i=0; i <tblRowCount; i++) {
            String sDate = helper.getddMMyyyyObject(tblModel.getValueAt(i, colIndex[0]).toString());
            //String eDate = helper.getddMMyyyyObject(tblModel.getValueAt(i, colIndex[1]).toString());
            tblModel.setValueAt(sDate, i, colIndex[0]);
            if (colIndex.length > 1) {
                //tblModel.setValueAt(eDate, i, colIndex[1]);
            }
        }
        return tblModel;
    }
    
    public ResultSet getAllEmployeeContact() {
        String sql = "SELECT p_id, contact_no, concat(f_name, ' ', l_name) as fullname FROM employee";
        //esultSet resultSet = db.fetchData(sql);
        return db.fetchData(sql);
    } 
    
    
}
