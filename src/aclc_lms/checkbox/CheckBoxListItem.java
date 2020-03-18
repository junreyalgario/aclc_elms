package aclc_lms.checkbox;

public class CheckBoxListItem {
    private String label;
    private boolean isSelected = false;
    private String pId;
    
    public CheckBoxListItem(String label) {
        this.label = label;
    }
 
    public boolean isSelected() {
        return isSelected;
    }
 
    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
 
    public String toString() {
        return label;
    }
    
    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }
}
