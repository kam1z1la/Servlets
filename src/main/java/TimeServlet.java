import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Optional;


@WebServlet("/time")
public class TimeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String timezone = Optional.ofNullable(req.getParameter("timezone"))
                .orElseGet(() -> "UTC")
                .replaceAll(" ","+");
        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().write("<table>\n" +
                "<tr>\n" +
                "<td>Date</td>\n" +
                "<td>Time</td>\n" +
                "<td>Time zone</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td>"+ LocalTime.now(ZoneId.of(timezone)) + "</td>\n" +
                "<td>"+ LocalDate.now(ZoneId.of(timezone)) +"</td>\n" +
                "<td>"+ ZoneId.of(timezone) +"</td>\n" +
                "</tr>\n" +
                "</table>");
        resp.getWriter().close();
    }
}
