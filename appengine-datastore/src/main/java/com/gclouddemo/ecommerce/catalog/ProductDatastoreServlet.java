package com.gclouddemo.ecommerce.catalog;

import com.gclouddemo.ecommerce.catalog.bean.Product;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Result;
import com.googlecode.objectify.cmd.LoadType;
import com.googlecode.objectify.cmd.Loader;
import com.googlecode.objectify.cmd.Query;
import com.googlecode.objectify.cmd.Saver;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProductDatastoreServlet
  extends HttpServlet
{
  private static final String MIME_TYPE_JSON = "application/json";
  private static final String MIME_TYPE_HTML = "text/html";
  
  public ProductDatastoreServlet() {}
  
  public void doPost(HttpServletRequest req, HttpServletResponse resp)
    throws IOException
  {
    String catalogName = req.getParameter("catalogName");
    String name = req.getParameter("name");
    String desc = req.getParameter("desc");
    String price = req.getParameter("price");
    String unit = req.getParameter("unit");
    
    if ((!catalogName.equals("")) && (!name.equals("")) && (!desc.equals("")) && (!price.equals("")) && (!unit.equals("")))
    {

      Product product = new Product(catalogName, name, desc, price, unit);
      ObjectifyService.ofy().save().entity(product).now();
      try {
        req.getRequestDispatcher("/productSaved.jsp").forward(req, resp);
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
    else {
      try {
        req.getRequestDispatcher("addProduct.jsp").forward(req, resp);
      }
      catch (ServletException e) {
        e.printStackTrace();
      }
    }
  }
  
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException
  {
    String addProduct = req.getParameter("add");
    if (addProduct == null) {
      List products = getProducts();
      req.setAttribute("products", products);
      try
      {
        req.getRequestDispatcher("productListing.jsp").forward(req, resp);
      }
      catch (ServletException e) {
        e.printStackTrace();
      }
    }
    else {
      try {
        req.getRequestDispatcher("addProduct.jsp").forward(req, resp);
      }
      catch (ServletException e) {
        e.printStackTrace();
      }
    }
  }
  












  private List getProducts()
  {
    List<Product> products = ObjectifyService.ofy()
      .load()
      .type(Product.class)
      .order("-date")
      .limit(20000)
      .list();
    
    return products;
  }
  
  private void sendJson(HttpServletResponse response, String jsonStr) throws Exception {
    sendBody(response, jsonStr, "application/json");
  }
  
  private void sendHtml(HttpServletResponse response, String htmlStr) throws Exception {
    sendBody(response, htmlStr, "text/html");
  }
  
  private void sendBody(HttpServletResponse response, String bodyStr, String mimeType) throws Exception {
    response.setStatus(200);
    response.setContentType(mimeType);
    PrintWriter responseWriter = response.getWriter();
    if (bodyStr != null) {
      responseWriter.println(bodyStr);
    }
    
    responseWriter.close();
  }
}
