/**
 * 
 */
package com.gclouddemo.ecommerce.catalog.cloudsql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.gclouddemo.ecommerce.catalog.EcomCatalogConnection;
import com.gclouddemo.ecommerce.catalog.bean.CatalogItem;

/**
 *
 */
public class EcomCatalogCloudSql implements EcomCatalogConnection {
	
	private static final Logger LOG = Logger.getLogger(EcomCatalogCloudSql.class.getName());
	
	private static final String LIST_QUERY_ALL_STR = "SELECT * FROM gclouddemo_productstore.products";
	private static final String LIST_QUERY_CAT_STR =
			"SELECT * FROM gclouddemo_productstore.products WHERE category='%s'";
	private static final String LIST_QUERY_CAT_SUBCAT_STR =
			"SELECT * FROM gclouddemo_productstore.products WHERE category='%s' AND subcategory='%s'";
	
	private static final int PRODUCTS_ID_COL = 1;
	private static final int PRODUCTS_SUMMARY_COL = 2;
	private static final int PRODUCTS_DESCRIPTION_COL = 3;
	private static final int PRODUCTS_PRICE_COL = 4;
	private static final int PRODUCTS_THUMB_COL = 5;
	private static final int PRODUCTS_IMAGE_COL = 6;
	private static final int PRODUCTS_CATEGORY_COL = 8;
	private static final int PRODUCTS_SUBCATEGORY_COL = 9;
	private static final int PRODUCTS_DETAILS_COL = 10;

	private final String url;
	private Connection conn = null;
	
	public EcomCatalogCloudSql(String url) {
		this.url = url;
	}

	@Override
	public boolean open() throws Exception {
		if (this.url != null) {
			this.conn = DriverManager.getConnection(url);
		}
		
		return true;
	}

	@Override
	public void close() {
		if (this.conn != null) {
			try {
				if (!this.conn.isClosed()) {
					conn.close();
				}
			} catch (Throwable thr) {
				LOG.log(Level.SEVERE, thr.getLocalizedMessage(), thr);
			}
		}
	}

	@Override
	public List<CatalogItem> listItems(String category, String subCategory) throws Exception {
		List<CatalogItem> items = new ArrayList<CatalogItem>();
		ResultSet rs = null;
		StringBuilder ssb = new StringBuilder();
		Formatter formatter = new Formatter(ssb, Locale.US);
		
		try {
			if (conn != null) {
				if (category == null) {
					ssb.append(LIST_QUERY_ALL_STR);
				} else if (subCategory == null) {
					formatter.format(LIST_QUERY_CAT_STR, category);
				} else {
					formatter.format(LIST_QUERY_CAT_SUBCAT_STR, category, subCategory);
				}
				
				rs = conn.createStatement().executeQuery(ssb.toString());
				
				
			}
		} finally {
			if (rs != null) {
				while (rs.next()) {
					items.add(constructItem(rs));
				}
			}
			
			if (formatter != null) {
				formatter.close();
			}
		}
		
		return items;
	}
	
	private CatalogItem constructItem(ResultSet rs) throws SQLException {
		CatalogItem item = new CatalogItem();
		
		if (rs != null) {
			item.setId(rs.getInt(PRODUCTS_ID_COL));
			item.setSummary(rs.getString(PRODUCTS_SUMMARY_COL));
			item.setDescription(rs.getString(PRODUCTS_DESCRIPTION_COL));
			item.setPrice(rs.getBigDecimal(PRODUCTS_PRICE_COL));
			item.setThumb(rs.getString(PRODUCTS_THUMB_COL));
			item.setImage(rs.getString(PRODUCTS_IMAGE_COL));
			item.setCategory(rs.getString(PRODUCTS_CATEGORY_COL));
			item.setSubcategory(rs.getString(PRODUCTS_SUBCATEGORY_COL));
			item.setDetails(rs.getString(PRODUCTS_DETAILS_COL));
		}
		
		return item;
	}

	@Override
	public List<CatalogItem> searchItems(String searchString) throws Exception {
		return null;
	}
}
