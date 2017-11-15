package myservice.mynamespace.web;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.olingo.server.api.OData;
import org.apache.olingo.server.api.ODataHttpHandler;
import org.apache.olingo.server.api.ServiceMetadata;
import org.apache.olingo.server.api.edmx.EdmxReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import myservice.mynamespace.service.DemoEdmProvider;
import myservice.mynamespace.service.DemoEntityCollectionProcessor;

public class DemoServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;
  private static final Logger LOG = LoggerFactory.getLogger(DemoServlet.class);

  protected void service(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
    try {
      // create odata handler and configure it with CsdlEdmProvider and Processor
      OData odata = OData.newInstance();
      ServiceMetadata edm = odata.createServiceMetadata(new DemoEdmProvider(), new ArrayList<EdmxReference>());
      ODataHttpHandler handler = odata.createHandler(edm);
      handler.register(new DemoEntityCollectionProcessor());

      // let the handler do the work
      handler.process(req, resp);
    } catch (RuntimeException e) {
      LOG.error("Server Error occurred in ExampleServlet", e);
      throw new ServletException(e);
    }
  }
}
