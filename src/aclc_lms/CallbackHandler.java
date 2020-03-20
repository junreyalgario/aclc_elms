package aclc_lms;

public class CallbackHandler {
    private Callback callback;

    public void setCallback(Callback callback) {
        this.callback = callback;
    }
    
    public void jobDone() {
        callback.onJobDone();
    }
    
    public interface Callback {
        void onJobDone();
    }
}
