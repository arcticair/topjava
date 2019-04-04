package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@WebServlet(name = "meals", urlPatterns = "/meals")
public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to users");
        List<MealTo> m = MealsUtil.getFilteredWithExcess(MealsUtil.meals, LocalTime.of(00,00), LocalTime.of(23, 59), 1);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/meals.jsp");
        request.setAttribute("meallist", m);
        dispatcher.forward(request,response);
//        request.getRequestDispatcher("/users.jsp").forward(request, response);
 //       response.sendRedirect("users.jsp");
    }
}
