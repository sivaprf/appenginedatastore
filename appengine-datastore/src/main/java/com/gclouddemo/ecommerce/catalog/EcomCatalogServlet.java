/**
 * 
 */
package com.gclouddemo.ecommerce.catalog;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gclouddemo.ecommerce.catalog.bean.CatalogItem;
import com.gclouddemo.ecommerce.catalog.cloudsql.EcomCatalogCloudSql;
import com.gclouddemo.ecommerce.catalog.renderer.EcomHtmlRenderer;
import com.gclouddemo.ecommerce.catalog.renderer.EcomJsonRenderer;
import com.google.apphosting.api.ApiProxy;

/**
 * App engine entry servlet that fields the main Ecommerce REST get calls for
 */
public class EcomCatalogServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(EcomCatalogServlet.class.getName());
	
    private static final String HOSTNAME_PROP_NAME = "com.google.appengine.runtime.default_version_hostname";
    
    private static final String CONNECTION_TYPE_CLOUDSQL = "cloudsql";
    private static final String CONNECTION_TYPE_TEST = "test";
    
    private static final String LOCAL_CONN_PROP_NAME = "cloudsql-local";
    private static final String DEPLOYED_CONN_PROP_NAME = "cloudsql-deployed";
    
    private static final String MIME_TYPE_JSON = "application/json";
    private static final String MIME_TYPE_HTML = "text/html";
    
    private static final String PARAM_QUERY_LIST_NAME = "l";
    private static final String PARAM_RENDER_TYPE_NAME = "r";
    private static final String PARAM_RENDER_JSON = "json";
    private static final String PARAM_RENDER_HTML = "html";
    
    private static final String PARAM_CATEGORY_NAME = "c";
    private static final String PARAM_SUBCATEGORY_NAME = "s";
       	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		EcomCatalogConnection conn = null;
		String listParam = request.getParameter(PARAM_QUERY_LIST_NAME);
		String renderType = request.getParameter(PARAM_RENDER_TYPE_NAME);
		String categoryName = request.getParameter(PARAM_CATEGORY_NAME);
		String subCategoryName = request.getParameter(PARAM_SUBCATEGORY_NAME);
		
		try {
			String url = getConnectionUrl(null);
			conn = getConnection(CONNECTION_TYPE_CLOUDSQL, url);
			if (conn != null) {
				if (listParam != null) {
					if (PARAM_RENDER_JSON.equalsIgnoreCase(renderType)) {
						sendJson(response,
								new EcomJsonRenderer().renderItemList(getItemList(conn, categoryName, subCategoryName),
															null, null));
					} else if (PARAM_RENDER_HTML.equalsIgnoreCase(renderType)) {
						sendHtml(response, new EcomHtmlRenderer().renderItemList(getItemList(conn, categoryName, subCategoryName),
								"<html><body>", "</body></html>"));
					}
				}
			}	
		} catch (Throwable thr) {
			LOG.log(Level.SEVERE, thr.getLocalizedMessage(), thr);
		}
		
	}
	
	private EcomCatalogConnection getConnection(String type, String url) {
		if (CONNECTION_TYPE_CLOUDSQL.equalsIgnoreCase(type)) {
			return new EcomCatalogCloudSql(url);
		} else if (CONNECTION_TYPE_TEST.equalsIgnoreCase(type)) {
			return new EcomCatalogCloudSql(null);
		}
		
		return null;
	}
	
	private List<CatalogItem> getItemList(EcomCatalogConnection conn, String category, String subCategory) throws Exception {
		try {
			if (conn != null) {
				conn.open();
				return conn.listItems(category, subCategory);
			} else  {
				LOG.log(Level.SEVERE, "Unable to connect to ecommerce catalog");
				return null;
			}
		} catch (Throwable thr) {
			LOG.log(Level.SEVERE, thr.getLocalizedMessage(), thr);
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return null;
	}
	
	private String getConnectionUrl(String type) {
		ApiProxy.Environment env = ApiProxy.getCurrentEnvironment();
		Map<String, Object> attr = env.getAttributes();
		String hostName = (String) attr.get(HOSTNAME_PROP_NAME);
		
		if (hostName != null && !hostName.isEmpty()) {
			if (!hostName.contains("localhost")) {
				return System.getProperty(DEPLOYED_CONN_PROP_NAME);
			}
		}
		
		return System.getProperty(LOCAL_CONN_PROP_NAME);
	}
	
	private void sendJson(HttpServletResponse response, String jsonStr) throws Exception {		
		sendBody(response, jsonStr, MIME_TYPE_JSON);
	}
	
	private void sendHtml(HttpServletResponse response, String htmlStr) throws Exception {
		sendBody(response, htmlStr, MIME_TYPE_HTML);
	}
	
	private void sendBody(HttpServletResponse response, String bodyStr, String mimeType) throws Exception {
		response.setStatus(HttpURLConnection.HTTP_OK);
		response.setContentType(mimeType);
		PrintWriter responseWriter = response.getWriter();
		if (bodyStr != null) {
			responseWriter.println(bodyStr);
		}
		
		responseWriter.close();
	}
}
