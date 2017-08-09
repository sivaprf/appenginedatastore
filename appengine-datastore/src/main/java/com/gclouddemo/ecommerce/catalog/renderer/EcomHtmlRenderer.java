/**
 * 
 */
package com.gclouddemo.ecommerce.catalog.renderer;

import java.util.List;
import java.util.logging.Logger;

import com.gclouddemo.ecommerce.catalog.EcomCatalogServlet;
import com.gclouddemo.ecommerce.catalog.bean.CatalogItem;

/**
 *
 */
public class EcomHtmlRenderer implements EcomCatalogRenderer {
	@SuppressWarnings("unused")
	private static final Logger LOG = Logger.getLogger(EcomCatalogServlet.class.getName());

	public String renderItemList(List<CatalogItem> items, String prefixStr, String suffixStr) {
		StringBuilder renderedItems = new StringBuilder();
		
		if (items != null) {
			renderedItems.append(prefixStr);
			for (CatalogItem item : items) {
				if (item != null) {
					renderedItems.append(renderCatalogItem(item));
				}
			}
			renderedItems.append(suffixStr);
		}
		
		return renderedItems.toString();
	}
	
	public String renderCatalogItem(CatalogItem item) {
		StringBuilder renderedItem = new StringBuilder();
		
		if (item != null) {
			renderedItem.append("<div class='gclouddemo_catalog_item'>");
			renderedItem.append("<h1>" + safeString(item.getSummary()) + "</h1>");
			renderedItem.append("<img src='" + safeString(item.getImage()) + "'>");
			renderedItem.append("<H2>" + safeString(item.getDescription()) + "</h2>");
			renderedItem.append("<p>Price: $" + safeString(item.getPrice().toPlainString()));
			renderedItem.append("<H2>" + safeString(item.getCategory()) + " / " + safeString(item.getSubcategory()) + "</h2>");
			renderedItem.append("</div>");
		}
		
		return renderedItem.toString();
	}
	
	private String safeString(String str) {
		if (str != null) {
			return str;
		} else {
			return "";
		}
	}
}
