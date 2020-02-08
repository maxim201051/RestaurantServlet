package ua.training.restaurant.service;

import org.apache.log4j.Logger;
import ua.training.restaurant.dao.DaoFactory;
import ua.training.restaurant.dao.OrderDao;
import ua.training.restaurant.entity.Dish;
import ua.training.restaurant.entity.OrderUnit;
import ua.training.restaurant.entity.order.Order;
import ua.training.restaurant.entity.order.Order_Status;
import ua.training.restaurant.entity.user.User;
import ua.training.restaurant.exceptions.EmptyOrderException;
import ua.training.restaurant.exceptions.OrderNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.time.LocalDateTime.now;

/**
 * Created by Student
 */
public class OrderServiceImpl implements OrderService {
    private final static Logger log = Logger.getLogger(OrderServiceImpl.class);
    private DaoFactory daoFactory;

    public OrderServiceImpl() {
        daoFactory = DaoFactory.getInstance();
    }

    @Override
    public List<Order> findByUser(User user) throws Exception {
        try (OrderDao dao = daoFactory.createOrderDao()) {
            return dao.findByUser(user);
        }
    }

    @Override
    public Order findById(Long id) throws Exception {
        try (OrderDao dao = daoFactory.createOrderDao()) {
            return dao.findById(id).orElseThrow(OrderNotFoundException::new);
        }
    }

    @Override
    public Order save(Order order, User user) throws Exception {
        if (!order.isEmpty()) {
            order.setUser(user);
            order.setStatus(Order_Status.CREATED);
            order.setCreated(now());
            try (OrderDao dao = daoFactory.createOrderDao()) {
                return dao.save(order);
            }
        } else {
            throw new EmptyOrderException();
        }
    }

    @Override
    public List<Order> findByStatus(Order_Status order_status) throws Exception {
        try (OrderDao dao = daoFactory.createOrderDao()) {
            return dao.findByStatus(order_status);
        }
    }

    @Override
    public Order update(Order order) throws Exception {
        try (OrderDao dao = daoFactory.createOrderDao()) {
            return dao.update(order);
        }
    }

    private Optional<OrderUnit> findOrderUnitById(Order order, Long id) {
        return order.getOrderUnits().stream().filter(unit -> unit.getDish().getId().equals(id)).findFirst();
    }

    public Order addDish(Order order, Dish dish, int quantity) {
        Optional<OrderUnit> unit = this.findOrderUnitById(order, dish.getId());

        if (!unit.isPresent()) {
            unit = Optional.of(new OrderUnit());
            unit.get().setQuantity(0);
            unit.get().setDish(dish);
            order.getOrderUnits().add(unit.get());
        }
        int newQuantity = unit.get().getQuantity() + quantity;
        if (newQuantity <= 0) {
            order.getOrderUnits().remove(unit.get());
        } else {
            unit.get().setQuantity(newQuantity);
        }
        return order;
    }

    public Order updateDish(Order order, Long id, int quantity) {
        Optional<OrderUnit> unit = this.findOrderUnitById(order, id);
        unit.ifPresent(u -> {
            if (quantity <= 0) {
                order.getOrderUnits().remove(unit.get());
            } else {
                unit.get().setQuantity(quantity);
            }
        });
        return order;
    }

    public Order removeDish(Order order, Dish dish) {
        Optional<OrderUnit> unit = this.findOrderUnitById(order, dish.getId());
        unit.ifPresent(u -> order.getOrderUnits().remove(unit.get()));
        return order;
    }

    public Order updateQuantity(Order order1, Order order2) {
        Optional.ofNullable(order2).ifPresent(o -> {
            List<OrderUnit> units = order2.getOrderUnits();
            units.forEach(unit -> this.updateDish(order1, unit.getDish().getId(), unit.getQuantity()));
        });
        return order1;
    }

    @Override
    public List<Order> findByUserId(Long id) throws Exception {
        try (OrderDao dao = daoFactory.createOrderDao()) {
            List<Order> orders = dao.findByUserId(id);
            makeUserUnique(orders);
            return orders;
        }
    }

    @Override
    public void confirmOrder(Long id) throws Exception {
        Order order = findById(id);
        order.setAccepted(now());
        order.setStatus(Order_Status.ACCEPTED);
        update(order);
        KitchenService.cookDishes(order);
        update(order);
    }

    @Override
    public void setPaid(Order order) {
        order.setStatus(Order_Status.PAID);
        order.setPaid(now());
    }

    private void makeUserUnique(List<Order> orders) {
        User user = orders.get(0).getUser();
        orders.forEach(o -> o.setUser(user));
    }
}
