package forms;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import helper.Utils;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PreviewGUI {
    private JFrame frame;
    private JPanel panel;
    private JTextField textField;
    private JButton done;
    private Editor editor;
    private AnActionEvent event;

    public PreviewGUI(AnActionEvent event) {
        this.frame = new JFrame("Result");
        this.frame.setContentPane(panel);
        this.frame.pack();
        this.event = event;
        this.editor = event.getRequiredData(CommonDataKeys.EDITOR);

        addListener();
    }

    public void addListener() {
        done.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (textField.getText().trim().equals("")) {
                    Utils.UI.closeFrame(panel);
                } else {
                    Utils.UI.replaceSelectedText(editor, getTextFiledValue());
                    Utils.UI.closeFrame(panel);
                }
                super.mousePressed(e);
            }
        });
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    Utils.UI.closeFrame(panel);
                } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    Utils.UI.replaceSelectedText(editor, getTextFiledValue());
                    Utils.UI.closeFrame(panel);
                }
                super.keyTyped(e);
            }
        });
    }

    private String getTextFiledValue() {
        return textField.getText();
    }

    public void openWithText(String text) {

        if (text == null || text.trim().equals("")) return;

        textField.setText(text);
        textField.selectAll();

        Utils.UI.setCenter(frame);
        frame.setVisible(true);
    }

}
