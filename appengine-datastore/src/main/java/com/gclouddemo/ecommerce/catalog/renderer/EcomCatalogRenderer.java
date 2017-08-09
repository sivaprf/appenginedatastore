/**
 * 
 */
package com.gclouddemo.ecommerce.catalog.renderer;

import java.util.List;

import com.gclouddemo.ecommerce.catalog.bean.CatalogItem;

/**
 *
 */
public interface EcomCatalogRenderer {
	String renderItemList(List<CatalogItem> items, String prefixStr, String suffixStr);
	String renderCatalogItem(CatalogItem item);
}
