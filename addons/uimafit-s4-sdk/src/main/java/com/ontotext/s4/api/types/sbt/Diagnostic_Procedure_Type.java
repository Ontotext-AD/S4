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
/* First created by JCasGen Tue Mar 10 17:57:37 EET 2015 */
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

/** Automatically generated type for Diagnostic_Procedure
 * Updated by JCasGen Tue Mar 10 17:57:37 EET 2015
 * @generated */
public class Diagnostic_Procedure_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Diagnostic_Procedure_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Diagnostic_Procedure_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Diagnostic_Procedure(addr, Diagnostic_Procedure_Type.this);
  			   Diagnostic_Procedure_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Diagnostic_Procedure(addr, Diagnostic_Procedure_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Diagnostic_Procedure.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("com.ontotext.s4.api.types.sbt.Diagnostic_Procedure");
 
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
      jcas.throwFeatMissing("string", "com.ontotext.s4.api.types.sbt.Diagnostic_Procedure");
    return ll_cas.ll_getStringValue(addr, casFeatCode_string);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setString(int addr, String v) {
        if (featOkTst && casFeat_string == null)
      jcas.throwFeatMissing("string", "com.ontotext.s4.api.types.sbt.Diagnostic_Procedure");
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
      jcas.throwFeatMissing("class_feature", "com.ontotext.s4.api.types.sbt.Diagnostic_Procedure");
    return ll_cas.ll_getStringValue(addr, casFeatCode_class_feature);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setClass_feature(int addr, String v) {
        if (featOkTst && casFeat_class_feature == null)
      jcas.throwFeatMissing("class_feature", "com.ontotext.s4.api.types.sbt.Diagnostic_Procedure");
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
      jcas.throwFeatMissing("inst", "com.ontotext.s4.api.types.sbt.Diagnostic_Procedure");
    return ll_cas.ll_getStringValue(addr, casFeatCode_inst);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setInst(int addr, String v) {
        if (featOkTst && casFeat_inst == null)
      jcas.throwFeatMissing("inst", "com.ontotext.s4.api.types.sbt.Diagnostic_Procedure");
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
      jcas.throwFeatMissing("type_feature", "com.ontotext.s4.api.types.sbt.Diagnostic_Procedure");
    return ll_cas.ll_getStringValue(addr, casFeatCode_type_feature);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setType_feature(int addr, String v) {
        if (featOkTst && casFeat_type_feature == null)
      jcas.throwFeatMissing("type_feature", "com.ontotext.s4.api.types.sbt.Diagnostic_Procedure");
    ll_cas.ll_setStringValue(addr, casFeatCode_type_feature, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public Diagnostic_Procedure_Type(JCas jcas, Type casType) {
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



    