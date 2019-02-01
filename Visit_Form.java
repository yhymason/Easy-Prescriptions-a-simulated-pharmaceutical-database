import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.text.SimpleDateFormat;

public class Visit_Form {
    private JTextField phn;
    private JTextField date;
    private JTextArea reason;
    private JTextArea notes;
    private JButton createButton;
    public JPanel panel;
    private int did;
    public Visit_Form(int id) {
        this.did = id;
        try
        {
            Connection con = Connections.getConnection();
            Utilities util = new Utilities();
            createButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int n;
                    if(phn.getText().trim().length() > 0)
                        n = Integer.parseInt(phn.getText().trim());
                    else
                        n = 0;
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    String r = reason.getText().trim();
                    String no = notes.getText().trim();
                    try
                    {
                        java.util.Date d;
                        if (date.getText().trim().length() > 0) {
                            d = formatter.parse(date.getText().trim());
                            java.sql.Date visit_date = new java.sql.Date(d.getTime());
                            if(did > 0 && n > 0) {
                                util.insert_doctor_visit(n,did,visit_date,r,no,con);
                                JOptionPane.showMessageDialog(null,"Success");
                            }
                            else
                                JOptionPane.showMessageDialog(null,"You must at least have all the IDs!");
                        }
                        else
                            JOptionPane.showMessageDialog(null,"You must Enter a date");
                    }
                    catch (Exception e1)
                    {
                        JOptionPane.showMessageDialog(null, e1.getMessage());
                    }
                }
            });
        }
        catch (Exception e2)
        {
            JOptionPane.showMessageDialog(null,"Connection Failed");
        }
    }
}
