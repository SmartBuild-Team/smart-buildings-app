package edu.episen.si.ing1.pds.client.swing.global;

import edu.episen.si.ing1.pds.client.network.Request;
import edu.episen.si.ing1.pds.client.network.Response;
import edu.episen.si.ing1.pds.client.swing.global.shared.Ui;
import edu.episen.si.ing1.pds.client.swing.global.shared.toast.Toast;
import edu.episen.si.ing1.pds.client.utils.Utils;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class CompanyFrame extends JPanel {
    private JComboBox comboBox;
    private List<Map> companies;

    private Main app;
    public CompanyFrame(Main app) {
        this.app = app;

        JPanel mainJPanel = getMainJPanel();

        addLogo(mainJPanel);

        addSeparator(mainJPanel);

        addComboBoxField(mainJPanel);

        addLoginButton(mainJPanel);

        copyRightText(mainJPanel);

        this.add(mainJPanel);
        this.setVisible(true);

    }

    private JPanel getMainJPanel() {
        Dimension size = new Dimension(800, 400);

        JPanel panel1 = new JPanel();
        panel1.setSize(size);
        panel1.setPreferredSize(size);
        panel1.setBackground(Ui.COLOR_BACKGROUND);
        panel1.setLayout(null);


        return panel1;
    }

    private void addSeparator(JPanel panel1) {
        JSeparator separator1 = new JSeparator();
        separator1.setOrientation(SwingConstants.VERTICAL);
        separator1.setForeground(Ui.COLOR_OUTLINE);
        panel1.add(separator1);
        separator1.setBounds(310, 80, 1, 240);
    }

    private void addLogo(JPanel panel1) {
        JLabel label1 = new JLabel();
        label1.setFocusable(false);
        label1.setIcon(Utils.getImageIconFromResource("icon/logo.png"));
        label1.setBounds(55, 120, 200, 110);
        label1.setVisible(true);
        panel1.add(label1);
    }

    private void addComboBoxField(JPanel panel1) {
        companies = getCompanyList();
        comboBox = new JComboBox(new Vector(companies));

        comboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if(value instanceof Map) {
                    setText(((Map) value).get("name").toString());
                }
                return this;
            }
        });

        comboBox.setBounds(423, 109, 250, 44);
        comboBox.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                comboBox.setForeground(Color.white);
                comboBox.setBorder(new LineBorder(Ui.COLOR_INTERACTIVE_DARKER));
                comboBox.setBackground(Ui.COLOR_BACKGROUND);
            }

            @Override
            public void focusLost(FocusEvent e) {
                comboBox.setForeground(Color.white);
                comboBox.setBorder(new LineBorder(Ui.COLOR_BACKGROUND));
                comboBox.setBackground(Ui.COLOR_BACKGROUND);
            }
        });

        panel1.add(comboBox);
    }

    private void addLoginButton(JPanel panel1) {
        final Color[] loginButtonColors = {Ui.COLOR_INTERACTIVE, Color.white};

        JLabel loginButton = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = Ui.get2dGraphics(g);
                super.paintComponent(g2);

                Insets insets = getInsets();
                int w = getWidth() - insets.left - insets.right;
                int h = getHeight() - insets.top - insets.bottom;
                g2.setColor(loginButtonColors[0]);
                g2.fillRoundRect(insets.left, insets.top, w, h, Ui.ROUNDNESS, Ui.ROUNDNESS);

                FontMetrics metrics = g2.getFontMetrics(Ui.FONT_GENERAL_UI);
                int x2 = (getWidth() - metrics.stringWidth(Ui.BUTTON_TEXT_LOGIN)) / 2;
                int y2 = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();
                g2.setFont(Ui.FONT_GENERAL_UI);
                g2.setColor(loginButtonColors[1]);
                g2.drawString(Ui.BUTTON_TEXT_LOGIN, x2, y2);
            }
        };

        loginButton.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                loginEventHandler();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                loginButtonColors[0] = Ui.COLOR_INTERACTIVE_DARKER;
                loginButtonColors[1] = Ui.OFFWHITE;
                loginButton.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                loginButtonColors[0] = Ui.COLOR_INTERACTIVE;
                loginButtonColors[1] = Color.white;
                loginButton.repaint();
            }
        });

        loginButton.setBackground(Ui.COLOR_BACKGROUND);
        loginButton.setBounds(423, 247, 250, 44);
        loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panel1.add(loginButton);
    }

    private void copyRightText(JPanel panel1) {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        JLabel cr = new JLabel("SmartBuilding Â© 2020 - " + year);
        cr.setForeground(Ui.OFFWHITE);
        cr.setOpaque(false);
        cr.setFont(Ui.FONT_COPYRIGHT);
        cr.setBounds(625, 320, 300, 50);
        panel1.add(cr);
    }

    private void loginEventHandler() {
        Toast toastr = new Toast(getMainJPanel());
        toastr.success("Veuillez Patientez ...");
        Map selectedMap = (Map) comboBox.getSelectedItem();
        Utils.setCompanyId((Integer) selectedMap.get("id_companies"), selectedMap.get("name").toString());
        app.loadSystemWindow();
    }

    private List<Map> getCompanyList() {
        Request req = new Request();
        req.setEvent("companies_list");
        Response response = Utils.sendRequest(req);
        return  (List<Map>) response.getMessage();
    }
}
