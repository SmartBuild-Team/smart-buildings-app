package edu.episen.si.ing1.pds.backend.server.workspace.location;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.episen.si.ing1.pds.backend.server.network.Request;
import edu.episen.si.ing1.pds.backend.server.utils.Utils;

import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocationNetwork {

    public LocationNetwork(Request request, Connection connection, PrintWriter writer) throws Exception {
        String event = request.getEvent();
        Integer companyId = request.getCompanyId();
        final ObjectMapper mapper = new ObjectMapper();

        switch (event) {
            case "location_building_list":
                List<Map<String, Object>> buildingList = new ArrayList<>();
                String query = "SELECT * FROM buildings";
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(query);
                while (rs.next()) {
                    Map<String, Object> hm = new HashMap<>();
                    hm.put("id", rs.getInt("id_buildings"));
                    hm.put("name", rs.getString("name"));

                    buildingList.add(hm);
                }
                Map responseList = Utils.responseFactory(buildingList, event);
                String msg = mapper.writeValueAsString(responseList);
                writer.println(msg);
                break;
                
                
            case "location_building_byid":
                Map<String, Object> building = new HashMap<>();
                Integer building_id = request.getData().get("building_id").asInt();
                String query2 = "SELECT * FROM buildings WHERE id_buildings = ?";
                PreparedStatement stmt = connection.prepareStatement(query2);
                stmt.setInt(1, building_id);
                ResultSet rs2 = stmt.executeQuery();
                if (rs2.next()) {
                    building.put("id", rs2.getInt("id_buildings"));
                    building.put("name", rs2.getString("name"));
                }
                Map responseById = Utils.responseFactory(building, event);
                String msgById = mapper.writeValueAsString(responseById);
                writer.println(msgById);
                break;
                
                
            case "random_offer_openspace":
            	List<Map<String,Object>> offeropenspace = new ArrayList<>();
            	String query3 = "SELECT * FROM workspace where workspace_state='disponible' and workspace_type='Espace Ouvert' order by random() limit 1";
            	PreparedStatement statement1 = connection.prepareStatement(query3);
            	 ResultSet rs3 = statement1.executeQuery();
            	 while(rs3.next()) {
            		 Map<String, Object> mapOP = new HashMap<>();
            		 mapOP.put("id",rs3.getInt("id_workspace"));
            		 mapOP.put("size",rs3.getInt("size"));
            		 mapOP.put("price",rs3.getInt("price"));
            		 mapOP.put("employee",rs3.getInt("max_person"));
            		 offeropenspace.add(mapOP);
            	 }
            		 Map response3 = Utils.responseFactory(offeropenspace, event);
                 String msg3 = mapper.writeValueAsString(response3);
                 writer.println(msg3);
                 break;
                 
            case "random_offer_desk":
            	List<Map<String,Object>> offerdesk = new ArrayList<>();
            	String query4 = "SELECT * FROM workspace where workspace_state='disponible' and workspace_type='Bureau' order by random() limit 1";
            	PreparedStatement statement2 = connection.prepareStatement(query4);
            	 ResultSet rs4 = statement2.executeQuery();
            	 while(rs4.next()) {
            		 Map<String, Object> mapD = new HashMap<>();
            		 mapD.put("id",rs4.getInt("id_workspace"));
            		 mapD.put("size",rs4.getInt("size"));
            		 mapD.put("price",rs4.getInt("price"));
            		 mapD.put("employee",rs4.getInt("max_person"));
            		 offerdesk.add(mapD);
            	 }
            		 Map response4 = Utils.responseFactory(offerdesk, event);
                 String msg4 = mapper.writeValueAsString(response4);
                 writer.println(msg4);
                 break;
                 
                 
            case "random_offer":
            	List<Map<String,Object>> offer = new ArrayList<>();
            	String query5 = "SELECT * FROM workspace where workspace_state='disponible' order by random() limit 1";
            	PreparedStatement statement3 = connection.prepareStatement(query5);
            	 ResultSet rs5 = statement3.executeQuery();
            	 while(rs5.next()) {
            		 Map<String, Object> mapOF = new HashMap<>();
            		 mapOF.put("id",rs5.getInt("id_workspace"));
            		 mapOF.put("size",rs5.getInt("size"));
            		 mapOF.put("price",rs5.getInt("price"));
            		 mapOF.put("employee",rs5.getInt("max_person"));
            		 offer.add(mapOF);
            	 }
            		 Map response5 = Utils.responseFactory(offer, event);
                 String msg5 = mapper.writeValueAsString(response5);
                 writer.println(msg5);
                 break;
                 
            case "done_reservtion":
                Map<String, Object> reservat = new HashMap<>();
                Integer list_worksp = request.getData().get("list_workspace").asInt();
                Integer reserv_numb = request.getData().get("reserv_numb").asInt();
                Integer id_compa = request.getData().get("id_compa").asInt();
                Integer id_worksp = request.getData().get("id_worksp").asInt();
                String query6 = "UPDATE workspace SET workspace_state ='indisponible' where id_workspace= ?;"
                		+ "INSERT INTO reservations ('reservation_number','id_companies','id_workspace')VALUES('?','?','?');";
                PreparedStatement statement4 = connection.prepareStatement(query6);
                statement4.setInt(1, list_worksp);
                statement4.setInt(2, reserv_numb);
                statement4.setInt(3, id_compa);
                statement4.setInt(4, id_worksp);
                ResultSet rs6 = statement4.executeQuery();
                if (rs6.next()) {
                    reservat.put("id", rs6.getInt("id_workspace"));
                    reservat.put("state", rs6.getString("workspace_state"));
                }
                Map response6 = Utils.responseFactory(reservat, event);
                String msg6 = mapper.writeValueAsString(response6);
                writer.println(msg6);
                break;
                 
            case "reservation_list":
                Map<String, Object> list_reserv = new HashMap<>();
                Integer company = request.getData().get("company_id").asInt();
                String query7 = "SELECT * FROM buildings WHERE id_companies = ?";
                PreparedStatement statement5 = connection.prepareStatement(query7);
                statement5.setInt(1, company);
                ResultSet rs7 = statement5.executeQuery();
                if (rs7.next()) {
                    list_reserv.put("id", rs7.getInt("id_workspace"));
                }
                Map response7 = Utils.responseFactory(list_reserv, event);
                String msg7 = mapper.writeValueAsString(response7);
                writer.println(msg7);
                break;
                
            case "nb_reservation_list":
                Map<String, Object> nb_reserva = new HashMap<>();
                Integer company1 = request.getData().get("company_id").asInt();
                String query8 = "SELECT count(*) FROM buildings WHERE id_companies = ?";
                PreparedStatement statement6 = connection.prepareStatement(query8);
                statement6.setInt(1, company1);
                ResultSet rs8 = statement6.executeQuery();
                if (rs8.next()) {
      
                }
                Map response8 = Utils.responseFactory(nb_reserva, event);
                String msg8 = mapper.writeValueAsString(response8);
                writer.println(msg8);
                break;
                
            case "kill_reservation":
                Map<String, Object> ask_destroy = new HashMap<>();
                Integer company2 = request.getData().get("company_id").asInt();
                Integer workspace2 = request.getData().get("company_id").asInt();
                String query9 = "UPDATE workspace SET workspace_state ='disponible' where id_workspace= ?;";
                PreparedStatement statement7 = connection.prepareStatement(query9);
                statement7.setInt(1, company2);
                statement7.setInt(2, workspace2);
                break;
                
            case "buildings_list":
            	List<Map> response = new ArrayList<>();                            
                Statement statement8 = connection.createStatement();
                ResultSet rs9 = statement8.executeQuery("SELECT * FROM buildings");
                while (rs9.next()) {
                    Map bMap=new HashMap();
                    bMap.put("name", rs9.getString("name"));
                    response.add(bMap);
                }
                Map response9=Utils.responseFactory(response, event);
                String msg10=mapper.writeValueAsString(response9);
                writer.println(msg10);
                
            case "floors_list":
            	List<Map<Map,Object>> response10 = new ArrayList<>();                            
                Statement statement9 = connection.createStatement();
                Integer building_id1 = request.getData().get("building_id").asInt();
                ResultSet rs10 = statement9.executeQuery("SELECT * FROM floors WHERE id_buildings=?");
                ((PreparedStatement) statement9).setInt(1, building_id1);
                while (rs10.next()) {
                    Map fMap=new HashMap();
                    fMap.put("number", rs10.getString("floor_number"));
                    response10.add(fMap);
                }
                Map rsp11=Utils.responseFactory(response10, event);
                String msg11=mapper.writeValueAsString(response10);
                writer.println(msg11);
                
                
           
                

    }
}
}
