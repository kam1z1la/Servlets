import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import thymeleaf.TimeZoneData;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Locale;
import java.util.TimeZone;


@WebFilter("/time")
public class TimezoneValidateFilter extends HttpFilter {
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
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        TimeZoneData.TIMEZONE = getLastCookie(req, res, req.getParameter("timezone"));

        for (var time : TimeZone.getAvailableIDs()) {
            if (time.equals(TimeZoneData.TIMEZONE.toString())) {
                chain.doFilter(req, res);
            }
        }

        res.setStatus(400);
        res.setContentType("text/html");
        Cookie cookieRemove = new Cookie("lastTimezone", TimeZoneData.TIMEZONE.toString());
        cookieRemove.setMaxAge(0);
        res.addCookie(cookieRemove);
        Context simpleContext = new Context(Locale.ENGLISH);
        simpleContext.setVariable("errorContext", "Enter the correct timezone");
        engine.process("error", simpleContext, res.getWriter());
        res.getWriter().close();
    }

    private Object getLastCookie(HttpServletRequest req, HttpServletResponse res, String nameTimeZone) {
        HttpSession session = req.getSession();
        session.setMaxInactiveInterval(-1);

        if (nameTimeZone != null) {
            session.setAttribute("lastTimezone", nameTimeZone);
            Cookie cookie = new Cookie("lastTimezone", nameTimeZone);
            res.addCookie(cookie);
            return cookie.getValue();
        } else {
            for (var cookie : req.getCookies()) {
                    if (cookie.getName().equals("lastTimezone")) {
                        return cookie.getValue();

                }
            }
        }
        return "UTC";
    }
}