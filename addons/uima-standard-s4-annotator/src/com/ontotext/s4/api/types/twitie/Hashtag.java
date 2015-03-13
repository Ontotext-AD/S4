

/* First created by JCasGen Tue Mar 10 18:00:03 EET 2015 */
package com.ontotext.s4.api.types.twitie;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** Automatically generated type for Hashtag
 * Updated by JCasGen Tue Mar 10 18:00:03 EET 2015
 * XML source: desc/twitie_typesystem.xml
 * @generated */
public class Hashtag extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Hashtag.class);
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
  protected Hashtag() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public Hashtag(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public Hashtag(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public Hashtag(JCas jcas, int begin, int end) {
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
  //* Feature: rule

  /** getter for rule - gets Feature <rule> for type <Hashtag>
   * @generated
   * @return value of the feature 
   */
  public String getRule() {
    if (Hashtag_Type.featOkTst && ((Hashtag_Type)jcasType).casFeat_rule == null)
      jcasType.jcas.throwFeatMissing("rule", "com.ontotext.s4.com.ontotext.s4.api.types.twitie.Hashtag");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Hashtag_Type)jcasType).casFeatCode_rule);}
    
  /** setter for rule - sets Feature <rule> for type <Hashtag> 
   * @generated
   * @param v value to set into the feature 
   */
  public void setRule(String v) {
    if (Hashtag_Type.featOkTst && ((Hashtag_Type)jcasType).casFeat_rule == null)
      jcasType.jcas.throwFeatMissing("rule", "com.ontotext.s4.com.ontotext.s4.api.types.twitie.Hashtag");
    jcasType.ll_cas.ll_setStringValue(addr, ((Hashtag_Type)jcasType).casFeatCode_rule, v);}    
   
    
  //*--------------*
  //* Feature: string

  /** getter for string - gets Feature <string> for type <Hashtag>
   * @generated
   * @return value of the feature 
   */
  public String getString() {
    if (Hashtag_Type.featOkTst && ((Hashtag_Type)jcasType).casFeat_string == null)
      jcasType.jcas.throwFeatMissing("string", "com.ontotext.s4.com.ontotext.s4.api.types.twitie.Hashtag");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Hashtag_Type)jcasType).casFeatCode_string);}
    
  /** setter for string - sets Feature <string> for type <Hashtag> 
   * @generated
   * @param v value to set into the feature 
   */
  public void setString(String v) {
    if (Hashtag_Type.featOkTst && ((Hashtag_Type)jcasType).casFeat_string == null)
      jcasType.jcas.throwFeatMissing("string", "com.ontotext.s4.com.ontotext.s4.api.types.twitie.Hashtag");
    jcasType.ll_cas.ll_setStringValue(addr, ((Hashtag_Type)jcasType).casFeatCode_string, v);}    
   
    
  //*--------------*
  //* Feature: length

  /** getter for length - gets Feature <length> for type <Hashtag>
   * @generated
   * @return value of the feature 
   */
  public String getLength() {
    if (Hashtag_Type.featOkTst && ((Hashtag_Type)jcasType).casFeat_length == null)
      jcasType.jcas.throwFeatMissing("length", "com.ontotext.s4.com.ontotext.s4.api.types.twitie.Hashtag");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Hashtag_Type)jcasType).casFeatCode_length);}
    
  /** setter for length - sets Feature <length> for type <Hashtag> 
   * @generated
   * @param v value to set into the feature 
   */
  public void setLength(String v) {
    if (Hashtag_Type.featOkTst && ((Hashtag_Type)jcasType).casFeat_length == null)
      jcasType.jcas.throwFeatMissing("length", "com.ontotext.s4.com.ontotext.s4.api.types.twitie.Hashtag");
    jcasType.ll_cas.ll_setStringValue(addr, ((Hashtag_Type)jcasType).casFeatCode_length, v);}    
   
    
  //*--------------*
  //* Feature: type_feature

  /** getter for type_feature - gets Feature <type_feature> for type <Hashtag>
   * @generated
   * @return value of the feature 
   */
  public String getType_feature() {
    if (Hashtag_Type.featOkTst && ((Hashtag_Type)jcasType).casFeat_type_feature == null)
      jcasType.jcas.throwFeatMissing("type_feature", "com.ontotext.s4.com.ontotext.s4.api.types.twitie.Hashtag");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Hashtag_Type)jcasType).casFeatCode_type_feature);}
    
  /** setter for type_feature - sets Feature <type_feature> for type <Hashtag> 
   * @generated
   * @param v value to set into the feature 
   */
  public void setType_feature(String v) {
    if (Hashtag_Type.featOkTst && ((Hashtag_Type)jcasType).casFeat_type_feature == null)
      jcasType.jcas.throwFeatMissing("type_feature", "com.ontotext.s4.com.ontotext.s4.api.types.twitie.Hashtag");
    jcasType.ll_cas.ll_setStringValue(addr, ((Hashtag_Type)jcasType).casFeatCode_type_feature, v);}    
   
    
  //*--------------*
  //* Feature: kind

  /** getter for kind - gets Feature <kind> for type <Hashtag>
   * @generated
   * @return value of the feature 
   */
  public String getKind() {
    if (Hashtag_Type.featOkTst && ((Hashtag_Type)jcasType).casFeat_kind == null)
      jcasType.jcas.throwFeatMissing("kind", "com.ontotext.s4.com.ontotext.s4.api.types.twitie.Hashtag");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Hashtag_Type)jcasType).casFeatCode_kind);}
    
  /** setter for kind - sets Feature <kind> for type <Hashtag> 
   * @generated
   * @param v value to set into the feature 
   */
  public void setKind(String v) {
    if (Hashtag_Type.featOkTst && ((Hashtag_Type)jcasType).casFeat_kind == null)
      jcasType.jcas.throwFeatMissing("kind", "com.ontotext.s4.com.ontotext.s4.api.types.twitie.Hashtag");
    jcasType.ll_cas.ll_setStringValue(addr, ((Hashtag_Type)jcasType).casFeatCode_kind, v);}    
  }

    