package edu.episen.si.ing1.pds.client.swing.mapping.selim;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.SocketException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.swing.AbstractButton;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.RootPaneContainer;
import javax.swing.TransferHandler;
import javax.swing.border.LineBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import edu.episen.si.ing1.pds.client.network.Request;
import edu.episen.si.ing1.pds.client.network.Response;

import edu.episen.si.ing1.pds.client.swing.global.Main;
import edu.episen.si.ing1.pds.client.swing.global.Navigate;
import edu.episen.si.ing1.pds.client.swing.global.shared.toast.Toast;
import edu.episen.si.ing1.pds.client.utils.Utils;

public class Mapping_Window implements Navigate {
	Main global;
	private JPanel content = new JPanel();
	private Toast toaster;
	private Map clicked = new HashMap<>();

	public Mapping_Window(Main global) {
		this.global = global;
	}

	private JToolBar createToolBar() {

		JToolBar toolBar = new JToolBar();
		JButton retour = new JButton("Retour");
		retour.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				global.getBloc().removeAll();
				global.setupFrame();
				global.getFrame().pack();
			}
		});
		toolBar.add(retour);
		return toolBar;
	}

	private void menuScroll(JTree arbre2) {
		JPanel menuPanel = global.getMenu();
		JScrollPane menu = new JScrollPane(arbre2);

		menuPanel.removeAll();
		menu.setPreferredSize(new Dimension(200, 0));
		menuPanel.add(menu);
		menuPanel.invalidate();
		menuPanel.validate();
		menuPanel.repaint();
	}

	private JTree buildTree() {
		JTree tree = null;
		try {
			Request request = new Request();
			request.setEvent("tree_list");
			Response response = Utils.sendRequest(request);
			Map<Map, List<Map>> dataMap = (Map<Map, List<Map>>) response.getMessage();

			DefaultMutableTreeNode racine = new DefaultMutableTreeNode(Utils.getCompanyName());

			for (Object building : dataMap.keySet()) {
				Map buildingMap = Utils.toMap(building.toString());

				DefaultMutableTreeNode buildNode = new DefaultMutableTreeNode(buildingMap.get("name"));

				for (Object floor : dataMap.get(building)) {
					Map<Map, List> floorN = (Map) floor;

					for (Object works : floorN.keySet()) {
						Map floorMap = Utils.toMap(works.toString());

						DefaultMutableTreeNode floorNode = new DefaultMutableTreeNode(floorMap.get("floor"));
						buildNode.add(floorNode);
						for (List<Map> dataN : floorN.values()) {
							for (Map workspace : dataN) {
								DefaultMutableTreeNode workNode = new DefaultMutableTreeNode(workspace);
								floorNode.add(workNode);
							}
						}
					}
				}
				racine.add(buildNode);
			}
			tree = new JTree(racine);

			tree.setCellRenderer(new DefaultTreeCellRenderer() {
				@Override
				public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded,
						boolean leaf, int row, boolean hasFocus) {
					super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
					Object selected = ((DefaultMutableTreeNode) value).getUserObject();
					if (selected instanceof Map) {
						setText(((Map) selected).get("workspace_type").toString());
					}

					if (leaf && sel && hasFocus) {
						content.removeAll();

						content.setLayout(new BorderLayout());
						content.add(carte((Integer) ((Map) selected).get("id_workspace")));
						content.invalidate();
						content.validate();
						content.repaint();
					}
					return this;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tree;
	}

	private JPanel carte(Integer id) {
		Request request = new Request();
		request.setEvent("mapping_list");
		request.setData(Map.of("workspace_id", id));
		Response response = Utils.sendRequest(request);
		List<Map> data = (List<Map>) response.getMessage();

		JPanel carte = new JPanel(new GridBagLayout());
		Toast toaster = new Toast(carte);
		GridBagConstraints gcon = new GridBagConstraints();
		gcon.weightx = 1;
		gcon.weighty = 1;
		gcon.fill = GridBagConstraints.BOTH;

		Map<JButton, Map> equips = new HashMap<>();

		for (Map e : data) {

			Integer id_workspace_equipments = Integer.valueOf(e.get("id_workspace_equipments").toString());
			Integer id_equipments = Integer.valueOf(e.get("equipment_id").toString());
			String verif_etat = e.get("etat").toString();
			JButton btn = new JButton();
			equips.put(btn, e);
			if (!e.get("etat").equals("")) {
				ImageIcon icon = Utils.getImageIconFromResource(String.valueOf(e.get("etat")));
				btn.setIcon(icon);
				btn.setTransferHandler(new TransferHandler("text"));
			}
			btn.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent evt) {
					JButton bt = (JButton) evt.getSource();
					if (bt.getIcon() == null) {
						if ((Integer.valueOf(((AbstractButton) evt.getSource()).getText()) == id_equipments)
								&& (verif_etat == "")) {
							Map update = add_equipment(id_workspace_equipments, id_equipments);
							if (!(update.get("etat").equals("") || update.get("etat") == null)) {
								ImageIcon icon = Utils.getImageIconFromResource(String.valueOf(update.get("etat")));
								bt.setIcon(icon);
								bt.repaint();
								toaster.success("Mapping de l'équipement réussi !");
							}

						} else if (verif_etat != "") {
							toaster.warn("L'équipement est déjà mappé !");
						}

						else {
							toaster.error("Emplacement non réservé pour cet équipement");

						}
					} else {
						clicked.put("button", bt);
						clicked.put("equip", equips.get(bt));
					}

				}
			});
			gcon.gridx = Integer.valueOf(e.get("gridx").toString());
			gcon.gridy = Integer.valueOf(e.get("gridy").toString());
			gcon.gridheight = Integer.valueOf(e.get("gridheigth").toString());
			gcon.gridwidth = Integer.valueOf(e.get("gridwidth").toString());
			btn.setTransferHandler(new TransferHandler("text"));
			carte.add(btn, gcon);
		}
		return carte;
	}

	private void bloc_equipement() {

		JPanel bloc = global.getBloc();
		bloc.setLayout(new GridLayout(6, 1));

		ImageIcon icon1 = Utils.getImageIconFromResource("icon/ecran.png");
		ImageIcon icon2 = Utils.getImageIconFromResource("icon/capteur.png");
		ImageIcon icon3 = Utils.getImageIconFromResource("icon/prise.png");
		ImageIcon icon4 = Utils.getImageIconFromResource("icon/fenetre.png");

		JLabel label0 = new JLabel(
				"<html><p><br>Pour activer un équipement <br>"
						+ "  merci de le déplacer vers <br>son emplacement respectif<br>" + "<br>"
						+ "Une icone de couleur verte :<br>" + "l'équipement est actif<br>"
						+ "Une icône de couleur rouge :<br>" + "l'équipement est hors service" + "</p></html>",
				JLabel.CENTER);

		JLabel label1 = new JLabel("1", icon1, JLabel.CENTER);
		JLabel label2 = new JLabel("2", icon2, JLabel.CENTER);
		JLabel label3 = new JLabel("3", icon3, JLabel.CENTER);
		JLabel label4 = new JLabel("4", icon4, JLabel.CENTER);

		DragMouseAdapter listener = new DragMouseAdapter();
		label1.addMouseListener(listener);
		label2.addMouseListener(listener);
		label3.addMouseListener(listener);
		label4.addMouseListener(listener);

		label1.setTransferHandler(new TransferHandler("text"));
		label2.setTransferHandler(new TransferHandler("text"));
		label3.setTransferHandler(new TransferHandler("text"));
		label4.setTransferHandler(new TransferHandler("text"));

		JPanel button = new JPanel(new FlowLayout());
		Toast toaster = new Toast(button);

		JButton delete = new JButton("Supprimer");
		delete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (clicked.size() > 0) {
					JButton cButton = (JButton) clicked.get("button");
					cButton.setIcon(null);

					Map equipdata = (Map) clicked.get("equip");
					Integer id_workspace_equipment = (Integer) equipdata.get("id_workspace_equipments");
					Map delete = delete_equipement(id_workspace_equipment);

					clicked.clear();
					toaster.success("Suppression réussite !");
					if (delete.get("etat").equals("")) {
						ImageIcon icon = Utils.getImageIconFromResource(String.valueOf(delete.get("etat")));
						cButton.setIcon(icon);
						cButton.repaint();
						cButton.getIcon();
						cButton.repaint();
						toaster.success("Suppression de l'équipement réussite !");
					}
				}
			}
		});
		JButton update = new JButton("Changer l'état");
		update.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (clicked.size() > 0) {
					JButton cButton = (JButton) clicked.get("button");
					Map equipdata = (Map) clicked.get("equip");
					Integer id_workspace_equipment = (Integer) equipdata.get("id_workspace_equipments");
					Integer id_equipment = (Integer) equipdata.get("equipment_id");
					Map update = update_equipement(id_workspace_equipment, id_equipment);
					if (!(update.get("etat").equals("") || update.get("etat") == null)) {
						ImageIcon icon = Utils.getImageIconFromResource(String.valueOf(update.get("etat")));
						cButton.setIcon(icon);
						cButton.repaint();
						toaster.error("Equipement hors service ! ");
					}
				}
			}
		});

		button.add(update);
		button.add(delete);
		bloc.add(label0);
		bloc.add(label1);
		bloc.add(label2);
		bloc.add(label3);
		bloc.add(label4);
		bloc.add(button);
		bloc.setVisible(true);
		bloc.repaint();

	}

	private class DragMouseAdapter extends MouseAdapter {

		public void mousePressed(MouseEvent e) {

			JComponent c = (JComponent) e.getSource();
			TransferHandler handler = c.getTransferHandler();
			handler.exportAsDrag(c, e, TransferHandler.COPY);
		}
	}

	private Map add_equipment(int id_workspace_equipment, int equipment_id) {

		Request request = new Request();
		request.setEvent("add_equipment");
		request.setData(Map.of("id_workspace_equipments", id_workspace_equipment, "equipment_id", equipment_id));
		Response response = Utils.sendRequest(request); // Object POJO converti en String <=> Serialization JSON
		return (Map) response.getMessage();
	}

	private Map delete_equipement(int id_workspace_equipment) {
		Request request = new Request();
		request.setEvent("delete_equipment");
		request.setData(Map.of("id_workspace_equipments", id_workspace_equipment));
		Response response = Utils.sendRequest(request);
		return (Map) response.getMessage();
	}

	private Map update_equipement(int id_workspace_equipment, int equipment_id) {
		Request request = new Request();
		request.setEvent("update_equipment");
		request.setData(Map.of("id_workspace_equipments", id_workspace_equipment, "equipment_id", equipment_id));
		Response response = Utils.sendRequest(request);
		return (Map) response.getMessage();
	}

	@Override
	public void start() {
		JPanel contentPane = global.getContext();
		BorderLayout borderLayout = new BorderLayout();
		borderLayout.setHgap(20);
		borderLayout.setVgap(20);
		contentPane.setLayout(borderLayout);
		JTree arbre = buildTree();
		menuScroll(arbre);
		bloc_equipement();

		JLabel label = new JLabel("<html><p>Bienvenue sur l'interface de configuration des équipements<br>"
				+ "Veuillez sélectionnez un espace à l'aide du menu" + "</p></html>");
		content.add(label);
		contentPane.add(content);
		contentPane.add(createToolBar(), BorderLayout.NORTH);
		global.getFrame().pack();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
}