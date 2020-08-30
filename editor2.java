import javax.swing.*;

public class editor2 {
    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Editor();
            }

        });
    }
}
