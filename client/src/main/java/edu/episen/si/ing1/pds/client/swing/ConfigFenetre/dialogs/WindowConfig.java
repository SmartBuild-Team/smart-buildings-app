package edu.episen.si.ing1.pds.client.swing.ConfigFenetre.dialogs;

import edu.episen.si.ing1.pds.client.swing.ConfigFenetre.tools.WindowRequests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class WindowConfig extends JDialog implements ActionListener {
	JButton boutonS = new JButton("Valider");
	JLabel labstoredebut, unite_labstoredebut, pourstoredebut, unite_pourstoredebut, labstoreaug, unite_labstoreaug,
			pourstoreaug, unité_pourstoreaug, unite_valstoreaug;
	JTextField entre_valdebutstore, entre_pourdebutstore, entre_valstoreaug, entre_pourstoreaug;

	JLabel lablumdebut, unite_lablumdebut, pourlumdebut, unite_pourlumdebut, lablumaug, unite_lablumeaug, pourlumaug,
			unité_pourlumaug, unite_vallumaug;
	JTextField entre_valdebulum, entre_pourdebutlum, entre_vallumaug, entre_pourlumaug;
	JButton boutonT = new JButton("Confirmer");

	private final Logger logger = LoggerFactory.getLogger(WindowConfig.class.getName());
	private JRadioButton wClosed, wMOpened, wOpened, sClosed, sOpened, tAuto, tManual, tpFChoice, tpSChoice;
	private ButtonGroup wG, wT, wS, wTt;
	private final Map<String, Map> data;
	// private final Map<String, Map> datas;
	private final Frame frame;
	private final int positionID;

	public WindowConfig(Frame frame, int positionID) {
		super(frame);
		this.frame = frame;
		this.positionID = positionID;

		/*
		 * hm.put("valeurtemp_debut", vd_pars);
		 * 
		 * hm.put("pourcentagetemp_debut", pd_pars);
		 * 
		 * hm.put("valeurtemp_avance", va_pars);
		 * 
		 * hm.put("pourcentagetemp_avance", pa_pars);
		 */

		this.setSize(1000, 1000);
		this.setPreferredSize(this.getSize());

		data = WindowRequests.getWindowConfig(positionID);

		;
		JPanel content = new JPanel(new BorderLayout(40, 40));

		if (data.size() > 0) {
			JPanel header = new JPanel(new GridLayout(2, 1, 20, 20));
			String[][] rows = {
					{ "Etat de fenetre",
							(int) data.get("window").get("window_state") == 0 ? "Fermée"
									: (int) data.get("window").get("window_state") == 1 ? "Mi-Ouverte" : "Ouverte" },
					{ "Store fermée", String.valueOf(data.get("store").get("status_blind")) },
					{ "Fenetre teinté", String.valueOf(data.get("teinte").get("status_stain")) },
					{ "T interieur", data.get("temp").get("inside_temperature") + " Â°C" },
					{ "T exterieur", data.get("temp").get("outside_temperature") + " Â°C" } };
			JTable table = new JTable(rows, new String[] { "-", "-" });
			JButton refresh = new JButton("Actualiser");
			refresh.setActionCommand("refresh");
			refresh.addActionListener(this);

			header.add(table);
			header.add(refresh);

			content.add(header, BorderLayout.NORTH);
		}

		JTabbedPane tp = new JTabbedPane();

		JPanel sTab = new JPanel();
		sTab.setLayout(new BoxLayout(sTab, BoxLayout.Y_AXIS));

		JLabel l = new JLabel(" Veuillez Choisir un choix pour le volet de la fenetre");

		wS = new ButtonGroup();
		String sClosedText = "Ferme le volet lorsque le capteur de la fenetre releve une valeur ";

		boutonS.setBounds(0, 1000, 50, 50);
		boutonS.addActionListener(this);
		sTab.add(boutonS);
		labstoredebut = new JLabel(
				"Valeur de la temperature exterieure a partir de laquelle le store commence a se fermer (entier)");

		labstoredebut.setBounds(58, 150, 770, 29);
		sTab.add(labstoredebut);

		entre_valdebutstore = new JTextField();
		entre_valdebutstore.setFont(new Font("Tahoma", Font.PLAIN, 32));
		entre_valdebutstore.setBounds(800, 150, 7, 4);
		sTab.add(entre_valdebutstore);

		unite_labstoredebut = new JLabel("degre");
		unite_labstoredebut.setFont(new Font("Tahoma", Font.PLAIN, 17));
		unite_labstoredebut.setBounds(880, 150, 210, 29);
		sTab.add(unite_labstoredebut);

		pourstoredebut = new JLabel("A combien de pourcent le store doit se fermer (entre 0 et 100)");
		pourstoredebut.setFont(new Font("Tahoma", Font.PLAIN, 18));
		pourstoredebut.setBounds(58, 200, 600, 29);
		sTab.add(pourstoredebut);

		entre_pourdebutstore = new JTextField();
		entre_pourdebutstore.setFont(new Font("Tahoma", Font.PLAIN, 32));
		entre_pourdebutstore.setBounds(620, 200, 70, 40);
		sTab.add(entre_pourdebutstore);
		entre_pourdebutstore.setColumns(10);

		unite_pourstoredebut = new JLabel("%");
		unite_pourstoredebut.setFont(new Font("Tahoma", Font.PLAIN, 17));
		unite_pourstoredebut.setBounds(700, 200, 210, 29);
		sTab.add(unite_pourstoredebut);

		labstoreaug = new JLabel("Si la valeur de cette temperature augmenter de... (positif)");
		labstoreaug.setFont(new Font("Tahoma", Font.PLAIN, 18));
		labstoreaug.setBounds(58, 250, 600, 29);
		sTab.add(labstoreaug);

		entre_valstoreaug = new JTextField();
		entre_valstoreaug.setFont(new Font("Tahoma", Font.PLAIN, 32));
		entre_valstoreaug.setBounds(620, 250, 70, 40);
		sTab.add(entre_valstoreaug);
		entre_valstoreaug.setColumns(10);

		unite_valstoreaug = new JLabel("degre");
		unite_valstoreaug.setFont(new Font("Tahoma", Font.PLAIN, 17));
		unite_valstoreaug.setBounds(700, 250, 210, 29);
		sTab.add(unite_valstoreaug);

		pourstoreaug = new JLabel("Le pourcentage de la store doit aussi augmenter de combien  (entre 0 et 20)");
		pourstoreaug.setFont(new Font("Tahoma", Font.PLAIN, 18));
		pourstoreaug.setBounds(58, 300, 700, 29);
		sTab.add(pourstoreaug);

		entre_pourstoreaug = new JTextField();
		entre_pourstoreaug.setFont(new Font("Tahoma", Font.PLAIN, 32));
		entre_pourstoreaug.setBounds(700, 300, 70, 40);
		sTab.add(entre_pourstoreaug);
		entre_pourstoreaug.setColumns(10);

		unité_pourstoreaug = new JLabel("%");
		unité_pourstoreaug.setFont(new Font("Tahoma", Font.PLAIN, 17));
		unité_pourstoreaug.setBounds(770, 300, 210, 29);
		sTab.add(unité_pourstoreaug);
		tp.addTab("Store", sTab);

		JPanel tPanel = new JPanel(new GridLayout(3, 1, 40, 40));
		// tPanel.add(new JLabel(" veuillez Choisir un choix pour la teinte de la
		// fenetre"));

		wTt = new ButtonGroup();
		JPanel tFirstP = new JPanel(new GridLayout(2, 1, 30, 30));
		tAuto = new JRadioButton("Teinte automatique");
		wTt.add(tAuto);

		boutonT.setBounds(0, 1000, 50, 50);
		tFirstP.add(boutonT);
		lablumdebut = new JLabel(
				"Valeur du niveau d'ensoleillement a partir de laquelle la vitre vont commence a se teinter (entier)");
		lablumdebut.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lablumdebut.setBounds(58, 150, 1000, 29);
		tFirstP.add(lablumdebut);

		entre_valdebulum = new JTextField();

		entre_valdebulum.setBounds(800, 150, 70, 40);
		tFirstP.add(entre_valdebulum);

		unite_lablumdebut = new JLabel("degre");

		unite_lablumdebut.setBounds(880, 150, 210, 29);
		tFirstP.add(unite_lablumdebut);

		pourlumdebut = new JLabel("A combien de pourcent la vitre doit se teinter (entre 0 et 100)");
		pourlumdebut.setFont(new Font("Tahoma", Font.PLAIN, 18));
		pourlumdebut.setBounds(58, 200, 1000, 29);
		tFirstP.add(pourlumdebut);

		entre_pourdebutlum = new JTextField();
		entre_pourdebutlum.setFont(new Font("Tahoma", Font.PLAIN, 32));
		entre_pourdebutlum.setBounds(620, 200, 70, 40);
		tFirstP.add(entre_pourdebutlum);
		;

		unite_pourlumdebut = new JLabel("%");
		unite_pourlumdebut.setFont(new Font("Tahoma", Font.PLAIN, 17));
		unite_pourlumdebut.setBounds(700, 200, 210, 29);
		tFirstP.add(unite_pourlumdebut);

		lablumaug = new JLabel("Si la valeur de ce niveau d'ensoleillement augmente de... (positif)");
		lablumaug.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lablumaug.setBounds(58, 250, 600, 29);
		tFirstP.add(lablumaug);

		entre_vallumaug = new JTextField();
		entre_vallumaug.setFont(new Font("Tahoma", Font.PLAIN, 32));
		entre_vallumaug.setBounds(620, 250, 70, 40);
		tFirstP.add(entre_vallumaug);

		unite_vallumaug = new JLabel("degre");
		unite_vallumaug.setFont(new Font("Tahoma", Font.PLAIN, 17));
		unite_vallumaug.setBounds(700, 250, 210, 29);
		tFirstP.add(unite_vallumaug);

		pourlumaug = new JLabel("Le pourcentage de la teinte doit aussi augmenter de combien  (entre 0 et 20)");
		pourlumaug.setFont(new Font("Tahoma", Font.PLAIN, 18));
		pourlumaug.setBounds(58, 300, 700, 29);
		tFirstP.add(pourlumaug);

		entre_pourlumaug = new JTextField();
		entre_pourlumaug.setFont(new Font("Tahoma", Font.PLAIN, 32));
		entre_pourlumaug.setBounds(700, 300, 70, 40);
		tFirstP.add(entre_pourlumaug);

		unité_pourlumaug = new JLabel("%");
		unité_pourlumaug.setFont(new Font("Tahoma", Font.PLAIN, 17));
		unité_pourlumaug.setBounds(770, 300, 210, 29);
		tFirstP.add(unité_pourlumaug);
		tPanel.add(tFirstP);

		JPanel tSecondP = new JPanel(new GridLayout(2, 1, 30, 30));
		tManual = new JRadioButton("Maintenir une teinte constante de la fenetre");
		wTt.add(tManual);

		tSecondP.add(tManual);
		String tMLabel = "min_Teinte = %s, max_Teinte = %s)";
		tMLabel = String.format(tMLabel, "0", "100");

		tSecondP.add(new JLabel(tMLabel));

		tPanel.add(tSecondP);

		tp.addTab("Teinte", tPanel);

		JPanel tTab = new JPanel();
		tTab.setLayout(new GridLayout(2, 1, 40, 40));
		wT = new ButtonGroup();

		JPanel firstChoice = new JPanel(new GridLayout(3, 1, 40, 40));

		JLabel labeltempextfiel, labelunite, labelfenetre, labelinstructionR, labeltempintfiel, labelpStore,
				labelluminterne, labellumiexterne, labelpteinte, labelinstruction;
		JPanel paneA = new JPanel();
		paneA.setLayout(new BoxLayout(paneA, BoxLayout.Y_AXIS));
		JButton boutonA = new JButton("Actualiser");

		tp.addTab("EtatActuel", paneA);
		boutonA.setBounds(0, 1000, 50, 50);
		paneA.add(boutonA);

		labeltempextfiel = new JLabel("Temperature exterieure");
		labeltempextfiel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		labeltempextfiel.setBounds(58, 200, 210, 29);
		paneA.add(labeltempextfiel);

		labeltempextfiel = new JLabel("0");
		labeltempextfiel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		labeltempextfiel.setBounds(358, 200, 210, 29);
		paneA.add(labeltempextfiel);

		labelunite = new JLabel("degre");
		labelunite.setFont(new Font("Tahoma", Font.PLAIN, 18));
		labelunite.setBounds(400, 200, 210, 29);
		paneA.add(labelunite);

		labelluminterne = new JLabel("Niveau d'ensoleillement");
		labelluminterne.setFont(new Font("Tahoma", Font.PLAIN, 18));
		labelluminterne.setBounds(58, 250, 210, 29);
		paneA.add(labelluminterne);

		labelluminterne = new JLabel("0");
		labelluminterne.setFont(new Font("Tahoma", Font.PLAIN, 20));
		labelluminterne.setBounds(358, 250, 210, 29);
		paneA.add(labelluminterne);

		labelunite = new JLabel("lux");
		labelunite.setFont(new Font("Tahoma", Font.PLAIN, 18));
		labelunite.setBounds(400, 250, 210, 29);
		paneA.add(labelunite);

		labelpStore = new JLabel("Store ferme a ");
		labelpStore.setFont(new Font("Tahoma", Font.PLAIN, 18));
		labelpStore.setBounds(58, 300, 210, 29);
		labelpStore.setFont(labelpStore.getFont().deriveFont(20.0f));
		paneA.add(labelpStore);

		labelpStore = new JLabel("storeOuvert");
		labelpStore.setFont(new Font("Tahoma", Font.PLAIN, 25));
		labelpStore.setBounds(358, 300, 210, 29);
		labelpStore.setFont(labelpStore.getFont().deriveFont(22.0f));
		paneA.add(labelpStore);

		labelunite = new JLabel("%");
		labelunite.setFont(new Font("Tahoma", Font.PLAIN, 18));
		labelunite.setBounds(400, 300, 210, 29);
		paneA.add(labelunite);

		labelpteinte = new JLabel("Vitre teinte a ");
		labelpteinte.setFont(new Font("Tahoma", Font.PLAIN, 18));
		labelpteinte.setBounds(58, 350, 210, 29);
		labelpteinte.setFont(labelpStore.getFont().deriveFont(20.0f));
		paneA.add(labelpteinte);

		labelpteinte = new JLabel("pas_teinter");
		labelpteinte.setFont(new Font("Tahoma", Font.PLAIN, 25));
		labelpteinte.setBounds(358, 350, 210, 29);
		labelpteinte.setFont(labelpStore.getFont().deriveFont(20.0f));
		paneA.add(labelpteinte);

		labelunite = new JLabel("%");
		labelunite.setFont(new Font("Tahoma", Font.PLAIN, 18));
		labelunite.setBounds(400, 350, 210, 29);
		paneA.add(labelunite);

		content.add(tp, BorderLayout.CENTER);

		JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JButton confirmBtn = new JButton("Confirmer");
		confirmBtn.setActionCommand("submit");
		confirmBtn.addActionListener(this);

		content.add(footer, BorderLayout.PAGE_END);

		this.setContentPane(content);
		this.setVisible(true);
		this.pack();
		this.setLocationRelativeTo(frame);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o instanceof JButton) {
			JButton btn = (JButton) o;
			String cmd = btn.getActionCommand();
			if (cmd.equals("submit")) {
				final Map<String, Integer> inputs = new HashMap<>();
				inputs.put("update", data.size() == 0 ? 0 : 1);
				inputs.put("window", wClosed.isSelected() ? 0 : wMOpened.isSelected() ? 1 : 2);
				logger.info("Send request to backend:  {}", data);
			} else if (cmd.equals("close")) {
				this.dispose();
			} else if (cmd.equals("refresh")) {
				this.dispose();
				new WindowConfig(this.frame, this.positionID);
			}
		}
		if (o == boutonS) {

			String vd = entre_valdebutstore.getText();
			String pd = entre_pourdebutstore.getText();

			String va = entre_valstoreaug.getText();
			String pa = entre_pourstoreaug.getText();

			int vd_pars, pd_pars, va_pars, pa_pars;

			if (!isInteger(vd) || !isInteger(pd) || !isInteger(va) || !isInteger(pa)) {
				//JOptionPane.showMessageDialog(entre_valdebutstore, "Saisir un entier !", "ERREUR",
						//JOptionPane.ERROR_MESSAGE);
						
			
			} else 
			{
				vd_pars = Integer.parseInt(vd);
				if (vd_pars > 50 || vd_pars < -20) {
					JOptionPane.showMessageDialog(entre_valdebutstore,
							"La Valeur de debut de la temperature doit etre comprise entre -20 et 50 degre", "ERREUR",
							JOptionPane.ERROR_MESSAGE);
				}

				pd_pars = Integer.parseInt(pd);
				if (pd_pars > 100 || pd_pars < 0) 
				{
					JOptionPane.showMessageDialog(entre_pourdebutstore,
							"La pourcentage de debut doit etre comprise entre 0 et 100 %", "ERREUR",
							JOptionPane.ERROR_MESSAGE);
				}

				va_pars = Integer.parseInt(va);
				if (va_pars > 20 || va_pars < 0) 
				
				{
					JOptionPane.showMessageDialog(entre_valstoreaug,
							"L'augmentation de la valeur doit etre comprise entre 0 et 20 degre", "ERREUR",
							JOptionPane.ERROR_MESSAGE);
				}

				pa_pars = Integer.parseInt(pa);
				if (pa_pars > 20 || pa_pars < 0) 
				{
					JOptionPane.showMessageDialog(entre_pourstoreaug,
							"L'augmentation du pourcentage doit etre comprise entre 0 et 20 %", "ERREUR",
							JOptionPane.ERROR_MESSAGE);
				}

				if ((vd_pars <= 50 && vd_pars >= -20) && (pd_pars <= 100 && pd_pars >= 0)
						&& (va_pars <= 20 && vd_pars >= 0) && (pa_pars <= 20 && pd_pars >= 0)) 
				{

					if (o instanceof JButton) {
						JButton btn = (JButton) o;
						String cmd = btn.getActionCommand();
						if (cmd.equals("boutonS")) {
							final Map<String, Integer> inputs = new HashMap<>();
							inputs.put("entre_valdebutstore", vd_pars);
							inputs.put("entre_pourdebutstore", pd_pars);
							inputs.put("entre_valstoreaug", va_pars);
							inputs.put("entre_pourstoreaug", pa_pars);
							inputs.put("window", wClosed.isSelected() ? 0 : wMOpened.isSelected() ? 1 : 2);
							logger.info("Send request to backend:  {}", vd_pars, pd_pars, va_pars, pa_pars);
						
						System.out.println(inputs);
						JOptionPane.showMessageDialog(entre_valdebutstore, "configuration prise en compte",
								"INFORMATION", JOptionPane.INFORMATION_MESSAGE);
						}
					}
				}
						
							/*
							 * datas.put("valeurtemp_debut", vd_pars); datas.put("pourcentagetemp_debut",
							 * pd_pars); datas.put("valeurtemp_avance", va_pars);
							 * datas.put("pourcentagetemp_avance", pa_pars);
							 * 
							 * System.out.println(datas);
							 * 
							 * System.out.println(datas);
							 */
							
							if (o == boutonT) {
								String vd1 = entre_valdebulum.getText();
								String pd1 = entre_pourdebutlum.getText();

								String va1 = entre_vallumaug.getText();
								String pa1 = entre_pourlumaug.getText();

								int vd_pars1, pd_pars1, va_pars1, pa_pars1;

								if (!isInteger(vd1) || !isInteger(pd1) || !isInteger(va1) || !isInteger(pa1)) {
									JOptionPane.showMessageDialog(entre_valdebulum, "Saisir un entier !", "ERREUR",
											JOptionPane.ERROR_MESSAGE);
								} else {
									vd_pars1 = Integer.parseInt(vd1);
									if (vd_pars1 > 50 || vd_pars1 < 0) {
										JOptionPane.showMessageDialog(entre_valdebulum,
												"La Valeur de debut doit etre comprise entre 0 et 50 lux", "ERREUR",
												JOptionPane.ERROR_MESSAGE);
									}

									pd_pars1 = Integer.parseInt(pd1);
									if (pd_pars1 > 100 || pd_pars1 < 0) {
										JOptionPane.showMessageDialog(entre_pourdebutlum,
												"La pourcentage de debut doit etre comprise entre 0 et 100 %", "ERREUR",
												JOptionPane.ERROR_MESSAGE);
									}

									va_pars1 = Integer.parseInt(va1);
									if (va_pars1 > 20 || va_pars1 < 0) {
										JOptionPane.showMessageDialog(entre_vallumaug,
												"L'augmentation de la valeur doit etre comprise entre 0 et 20 lux",
												"ERREUR", JOptionPane.ERROR_MESSAGE);
									}

									pa_pars1 = Integer.parseInt(pa1);
									if (pa_pars1 > 20 || pa_pars1 < 0) {
										JOptionPane.showMessageDialog(entre_pourlumaug,
												"L'augmentation du pourcentage doit etre comprise entre 0 et 30 %",
												"ERREUR", JOptionPane.ERROR_MESSAGE);
									}

									if ((vd_pars1 <= 50 && vd_pars1 >= 0) && (pd_pars1 <= 100 && pd_pars1 >= 0)
											&& (va_pars1 <= 20 && va_pars1 >= 0) && (pa_pars1 <= 20 && pa_pars1 >= 0)) {
										;
										if (o instanceof JButton) {
											JButton btn1 = (JButton) o;
											String cmd1 = btn1.getActionCommand();
											if (cmd1.equals("boutonT")) {
												final Map<String, Integer> inputs1 = new HashMap<>();
												inputs1.put("entre_valdebulum", vd_pars1);
												inputs1.put("entre_pourdebutlum", pd_pars1);
												inputs1.put("entre_vallumaug", va_pars1);
												inputs1.put("entre_pourlumaug", pa_pars1);
												inputs1.put("window",
														wClosed.isSelected() ? 0 : wMOpened.isSelected() ? 1 : 2);
												logger.info("Send request to backend:  {}", vd_pars1, pd_pars1,
														va_pars1, pa_pars1);
												/*
												 * RequestSocket request = new RequestSocket();
												 * request.setRequest("lum"); Map<String, Object> data = new
												 * HashMap<>(); /*data.put("valeur_debut", vd_pars1);
												 * data.put("pourcentage_debut", pd_pars1); data.put("valeur_avance",
												 * va_pars1); data.put("pourcentage_avance", pa_pars1);
												 * 
												 * System.out.println(data); //request.setData(data);
												 * System.out.println(data);
												 */
												JOptionPane.showMessageDialog(entre_valdebulum,
														"configuration prise en compte", "INFORMATION",
														JOptionPane.INFORMATION_MESSAGE);

											}
										}
									}
								}
							}
						}
					
			}}
			
		
	

	private boolean isInteger(String vd) {
		// TODO Auto-generated method stub
		return false;
	}
}
