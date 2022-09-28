package servlets;

import model.Visitor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MainServlet extends HttpServlet {

    static List<Visitor> visitorsList = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter responseBody = resp.getWriter();
        resp.setContentType("text/html");

//        req.setAttribute("visitorsList", visitorsList);

        String userAgent = req.getHeader("User-Agent");
        String ipAddress = req.getRemoteAddr();
        LocalDateTime dateTime = LocalDateTime.now();

        Visitor visitor = new Visitor(ipAddress, userAgent, dateTime);


        responseBody.println("<h3>List of visitors:</h3>");
//        responseBody.println("<tr>");
        visitorsList.forEach(v -> {
            responseBody.println(v +"<br>");
        });
//        for (Visitor v: visitorsList) {
//            responseBody.println(v);
//        }
        responseBody.println("<b>"+ visitor +"</b>");

        visitorsList.add(visitor);

//        req.setAttribute("visitor", visitor);

//        req.getRequestDispatcher("/main.jsp").forward(req, resp);
    }
}