/*
 * S4 Java client library
 * Copyright (c) 2014, Ontotext AD, All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library.
 */
package com.ontotext.s4.catalog;


/**
 * "Struct" class to represent a single service.
 */
public class ServiceDescriptor  {

  /**
   * The service name.
   */
  public String name;

  /**
   * A short fragment of HTML describing the service.
   */
  public String shortDescription;

 /**
   * URL to process documents using the service endpoint.
   */
  public String onlineUrl;

}
