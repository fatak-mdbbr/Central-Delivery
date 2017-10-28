package newpackage;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class RequestProcess {

    //variables
    private String api_key;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient();

    //constructor 
    public RequestProcess(String apiKey) {
        api_key = apiKey;
    }

    //methods
    String post(String url, String jsonText) throws IOException {
        RequestBody body = RequestBody.create(JSON, jsonText);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    String getTicket(String url, int algo_id) throws IOException, ParseException {
        //encode json
        JSONObject obj = new JSONObject();
        obj.put("apiKey", api_key);
        JSONArray array = new JSONArray();
        array.add(algo_id);
        obj.put("algorithmIDs", array);
        StringWriter out = new StringWriter();
        obj.writeJSONString(out);
        String encJson = out.toString();
        //post to service
        String post_output = post(url, encJson);
        //test
        System.out.println(post_output);
        //decode json
        JSONParser parser = new JSONParser();
        Object parsObj = parser.parse(post_output);
        JSONObject jsonObj = (JSONObject) parsObj;
        // System.out.println(jsonObj);
        String data = (String) jsonObj.get("Data");
        // System.out.println(data);
        return data;
    }//getTicket

    void stationSet(String url,
            String computation_ticket_id,
            List<Station> station_list,
            int data_count) throws IOException, ParseException {

        //encode json
        JSONObject obj = new JSONObject();
        JSONObject obj_complete_set = new JSONObject();
        JSONObject obj_station_set = new JSONObject();

        obj.put("apiKey", api_key);
        obj.put("computationTicketID", computation_ticket_id);

        obj_complete_set.put("DistanceMatrix", null);
        obj_complete_set.put("DurationMatrix", null);

        obj_station_set.put("ID", 0);
        obj_station_set.put("SetName", "");
        obj_station_set.put("RelatedStationSetID", 0);
        obj_station_set.put("OrganizationID", 0);
        obj_station_set.put("CreatorUserID", 0);
        obj_station_set.put("CreatorUserName", "");
        obj_station_set.put("CreationTime", "");
        obj_station_set.put("LastUpdatedUserID", 0);
        obj_station_set.put("LastUpdatedTime", "");
        obj_station_set.put("DataCount", data_count);
        obj_station_set.put("IsDeleted", false);

        obj_complete_set.put("StationSet", obj_station_set);

        JSONArray json_st_array = new JSONArray();

        int numberOfStation = station_list.size();
        JSONObject[] eachStation = new JSONObject[numberOfStation];
        for (int i = 0; i < numberOfStation; i++) {
            eachStation[i] = new JSONObject();

            /* 1 */ eachStation[i].put("ID",(int)station_list.get(i).id);
            /* 2 */ eachStation[i].put("N",(String)station_list.get(i).name);
            /* 3 */ eachStation[i].put("SID", 0);
            /* 4 */ eachStation[i].put("OID", 0);
            /* 5 */ eachStation[i].put("CID", 0);
            /* 6 */ eachStation[i].put("X",(double)station_list.get(i).x);
            /* 7 */ eachStation[i].put("Y",(double)station_list.get(i).y);
            /* 8 */ eachStation[i].put("AD", "");
            /* 9 */ eachStation[i].put("AS", "");
            /* 10 */ eachStation[i].put("P", 0);
            /* 11 */ eachStation[i].put("A", true);
            /* 12 */ eachStation[i].put("DT",station_list.get(i).isDepot==0 ? false : true);
            /* 13 */ eachStation[i].put("D", false);
            /* 14 */ eachStation[i].put("BaseServiceTime",station_list.get(i).bst);
            /* 15 */ eachStation[i].put("C", false);

            json_st_array.add(eachStation[i]);
        }
        obj_complete_set.put("Stations", json_st_array);
        obj.put("completeStationSet", obj_complete_set);
        StringWriter out = new StringWriter();
        obj.writeJSONString(out);
        String enc_json = out.toString();
        //post to service
        String post_output = post(url, enc_json);
        //test
        System.out.println(post_output);
        //decode json
    }

    void disORdurMatrix(String url,
            String computation_ticket_id,
            String dis_or_dure) throws IOException, ParseException {

        //encode json
        JSONObject obj = new JSONObject();
        obj.put("apiKey", api_key);
        obj.put("computationTicketID", computation_ticket_id);
        obj.put(dis_or_dure, null);
        StringWriter out = new StringWriter();
        obj.writeJSONString(out);
        String enc_json = out.toString();
        //post to service
        String post_output = post(url, enc_json);
        //test
        System.out.println(post_output);
        //decode json
    }//method disORdureMatrix

    void orderSet(String url,
            String computation_ticket_id,
            List<Order> order_list,
            int data_count) throws IOException, ParseException {

        //encode json
        JSONObject obj = new JSONObject();
        JSONObject obj_complete_order_set = new JSONObject();
        JSONObject obj_order_set = new JSONObject();
        JSONArray json_or_array = new JSONArray();

        obj.put("apiKey", api_key);
        obj.put("computationTicketID", computation_ticket_id);

        obj_order_set.put("ID", 0);
        obj_order_set.put("SetName", "");
        obj_order_set.put("RelatedStationSetID", 0);
        obj_order_set.put("OrganizationID", 0);
        obj_order_set.put("CreatorUserID", 0);
        obj_order_set.put("CreatorUserName", "");
        obj_order_set.put("CreationTime", "");
        obj_order_set.put("LastUpdatedUserID", 0);
        obj_order_set.put("LastUpdatedTime", "");
        obj_order_set.put("DataCount", data_count);
        obj_order_set.put("IsDeleted", false);

        obj_complete_order_set.put("OrderSet", obj_order_set);

        int number_of_order = order_list.size();
        JSONObject[] each_order = new JSONObject[number_of_order];
        for (int i = 0; i < number_of_order; i++) {
            each_order[i] = new JSONObject();

            /* 1 */ each_order[i].put("ID",(int)order_list.get(i).id);
            /* 2 */ each_order[i].put("UID", "");
            /* 3 */ each_order[i].put("FI",(int)order_list.get(i).fi);
            /* 4 */ each_order[i].put("TI",(int)order_list.get(i).ti);
            /* 5 */ each_order[i].put("LT",(int)order_list.get(i).loadType);
            /* 6 */ each_order[i].put("A",(float)order_list.get(i).amount);
            /* 7 */ each_order[i].put("V",(float)order_list.get(i).volume);
            /* 8 */ each_order[i].put("W",(float)order_list.get(i).weight);
            /* 9 */ each_order[i].put("LD", "");
            /* 10 */ each_order[i].put("UD", "");
            /* 11 */ each_order[i].put("T1", null);
            /* 12 */ each_order[i].put("T2", null);
            each_order[i].put("T3",(String)order_list.get(i).t3);
            /* 14 */ each_order[i].put("T4",(String)order_list.get(i).t4);
            /* 15 */ each_order[i].put("D", false);
            /* 16 */ each_order[i].put("Priority", 0);

            json_or_array.add(each_order[i]);
        }
        obj_complete_order_set.put("Orders", json_or_array);
        obj.put("completeOrderSet", obj_complete_order_set);
        StringWriter out = new StringWriter();
        obj.writeJSONString(out);
        String enc_json = out.toString();
        //post to service
        String post_output = post(url, enc_json);
        //test
        System.out.println(post_output);
        //decode json
    }//method orderSet

    void vehicleSet(String url,
            String computation_ticket_id,
            List<Vehicle> vehicle_list,
            int data_count
    ) throws IOException, ParseException {

        //encode json
        JSONObject obj = new JSONObject();
        JSONObject obj_complete_vehicle_set = new JSONObject();
        JSONObject obj_vehicle_set = new JSONObject();
        JSONArray json_ve_array = new JSONArray();

        obj.put("apiKey", api_key);
        obj.put("computationTicketID", computation_ticket_id);

        obj_vehicle_set.put("ID", 0);
        obj_vehicle_set.put("SetName", "");
        obj_vehicle_set.put("RelatedStationSetID", 0);
        obj_vehicle_set.put("OrganizationID", 0);
        obj_vehicle_set.put("CreatorUserID", 0);
        obj_vehicle_set.put("CreatorUserName", "");
        obj_vehicle_set.put("CreationTime", "");
        obj_vehicle_set.put("LastUpdatedUserID", 0);
        obj_vehicle_set.put("LastUpdatedTime", "");
        obj_vehicle_set.put("DataCount", data_count);
        obj_vehicle_set.put("IsDeleted", false);

        obj_complete_vehicle_set.put("VehicleSet", obj_vehicle_set);

        int number_of_vehicle = vehicle_list.size();
        JSONObject[] each_vehicle = new JSONObject[number_of_vehicle];
        for (int i = 0; i < number_of_vehicle; i++) {
            each_vehicle[i] = new JSONObject();

            /* 1 */ each_vehicle[i].put("ID",(int)vehicle_list.get(i).id);
            /* 2 */ each_vehicle[i].put("N",(String)vehicle_list.get(i).name);
            /* 3 */ each_vehicle[i].put("VID", 0);
            /* 4 */ each_vehicle[i].put("OID", 0);
            /* 5 */ each_vehicle[i].put("CID", 0);
            /* 6 */ each_vehicle[i].put("BID",(int)vehicle_list.get(i).bid);
            /* 7 */ each_vehicle[i].put("EID",(int)vehicle_list.get(i).eid);
            /* 8 */ each_vehicle[i].put("LT",(int)vehicle_list.get(i).loadType);
            /* 9 */ each_vehicle[i].put("CP",(float)vehicle_list.get(i).cp);
            /* 10 */ each_vehicle[i].put("CW",(float)vehicle_list.get(i).cw);
            /* 11 */ each_vehicle[i].put("CV",(float)vehicle_list.get(i).cv);
            /* 12 */ each_vehicle[i].put("S",(float)vehicle_list.get(i).speed);
            each_vehicle[i].put("DC", 0.5);
            /* 14 */ each_vehicle[i].put("F", 5);
            /* 15 */ each_vehicle[i].put("DD",(String)vehicle_list.get(i).dd);
            /* 16 */ each_vehicle[i].put("A", true);
            /* 17 */ each_vehicle[i].put("D", false);
            /* 18 */ each_vehicle[i].put("Priority", 0);

            json_ve_array.add(each_vehicle[i]);
        }
        obj_complete_vehicle_set.put("Vehicles", json_ve_array);
        obj.put("completeVehicleSet", obj_complete_vehicle_set);
        StringWriter out = new StringWriter();
        obj.writeJSONString(out);
        String enc_json = out.toString();
        //post to service
        String post_output = post(url, enc_json);
        //test
        System.out.println(post_output);

        //decode json
    }//method vehicleSet

    long Optimization(String url, String computation_ticket_id) throws IOException, ParseException {

        //encode json
        JSONObject obj = new JSONObject();
        obj.put("apiKey", api_key);
        obj.put("computationTicketID", computation_ticket_id);
        obj.put("algorithmID", 1);
        JSONObject obj_computation_parameters = new JSONObject();
        JSONObject obj_availableHoursList = new JSONObject();
        JSONArray weekly_working_time = new JSONArray();
        JSONObject each_week_day = new JSONObject();

        JSONObject obj_start = new JSONObject();
        obj_start.put("hour", "00");
        obj_start.put("minute", "00");
        JSONObject obj_end = new JSONObject();
        obj_end.put("hour", "00");
        obj_end.put("minute", "00");
        JSONObject obj_p1 = new JSONObject();
        obj_p1.put("start", obj_start);
        obj_p1.put("end", obj_end);
        JSONObject obj_p2 = new JSONObject();
        obj_p2.put("start", obj_start);
        obj_p2.put("end", obj_end);
        each_week_day.put("P1", obj_p1);
        each_week_day.put("P2", obj_p2);
        for (int i = 0; i < 7; i++) {
            weekly_working_time.add(each_week_day);
        }
        JSONArray holidays = new JSONArray();

        obj_availableHoursList.put("weeklyWorkingTime", weekly_working_time);
        obj_availableHoursList.put("holidays", holidays);

        JSONObject obj_max_continuous_ride_time = new JSONObject();
        obj_max_continuous_ride_time.put("hour", "0");
        obj_max_continuous_ride_time.put("minute", "0");

        JSONObject obj_max_work_time_in_day = new JSONObject();
        obj_max_work_time_in_day.put("hour", "0");
        obj_max_work_time_in_day.put("minute", "0");

        JSONObject obj_min_pause_time = new JSONObject();
        obj_min_pause_time.put("hour", "0");
        obj_min_pause_time.put("minute", "0");

        JSONObject obj_max_route_duration = new JSONObject();
        obj_max_route_duration.put("day", "0");
        obj_max_route_duration.put("hour", "0");
        obj_max_route_duration.put("minute", "0");

        obj_computation_parameters.put("userID", 0);
        obj_computation_parameters.put("avoidRegions", null);
        obj_computation_parameters.put("currencyID", 1);
        obj_computation_parameters.put("distanceUnitID", 1);
        obj_computation_parameters.put("optimizationGoal", 1);
        obj_computation_parameters.put("useCFDinAlg", false);
        obj_computation_parameters.put("splitDeliveryPickupVehicles", false);
        obj_computation_parameters.put("avoidHighways", false);
        obj_computation_parameters.put("avoidTolls", false);
        obj_computation_parameters.put("vehicleEndOfDayLocation", 1);
        obj_computation_parameters.put("visitStationsOnce", false);
        obj_computation_parameters.put("partitionByLocations", false);
        obj_computation_parameters.put("allowDepotRevisitBySameVehicle", true);
        obj_computation_parameters.put("maxDistancePerRoute", 0);
        obj_computation_parameters.put("maxContinuousRideTime", obj_max_continuous_ride_time);
        obj_computation_parameters.put("maxWorkTimeInDay", obj_max_work_time_in_day);
        obj_computation_parameters.put("minPauseTime", obj_min_pause_time);
        obj_computation_parameters.put("maxNumberOfRoutes", 0);
        obj_computation_parameters.put("maxRouteDuration", obj_max_route_duration);
        obj_computation_parameters.put("maxStopsPerRoute", 0);
        obj_computation_parameters.put("availableHoursList", obj_availableHoursList);

        //json_or_array.add(each_order[i]);
        obj.put("computationParameters", obj_computation_parameters);
        StringWriter out = new StringWriter();
        obj.writeJSONString(out);
        String enc_json = out.toString();

        //post to service
        String post_output = post(url, enc_json);

        //test
        System.out.println(post_output);

        //decode json
        JSONParser parser = new JSONParser();
        Object pars_obj = parser.parse(post_output);
        JSONObject jsonObj = (JSONObject) pars_obj;
        // System.out.println(jsonObj);

        long data = (long) jsonObj.get("Data");

        return data;

        //decode json
    }//method optimization

    void result(String url, String computation_ticket_id, int algorithm_id) throws IOException, ParseException {
        //encode json
        JSONObject obj = new JSONObject();
        obj.put("apiKey", api_key);
        obj.put("computationTicketID", computation_ticket_id);
        obj.put("algorithmIDs", algorithm_id);
        StringWriter out = new StringWriter();
        obj.writeJSONString(out);
        String enc_json = out.toString();
        //post to service
        String post_output = post(url, enc_json);
        //test
        System.out.println(post_output);
        //decode json
        JSONParser parser = new JSONParser();
        Object pars_obj = parser.parse(post_output);
        JSONObject jsonObj = (JSONObject) pars_obj;
        System.out.println(jsonObj);
        // System.out.println(data);
    }
}//end of class requestProcess

