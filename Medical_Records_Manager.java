import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class Medical_Records_Manager {
    public JPanel Login;
    private JButton loginButton;
    private JButton registerButton;
    private JTextField user_Name;
    private JPasswordField User_ID;
    Utilities util = new Utilities();

    public Medical_Records_Manager() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    Connection con = Connections.getConnection();
                    String user_id_string = new String(User_ID.getPassword());
                    int user_id = Integer.parseInt(user_id_string.trim());
                    String user_name = user_Name.getText().trim();
                    try{
                        if(util.patient_login(user_id,user_name,con)){
                            try{
                                JFrame frame = JFrames.get_frame();
                                frame.setTitle("Patient_UI");
                                Patient_UI pui = new Patient_UI(user_id,user_name);
                                frame.setContentPane(pui.panel);
                                frame.pack();
                                frame.setVisible(true);
                                frame.setLocationRelativeTo(null);

                            }catch (Exception e1){
                                JOptionPane.showMessageDialog(null, "Failed to Open");
                            }
                        }
                        else if(util.doctor_login(user_id,user_name,con)){
                            try{
                                JFrame frame = JFrames.get_frame();
                                frame.setTitle("Doctor_UI");
                                Doctor_UI dui = new Doctor_UI(user_id,user_name);
                                frame.setContentPane(dui.panel);
                                frame.pack();
                                frame.setVisible(true);
                                frame.setLocationRelativeTo(null);

                            }catch (Exception e1){
                                JOptionPane.showMessageDialog(null, "Failed to Open");
                            }
                        }
                        else if(util.pharmacist_login(user_id,user_name,con))
                        {
                            try{
                                JFrame frame = JFrames.get_frame();
                                frame.setTitle("Pharmacist_UI");
                                Pharmacist_UI phui = new Pharmacist_UI(user_id,user_name);
                                frame.setContentPane(phui.panel);
                                frame.pack();
                                frame.setVisible(true);
                                frame.setLocationRelativeTo(null);

                            }catch (Exception e1){
                                JOptionPane.showMessageDialog(null, "BUG_UI");
                            }
                        }
                        else{
                            JOptionPane.showMessageDialog(null,"Login Failed, User not found");
                        }
                    }catch (java.sql.SQLException e1){
                        JOptionPane.showMessageDialog(null, e1.getMessage());
                    }
                }catch (Exception e2){
                    JOptionPane.showMessageDialog(null,"Input cannot be empty");
                }
            }
        });
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    JFrame frame = JFrames.get_frame();
                    frame.setTitle("Registration");
                    frame.setContentPane(new Registration(0).panel1);
                    frame.pack();
                    frame.setVisible(true);
                    frame.setLocationRelativeTo(null);
                }catch (Exception e1){
                    JOptionPane.showMessageDialog(null, "Failed to Open");
                }
            }
        });
    }
}
