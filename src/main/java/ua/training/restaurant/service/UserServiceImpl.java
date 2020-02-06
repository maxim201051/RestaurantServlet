package ua.training.restaurant.service;

import org.apache.log4j.Logger;
import ua.training.restaurant.dao.DaoFactory;
import ua.training.restaurant.dao.UserDao;
import ua.training.restaurant.entity.order.Order;
import ua.training.restaurant.entity.user.Role;
import ua.training.restaurant.entity.user.User;
import ua.training.restaurant.exceptions.UserNotFoundException;
import ua.training.restaurant.servlet.command.AcceptOrder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Student
 */
public class UserServiceImpl implements UserService {
    private DaoFactory daoFactory = DaoFactory.getInstance();
    private final static Logger log = Logger.getLogger(UserServiceImpl.class);


    @Override
    public User save(User user) {
        try (UserDao dao = daoFactory.createUserDao()) {
            return dao.save(user);
        } catch (Exception e) {
            log.error(e);
        }
        return user;
    }

    @Override
    public User update(User user) {
        try (UserDao dao = daoFactory.createUserDao()) {
            return dao.update(user);
        } catch (Exception e) {
            log.error(e);
        }
        return user;
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) throws UserNotFoundException {
        User user = null;
        try (UserDao dao = daoFactory.createUserDao()) {
            user = dao.findByUsernameAndPassword(username, password).orElseThrow(UserNotFoundException::new);
        } catch (Exception e) {
            log.error(e);
        }
        return user;
    }

    public List<User> findAllUsers() { //means role.user
        try (UserDao dao = daoFactory.createUserDao()) {
            return dao.findByAuthoritiesContaining(Role.USER);
        } catch (Exception e) {
            log.error(e);
        }
        return new ArrayList<>();
    }

    @Override
    public User setDefaultParams(User user) {
        List<Role> authorities = new ArrayList<>();
        authorities.add(Role.USER);
        user.setAuthorities(authorities);
        user.setFunds(0L);
        user.setOrdersNumber(0);
        user.setOrdersTotalCost(0L);
        user.setRegistrationDate(LocalDate.now());
        return user;
    }

    @Override
    public User addOrderToStatistic(User user, Order order) {
        user.setFunds(user.getFunds() - order.getAmountTotal());
        user.setOrdersTotalCost(user.getOrdersTotalCost() + order.getAmountTotal());
        user.setOrdersNumber(user.getOrdersNumber() + 1);
        return user;
    }

    @Override
    public User addFunds(User user, Long funds) {
        user.setFunds(user.getFunds() + funds);
        update(user);
        return user;
    }
}
