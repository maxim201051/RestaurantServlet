import org.apache.log4j.Logger;
import ua.training.restaurant.entity.RegexContainer;
import ua.training.restaurant.entity.user.Role;
import ua.training.restaurant.service.KitchenService;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Student on 27.01.2020
 */
public class App {
    final static Logger log = Logger.getLogger(KitchenService.class);

    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
        /*FileInputStream fis = new FileInputStream("D:\\EPAM\\RestaurantServlet\\src\\main\\resources\\queries.properties");
        Properties prop = new Properties();
        prop.load(fis);
        String query=MessageFormat.format(prop.getProperty("dishes.findAll"), "");
        System.out.println(query);*/
        System.out.println(RegexContainer.NAME_REGEX);
    }
}
