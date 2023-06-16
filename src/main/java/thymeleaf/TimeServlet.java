package thymeleaf;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

@WebServlet("/time")
public class TimeServlet extends HttpServlet {
    private TemplateEngine engine;

    @Override
    public void init() throws ServletException {
        super.init();
        engine = new TemplateEngine();

        ClassLoaderTemplateResolver loaderTemplateResolver = new ClassLoaderTemplateResolver();
        loaderTemplateResolver.setTemplateMode("HTML5");
        loaderTemplateResolver.setPrefix("/templates/");
        loaderTemplateResolver.setSuffix(".html");
        loaderTemplateResolver.setOrder(1);
        loaderTemplateResolver.setCacheable(true);
        loaderTemplateResolver.setCacheTTLMs(60000L);
        loaderTemplateResolver.setCharacterEncoding("utf-8");
        engine.addTemplateResolver(loaderTemplateResolver);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");

        String timezone = TimeZoneData.TIMEZONE.toString();

        Map<String, Object> timezoneMap = new LinkedHashMap<>();
        timezoneMap.put("Date", LocalTime.now(ZoneId.of(timezone)));
        timezoneMap.put("Time", LocalDate.now(ZoneId.of(timezone)));
        timezoneMap.put("Time zone", ZoneId.of(timezone));

        Context simpleContext = new Context(
                Locale.ENGLISH,
                Map.of("timezone", timezoneMap)
        );

        engine.process("time", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        TimeZoneData.TIMEZONE = req.getParameter("timezone");
        resp.sendRedirect("/Servlet/time");
    }
}