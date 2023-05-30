import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.TimeZone;

@WebFilter("/time")
public class TimezoneValidateFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {

        for (var time : TimeZone.getAvailableIDs()) {
            if (time.equals(req.getParameter("timezone"))){
                chain.doFilter(req, res);
            }
        }
        res.setStatus(400);
        res.setContentType("text/html;charset=UTF-8");
        res.getWriter().write("<h1> Error "+ res.getStatus() +"</h1>"+
                        "<h2>Not found time zone</h2>" );
        res.getWriter().close();
    }
}
