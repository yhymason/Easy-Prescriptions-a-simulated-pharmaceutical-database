import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class Patient_Profile {
    private JTextField pnum;
    private JTextField adr;
    private JButton saveButton;
    public JPanel panel;
    private int phn;

    public Patient_Profile(int id) {
        this.phn = id;
        try {
            Connection con = Connections.getConnection();
            Utilities util = new Utilities();
            int phn = this.phn;
            saveButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try{
                        String new_phone_number = pnum.getText().trim();
                        String new_address = adr.getText().trim();
                        if(util.updatePatientInfo(phn,new_phone_number,new_address,con))
                        {
                            JOptionPane.showMessageDialog(null,"Success");
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null,"Not Saved");
                        }
                    }
                    catch (java.sql.SQLException e1){
                        JOptionPane.showMessageDialog(null, e1.getMessage());
                    }
                }
            });
        }
        catch(Exception e1){
            JOptionPane.showMessageDialog(null,"Connection Failed");
        }
    }
}
