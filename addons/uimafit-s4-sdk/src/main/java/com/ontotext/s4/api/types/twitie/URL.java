/*
 * Copyright 2016 Ontotext AD
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/* First created by JCasGen Tue Mar 10 18:00:03 EET 2015 */
package com.ontotext.s4.api.types.twitie;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** Automatically generated type for URL
 * Updated by JCasGen Tue Mar 10 18:00:03 EET 2015
 * XML source: desc/twitie_typesystem.xml
 * @generated */
public class URL extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(URL.class);
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
  protected URL() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public URL(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public URL(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public URL(JCas jcas, int begin, int end) {
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

  /** getter for rule1 - gets Feature <rule1> for type <URL>
   * @generated
   * @return value of the feature 
   */
  public String getRule1() {
    if (URL_Type.featOkTst && ((URL_Type)jcasType).casFeat_rule1 == null)
      jcasType.jcas.throwFeatMissing("rule1", "com.ontotext.s4.api.types.twitie.URL");
    return jcasType.ll_cas.ll_getStringValue(addr, ((URL_Type)jcasType).casFeatCode_rule1);}
    
  /** setter for rule1 - sets Feature <rule1> for type <URL> 
   * @generated
   * @param v value to set into the feature 
   */
  public void setRule1(String v) {
    if (URL_Type.featOkTst && ((URL_Type)jcasType).casFeat_rule1 == null)
      jcasType.jcas.throwFeatMissing("rule1", "com.ontotext.s4.api.types.twitie.URL");
    jcasType.ll_cas.ll_setStringValue(addr, ((URL_Type)jcasType).casFeatCode_rule1, v);}    
   
    
  //*--------------*
  //* Feature: rule2

  /** getter for rule2 - gets Feature <rule2> for type <URL>
   * @generated
   * @return value of the feature 
   */
  public String getRule2() {
    if (URL_Type.featOkTst && ((URL_Type)jcasType).casFeat_rule2 == null)
      jcasType.jcas.throwFeatMissing("rule2", "com.ontotext.s4.api.types.twitie.URL");
    return jcasType.ll_cas.ll_getStringValue(addr, ((URL_Type)jcasType).casFeatCode_rule2);}
    
  /** setter for rule2 - sets Feature <rule2> for type <URL> 
   * @generated
   * @param v value to set into the feature 
   */
  public void setRule2(String v) {
    if (URL_Type.featOkTst && ((URL_Type)jcasType).casFeat_rule2 == null)
      jcasType.jcas.throwFeatMissing("rule2", "com.ontotext.s4.api.types.twitie.URL");
    jcasType.ll_cas.ll_setStringValue(addr, ((URL_Type)jcasType).casFeatCode_rule2, v);}    
   
    
  //*--------------*
  //* Feature: type_feature

  /** getter for type_feature - gets Feature <type_feature> for type <URL>
   * @generated
   * @return value of the feature 
   */
  public String getType_feature() {
    if (URL_Type.featOkTst && ((URL_Type)jcasType).casFeat_type_feature == null)
      jcasType.jcas.throwFeatMissing("type_feature", "com.ontotext.s4.api.types.twitie.URL");
    return jcasType.ll_cas.ll_getStringValue(addr, ((URL_Type)jcasType).casFeatCode_type_feature);}
    
  /** setter for type_feature - sets Feature <type_feature> for type <URL> 
   * @generated
   * @param v value to set into the feature 
   */
  public void setType_feature(String v) {
    if (URL_Type.featOkTst && ((URL_Type)jcasType).casFeat_type_feature == null)
      jcasType.jcas.throwFeatMissing("type_feature", "com.ontotext.s4.api.types.twitie.URL");
    jcasType.ll_cas.ll_setStringValue(addr, ((URL_Type)jcasType).casFeatCode_type_feature, v);}    
   
    
  //*--------------*
  //* Feature: kind

  /** getter for kind - gets Feature <kind> for type <URL>
   * @generated
   * @return value of the feature 
   */
  public String getKind() {
    if (URL_Type.featOkTst && ((URL_Type)jcasType).casFeat_kind == null)
      jcasType.jcas.throwFeatMissing("kind", "com.ontotext.s4.api.types.twitie.URL");
    return jcasType.ll_cas.ll_getStringValue(addr, ((URL_Type)jcasType).casFeatCode_kind);}
    
  /** setter for kind - sets Feature <kind> for type <URL> 
   * @generated
   * @param v value to set into the feature 
   */
  public void setKind(String v) {
    if (URL_Type.featOkTst && ((URL_Type)jcasType).casFeat_kind == null)
      jcasType.jcas.throwFeatMissing("kind", "com.ontotext.s4.api.types.twitie.URL");
    jcasType.ll_cas.ll_setStringValue(addr, ((URL_Type)jcasType).casFeatCode_kind, v);}    
   
    
  //*--------------*
  //* Feature: rule

  /** getter for rule - gets Feature <rule> for type <URL>
   * @generated
   * @return value of the feature 
   */
  public String getRule() {
    if (URL_Type.featOkTst && ((URL_Type)jcasType).casFeat_rule == null)
      jcasType.jcas.throwFeatMissing("rule", "com.ontotext.s4.api.types.twitie.URL");
    return jcasType.ll_cas.ll_getStringValue(addr, ((URL_Type)jcasType).casFeatCode_rule);}
    
  /** setter for rule - sets Feature <rule> for type <URL> 
   * @generated
   * @param v value to set into the feature 
   */
  public void setRule(String v) {
    if (URL_Type.featOkTst && ((URL_Type)jcasType).casFeat_rule == null)
      jcasType.jcas.throwFeatMissing("rule", "com.ontotext.s4.api.types.twitie.URL");
    jcasType.ll_cas.ll_setStringValue(addr, ((URL_Type)jcasType).casFeatCode_rule, v);}    
   
    
  //*--------------*
  //* Feature: string

  /** getter for string - gets Feature <string> for type <URL>
   * @generated
   * @return value of the feature 
   */
  public String getString() {
    if (URL_Type.featOkTst && ((URL_Type)jcasType).casFeat_string == null)
      jcasType.jcas.throwFeatMissing("string", "com.ontotext.s4.api.types.twitie.URL");
    return jcasType.ll_cas.ll_getStringValue(addr, ((URL_Type)jcasType).casFeatCode_string);}
    
  /** setter for string - sets Feature <string> for type <URL> 
   * @generated
   * @param v value to set into the feature 
   */
  public void setString(String v) {
    if (URL_Type.featOkTst && ((URL_Type)jcasType).casFeat_string == null)
      jcasType.jcas.throwFeatMissing("string", "com.ontotext.s4.api.types.twitie.URL");
    jcasType.ll_cas.ll_setStringValue(addr, ((URL_Type)jcasType).casFeatCode_string, v);}    
   
    
  //*--------------*
  //* Feature: length

  /** getter for length - gets Feature <length> for type <URL>
   * @generated
   * @return value of the feature 
   */
  public String getLength() {
    if (URL_Type.featOkTst && ((URL_Type)jcasType).casFeat_length == null)
      jcasType.jcas.throwFeatMissing("length", "com.ontotext.s4.api.types.twitie.URL");
    return jcasType.ll_cas.ll_getStringValue(addr, ((URL_Type)jcasType).casFeatCode_length);}
    
  /** setter for length - sets Feature <length> for type <URL> 
   * @generated
   * @param v value to set into the feature 
   */
  public void setLength(String v) {
    if (URL_Type.featOkTst && ((URL_Type)jcasType).casFeat_length == null)
      jcasType.jcas.throwFeatMissing("length", "com.ontotext.s4.api.types.twitie.URL");
    jcasType.ll_cas.ll_setStringValue(addr, ((URL_Type)jcasType).casFeatCode_length, v);}    
   
    
  //*--------------*
  //* Feature: normalized

  /** getter for normalized - gets Feature <normalized> for type <URL>
   * @generated
   * @return value of the feature 
   */
  public String getNormalized() {
    if (URL_Type.featOkTst && ((URL_Type)jcasType).casFeat_normalized == null)
      jcasType.jcas.throwFeatMissing("normalized", "com.ontotext.s4.api.types.twitie.URL");
    return jcasType.ll_cas.ll_getStringValue(addr, ((URL_Type)jcasType).casFeatCode_normalized);}
    
  /** setter for normalized - sets Feature <normalized> for type <URL> 
   * @generated
   * @param v value to set into the feature 
   */
  public void setNormalized(String v) {
    if (URL_Type.featOkTst && ((URL_Type)jcasType).casFeat_normalized == null)
      jcasType.jcas.throwFeatMissing("normalized", "com.ontotext.s4.api.types.twitie.URL");
    jcasType.ll_cas.ll_setStringValue(addr, ((URL_Type)jcasType).casFeatCode_normalized, v);}    
  }

    
