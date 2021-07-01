package edu.episen.si.ing1.pds.client.swing.ConfigFenetre.tools;

import edu.episen.si.ing1.pds.client.network.Request;
import edu.episen.si.ing1.pds.client.network.Response;
import edu.episen.si.ing1.pds.client.utils.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WindowRequests {
	private static Integer va_pars1;
	private static Integer pd_pars1;
	private static Integer vd_pars1;
	private static Integer pa_pars1;
	private static Integer vd_pars;
	private static Integer pd_pars;
	private static Integer va_pars;
	private static Integer pa_pars;

	public static List<Map> getWindowsByWS(int spaceId) {
		Request request = new Request();
		request.setEvent("windows_by_space_id");
		Map<String, Integer> hm = new HashMap<>();
		hm.put("space_id", spaceId);
		request.setData(hm);
		Response response = Utils.sendRequest(request);
		return (List<Map>) response.getMessage();
	}

	public static Map<String, Map> getWindowConfig(int positionID) {
		Request request = new Request();
		request.setEvent("window_config_info");
		Map<String, Integer> hm = new HashMap<>();
		hm.put("position_id", positionID);
		request.setData(hm);
		Response response = Utils.sendRequest(request);
		return (Map<String, Map>) response.getMessage();
	}

	public static Map<String, Map> getWindowConfig1(int StoreId) {
		Request request = new Request();
		request.setEvent("teinte");
		Map<String, Integer> hm = new HashMap<>();

		
		hm.put("entre_valdebulum", vd_pars1);

		
		hm.put("entre_pourdebutlum", pd_pars1);

		
		hm.put("entre_vallumaug", va_pars1);

		
		hm.put("entre_pourlumaug", pa_pars1);
		request.setData(hm);
		Response response = Utils.sendRequest(request);
		return (Map<String, Map>) response.getMessage();
	}

	public static Map<String, Map> getWindowConfig2(int StoreId) {
		Request request = new Request();
		request.setEvent("store");
		Map<String, Integer> hm = new HashMap<>();
	
		hm.put("entre_valdebutstore", vd_pars);

		hm.put("entre_pourdebutstore", pd_pars);

		hm.put("entre_valstoreaug", va_pars);

		hm.put("entre_pourstoreaug", pa_pars);
		request.setData(hm);
		Response response = Utils.sendRequest(request);
		return (Map<String, Map>) response.getMessage();
	}
}
