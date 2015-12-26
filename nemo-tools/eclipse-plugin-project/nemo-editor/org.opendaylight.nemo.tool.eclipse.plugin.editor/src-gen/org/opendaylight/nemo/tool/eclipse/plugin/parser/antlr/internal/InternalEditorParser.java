package org.opendaylight.nemo.tool.eclipse.plugin.parser.antlr.internal; 

import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.AbstractInternalAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.parser.antlr.AntlrDatatypeRuleToken;
import org.opendaylight.nemo.tool.eclipse.plugin.services.EditorGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalEditorParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_IPV4_SUB", "RULE_STRING", "RULE_NEMOID", "RULE_INT", "RULE_ID", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'Engines:'", "','", "';'", "'.'", "'CREATE'", "'Node'", "'Type'", "'Contain'", "'UPDATE'", "'DELETE'", "'Connection'", "'Endnodes'", "'Flow'", "'Match'", "':'", "'Operation'", "'Priority'", "'Target'", "'Action'", "'Condition'", "'Property '", "'Property'", "'IMPORT'"
    };
    public static final int RULE_STRING=5;
    public static final int RULE_SL_COMMENT=10;
    public static final int T__19=19;
    public static final int T__15=15;
    public static final int T__16=16;
    public static final int T__17=17;
    public static final int T__18=18;
    public static final int T__33=33;
    public static final int T__34=34;
    public static final int T__13=13;
    public static final int T__35=35;
    public static final int T__14=14;
    public static final int EOF=-1;
    public static final int T__30=30;
    public static final int T__31=31;
    public static final int T__32=32;
    public static final int RULE_ID=8;
    public static final int RULE_WS=11;
    public static final int RULE_ANY_OTHER=12;
    public static final int T__26=26;
    public static final int T__27=27;
    public static final int RULE_IPV4_SUB=4;
    public static final int T__28=28;
    public static final int RULE_INT=7;
    public static final int T__29=29;
    public static final int RULE_NEMOID=6;
    public static final int T__22=22;
    public static final int RULE_ML_COMMENT=9;
    public static final int T__23=23;
    public static final int T__24=24;
    public static final int T__25=25;
    public static final int T__20=20;
    public static final int T__21=21;

    // delegates
    // delegators


        public InternalEditorParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalEditorParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalEditorParser.tokenNames; }
    public String getGrammarFileName() { return "../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g"; }



     	private EditorGrammarAccess grammarAccess;
     	
        public InternalEditorParser(TokenStream input, EditorGrammarAccess grammarAccess) {
            this(input);
            this.grammarAccess = grammarAccess;
            registerRules(grammarAccess.getGrammar());
        }
        
        @Override
        protected String getFirstRuleName() {
        	return "Model";	
       	}
       	
       	@Override
       	protected EditorGrammarAccess getGrammarAccess() {
       		return grammarAccess;
       	}



    // $ANTLR start "entryRuleModel"
    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:67:1: entryRuleModel returns [EObject current=null] : iv_ruleModel= ruleModel EOF ;
    public final EObject entryRuleModel() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleModel = null;


        try {
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:68:2: (iv_ruleModel= ruleModel EOF )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:69:2: iv_ruleModel= ruleModel EOF
            {
             newCompositeNode(grammarAccess.getModelRule()); 
            pushFollow(FOLLOW_ruleModel_in_entryRuleModel75);
            iv_ruleModel=ruleModel();

            state._fsp--;

             current =iv_ruleModel; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleModel85); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleModel"


    // $ANTLR start "ruleModel"
    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:76:1: ruleModel returns [EObject current=null] : ( (lv_sentences_0_0= ruleSentence ) )* ;
    public final EObject ruleModel() throws RecognitionException {
        EObject current = null;

        EObject lv_sentences_0_0 = null;


         enterRule(); 
            
        try {
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:79:28: ( ( (lv_sentences_0_0= ruleSentence ) )* )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:80:1: ( (lv_sentences_0_0= ruleSentence ) )*
            {
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:80:1: ( (lv_sentences_0_0= ruleSentence ) )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==13||LA1_0==17||(LA1_0>=21 && LA1_0<=22)||LA1_0==35) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:81:1: (lv_sentences_0_0= ruleSentence )
            	    {
            	    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:81:1: (lv_sentences_0_0= ruleSentence )
            	    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:82:3: lv_sentences_0_0= ruleSentence
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getModelAccess().getSentencesSentenceParserRuleCall_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleSentence_in_ruleModel130);
            	    lv_sentences_0_0=ruleSentence();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getModelRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"sentences",
            	            		lv_sentences_0_0, 
            	            		"Sentence");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleModel"


    // $ANTLR start "entryRuleSentence"
    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:106:1: entryRuleSentence returns [EObject current=null] : iv_ruleSentence= ruleSentence EOF ;
    public final EObject entryRuleSentence() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSentence = null;


        try {
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:107:2: (iv_ruleSentence= ruleSentence EOF )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:108:2: iv_ruleSentence= ruleSentence EOF
            {
             newCompositeNode(grammarAccess.getSentenceRule()); 
            pushFollow(FOLLOW_ruleSentence_in_entryRuleSentence166);
            iv_ruleSentence=ruleSentence();

            state._fsp--;

             current =iv_ruleSentence; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleSentence176); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSentence"


    // $ANTLR start "ruleSentence"
    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:115:1: ruleSentence returns [EObject current=null] : ( ruleSenEngines | ruleUser | this_Node_2= ruleNode | this_NodeOperating_3= ruleNodeOperating | this_Connection_4= ruleConnection | this_ConnectionUpdate_5= ruleConnectionUpdate | this_Flow_6= ruleFlow | this_FlowUpdate_7= ruleFlowUpdate | this_Operation_8= ruleOperation ) ;
    public final EObject ruleSentence() throws RecognitionException {
        EObject current = null;

        EObject this_Node_2 = null;

        EObject this_NodeOperating_3 = null;

        EObject this_Connection_4 = null;

        EObject this_ConnectionUpdate_5 = null;

        EObject this_Flow_6 = null;

        EObject this_FlowUpdate_7 = null;

        EObject this_Operation_8 = null;


         enterRule(); 
            
        try {
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:118:28: ( ( ruleSenEngines | ruleUser | this_Node_2= ruleNode | this_NodeOperating_3= ruleNodeOperating | this_Connection_4= ruleConnection | this_ConnectionUpdate_5= ruleConnectionUpdate | this_Flow_6= ruleFlow | this_FlowUpdate_7= ruleFlowUpdate | this_Operation_8= ruleOperation ) )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:119:1: ( ruleSenEngines | ruleUser | this_Node_2= ruleNode | this_NodeOperating_3= ruleNodeOperating | this_Connection_4= ruleConnection | this_ConnectionUpdate_5= ruleConnectionUpdate | this_Flow_6= ruleFlow | this_FlowUpdate_7= ruleFlowUpdate | this_Operation_8= ruleOperation )
            {
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:119:1: ( ruleSenEngines | ruleUser | this_Node_2= ruleNode | this_NodeOperating_3= ruleNodeOperating | this_Connection_4= ruleConnection | this_ConnectionUpdate_5= ruleConnectionUpdate | this_Flow_6= ruleFlow | this_FlowUpdate_7= ruleFlowUpdate | this_Operation_8= ruleOperation )
            int alt2=9;
            alt2 = dfa2.predict(input);
            switch (alt2) {
                case 1 :
                    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:120:5: ruleSenEngines
                    {
                     
                            newCompositeNode(grammarAccess.getSentenceAccess().getSenEnginesParserRuleCall_0()); 
                        
                    pushFollow(FOLLOW_ruleSenEngines_in_ruleSentence217);
                    ruleSenEngines();

                    state._fsp--;

                     
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 2 :
                    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:129:5: ruleUser
                    {
                     
                            newCompositeNode(grammarAccess.getSentenceAccess().getUserParserRuleCall_1()); 
                        
                    pushFollow(FOLLOW_ruleUser_in_ruleSentence238);
                    ruleUser();

                    state._fsp--;

                     
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 3 :
                    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:138:5: this_Node_2= ruleNode
                    {
                     
                            newCompositeNode(grammarAccess.getSentenceAccess().getNodeParserRuleCall_2()); 
                        
                    pushFollow(FOLLOW_ruleNode_in_ruleSentence265);
                    this_Node_2=ruleNode();

                    state._fsp--;

                     
                            current = this_Node_2; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 4 :
                    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:148:5: this_NodeOperating_3= ruleNodeOperating
                    {
                     
                            newCompositeNode(grammarAccess.getSentenceAccess().getNodeOperatingParserRuleCall_3()); 
                        
                    pushFollow(FOLLOW_ruleNodeOperating_in_ruleSentence292);
                    this_NodeOperating_3=ruleNodeOperating();

                    state._fsp--;

                     
                            current = this_NodeOperating_3; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 5 :
                    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:158:5: this_Connection_4= ruleConnection
                    {
                     
                            newCompositeNode(grammarAccess.getSentenceAccess().getConnectionParserRuleCall_4()); 
                        
                    pushFollow(FOLLOW_ruleConnection_in_ruleSentence319);
                    this_Connection_4=ruleConnection();

                    state._fsp--;

                     
                            current = this_Connection_4; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 6 :
                    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:168:5: this_ConnectionUpdate_5= ruleConnectionUpdate
                    {
                     
                            newCompositeNode(grammarAccess.getSentenceAccess().getConnectionUpdateParserRuleCall_5()); 
                        
                    pushFollow(FOLLOW_ruleConnectionUpdate_in_ruleSentence346);
                    this_ConnectionUpdate_5=ruleConnectionUpdate();

                    state._fsp--;

                     
                            current = this_ConnectionUpdate_5; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 7 :
                    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:178:5: this_Flow_6= ruleFlow
                    {
                     
                            newCompositeNode(grammarAccess.getSentenceAccess().getFlowParserRuleCall_6()); 
                        
                    pushFollow(FOLLOW_ruleFlow_in_ruleSentence373);
                    this_Flow_6=ruleFlow();

                    state._fsp--;

                     
                            current = this_Flow_6; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 8 :
                    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:188:5: this_FlowUpdate_7= ruleFlowUpdate
                    {
                     
                            newCompositeNode(grammarAccess.getSentenceAccess().getFlowUpdateParserRuleCall_7()); 
                        
                    pushFollow(FOLLOW_ruleFlowUpdate_in_ruleSentence400);
                    this_FlowUpdate_7=ruleFlowUpdate();

                    state._fsp--;

                     
                            current = this_FlowUpdate_7; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 9 :
                    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:198:5: this_Operation_8= ruleOperation
                    {
                     
                            newCompositeNode(grammarAccess.getSentenceAccess().getOperationParserRuleCall_8()); 
                        
                    pushFollow(FOLLOW_ruleOperation_in_ruleSentence427);
                    this_Operation_8=ruleOperation();

                    state._fsp--;

                     
                            current = this_Operation_8; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSentence"


    // $ANTLR start "entryRuleSenEngines"
    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:214:1: entryRuleSenEngines returns [String current=null] : iv_ruleSenEngines= ruleSenEngines EOF ;
    public final String entryRuleSenEngines() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSenEngines = null;


        try {
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:215:2: (iv_ruleSenEngines= ruleSenEngines EOF )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:216:2: iv_ruleSenEngines= ruleSenEngines EOF
            {
             newCompositeNode(grammarAccess.getSenEnginesRule()); 
            pushFollow(FOLLOW_ruleSenEngines_in_entryRuleSenEngines463);
            iv_ruleSenEngines=ruleSenEngines();

            state._fsp--;

             current =iv_ruleSenEngines.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleSenEngines474); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSenEngines"


    // $ANTLR start "ruleSenEngines"
    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:223:1: ruleSenEngines returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= 'Engines:' this_Ipv4_1= ruleIpv4 (kw= ',' this_Ipv4_3= ruleIpv4 )* kw= ';' ) ;
    public final AntlrDatatypeRuleToken ruleSenEngines() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_Ipv4_1 = null;

        AntlrDatatypeRuleToken this_Ipv4_3 = null;


         enterRule(); 
            
        try {
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:226:28: ( (kw= 'Engines:' this_Ipv4_1= ruleIpv4 (kw= ',' this_Ipv4_3= ruleIpv4 )* kw= ';' ) )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:227:1: (kw= 'Engines:' this_Ipv4_1= ruleIpv4 (kw= ',' this_Ipv4_3= ruleIpv4 )* kw= ';' )
            {
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:227:1: (kw= 'Engines:' this_Ipv4_1= ruleIpv4 (kw= ',' this_Ipv4_3= ruleIpv4 )* kw= ';' )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:228:2: kw= 'Engines:' this_Ipv4_1= ruleIpv4 (kw= ',' this_Ipv4_3= ruleIpv4 )* kw= ';'
            {
            kw=(Token)match(input,13,FOLLOW_13_in_ruleSenEngines512); 

                    current.merge(kw);
                    newLeafNode(kw, grammarAccess.getSenEnginesAccess().getEnginesKeyword_0()); 
                
             
                    newCompositeNode(grammarAccess.getSenEnginesAccess().getIpv4ParserRuleCall_1()); 
                
            pushFollow(FOLLOW_ruleIpv4_in_ruleSenEngines534);
            this_Ipv4_1=ruleIpv4();

            state._fsp--;


            		current.merge(this_Ipv4_1);
                
             
                    afterParserOrEnumRuleCall();
                
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:244:1: (kw= ',' this_Ipv4_3= ruleIpv4 )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==14) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:245:2: kw= ',' this_Ipv4_3= ruleIpv4
            	    {
            	    kw=(Token)match(input,14,FOLLOW_14_in_ruleSenEngines553); 

            	            current.merge(kw);
            	            newLeafNode(kw, grammarAccess.getSenEnginesAccess().getCommaKeyword_2_0()); 
            	        
            	     
            	            newCompositeNode(grammarAccess.getSenEnginesAccess().getIpv4ParserRuleCall_2_1()); 
            	        
            	    pushFollow(FOLLOW_ruleIpv4_in_ruleSenEngines575);
            	    this_Ipv4_3=ruleIpv4();

            	    state._fsp--;


            	    		current.merge(this_Ipv4_3);
            	        
            	     
            	            afterParserOrEnumRuleCall();
            	        

            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);

            kw=(Token)match(input,15,FOLLOW_15_in_ruleSenEngines595); 

                    current.merge(kw);
                    newLeafNode(kw, grammarAccess.getSenEnginesAccess().getSemicolonKeyword_3()); 
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSenEngines"


    // $ANTLR start "entryRuleIpv4"
    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:275:1: entryRuleIpv4 returns [String current=null] : iv_ruleIpv4= ruleIpv4 EOF ;
    public final String entryRuleIpv4() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleIpv4 = null;


        try {
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:276:2: (iv_ruleIpv4= ruleIpv4 EOF )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:277:2: iv_ruleIpv4= ruleIpv4 EOF
            {
             newCompositeNode(grammarAccess.getIpv4Rule()); 
            pushFollow(FOLLOW_ruleIpv4_in_entryRuleIpv4636);
            iv_ruleIpv4=ruleIpv4();

            state._fsp--;

             current =iv_ruleIpv4.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleIpv4647); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleIpv4"


    // $ANTLR start "ruleIpv4"
    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:284:1: ruleIpv4 returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_IPV4_SUB_0= RULE_IPV4_SUB kw= '.' this_IPV4_SUB_2= RULE_IPV4_SUB kw= '.' this_IPV4_SUB_4= RULE_IPV4_SUB kw= '.' this_IPV4_SUB_6= RULE_IPV4_SUB ) ;
    public final AntlrDatatypeRuleToken ruleIpv4() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_IPV4_SUB_0=null;
        Token kw=null;
        Token this_IPV4_SUB_2=null;
        Token this_IPV4_SUB_4=null;
        Token this_IPV4_SUB_6=null;

         enterRule(); 
            
        try {
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:287:28: ( (this_IPV4_SUB_0= RULE_IPV4_SUB kw= '.' this_IPV4_SUB_2= RULE_IPV4_SUB kw= '.' this_IPV4_SUB_4= RULE_IPV4_SUB kw= '.' this_IPV4_SUB_6= RULE_IPV4_SUB ) )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:288:1: (this_IPV4_SUB_0= RULE_IPV4_SUB kw= '.' this_IPV4_SUB_2= RULE_IPV4_SUB kw= '.' this_IPV4_SUB_4= RULE_IPV4_SUB kw= '.' this_IPV4_SUB_6= RULE_IPV4_SUB )
            {
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:288:1: (this_IPV4_SUB_0= RULE_IPV4_SUB kw= '.' this_IPV4_SUB_2= RULE_IPV4_SUB kw= '.' this_IPV4_SUB_4= RULE_IPV4_SUB kw= '.' this_IPV4_SUB_6= RULE_IPV4_SUB )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:288:6: this_IPV4_SUB_0= RULE_IPV4_SUB kw= '.' this_IPV4_SUB_2= RULE_IPV4_SUB kw= '.' this_IPV4_SUB_4= RULE_IPV4_SUB kw= '.' this_IPV4_SUB_6= RULE_IPV4_SUB
            {
            this_IPV4_SUB_0=(Token)match(input,RULE_IPV4_SUB,FOLLOW_RULE_IPV4_SUB_in_ruleIpv4687); 

            		current.merge(this_IPV4_SUB_0);
                
             
                newLeafNode(this_IPV4_SUB_0, grammarAccess.getIpv4Access().getIPV4_SUBTerminalRuleCall_0()); 
                
            kw=(Token)match(input,16,FOLLOW_16_in_ruleIpv4705); 

                    current.merge(kw);
                    newLeafNode(kw, grammarAccess.getIpv4Access().getFullStopKeyword_1()); 
                
            this_IPV4_SUB_2=(Token)match(input,RULE_IPV4_SUB,FOLLOW_RULE_IPV4_SUB_in_ruleIpv4720); 

            		current.merge(this_IPV4_SUB_2);
                
             
                newLeafNode(this_IPV4_SUB_2, grammarAccess.getIpv4Access().getIPV4_SUBTerminalRuleCall_2()); 
                
            kw=(Token)match(input,16,FOLLOW_16_in_ruleIpv4738); 

                    current.merge(kw);
                    newLeafNode(kw, grammarAccess.getIpv4Access().getFullStopKeyword_3()); 
                
            this_IPV4_SUB_4=(Token)match(input,RULE_IPV4_SUB,FOLLOW_RULE_IPV4_SUB_in_ruleIpv4753); 

            		current.merge(this_IPV4_SUB_4);
                
             
                newLeafNode(this_IPV4_SUB_4, grammarAccess.getIpv4Access().getIPV4_SUBTerminalRuleCall_4()); 
                
            kw=(Token)match(input,16,FOLLOW_16_in_ruleIpv4771); 

                    current.merge(kw);
                    newLeafNode(kw, grammarAccess.getIpv4Access().getFullStopKeyword_5()); 
                
            this_IPV4_SUB_6=(Token)match(input,RULE_IPV4_SUB,FOLLOW_RULE_IPV4_SUB_in_ruleIpv4786); 

            		current.merge(this_IPV4_SUB_6);
                
             
                newLeafNode(this_IPV4_SUB_6, grammarAccess.getIpv4Access().getIPV4_SUBTerminalRuleCall_6()); 
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleIpv4"


    // $ANTLR start "entryRuleUser"
    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:342:1: entryRuleUser returns [String current=null] : iv_ruleUser= ruleUser EOF ;
    public final String entryRuleUser() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleUser = null;


        try {
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:343:2: (iv_ruleUser= ruleUser EOF )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:344:2: iv_ruleUser= ruleUser EOF
            {
             newCompositeNode(grammarAccess.getUserRule()); 
            pushFollow(FOLLOW_ruleUser_in_entryRuleUser832);
            iv_ruleUser=ruleUser();

            state._fsp--;

             current =iv_ruleUser.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleUser843); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleUser"


    // $ANTLR start "ruleUser"
    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:351:1: ruleUser returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= 'CREATE' this_STRING_1= RULE_STRING this_STRING_2= RULE_STRING this_STRING_3= RULE_STRING kw= ';' ) ;
    public final AntlrDatatypeRuleToken ruleUser() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_STRING_1=null;
        Token this_STRING_2=null;
        Token this_STRING_3=null;

         enterRule(); 
            
        try {
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:354:28: ( (kw= 'CREATE' this_STRING_1= RULE_STRING this_STRING_2= RULE_STRING this_STRING_3= RULE_STRING kw= ';' ) )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:355:1: (kw= 'CREATE' this_STRING_1= RULE_STRING this_STRING_2= RULE_STRING this_STRING_3= RULE_STRING kw= ';' )
            {
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:355:1: (kw= 'CREATE' this_STRING_1= RULE_STRING this_STRING_2= RULE_STRING this_STRING_3= RULE_STRING kw= ';' )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:356:2: kw= 'CREATE' this_STRING_1= RULE_STRING this_STRING_2= RULE_STRING this_STRING_3= RULE_STRING kw= ';'
            {
            kw=(Token)match(input,17,FOLLOW_17_in_ruleUser881); 

                    current.merge(kw);
                    newLeafNode(kw, grammarAccess.getUserAccess().getCREATEKeyword_0()); 
                
            this_STRING_1=(Token)match(input,RULE_STRING,FOLLOW_RULE_STRING_in_ruleUser896); 

            		current.merge(this_STRING_1);
                
             
                newLeafNode(this_STRING_1, grammarAccess.getUserAccess().getSTRINGTerminalRuleCall_1()); 
                
            this_STRING_2=(Token)match(input,RULE_STRING,FOLLOW_RULE_STRING_in_ruleUser916); 

            		current.merge(this_STRING_2);
                
             
                newLeafNode(this_STRING_2, grammarAccess.getUserAccess().getSTRINGTerminalRuleCall_2()); 
                
            this_STRING_3=(Token)match(input,RULE_STRING,FOLLOW_RULE_STRING_in_ruleUser936); 

            		current.merge(this_STRING_3);
                
             
                newLeafNode(this_STRING_3, grammarAccess.getUserAccess().getSTRINGTerminalRuleCall_3()); 
                
            kw=(Token)match(input,15,FOLLOW_15_in_ruleUser954); 

                    current.merge(kw);
                    newLeafNode(kw, grammarAccess.getUserAccess().getSemicolonKeyword_4()); 
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleUser"


    // $ANTLR start "entryRuleNode"
    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:396:1: entryRuleNode returns [EObject current=null] : iv_ruleNode= ruleNode EOF ;
    public final EObject entryRuleNode() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleNode = null;


        try {
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:397:2: (iv_ruleNode= ruleNode EOF )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:398:2: iv_ruleNode= ruleNode EOF
            {
             newCompositeNode(grammarAccess.getNodeRule()); 
            pushFollow(FOLLOW_ruleNode_in_entryRuleNode994);
            iv_ruleNode=ruleNode();

            state._fsp--;

             current =iv_ruleNode; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleNode1004); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleNode"


    // $ANTLR start "ruleNode"
    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:405:1: ruleNode returns [EObject current=null] : ( ruleNewObj otherlv_1= 'Node' ( (lv_name_2_0= RULE_NEMOID ) ) (otherlv_3= 'Type' this_NemoId_4= RULE_NEMOID ) (otherlv_5= 'Contain' ( (otherlv_6= RULE_NEMOID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_NEMOID ) ) )* )? ( ruleProperty )? otherlv_10= ';' ) ;
    public final EObject ruleNode() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_name_2_0=null;
        Token otherlv_3=null;
        Token this_NemoId_4=null;
        Token otherlv_5=null;
        Token otherlv_6=null;
        Token otherlv_7=null;
        Token otherlv_8=null;
        Token otherlv_10=null;

         enterRule(); 
            
        try {
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:408:28: ( ( ruleNewObj otherlv_1= 'Node' ( (lv_name_2_0= RULE_NEMOID ) ) (otherlv_3= 'Type' this_NemoId_4= RULE_NEMOID ) (otherlv_5= 'Contain' ( (otherlv_6= RULE_NEMOID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_NEMOID ) ) )* )? ( ruleProperty )? otherlv_10= ';' ) )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:409:1: ( ruleNewObj otherlv_1= 'Node' ( (lv_name_2_0= RULE_NEMOID ) ) (otherlv_3= 'Type' this_NemoId_4= RULE_NEMOID ) (otherlv_5= 'Contain' ( (otherlv_6= RULE_NEMOID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_NEMOID ) ) )* )? ( ruleProperty )? otherlv_10= ';' )
            {
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:409:1: ( ruleNewObj otherlv_1= 'Node' ( (lv_name_2_0= RULE_NEMOID ) ) (otherlv_3= 'Type' this_NemoId_4= RULE_NEMOID ) (otherlv_5= 'Contain' ( (otherlv_6= RULE_NEMOID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_NEMOID ) ) )* )? ( ruleProperty )? otherlv_10= ';' )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:410:5: ruleNewObj otherlv_1= 'Node' ( (lv_name_2_0= RULE_NEMOID ) ) (otherlv_3= 'Type' this_NemoId_4= RULE_NEMOID ) (otherlv_5= 'Contain' ( (otherlv_6= RULE_NEMOID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_NEMOID ) ) )* )? ( ruleProperty )? otherlv_10= ';'
            {
             
                    newCompositeNode(grammarAccess.getNodeAccess().getNewObjParserRuleCall_0()); 
                
            pushFollow(FOLLOW_ruleNewObj_in_ruleNode1045);
            ruleNewObj();

            state._fsp--;

             
                    afterParserOrEnumRuleCall();
                
            otherlv_1=(Token)match(input,18,FOLLOW_18_in_ruleNode1056); 

                	newLeafNode(otherlv_1, grammarAccess.getNodeAccess().getNodeKeyword_1());
                
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:421:1: ( (lv_name_2_0= RULE_NEMOID ) )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:422:1: (lv_name_2_0= RULE_NEMOID )
            {
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:422:1: (lv_name_2_0= RULE_NEMOID )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:423:3: lv_name_2_0= RULE_NEMOID
            {
            lv_name_2_0=(Token)match(input,RULE_NEMOID,FOLLOW_RULE_NEMOID_in_ruleNode1073); 

            			newLeafNode(lv_name_2_0, grammarAccess.getNodeAccess().getNameNemoIdTerminalRuleCall_2_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getNodeRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"name",
                    		lv_name_2_0, 
                    		"NemoId");
            	    

            }


            }

            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:439:2: (otherlv_3= 'Type' this_NemoId_4= RULE_NEMOID )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:439:4: otherlv_3= 'Type' this_NemoId_4= RULE_NEMOID
            {
            otherlv_3=(Token)match(input,19,FOLLOW_19_in_ruleNode1091); 

                	newLeafNode(otherlv_3, grammarAccess.getNodeAccess().getTypeKeyword_3_0());
                
            this_NemoId_4=(Token)match(input,RULE_NEMOID,FOLLOW_RULE_NEMOID_in_ruleNode1102); 
             
                newLeafNode(this_NemoId_4, grammarAccess.getNodeAccess().getNemoIdTerminalRuleCall_3_1()); 
                

            }

            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:447:2: (otherlv_5= 'Contain' ( (otherlv_6= RULE_NEMOID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_NEMOID ) ) )* )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==20) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:447:4: otherlv_5= 'Contain' ( (otherlv_6= RULE_NEMOID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_NEMOID ) ) )*
                    {
                    otherlv_5=(Token)match(input,20,FOLLOW_20_in_ruleNode1115); 

                        	newLeafNode(otherlv_5, grammarAccess.getNodeAccess().getContainKeyword_4_0());
                        
                    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:451:1: ( (otherlv_6= RULE_NEMOID ) )
                    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:452:1: (otherlv_6= RULE_NEMOID )
                    {
                    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:452:1: (otherlv_6= RULE_NEMOID )
                    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:453:3: otherlv_6= RULE_NEMOID
                    {

                    			if (current==null) {
                    	            current = createModelElement(grammarAccess.getNodeRule());
                    	        }
                            
                    otherlv_6=(Token)match(input,RULE_NEMOID,FOLLOW_RULE_NEMOID_in_ruleNode1135); 

                    		newLeafNode(otherlv_6, grammarAccess.getNodeAccess().getNodesNodeCrossReference_4_1_0()); 
                    	

                    }


                    }

                    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:464:2: (otherlv_7= ',' ( (otherlv_8= RULE_NEMOID ) ) )*
                    loop4:
                    do {
                        int alt4=2;
                        int LA4_0 = input.LA(1);

                        if ( (LA4_0==14) ) {
                            alt4=1;
                        }


                        switch (alt4) {
                    	case 1 :
                    	    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:464:4: otherlv_7= ',' ( (otherlv_8= RULE_NEMOID ) )
                    	    {
                    	    otherlv_7=(Token)match(input,14,FOLLOW_14_in_ruleNode1148); 

                    	        	newLeafNode(otherlv_7, grammarAccess.getNodeAccess().getCommaKeyword_4_2_0());
                    	        
                    	    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:468:1: ( (otherlv_8= RULE_NEMOID ) )
                    	    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:469:1: (otherlv_8= RULE_NEMOID )
                    	    {
                    	    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:469:1: (otherlv_8= RULE_NEMOID )
                    	    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:470:3: otherlv_8= RULE_NEMOID
                    	    {

                    	    			if (current==null) {
                    	    	            current = createModelElement(grammarAccess.getNodeRule());
                    	    	        }
                    	            
                    	    otherlv_8=(Token)match(input,RULE_NEMOID,FOLLOW_RULE_NEMOID_in_ruleNode1168); 

                    	    		newLeafNode(otherlv_8, grammarAccess.getNodeAccess().getNodesNodeCrossReference_4_2_1_0()); 
                    	    	

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop4;
                        }
                    } while (true);


                    }
                    break;

            }

            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:481:6: ( ruleProperty )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==33) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:482:5: ruleProperty
                    {
                     
                            newCompositeNode(grammarAccess.getNodeAccess().getPropertyParserRuleCall_5()); 
                        
                    pushFollow(FOLLOW_ruleProperty_in_ruleNode1189);
                    ruleProperty();

                    state._fsp--;

                     
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;

            }

            otherlv_10=(Token)match(input,15,FOLLOW_15_in_ruleNode1202); 

                	newLeafNode(otherlv_10, grammarAccess.getNodeAccess().getSemicolonKeyword_6());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleNode"


    // $ANTLR start "entryRuleNodeOperating"
    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:503:1: entryRuleNodeOperating returns [EObject current=null] : iv_ruleNodeOperating= ruleNodeOperating EOF ;
    public final EObject entryRuleNodeOperating() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleNodeOperating = null;


        try {
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:504:2: (iv_ruleNodeOperating= ruleNodeOperating EOF )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:505:2: iv_ruleNodeOperating= ruleNodeOperating EOF
            {
             newCompositeNode(grammarAccess.getNodeOperatingRule()); 
            pushFollow(FOLLOW_ruleNodeOperating_in_entryRuleNodeOperating1240);
            iv_ruleNodeOperating=ruleNodeOperating();

            state._fsp--;

             current =iv_ruleNodeOperating; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleNodeOperating1250); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleNodeOperating"


    // $ANTLR start "ruleNodeOperating"
    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:512:1: ruleNodeOperating returns [EObject current=null] : ( (otherlv_0= 'UPDATE' | otherlv_1= 'DELETE' ) otherlv_2= 'Node' ( (otherlv_3= RULE_NEMOID ) ) (otherlv_4= 'Type' this_NemoId_5= RULE_NEMOID )? (otherlv_6= 'Contain' ( (otherlv_7= RULE_NEMOID ) ) (otherlv_8= ',' ( (otherlv_9= RULE_NEMOID ) ) )* )? ( ruleProperty )? otherlv_11= ';' ) ;
    public final EObject ruleNodeOperating() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token this_NemoId_5=null;
        Token otherlv_6=null;
        Token otherlv_7=null;
        Token otherlv_8=null;
        Token otherlv_9=null;
        Token otherlv_11=null;

         enterRule(); 
            
        try {
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:515:28: ( ( (otherlv_0= 'UPDATE' | otherlv_1= 'DELETE' ) otherlv_2= 'Node' ( (otherlv_3= RULE_NEMOID ) ) (otherlv_4= 'Type' this_NemoId_5= RULE_NEMOID )? (otherlv_6= 'Contain' ( (otherlv_7= RULE_NEMOID ) ) (otherlv_8= ',' ( (otherlv_9= RULE_NEMOID ) ) )* )? ( ruleProperty )? otherlv_11= ';' ) )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:516:1: ( (otherlv_0= 'UPDATE' | otherlv_1= 'DELETE' ) otherlv_2= 'Node' ( (otherlv_3= RULE_NEMOID ) ) (otherlv_4= 'Type' this_NemoId_5= RULE_NEMOID )? (otherlv_6= 'Contain' ( (otherlv_7= RULE_NEMOID ) ) (otherlv_8= ',' ( (otherlv_9= RULE_NEMOID ) ) )* )? ( ruleProperty )? otherlv_11= ';' )
            {
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:516:1: ( (otherlv_0= 'UPDATE' | otherlv_1= 'DELETE' ) otherlv_2= 'Node' ( (otherlv_3= RULE_NEMOID ) ) (otherlv_4= 'Type' this_NemoId_5= RULE_NEMOID )? (otherlv_6= 'Contain' ( (otherlv_7= RULE_NEMOID ) ) (otherlv_8= ',' ( (otherlv_9= RULE_NEMOID ) ) )* )? ( ruleProperty )? otherlv_11= ';' )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:516:2: (otherlv_0= 'UPDATE' | otherlv_1= 'DELETE' ) otherlv_2= 'Node' ( (otherlv_3= RULE_NEMOID ) ) (otherlv_4= 'Type' this_NemoId_5= RULE_NEMOID )? (otherlv_6= 'Contain' ( (otherlv_7= RULE_NEMOID ) ) (otherlv_8= ',' ( (otherlv_9= RULE_NEMOID ) ) )* )? ( ruleProperty )? otherlv_11= ';'
            {
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:516:2: (otherlv_0= 'UPDATE' | otherlv_1= 'DELETE' )
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==21) ) {
                alt7=1;
            }
            else if ( (LA7_0==22) ) {
                alt7=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;
            }
            switch (alt7) {
                case 1 :
                    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:516:4: otherlv_0= 'UPDATE'
                    {
                    otherlv_0=(Token)match(input,21,FOLLOW_21_in_ruleNodeOperating1288); 

                        	newLeafNode(otherlv_0, grammarAccess.getNodeOperatingAccess().getUPDATEKeyword_0_0());
                        

                    }
                    break;
                case 2 :
                    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:521:7: otherlv_1= 'DELETE'
                    {
                    otherlv_1=(Token)match(input,22,FOLLOW_22_in_ruleNodeOperating1306); 

                        	newLeafNode(otherlv_1, grammarAccess.getNodeOperatingAccess().getDELETEKeyword_0_1());
                        

                    }
                    break;

            }

            otherlv_2=(Token)match(input,18,FOLLOW_18_in_ruleNodeOperating1319); 

                	newLeafNode(otherlv_2, grammarAccess.getNodeOperatingAccess().getNodeKeyword_1());
                
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:529:1: ( (otherlv_3= RULE_NEMOID ) )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:530:1: (otherlv_3= RULE_NEMOID )
            {
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:530:1: (otherlv_3= RULE_NEMOID )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:531:3: otherlv_3= RULE_NEMOID
            {

            			if (current==null) {
            	            current = createModelElement(grammarAccess.getNodeOperatingRule());
            	        }
                    
            otherlv_3=(Token)match(input,RULE_NEMOID,FOLLOW_RULE_NEMOID_in_ruleNodeOperating1339); 

            		newLeafNode(otherlv_3, grammarAccess.getNodeOperatingAccess().getNodenameNodeCrossReference_2_0()); 
            	

            }


            }

            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:542:2: (otherlv_4= 'Type' this_NemoId_5= RULE_NEMOID )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==19) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:542:4: otherlv_4= 'Type' this_NemoId_5= RULE_NEMOID
                    {
                    otherlv_4=(Token)match(input,19,FOLLOW_19_in_ruleNodeOperating1352); 

                        	newLeafNode(otherlv_4, grammarAccess.getNodeOperatingAccess().getTypeKeyword_3_0());
                        
                    this_NemoId_5=(Token)match(input,RULE_NEMOID,FOLLOW_RULE_NEMOID_in_ruleNodeOperating1363); 
                     
                        newLeafNode(this_NemoId_5, grammarAccess.getNodeOperatingAccess().getNemoIdTerminalRuleCall_3_1()); 
                        

                    }
                    break;

            }

            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:550:3: (otherlv_6= 'Contain' ( (otherlv_7= RULE_NEMOID ) ) (otherlv_8= ',' ( (otherlv_9= RULE_NEMOID ) ) )* )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==20) ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:550:5: otherlv_6= 'Contain' ( (otherlv_7= RULE_NEMOID ) ) (otherlv_8= ',' ( (otherlv_9= RULE_NEMOID ) ) )*
                    {
                    otherlv_6=(Token)match(input,20,FOLLOW_20_in_ruleNodeOperating1377); 

                        	newLeafNode(otherlv_6, grammarAccess.getNodeOperatingAccess().getContainKeyword_4_0());
                        
                    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:554:1: ( (otherlv_7= RULE_NEMOID ) )
                    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:555:1: (otherlv_7= RULE_NEMOID )
                    {
                    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:555:1: (otherlv_7= RULE_NEMOID )
                    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:556:3: otherlv_7= RULE_NEMOID
                    {

                    			if (current==null) {
                    	            current = createModelElement(grammarAccess.getNodeOperatingRule());
                    	        }
                            
                    otherlv_7=(Token)match(input,RULE_NEMOID,FOLLOW_RULE_NEMOID_in_ruleNodeOperating1397); 

                    		newLeafNode(otherlv_7, grammarAccess.getNodeOperatingAccess().getNodesNodeCrossReference_4_1_0()); 
                    	

                    }


                    }

                    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:567:2: (otherlv_8= ',' ( (otherlv_9= RULE_NEMOID ) ) )*
                    loop9:
                    do {
                        int alt9=2;
                        int LA9_0 = input.LA(1);

                        if ( (LA9_0==14) ) {
                            alt9=1;
                        }


                        switch (alt9) {
                    	case 1 :
                    	    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:567:4: otherlv_8= ',' ( (otherlv_9= RULE_NEMOID ) )
                    	    {
                    	    otherlv_8=(Token)match(input,14,FOLLOW_14_in_ruleNodeOperating1410); 

                    	        	newLeafNode(otherlv_8, grammarAccess.getNodeOperatingAccess().getCommaKeyword_4_2_0());
                    	        
                    	    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:571:1: ( (otherlv_9= RULE_NEMOID ) )
                    	    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:572:1: (otherlv_9= RULE_NEMOID )
                    	    {
                    	    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:572:1: (otherlv_9= RULE_NEMOID )
                    	    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:573:3: otherlv_9= RULE_NEMOID
                    	    {

                    	    			if (current==null) {
                    	    	            current = createModelElement(grammarAccess.getNodeOperatingRule());
                    	    	        }
                    	            
                    	    otherlv_9=(Token)match(input,RULE_NEMOID,FOLLOW_RULE_NEMOID_in_ruleNodeOperating1430); 

                    	    		newLeafNode(otherlv_9, grammarAccess.getNodeOperatingAccess().getNodesNodeCrossReference_4_2_1_0()); 
                    	    	

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop9;
                        }
                    } while (true);


                    }
                    break;

            }

            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:584:6: ( ruleProperty )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==33) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:585:5: ruleProperty
                    {
                     
                            newCompositeNode(grammarAccess.getNodeOperatingAccess().getPropertyParserRuleCall_5()); 
                        
                    pushFollow(FOLLOW_ruleProperty_in_ruleNodeOperating1451);
                    ruleProperty();

                    state._fsp--;

                     
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;

            }

            otherlv_11=(Token)match(input,15,FOLLOW_15_in_ruleNodeOperating1464); 

                	newLeafNode(otherlv_11, grammarAccess.getNodeOperatingAccess().getSemicolonKeyword_6());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleNodeOperating"


    // $ANTLR start "entryRuleConnection"
    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:604:1: entryRuleConnection returns [EObject current=null] : iv_ruleConnection= ruleConnection EOF ;
    public final EObject entryRuleConnection() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleConnection = null;


        try {
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:605:2: (iv_ruleConnection= ruleConnection EOF )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:606:2: iv_ruleConnection= ruleConnection EOF
            {
             newCompositeNode(grammarAccess.getConnectionRule()); 
            pushFollow(FOLLOW_ruleConnection_in_entryRuleConnection1500);
            iv_ruleConnection=ruleConnection();

            state._fsp--;

             current =iv_ruleConnection; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleConnection1510); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleConnection"


    // $ANTLR start "ruleConnection"
    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:613:1: ruleConnection returns [EObject current=null] : (otherlv_0= 'CREATE' otherlv_1= 'Connection' ( (lv_name_2_0= RULE_NEMOID ) ) otherlv_3= 'Type' this_NemoId_4= RULE_NEMOID (otherlv_5= 'Endnodes' ( (otherlv_6= RULE_NEMOID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_NEMOID ) ) )* ) ( ruleProperty )? otherlv_10= ';' ) ;
    public final EObject ruleConnection() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token lv_name_2_0=null;
        Token otherlv_3=null;
        Token this_NemoId_4=null;
        Token otherlv_5=null;
        Token otherlv_6=null;
        Token otherlv_7=null;
        Token otherlv_8=null;
        Token otherlv_10=null;

         enterRule(); 
            
        try {
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:616:28: ( (otherlv_0= 'CREATE' otherlv_1= 'Connection' ( (lv_name_2_0= RULE_NEMOID ) ) otherlv_3= 'Type' this_NemoId_4= RULE_NEMOID (otherlv_5= 'Endnodes' ( (otherlv_6= RULE_NEMOID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_NEMOID ) ) )* ) ( ruleProperty )? otherlv_10= ';' ) )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:617:1: (otherlv_0= 'CREATE' otherlv_1= 'Connection' ( (lv_name_2_0= RULE_NEMOID ) ) otherlv_3= 'Type' this_NemoId_4= RULE_NEMOID (otherlv_5= 'Endnodes' ( (otherlv_6= RULE_NEMOID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_NEMOID ) ) )* ) ( ruleProperty )? otherlv_10= ';' )
            {
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:617:1: (otherlv_0= 'CREATE' otherlv_1= 'Connection' ( (lv_name_2_0= RULE_NEMOID ) ) otherlv_3= 'Type' this_NemoId_4= RULE_NEMOID (otherlv_5= 'Endnodes' ( (otherlv_6= RULE_NEMOID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_NEMOID ) ) )* ) ( ruleProperty )? otherlv_10= ';' )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:617:3: otherlv_0= 'CREATE' otherlv_1= 'Connection' ( (lv_name_2_0= RULE_NEMOID ) ) otherlv_3= 'Type' this_NemoId_4= RULE_NEMOID (otherlv_5= 'Endnodes' ( (otherlv_6= RULE_NEMOID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_NEMOID ) ) )* ) ( ruleProperty )? otherlv_10= ';'
            {
            otherlv_0=(Token)match(input,17,FOLLOW_17_in_ruleConnection1547); 

                	newLeafNode(otherlv_0, grammarAccess.getConnectionAccess().getCREATEKeyword_0());
                
            otherlv_1=(Token)match(input,23,FOLLOW_23_in_ruleConnection1559); 

                	newLeafNode(otherlv_1, grammarAccess.getConnectionAccess().getConnectionKeyword_1());
                
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:625:1: ( (lv_name_2_0= RULE_NEMOID ) )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:626:1: (lv_name_2_0= RULE_NEMOID )
            {
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:626:1: (lv_name_2_0= RULE_NEMOID )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:627:3: lv_name_2_0= RULE_NEMOID
            {
            lv_name_2_0=(Token)match(input,RULE_NEMOID,FOLLOW_RULE_NEMOID_in_ruleConnection1576); 

            			newLeafNode(lv_name_2_0, grammarAccess.getConnectionAccess().getNameNemoIdTerminalRuleCall_2_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getConnectionRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"name",
                    		lv_name_2_0, 
                    		"NemoId");
            	    

            }


            }

            otherlv_3=(Token)match(input,19,FOLLOW_19_in_ruleConnection1593); 

                	newLeafNode(otherlv_3, grammarAccess.getConnectionAccess().getTypeKeyword_3());
                
            this_NemoId_4=(Token)match(input,RULE_NEMOID,FOLLOW_RULE_NEMOID_in_ruleConnection1604); 
             
                newLeafNode(this_NemoId_4, grammarAccess.getConnectionAccess().getNemoIdTerminalRuleCall_4()); 
                
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:651:1: (otherlv_5= 'Endnodes' ( (otherlv_6= RULE_NEMOID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_NEMOID ) ) )* )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:651:3: otherlv_5= 'Endnodes' ( (otherlv_6= RULE_NEMOID ) ) (otherlv_7= ',' ( (otherlv_8= RULE_NEMOID ) ) )*
            {
            otherlv_5=(Token)match(input,24,FOLLOW_24_in_ruleConnection1616); 

                	newLeafNode(otherlv_5, grammarAccess.getConnectionAccess().getEndnodesKeyword_5_0());
                
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:655:1: ( (otherlv_6= RULE_NEMOID ) )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:656:1: (otherlv_6= RULE_NEMOID )
            {
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:656:1: (otherlv_6= RULE_NEMOID )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:657:3: otherlv_6= RULE_NEMOID
            {

            			if (current==null) {
            	            current = createModelElement(grammarAccess.getConnectionRule());
            	        }
                    
            otherlv_6=(Token)match(input,RULE_NEMOID,FOLLOW_RULE_NEMOID_in_ruleConnection1636); 

            		newLeafNode(otherlv_6, grammarAccess.getConnectionAccess().getEndnodeNodeCrossReference_5_1_0()); 
            	

            }


            }

            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:668:2: (otherlv_7= ',' ( (otherlv_8= RULE_NEMOID ) ) )*
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( (LA12_0==14) ) {
                    alt12=1;
                }


                switch (alt12) {
            	case 1 :
            	    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:668:4: otherlv_7= ',' ( (otherlv_8= RULE_NEMOID ) )
            	    {
            	    otherlv_7=(Token)match(input,14,FOLLOW_14_in_ruleConnection1649); 

            	        	newLeafNode(otherlv_7, grammarAccess.getConnectionAccess().getCommaKeyword_5_2_0());
            	        
            	    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:672:1: ( (otherlv_8= RULE_NEMOID ) )
            	    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:673:1: (otherlv_8= RULE_NEMOID )
            	    {
            	    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:673:1: (otherlv_8= RULE_NEMOID )
            	    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:674:3: otherlv_8= RULE_NEMOID
            	    {

            	    			if (current==null) {
            	    	            current = createModelElement(grammarAccess.getConnectionRule());
            	    	        }
            	            
            	    otherlv_8=(Token)match(input,RULE_NEMOID,FOLLOW_RULE_NEMOID_in_ruleConnection1669); 

            	    		newLeafNode(otherlv_8, grammarAccess.getConnectionAccess().getEndnodeNodeCrossReference_5_2_1_0()); 
            	    	

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop12;
                }
            } while (true);


            }

            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:685:5: ( ruleProperty )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==33) ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:686:5: ruleProperty
                    {
                     
                            newCompositeNode(grammarAccess.getConnectionAccess().getPropertyParserRuleCall_6()); 
                        
                    pushFollow(FOLLOW_ruleProperty_in_ruleConnection1689);
                    ruleProperty();

                    state._fsp--;

                     
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;

            }

            otherlv_10=(Token)match(input,15,FOLLOW_15_in_ruleConnection1702); 

                	newLeafNode(otherlv_10, grammarAccess.getConnectionAccess().getSemicolonKeyword_7());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleConnection"


    // $ANTLR start "entryRuleConnectionUpdate"
    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:705:1: entryRuleConnectionUpdate returns [EObject current=null] : iv_ruleConnectionUpdate= ruleConnectionUpdate EOF ;
    public final EObject entryRuleConnectionUpdate() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleConnectionUpdate = null;


        try {
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:706:2: (iv_ruleConnectionUpdate= ruleConnectionUpdate EOF )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:707:2: iv_ruleConnectionUpdate= ruleConnectionUpdate EOF
            {
             newCompositeNode(grammarAccess.getConnectionUpdateRule()); 
            pushFollow(FOLLOW_ruleConnectionUpdate_in_entryRuleConnectionUpdate1738);
            iv_ruleConnectionUpdate=ruleConnectionUpdate();

            state._fsp--;

             current =iv_ruleConnectionUpdate; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleConnectionUpdate1748); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleConnectionUpdate"


    // $ANTLR start "ruleConnectionUpdate"
    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:714:1: ruleConnectionUpdate returns [EObject current=null] : ( (otherlv_0= 'UPDATE' | otherlv_1= 'DELETE' ) otherlv_2= 'Connection' ( (otherlv_3= RULE_NEMOID ) ) (otherlv_4= 'Type' this_NemoId_5= RULE_NEMOID )? (otherlv_6= 'Endnodes' ( (otherlv_7= RULE_NEMOID ) ) (otherlv_8= ',' ( (otherlv_9= RULE_NEMOID ) ) )* )? ( ruleProperty )? otherlv_11= ';' ) ;
    public final EObject ruleConnectionUpdate() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token this_NemoId_5=null;
        Token otherlv_6=null;
        Token otherlv_7=null;
        Token otherlv_8=null;
        Token otherlv_9=null;
        Token otherlv_11=null;

         enterRule(); 
            
        try {
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:717:28: ( ( (otherlv_0= 'UPDATE' | otherlv_1= 'DELETE' ) otherlv_2= 'Connection' ( (otherlv_3= RULE_NEMOID ) ) (otherlv_4= 'Type' this_NemoId_5= RULE_NEMOID )? (otherlv_6= 'Endnodes' ( (otherlv_7= RULE_NEMOID ) ) (otherlv_8= ',' ( (otherlv_9= RULE_NEMOID ) ) )* )? ( ruleProperty )? otherlv_11= ';' ) )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:718:1: ( (otherlv_0= 'UPDATE' | otherlv_1= 'DELETE' ) otherlv_2= 'Connection' ( (otherlv_3= RULE_NEMOID ) ) (otherlv_4= 'Type' this_NemoId_5= RULE_NEMOID )? (otherlv_6= 'Endnodes' ( (otherlv_7= RULE_NEMOID ) ) (otherlv_8= ',' ( (otherlv_9= RULE_NEMOID ) ) )* )? ( ruleProperty )? otherlv_11= ';' )
            {
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:718:1: ( (otherlv_0= 'UPDATE' | otherlv_1= 'DELETE' ) otherlv_2= 'Connection' ( (otherlv_3= RULE_NEMOID ) ) (otherlv_4= 'Type' this_NemoId_5= RULE_NEMOID )? (otherlv_6= 'Endnodes' ( (otherlv_7= RULE_NEMOID ) ) (otherlv_8= ',' ( (otherlv_9= RULE_NEMOID ) ) )* )? ( ruleProperty )? otherlv_11= ';' )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:718:2: (otherlv_0= 'UPDATE' | otherlv_1= 'DELETE' ) otherlv_2= 'Connection' ( (otherlv_3= RULE_NEMOID ) ) (otherlv_4= 'Type' this_NemoId_5= RULE_NEMOID )? (otherlv_6= 'Endnodes' ( (otherlv_7= RULE_NEMOID ) ) (otherlv_8= ',' ( (otherlv_9= RULE_NEMOID ) ) )* )? ( ruleProperty )? otherlv_11= ';'
            {
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:718:2: (otherlv_0= 'UPDATE' | otherlv_1= 'DELETE' )
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==21) ) {
                alt14=1;
            }
            else if ( (LA14_0==22) ) {
                alt14=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 14, 0, input);

                throw nvae;
            }
            switch (alt14) {
                case 1 :
                    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:718:4: otherlv_0= 'UPDATE'
                    {
                    otherlv_0=(Token)match(input,21,FOLLOW_21_in_ruleConnectionUpdate1786); 

                        	newLeafNode(otherlv_0, grammarAccess.getConnectionUpdateAccess().getUPDATEKeyword_0_0());
                        

                    }
                    break;
                case 2 :
                    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:723:7: otherlv_1= 'DELETE'
                    {
                    otherlv_1=(Token)match(input,22,FOLLOW_22_in_ruleConnectionUpdate1804); 

                        	newLeafNode(otherlv_1, grammarAccess.getConnectionUpdateAccess().getDELETEKeyword_0_1());
                        

                    }
                    break;

            }

            otherlv_2=(Token)match(input,23,FOLLOW_23_in_ruleConnectionUpdate1817); 

                	newLeafNode(otherlv_2, grammarAccess.getConnectionUpdateAccess().getConnectionKeyword_1());
                
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:731:1: ( (otherlv_3= RULE_NEMOID ) )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:732:1: (otherlv_3= RULE_NEMOID )
            {
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:732:1: (otherlv_3= RULE_NEMOID )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:733:3: otherlv_3= RULE_NEMOID
            {

            			if (current==null) {
            	            current = createModelElement(grammarAccess.getConnectionUpdateRule());
            	        }
                    
            otherlv_3=(Token)match(input,RULE_NEMOID,FOLLOW_RULE_NEMOID_in_ruleConnectionUpdate1837); 

            		newLeafNode(otherlv_3, grammarAccess.getConnectionUpdateAccess().getConnectionnameConnectionCrossReference_2_0()); 
            	

            }


            }

            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:744:2: (otherlv_4= 'Type' this_NemoId_5= RULE_NEMOID )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==19) ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:744:4: otherlv_4= 'Type' this_NemoId_5= RULE_NEMOID
                    {
                    otherlv_4=(Token)match(input,19,FOLLOW_19_in_ruleConnectionUpdate1850); 

                        	newLeafNode(otherlv_4, grammarAccess.getConnectionUpdateAccess().getTypeKeyword_3_0());
                        
                    this_NemoId_5=(Token)match(input,RULE_NEMOID,FOLLOW_RULE_NEMOID_in_ruleConnectionUpdate1861); 
                     
                        newLeafNode(this_NemoId_5, grammarAccess.getConnectionUpdateAccess().getNemoIdTerminalRuleCall_3_1()); 
                        

                    }
                    break;

            }

            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:752:3: (otherlv_6= 'Endnodes' ( (otherlv_7= RULE_NEMOID ) ) (otherlv_8= ',' ( (otherlv_9= RULE_NEMOID ) ) )* )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==24) ) {
                alt17=1;
            }
            switch (alt17) {
                case 1 :
                    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:752:5: otherlv_6= 'Endnodes' ( (otherlv_7= RULE_NEMOID ) ) (otherlv_8= ',' ( (otherlv_9= RULE_NEMOID ) ) )*
                    {
                    otherlv_6=(Token)match(input,24,FOLLOW_24_in_ruleConnectionUpdate1875); 

                        	newLeafNode(otherlv_6, grammarAccess.getConnectionUpdateAccess().getEndnodesKeyword_4_0());
                        
                    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:756:1: ( (otherlv_7= RULE_NEMOID ) )
                    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:757:1: (otherlv_7= RULE_NEMOID )
                    {
                    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:757:1: (otherlv_7= RULE_NEMOID )
                    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:758:3: otherlv_7= RULE_NEMOID
                    {

                    			if (current==null) {
                    	            current = createModelElement(grammarAccess.getConnectionUpdateRule());
                    	        }
                            
                    otherlv_7=(Token)match(input,RULE_NEMOID,FOLLOW_RULE_NEMOID_in_ruleConnectionUpdate1895); 

                    		newLeafNode(otherlv_7, grammarAccess.getConnectionUpdateAccess().getEndnodeNodeCrossReference_4_1_0()); 
                    	

                    }


                    }

                    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:769:2: (otherlv_8= ',' ( (otherlv_9= RULE_NEMOID ) ) )*
                    loop16:
                    do {
                        int alt16=2;
                        int LA16_0 = input.LA(1);

                        if ( (LA16_0==14) ) {
                            alt16=1;
                        }


                        switch (alt16) {
                    	case 1 :
                    	    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:769:4: otherlv_8= ',' ( (otherlv_9= RULE_NEMOID ) )
                    	    {
                    	    otherlv_8=(Token)match(input,14,FOLLOW_14_in_ruleConnectionUpdate1908); 

                    	        	newLeafNode(otherlv_8, grammarAccess.getConnectionUpdateAccess().getCommaKeyword_4_2_0());
                    	        
                    	    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:773:1: ( (otherlv_9= RULE_NEMOID ) )
                    	    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:774:1: (otherlv_9= RULE_NEMOID )
                    	    {
                    	    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:774:1: (otherlv_9= RULE_NEMOID )
                    	    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:775:3: otherlv_9= RULE_NEMOID
                    	    {

                    	    			if (current==null) {
                    	    	            current = createModelElement(grammarAccess.getConnectionUpdateRule());
                    	    	        }
                    	            
                    	    otherlv_9=(Token)match(input,RULE_NEMOID,FOLLOW_RULE_NEMOID_in_ruleConnectionUpdate1928); 

                    	    		newLeafNode(otherlv_9, grammarAccess.getConnectionUpdateAccess().getEndnodeNodeCrossReference_4_2_1_0()); 
                    	    	

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop16;
                        }
                    } while (true);


                    }
                    break;

            }

            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:786:6: ( ruleProperty )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==33) ) {
                alt18=1;
            }
            switch (alt18) {
                case 1 :
                    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:787:5: ruleProperty
                    {
                     
                            newCompositeNode(grammarAccess.getConnectionUpdateAccess().getPropertyParserRuleCall_5()); 
                        
                    pushFollow(FOLLOW_ruleProperty_in_ruleConnectionUpdate1949);
                    ruleProperty();

                    state._fsp--;

                     
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;

            }

            otherlv_11=(Token)match(input,15,FOLLOW_15_in_ruleConnectionUpdate1962); 

                	newLeafNode(otherlv_11, grammarAccess.getConnectionUpdateAccess().getSemicolonKeyword_6());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleConnectionUpdate"


    // $ANTLR start "entryRuleFlow"
    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:806:1: entryRuleFlow returns [EObject current=null] : iv_ruleFlow= ruleFlow EOF ;
    public final EObject entryRuleFlow() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFlow = null;


        try {
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:807:2: (iv_ruleFlow= ruleFlow EOF )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:808:2: iv_ruleFlow= ruleFlow EOF
            {
             newCompositeNode(grammarAccess.getFlowRule()); 
            pushFollow(FOLLOW_ruleFlow_in_entryRuleFlow1998);
            iv_ruleFlow=ruleFlow();

            state._fsp--;

             current =iv_ruleFlow; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleFlow2008); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleFlow"


    // $ANTLR start "ruleFlow"
    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:815:1: ruleFlow returns [EObject current=null] : (otherlv_0= 'CREATE' otherlv_1= 'Flow' ( (lv_name_2_0= RULE_NEMOID ) ) ( ruleMatches )? otherlv_4= ';' ) ;
    public final EObject ruleFlow() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token lv_name_2_0=null;
        Token otherlv_4=null;

         enterRule(); 
            
        try {
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:818:28: ( (otherlv_0= 'CREATE' otherlv_1= 'Flow' ( (lv_name_2_0= RULE_NEMOID ) ) ( ruleMatches )? otherlv_4= ';' ) )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:819:1: (otherlv_0= 'CREATE' otherlv_1= 'Flow' ( (lv_name_2_0= RULE_NEMOID ) ) ( ruleMatches )? otherlv_4= ';' )
            {
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:819:1: (otherlv_0= 'CREATE' otherlv_1= 'Flow' ( (lv_name_2_0= RULE_NEMOID ) ) ( ruleMatches )? otherlv_4= ';' )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:819:3: otherlv_0= 'CREATE' otherlv_1= 'Flow' ( (lv_name_2_0= RULE_NEMOID ) ) ( ruleMatches )? otherlv_4= ';'
            {
            otherlv_0=(Token)match(input,17,FOLLOW_17_in_ruleFlow2045); 

                	newLeafNode(otherlv_0, grammarAccess.getFlowAccess().getCREATEKeyword_0());
                
            otherlv_1=(Token)match(input,25,FOLLOW_25_in_ruleFlow2057); 

                	newLeafNode(otherlv_1, grammarAccess.getFlowAccess().getFlowKeyword_1());
                
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:827:1: ( (lv_name_2_0= RULE_NEMOID ) )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:828:1: (lv_name_2_0= RULE_NEMOID )
            {
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:828:1: (lv_name_2_0= RULE_NEMOID )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:829:3: lv_name_2_0= RULE_NEMOID
            {
            lv_name_2_0=(Token)match(input,RULE_NEMOID,FOLLOW_RULE_NEMOID_in_ruleFlow2074); 

            			newLeafNode(lv_name_2_0, grammarAccess.getFlowAccess().getNameNemoIdTerminalRuleCall_2_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getFlowRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"name",
                    		lv_name_2_0, 
                    		"NemoId");
            	    

            }


            }

            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:845:2: ( ruleMatches )?
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==26) ) {
                alt19=1;
            }
            switch (alt19) {
                case 1 :
                    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:846:5: ruleMatches
                    {
                     
                            newCompositeNode(grammarAccess.getFlowAccess().getMatchesParserRuleCall_3()); 
                        
                    pushFollow(FOLLOW_ruleMatches_in_ruleFlow2096);
                    ruleMatches();

                    state._fsp--;

                     
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;

            }

            otherlv_4=(Token)match(input,15,FOLLOW_15_in_ruleFlow2109); 

                	newLeafNode(otherlv_4, grammarAccess.getFlowAccess().getSemicolonKeyword_4());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleFlow"


    // $ANTLR start "entryRuleFlowUpdate"
    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:865:1: entryRuleFlowUpdate returns [EObject current=null] : iv_ruleFlowUpdate= ruleFlowUpdate EOF ;
    public final EObject entryRuleFlowUpdate() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFlowUpdate = null;


        try {
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:866:2: (iv_ruleFlowUpdate= ruleFlowUpdate EOF )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:867:2: iv_ruleFlowUpdate= ruleFlowUpdate EOF
            {
             newCompositeNode(grammarAccess.getFlowUpdateRule()); 
            pushFollow(FOLLOW_ruleFlowUpdate_in_entryRuleFlowUpdate2145);
            iv_ruleFlowUpdate=ruleFlowUpdate();

            state._fsp--;

             current =iv_ruleFlowUpdate; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleFlowUpdate2155); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleFlowUpdate"


    // $ANTLR start "ruleFlowUpdate"
    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:874:1: ruleFlowUpdate returns [EObject current=null] : ( (otherlv_0= 'UPDATE' | otherlv_1= 'DELETE' ) otherlv_2= 'Flow' ( (otherlv_3= RULE_NEMOID ) ) ( ruleMatches )? otherlv_5= ';' ) ;
    public final EObject ruleFlowUpdate() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_5=null;

         enterRule(); 
            
        try {
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:877:28: ( ( (otherlv_0= 'UPDATE' | otherlv_1= 'DELETE' ) otherlv_2= 'Flow' ( (otherlv_3= RULE_NEMOID ) ) ( ruleMatches )? otherlv_5= ';' ) )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:878:1: ( (otherlv_0= 'UPDATE' | otherlv_1= 'DELETE' ) otherlv_2= 'Flow' ( (otherlv_3= RULE_NEMOID ) ) ( ruleMatches )? otherlv_5= ';' )
            {
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:878:1: ( (otherlv_0= 'UPDATE' | otherlv_1= 'DELETE' ) otherlv_2= 'Flow' ( (otherlv_3= RULE_NEMOID ) ) ( ruleMatches )? otherlv_5= ';' )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:878:2: (otherlv_0= 'UPDATE' | otherlv_1= 'DELETE' ) otherlv_2= 'Flow' ( (otherlv_3= RULE_NEMOID ) ) ( ruleMatches )? otherlv_5= ';'
            {
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:878:2: (otherlv_0= 'UPDATE' | otherlv_1= 'DELETE' )
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==21) ) {
                alt20=1;
            }
            else if ( (LA20_0==22) ) {
                alt20=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 20, 0, input);

                throw nvae;
            }
            switch (alt20) {
                case 1 :
                    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:878:4: otherlv_0= 'UPDATE'
                    {
                    otherlv_0=(Token)match(input,21,FOLLOW_21_in_ruleFlowUpdate2193); 

                        	newLeafNode(otherlv_0, grammarAccess.getFlowUpdateAccess().getUPDATEKeyword_0_0());
                        

                    }
                    break;
                case 2 :
                    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:883:7: otherlv_1= 'DELETE'
                    {
                    otherlv_1=(Token)match(input,22,FOLLOW_22_in_ruleFlowUpdate2211); 

                        	newLeafNode(otherlv_1, grammarAccess.getFlowUpdateAccess().getDELETEKeyword_0_1());
                        

                    }
                    break;

            }

            otherlv_2=(Token)match(input,25,FOLLOW_25_in_ruleFlowUpdate2224); 

                	newLeafNode(otherlv_2, grammarAccess.getFlowUpdateAccess().getFlowKeyword_1());
                
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:891:1: ( (otherlv_3= RULE_NEMOID ) )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:892:1: (otherlv_3= RULE_NEMOID )
            {
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:892:1: (otherlv_3= RULE_NEMOID )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:893:3: otherlv_3= RULE_NEMOID
            {

            			if (current==null) {
            	            current = createModelElement(grammarAccess.getFlowUpdateRule());
            	        }
                    
            otherlv_3=(Token)match(input,RULE_NEMOID,FOLLOW_RULE_NEMOID_in_ruleFlowUpdate2244); 

            		newLeafNode(otherlv_3, grammarAccess.getFlowUpdateAccess().getFlowIdFlowCrossReference_2_0()); 
            	

            }


            }

            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:904:2: ( ruleMatches )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==26) ) {
                alt21=1;
            }
            switch (alt21) {
                case 1 :
                    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:905:5: ruleMatches
                    {
                     
                            newCompositeNode(grammarAccess.getFlowUpdateAccess().getMatchesParserRuleCall_3()); 
                        
                    pushFollow(FOLLOW_ruleMatches_in_ruleFlowUpdate2261);
                    ruleMatches();

                    state._fsp--;

                     
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;

            }

            otherlv_5=(Token)match(input,15,FOLLOW_15_in_ruleFlowUpdate2274); 

                	newLeafNode(otherlv_5, grammarAccess.getFlowUpdateAccess().getSemicolonKeyword_4());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleFlowUpdate"


    // $ANTLR start "entryRuleMatches"
    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:924:1: entryRuleMatches returns [String current=null] : iv_ruleMatches= ruleMatches EOF ;
    public final String entryRuleMatches() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleMatches = null;


        try {
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:925:2: (iv_ruleMatches= ruleMatches EOF )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:926:2: iv_ruleMatches= ruleMatches EOF
            {
             newCompositeNode(grammarAccess.getMatchesRule()); 
            pushFollow(FOLLOW_ruleMatches_in_entryRuleMatches2311);
            iv_ruleMatches=ruleMatches();

            state._fsp--;

             current =iv_ruleMatches.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMatches2322); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleMatches"


    // $ANTLR start "ruleMatches"
    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:933:1: ruleMatches returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= 'Match' this_Match_1= ruleMatch (kw= ',' this_Match_3= ruleMatch )* ) ;
    public final AntlrDatatypeRuleToken ruleMatches() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_Match_1 = null;

        AntlrDatatypeRuleToken this_Match_3 = null;


         enterRule(); 
            
        try {
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:936:28: ( (kw= 'Match' this_Match_1= ruleMatch (kw= ',' this_Match_3= ruleMatch )* ) )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:937:1: (kw= 'Match' this_Match_1= ruleMatch (kw= ',' this_Match_3= ruleMatch )* )
            {
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:937:1: (kw= 'Match' this_Match_1= ruleMatch (kw= ',' this_Match_3= ruleMatch )* )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:938:2: kw= 'Match' this_Match_1= ruleMatch (kw= ',' this_Match_3= ruleMatch )*
            {
            kw=(Token)match(input,26,FOLLOW_26_in_ruleMatches2360); 

                    current.merge(kw);
                    newLeafNode(kw, grammarAccess.getMatchesAccess().getMatchKeyword_0()); 
                
             
                    newCompositeNode(grammarAccess.getMatchesAccess().getMatchParserRuleCall_1()); 
                
            pushFollow(FOLLOW_ruleMatch_in_ruleMatches2382);
            this_Match_1=ruleMatch();

            state._fsp--;


            		current.merge(this_Match_1);
                
             
                    afterParserOrEnumRuleCall();
                
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:954:1: (kw= ',' this_Match_3= ruleMatch )*
            loop22:
            do {
                int alt22=2;
                int LA22_0 = input.LA(1);

                if ( (LA22_0==14) ) {
                    alt22=1;
                }


                switch (alt22) {
            	case 1 :
            	    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:955:2: kw= ',' this_Match_3= ruleMatch
            	    {
            	    kw=(Token)match(input,14,FOLLOW_14_in_ruleMatches2401); 

            	            current.merge(kw);
            	            newLeafNode(kw, grammarAccess.getMatchesAccess().getCommaKeyword_2_0()); 
            	        
            	     
            	            newCompositeNode(grammarAccess.getMatchesAccess().getMatchParserRuleCall_2_1()); 
            	        
            	    pushFollow(FOLLOW_ruleMatch_in_ruleMatches2423);
            	    this_Match_3=ruleMatch();

            	    state._fsp--;


            	    		current.merge(this_Match_3);
            	        
            	     
            	            afterParserOrEnumRuleCall();
            	        

            	    }
            	    break;

            	default :
            	    break loop22;
                }
            } while (true);


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleMatches"


    // $ANTLR start "entryRuleMatch"
    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:979:1: entryRuleMatch returns [String current=null] : iv_ruleMatch= ruleMatch EOF ;
    public final String entryRuleMatch() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleMatch = null;


        try {
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:980:2: (iv_ruleMatch= ruleMatch EOF )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:981:2: iv_ruleMatch= ruleMatch EOF
            {
             newCompositeNode(grammarAccess.getMatchRule()); 
            pushFollow(FOLLOW_ruleMatch_in_entryRuleMatch2471);
            iv_ruleMatch=ruleMatch();

            state._fsp--;

             current =iv_ruleMatch.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMatch2482); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleMatch"


    // $ANTLR start "ruleMatch"
    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:988:1: ruleMatch returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_NemoId_0= RULE_NEMOID kw= ':' this_STRING_2= RULE_STRING ) ;
    public final AntlrDatatypeRuleToken ruleMatch() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_NemoId_0=null;
        Token kw=null;
        Token this_STRING_2=null;

         enterRule(); 
            
        try {
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:991:28: ( (this_NemoId_0= RULE_NEMOID kw= ':' this_STRING_2= RULE_STRING ) )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:992:1: (this_NemoId_0= RULE_NEMOID kw= ':' this_STRING_2= RULE_STRING )
            {
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:992:1: (this_NemoId_0= RULE_NEMOID kw= ':' this_STRING_2= RULE_STRING )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:992:6: this_NemoId_0= RULE_NEMOID kw= ':' this_STRING_2= RULE_STRING
            {
            this_NemoId_0=(Token)match(input,RULE_NEMOID,FOLLOW_RULE_NEMOID_in_ruleMatch2522); 

            		current.merge(this_NemoId_0);
                
             
                newLeafNode(this_NemoId_0, grammarAccess.getMatchAccess().getNemoIdTerminalRuleCall_0()); 
                
            kw=(Token)match(input,27,FOLLOW_27_in_ruleMatch2540); 

                    current.merge(kw);
                    newLeafNode(kw, grammarAccess.getMatchAccess().getColonKeyword_1()); 
                
            this_STRING_2=(Token)match(input,RULE_STRING,FOLLOW_RULE_STRING_in_ruleMatch2555); 

            		current.merge(this_STRING_2);
                
             
                newLeafNode(this_STRING_2, grammarAccess.getMatchAccess().getSTRINGTerminalRuleCall_2()); 
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleMatch"


    // $ANTLR start "entryRuleOperation"
    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1020:1: entryRuleOperation returns [EObject current=null] : iv_ruleOperation= ruleOperation EOF ;
    public final EObject entryRuleOperation() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleOperation = null;


        try {
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1021:2: (iv_ruleOperation= ruleOperation EOF )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1022:2: iv_ruleOperation= ruleOperation EOF
            {
             newCompositeNode(grammarAccess.getOperationRule()); 
            pushFollow(FOLLOW_ruleOperation_in_entryRuleOperation2600);
            iv_ruleOperation=ruleOperation();

            state._fsp--;

             current =iv_ruleOperation; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleOperation2610); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleOperation"


    // $ANTLR start "ruleOperation"
    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1029:1: ruleOperation returns [EObject current=null] : (otherlv_0= 'CREATE' otherlv_1= 'Operation' ( (lv_name_2_0= RULE_NEMOID ) ) (otherlv_3= 'Priority' ( (lv_value_4_0= RULE_INT ) ) )? otherlv_5= 'Target' ( (otherlv_6= RULE_NEMOID ) ) ( ruleCondition )? otherlv_8= 'Action' (this_NemoId_9= RULE_NEMOID | (this_NemoId_10= RULE_NEMOID otherlv_11= ':' ( (otherlv_12= RULE_NEMOID ) ) ) ) otherlv_13= ';' ) ;
    public final EObject ruleOperation() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token lv_name_2_0=null;
        Token otherlv_3=null;
        Token lv_value_4_0=null;
        Token otherlv_5=null;
        Token otherlv_6=null;
        Token otherlv_8=null;
        Token this_NemoId_9=null;
        Token this_NemoId_10=null;
        Token otherlv_11=null;
        Token otherlv_12=null;
        Token otherlv_13=null;

         enterRule(); 
            
        try {
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1032:28: ( (otherlv_0= 'CREATE' otherlv_1= 'Operation' ( (lv_name_2_0= RULE_NEMOID ) ) (otherlv_3= 'Priority' ( (lv_value_4_0= RULE_INT ) ) )? otherlv_5= 'Target' ( (otherlv_6= RULE_NEMOID ) ) ( ruleCondition )? otherlv_8= 'Action' (this_NemoId_9= RULE_NEMOID | (this_NemoId_10= RULE_NEMOID otherlv_11= ':' ( (otherlv_12= RULE_NEMOID ) ) ) ) otherlv_13= ';' ) )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1033:1: (otherlv_0= 'CREATE' otherlv_1= 'Operation' ( (lv_name_2_0= RULE_NEMOID ) ) (otherlv_3= 'Priority' ( (lv_value_4_0= RULE_INT ) ) )? otherlv_5= 'Target' ( (otherlv_6= RULE_NEMOID ) ) ( ruleCondition )? otherlv_8= 'Action' (this_NemoId_9= RULE_NEMOID | (this_NemoId_10= RULE_NEMOID otherlv_11= ':' ( (otherlv_12= RULE_NEMOID ) ) ) ) otherlv_13= ';' )
            {
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1033:1: (otherlv_0= 'CREATE' otherlv_1= 'Operation' ( (lv_name_2_0= RULE_NEMOID ) ) (otherlv_3= 'Priority' ( (lv_value_4_0= RULE_INT ) ) )? otherlv_5= 'Target' ( (otherlv_6= RULE_NEMOID ) ) ( ruleCondition )? otherlv_8= 'Action' (this_NemoId_9= RULE_NEMOID | (this_NemoId_10= RULE_NEMOID otherlv_11= ':' ( (otherlv_12= RULE_NEMOID ) ) ) ) otherlv_13= ';' )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1033:3: otherlv_0= 'CREATE' otherlv_1= 'Operation' ( (lv_name_2_0= RULE_NEMOID ) ) (otherlv_3= 'Priority' ( (lv_value_4_0= RULE_INT ) ) )? otherlv_5= 'Target' ( (otherlv_6= RULE_NEMOID ) ) ( ruleCondition )? otherlv_8= 'Action' (this_NemoId_9= RULE_NEMOID | (this_NemoId_10= RULE_NEMOID otherlv_11= ':' ( (otherlv_12= RULE_NEMOID ) ) ) ) otherlv_13= ';'
            {
            otherlv_0=(Token)match(input,17,FOLLOW_17_in_ruleOperation2647); 

                	newLeafNode(otherlv_0, grammarAccess.getOperationAccess().getCREATEKeyword_0());
                
            otherlv_1=(Token)match(input,28,FOLLOW_28_in_ruleOperation2659); 

                	newLeafNode(otherlv_1, grammarAccess.getOperationAccess().getOperationKeyword_1());
                
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1041:1: ( (lv_name_2_0= RULE_NEMOID ) )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1042:1: (lv_name_2_0= RULE_NEMOID )
            {
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1042:1: (lv_name_2_0= RULE_NEMOID )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1043:3: lv_name_2_0= RULE_NEMOID
            {
            lv_name_2_0=(Token)match(input,RULE_NEMOID,FOLLOW_RULE_NEMOID_in_ruleOperation2676); 

            			newLeafNode(lv_name_2_0, grammarAccess.getOperationAccess().getNameNemoIdTerminalRuleCall_2_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getOperationRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"name",
                    		lv_name_2_0, 
                    		"NemoId");
            	    

            }


            }

            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1059:2: (otherlv_3= 'Priority' ( (lv_value_4_0= RULE_INT ) ) )?
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==29) ) {
                alt23=1;
            }
            switch (alt23) {
                case 1 :
                    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1059:4: otherlv_3= 'Priority' ( (lv_value_4_0= RULE_INT ) )
                    {
                    otherlv_3=(Token)match(input,29,FOLLOW_29_in_ruleOperation2694); 

                        	newLeafNode(otherlv_3, grammarAccess.getOperationAccess().getPriorityKeyword_3_0());
                        
                    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1063:1: ( (lv_value_4_0= RULE_INT ) )
                    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1064:1: (lv_value_4_0= RULE_INT )
                    {
                    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1064:1: (lv_value_4_0= RULE_INT )
                    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1065:3: lv_value_4_0= RULE_INT
                    {
                    lv_value_4_0=(Token)match(input,RULE_INT,FOLLOW_RULE_INT_in_ruleOperation2711); 

                    			newLeafNode(lv_value_4_0, grammarAccess.getOperationAccess().getValueINTTerminalRuleCall_3_1_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getOperationRule());
                    	        }
                           		addWithLastConsumed(
                           			current, 
                           			"value",
                            		lv_value_4_0, 
                            		"INT");
                    	    

                    }


                    }


                    }
                    break;

            }

            otherlv_5=(Token)match(input,30,FOLLOW_30_in_ruleOperation2730); 

                	newLeafNode(otherlv_5, grammarAccess.getOperationAccess().getTargetKeyword_4());
                
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1085:1: ( (otherlv_6= RULE_NEMOID ) )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1086:1: (otherlv_6= RULE_NEMOID )
            {
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1086:1: (otherlv_6= RULE_NEMOID )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1087:3: otherlv_6= RULE_NEMOID
            {

            			if (current==null) {
            	            current = createModelElement(grammarAccess.getOperationRule());
            	        }
                    
            otherlv_6=(Token)match(input,RULE_NEMOID,FOLLOW_RULE_NEMOID_in_ruleOperation2750); 

            		newLeafNode(otherlv_6, grammarAccess.getOperationAccess().getTargetIdFlowCrossReference_5_0()); 
            	

            }


            }

            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1098:2: ( ruleCondition )?
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==32) ) {
                alt24=1;
            }
            switch (alt24) {
                case 1 :
                    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1099:5: ruleCondition
                    {
                     
                            newCompositeNode(grammarAccess.getOperationAccess().getConditionParserRuleCall_6()); 
                        
                    pushFollow(FOLLOW_ruleCondition_in_ruleOperation2767);
                    ruleCondition();

                    state._fsp--;

                     
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;

            }

            otherlv_8=(Token)match(input,31,FOLLOW_31_in_ruleOperation2780); 

                	newLeafNode(otherlv_8, grammarAccess.getOperationAccess().getActionKeyword_7());
                
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1110:1: (this_NemoId_9= RULE_NEMOID | (this_NemoId_10= RULE_NEMOID otherlv_11= ':' ( (otherlv_12= RULE_NEMOID ) ) ) )
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( (LA25_0==RULE_NEMOID) ) {
                int LA25_1 = input.LA(2);

                if ( (LA25_1==27) ) {
                    alt25=2;
                }
                else if ( (LA25_1==15) ) {
                    alt25=1;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 25, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 25, 0, input);

                throw nvae;
            }
            switch (alt25) {
                case 1 :
                    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1110:2: this_NemoId_9= RULE_NEMOID
                    {
                    this_NemoId_9=(Token)match(input,RULE_NEMOID,FOLLOW_RULE_NEMOID_in_ruleOperation2792); 
                     
                        newLeafNode(this_NemoId_9, grammarAccess.getOperationAccess().getNemoIdTerminalRuleCall_8_0()); 
                        

                    }
                    break;
                case 2 :
                    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1115:6: (this_NemoId_10= RULE_NEMOID otherlv_11= ':' ( (otherlv_12= RULE_NEMOID ) ) )
                    {
                    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1115:6: (this_NemoId_10= RULE_NEMOID otherlv_11= ':' ( (otherlv_12= RULE_NEMOID ) ) )
                    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1115:7: this_NemoId_10= RULE_NEMOID otherlv_11= ':' ( (otherlv_12= RULE_NEMOID ) )
                    {
                    this_NemoId_10=(Token)match(input,RULE_NEMOID,FOLLOW_RULE_NEMOID_in_ruleOperation2809); 
                     
                        newLeafNode(this_NemoId_10, grammarAccess.getOperationAccess().getNemoIdTerminalRuleCall_8_1_0()); 
                        
                    otherlv_11=(Token)match(input,27,FOLLOW_27_in_ruleOperation2820); 

                        	newLeafNode(otherlv_11, grammarAccess.getOperationAccess().getColonKeyword_8_1_1());
                        
                    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1123:1: ( (otherlv_12= RULE_NEMOID ) )
                    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1124:1: (otherlv_12= RULE_NEMOID )
                    {
                    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1124:1: (otherlv_12= RULE_NEMOID )
                    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1125:3: otherlv_12= RULE_NEMOID
                    {

                    			if (current==null) {
                    	            current = createModelElement(grammarAccess.getOperationRule());
                    	        }
                            
                    otherlv_12=(Token)match(input,RULE_NEMOID,FOLLOW_RULE_NEMOID_in_ruleOperation2840); 

                    		newLeafNode(otherlv_12, grammarAccess.getOperationAccess().getTargetNodeNodeCrossReference_8_1_2_0()); 
                    	

                    }


                    }


                    }


                    }
                    break;

            }

            otherlv_13=(Token)match(input,15,FOLLOW_15_in_ruleOperation2854); 

                	newLeafNode(otherlv_13, grammarAccess.getOperationAccess().getSemicolonKeyword_9());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleOperation"


    // $ANTLR start "entryRuleCondition"
    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1148:1: entryRuleCondition returns [String current=null] : iv_ruleCondition= ruleCondition EOF ;
    public final String entryRuleCondition() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleCondition = null;


        try {
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1149:2: (iv_ruleCondition= ruleCondition EOF )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1150:2: iv_ruleCondition= ruleCondition EOF
            {
             newCompositeNode(grammarAccess.getConditionRule()); 
            pushFollow(FOLLOW_ruleCondition_in_entryRuleCondition2891);
            iv_ruleCondition=ruleCondition();

            state._fsp--;

             current =iv_ruleCondition.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleCondition2902); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleCondition"


    // $ANTLR start "ruleCondition"
    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1157:1: ruleCondition returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= 'Condition' this_STRING_1= RULE_STRING (kw= ',' this_STRING_3= RULE_STRING )* ) ;
    public final AntlrDatatypeRuleToken ruleCondition() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_STRING_1=null;
        Token this_STRING_3=null;

         enterRule(); 
            
        try {
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1160:28: ( (kw= 'Condition' this_STRING_1= RULE_STRING (kw= ',' this_STRING_3= RULE_STRING )* ) )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1161:1: (kw= 'Condition' this_STRING_1= RULE_STRING (kw= ',' this_STRING_3= RULE_STRING )* )
            {
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1161:1: (kw= 'Condition' this_STRING_1= RULE_STRING (kw= ',' this_STRING_3= RULE_STRING )* )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1162:2: kw= 'Condition' this_STRING_1= RULE_STRING (kw= ',' this_STRING_3= RULE_STRING )*
            {
            kw=(Token)match(input,32,FOLLOW_32_in_ruleCondition2940); 

                    current.merge(kw);
                    newLeafNode(kw, grammarAccess.getConditionAccess().getConditionKeyword_0()); 
                
            this_STRING_1=(Token)match(input,RULE_STRING,FOLLOW_RULE_STRING_in_ruleCondition2955); 

            		current.merge(this_STRING_1);
                
             
                newLeafNode(this_STRING_1, grammarAccess.getConditionAccess().getSTRINGTerminalRuleCall_1()); 
                
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1174:1: (kw= ',' this_STRING_3= RULE_STRING )*
            loop26:
            do {
                int alt26=2;
                int LA26_0 = input.LA(1);

                if ( (LA26_0==14) ) {
                    alt26=1;
                }


                switch (alt26) {
            	case 1 :
            	    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1175:2: kw= ',' this_STRING_3= RULE_STRING
            	    {
            	    kw=(Token)match(input,14,FOLLOW_14_in_ruleCondition2974); 

            	            current.merge(kw);
            	            newLeafNode(kw, grammarAccess.getConditionAccess().getCommaKeyword_2_0()); 
            	        
            	    this_STRING_3=(Token)match(input,RULE_STRING,FOLLOW_RULE_STRING_in_ruleCondition2989); 

            	    		current.merge(this_STRING_3);
            	        
            	     
            	        newLeafNode(this_STRING_3, grammarAccess.getConditionAccess().getSTRINGTerminalRuleCall_2_1()); 
            	        

            	    }
            	    break;

            	default :
            	    break loop26;
                }
            } while (true);


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleCondition"


    // $ANTLR start "entryRuleProperty"
    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1195:1: entryRuleProperty returns [String current=null] : iv_ruleProperty= ruleProperty EOF ;
    public final String entryRuleProperty() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleProperty = null;


        try {
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1196:2: (iv_ruleProperty= ruleProperty EOF )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1197:2: iv_ruleProperty= ruleProperty EOF
            {
             newCompositeNode(grammarAccess.getPropertyRule()); 
            pushFollow(FOLLOW_ruleProperty_in_entryRuleProperty3037);
            iv_ruleProperty=ruleProperty();

            state._fsp--;

             current =iv_ruleProperty.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleProperty3048); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleProperty"


    // $ANTLR start "ruleProperty"
    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1204:1: ruleProperty returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= 'Property ' this_OneProperty_1= ruleOneProperty (kw= ',' this_OneProperty_3= ruleOneProperty )* ) ;
    public final AntlrDatatypeRuleToken ruleProperty() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_OneProperty_1 = null;

        AntlrDatatypeRuleToken this_OneProperty_3 = null;


         enterRule(); 
            
        try {
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1207:28: ( (kw= 'Property ' this_OneProperty_1= ruleOneProperty (kw= ',' this_OneProperty_3= ruleOneProperty )* ) )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1208:1: (kw= 'Property ' this_OneProperty_1= ruleOneProperty (kw= ',' this_OneProperty_3= ruleOneProperty )* )
            {
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1208:1: (kw= 'Property ' this_OneProperty_1= ruleOneProperty (kw= ',' this_OneProperty_3= ruleOneProperty )* )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1209:2: kw= 'Property ' this_OneProperty_1= ruleOneProperty (kw= ',' this_OneProperty_3= ruleOneProperty )*
            {
            kw=(Token)match(input,33,FOLLOW_33_in_ruleProperty3086); 

                    current.merge(kw);
                    newLeafNode(kw, grammarAccess.getPropertyAccess().getPropertyKeyword_0()); 
                
             
                    newCompositeNode(grammarAccess.getPropertyAccess().getOnePropertyParserRuleCall_1()); 
                
            pushFollow(FOLLOW_ruleOneProperty_in_ruleProperty3108);
            this_OneProperty_1=ruleOneProperty();

            state._fsp--;


            		current.merge(this_OneProperty_1);
                
             
                    afterParserOrEnumRuleCall();
                
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1225:1: (kw= ',' this_OneProperty_3= ruleOneProperty )*
            loop27:
            do {
                int alt27=2;
                int LA27_0 = input.LA(1);

                if ( (LA27_0==14) ) {
                    alt27=1;
                }


                switch (alt27) {
            	case 1 :
            	    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1226:2: kw= ',' this_OneProperty_3= ruleOneProperty
            	    {
            	    kw=(Token)match(input,14,FOLLOW_14_in_ruleProperty3127); 

            	            current.merge(kw);
            	            newLeafNode(kw, grammarAccess.getPropertyAccess().getCommaKeyword_2_0()); 
            	        
            	     
            	            newCompositeNode(grammarAccess.getPropertyAccess().getOnePropertyParserRuleCall_2_1()); 
            	        
            	    pushFollow(FOLLOW_ruleOneProperty_in_ruleProperty3149);
            	    this_OneProperty_3=ruleOneProperty();

            	    state._fsp--;


            	    		current.merge(this_OneProperty_3);
            	        
            	     
            	            afterParserOrEnumRuleCall();
            	        

            	    }
            	    break;

            	default :
            	    break loop27;
                }
            } while (true);


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleProperty"


    // $ANTLR start "entryRuleOneProperty"
    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1250:1: entryRuleOneProperty returns [String current=null] : iv_ruleOneProperty= ruleOneProperty EOF ;
    public final String entryRuleOneProperty() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleOneProperty = null;


        try {
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1251:2: (iv_ruleOneProperty= ruleOneProperty EOF )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1252:2: iv_ruleOneProperty= ruleOneProperty EOF
            {
             newCompositeNode(grammarAccess.getOnePropertyRule()); 
            pushFollow(FOLLOW_ruleOneProperty_in_entryRuleOneProperty3197);
            iv_ruleOneProperty=ruleOneProperty();

            state._fsp--;

             current =iv_ruleOneProperty.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleOneProperty3208); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleOneProperty"


    // $ANTLR start "ruleOneProperty"
    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1259:1: ruleOneProperty returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_NemoId_0= RULE_NEMOID kw= ':' (this_STRING_2= RULE_STRING | this_INT_3= RULE_INT ) ) ;
    public final AntlrDatatypeRuleToken ruleOneProperty() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_NemoId_0=null;
        Token kw=null;
        Token this_STRING_2=null;
        Token this_INT_3=null;

         enterRule(); 
            
        try {
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1262:28: ( (this_NemoId_0= RULE_NEMOID kw= ':' (this_STRING_2= RULE_STRING | this_INT_3= RULE_INT ) ) )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1263:1: (this_NemoId_0= RULE_NEMOID kw= ':' (this_STRING_2= RULE_STRING | this_INT_3= RULE_INT ) )
            {
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1263:1: (this_NemoId_0= RULE_NEMOID kw= ':' (this_STRING_2= RULE_STRING | this_INT_3= RULE_INT ) )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1263:6: this_NemoId_0= RULE_NEMOID kw= ':' (this_STRING_2= RULE_STRING | this_INT_3= RULE_INT )
            {
            this_NemoId_0=(Token)match(input,RULE_NEMOID,FOLLOW_RULE_NEMOID_in_ruleOneProperty3248); 

            		current.merge(this_NemoId_0);
                
             
                newLeafNode(this_NemoId_0, grammarAccess.getOnePropertyAccess().getNemoIdTerminalRuleCall_0()); 
                
            kw=(Token)match(input,27,FOLLOW_27_in_ruleOneProperty3266); 

                    current.merge(kw);
                    newLeafNode(kw, grammarAccess.getOnePropertyAccess().getColonKeyword_1()); 
                
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1276:1: (this_STRING_2= RULE_STRING | this_INT_3= RULE_INT )
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( (LA28_0==RULE_STRING) ) {
                alt28=1;
            }
            else if ( (LA28_0==RULE_INT) ) {
                alt28=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 28, 0, input);

                throw nvae;
            }
            switch (alt28) {
                case 1 :
                    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1276:6: this_STRING_2= RULE_STRING
                    {
                    this_STRING_2=(Token)match(input,RULE_STRING,FOLLOW_RULE_STRING_in_ruleOneProperty3282); 

                    		current.merge(this_STRING_2);
                        
                     
                        newLeafNode(this_STRING_2, grammarAccess.getOnePropertyAccess().getSTRINGTerminalRuleCall_2_0()); 
                        

                    }
                    break;
                case 2 :
                    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1284:10: this_INT_3= RULE_INT
                    {
                    this_INT_3=(Token)match(input,RULE_INT,FOLLOW_RULE_INT_in_ruleOneProperty3308); 

                    		current.merge(this_INT_3);
                        
                     
                        newLeafNode(this_INT_3, grammarAccess.getOnePropertyAccess().getINTTerminalRuleCall_2_1()); 
                        

                    }
                    break;

            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleOneProperty"


    // $ANTLR start "entryRuleModelProperty"
    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1299:1: entryRuleModelProperty returns [String current=null] : iv_ruleModelProperty= ruleModelProperty EOF ;
    public final String entryRuleModelProperty() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleModelProperty = null;


        try {
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1300:2: (iv_ruleModelProperty= ruleModelProperty EOF )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1301:2: iv_ruleModelProperty= ruleModelProperty EOF
            {
             newCompositeNode(grammarAccess.getModelPropertyRule()); 
            pushFollow(FOLLOW_ruleModelProperty_in_entryRuleModelProperty3355);
            iv_ruleModelProperty=ruleModelProperty();

            state._fsp--;

             current =iv_ruleModelProperty.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleModelProperty3366); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleModelProperty"


    // $ANTLR start "ruleModelProperty"
    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1308:1: ruleModelProperty returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= 'Property' this_OneModelProperty_1= ruleOneModelProperty (kw= ',' this_OneModelProperty_3= ruleOneModelProperty )* ) ;
    public final AntlrDatatypeRuleToken ruleModelProperty() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_OneModelProperty_1 = null;

        AntlrDatatypeRuleToken this_OneModelProperty_3 = null;


         enterRule(); 
            
        try {
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1311:28: ( (kw= 'Property' this_OneModelProperty_1= ruleOneModelProperty (kw= ',' this_OneModelProperty_3= ruleOneModelProperty )* ) )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1312:1: (kw= 'Property' this_OneModelProperty_1= ruleOneModelProperty (kw= ',' this_OneModelProperty_3= ruleOneModelProperty )* )
            {
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1312:1: (kw= 'Property' this_OneModelProperty_1= ruleOneModelProperty (kw= ',' this_OneModelProperty_3= ruleOneModelProperty )* )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1313:2: kw= 'Property' this_OneModelProperty_1= ruleOneModelProperty (kw= ',' this_OneModelProperty_3= ruleOneModelProperty )*
            {
            kw=(Token)match(input,34,FOLLOW_34_in_ruleModelProperty3404); 

                    current.merge(kw);
                    newLeafNode(kw, grammarAccess.getModelPropertyAccess().getPropertyKeyword_0()); 
                
             
                    newCompositeNode(grammarAccess.getModelPropertyAccess().getOneModelPropertyParserRuleCall_1()); 
                
            pushFollow(FOLLOW_ruleOneModelProperty_in_ruleModelProperty3426);
            this_OneModelProperty_1=ruleOneModelProperty();

            state._fsp--;


            		current.merge(this_OneModelProperty_1);
                
             
                    afterParserOrEnumRuleCall();
                
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1329:1: (kw= ',' this_OneModelProperty_3= ruleOneModelProperty )*
            loop29:
            do {
                int alt29=2;
                int LA29_0 = input.LA(1);

                if ( (LA29_0==14) ) {
                    alt29=1;
                }


                switch (alt29) {
            	case 1 :
            	    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1330:2: kw= ',' this_OneModelProperty_3= ruleOneModelProperty
            	    {
            	    kw=(Token)match(input,14,FOLLOW_14_in_ruleModelProperty3445); 

            	            current.merge(kw);
            	            newLeafNode(kw, grammarAccess.getModelPropertyAccess().getCommaKeyword_2_0()); 
            	        
            	     
            	            newCompositeNode(grammarAccess.getModelPropertyAccess().getOneModelPropertyParserRuleCall_2_1()); 
            	        
            	    pushFollow(FOLLOW_ruleOneModelProperty_in_ruleModelProperty3467);
            	    this_OneModelProperty_3=ruleOneModelProperty();

            	    state._fsp--;


            	    		current.merge(this_OneModelProperty_3);
            	        
            	     
            	            afterParserOrEnumRuleCall();
            	        

            	    }
            	    break;

            	default :
            	    break loop29;
                }
            } while (true);


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleModelProperty"


    // $ANTLR start "entryRuleOneModelProperty"
    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1354:1: entryRuleOneModelProperty returns [String current=null] : iv_ruleOneModelProperty= ruleOneModelProperty EOF ;
    public final String entryRuleOneModelProperty() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleOneModelProperty = null;


        try {
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1355:2: (iv_ruleOneModelProperty= ruleOneModelProperty EOF )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1356:2: iv_ruleOneModelProperty= ruleOneModelProperty EOF
            {
             newCompositeNode(grammarAccess.getOneModelPropertyRule()); 
            pushFollow(FOLLOW_ruleOneModelProperty_in_entryRuleOneModelProperty3515);
            iv_ruleOneModelProperty=ruleOneModelProperty();

            state._fsp--;

             current =iv_ruleOneModelProperty.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleOneModelProperty3526); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleOneModelProperty"


    // $ANTLR start "ruleOneModelProperty"
    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1363:1: ruleOneModelProperty returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (this_STRING_0= RULE_STRING | this_INT_1= RULE_INT ) kw= ':' this_NemoId_3= RULE_NEMOID ) ;
    public final AntlrDatatypeRuleToken ruleOneModelProperty() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_STRING_0=null;
        Token this_INT_1=null;
        Token kw=null;
        Token this_NemoId_3=null;

         enterRule(); 
            
        try {
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1366:28: ( ( (this_STRING_0= RULE_STRING | this_INT_1= RULE_INT ) kw= ':' this_NemoId_3= RULE_NEMOID ) )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1367:1: ( (this_STRING_0= RULE_STRING | this_INT_1= RULE_INT ) kw= ':' this_NemoId_3= RULE_NEMOID )
            {
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1367:1: ( (this_STRING_0= RULE_STRING | this_INT_1= RULE_INT ) kw= ':' this_NemoId_3= RULE_NEMOID )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1367:2: (this_STRING_0= RULE_STRING | this_INT_1= RULE_INT ) kw= ':' this_NemoId_3= RULE_NEMOID
            {
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1367:2: (this_STRING_0= RULE_STRING | this_INT_1= RULE_INT )
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( (LA30_0==RULE_STRING) ) {
                alt30=1;
            }
            else if ( (LA30_0==RULE_INT) ) {
                alt30=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 30, 0, input);

                throw nvae;
            }
            switch (alt30) {
                case 1 :
                    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1367:7: this_STRING_0= RULE_STRING
                    {
                    this_STRING_0=(Token)match(input,RULE_STRING,FOLLOW_RULE_STRING_in_ruleOneModelProperty3567); 

                    		current.merge(this_STRING_0);
                        
                     
                        newLeafNode(this_STRING_0, grammarAccess.getOneModelPropertyAccess().getSTRINGTerminalRuleCall_0_0()); 
                        

                    }
                    break;
                case 2 :
                    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1375:10: this_INT_1= RULE_INT
                    {
                    this_INT_1=(Token)match(input,RULE_INT,FOLLOW_RULE_INT_in_ruleOneModelProperty3593); 

                    		current.merge(this_INT_1);
                        
                     
                        newLeafNode(this_INT_1, grammarAccess.getOneModelPropertyAccess().getINTTerminalRuleCall_0_1()); 
                        

                    }
                    break;

            }

            kw=(Token)match(input,27,FOLLOW_27_in_ruleOneModelProperty3612); 

                    current.merge(kw);
                    newLeafNode(kw, grammarAccess.getOneModelPropertyAccess().getColonKeyword_1()); 
                
            this_NemoId_3=(Token)match(input,RULE_NEMOID,FOLLOW_RULE_NEMOID_in_ruleOneModelProperty3627); 

            		current.merge(this_NemoId_3);
                
             
                newLeafNode(this_NemoId_3, grammarAccess.getOneModelPropertyAccess().getNemoIdTerminalRuleCall_2()); 
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleOneModelProperty"


    // $ANTLR start "entryRuleNewObj"
    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1403:1: entryRuleNewObj returns [String current=null] : iv_ruleNewObj= ruleNewObj EOF ;
    public final String entryRuleNewObj() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleNewObj = null;


        try {
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1404:2: (iv_ruleNewObj= ruleNewObj EOF )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1405:2: iv_ruleNewObj= ruleNewObj EOF
            {
             newCompositeNode(grammarAccess.getNewObjRule()); 
            pushFollow(FOLLOW_ruleNewObj_in_entryRuleNewObj3673);
            iv_ruleNewObj=ruleNewObj();

            state._fsp--;

             current =iv_ruleNewObj.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleNewObj3684); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleNewObj"


    // $ANTLR start "ruleNewObj"
    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1412:1: ruleNewObj returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= 'CREATE' | kw= 'IMPORT' ) ;
    public final AntlrDatatypeRuleToken ruleNewObj() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;

         enterRule(); 
            
        try {
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1415:28: ( (kw= 'CREATE' | kw= 'IMPORT' ) )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1416:1: (kw= 'CREATE' | kw= 'IMPORT' )
            {
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1416:1: (kw= 'CREATE' | kw= 'IMPORT' )
            int alt31=2;
            int LA31_0 = input.LA(1);

            if ( (LA31_0==17) ) {
                alt31=1;
            }
            else if ( (LA31_0==35) ) {
                alt31=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 31, 0, input);

                throw nvae;
            }
            switch (alt31) {
                case 1 :
                    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1417:2: kw= 'CREATE'
                    {
                    kw=(Token)match(input,17,FOLLOW_17_in_ruleNewObj3722); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getNewObjAccess().getCREATEKeyword_0()); 
                        

                    }
                    break;
                case 2 :
                    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/parser/antlr/internal/InternalEditor.g:1424:2: kw= 'IMPORT'
                    {
                    kw=(Token)match(input,35,FOLLOW_35_in_ruleNewObj3741); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getNewObjAccess().getIMPORTKeyword_1()); 
                        

                    }
                    break;

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleNewObj"

    // Delegated rules


    protected DFA2 dfa2 = new DFA2(this);
    static final String DFA2_eotS =
        "\15\uffff";
    static final String DFA2_eofS =
        "\15\uffff";
    static final String DFA2_minS =
        "\1\15\1\uffff\1\5\1\uffff\2\22\7\uffff";
    static final String DFA2_maxS =
        "\1\43\1\uffff\1\34\1\uffff\2\31\7\uffff";
    static final String DFA2_acceptS =
        "\1\uffff\1\1\1\uffff\1\3\2\uffff\1\2\1\7\1\5\1\11\1\10\1\4\1\6";
    static final String DFA2_specialS =
        "\15\uffff}>";
    static final String[] DFA2_transitionS = {
            "\1\1\3\uffff\1\2\3\uffff\1\4\1\5\14\uffff\1\3",
            "",
            "\1\6\14\uffff\1\3\4\uffff\1\10\1\uffff\1\7\2\uffff\1\11",
            "",
            "\1\13\4\uffff\1\14\1\uffff\1\12",
            "\1\13\4\uffff\1\14\1\uffff\1\12",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA2_eot = DFA.unpackEncodedString(DFA2_eotS);
    static final short[] DFA2_eof = DFA.unpackEncodedString(DFA2_eofS);
    static final char[] DFA2_min = DFA.unpackEncodedStringToUnsignedChars(DFA2_minS);
    static final char[] DFA2_max = DFA.unpackEncodedStringToUnsignedChars(DFA2_maxS);
    static final short[] DFA2_accept = DFA.unpackEncodedString(DFA2_acceptS);
    static final short[] DFA2_special = DFA.unpackEncodedString(DFA2_specialS);
    static final short[][] DFA2_transition;

    static {
        int numStates = DFA2_transitionS.length;
        DFA2_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA2_transition[i] = DFA.unpackEncodedString(DFA2_transitionS[i]);
        }
    }

    class DFA2 extends DFA {

        public DFA2(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 2;
            this.eot = DFA2_eot;
            this.eof = DFA2_eof;
            this.min = DFA2_min;
            this.max = DFA2_max;
            this.accept = DFA2_accept;
            this.special = DFA2_special;
            this.transition = DFA2_transition;
        }
        public String getDescription() {
            return "119:1: ( ruleSenEngines | ruleUser | this_Node_2= ruleNode | this_NodeOperating_3= ruleNodeOperating | this_Connection_4= ruleConnection | this_ConnectionUpdate_5= ruleConnectionUpdate | this_Flow_6= ruleFlow | this_FlowUpdate_7= ruleFlowUpdate | this_Operation_8= ruleOperation )";
        }
    }
 

    public static final BitSet FOLLOW_ruleModel_in_entryRuleModel75 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleModel85 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleSentence_in_ruleModel130 = new BitSet(new long[]{0x0000000800622002L});
    public static final BitSet FOLLOW_ruleSentence_in_entryRuleSentence166 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleSentence176 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleSenEngines_in_ruleSentence217 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleUser_in_ruleSentence238 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleNode_in_ruleSentence265 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleNodeOperating_in_ruleSentence292 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleConnection_in_ruleSentence319 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleConnectionUpdate_in_ruleSentence346 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFlow_in_ruleSentence373 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFlowUpdate_in_ruleSentence400 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOperation_in_ruleSentence427 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleSenEngines_in_entryRuleSenEngines463 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleSenEngines474 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_13_in_ruleSenEngines512 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleIpv4_in_ruleSenEngines534 = new BitSet(new long[]{0x000000000000C000L});
    public static final BitSet FOLLOW_14_in_ruleSenEngines553 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleIpv4_in_ruleSenEngines575 = new BitSet(new long[]{0x000000000000C000L});
    public static final BitSet FOLLOW_15_in_ruleSenEngines595 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleIpv4_in_entryRuleIpv4636 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleIpv4647 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_IPV4_SUB_in_ruleIpv4687 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_16_in_ruleIpv4705 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_IPV4_SUB_in_ruleIpv4720 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_16_in_ruleIpv4738 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_IPV4_SUB_in_ruleIpv4753 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_16_in_ruleIpv4771 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_IPV4_SUB_in_ruleIpv4786 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleUser_in_entryRuleUser832 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleUser843 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_17_in_ruleUser881 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_RULE_STRING_in_ruleUser896 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_RULE_STRING_in_ruleUser916 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_RULE_STRING_in_ruleUser936 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_15_in_ruleUser954 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleNode_in_entryRuleNode994 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleNode1004 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleNewObj_in_ruleNode1045 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_18_in_ruleNode1056 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RULE_NEMOID_in_ruleNode1073 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_19_in_ruleNode1091 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RULE_NEMOID_in_ruleNode1102 = new BitSet(new long[]{0x0000000200108000L});
    public static final BitSet FOLLOW_20_in_ruleNode1115 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RULE_NEMOID_in_ruleNode1135 = new BitSet(new long[]{0x000000020000C000L});
    public static final BitSet FOLLOW_14_in_ruleNode1148 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RULE_NEMOID_in_ruleNode1168 = new BitSet(new long[]{0x000000020000C000L});
    public static final BitSet FOLLOW_ruleProperty_in_ruleNode1189 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_15_in_ruleNode1202 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleNodeOperating_in_entryRuleNodeOperating1240 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleNodeOperating1250 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_21_in_ruleNodeOperating1288 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_22_in_ruleNodeOperating1306 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_18_in_ruleNodeOperating1319 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RULE_NEMOID_in_ruleNodeOperating1339 = new BitSet(new long[]{0x0000000200188000L});
    public static final BitSet FOLLOW_19_in_ruleNodeOperating1352 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RULE_NEMOID_in_ruleNodeOperating1363 = new BitSet(new long[]{0x0000000200108000L});
    public static final BitSet FOLLOW_20_in_ruleNodeOperating1377 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RULE_NEMOID_in_ruleNodeOperating1397 = new BitSet(new long[]{0x000000020000C000L});
    public static final BitSet FOLLOW_14_in_ruleNodeOperating1410 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RULE_NEMOID_in_ruleNodeOperating1430 = new BitSet(new long[]{0x000000020000C000L});
    public static final BitSet FOLLOW_ruleProperty_in_ruleNodeOperating1451 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_15_in_ruleNodeOperating1464 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleConnection_in_entryRuleConnection1500 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleConnection1510 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_17_in_ruleConnection1547 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_23_in_ruleConnection1559 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RULE_NEMOID_in_ruleConnection1576 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_19_in_ruleConnection1593 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RULE_NEMOID_in_ruleConnection1604 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_24_in_ruleConnection1616 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RULE_NEMOID_in_ruleConnection1636 = new BitSet(new long[]{0x000000020000C000L});
    public static final BitSet FOLLOW_14_in_ruleConnection1649 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RULE_NEMOID_in_ruleConnection1669 = new BitSet(new long[]{0x000000020000C000L});
    public static final BitSet FOLLOW_ruleProperty_in_ruleConnection1689 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_15_in_ruleConnection1702 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleConnectionUpdate_in_entryRuleConnectionUpdate1738 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleConnectionUpdate1748 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_21_in_ruleConnectionUpdate1786 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_22_in_ruleConnectionUpdate1804 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_23_in_ruleConnectionUpdate1817 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RULE_NEMOID_in_ruleConnectionUpdate1837 = new BitSet(new long[]{0x0000000201088000L});
    public static final BitSet FOLLOW_19_in_ruleConnectionUpdate1850 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RULE_NEMOID_in_ruleConnectionUpdate1861 = new BitSet(new long[]{0x0000000201008000L});
    public static final BitSet FOLLOW_24_in_ruleConnectionUpdate1875 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RULE_NEMOID_in_ruleConnectionUpdate1895 = new BitSet(new long[]{0x000000020000C000L});
    public static final BitSet FOLLOW_14_in_ruleConnectionUpdate1908 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RULE_NEMOID_in_ruleConnectionUpdate1928 = new BitSet(new long[]{0x000000020000C000L});
    public static final BitSet FOLLOW_ruleProperty_in_ruleConnectionUpdate1949 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_15_in_ruleConnectionUpdate1962 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFlow_in_entryRuleFlow1998 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleFlow2008 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_17_in_ruleFlow2045 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_25_in_ruleFlow2057 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RULE_NEMOID_in_ruleFlow2074 = new BitSet(new long[]{0x0000000004008000L});
    public static final BitSet FOLLOW_ruleMatches_in_ruleFlow2096 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_15_in_ruleFlow2109 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFlowUpdate_in_entryRuleFlowUpdate2145 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleFlowUpdate2155 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_21_in_ruleFlowUpdate2193 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_22_in_ruleFlowUpdate2211 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_25_in_ruleFlowUpdate2224 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RULE_NEMOID_in_ruleFlowUpdate2244 = new BitSet(new long[]{0x0000000004008000L});
    public static final BitSet FOLLOW_ruleMatches_in_ruleFlowUpdate2261 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_15_in_ruleFlowUpdate2274 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMatches_in_entryRuleMatches2311 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMatches2322 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_26_in_ruleMatches2360 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_ruleMatch_in_ruleMatches2382 = new BitSet(new long[]{0x0000000000004002L});
    public static final BitSet FOLLOW_14_in_ruleMatches2401 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_ruleMatch_in_ruleMatches2423 = new BitSet(new long[]{0x0000000000004002L});
    public static final BitSet FOLLOW_ruleMatch_in_entryRuleMatch2471 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMatch2482 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_NEMOID_in_ruleMatch2522 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_27_in_ruleMatch2540 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_RULE_STRING_in_ruleMatch2555 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOperation_in_entryRuleOperation2600 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleOperation2610 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_17_in_ruleOperation2647 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_28_in_ruleOperation2659 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RULE_NEMOID_in_ruleOperation2676 = new BitSet(new long[]{0x0000000060000000L});
    public static final BitSet FOLLOW_29_in_ruleOperation2694 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_RULE_INT_in_ruleOperation2711 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_30_in_ruleOperation2730 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RULE_NEMOID_in_ruleOperation2750 = new BitSet(new long[]{0x0000000180000000L});
    public static final BitSet FOLLOW_ruleCondition_in_ruleOperation2767 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_31_in_ruleOperation2780 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RULE_NEMOID_in_ruleOperation2792 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_RULE_NEMOID_in_ruleOperation2809 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_27_in_ruleOperation2820 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RULE_NEMOID_in_ruleOperation2840 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_15_in_ruleOperation2854 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleCondition_in_entryRuleCondition2891 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleCondition2902 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_32_in_ruleCondition2940 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_RULE_STRING_in_ruleCondition2955 = new BitSet(new long[]{0x0000000000004002L});
    public static final BitSet FOLLOW_14_in_ruleCondition2974 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_RULE_STRING_in_ruleCondition2989 = new BitSet(new long[]{0x0000000000004002L});
    public static final BitSet FOLLOW_ruleProperty_in_entryRuleProperty3037 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleProperty3048 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_33_in_ruleProperty3086 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_ruleOneProperty_in_ruleProperty3108 = new BitSet(new long[]{0x0000000000004002L});
    public static final BitSet FOLLOW_14_in_ruleProperty3127 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_ruleOneProperty_in_ruleProperty3149 = new BitSet(new long[]{0x0000000000004002L});
    public static final BitSet FOLLOW_ruleOneProperty_in_entryRuleOneProperty3197 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleOneProperty3208 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_NEMOID_in_ruleOneProperty3248 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_27_in_ruleOneProperty3266 = new BitSet(new long[]{0x00000000000000A0L});
    public static final BitSet FOLLOW_RULE_STRING_in_ruleOneProperty3282 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_INT_in_ruleOneProperty3308 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleModelProperty_in_entryRuleModelProperty3355 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleModelProperty3366 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_34_in_ruleModelProperty3404 = new BitSet(new long[]{0x00000000000000A0L});
    public static final BitSet FOLLOW_ruleOneModelProperty_in_ruleModelProperty3426 = new BitSet(new long[]{0x0000000000004002L});
    public static final BitSet FOLLOW_14_in_ruleModelProperty3445 = new BitSet(new long[]{0x00000000000000A0L});
    public static final BitSet FOLLOW_ruleOneModelProperty_in_ruleModelProperty3467 = new BitSet(new long[]{0x0000000000004002L});
    public static final BitSet FOLLOW_ruleOneModelProperty_in_entryRuleOneModelProperty3515 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleOneModelProperty3526 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_STRING_in_ruleOneModelProperty3567 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_RULE_INT_in_ruleOneModelProperty3593 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_27_in_ruleOneModelProperty3612 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RULE_NEMOID_in_ruleOneModelProperty3627 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleNewObj_in_entryRuleNewObj3673 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleNewObj3684 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_17_in_ruleNewObj3722 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_35_in_ruleNewObj3741 = new BitSet(new long[]{0x0000000000000002L});

}