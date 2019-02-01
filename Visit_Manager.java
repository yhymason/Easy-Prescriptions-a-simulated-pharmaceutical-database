import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.ArrayList;

public class Visit_Manager {
    public JPanel panel;
    private JTable table;
    private JButton searchAndEditButton;
    private JButton createANewVisitButton;
    private int did;

    public Visit_Manager(int id) {

        this.did = id;

        try {

            Connection con = Connections.getConnection();
            Utilities util = new Utilities();
            ArrayList<Utilities.DoctorVisit> all_visits = util.showAllDocVisits(did, con);
            String[] cols = {"Personal_health_number", "Name", "Date", "Reason", "Notes"};
            String [][] rows = new String[all_visits.size()][5];
            if(all_visits.size() > 0) {
                for (int i = 0; i < all_visits.size(); i++) {
                    rows[i][0] = Integer.toString(all_visits.get(i).getPhn());
                    rows[i][1] = all_visits.get(i).getName().trim();
                    rows[i][2] = all_visits.get(i).getVisitDate().toString().trim();
                    if(all_visits.get(i).getReason() != null)
                        rows[i][3] = all_visits.get(i).getReason().trim();
                    else
                        rows[i][3] = " ";
                    if(all_visits.get(i).getNotes() != null)
                        rows[i][4] = all_visits.get(i).getReason().trim();
                    else
                        rows[i][4] = " ";
                }
            }
            DefaultTableModel model = new DefaultTableModel(rows, cols);
            table.setModel(model);

            searchAndEditButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JTextField name = new JTextField();
                    JTextField phn = new JTextField();
                    Object[] message = {
                            "Patient Name:", name,
                            "Health Number:", phn
                    };
                    int option = JOptionPane.showConfirmDialog(null,
                            message, "Search", JOptionPane.OK_CANCEL_OPTION);
                    if (option == JOptionPane.OK_OPTION)
                    {
                        try
                        {
                            Utilities.DoctorVisit visit = util.showDocVisitForPatient(did,
                                    Integer.parseInt(phn.getText()), con);
                            JLabel date = new JLabel();
                            date.setText(visit.getVisitDate().toString().trim());
                            JTextArea reason = new JTextArea();
                            JTextArea notes = new JTextArea();
                            reason.setText(visit.getReason().trim());
                            notes.setText(visit.getNotes().trim());
                            Object[] out_message = {
                                    "Date:", date,
                                    "Reason:", reason,
                                    "Notes:", notes
                            };
                            int flag = JOptionPane.showConfirmDialog(null,
                                    out_message, "Results, click 'OK' to save", JOptionPane.OK_CANCEL_OPTION,
                                    JOptionPane.INFORMATION_MESSAGE, null);
                            if (flag == JOptionPane.OK_OPTION)
                            {
                                try{
                                    if(util.updateDoctorVisit(reason.getText().trim(),notes.getText().trim(),
                                            Integer.parseInt(phn.getText()),did,visit.getVisitDate(),con))
                                    {
                                        JOptionPane.showMessageDialog(null,"Success");
                                    }
                                    else
                                    {
                                        JOptionPane.showMessageDialog(null,"Not Saved");
                                    }
                                }
                                catch (Exception e1){
                                    JOptionPane.showMessageDialog(null, "BUG_UI");
                                }
                            }

                        }catch (Exception e1){
                            JOptionPane.showMessageDialog(null, "BUG_UI");
                        }
                    }
                }
            });

            createANewVisitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFrame frame = new JFrame();
                    frame.setTitle("Add a new visit record");
                    Visit_Form vf = new Visit_Form(id);
                    frame.setContentPane(vf.panel);
                    frame.pack();
                    frame.setVisible(true);
                    frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    frame.setLocationRelativeTo(null);
                }
            });

        }catch (Exception e1)
        {
            JOptionPane.showMessageDialog(null,"No Data Found");
        }
    }
}
