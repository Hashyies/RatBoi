package me.hashy.rat.OLD;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.DefaultCaret;

public class CommandEditor extends JFrame {

    // Frame Dimensions
    private static final int WIDTH = 800;
    private static final int HEIGHT = 450;
    // Panel Dimensions
    private static final int PANEL_WIDTH = WIDTH - 40;
    private static final int PANEL_HEIGHT = HEIGHT - 100;

    private JPanel panel;
    private JTextArea txtArea;
    private JTextField txtField;
    private JButton btnSend;
    private JButton btnDarkTheme;

    public CommandEditor() {
        initUI();
    }

    private void initUI() {
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBorder(new LineBorder(Color.BLACK, 3));
        panel.setBounds(20, 20, PANEL_WIDTH, PANEL_HEIGHT);
        panel.setBackground(Color.WHITE);

        txtArea = new JTextArea();
        txtArea.setBounds(10, 10, PANEL_WIDTH - 20, PANEL_HEIGHT - 90);
        txtArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        txtArea.setBorder(new EmptyBorder(7, 7, 7, 7));
        txtArea.setLineWrap(true);
        txtArea.setCaretColor(Color.BLACK);
        ((DefaultCaret) txtArea.getCaret()).setUpdatePolicy(DefaultCaret.NEVER_UPDATE);

        txtField = new JTextField();
        txtField.setBounds(10, PANEL_HEIGHT - 70, PANEL_WIDTH - 120, 30);
        txtField.setFont(new Font("Consolas", Font.PLAIN, 14));
        txtField.setBorder(new EmptyBorder(7, 7, 7, 7));
        txtField.setCaretColor(Color.BLACK);
        txtField.setHorizontalAlignment(SwingConstants.LEFT);

        btnSend = new JButton("Send");
        btnSend.setBounds(PANEL_WIDTH - 100, PANEL_HEIGHT - 70, 90, 30);
        btnSend.setFont(new Font("Arial", Font.PLAIN, 12));
        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Empty on click action
            }
        });

        btnDarkTheme = new JButton("Dark Theme");
        btnDarkTheme.setBounds(10, PANEL_HEIGHT - 30, 90, 30);
        btnDarkTheme.setFont(new Font("Arial", Font.PLAIN, 12));
        btnDarkTheme.addActionListener(e -> {
            if (panel.getBackground().equals(Color.WHITE)) {
                panel.setBackground(Color.BLACK);
                txtArea.setForeground(Color.WHITE);
                txtField.setForeground(Color.WHITE);
                txtArea.setCaretColor(Color.WHITE);
                txtField.setCaretColor(Color.WHITE);
            } else {
                panel.setBackground(Color.WHITE);
                txtArea.setForeground(Color.BLACK);
                txtField.setForeground(Color.BLACK);
                txtArea.setCaretColor(Color.BLACK);
                txtField.setCaretColor(Color.BLACK);
            }
        });

        panel.add(txtArea);
        panel.add(txtField);
        panel.add(btnSend);
        panel.add(btnDarkTheme);

        add(panel);

        setTitle("Command Editor");
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);
        setBackground(Color.WHITE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.BLACK);
        g.fillRoundRect(20, 20, PANEL_WIDTH, PANEL_HEIGHT, 14, 14);
    }

    public static void main(String[] args) {
        CommandEditor commandEditor = new CommandEditor();
        commandEditor.setVisible(true);
    }

}