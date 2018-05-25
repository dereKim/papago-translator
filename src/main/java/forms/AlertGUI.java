package forms;

import helper.Utils;

import javax.swing.*;
import java.awt.event.*;

public class AlertGUI extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JLabel label;

    public AlertGUI() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    public void setText(String text) {
        label.setText(text);
    }

    public void openWithText(String text) {
        label.setText(text);
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        Utils.setCenter(frame);
        frame.setVisible(true);
    }


}
