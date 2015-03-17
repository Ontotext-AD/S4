

/* First created by JCasGen Tue Mar 10 18:00:03 EET 2015 */
package com.ontotext.s4.api.uima.types.twitie;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** Automatically generated type for Emoticon
 * Updated by JCasGen Tue Mar 10 18:00:03 EET 2015
 * XML source: desc/twitie_typesystem.xml
 * @generated */
public class Emoticon extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Emoticon.class);
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
  protected Emoticon() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public Emoticon(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public Emoticon(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public Emoticon(JCas jcas, int begin, int end) {
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
  //* Feature: normalized

  /** getter for normalized - gets Feature <normalized> for type <Emoticon>
   * @generated
   * @return value of the feature 
   */
  public String getNormalized() {
    if (Emoticon_Type.featOkTst && ((Emoticon_Type)jcasType).casFeat_normalized == null)
      jcasType.jcas.throwFeatMissing("normalized", "com.ontotext.s4.api.uima.types.com.ontotext.s4.api.types.twitie.Emoticon");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Emoticon_Type)jcasType).casFeatCode_normalized);}
    
  /** setter for normalized - sets Feature <normalized> for type <Emoticon> 
   * @generated
   * @param v value to set into the feature 
   */
  public void setNormalized(String v) {
    if (Emoticon_Type.featOkTst && ((Emoticon_Type)jcasType).casFeat_normalized == null)
      jcasType.jcas.throwFeatMissing("normalized", "com.ontotext.s4.api.uima.types.com.ontotext.s4.api.types.twitie.Emoticon");
    jcasType.ll_cas.ll_setStringValue(addr, ((Emoticon_Type)jcasType).casFeatCode_normalized, v);}    
   
    
  //*--------------*
  //* Feature: type_feature

  /** getter for type_feature - gets Feature <type_feature> for type <Emoticon>
   * @generated
   * @return value of the feature 
   */
  public String getType_feature() {
    if (Emoticon_Type.featOkTst && ((Emoticon_Type)jcasType).casFeat_type_feature == null)
      jcasType.jcas.throwFeatMissing("type_feature", "com.ontotext.s4.api.uima.types.com.ontotext.s4.api.types.twitie.Emoticon");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Emoticon_Type)jcasType).casFeatCode_type_feature);}
    
  /** setter for type_feature - sets Feature <type_feature> for type <Emoticon> 
   * @generated
   * @param v value to set into the feature 
   */
  public void setType_feature(String v) {
    if (Emoticon_Type.featOkTst && ((Emoticon_Type)jcasType).casFeat_type_feature == null)
      jcasType.jcas.throwFeatMissing("type_feature", "com.ontotext.s4.api.uima.types.com.ontotext.s4.api.types.twitie.Emoticon");
    jcasType.ll_cas.ll_setStringValue(addr, ((Emoticon_Type)jcasType).casFeatCode_type_feature, v);}    
  }

    