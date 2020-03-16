package aclc_lms;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.TableModel;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author Junrey Algario
 */
public class Model {
    
    private final Db db = new Db();
    private final Helper helper;
    
    //private final Preferences prefs; Error

    public Model() {
        helper = new Helper(Model.class.getName());
        //prefs = Preferences.userRoot().node(this.getClass().getName()); error
    }
    
    private void setUserPref(ResultSet resultSet) {
        
    }
   
    public ResultSet auth(String username, String password) {
        String sql = "SELECT * FROM employee WHERE employee_id = '"+ username +"' AND password = '"+ password +"'";
        return db.fetchData(sql);
    }
    // -------------------------------------------------------------------------
    // -------------------------------- GETTERS --------------------------------
    // -------------------------------------------------------------------------
    
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
        String sql = "SELECT leave_request.leave_request_id, employee.employee_id, employee.department, CONCAT(employee.f_name, ' ', employee.l_name) as fullname, leave_type.leave_name, leave_request.start_date, leave_request.end_date, leave_request.reason, leave_request.command, leave_request.status FROM leave_type JOIN leave_request ON leave_type.leave_type_id = leave_request.leave_type_id JOIN employee ON leave_request.p_id = employee.p_id WHERE leave_request.seen = 0 "+ AND;
        return DbUtils.resultSetToTableModel(getResultSet(sql));
    }
    
    public ResultSet getLeaveRequest(String leaveId) {
        String sql = "SELECT *, CONCAT(f_name, ' ', l_name) as fullname FROM leave_type JOIN leave_request ON leave_type.leave_type_id = leave_request.leave_type_id JOIN employee ON leave_request.p_id = employee.p_id WHERE leave_request.leave_request_id = "+ leaveId;
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
    
    // -------------------------------------------------------------------------
    // --------------------------------- OTHERS --------------------------------
    // -------------------------------------------------------------------------
    
    // Insert, update query
    public void query(String sql) {
        db.executeSql(sql);
    }
}
