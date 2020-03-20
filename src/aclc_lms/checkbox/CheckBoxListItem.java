package aclc_lms.checkbox;

public class CheckBoxListItem {
    private boolean isSelected = false;
    private String pId;
    private String fullName;
    
    public CheckBoxListItem(String fullName) {
        this.fullName = fullName;
    }
 
    public boolean isSelected() {
        return isSelected;
    }
 
    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
 
    public String toString() {
        return fullName;
    }
    
    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }
}
