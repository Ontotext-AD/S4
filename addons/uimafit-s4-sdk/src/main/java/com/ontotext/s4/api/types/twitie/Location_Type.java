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
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.tcas.Annotation_Type;

/** Automatically generated type for Location
 * Updated by JCasGen Tue Mar 10 18:00:03 EET 2015
 * @generated */
public class Location_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Location_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Location_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Location(addr, Location_Type.this);
  			   Location_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Location(addr, Location_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Location.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("com.ontotext.s4.api.types.twitie.Location");
 
  /** @generated */
  final Feature casFeat_rule1;
  /** @generated */
  final int     casFeatCode_rule1;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getRule1(int addr) {
        if (featOkTst && casFeat_rule1 == null)
      jcas.throwFeatMissing("rule1", "com.ontotext.s4.api.types.twitie.Location");
    return ll_cas.ll_getStringValue(addr, casFeatCode_rule1);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setRule1(int addr, String v) {
        if (featOkTst && casFeat_rule1 == null)
      jcas.throwFeatMissing("rule1", "com.ontotext.s4.api.types.twitie.Location");
    ll_cas.ll_setStringValue(addr, casFeatCode_rule1, v);}
    
  
 
  /** @generated */
  final Feature casFeat_rule2;
  /** @generated */
  final int     casFeatCode_rule2;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getRule2(int addr) {
        if (featOkTst && casFeat_rule2 == null)
      jcas.throwFeatMissing("rule2", "com.ontotext.s4.api.types.twitie.Location");
    return ll_cas.ll_getStringValue(addr, casFeatCode_rule2);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setRule2(int addr, String v) {
        if (featOkTst && casFeat_rule2 == null)
      jcas.throwFeatMissing("rule2", "com.ontotext.s4.api.types.twitie.Location");
    ll_cas.ll_setStringValue(addr, casFeatCode_rule2, v);}
    
  
 
  /** @generated */
  final Feature casFeat_type_feature;
  /** @generated */
  final int     casFeatCode_type_feature;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getType_feature(int addr) {
        if (featOkTst && casFeat_type_feature == null)
      jcas.throwFeatMissing("type_feature", "com.ontotext.s4.api.types.twitie.Location");
    return ll_cas.ll_getStringValue(addr, casFeatCode_type_feature);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setType_feature(int addr, String v) {
        if (featOkTst && casFeat_type_feature == null)
      jcas.throwFeatMissing("type_feature", "com.ontotext.s4.api.types.twitie.Location");
    ll_cas.ll_setStringValue(addr, casFeatCode_type_feature, v);}
    
  
 
  /** @generated */
  final Feature casFeat_kind;
  /** @generated */
  final int     casFeatCode_kind;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getKind(int addr) {
        if (featOkTst && casFeat_kind == null)
      jcas.throwFeatMissing("kind", "com.ontotext.s4.api.types.twitie.Location");
    return ll_cas.ll_getStringValue(addr, casFeatCode_kind);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setKind(int addr, String v) {
        if (featOkTst && casFeat_kind == null)
      jcas.throwFeatMissing("kind", "com.ontotext.s4.api.types.twitie.Location");
    ll_cas.ll_setStringValue(addr, casFeatCode_kind, v);}
    
  
 
  /** @generated */
  final Feature casFeat_rule;
  /** @generated */
  final int     casFeatCode_rule;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getRule(int addr) {
        if (featOkTst && casFeat_rule == null)
      jcas.throwFeatMissing("rule", "com.ontotext.s4.api.types.twitie.Location");
    return ll_cas.ll_getStringValue(addr, casFeatCode_rule);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setRule(int addr, String v) {
        if (featOkTst && casFeat_rule == null)
      jcas.throwFeatMissing("rule", "com.ontotext.s4.api.types.twitie.Location");
    ll_cas.ll_setStringValue(addr, casFeatCode_rule, v);}
    
  
 
  /** @generated */
  final Feature casFeat_string;
  /** @generated */
  final int     casFeatCode_string;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getString(int addr) {
        if (featOkTst && casFeat_string == null)
      jcas.throwFeatMissing("string", "com.ontotext.s4.api.types.twitie.Location");
    return ll_cas.ll_getStringValue(addr, casFeatCode_string);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setString(int addr, String v) {
        if (featOkTst && casFeat_string == null)
      jcas.throwFeatMissing("string", "com.ontotext.s4.api.types.twitie.Location");
    ll_cas.ll_setStringValue(addr, casFeatCode_string, v);}
    
  
 
  /** @generated */
  final Feature casFeat_length;
  /** @generated */
  final int     casFeatCode_length;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getLength(int addr) {
        if (featOkTst && casFeat_length == null)
      jcas.throwFeatMissing("length", "com.ontotext.s4.api.types.twitie.Location");
    return ll_cas.ll_getStringValue(addr, casFeatCode_length);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setLength(int addr, String v) {
        if (featOkTst && casFeat_length == null)
      jcas.throwFeatMissing("length", "com.ontotext.s4.api.types.twitie.Location");
    ll_cas.ll_setStringValue(addr, casFeatCode_length, v);}
    
  
 
  /** @generated */
  final Feature casFeat_replaced;
  /** @generated */
  final int     casFeatCode_replaced;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getReplaced(int addr) {
        if (featOkTst && casFeat_replaced == null)
      jcas.throwFeatMissing("replaced", "com.ontotext.s4.api.types.twitie.Location");
    return ll_cas.ll_getStringValue(addr, casFeatCode_replaced);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setReplaced(int addr, String v) {
        if (featOkTst && casFeat_replaced == null)
      jcas.throwFeatMissing("replaced", "com.ontotext.s4.api.types.twitie.Location");
    ll_cas.ll_setStringValue(addr, casFeatCode_replaced, v);}
    
  
 
  /** @generated */
  final Feature casFeat_user;
  /** @generated */
  final int     casFeatCode_user;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getUser(int addr) {
        if (featOkTst && casFeat_user == null)
      jcas.throwFeatMissing("user", "com.ontotext.s4.api.types.twitie.Location");
    return ll_cas.ll_getStringValue(addr, casFeatCode_user);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setUser(int addr, String v) {
        if (featOkTst && casFeat_user == null)
      jcas.throwFeatMissing("user", "com.ontotext.s4.api.types.twitie.Location");
    ll_cas.ll_setStringValue(addr, casFeatCode_user, v);}
    
  
 
  /** @generated */
  final Feature casFeat_normalized;
  /** @generated */
  final int     casFeatCode_normalized;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getNormalized(int addr) {
        if (featOkTst && casFeat_normalized == null)
      jcas.throwFeatMissing("normalized", "com.ontotext.s4.api.types.twitie.Location");
    return ll_cas.ll_getStringValue(addr, casFeatCode_normalized);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setNormalized(int addr, String v) {
        if (featOkTst && casFeat_normalized == null)
      jcas.throwFeatMissing("normalized", "com.ontotext.s4.api.types.twitie.Location");
    ll_cas.ll_setStringValue(addr, casFeatCode_normalized, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public Location_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_rule1 = jcas.getRequiredFeatureDE(casType, "rule1", "uima.cas.String", featOkTst);
    casFeatCode_rule1  = (null == casFeat_rule1) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_rule1).getCode();

 
    casFeat_rule2 = jcas.getRequiredFeatureDE(casType, "rule2", "uima.cas.String", featOkTst);
    casFeatCode_rule2  = (null == casFeat_rule2) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_rule2).getCode();

 
    casFeat_type_feature = jcas.getRequiredFeatureDE(casType, "type_feature", "uima.cas.String", featOkTst);
    casFeatCode_type_feature  = (null == casFeat_type_feature) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_type_feature).getCode();

 
    casFeat_kind = jcas.getRequiredFeatureDE(casType, "kind", "uima.cas.String", featOkTst);
    casFeatCode_kind  = (null == casFeat_kind) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_kind).getCode();

 
    casFeat_rule = jcas.getRequiredFeatureDE(casType, "rule", "uima.cas.String", featOkTst);
    casFeatCode_rule  = (null == casFeat_rule) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_rule).getCode();

 
    casFeat_string = jcas.getRequiredFeatureDE(casType, "string", "uima.cas.String", featOkTst);
    casFeatCode_string  = (null == casFeat_string) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_string).getCode();

 
    casFeat_length = jcas.getRequiredFeatureDE(casType, "length", "uima.cas.String", featOkTst);
    casFeatCode_length  = (null == casFeat_length) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_length).getCode();

 
    casFeat_replaced = jcas.getRequiredFeatureDE(casType, "replaced", "uima.cas.String", featOkTst);
    casFeatCode_replaced  = (null == casFeat_replaced) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_replaced).getCode();

 
    casFeat_user = jcas.getRequiredFeatureDE(casType, "user", "uima.cas.String", featOkTst);
    casFeatCode_user  = (null == casFeat_user) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_user).getCode();

 
    casFeat_normalized = jcas.getRequiredFeatureDE(casType, "normalized", "uima.cas.String", featOkTst);
    casFeatCode_normalized  = (null == casFeat_normalized) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_normalized).getCode();

  }
}



    