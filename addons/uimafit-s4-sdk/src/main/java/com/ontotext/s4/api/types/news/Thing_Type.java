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

/* First created by JCasGen Mon Apr 18 13:49:01 EEST 2016 */
package com.ontotext.s4.api.types.news;

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

/** Automatically generated type for com.ontotext.s4.api.types.news.Thing
 * Updated by JCasGen Mon Apr 18 13:49:01 EEST 2016
 * @generated */
public class Thing_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Thing_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Thing_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Thing(addr, Thing_Type.this);
  			   Thing_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Thing(addr, Thing_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Thing.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("com.ontotext.s4.api.types.news.Thing");
 
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
      jcas.throwFeatMissing("string", "com.ontotext.s4.api.types.news.Thing");
    return ll_cas.ll_getStringValue(addr, casFeatCode_string);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setString(int addr, String v) {
        if (featOkTst && casFeat_string == null)
      jcas.throwFeatMissing("string", "com.ontotext.s4.api.types.news.Thing");
    ll_cas.ll_setStringValue(addr, casFeatCode_string, v);}
    
  
 
  /** @generated */
  final Feature casFeat_class_feature;
  /** @generated */
  final int     casFeatCode_class_feature;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getClass_feature(int addr) {
        if (featOkTst && casFeat_class_feature == null)
      jcas.throwFeatMissing("class_feature", "com.ontotext.s4.api.types.news.Thing");
    return ll_cas.ll_getStringValue(addr, casFeatCode_class_feature);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setClass_feature(int addr, String v) {
        if (featOkTst && casFeat_class_feature == null)
      jcas.throwFeatMissing("class_feature", "com.ontotext.s4.api.types.news.Thing");
    ll_cas.ll_setStringValue(addr, casFeatCode_class_feature, v);}
    
  
 
  /** @generated */
  final Feature casFeat_inst;
  /** @generated */
  final int     casFeatCode_inst;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getInst(int addr) {
        if (featOkTst && casFeat_inst == null)
      jcas.throwFeatMissing("inst", "com.ontotext.s4.api.types.news.Thing");
    return ll_cas.ll_getStringValue(addr, casFeatCode_inst);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setInst(int addr, String v) {
        if (featOkTst && casFeat_inst == null)
      jcas.throwFeatMissing("inst", "com.ontotext.s4.api.types.news.Thing");
    ll_cas.ll_setStringValue(addr, casFeatCode_inst, v);}
    
  
 
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
      jcas.throwFeatMissing("type_feature", "com.ontotext.s4.api.types.news.Thing");
    return ll_cas.ll_getStringValue(addr, casFeatCode_type_feature);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setType_feature(int addr, String v) {
        if (featOkTst && casFeat_type_feature == null)
      jcas.throwFeatMissing("type_feature", "com.ontotext.s4.api.types.news.Thing");
    ll_cas.ll_setStringValue(addr, casFeatCode_type_feature, v);}
    
  
 
  /** @generated */
  final Feature casFeat_sublcasses;
  /** @generated */
  final int     casFeatCode_sublcasses;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getSublcasses(int addr) {
        if (featOkTst && casFeat_sublcasses == null)
      jcas.throwFeatMissing("sublcasses", "com.ontotext.s4.api.types.news.Thing");
    return ll_cas.ll_getStringValue(addr, casFeatCode_sublcasses);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setSublcasses(int addr, String v) {
        if (featOkTst && casFeat_sublcasses == null)
      jcas.throwFeatMissing("sublcasses", "com.ontotext.s4.api.types.news.Thing");
    ll_cas.ll_setStringValue(addr, casFeatCode_sublcasses, v);}
    
  
 
  /** @generated */
  final Feature casFeat_preferredLabel;
  /** @generated */
  final int     casFeatCode_preferredLabel;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getPreferredLabel(int addr) {
        if (featOkTst && casFeat_preferredLabel == null)
      jcas.throwFeatMissing("preferredLabel", "com.ontotext.s4.api.types.news.Thing");
    return ll_cas.ll_getStringValue(addr, casFeatCode_preferredLabel);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setPreferredLabel(int addr, String v) {
        if (featOkTst && casFeat_preferredLabel == null)
      jcas.throwFeatMissing("preferredLabel", "com.ontotext.s4.api.types.news.Thing");
    ll_cas.ll_setStringValue(addr, casFeatCode_preferredLabel, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public Thing_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_string = jcas.getRequiredFeatureDE(casType, "string", "uima.cas.String", featOkTst);
    casFeatCode_string  = (null == casFeat_string) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_string).getCode();

 
    casFeat_class_feature = jcas.getRequiredFeatureDE(casType, "class_feature", "uima.cas.String", featOkTst);
    casFeatCode_class_feature  = (null == casFeat_class_feature) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_class_feature).getCode();

 
    casFeat_inst = jcas.getRequiredFeatureDE(casType, "inst", "uima.cas.String", featOkTst);
    casFeatCode_inst  = (null == casFeat_inst) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_inst).getCode();

 
    casFeat_type_feature = jcas.getRequiredFeatureDE(casType, "type_feature", "uima.cas.String", featOkTst);
    casFeatCode_type_feature  = (null == casFeat_type_feature) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_type_feature).getCode();

 
    casFeat_sublcasses = jcas.getRequiredFeatureDE(casType, "sublcasses", "uima.cas.String", featOkTst);
    casFeatCode_sublcasses  = (null == casFeat_sublcasses) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_sublcasses).getCode();

 
    casFeat_preferredLabel = jcas.getRequiredFeatureDE(casType, "preferredLabel", "uima.cas.String", featOkTst);
    casFeatCode_preferredLabel  = (null == casFeat_preferredLabel) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_preferredLabel).getCode();

  }
}



    