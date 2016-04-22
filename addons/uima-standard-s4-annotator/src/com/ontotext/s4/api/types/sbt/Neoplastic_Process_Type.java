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
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.tcas.Annotation_Type;

/** Automatically generated type for Neoplastic_Process
 * Updated by JCasGen Tue Mar 10 17:57:38 EET 2015
 * @generated */
public class Neoplastic_Process_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Neoplastic_Process_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Neoplastic_Process_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Neoplastic_Process(addr, Neoplastic_Process_Type.this);
  			   Neoplastic_Process_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Neoplastic_Process(addr, Neoplastic_Process_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Neoplastic_Process.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("com.ontotext.s4.api.types.sbt.Neoplastic_Process");
 
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
      jcas.throwFeatMissing("string", "com.ontotext.s4.api.types.sbt.Neoplastic_Process");
    return ll_cas.ll_getStringValue(addr, casFeatCode_string);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setString(int addr, String v) {
        if (featOkTst && casFeat_string == null)
      jcas.throwFeatMissing("string", "com.ontotext.s4.api.types.sbt.Neoplastic_Process");
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
      jcas.throwFeatMissing("class_feature", "com.ontotext.s4.api.types.sbt.Neoplastic_Process");
    return ll_cas.ll_getStringValue(addr, casFeatCode_class_feature);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setClass_feature(int addr, String v) {
        if (featOkTst && casFeat_class_feature == null)
      jcas.throwFeatMissing("class_feature", "com.ontotext.s4.api.types.sbt.Neoplastic_Process");
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
      jcas.throwFeatMissing("inst", "com.ontotext.s4.api.types.sbt.Neoplastic_Process");
    return ll_cas.ll_getStringValue(addr, casFeatCode_inst);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setInst(int addr, String v) {
        if (featOkTst && casFeat_inst == null)
      jcas.throwFeatMissing("inst", "com.ontotext.s4.api.types.sbt.Neoplastic_Process");
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
      jcas.throwFeatMissing("type_feature", "com.ontotext.s4.api.types.sbt.Neoplastic_Process");
    return ll_cas.ll_getStringValue(addr, casFeatCode_type_feature);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setType_feature(int addr, String v) {
        if (featOkTst && casFeat_type_feature == null)
      jcas.throwFeatMissing("type_feature", "com.ontotext.s4.api.types.sbt.Neoplastic_Process");
    ll_cas.ll_setStringValue(addr, casFeatCode_type_feature, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public Neoplastic_Process_Type(JCas jcas, Type casType) {
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

  }
}



    