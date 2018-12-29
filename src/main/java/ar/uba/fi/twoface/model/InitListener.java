package ar.uba.fi.twoface.model;

import org.pmw.tinylog.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Servlet listener for reading configuration flags on startup and initializing the {@link Model}.
 */
@WebListener
public class InitListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Logger.info("Initializing context.");
        ServletContext ctx = sce.getServletContext();

        String backendLocation = ctx.getInitParameter("backend");
        Model model = new ModelImpl(backendLocation);

        ctx.setAttribute(Model.CTX_ATTRIBUTE, model);
    }

}
