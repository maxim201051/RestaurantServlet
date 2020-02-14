package ua.training.restaurant.servlet.filter;

import ua.training.restaurant.entity.user.User;
import ua.training.restaurant.servlet.request.UserRoleRequestWrapper;
import ua.training.restaurant.utils.AppUtils;
import ua.training.restaurant.utils.SecurityUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Student on 22.01.2020
 */

@WebFilter(filterName = "AuthFilter", urlPatterns = "/*")
public class AuthFilter implements Filter {
    public AuthFilter() {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        String servletPath = request.getServletPath();
        // User information successfully stored in  Session
        // (After successful login).
        User loginedUser = AppUtils.getLoginedUser(request.getSession());

        if (servletPath.equals("/")) {
            chain.doFilter(request, response);
            return;
        }
        HttpServletRequest wrapRequest = request;

        if (loginedUser != null) {
            // User Name
            String userName = loginedUser.getUsername();

            // Roles.
            List<String> roles = new ArrayList<>();
            loginedUser.getAuthorities().forEach(a -> roles.add(a.name()));


            // Old request packet with new Request with userName and Roles information.
            wrapRequest = new UserRoleRequestWrapper(userName, roles, request);
        }

        // Pages requiring login.
        if (SecurityUtils.isSecurityPage(request)) {
            System.out.println("Security page");
            // If user not logined,
            // Redirect to login page.
            if (loginedUser == null) {

                String requestUri = request.getRequestURI();

                // Save current page to redirect after successful login.
                int redirectId = AppUtils.storeRedirectAfterLoginUrl(request.getSession(), requestUri);

                response.sendRedirect(wrapRequest.getContextPath() + "/login?redirectId=" + redirectId);
                return;
            }

            // Check user has a valid role or not?
            boolean hasPermission = SecurityUtils.hasPermission(wrapRequest);
            if (!hasPermission) {

                RequestDispatcher dispatcher //
                        = request.getServletContext().getRequestDispatcher("/"+
                        loginedUser.getAuthorities().get(0).name().toLowerCase());
                System.out.println("request: "+request.getRequestURI());
                dispatcher.forward(request, response);
                return;
            }
        }

        chain.doFilter(wrapRequest, response);
    }

    @Override
    public void init(FilterConfig fConfig) throws ServletException {

    }
}
