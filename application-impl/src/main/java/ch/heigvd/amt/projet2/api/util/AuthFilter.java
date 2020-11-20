package ch.heigvd.amt.projet2.api.util;

import ch.heigvd.amt.projet2.entities.ApplicationEntity;
import ch.heigvd.amt.projet2.repositories.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
@Order(1)
public class AuthFilter implements Filter {
    @Autowired
    ApplicationRepository applicationRepository;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        if(req.getRequestURI().equals("/") ||
                req.getRequestURI().startsWith("/swagger") ||
                req.getRequestURI().startsWith("/v3") ||
                (req.getRequestURI().startsWith("/applications") && req.getMethod().equalsIgnoreCase("post")) ||
                req.getRequestURI().startsWith("/registerUser")){
            chain.doFilter(request, response);
            return;
        }

        String apiKey=req.getHeader("X-API-KEY");
        // si pas d'API Key fournit on envoie un 403
        if(apiKey==null) {
            res.setStatus(403);
            return;
        }

        try {
            UUID xApiKey = UUID.fromString(apiKey);
            List<ApplicationEntity> apps = applicationRepository.findByXApiKey(xApiKey);
            if(!apps.isEmpty()) {
                req.setAttribute("application", apps.get(0));
                chain.doFilter(request, response);
            }else{
                // si l'API Key ne correspond pas à une application on envoie un 403
                res.setStatus(401);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void destroy() { }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException { }
}
