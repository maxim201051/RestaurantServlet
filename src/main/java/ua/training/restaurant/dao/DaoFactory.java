package ua.training.restaurant.dao;

import ua.training.restaurant.dao.impl.JDBCDaoFactory;

/**
 * Created by Student on 26.01.2020
 */
public abstract class DaoFactory {
    private static DaoFactory daoFactory;

    public static DaoFactory getInstance() {
        if (daoFactory == null) {
            synchronized (DaoFactory.class) {
                if (daoFactory == null) {
                    daoFactory = new JDBCDaoFactory();
                }
            }
        }
        return daoFactory;
    }

    public abstract UserDao createUserDao();

    public abstract OrderDao createOrderDao();

    public abstract DishDao createDishDao();
}
