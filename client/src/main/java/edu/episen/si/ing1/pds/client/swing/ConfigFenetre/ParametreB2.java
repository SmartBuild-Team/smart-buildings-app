package edu.episen.si.ing1.pds.client.swing.ConfigFenetre;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ParametreB2 extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	JTextField Température_ExtérieureField = new JTextField();
	JTextField Température_IntérieureField = new JTextField();
	JTextField Baisser_StoreField = new JTextField();
	JTextField Teinture_VitreField = new JTextField();
	JTextField Eclairage_DirectField = new JTextField();
	JTextField Eclairage_IndirectField = new JTextField();

	JLabel Température_Extérieure = new JLabel("Température_Extérieure");

	JLabel Température_Intérieure = new JLabel("Température_Intérieure");

	JLabel Baisser_Store = new JLabel("Baisser_Store");

	public ParametreB2() {
		this.setVisible(true);
		this.setTitle("window");
		this.setSize(500, 400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());

		JPanel contentPane = (JPanel) this.getContentPane();
		contentPane.add(labelparametreB2(), BorderLayout.CENTER);
		contentPane.add(fieldparametreB2(), BorderLayout.EAST);
		
	}

	public JPanel labelparametreB2() {
		JPanel lab = new JPanel(new GridLayout(7, 1));
		lab.add(Température_Extérieure);
		lab.add(Température_Intérieure);

		lab.add(Baisser_Store);

		return lab;
	}

	public JPanel fieldparametreB2() {
		JPanel field1 = new JPanel(new GridLayout(7, 1, 20, 20));
		field1.setPreferredSize(new Dimension(200, 200));
		field1.add(Température_ExtérieureField);
		field1.add(Température_IntérieureField);

		field1.add(Baisser_StoreField);
		JButton bouton = new JButton("Valider");
		field1.add(bouton);
		bouton.addActionListener(this);
		return field1;
	}

	public void actionPerformed(ActionEvent e) {
		
		System.out.println(Température_ExtérieureField.getText());
		System.out.println(Température_IntérieureField.getText());
		System.out.println(Baisser_StoreField.getText());

		 
	
    }
}