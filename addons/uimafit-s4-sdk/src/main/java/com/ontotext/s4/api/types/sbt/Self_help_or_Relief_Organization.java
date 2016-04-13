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

/* First created by JCasGen Tue Mar 10 17:57:38 EET 2015 */
package com.ontotext.s4.api.types.sbt;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** Automatically generated type for Self_help_or_Relief_Organization
 * Updated by JCasGen Tue Mar 10 17:57:38 EET 2015
 * XML source: desc/sbt_typesystem.xml
 * @generated */
public class Self_help_or_Relief_Organization extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Self_help_or_Relief_Organization.class);
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
  protected Self_help_or_Relief_Organization() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public Self_help_or_Relief_Organization(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public Self_help_or_Relief_Organization(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public Self_help_or_Relief_Organization(JCas jcas, int begin, int end) {
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
  //* Feature: string

  /** getter for string - gets Feature <string> for type <Self_help_or_Relief_Organization>
   * @generated
   * @return value of the feature 
   */
  public String getString() {
    if (Self_help_or_Relief_Organization_Type.featOkTst && ((Self_help_or_Relief_Organization_Type)jcasType).casFeat_string == null)
      jcasType.jcas.throwFeatMissing("string", "com.ontotext.s4.api.types.sbt.Self_help_or_Relief_Organization");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Self_help_or_Relief_Organization_Type)jcasType).casFeatCode_string);}
    
  /** setter for string - sets Feature <string> for type <Self_help_or_Relief_Organization> 
   * @generated
   * @param v value to set into the feature 
   */
  public void setString(String v) {
    if (Self_help_or_Relief_Organization_Type.featOkTst && ((Self_help_or_Relief_Organization_Type)jcasType).casFeat_string == null)
      jcasType.jcas.throwFeatMissing("string", "com.ontotext.s4.api.types.sbt.Self_help_or_Relief_Organization");
    jcasType.ll_cas.ll_setStringValue(addr, ((Self_help_or_Relief_Organization_Type)jcasType).casFeatCode_string, v);}    
   
    
  //*--------------*
  //* Feature: class_feature

  /** getter for class_feature - gets Feature <class_feature> for type <Self_help_or_Relief_Organization>
   * @generated
   * @return value of the feature 
   */
  public String getClass_feature() {
    if (Self_help_or_Relief_Organization_Type.featOkTst && ((Self_help_or_Relief_Organization_Type)jcasType).casFeat_class_feature == null)
      jcasType.jcas.throwFeatMissing("class_feature", "com.ontotext.s4.api.types.sbt.Self_help_or_Relief_Organization");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Self_help_or_Relief_Organization_Type)jcasType).casFeatCode_class_feature);}
    
  /** setter for class_feature - sets Feature <class_feature> for type <Self_help_or_Relief_Organization> 
   * @generated
   * @param v value to set into the feature 
   */
  public void setClass_feature(String v) {
    if (Self_help_or_Relief_Organization_Type.featOkTst && ((Self_help_or_Relief_Organization_Type)jcasType).casFeat_class_feature == null)
      jcasType.jcas.throwFeatMissing("class_feature", "com.ontotext.s4.api.types.sbt.Self_help_or_Relief_Organization");
    jcasType.ll_cas.ll_setStringValue(addr, ((Self_help_or_Relief_Organization_Type)jcasType).casFeatCode_class_feature, v);}    
   
    
  //*--------------*
  //* Feature: inst

  /** getter for inst - gets Feature <inst> for type <Self_help_or_Relief_Organization>
   * @generated
   * @return value of the feature 
   */
  public String getInst() {
    if (Self_help_or_Relief_Organization_Type.featOkTst && ((Self_help_or_Relief_Organization_Type)jcasType).casFeat_inst == null)
      jcasType.jcas.throwFeatMissing("inst", "com.ontotext.s4.api.types.sbt.Self_help_or_Relief_Organization");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Self_help_or_Relief_Organization_Type)jcasType).casFeatCode_inst);}
    
  /** setter for inst - sets Feature <inst> for type <Self_help_or_Relief_Organization> 
   * @generated
   * @param v value to set into the feature 
   */
  public void setInst(String v) {
    if (Self_help_or_Relief_Organization_Type.featOkTst && ((Self_help_or_Relief_Organization_Type)jcasType).casFeat_inst == null)
      jcasType.jcas.throwFeatMissing("inst", "com.ontotext.s4.api.types.sbt.Self_help_or_Relief_Organization");
    jcasType.ll_cas.ll_setStringValue(addr, ((Self_help_or_Relief_Organization_Type)jcasType).casFeatCode_inst, v);}    
   
    
  //*--------------*
  //* Feature: type_feature

  /** getter for type_feature - gets Feature <type_feature> for type <Self_help_or_Relief_Organization>
   * @generated
   * @return value of the feature 
   */
  public String getType_feature() {
    if (Self_help_or_Relief_Organization_Type.featOkTst && ((Self_help_or_Relief_Organization_Type)jcasType).casFeat_type_feature == null)
      jcasType.jcas.throwFeatMissing("type_feature", "com.ontotext.s4.api.types.sbt.Self_help_or_Relief_Organization");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Self_help_or_Relief_Organization_Type)jcasType).casFeatCode_type_feature);}
    
  /** setter for type_feature - sets Feature <type_feature> for type <Self_help_or_Relief_Organization> 
   * @generated
   * @param v value to set into the feature 
   */
  public void setType_feature(String v) {
    if (Self_help_or_Relief_Organization_Type.featOkTst && ((Self_help_or_Relief_Organization_Type)jcasType).casFeat_type_feature == null)
      jcasType.jcas.throwFeatMissing("type_feature", "com.ontotext.s4.api.types.sbt.Self_help_or_Relief_Organization");
    jcasType.ll_cas.ll_setStringValue(addr, ((Self_help_or_Relief_Organization_Type)jcasType).casFeatCode_type_feature, v);}    
  }

    