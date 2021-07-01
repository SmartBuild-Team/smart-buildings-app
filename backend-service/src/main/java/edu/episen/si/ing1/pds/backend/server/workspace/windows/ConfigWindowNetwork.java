package edu.episen.si.ing1.pds.backend.server.workspace.windows;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import javax.sound.sampled.Clip;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.episen.si.ing1.pds.backend.server.network.Request;
import edu.episen.si.ing1.pds.backend.server.utils.Utils;

public class ConfigWindowNetwork {
	private String id;
	private String vtemp_debut;
	private String ptemp;
	private String ptemp_augmente;
	private String vtemp_augment;
	private String valeur_debut;
	private String pourcentage_debut;
	private String valeur_augment;
	private String pourcentage_augmente;

    public ConfigWindowNetwork(Request request, Connection connection, PrintWriter writer) throws Exception {
        String event = request.getEvent();
        ObjectMapper mapper = new ObjectMapper();
        if(event.equalsIgnoreCase("store")) {
            List<Map> response = new LinkedList<>();
            //int spaceId = request.getData().get("space_id").asInt();
            String sql = "UPDATE configuration SET blind_level_start = " + vtemp_debut+ ", blind_percentage_start = "+ ptemp + ", blind_level_add = " + vtemp_augment + ", blind_percentage_add = " + ptemp_augmente + " WHERE id = 1";

            PreparedStatement statement = connection.prepareStatement(sql);
            //statement.setInt(1, spaceId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                LinkedHashMap<String, Object> lhm = new LinkedHashMap<>();
                lhm.put("vtemp_debut", rs.getInt("valeurtemp_debut"));
                lhm.put("space_id", rs.getInt("id_workspace"));
                lhm.put("ptemp", rs.getInt("pourcentagetemp_debut"));
                lhm.put("vtemp_augment", rs.getInt("valeurtemp_avance"));
                lhm.put("ptemp_augmente", rs.getInt("pourcentagetemp_avance"));
                
                response.add(lhm);
            }
            Map responseMsg=Utils.responseFactory(response, event);
            String serializedMsgString=mapper.writeValueAsString(responseMsg);
            writer.println(serializedMsgString);

        

        }
        
    
         else if(event.equalsIgnoreCase("windows_by_space_id")) {
            List<Map> response = new LinkedList<>();
            int spaceId = request.getData().get("space_id").asInt();
            String sql = "SELECT *,  case when (SELECT is_window FROM equipments WHERE equipments.id_equipments = ws.equipment_id) then true else false end as is_window FROM workspace_equipments ws WHERE  id_workspace = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, spaceId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                LinkedHashMap<String, Object> lhm = new LinkedHashMap<>();
                lhm.put("position_id", rs.getInt("id_workspace_equipments"));
                lhm.put("space_id", rs.getInt("id_workspace"));
                lhm.put("equipment_id", rs.getInt("equipment_id"));
                lhm.put("x", rs.getInt("gridx"));
                lhm.put("y", rs.getInt("gridy"));
                lhm.put("height", rs.getInt("gridheight"));
                lhm.put("width", rs.getInt("gridwidth"));
                lhm.put("state", rs.getString("etat"));
                lhm.put("is_window", rs.getBoolean("is_window"));
                response.add(lhm);
            }
            Map responseMsg=Utils.responseFactory(response, event);
            String serializedMsgString=mapper.writeValueAsString(responseMsg);
            writer.println(serializedMsgString);

        }
    
        if(event.equalsIgnoreCase("teinte")) {
            List<Map> response = new LinkedList<>();
          //int spaceId = request.getData().get("space_id").asInt();
            String sql =  "UPDATE configuration SET opacity_level_start = " + valeur_debut+ ", opacity_percentage_start = "+ pourcentage_debut + ", opacity_level_add = " + valeur_augment + ", opacity_percentage_add = " + pourcentage_augmente + " WHERE id = 1";

            PreparedStatement statement = connection.prepareStatement(sql);
           // statement.setInt(1, spaceId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                LinkedHashMap<String, Object> lhm = new LinkedHashMap<>();
                lhm.put("valeur_debut", rs.getInt("valeur_debut"));
                //lhm.put("space_id", rs.getInt("id_workspace"));
                lhm.put("pourcentage_debut", rs.getInt("pourcentage_debut"));
                lhm.put("valeur_augment", rs.getInt("valeur_avance"));
                lhm.put("pourcentage_augmente", rs.getInt("pourcentage_avance"));
                
                response.add(lhm);
            }
            Map responseMsg=Utils.responseFactory(response, event);
            String serializedMsgString=mapper.writeValueAsString(responseMsg);
            writer.println(serializedMsgString);

        

        }
        
    
            else if(event.equalsIgnoreCase("window_config_info")) {
                String sql = "SELECT w.* FROM setting s JOIN windows w on w.id = s.id_window WHERE s.position_id = ?";
                Map<String, Map> data = new HashMap<>();
                int pid = request.getData().get("position_id").asInt();
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, pid);
                ResultSet rs = statement.executeQuery();
                if(rs.next()) {
                    data.put("window", Map.of("window_state", rs.getInt("window_state")));
                    data.put("store", Map.of("status_blind", rs.getInt("status_blind")));
                    data.put("teinte", Map.of("status_stain", rs.getInt("status_stain")));
                    Map temp = new HashMap();
                    temp.put("inside_temperature", rs.getInt("inside_temperature"));
                    temp.put("outside_temperature", rs.getInt("outside_temperature"));
                    temp.put("sun_degree", rs.getInt("sun_degree"));
                    data.put("temp", temp);
                }

                Map responseMsg=Utils.responseFactory(data, event);
                String serializedMsgString=mapper.writeValueAsString(responseMsg);
                writer.println(serializedMsgString);

        }

    }


}
