package com.gclouddemo.ecommerce.catalog.bean;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;
import java.util.Date;


@Entity
public class Product
{
  @Parent
  public Key<Catalog> theCatalog;
  @Id
  public Long id;
  public String name;
  public String description;
  public String price;
  public String unit;
  @Index
  public Date date;
  
  public Product()
  {
    date = new Date();
  }
  


  public Product(String catalog, String name)
  {
    this();
    if (catalog != null) {
      theCatalog = Key.create(Catalog.class, catalog);
    } else {
      theCatalog = Key.create(Catalog.class, "default");
    }
    this.name = name;
  }
  


  public Product(String catalog, String name, String description, String price, String unit)
  {
    this(catalog, name);
    this.description = description;
    this.price = price;
    this.unit = unit;
  }
  
  public String getAncestor() { return theCatalog.getName(); }
}