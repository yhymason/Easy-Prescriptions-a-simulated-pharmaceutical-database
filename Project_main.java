import javax.swing.*;

public class Project_main {
    public static void main(String args[]){
        JFrame frame = JFrames.get_frame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Login");
        frame.setContentPane(new Medical_Records_Manager().Login);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
