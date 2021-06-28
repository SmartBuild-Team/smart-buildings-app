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

    private final Logger logger = LoggerFactory.getLogger(WindowConfig.class.getName());
    private JRadioButton wClosed, wMOpened, wOpened, sClosed, sOpened, tAuto, tManual, tpFChoice, tpSChoice;
    private ButtonGroup wG, wT, wS, wTt;
    private final Map<String, Map> data;
    private final Frame frame;
    private final int positionID;

    public WindowConfig(Frame frame, int positionID) {
        super(frame);
        this.frame = frame;
        this.positionID = positionID;

        this.setSize(1000, 1000);
        this.setPreferredSize(this.getSize());

        data = WindowRequests.getWindowConfig(positionID);

        JPanel content = new JPanel(new BorderLayout(40, 40));

        if(data.size() > 0) {
            JPanel header = new JPanel(new GridLayout(2,1,20,20));
            String[][] rows = {
                    { "Etat de fenetre", (int)data.get("window").get("window_state") == 0 ? "Fermée" : (int)data.get("window").get("window_state") == 1 ? "Mi-Ouverte" : "Ouverte" },
                    { "Store fermée",  String.valueOf(data.get("store").get("status_blind")) },
                    { "Fenetre teinté",  String.valueOf(data.get("teinte").get("status_stain"))},
                    { "T interieur",   data.get("temp").get("inside_temperature") + " Â°C" },
                    { "T exterieur", data.get("temp").get("outside_temperature") + " Â°C" }
            };
            JTable table = new JTable(rows, new String[] { "-", "-" });
            JButton refresh = new JButton("Actualiser");
            refresh.setActionCommand("refresh");
            refresh.addActionListener(this);

            header.add(table);
            header.add(refresh);

            content.add(header, BorderLayout.NORTH);
        }


        JTabbedPane tp=new JTabbedPane();

        JPanel wTab = new JPanel();
        wTab.setLayout(new BoxLayout(wTab, BoxLayout.Y_AXIS));
        wG = new ButtonGroup();
        wClosed = new JRadioButton("fenetre fermée");
        wMOpened = new JRadioButton("fenetre vers ouverte");
        wOpened = new JRadioButton("fenetre ouverte");

        wG.add(wClosed); wG.add(wMOpened); wG.add(wOpened);

        wTab.add(new JLabel("Veuillez Choisissez un choix pour l'ouverture de fenetre"));
        wTab.add(Box.createRigidArea(new Dimension(5, 20)));
        wTab.add(wClosed);
        wTab.add(Box.createRigidArea(new Dimension(5, 20)));
        wTab.add(wMOpened);
        wTab.add(Box.createRigidArea(new Dimension(5, 20)));
        wTab.add(wOpened);
        tp.addTab("Fenetre", wTab);

        JPanel sTab = new JPanel();
        sTab.setLayout(new BoxLayout(sTab, BoxLayout.Y_AXIS));

        JLabel l = new JLabel(" Veuillez Choisir un choix pour le volet de la fenetre");

        wS = new ButtonGroup();
        String sClosedText = "Ferme le volet lorsque le capteur de la fenetre releve une valeur ";
         JLabel sClosedDescription =  new JLabel("\r"
                                            + "1 - A partir de quel valeur ..." + " 23 \n"
                                            + "    a quel percentage" + "23% \n"
                                            + "2- TOTOTOT" + " \n"
                                            + "     TITITITI");

        sClosed = new JRadioButton(sClosedText);
        wS.add(sClosed);

        sOpened = new JRadioButton("Maintenir le volet fermé a certain %");
        wS.add(sOpened);
        sTab.add(l); sTab.add(sClosed);  sTab.add(sClosedDescription); sTab.add(sOpened);


        tp.addTab("Store", sTab);


        JPanel tPanel = new JPanel(new GridLayout(3, 1, 40, 40));
        tPanel.add(new JLabel(" veuillez Choisir un choix pour la teinte de la fenetre"));

        wTt = new ButtonGroup();
        JPanel tFirstP = new JPanel(new GridLayout(2, 1, 30, 30));
        tAuto = new JRadioButton("Teinte auto");
        wTt.add(tAuto);
        String tLabel = "1- A partir de : %s la teinte, le pourcentage de teinte est : %s"
                                + "\n 2- Quand la valeur augmente de %s , on augmente la teinte de vitre de + %s ";
        tLabel = String.format(tLabel, "200", "5", "20", "5");
        JTextArea jta = new JTextArea(tLabel);

        tFirstP.add(tAuto);
        tFirstP.add(jta);

        tPanel.add(tFirstP);

        JPanel tSecondP = new JPanel(new GridLayout(2,1, 30, 30));
        tManual = new JRadioButton("Maintenir une teinte constante de la fenetre");
        wTt.add(tManual);

        tSecondP.add(tManual);
        String tMLabel = "min_Teinte = %s, max_Teinte = %s)";
        tMLabel = String.format(tMLabel, "0", "100");
        tSecondP.add(new JLabel(tMLabel));

        tPanel.add(tSecondP);

        tp.addTab("Teinte",tPanel);


        JPanel tTab = new JPanel();
        tTab.setLayout(new GridLayout(2, 1, 40, 40));
        wT = new ButtonGroup();

        JPanel firstChoice = new JPanel(new GridLayout(3, 1, 40, 40));
       tpFChoice = new JRadioButton("Choix 1");
        wT.add(tpFChoice);
        
        JSlider slider = new JSlider();
        slider.setMinimum(18);
        slider.setMaximum(22);
        slider.setMajorTickSpacing(1);
        slider.setPaintTrack(true);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        firstChoice.add(tpFChoice);
      
        firstChoice.add(slider);

       
        wT.add(tpFChoice);
      
        tTab.add(firstChoice);


        tp.addTab("Temperature", tTab);

        content.add(tp, BorderLayout.CENTER);

        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton confirmBtn = new JButton("Confirmer");
        confirmBtn.setActionCommand("submit");
        confirmBtn.addActionListener(this);
        JButton cancelBtn = new JButton("Annuler");
        cancelBtn.setActionCommand("close");
        cancelBtn.addActionListener(this);

        footer.add(confirmBtn);
        footer.add(cancelBtn);

        content.add(footer, BorderLayout.PAGE_END);

        this.setContentPane(content);
        this.setVisible(true);
        this.pack();
        this.setLocationRelativeTo(frame);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if(o instanceof JButton) {
            JButton btn = (JButton) o;
            String cmd = btn.getActionCommand();
            if(cmd.equals("submit")) {
                final Map<String, Integer> inputs = new HashMap<>();
                inputs.put("update", data.size() == 0 ? 0 : 1);
                inputs.put("window", wClosed.isSelected() ? 0 : wMOpened.isSelected() ? 1 : 2);
                logger.info("Send request to backend:  {}", data);
            } else if(cmd.equals("close")) {
                this.dispose();
            } else if(cmd.equals("refresh")) {
                this.dispose();
                new WindowConfig(this.frame, this.positionID);
            }
        }
    }
}
