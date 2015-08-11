/* Self-Service Semantic Suite
Copyright (c) 2014, Ontotext AD, All rights reserved.

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 3.0 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library.
*/

/* First created by JCasGen Tue Mar 10 17:39:00 EET 2015 */
package com.ontotext.s4.api.types.news;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** Automatically generated type for Location
 * Updated by JCasGen Tue Mar 10 17:39:00 EET 2015
 * XML source: desc/news_typesystem.xml
 * @generated */
public class Location extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Location.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated
   * @return index of the type  
   */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected Location() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public Location(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public Location(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public Location(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** 
   * <!-- begin-user-doc -->
   * Write your own initialization here
   * <!-- end-user-doc -->
   *
   * @generated modifiable 
   */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: organization1Str

  /** getter for organization1Str - gets Feature <organization1Str> for type <Location>
   * @generated
   * @return value of the feature 
   */
  public String getOrganization1Str() {
    if (Location_Type.featOkTst && ((Location_Type)jcasType).casFeat_organization1Str == null)
      jcasType.jcas.throwFeatMissing("organization1Str", "com.ontotext.s4.api.types.news.Location");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Location_Type)jcasType).casFeatCode_organization1Str);}
    
  /** setter for organization1Str - sets Feature <organization1Str> for type <Location> 
   * @generated
   * @param v value to set into the feature 
   */
  public void setOrganization1Str(String v) {
    if (Location_Type.featOkTst && ((Location_Type)jcasType).casFeat_organization1Str == null)
      jcasType.jcas.throwFeatMissing("organization1Str", "com.ontotext.s4.api.types.news.Location");
    jcasType.ll_cas.ll_setStringValue(addr, ((Location_Type)jcasType).casFeatCode_organization1Str, v);}    
   
    
  //*--------------*
  //* Feature: organizationUri

  /** getter for organizationUri - gets Feature <organizationUri> for type <Location>
   * @generated
   * @return value of the feature 
   */
  public String getOrganizationUri() {
    if (Location_Type.featOkTst && ((Location_Type)jcasType).casFeat_organizationUri == null)
      jcasType.jcas.throwFeatMissing("organizationUri", "com.ontotext.s4.api.types.news.Location");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Location_Type)jcasType).casFeatCode_organizationUri);}
    
  /** setter for organizationUri - sets Feature <organizationUri> for type <Location> 
   * @generated
   * @param v value to set into the feature 
   */
  public void setOrganizationUri(String v) {
    if (Location_Type.featOkTst && ((Location_Type)jcasType).casFeat_organizationUri == null)
      jcasType.jcas.throwFeatMissing("organizationUri", "com.ontotext.s4.api.types.news.Location");
    jcasType.ll_cas.ll_setStringValue(addr, ((Location_Type)jcasType).casFeatCode_organizationUri, v);}    
   
    
  //*--------------*
  //* Feature: organization1Uri

  /** getter for organization1Uri - gets Feature <organization1Uri> for type <Location>
   * @generated
   * @return value of the feature 
   */
  public String getOrganization1Uri() {
    if (Location_Type.featOkTst && ((Location_Type)jcasType).casFeat_organization1Uri == null)
      jcasType.jcas.throwFeatMissing("organization1Uri", "com.ontotext.s4.api.types.news.Location");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Location_Type)jcasType).casFeatCode_organization1Uri);}
    
  /** setter for organization1Uri - sets Feature <organization1Uri> for type <Location> 
   * @generated
   * @param v value to set into the feature 
   */
  public void setOrganization1Uri(String v) {
    if (Location_Type.featOkTst && ((Location_Type)jcasType).casFeat_organization1Uri == null)
      jcasType.jcas.throwFeatMissing("organization1Uri", "com.ontotext.s4.api.types.news.Location");
    jcasType.ll_cas.ll_setStringValue(addr, ((Location_Type)jcasType).casFeatCode_organization1Uri, v);}    
   
    
  //*--------------*
  //* Feature: relType

  /** getter for relType - gets Feature <relType> for type <Location>
   * @generated
   * @return value of the feature 
   */
  public String getRelType() {
    if (Location_Type.featOkTst && ((Location_Type)jcasType).casFeat_relType == null)
      jcasType.jcas.throwFeatMissing("relType", "com.ontotext.s4.api.types.news.Location");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Location_Type)jcasType).casFeatCode_relType);}
    
  /** setter for relType - sets Feature <relType> for type <Location> 
   * @generated
   * @param v value to set into the feature 
   */
  public void setRelType(String v) {
    if (Location_Type.featOkTst && ((Location_Type)jcasType).casFeat_relType == null)
      jcasType.jcas.throwFeatMissing("relType", "com.ontotext.s4.api.types.news.Location");
    jcasType.ll_cas.ll_setStringValue(addr, ((Location_Type)jcasType).casFeatCode_relType, v);}    
   
    
  //*--------------*
  //* Feature: rule

  /** getter for rule - gets Feature <rule> for type <Location>
   * @generated
   * @return value of the feature 
   */
  public String getRule() {
    if (Location_Type.featOkTst && ((Location_Type)jcasType).casFeat_rule == null)
      jcasType.jcas.throwFeatMissing("rule", "com.ontotext.s4.api.types.news.Location");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Location_Type)jcasType).casFeatCode_rule);}
    
  /** setter for rule - sets Feature <rule> for type <Location> 
   * @generated
   * @param v value to set into the feature 
   */
  public void setRule(String v) {
    if (Location_Type.featOkTst && ((Location_Type)jcasType).casFeat_rule == null)
      jcasType.jcas.throwFeatMissing("rule", "com.ontotext.s4.api.types.news.Location");
    jcasType.ll_cas.ll_setStringValue(addr, ((Location_Type)jcasType).casFeatCode_rule, v);}    
   
    
  //*--------------*
  //* Feature: string

  /** getter for string - gets Feature <string> for type <Location>
   * @generated
   * @return value of the feature 
   */
  public String getString() {
    if (Location_Type.featOkTst && ((Location_Type)jcasType).casFeat_string == null)
      jcasType.jcas.throwFeatMissing("string", "com.ontotext.s4.api.types.news.Location");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Location_Type)jcasType).casFeatCode_string);}
    
  /** setter for string - sets Feature <string> for type <Location> 
   * @generated
   * @param v value to set into the feature 
   */
  public void setString(String v) {
    if (Location_Type.featOkTst && ((Location_Type)jcasType).casFeat_string == null)
      jcasType.jcas.throwFeatMissing("string", "com.ontotext.s4.api.types.news.Location");
    jcasType.ll_cas.ll_setStringValue(addr, ((Location_Type)jcasType).casFeatCode_string, v);}    
   
    
  //*--------------*
  //* Feature: class_feature

  /** getter for class_feature - gets Feature <class_feature> for type <Location>
   * @generated
   * @return value of the feature 
   */
  public String getClass_feature() {
    if (Location_Type.featOkTst && ((Location_Type)jcasType).casFeat_class_feature == null)
      jcasType.jcas.throwFeatMissing("class_feature", "com.ontotext.s4.api.types.news.Location");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Location_Type)jcasType).casFeatCode_class_feature);}
    
  /** setter for class_feature - sets Feature <class_feature> for type <Location> 
   * @generated
   * @param v value to set into the feature 
   */
  public void setClass_feature(String v) {
    if (Location_Type.featOkTst && ((Location_Type)jcasType).casFeat_class_feature == null)
      jcasType.jcas.throwFeatMissing("class_feature", "com.ontotext.s4.api.types.news.Location");
    jcasType.ll_cas.ll_setStringValue(addr, ((Location_Type)jcasType).casFeatCode_class_feature, v);}    
   
    
  //*--------------*
  //* Feature: inst

  /** getter for inst - gets Feature <inst> for type <Location>
   * @generated
   * @return value of the feature 
   */
  public String getInst() {
    if (Location_Type.featOkTst && ((Location_Type)jcasType).casFeat_inst == null)
      jcasType.jcas.throwFeatMissing("inst", "com.ontotext.s4.api.types.news.Location");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Location_Type)jcasType).casFeatCode_inst);}
    
  /** setter for inst - sets Feature <inst> for type <Location> 
   * @generated
   * @param v value to set into the feature 
   */
  public void setInst(String v) {
    if (Location_Type.featOkTst && ((Location_Type)jcasType).casFeat_inst == null)
      jcasType.jcas.throwFeatMissing("inst", "com.ontotext.s4.api.types.news.Location");
    jcasType.ll_cas.ll_setStringValue(addr, ((Location_Type)jcasType).casFeatCode_inst, v);}    
   
    
  //*--------------*
  //* Feature: type_feature

  /** getter for type_feature - gets Feature <type_feature> for type <Location>
   * @generated
   * @return value of the feature 
   */
  public String getType_feature() {
    if (Location_Type.featOkTst && ((Location_Type)jcasType).casFeat_type_feature == null)
      jcasType.jcas.throwFeatMissing("type_feature", "com.ontotext.s4.api.types.news.Location");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Location_Type)jcasType).casFeatCode_type_feature);}
    
  /** setter for type_feature - sets Feature <type_feature> for type <Location> 
   * @generated
   * @param v value to set into the feature 
   */
  public void setType_feature(String v) {
    if (Location_Type.featOkTst && ((Location_Type)jcasType).casFeat_type_feature == null)
      jcasType.jcas.throwFeatMissing("type_feature", "com.ontotext.s4.api.types.news.Location");
    jcasType.ll_cas.ll_setStringValue(addr, ((Location_Type)jcasType).casFeatCode_type_feature, v);}    
   
    
  //*--------------*
  //* Feature: organizationStr

  /** getter for organizationStr - gets Feature <organizationStr> for type <Location>
   * @generated
   * @return value of the feature 
   */
  public String getOrganizationStr() {
    if (Location_Type.featOkTst && ((Location_Type)jcasType).casFeat_organizationStr == null)
      jcasType.jcas.throwFeatMissing("organizationStr", "com.ontotext.s4.api.types.news.Location");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Location_Type)jcasType).casFeatCode_organizationStr);}
    
  /** setter for organizationStr - sets Feature <organizationStr> for type <Location> 
   * @generated
   * @param v value to set into the feature 
   */
  public void setOrganizationStr(String v) {
    if (Location_Type.featOkTst && ((Location_Type)jcasType).casFeat_organizationStr == null)
      jcasType.jcas.throwFeatMissing("organizationStr", "com.ontotext.s4.api.types.news.Location");
    jcasType.ll_cas.ll_setStringValue(addr, ((Location_Type)jcasType).casFeatCode_organizationStr, v);}    
   
    
  //*--------------*
  //* Feature: abbrevUri

  /** getter for abbrevUri - gets Feature <abbrevUri> for type <Location>
   * @generated
   * @return value of the feature 
   */
  public String getAbbrevUri() {
    if (Location_Type.featOkTst && ((Location_Type)jcasType).casFeat_abbrevUri == null)
      jcasType.jcas.throwFeatMissing("abbrevUri", "com.ontotext.s4.api.types.news.Location");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Location_Type)jcasType).casFeatCode_abbrevUri);}
    
  /** setter for abbrevUri - sets Feature <abbrevUri> for type <Location> 
   * @generated
   * @param v value to set into the feature 
   */
  public void setAbbrevUri(String v) {
    if (Location_Type.featOkTst && ((Location_Type)jcasType).casFeat_abbrevUri == null)
      jcasType.jcas.throwFeatMissing("abbrevUri", "com.ontotext.s4.api.types.news.Location");
    jcasType.ll_cas.ll_setStringValue(addr, ((Location_Type)jcasType).casFeatCode_abbrevUri, v);}    
   
    
  //*--------------*
  //* Feature: abbrevStr

  /** getter for abbrevStr - gets Feature <abbrevStr> for type <Location>
   * @generated
   * @return value of the feature 
   */
  public String getAbbrevStr() {
    if (Location_Type.featOkTst && ((Location_Type)jcasType).casFeat_abbrevStr == null)
      jcasType.jcas.throwFeatMissing("abbrevStr", "com.ontotext.s4.api.types.news.Location");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Location_Type)jcasType).casFeatCode_abbrevStr);}
    
  /** setter for abbrevStr - sets Feature <abbrevStr> for type <Location> 
   * @generated
   * @param v value to set into the feature 
   */
  public void setAbbrevStr(String v) {
    if (Location_Type.featOkTst && ((Location_Type)jcasType).casFeat_abbrevStr == null)
      jcasType.jcas.throwFeatMissing("abbrevStr", "com.ontotext.s4.api.types.news.Location");
    jcasType.ll_cas.ll_setStringValue(addr, ((Location_Type)jcasType).casFeatCode_abbrevStr, v);}    
   
    
  //*--------------*
  //* Feature: quotationStr

  /** getter for quotationStr - gets Feature <quotationStr> for type <Location>
   * @generated
   * @return value of the feature 
   */
  public String getQuotationStr() {
    if (Location_Type.featOkTst && ((Location_Type)jcasType).casFeat_quotationStr == null)
      jcasType.jcas.throwFeatMissing("quotationStr", "com.ontotext.s4.api.types.news.Location");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Location_Type)jcasType).casFeatCode_quotationStr);}
    
  /** setter for quotationStr - sets Feature <quotationStr> for type <Location> 
   * @generated
   * @param v value to set into the feature 
   */
  public void setQuotationStr(String v) {
    if (Location_Type.featOkTst && ((Location_Type)jcasType).casFeat_quotationStr == null)
      jcasType.jcas.throwFeatMissing("quotationStr", "com.ontotext.s4.api.types.news.Location");
    jcasType.ll_cas.ll_setStringValue(addr, ((Location_Type)jcasType).casFeatCode_quotationStr, v);}    
  }

    
