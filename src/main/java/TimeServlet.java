import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebFilter("/time")
public class TimeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().write("<table>\n" +
                "<tr>\n" +
                "<td>Date</td>\n" +
                "<td>Time</td>\n" +
                "<td>Time zone</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td>12</td>\n" +
                "<td>12</td>\n" +
                "<td>zone</td>\n" +
                "</tr>\n" +
                "</table>");
        resp.getWriter().close();
    }

//    @Override
//    public void destroy() {
//        super.destroy();
//    }
//
//    @Override
//    public void init(ServletConfig config) throws ServletException {
//        super.init(config);
//        log("Servlet created");
//    }
}
