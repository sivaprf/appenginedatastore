/**
 * 
 */
package com.gclouddemo.ecommerce.catalog.renderer;

import java.util.List;

import com.gclouddemo.ecommerce.catalog.bean.CatalogItem;
import com.google.gson.Gson;

/**
 *
 */
public class EcomJsonRenderer implements EcomCatalogRenderer {
	
	private static Gson gson = new Gson();

	@Override
	public String renderItemList(List<CatalogItem> items, String prefixStr, String suffixStr) {
		if (items != null) {
			String json = gson.toJson(items);
			return json;
		}
		
		return null;
	}

	@Override
	public String renderCatalogItem(CatalogItem item) {
		return null;
	}

}
