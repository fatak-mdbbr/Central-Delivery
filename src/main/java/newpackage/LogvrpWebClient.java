package newpackage;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.time.Instant;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import newpackage.Order;
import newpackage.RequestProcess;
import newpackage.Station;
import newpackage.Vehicle;
import org.json.simple.parser.ParseException;

public class LogvrpWebClient {

    public static void main(String[] args) throws IOException, ParseException, SQLException {

        //reading from properties
        Properties prop = new Properties();
        InputStream input = null;
        input = new FileInputStream("config.properties");
        prop.load(input);
        String api_key = prop.getProperty("api_key");
        String databaseUrl = prop.getProperty("databaseUrl");
        System.out.println(api_key);
        System.out.println(databaseUrl);

        //urls
        String ticket_url = "http://logvrp.com/logvrpws/api/v1/Route.svc/rj/Ticket";
        String station_url = "http://logvrp.com/logvrpws/api/v1/Route.svc/rj/Stations";
        String distance_matrix_url = "http://logvrp.com/logvrpws/api/v1/Route.svc/rj/DistanceMatrix";
        String duration_matrix_url = "http://logvrp.com/logvrpws/api/v1/Route.svc/rj/DurationMatrix";
        String orders_url = "http://logvrp.com/logvrpws/api/v1/Route.svc/rj/Orders";
        String vehicles_url = "http://logvrp.com/logvrpws/api/v1/Route.svc/rj/Vehicles";
        String optimization_url = "http://logvrp.com/logvrpws/api/v1/Route.svc/rj/Optimization";
        String result_url = "http://logvrp.com/logvrpws/api/v1/Route.svc/rj/Result";

        //database
        ConnectionSource connectionSource = new JdbcConnectionSource(databaseUrl);
        Dao<Order,Integer> orderDao
                = DaoManager.createDao(connectionSource, Order.class);
        Dao<Station,Integer> stationDao
                = DaoManager.createDao(connectionSource, Station.class);
        Dao<Vehicle, Integer> vehicleDao
                = DaoManager.createDao(connectionSource, Vehicle.class);
        
        List<Order> order_list = orderDao.queryForAll();
        List<Station> station_list = stationDao.queryForAll();
        List<Vehicle> vehicle_list = vehicleDao.queryForAll();

        int[] algorithm_ids = {1, 2};
        int station_number = 5;
        int order_number = 4; 
        int vehicle_number = 2;

        RequestProcess req = new RequestProcess(api_key);
        String ticket = req.getTicket(ticket_url, algorithm_ids[0]);
        req.stationSet(station_url, ticket, station_list,station_number);
        req.disORdurMatrix(distance_matrix_url, ticket, "distanceMatrix");
        req.disORdurMatrix(duration_matrix_url, ticket, "durationMatrix");
        req.orderSet(orders_url, ticket, order_list,order_number);
        req.vehicleSet(vehicles_url, ticket,vehicle_list,order_number);
        long optim_time = req.Optimization(optimization_url, ticket);
        //System.out.println(optim_time);
        req.result(result_url, ticket, algorithm_ids[0]);
        /*
         ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);
         ScheduledFuture scheduledFuture = scheduledExecutorService.schedule(new Callable() {
         public Object call() throws Exception {
         req.result(result_url, ticket, algorithm_ids[0]);
         return "Called!";
         }
         },44, TimeUnit.SECONDS);
         scheduledExecutorService.shutdown();
         */
        // close the connection source
        connectionSource.close();
    }//main
}//class

