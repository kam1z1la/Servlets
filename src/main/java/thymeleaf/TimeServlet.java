package thymeleaf;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.FileTemplateResolver;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

@WebServlet("/time")
public class TimeServlet extends HttpServlet {
    private TemplateEngine engine;
    private FileTemplateResolver resolver;

    @Override
    public void init() throws ServletException {
        super.init();
        engine = new TemplateEngine();
        resolver = new FileTemplateResolver();

        resolver.setTemplateMode("HTML5");
        resolver.setPrefix("src/main/webapp/WEB-INF/templates/");
        resolver.setOrder(1);
        resolver.setCacheable(true);
        resolver.setCacheTTLMs(60000L);
        resolver.setCharacterEncoding("utf-8");
        engine.addTemplateResolver(resolver);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");

        String timezone = Optional.ofNullable(req.getParameter("timezone"))
                .orElseGet(() -> "UTC")
                .replaceAll(" ", "+");

        Map<String, Object> timezoneMap = new LinkedHashMap<>();
        timezoneMap.put("Date", LocalTime.now(ZoneId.of(timezone)));
        timezoneMap.put("Time", LocalDate.now(ZoneId.of(timezone)));
        timezoneMap.put("Time zone", ZoneId.of(timezone));

        Context simpleContext = new Context(
                Locale.ENGLISH,
                Map.of("timezone", timezoneMap)
        );

        engine.process("time/html", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }
}

//                resp.setContentType("text/html;charset=UTF-8");
//        resp.getWriter().write("<table>\n" +
//                "<tr>\n" +
//                "<td>Date</td>\n" +
//                "<td>Time</td>\n" +
//                "<td>Time zone</td>\n" +
//                "</tr>\n" +
//                "<tr>\n" +
//                "<td>" + LocalTime.now(ZoneId.of(timezone)) + "</td>\n" +
//                "<td>" + LocalDate.now(ZoneId.of(timezone)) + "</td>\n" +
//                "<td>" + ZoneId.of(timezone) + "</td>\n" +
//                "</tr>\n" +
//                "</table>");
//        resp.getWriter().close();