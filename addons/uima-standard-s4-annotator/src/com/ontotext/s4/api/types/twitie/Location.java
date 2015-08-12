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

/* First created by JCasGen Tue Mar 10 18:00:03 EET 2015 */
package com.ontotext.s4.api.types.twitie;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** Automatically generated type for Location
 * Updated by JCasGen Tue Mar 10 18:00:03 EET 2015
 * XML source: desc/twitie_typesystem.xml
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
  //* Feature: rule1

  /** getter for rule1 - gets Feature <rule1> for type <Location>
   * @generated
   * @return value of the feature 
   */
  public String getRule1() {
    if (Location_Type.featOkTst && ((Location_Type)jcasType).casFeat_rule1 == null)
      jcasType.jcas.throwFeatMissing("rule1", "com.ontotext.s4.api.types.twitie.Location");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Location_Type)jcasType).casFeatCode_rule1);}
    
  /** setter for rule1 - sets Feature <rule1> for type <Location> 
   * @generated
   * @param v value to set into the feature 
   */
  public void setRule1(String v) {
    if (Location_Type.featOkTst && ((Location_Type)jcasType).casFeat_rule1 == null)
      jcasType.jcas.throwFeatMissing("rule1", "com.ontotext.s4.api.types.twitie.Location");
    jcasType.ll_cas.ll_setStringValue(addr, ((Location_Type)jcasType).casFeatCode_rule1, v);}    
   
    
  //*--------------*
  //* Feature: rule2

  /** getter for rule2 - gets Feature <rule2> for type <Location>
   * @generated
   * @return value of the feature 
   */
  public String getRule2() {
    if (Location_Type.featOkTst && ((Location_Type)jcasType).casFeat_rule2 == null)
      jcasType.jcas.throwFeatMissing("rule2", "com.ontotext.s4.api.types.twitie.Location");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Location_Type)jcasType).casFeatCode_rule2);}
    
  /** setter for rule2 - sets Feature <rule2> for type <Location> 
   * @generated
   * @param v value to set into the feature 
   */
  public void setRule2(String v) {
    if (Location_Type.featOkTst && ((Location_Type)jcasType).casFeat_rule2 == null)
      jcasType.jcas.throwFeatMissing("rule2", "com.ontotext.s4.api.types.twitie.Location");
    jcasType.ll_cas.ll_setStringValue(addr, ((Location_Type)jcasType).casFeatCode_rule2, v);}    
   
    
  //*--------------*
  //* Feature: type_feature

  /** getter for type_feature - gets Feature <type_feature> for type <Location>
   * @generated
   * @return value of the feature 
   */
  public String getType_feature() {
    if (Location_Type.featOkTst && ((Location_Type)jcasType).casFeat_type_feature == null)
      jcasType.jcas.throwFeatMissing("type_feature", "com.ontotext.s4.api.types.twitie.Location");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Location_Type)jcasType).casFeatCode_type_feature);}
    
  /** setter for type_feature - sets Feature <type_feature> for type <Location> 
   * @generated
   * @param v value to set into the feature 
   */
  public void setType_feature(String v) {
    if (Location_Type.featOkTst && ((Location_Type)jcasType).casFeat_type_feature == null)
      jcasType.jcas.throwFeatMissing("type_feature", "com.ontotext.s4.api.types.twitie.Location");
    jcasType.ll_cas.ll_setStringValue(addr, ((Location_Type)jcasType).casFeatCode_type_feature, v);}    
   
    
  //*--------------*
  //* Feature: kind

  /** getter for kind - gets Feature <kind> for type <Location>
   * @generated
   * @return value of the feature 
   */
  public String getKind() {
    if (Location_Type.featOkTst && ((Location_Type)jcasType).casFeat_kind == null)
      jcasType.jcas.throwFeatMissing("kind", "com.ontotext.s4.api.types.twitie.Location");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Location_Type)jcasType).casFeatCode_kind);}
    
  /** setter for kind - sets Feature <kind> for type <Location> 
   * @generated
   * @param v value to set into the feature 
   */
  public void setKind(String v) {
    if (Location_Type.featOkTst && ((Location_Type)jcasType).casFeat_kind == null)
      jcasType.jcas.throwFeatMissing("kind", "com.ontotext.s4.api.types.twitie.Location");
    jcasType.ll_cas.ll_setStringValue(addr, ((Location_Type)jcasType).casFeatCode_kind, v);}    
   
    
  //*--------------*
  //* Feature: rule

  /** getter for rule - gets Feature <rule> for type <Location>
   * @generated
   * @return value of the feature 
   */
  public String getRule() {
    if (Location_Type.featOkTst && ((Location_Type)jcasType).casFeat_rule == null)
      jcasType.jcas.throwFeatMissing("rule", "com.ontotext.s4.api.types.twitie.Location");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Location_Type)jcasType).casFeatCode_rule);}
    
  /** setter for rule - sets Feature <rule> for type <Location> 
   * @generated
   * @param v value to set into the feature 
   */
  public void setRule(String v) {
    if (Location_Type.featOkTst && ((Location_Type)jcasType).casFeat_rule == null)
      jcasType.jcas.throwFeatMissing("rule", "com.ontotext.s4.api.types.twitie.Location");
    jcasType.ll_cas.ll_setStringValue(addr, ((Location_Type)jcasType).casFeatCode_rule, v);}    
   
    
  //*--------------*
  //* Feature: string

  /** getter for string - gets Feature <string> for type <Location>
   * @generated
   * @return value of the feature 
   */
  public String getString() {
    if (Location_Type.featOkTst && ((Location_Type)jcasType).casFeat_string == null)
      jcasType.jcas.throwFeatMissing("string", "com.ontotext.s4.api.types.twitie.Location");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Location_Type)jcasType).casFeatCode_string);}
    
  /** setter for string - sets Feature <string> for type <Location> 
   * @generated
   * @param v value to set into the feature 
   */
  public void setString(String v) {
    if (Location_Type.featOkTst && ((Location_Type)jcasType).casFeat_string == null)
      jcasType.jcas.throwFeatMissing("string", "com.ontotext.s4.api.types.twitie.Location");
    jcasType.ll_cas.ll_setStringValue(addr, ((Location_Type)jcasType).casFeatCode_string, v);}    
   
    
  //*--------------*
  //* Feature: length

  /** getter for length - gets Feature <length> for type <Location>
   * @generated
   * @return value of the feature 
   */
  public String getLength() {
    if (Location_Type.featOkTst && ((Location_Type)jcasType).casFeat_length == null)
      jcasType.jcas.throwFeatMissing("length", "com.ontotext.s4.api.types.twitie.Location");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Location_Type)jcasType).casFeatCode_length);}
    
  /** setter for length - sets Feature <length> for type <Location> 
   * @generated
   * @param v value to set into the feature 
   */
  public void setLength(String v) {
    if (Location_Type.featOkTst && ((Location_Type)jcasType).casFeat_length == null)
      jcasType.jcas.throwFeatMissing("length", "com.ontotext.s4.api.types.twitie.Location");
    jcasType.ll_cas.ll_setStringValue(addr, ((Location_Type)jcasType).casFeatCode_length, v);}    
   
    
  //*--------------*
  //* Feature: replaced

  /** getter for replaced - gets Feature <replaced> for type <Location>
   * @generated
   * @return value of the feature 
   */
  public String getReplaced() {
    if (Location_Type.featOkTst && ((Location_Type)jcasType).casFeat_replaced == null)
      jcasType.jcas.throwFeatMissing("replaced", "com.ontotext.s4.api.types.twitie.Location");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Location_Type)jcasType).casFeatCode_replaced);}
    
  /** setter for replaced - sets Feature <replaced> for type <Location> 
   * @generated
   * @param v value to set into the feature 
   */
  public void setReplaced(String v) {
    if (Location_Type.featOkTst && ((Location_Type)jcasType).casFeat_replaced == null)
      jcasType.jcas.throwFeatMissing("replaced", "com.ontotext.s4.api.types.twitie.Location");
    jcasType.ll_cas.ll_setStringValue(addr, ((Location_Type)jcasType).casFeatCode_replaced, v);}    
   
    
  //*--------------*
  //* Feature: user

  /** getter for user - gets Feature <user> for type <Location>
   * @generated
   * @return value of the feature 
   */
  public String getUser() {
    if (Location_Type.featOkTst && ((Location_Type)jcasType).casFeat_user == null)
      jcasType.jcas.throwFeatMissing("user", "com.ontotext.s4.api.types.twitie.Location");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Location_Type)jcasType).casFeatCode_user);}
    
  /** setter for user - sets Feature <user> for type <Location> 
   * @generated
   * @param v value to set into the feature 
   */
  public void setUser(String v) {
    if (Location_Type.featOkTst && ((Location_Type)jcasType).casFeat_user == null)
      jcasType.jcas.throwFeatMissing("user", "com.ontotext.s4.api.types.twitie.Location");
    jcasType.ll_cas.ll_setStringValue(addr, ((Location_Type)jcasType).casFeatCode_user, v);}    
   
    
  //*--------------*
  //* Feature: normalized

  /** getter for normalized - gets Feature <normalized> for type <Location>
   * @generated
   * @return value of the feature 
   */
  public String getNormalized() {
    if (Location_Type.featOkTst && ((Location_Type)jcasType).casFeat_normalized == null)
      jcasType.jcas.throwFeatMissing("normalized", "com.ontotext.s4.api.types.twitie.Location");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Location_Type)jcasType).casFeatCode_normalized);}
    
  /** setter for normalized - sets Feature <normalized> for type <Location> 
   * @generated
   * @param v value to set into the feature 
   */
  public void setNormalized(String v) {
    if (Location_Type.featOkTst && ((Location_Type)jcasType).casFeat_normalized == null)
      jcasType.jcas.throwFeatMissing("normalized", "com.ontotext.s4.api.types.twitie.Location");
    jcasType.ll_cas.ll_setStringValue(addr, ((Location_Type)jcasType).casFeatCode_normalized, v);}    
  }

    
