

/* First created by JCasGen Tue Mar 10 18:00:03 EET 2015 */
package com.ontotext.s4.api.types.twitie;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** Automatically generated type for UserID
 * Updated by JCasGen Tue Mar 10 18:00:03 EET 2015
 * XML source: desc/twitie_typesystem.xml
 * @generated */
public class UserID extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(UserID.class);
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
  protected UserID() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public UserID(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public UserID(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public UserID(JCas jcas, int begin, int end) {
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

  /** getter for rule - gets Feature <rule> for type <UserID>
   * @generated
   * @return value of the feature 
   */
  public String getRule() {
    if (UserID_Type.featOkTst && ((UserID_Type)jcasType).casFeat_rule == null)
      jcasType.jcas.throwFeatMissing("rule", "com.ontotext.s4.com.ontotext.s4.api.types.twitie.UserID");
    return jcasType.ll_cas.ll_getStringValue(addr, ((UserID_Type)jcasType).casFeatCode_rule);}
    
  /** setter for rule - sets Feature <rule> for type <UserID> 
   * @generated
   * @param v value to set into the feature 
   */
  public void setRule(String v) {
    if (UserID_Type.featOkTst && ((UserID_Type)jcasType).casFeat_rule == null)
      jcasType.jcas.throwFeatMissing("rule", "com.ontotext.s4.com.ontotext.s4.api.types.twitie.UserID");
    jcasType.ll_cas.ll_setStringValue(addr, ((UserID_Type)jcasType).casFeatCode_rule, v);}    
   
    
  //*--------------*
  //* Feature: string

  /** getter for string - gets Feature <string> for type <UserID>
   * @generated
   * @return value of the feature 
   */
  public String getString() {
    if (UserID_Type.featOkTst && ((UserID_Type)jcasType).casFeat_string == null)
      jcasType.jcas.throwFeatMissing("string", "com.ontotext.s4.com.ontotext.s4.api.types.twitie.UserID");
    return jcasType.ll_cas.ll_getStringValue(addr, ((UserID_Type)jcasType).casFeatCode_string);}
    
  /** setter for string - sets Feature <string> for type <UserID> 
   * @generated
   * @param v value to set into the feature 
   */
  public void setString(String v) {
    if (UserID_Type.featOkTst && ((UserID_Type)jcasType).casFeat_string == null)
      jcasType.jcas.throwFeatMissing("string", "com.ontotext.s4.com.ontotext.s4.api.types.twitie.UserID");
    jcasType.ll_cas.ll_setStringValue(addr, ((UserID_Type)jcasType).casFeatCode_string, v);}    
   
    
  //*--------------*
  //* Feature: length

  /** getter for length - gets Feature <length> for type <UserID>
   * @generated
   * @return value of the feature 
   */
  public String getLength() {
    if (UserID_Type.featOkTst && ((UserID_Type)jcasType).casFeat_length == null)
      jcasType.jcas.throwFeatMissing("length", "com.ontotext.s4.com.ontotext.s4.api.types.twitie.UserID");
    return jcasType.ll_cas.ll_getStringValue(addr, ((UserID_Type)jcasType).casFeatCode_length);}
    
  /** setter for length - sets Feature <length> for type <UserID> 
   * @generated
   * @param v value to set into the feature 
   */
  public void setLength(String v) {
    if (UserID_Type.featOkTst && ((UserID_Type)jcasType).casFeat_length == null)
      jcasType.jcas.throwFeatMissing("length", "com.ontotext.s4.com.ontotext.s4.api.types.twitie.UserID");
    jcasType.ll_cas.ll_setStringValue(addr, ((UserID_Type)jcasType).casFeatCode_length, v);}    
   
    
  //*--------------*
  //* Feature: replaced

  /** getter for replaced - gets Feature <replaced> for type <UserID>
   * @generated
   * @return value of the feature 
   */
  public String getReplaced() {
    if (UserID_Type.featOkTst && ((UserID_Type)jcasType).casFeat_replaced == null)
      jcasType.jcas.throwFeatMissing("replaced", "com.ontotext.s4.com.ontotext.s4.api.types.twitie.UserID");
    return jcasType.ll_cas.ll_getStringValue(addr, ((UserID_Type)jcasType).casFeatCode_replaced);}
    
  /** setter for replaced - sets Feature <replaced> for type <UserID> 
   * @generated
   * @param v value to set into the feature 
   */
  public void setReplaced(String v) {
    if (UserID_Type.featOkTst && ((UserID_Type)jcasType).casFeat_replaced == null)
      jcasType.jcas.throwFeatMissing("replaced", "com.ontotext.s4.com.ontotext.s4.api.types.twitie.UserID");
    jcasType.ll_cas.ll_setStringValue(addr, ((UserID_Type)jcasType).casFeatCode_replaced, v);}    
   
    
  //*--------------*
  //* Feature: type_feature

  /** getter for type_feature - gets Feature <type_feature> for type <UserID>
   * @generated
   * @return value of the feature 
   */
  public String getType_feature() {
    if (UserID_Type.featOkTst && ((UserID_Type)jcasType).casFeat_type_feature == null)
      jcasType.jcas.throwFeatMissing("type_feature", "com.ontotext.s4.com.ontotext.s4.api.types.twitie.UserID");
    return jcasType.ll_cas.ll_getStringValue(addr, ((UserID_Type)jcasType).casFeatCode_type_feature);}
    
  /** setter for type_feature - sets Feature <type_feature> for type <UserID> 
   * @generated
   * @param v value to set into the feature 
   */
  public void setType_feature(String v) {
    if (UserID_Type.featOkTst && ((UserID_Type)jcasType).casFeat_type_feature == null)
      jcasType.jcas.throwFeatMissing("type_feature", "com.ontotext.s4.com.ontotext.s4.api.types.twitie.UserID");
    jcasType.ll_cas.ll_setStringValue(addr, ((UserID_Type)jcasType).casFeatCode_type_feature, v);}    
   
    
  //*--------------*
  //* Feature: user

  /** getter for user - gets Feature <user> for type <UserID>
   * @generated
   * @return value of the feature 
   */
  public String getUser() {
    if (UserID_Type.featOkTst && ((UserID_Type)jcasType).casFeat_user == null)
      jcasType.jcas.throwFeatMissing("user", "com.ontotext.s4.com.ontotext.s4.api.types.twitie.UserID");
    return jcasType.ll_cas.ll_getStringValue(addr, ((UserID_Type)jcasType).casFeatCode_user);}
    
  /** setter for user - sets Feature <user> for type <UserID> 
   * @generated
   * @param v value to set into the feature 
   */
  public void setUser(String v) {
    if (UserID_Type.featOkTst && ((UserID_Type)jcasType).casFeat_user == null)
      jcasType.jcas.throwFeatMissing("user", "com.ontotext.s4.com.ontotext.s4.api.types.twitie.UserID");
    jcasType.ll_cas.ll_setStringValue(addr, ((UserID_Type)jcasType).casFeatCode_user, v);}    
   
    
  //*--------------*
  //* Feature: kind

  /** getter for kind - gets Feature <kind> for type <UserID>
   * @generated
   * @return value of the feature 
   */
  public String getKind() {
    if (UserID_Type.featOkTst && ((UserID_Type)jcasType).casFeat_kind == null)
      jcasType.jcas.throwFeatMissing("kind", "com.ontotext.s4.com.ontotext.s4.api.types.twitie.UserID");
    return jcasType.ll_cas.ll_getStringValue(addr, ((UserID_Type)jcasType).casFeatCode_kind);}
    
  /** setter for kind - sets Feature <kind> for type <UserID> 
   * @generated
   * @param v value to set into the feature 
   */
  public void setKind(String v) {
    if (UserID_Type.featOkTst && ((UserID_Type)jcasType).casFeat_kind == null)
      jcasType.jcas.throwFeatMissing("kind", "com.ontotext.s4.com.ontotext.s4.api.types.twitie.UserID");
    jcasType.ll_cas.ll_setStringValue(addr, ((UserID_Type)jcasType).casFeatCode_kind, v);}    
   
    
  //*--------------*
  //* Feature: rule1

  /** getter for rule1 - gets Feature <rule1> for type <UserID>
   * @generated
   * @return value of the feature 
   */
  public String getRule1() {
    if (UserID_Type.featOkTst && ((UserID_Type)jcasType).casFeat_rule1 == null)
      jcasType.jcas.throwFeatMissing("rule1", "com.ontotext.s4.com.ontotext.s4.api.types.twitie.UserID");
    return jcasType.ll_cas.ll_getStringValue(addr, ((UserID_Type)jcasType).casFeatCode_rule1);}
    
  /** setter for rule1 - sets Feature <rule1> for type <UserID> 
   * @generated
   * @param v value to set into the feature 
   */
  public void setRule1(String v) {
    if (UserID_Type.featOkTst && ((UserID_Type)jcasType).casFeat_rule1 == null)
      jcasType.jcas.throwFeatMissing("rule1", "com.ontotext.s4.com.ontotext.s4.api.types.twitie.UserID");
    jcasType.ll_cas.ll_setStringValue(addr, ((UserID_Type)jcasType).casFeatCode_rule1, v);}    
   
    
  //*--------------*
  //* Feature: rule2

  /** getter for rule2 - gets Feature <rule2> for type <UserID>
   * @generated
   * @return value of the feature 
   */
  public String getRule2() {
    if (UserID_Type.featOkTst && ((UserID_Type)jcasType).casFeat_rule2 == null)
      jcasType.jcas.throwFeatMissing("rule2", "com.ontotext.s4.com.ontotext.s4.api.types.twitie.UserID");
    return jcasType.ll_cas.ll_getStringValue(addr, ((UserID_Type)jcasType).casFeatCode_rule2);}
    
  /** setter for rule2 - sets Feature <rule2> for type <UserID> 
   * @generated
   * @param v value to set into the feature 
   */
  public void setRule2(String v) {
    if (UserID_Type.featOkTst && ((UserID_Type)jcasType).casFeat_rule2 == null)
      jcasType.jcas.throwFeatMissing("rule2", "com.ontotext.s4.com.ontotext.s4.api.types.twitie.UserID");
    jcasType.ll_cas.ll_setStringValue(addr, ((UserID_Type)jcasType).casFeatCode_rule2, v);}    
  }

    