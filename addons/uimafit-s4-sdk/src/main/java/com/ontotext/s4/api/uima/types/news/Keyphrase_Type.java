
/* First created by JCasGen Tue Mar 10 17:39:00 EET 2015 */
package com.ontotext.s4.api.uima.types.news;

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

/** Automatically generated type for Keyphrase
 * Updated by JCasGen Tue Mar 10 17:39:00 EET 2015
 * @generated */
public class Keyphrase_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Keyphrase_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Keyphrase_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Keyphrase(addr, Keyphrase_Type.this);
  			   Keyphrase_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Keyphrase(addr, Keyphrase_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Keyphrase.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("com.ontotext.s4.api.uima.types.com.ontotext.s4.api.types.news.Keyphrase");
 
  /** @generated */
  final Feature casFeat_organization1Str;
  /** @generated */
  final int     casFeatCode_organization1Str;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getOrganization1Str(int addr) {
        if (featOkTst && casFeat_organization1Str == null)
      jcas.throwFeatMissing("organization1Str", "com.ontotext.s4.api.uima.types.com.ontotext.s4.api.types.news.Keyphrase");
    return ll_cas.ll_getStringValue(addr, casFeatCode_organization1Str);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setOrganization1Str(int addr, String v) {
        if (featOkTst && casFeat_organization1Str == null)
      jcas.throwFeatMissing("organization1Str", "com.ontotext.s4.api.uima.types.com.ontotext.s4.api.types.news.Keyphrase");
    ll_cas.ll_setStringValue(addr, casFeatCode_organization1Str, v);}
    
  
 
  /** @generated */
  final Feature casFeat_organizationUri;
  /** @generated */
  final int     casFeatCode_organizationUri;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getOrganizationUri(int addr) {
        if (featOkTst && casFeat_organizationUri == null)
      jcas.throwFeatMissing("organizationUri", "com.ontotext.s4.api.uima.types.com.ontotext.s4.api.types.news.Keyphrase");
    return ll_cas.ll_getStringValue(addr, casFeatCode_organizationUri);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setOrganizationUri(int addr, String v) {
        if (featOkTst && casFeat_organizationUri == null)
      jcas.throwFeatMissing("organizationUri", "com.ontotext.s4.api.uima.types.com.ontotext.s4.api.types.news.Keyphrase");
    ll_cas.ll_setStringValue(addr, casFeatCode_organizationUri, v);}
    
  
 
  /** @generated */
  final Feature casFeat_organization1Uri;
  /** @generated */
  final int     casFeatCode_organization1Uri;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getOrganization1Uri(int addr) {
        if (featOkTst && casFeat_organization1Uri == null)
      jcas.throwFeatMissing("organization1Uri", "com.ontotext.s4.api.uima.types.com.ontotext.s4.api.types.news.Keyphrase");
    return ll_cas.ll_getStringValue(addr, casFeatCode_organization1Uri);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setOrganization1Uri(int addr, String v) {
        if (featOkTst && casFeat_organization1Uri == null)
      jcas.throwFeatMissing("organization1Uri", "com.ontotext.s4.api.uima.types.com.ontotext.s4.api.types.news.Keyphrase");
    ll_cas.ll_setStringValue(addr, casFeatCode_organization1Uri, v);}
    
  
 
  /** @generated */
  final Feature casFeat_relType;
  /** @generated */
  final int     casFeatCode_relType;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getRelType(int addr) {
        if (featOkTst && casFeat_relType == null)
      jcas.throwFeatMissing("relType", "com.ontotext.s4.api.uima.types.com.ontotext.s4.api.types.news.Keyphrase");
    return ll_cas.ll_getStringValue(addr, casFeatCode_relType);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setRelType(int addr, String v) {
        if (featOkTst && casFeat_relType == null)
      jcas.throwFeatMissing("relType", "com.ontotext.s4.api.uima.types.com.ontotext.s4.api.types.news.Keyphrase");
    ll_cas.ll_setStringValue(addr, casFeatCode_relType, v);}
    
  
 
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
      jcas.throwFeatMissing("rule", "com.ontotext.s4.api.uima.types.com.ontotext.s4.api.types.news.Keyphrase");
    return ll_cas.ll_getStringValue(addr, casFeatCode_rule);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setRule(int addr, String v) {
        if (featOkTst && casFeat_rule == null)
      jcas.throwFeatMissing("rule", "com.ontotext.s4.api.uima.types.com.ontotext.s4.api.types.news.Keyphrase");
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
      jcas.throwFeatMissing("string", "com.ontotext.s4.api.uima.types.com.ontotext.s4.api.types.news.Keyphrase");
    return ll_cas.ll_getStringValue(addr, casFeatCode_string);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setString(int addr, String v) {
        if (featOkTst && casFeat_string == null)
      jcas.throwFeatMissing("string", "com.ontotext.s4.api.uima.types.com.ontotext.s4.api.types.news.Keyphrase");
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
      jcas.throwFeatMissing("class_feature", "com.ontotext.s4.api.uima.types.com.ontotext.s4.api.types.news.Keyphrase");
    return ll_cas.ll_getStringValue(addr, casFeatCode_class_feature);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setClass_feature(int addr, String v) {
        if (featOkTst && casFeat_class_feature == null)
      jcas.throwFeatMissing("class_feature", "com.ontotext.s4.api.uima.types.com.ontotext.s4.api.types.news.Keyphrase");
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
      jcas.throwFeatMissing("inst", "com.ontotext.s4.api.uima.types.com.ontotext.s4.api.types.news.Keyphrase");
    return ll_cas.ll_getStringValue(addr, casFeatCode_inst);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setInst(int addr, String v) {
        if (featOkTst && casFeat_inst == null)
      jcas.throwFeatMissing("inst", "com.ontotext.s4.api.uima.types.com.ontotext.s4.api.types.news.Keyphrase");
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
      jcas.throwFeatMissing("type_feature", "com.ontotext.s4.api.uima.types.com.ontotext.s4.api.types.news.Keyphrase");
    return ll_cas.ll_getStringValue(addr, casFeatCode_type_feature);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setType_feature(int addr, String v) {
        if (featOkTst && casFeat_type_feature == null)
      jcas.throwFeatMissing("type_feature", "com.ontotext.s4.api.uima.types.com.ontotext.s4.api.types.news.Keyphrase");
    ll_cas.ll_setStringValue(addr, casFeatCode_type_feature, v);}
    
  
 
  /** @generated */
  final Feature casFeat_organizationStr;
  /** @generated */
  final int     casFeatCode_organizationStr;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getOrganizationStr(int addr) {
        if (featOkTst && casFeat_organizationStr == null)
      jcas.throwFeatMissing("organizationStr", "com.ontotext.s4.api.uima.types.com.ontotext.s4.api.types.news.Keyphrase");
    return ll_cas.ll_getStringValue(addr, casFeatCode_organizationStr);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setOrganizationStr(int addr, String v) {
        if (featOkTst && casFeat_organizationStr == null)
      jcas.throwFeatMissing("organizationStr", "com.ontotext.s4.api.uima.types.com.ontotext.s4.api.types.news.Keyphrase");
    ll_cas.ll_setStringValue(addr, casFeatCode_organizationStr, v);}
    
  
 
  /** @generated */
  final Feature casFeat_abbrevUri;
  /** @generated */
  final int     casFeatCode_abbrevUri;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getAbbrevUri(int addr) {
        if (featOkTst && casFeat_abbrevUri == null)
      jcas.throwFeatMissing("abbrevUri", "com.ontotext.s4.api.uima.types.com.ontotext.s4.api.types.news.Keyphrase");
    return ll_cas.ll_getStringValue(addr, casFeatCode_abbrevUri);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setAbbrevUri(int addr, String v) {
        if (featOkTst && casFeat_abbrevUri == null)
      jcas.throwFeatMissing("abbrevUri", "com.ontotext.s4.api.uima.types.com.ontotext.s4.api.types.news.Keyphrase");
    ll_cas.ll_setStringValue(addr, casFeatCode_abbrevUri, v);}
    
  
 
  /** @generated */
  final Feature casFeat_abbrevStr;
  /** @generated */
  final int     casFeatCode_abbrevStr;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getAbbrevStr(int addr) {
        if (featOkTst && casFeat_abbrevStr == null)
      jcas.throwFeatMissing("abbrevStr", "com.ontotext.s4.api.uima.types.com.ontotext.s4.api.types.news.Keyphrase");
    return ll_cas.ll_getStringValue(addr, casFeatCode_abbrevStr);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setAbbrevStr(int addr, String v) {
        if (featOkTst && casFeat_abbrevStr == null)
      jcas.throwFeatMissing("abbrevStr", "com.ontotext.s4.api.uima.types.com.ontotext.s4.api.types.news.Keyphrase");
    ll_cas.ll_setStringValue(addr, casFeatCode_abbrevStr, v);}
    
  
 
  /** @generated */
  final Feature casFeat_quotationStr;
  /** @generated */
  final int     casFeatCode_quotationStr;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getQuotationStr(int addr) {
        if (featOkTst && casFeat_quotationStr == null)
      jcas.throwFeatMissing("quotationStr", "com.ontotext.s4.api.uima.types.com.ontotext.s4.api.types.news.Keyphrase");
    return ll_cas.ll_getStringValue(addr, casFeatCode_quotationStr);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setQuotationStr(int addr, String v) {
        if (featOkTst && casFeat_quotationStr == null)
      jcas.throwFeatMissing("quotationStr", "com.ontotext.s4.api.uima.types.com.ontotext.s4.api.types.news.Keyphrase");
    ll_cas.ll_setStringValue(addr, casFeatCode_quotationStr, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public Keyphrase_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_organization1Str = jcas.getRequiredFeatureDE(casType, "organization1Str", "uima.cas.String", featOkTst);
    casFeatCode_organization1Str  = (null == casFeat_organization1Str) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_organization1Str).getCode();

 
    casFeat_organizationUri = jcas.getRequiredFeatureDE(casType, "organizationUri", "uima.cas.String", featOkTst);
    casFeatCode_organizationUri  = (null == casFeat_organizationUri) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_organizationUri).getCode();

 
    casFeat_organization1Uri = jcas.getRequiredFeatureDE(casType, "organization1Uri", "uima.cas.String", featOkTst);
    casFeatCode_organization1Uri  = (null == casFeat_organization1Uri) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_organization1Uri).getCode();

 
    casFeat_relType = jcas.getRequiredFeatureDE(casType, "relType", "uima.cas.String", featOkTst);
    casFeatCode_relType  = (null == casFeat_relType) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_relType).getCode();

 
    casFeat_rule = jcas.getRequiredFeatureDE(casType, "rule", "uima.cas.String", featOkTst);
    casFeatCode_rule  = (null == casFeat_rule) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_rule).getCode();

 
    casFeat_string = jcas.getRequiredFeatureDE(casType, "string", "uima.cas.String", featOkTst);
    casFeatCode_string  = (null == casFeat_string) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_string).getCode();

 
    casFeat_class_feature = jcas.getRequiredFeatureDE(casType, "class_feature", "uima.cas.String", featOkTst);
    casFeatCode_class_feature  = (null == casFeat_class_feature) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_class_feature).getCode();

 
    casFeat_inst = jcas.getRequiredFeatureDE(casType, "inst", "uima.cas.String", featOkTst);
    casFeatCode_inst  = (null == casFeat_inst) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_inst).getCode();

 
    casFeat_type_feature = jcas.getRequiredFeatureDE(casType, "type_feature", "uima.cas.String", featOkTst);
    casFeatCode_type_feature  = (null == casFeat_type_feature) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_type_feature).getCode();

 
    casFeat_organizationStr = jcas.getRequiredFeatureDE(casType, "organizationStr", "uima.cas.String", featOkTst);
    casFeatCode_organizationStr  = (null == casFeat_organizationStr) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_organizationStr).getCode();

 
    casFeat_abbrevUri = jcas.getRequiredFeatureDE(casType, "abbrevUri", "uima.cas.String", featOkTst);
    casFeatCode_abbrevUri  = (null == casFeat_abbrevUri) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_abbrevUri).getCode();

 
    casFeat_abbrevStr = jcas.getRequiredFeatureDE(casType, "abbrevStr", "uima.cas.String", featOkTst);
    casFeatCode_abbrevStr  = (null == casFeat_abbrevStr) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_abbrevStr).getCode();

 
    casFeat_quotationStr = jcas.getRequiredFeatureDE(casType, "quotationStr", "uima.cas.String", featOkTst);
    casFeatCode_quotationStr  = (null == casFeat_quotationStr) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_quotationStr).getCode();

  }
}



    