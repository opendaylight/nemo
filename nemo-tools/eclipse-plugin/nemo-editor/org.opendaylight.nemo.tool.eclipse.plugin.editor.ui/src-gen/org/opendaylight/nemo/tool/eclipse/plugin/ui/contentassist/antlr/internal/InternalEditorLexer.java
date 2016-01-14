package org.opendaylight.nemo.tool.eclipse.plugin.ui.contentassist.antlr.internal;

// Hack: Use our own Lexer superclass by means of import. 
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.ui.editor.contentassist.antlr.internal.Lexer;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalEditorLexer extends Lexer {
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
    public static final int RULE_IPV4_SUB=7;
    public static final int T__28=28;
    public static final int RULE_INT=6;
    public static final int T__29=29;
    public static final int RULE_NEMOID=4;
    public static final int T__22=22;
    public static final int RULE_ML_COMMENT=9;
    public static final int T__23=23;
    public static final int T__24=24;
    public static final int T__25=25;
    public static final int T__20=20;
    public static final int T__21=21;

    // delegates
    // delegators

    public InternalEditorLexer() {;} 
    public InternalEditorLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public InternalEditorLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g"; }

    // $ANTLR start "T__13"
    public final void mT__13() throws RecognitionException {
        try {
            int _type = T__13;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:11:7: ( 'UPDATE' )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:11:9: 'UPDATE'
            {
            match("UPDATE"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__13"

    // $ANTLR start "T__14"
    public final void mT__14() throws RecognitionException {
        try {
            int _type = T__14;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:12:7: ( 'DELETE' )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:12:9: 'DELETE'
            {
            match("DELETE"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__14"

    // $ANTLR start "T__15"
    public final void mT__15() throws RecognitionException {
        try {
            int _type = T__15;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:13:7: ( 'CREATE' )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:13:9: 'CREATE'
            {
            match("CREATE"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__15"

    // $ANTLR start "T__16"
    public final void mT__16() throws RecognitionException {
        try {
            int _type = T__16;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:14:7: ( 'IMPORT' )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:14:9: 'IMPORT'
            {
            match("IMPORT"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__16"

    // $ANTLR start "T__17"
    public final void mT__17() throws RecognitionException {
        try {
            int _type = T__17;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:15:7: ( 'Engines:' )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:15:9: 'Engines:'
            {
            match("Engines:"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__17"

    // $ANTLR start "T__18"
    public final void mT__18() throws RecognitionException {
        try {
            int _type = T__18;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:16:7: ( ';' )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:16:9: ';'
            {
            match(';'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__18"

    // $ANTLR start "T__19"
    public final void mT__19() throws RecognitionException {
        try {
            int _type = T__19;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:17:7: ( ',' )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:17:9: ','
            {
            match(','); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__19"

    // $ANTLR start "T__20"
    public final void mT__20() throws RecognitionException {
        try {
            int _type = T__20;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:18:7: ( '.' )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:18:9: '.'
            {
            match('.'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__20"

    // $ANTLR start "T__21"
    public final void mT__21() throws RecognitionException {
        try {
            int _type = T__21;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:19:7: ( 'Node' )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:19:9: 'Node'
            {
            match("Node"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__21"

    // $ANTLR start "T__22"
    public final void mT__22() throws RecognitionException {
        try {
            int _type = T__22;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:20:7: ( 'Type' )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:20:9: 'Type'
            {
            match("Type"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__22"

    // $ANTLR start "T__23"
    public final void mT__23() throws RecognitionException {
        try {
            int _type = T__23;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:21:7: ( 'Contain' )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:21:9: 'Contain'
            {
            match("Contain"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__23"

    // $ANTLR start "T__24"
    public final void mT__24() throws RecognitionException {
        try {
            int _type = T__24;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:22:7: ( 'Connection' )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:22:9: 'Connection'
            {
            match("Connection"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__24"

    // $ANTLR start "T__25"
    public final void mT__25() throws RecognitionException {
        try {
            int _type = T__25;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:23:7: ( 'Endnodes' )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:23:9: 'Endnodes'
            {
            match("Endnodes"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__25"

    // $ANTLR start "T__26"
    public final void mT__26() throws RecognitionException {
        try {
            int _type = T__26;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:24:7: ( 'Flow' )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:24:9: 'Flow'
            {
            match("Flow"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__26"

    // $ANTLR start "T__27"
    public final void mT__27() throws RecognitionException {
        try {
            int _type = T__27;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:25:7: ( 'Match' )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:25:9: 'Match'
            {
            match("Match"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__27"

    // $ANTLR start "T__28"
    public final void mT__28() throws RecognitionException {
        try {
            int _type = T__28;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:26:7: ( ':' )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:26:9: ':'
            {
            match(':'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__28"

    // $ANTLR start "T__29"
    public final void mT__29() throws RecognitionException {
        try {
            int _type = T__29;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:27:7: ( 'Operation' )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:27:9: 'Operation'
            {
            match("Operation"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__29"

    // $ANTLR start "T__30"
    public final void mT__30() throws RecognitionException {
        try {
            int _type = T__30;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:28:7: ( 'Target' )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:28:9: 'Target'
            {
            match("Target"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__30"

    // $ANTLR start "T__31"
    public final void mT__31() throws RecognitionException {
        try {
            int _type = T__31;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:29:7: ( 'Action' )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:29:9: 'Action'
            {
            match("Action"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__31"

    // $ANTLR start "T__32"
    public final void mT__32() throws RecognitionException {
        try {
            int _type = T__32;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:30:7: ( 'Priority' )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:30:9: 'Priority'
            {
            match("Priority"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__32"

    // $ANTLR start "T__33"
    public final void mT__33() throws RecognitionException {
        try {
            int _type = T__33;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:31:7: ( 'Condition' )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:31:9: 'Condition'
            {
            match("Condition"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__33"

    // $ANTLR start "T__34"
    public final void mT__34() throws RecognitionException {
        try {
            int _type = T__34;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:32:7: ( 'Property ' )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:32:9: 'Property '
            {
            match("Property "); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__34"

    // $ANTLR start "T__35"
    public final void mT__35() throws RecognitionException {
        try {
            int _type = T__35;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:33:7: ( 'Property' )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:33:9: 'Property'
            {
            match("Property"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__35"

    // $ANTLR start "RULE_IPV4_SUB"
    public final void mRULE_IPV4_SUB() throws RecognitionException {
        try {
            int _type = RULE_IPV4_SUB;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:5205:15: ( ( '0' .. '9' | '1' .. '9' '0' .. '9' | '1' '0' .. '9' '0' .. '9' | '2' '0' .. '4' '0' .. '9' | '25' '0' .. '5' ) )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:5205:17: ( '0' .. '9' | '1' .. '9' '0' .. '9' | '1' '0' .. '9' '0' .. '9' | '2' '0' .. '4' '0' .. '9' | '25' '0' .. '5' )
            {
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:5205:17: ( '0' .. '9' | '1' .. '9' '0' .. '9' | '1' '0' .. '9' '0' .. '9' | '2' '0' .. '4' '0' .. '9' | '25' '0' .. '5' )
            int alt1=5;
            alt1 = dfa1.predict(input);
            switch (alt1) {
                case 1 :
                    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:5205:18: '0' .. '9'
                    {
                    matchRange('0','9'); 

                    }
                    break;
                case 2 :
                    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:5205:27: '1' .. '9' '0' .. '9'
                    {
                    matchRange('1','9'); 
                    matchRange('0','9'); 

                    }
                    break;
                case 3 :
                    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:5205:45: '1' '0' .. '9' '0' .. '9'
                    {
                    match('1'); 
                    matchRange('0','9'); 
                    matchRange('0','9'); 

                    }
                    break;
                case 4 :
                    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:5205:67: '2' '0' .. '4' '0' .. '9'
                    {
                    match('2'); 
                    matchRange('0','4'); 
                    matchRange('0','9'); 

                    }
                    break;
                case 5 :
                    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:5205:89: '25' '0' .. '5'
                    {
                    match("25"); 

                    matchRange('0','5'); 

                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_IPV4_SUB"

    // $ANTLR start "RULE_NEMOID"
    public final void mRULE_NEMOID() throws RecognitionException {
        try {
            int _type = RULE_NEMOID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:5207:13: ( ( 'a' .. 'z' | 'A' .. 'Z' | '$' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '$' | '_' | '-' | '0' .. '9' )* )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:5207:15: ( 'a' .. 'z' | 'A' .. 'Z' | '$' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '$' | '_' | '-' | '0' .. '9' )*
            {
            if ( input.LA(1)=='$'||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:5207:43: ( 'a' .. 'z' | 'A' .. 'Z' | '$' | '_' | '-' | '0' .. '9' )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0=='$'||LA2_0=='-'||(LA2_0>='0' && LA2_0<='9')||(LA2_0>='A' && LA2_0<='Z')||LA2_0=='_'||(LA2_0>='a' && LA2_0<='z')) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:
            	    {
            	    if ( input.LA(1)=='$'||input.LA(1)=='-'||(input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_NEMOID"

    // $ANTLR start "RULE_ID"
    public final void mRULE_ID() throws RecognitionException {
        try {
            int _type = RULE_ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:5209:9: ( ( '^' )? ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )* )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:5209:11: ( '^' )? ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )*
            {
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:5209:11: ( '^' )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0=='^') ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:5209:11: '^'
                    {
                    match('^'); 

                    }
                    break;

            }

            if ( (input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:5209:40: ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( ((LA4_0>='0' && LA4_0<='9')||(LA4_0>='A' && LA4_0<='Z')||LA4_0=='_'||(LA4_0>='a' && LA4_0<='z')) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:
            	    {
            	    if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_ID"

    // $ANTLR start "RULE_INT"
    public final void mRULE_INT() throws RecognitionException {
        try {
            int _type = RULE_INT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:5211:10: ( ( '0' .. '9' )+ )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:5211:12: ( '0' .. '9' )+
            {
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:5211:12: ( '0' .. '9' )+
            int cnt5=0;
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( ((LA5_0>='0' && LA5_0<='9')) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:5211:13: '0' .. '9'
            	    {
            	    matchRange('0','9'); 

            	    }
            	    break;

            	default :
            	    if ( cnt5 >= 1 ) break loop5;
                        EarlyExitException eee =
                            new EarlyExitException(5, input);
                        throw eee;
                }
                cnt5++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_INT"

    // $ANTLR start "RULE_STRING"
    public final void mRULE_STRING() throws RecognitionException {
        try {
            int _type = RULE_STRING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:5213:13: ( ( '\"' ( '\\\\' . | ~ ( ( '\\\\' | '\"' ) ) )* '\"' | '\\'' ( '\\\\' . | ~ ( ( '\\\\' | '\\'' ) ) )* '\\'' ) )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:5213:15: ( '\"' ( '\\\\' . | ~ ( ( '\\\\' | '\"' ) ) )* '\"' | '\\'' ( '\\\\' . | ~ ( ( '\\\\' | '\\'' ) ) )* '\\'' )
            {
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:5213:15: ( '\"' ( '\\\\' . | ~ ( ( '\\\\' | '\"' ) ) )* '\"' | '\\'' ( '\\\\' . | ~ ( ( '\\\\' | '\\'' ) ) )* '\\'' )
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0=='\"') ) {
                alt8=1;
            }
            else if ( (LA8_0=='\'') ) {
                alt8=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 0, input);

                throw nvae;
            }
            switch (alt8) {
                case 1 :
                    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:5213:16: '\"' ( '\\\\' . | ~ ( ( '\\\\' | '\"' ) ) )* '\"'
                    {
                    match('\"'); 
                    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:5213:20: ( '\\\\' . | ~ ( ( '\\\\' | '\"' ) ) )*
                    loop6:
                    do {
                        int alt6=3;
                        int LA6_0 = input.LA(1);

                        if ( (LA6_0=='\\') ) {
                            alt6=1;
                        }
                        else if ( ((LA6_0>='\u0000' && LA6_0<='!')||(LA6_0>='#' && LA6_0<='[')||(LA6_0>=']' && LA6_0<='\uFFFF')) ) {
                            alt6=2;
                        }


                        switch (alt6) {
                    	case 1 :
                    	    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:5213:21: '\\\\' .
                    	    {
                    	    match('\\'); 
                    	    matchAny(); 

                    	    }
                    	    break;
                    	case 2 :
                    	    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:5213:28: ~ ( ( '\\\\' | '\"' ) )
                    	    {
                    	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='!')||(input.LA(1)>='#' && input.LA(1)<='[')||(input.LA(1)>=']' && input.LA(1)<='\uFFFF') ) {
                    	        input.consume();

                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;}


                    	    }
                    	    break;

                    	default :
                    	    break loop6;
                        }
                    } while (true);

                    match('\"'); 

                    }
                    break;
                case 2 :
                    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:5213:48: '\\'' ( '\\\\' . | ~ ( ( '\\\\' | '\\'' ) ) )* '\\''
                    {
                    match('\''); 
                    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:5213:53: ( '\\\\' . | ~ ( ( '\\\\' | '\\'' ) ) )*
                    loop7:
                    do {
                        int alt7=3;
                        int LA7_0 = input.LA(1);

                        if ( (LA7_0=='\\') ) {
                            alt7=1;
                        }
                        else if ( ((LA7_0>='\u0000' && LA7_0<='&')||(LA7_0>='(' && LA7_0<='[')||(LA7_0>=']' && LA7_0<='\uFFFF')) ) {
                            alt7=2;
                        }


                        switch (alt7) {
                    	case 1 :
                    	    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:5213:54: '\\\\' .
                    	    {
                    	    match('\\'); 
                    	    matchAny(); 

                    	    }
                    	    break;
                    	case 2 :
                    	    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:5213:61: ~ ( ( '\\\\' | '\\'' ) )
                    	    {
                    	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='&')||(input.LA(1)>='(' && input.LA(1)<='[')||(input.LA(1)>=']' && input.LA(1)<='\uFFFF') ) {
                    	        input.consume();

                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;}


                    	    }
                    	    break;

                    	default :
                    	    break loop7;
                        }
                    } while (true);

                    match('\''); 

                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_STRING"

    // $ANTLR start "RULE_ML_COMMENT"
    public final void mRULE_ML_COMMENT() throws RecognitionException {
        try {
            int _type = RULE_ML_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:5215:17: ( '/*' ( options {greedy=false; } : . )* '*/' )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:5215:19: '/*' ( options {greedy=false; } : . )* '*/'
            {
            match("/*"); 

            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:5215:24: ( options {greedy=false; } : . )*
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( (LA9_0=='*') ) {
                    int LA9_1 = input.LA(2);

                    if ( (LA9_1=='/') ) {
                        alt9=2;
                    }
                    else if ( ((LA9_1>='\u0000' && LA9_1<='.')||(LA9_1>='0' && LA9_1<='\uFFFF')) ) {
                        alt9=1;
                    }


                }
                else if ( ((LA9_0>='\u0000' && LA9_0<=')')||(LA9_0>='+' && LA9_0<='\uFFFF')) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:5215:52: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop9;
                }
            } while (true);

            match("*/"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_ML_COMMENT"

    // $ANTLR start "RULE_SL_COMMENT"
    public final void mRULE_SL_COMMENT() throws RecognitionException {
        try {
            int _type = RULE_SL_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:5217:17: ( '//' (~ ( ( '\\n' | '\\r' ) ) )* ( ( '\\r' )? '\\n' )? )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:5217:19: '//' (~ ( ( '\\n' | '\\r' ) ) )* ( ( '\\r' )? '\\n' )?
            {
            match("//"); 

            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:5217:24: (~ ( ( '\\n' | '\\r' ) ) )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( ((LA10_0>='\u0000' && LA10_0<='\t')||(LA10_0>='\u000B' && LA10_0<='\f')||(LA10_0>='\u000E' && LA10_0<='\uFFFF')) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:5217:24: ~ ( ( '\\n' | '\\r' ) )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='\t')||(input.LA(1)>='\u000B' && input.LA(1)<='\f')||(input.LA(1)>='\u000E' && input.LA(1)<='\uFFFF') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop10;
                }
            } while (true);

            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:5217:40: ( ( '\\r' )? '\\n' )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0=='\n'||LA12_0=='\r') ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:5217:41: ( '\\r' )? '\\n'
                    {
                    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:5217:41: ( '\\r' )?
                    int alt11=2;
                    int LA11_0 = input.LA(1);

                    if ( (LA11_0=='\r') ) {
                        alt11=1;
                    }
                    switch (alt11) {
                        case 1 :
                            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:5217:41: '\\r'
                            {
                            match('\r'); 

                            }
                            break;

                    }

                    match('\n'); 

                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_SL_COMMENT"

    // $ANTLR start "RULE_WS"
    public final void mRULE_WS() throws RecognitionException {
        try {
            int _type = RULE_WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:5219:9: ( ( ' ' | '\\t' | '\\r' | '\\n' )+ )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:5219:11: ( ' ' | '\\t' | '\\r' | '\\n' )+
            {
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:5219:11: ( ' ' | '\\t' | '\\r' | '\\n' )+
            int cnt13=0;
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( ((LA13_0>='\t' && LA13_0<='\n')||LA13_0=='\r'||LA13_0==' ') ) {
                    alt13=1;
                }


                switch (alt13) {
            	case 1 :
            	    // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:
            	    {
            	    if ( (input.LA(1)>='\t' && input.LA(1)<='\n')||input.LA(1)=='\r'||input.LA(1)==' ' ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    if ( cnt13 >= 1 ) break loop13;
                        EarlyExitException eee =
                            new EarlyExitException(13, input);
                        throw eee;
                }
                cnt13++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_WS"

    // $ANTLR start "RULE_ANY_OTHER"
    public final void mRULE_ANY_OTHER() throws RecognitionException {
        try {
            int _type = RULE_ANY_OTHER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:5221:16: ( . )
            // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:5221:18: .
            {
            matchAny(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_ANY_OTHER"

    public void mTokens() throws RecognitionException {
        // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:1:8: ( T__13 | T__14 | T__15 | T__16 | T__17 | T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | T__25 | T__26 | T__27 | T__28 | T__29 | T__30 | T__31 | T__32 | T__33 | T__34 | T__35 | RULE_IPV4_SUB | RULE_NEMOID | RULE_ID | RULE_INT | RULE_STRING | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS | RULE_ANY_OTHER )
        int alt14=32;
        alt14 = dfa14.predict(input);
        switch (alt14) {
            case 1 :
                // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:1:10: T__13
                {
                mT__13(); 

                }
                break;
            case 2 :
                // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:1:16: T__14
                {
                mT__14(); 

                }
                break;
            case 3 :
                // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:1:22: T__15
                {
                mT__15(); 

                }
                break;
            case 4 :
                // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:1:28: T__16
                {
                mT__16(); 

                }
                break;
            case 5 :
                // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:1:34: T__17
                {
                mT__17(); 

                }
                break;
            case 6 :
                // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:1:40: T__18
                {
                mT__18(); 

                }
                break;
            case 7 :
                // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:1:46: T__19
                {
                mT__19(); 

                }
                break;
            case 8 :
                // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:1:52: T__20
                {
                mT__20(); 

                }
                break;
            case 9 :
                // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:1:58: T__21
                {
                mT__21(); 

                }
                break;
            case 10 :
                // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:1:64: T__22
                {
                mT__22(); 

                }
                break;
            case 11 :
                // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:1:70: T__23
                {
                mT__23(); 

                }
                break;
            case 12 :
                // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:1:76: T__24
                {
                mT__24(); 

                }
                break;
            case 13 :
                // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:1:82: T__25
                {
                mT__25(); 

                }
                break;
            case 14 :
                // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:1:88: T__26
                {
                mT__26(); 

                }
                break;
            case 15 :
                // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:1:94: T__27
                {
                mT__27(); 

                }
                break;
            case 16 :
                // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:1:100: T__28
                {
                mT__28(); 

                }
                break;
            case 17 :
                // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:1:106: T__29
                {
                mT__29(); 

                }
                break;
            case 18 :
                // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:1:112: T__30
                {
                mT__30(); 

                }
                break;
            case 19 :
                // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:1:118: T__31
                {
                mT__31(); 

                }
                break;
            case 20 :
                // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:1:124: T__32
                {
                mT__32(); 

                }
                break;
            case 21 :
                // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:1:130: T__33
                {
                mT__33(); 

                }
                break;
            case 22 :
                // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:1:136: T__34
                {
                mT__34(); 

                }
                break;
            case 23 :
                // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:1:142: T__35
                {
                mT__35(); 

                }
                break;
            case 24 :
                // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:1:148: RULE_IPV4_SUB
                {
                mRULE_IPV4_SUB(); 

                }
                break;
            case 25 :
                // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:1:162: RULE_NEMOID
                {
                mRULE_NEMOID(); 

                }
                break;
            case 26 :
                // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:1:174: RULE_ID
                {
                mRULE_ID(); 

                }
                break;
            case 27 :
                // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:1:182: RULE_INT
                {
                mRULE_INT(); 

                }
                break;
            case 28 :
                // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:1:191: RULE_STRING
                {
                mRULE_STRING(); 

                }
                break;
            case 29 :
                // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:1:203: RULE_ML_COMMENT
                {
                mRULE_ML_COMMENT(); 

                }
                break;
            case 30 :
                // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:1:219: RULE_SL_COMMENT
                {
                mRULE_SL_COMMENT(); 

                }
                break;
            case 31 :
                // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:1:235: RULE_WS
                {
                mRULE_WS(); 

                }
                break;
            case 32 :
                // ../org.opendaylight.nemo.tool.eclipse.plugin.editor.ui/src-gen/org/opendaylight/nemo/tool/eclipse/plugin/ui/contentassist/antlr/internal/InternalEditor.g:1:243: RULE_ANY_OTHER
                {
                mRULE_ANY_OTHER(); 

                }
                break;

        }

    }


    protected DFA1 dfa1 = new DFA1(this);
    protected DFA14 dfa14 = new DFA14(this);
    static final String DFA1_eotS =
        "\1\uffff\1\2\1\uffff\2\2\3\10\4\uffff";
    static final String DFA1_eofS =
        "\14\uffff";
    static final String DFA1_minS =
        "\2\60\1\uffff\5\60\4\uffff";
    static final String DFA1_maxS =
        "\2\71\1\uffff\4\71\1\65\4\uffff";
    static final String DFA1_acceptS =
        "\2\uffff\1\1\5\uffff\1\2\1\3\1\4\1\5";
    static final String DFA1_specialS =
        "\14\uffff}>";
    static final String[] DFA1_transitionS = {
            "\1\2\1\1\1\3\7\4",
            "\12\5",
            "",
            "\5\6\1\7\4\10",
            "\12\10",
            "\12\11",
            "\12\12",
            "\6\13",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA1_eot = DFA.unpackEncodedString(DFA1_eotS);
    static final short[] DFA1_eof = DFA.unpackEncodedString(DFA1_eofS);
    static final char[] DFA1_min = DFA.unpackEncodedStringToUnsignedChars(DFA1_minS);
    static final char[] DFA1_max = DFA.unpackEncodedStringToUnsignedChars(DFA1_maxS);
    static final short[] DFA1_accept = DFA.unpackEncodedString(DFA1_acceptS);
    static final short[] DFA1_special = DFA.unpackEncodedString(DFA1_specialS);
    static final short[][] DFA1_transition;

    static {
        int numStates = DFA1_transitionS.length;
        DFA1_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA1_transition[i] = DFA.unpackEncodedString(DFA1_transitionS[i]);
        }
    }

    class DFA1 extends DFA {

        public DFA1(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 1;
            this.eot = DFA1_eot;
            this.eof = DFA1_eof;
            this.min = DFA1_min;
            this.max = DFA1_max;
            this.accept = DFA1_accept;
            this.special = DFA1_special;
            this.transition = DFA1_transition;
        }
        public String getDescription() {
            return "5205:17: ( '0' .. '9' | '1' .. '9' '0' .. '9' | '1' '0' .. '9' '0' .. '9' | '2' '0' .. '4' '0' .. '9' | '25' '0' .. '5' )";
        }
    }
    static final String DFA14_eotS =
        "\1\uffff\5\36\3\uffff\4\36\1\uffff\3\36\4\62\1\36\1\34\1\uffff\3\34\2\uffff\1\36\1\uffff\6\36\3\uffff\5\36\1\uffff\3\36\1\62\2\uffff\3\62\5\uffff\20\36\3\62\11\36\1\152\1\153\1\36\1\155\16\36\2\uffff\1\36\1\uffff\1\175\4\36\1\u0082\1\u0083\1\u0084\3\36\1\u0088\2\36\1\u008b\1\uffff\1\36\1\u008d\2\36\3\uffff\1\u0090\2\36\1\uffff\2\36\1\uffff\1\36\1\uffff\2\36\1\uffff\2\36\1\uffff\1\u009a\1\36\1\u009c\1\u009e\1\36\1\u00a0\1\uffff\1\u00a1\3\uffff\1\u00a2\3\uffff";
    static final String DFA14_eofS =
        "\u00a3\uffff";
    static final String DFA14_minS =
        "\1\0\5\60\3\uffff\4\60\1\uffff\10\60\1\101\1\uffff\2\0\1\52\2\uffff\1\60\1\uffff\6\60\3\uffff\5\60\1\uffff\4\60\2\uffff\3\60\5\uffff\34\60\2\44\1\60\1\44\16\60\2\uffff\1\60\1\uffff\1\44\4\60\3\44\3\60\1\44\2\60\1\44\1\uffff\1\60\1\44\2\60\3\uffff\1\44\2\60\1\uffff\2\60\1\uffff\1\60\1\uffff\2\60\1\uffff\2\60\1\uffff\1\44\1\60\1\44\1\40\1\60\1\44\1\uffff\1\44\3\uffff\1\44\3\uffff";
    static final String DFA14_maxS =
        "\1\uffff\5\172\3\uffff\4\172\1\uffff\3\172\4\71\2\172\1\uffff\2\uffff\1\57\2\uffff\1\172\1\uffff\6\172\3\uffff\5\172\1\uffff\3\172\1\71\2\uffff\3\71\5\uffff\20\172\3\71\33\172\2\uffff\1\172\1\uffff\17\172\1\uffff\4\172\3\uffff\3\172\1\uffff\2\172\1\uffff\1\172\1\uffff\2\172\1\uffff\2\172\1\uffff\6\172\1\uffff\1\172\3\uffff\1\172\3\uffff";
    static final String DFA14_acceptS =
        "\6\uffff\1\6\1\7\1\10\4\uffff\1\20\11\uffff\1\31\3\uffff\1\37\1\40\1\uffff\1\31\6\uffff\1\6\1\7\1\10\5\uffff\1\20\4\uffff\1\30\1\33\3\uffff\1\32\1\34\1\35\1\36\1\37\56\uffff\1\11\1\12\1\uffff\1\16\17\uffff\1\17\4\uffff\1\1\1\2\1\3\3\uffff\1\4\2\uffff\1\22\1\uffff\1\23\2\uffff\1\13\2\uffff\1\5\6\uffff\1\15\1\uffff\1\24\1\26\1\27\1\uffff\1\25\1\21\1\14";
    static final String DFA14_specialS =
        "\1\1\27\uffff\1\2\1\0\u0089\uffff}>";
    static final String[] DFA14_transitionS = {
            "\11\34\2\33\2\34\1\33\22\34\1\33\1\34\1\30\1\34\1\27\2\34\1\31\4\34\1\7\1\34\1\10\1\32\1\22\1\21\1\23\7\24\1\15\1\6\5\34\1\17\1\25\1\3\1\2\1\5\1\13\2\25\1\4\3\25\1\14\1\11\1\16\1\20\3\25\1\12\1\1\5\25\3\34\1\26\1\25\1\34\32\25\uff85\34",
            "\12\37\7\uffff\17\37\1\35\12\37\4\uffff\1\37\1\uffff\32\37",
            "\12\37\7\uffff\4\37\1\40\25\37\4\uffff\1\37\1\uffff\32\37",
            "\12\37\7\uffff\21\37\1\41\10\37\4\uffff\1\37\1\uffff\16\37\1\42\13\37",
            "\12\37\7\uffff\14\37\1\43\15\37\4\uffff\1\37\1\uffff\32\37",
            "\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\15\37\1\44\14\37",
            "",
            "",
            "",
            "\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\16\37\1\50\13\37",
            "\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\1\52\27\37\1\51\1\37",
            "\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\13\37\1\53\16\37",
            "\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\1\54\31\37",
            "",
            "\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\17\37\1\56\12\37",
            "\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\2\37\1\57\27\37",
            "\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\21\37\1\60\10\37",
            "\12\61",
            "\12\63",
            "\5\64\1\65\4\66",
            "\12\66",
            "\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\32\37",
            "\32\67\4\uffff\1\67\1\uffff\32\67",
            "",
            "\0\70",
            "\0\70",
            "\1\71\4\uffff\1\72",
            "",
            "",
            "\12\37\7\uffff\3\37\1\74\26\37\4\uffff\1\37\1\uffff\32\37",
            "",
            "\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\32\37",
            "\12\37\7\uffff\13\37\1\75\16\37\4\uffff\1\37\1\uffff\32\37",
            "\12\37\7\uffff\4\37\1\76\25\37\4\uffff\1\37\1\uffff\32\37",
            "\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\15\37\1\77\14\37",
            "\12\37\7\uffff\17\37\1\100\12\37\4\uffff\1\37\1\uffff\32\37",
            "\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\3\37\1\102\2\37\1\101\23\37",
            "",
            "",
            "",
            "\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\3\37\1\103\26\37",
            "\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\17\37\1\104\12\37",
            "\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\21\37\1\105\10\37",
            "\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\16\37\1\106\13\37",
            "\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\23\37\1\107\6\37",
            "",
            "\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\4\37\1\110\25\37",
            "\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\23\37\1\111\6\37",
            "\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\10\37\1\112\5\37\1\113\13\37",
            "\12\114",
            "",
            "",
            "\12\115",
            "\6\116\4\63",
            "\12\63",
            "",
            "",
            "",
            "",
            "",
            "\12\37\7\uffff\1\117\31\37\4\uffff\1\37\1\uffff\32\37",
            "\12\37\7\uffff\4\37\1\120\25\37\4\uffff\1\37\1\uffff\32\37",
            "\12\37\7\uffff\1\121\31\37\4\uffff\1\37\1\uffff\32\37",
            "\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\3\37\1\124\11\37\1\123\5\37\1\122\6\37",
            "\12\37\7\uffff\16\37\1\125\13\37\4\uffff\1\37\1\uffff\32\37",
            "\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\10\37\1\126\21\37",
            "\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\15\37\1\127\14\37",
            "\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\4\37\1\130\25\37",
            "\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\4\37\1\131\25\37",
            "\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\6\37\1\132\23\37",
            "\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\26\37\1\133\3\37",
            "\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\2\37\1\134\27\37",
            "\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\21\37\1\135\10\37",
            "\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\10\37\1\136\21\37",
            "\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\16\37\1\137\13\37",
            "\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\17\37\1\140\12\37",
            "\12\63",
            "\12\63",
            "\12\63",
            "\12\37\7\uffff\23\37\1\141\6\37\4\uffff\1\37\1\uffff\32\37",
            "\12\37\7\uffff\23\37\1\142\6\37\4\uffff\1\37\1\uffff\32\37",
            "\12\37\7\uffff\23\37\1\143\6\37\4\uffff\1\37\1\uffff\32\37",
            "\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\1\144\31\37",
            "\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\4\37\1\145\25\37",
            "\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\10\37\1\146\21\37",
            "\12\37\7\uffff\21\37\1\147\10\37\4\uffff\1\37\1\uffff\32\37",
            "\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\15\37\1\150\14\37",
            "\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\16\37\1\151\13\37",
            "\1\36\10\uffff\1\36\2\uffff\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\32\37",
            "\1\36\10\uffff\1\36\2\uffff\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\32\37",
            "\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\4\37\1\154\25\37",
            "\1\36\10\uffff\1\36\2\uffff\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\32\37",
            "\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\7\37\1\156\22\37",
            "\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\1\157\31\37",
            "\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\16\37\1\160\13\37",
            "\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\21\37\1\161\10\37",
            "\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\4\37\1\162\25\37",
            "\12\37\7\uffff\4\37\1\163\25\37\4\uffff\1\37\1\uffff\32\37",
            "\12\37\7\uffff\4\37\1\164\25\37\4\uffff\1\37\1\uffff\32\37",
            "\12\37\7\uffff\4\37\1\165\25\37\4\uffff\1\37\1\uffff\32\37",
            "\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\10\37\1\166\21\37",
            "\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\2\37\1\167\27\37",
            "\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\23\37\1\170\6\37",
            "\12\37\7\uffff\23\37\1\171\6\37\4\uffff\1\37\1\uffff\32\37",
            "\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\4\37\1\172\25\37",
            "\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\3\37\1\173\26\37",
            "",
            "",
            "\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\23\37\1\174\6\37",
            "",
            "\1\36\10\uffff\1\36\2\uffff\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\32\37",
            "\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\23\37\1\176\6\37",
            "\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\15\37\1\177\14\37",
            "\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\10\37\1\u0080\21\37",
            "\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\21\37\1\u0081\10\37",
            "\1\36\10\uffff\1\36\2\uffff\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\32\37",
            "\1\36\10\uffff\1\36\2\uffff\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\32\37",
            "\1\36\10\uffff\1\36\2\uffff\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\32\37",
            "\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\15\37\1\u0085\14\37",
            "\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\23\37\1\u0086\6\37",
            "\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\10\37\1\u0087\21\37",
            "\1\36\10\uffff\1\36\2\uffff\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\32\37",
            "\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\22\37\1\u0089\7\37",
            "\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\4\37\1\u008a\25\37",
            "\1\36\10\uffff\1\36\2\uffff\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\32\37",
            "",
            "\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\10\37\1\u008c\21\37",
            "\1\36\10\uffff\1\36\2\uffff\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\32\37",
            "\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\23\37\1\u008e\6\37",
            "\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\23\37\1\u008f\6\37",
            "",
            "",
            "",
            "\1\36\10\uffff\1\36\2\uffff\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\32\37",
            "\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\10\37\1\u0091\21\37",
            "\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\16\37\1\u0092\13\37",
            "",
            "\12\37\1\u0093\6\uffff\32\37\4\uffff\1\37\1\uffff\32\37",
            "\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\22\37\1\u0094\7\37",
            "",
            "\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\16\37\1\u0095\13\37",
            "",
            "\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\30\37\1\u0096\1\37",
            "\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\30\37\1\u0097\1\37",
            "",
            "\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\16\37\1\u0098\13\37",
            "\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\15\37\1\u0099\14\37",
            "",
            "\1\36\10\uffff\1\36\2\uffff\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\32\37",
            "\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\15\37\1\u009b\14\37",
            "\1\36\10\uffff\1\36\2\uffff\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\32\37",
            "\1\u009d\3\uffff\1\36\10\uffff\1\36\2\uffff\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\32\37",
            "\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\15\37\1\u009f\14\37",
            "\1\36\10\uffff\1\36\2\uffff\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\32\37",
            "",
            "\1\36\10\uffff\1\36\2\uffff\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\32\37",
            "",
            "",
            "",
            "\1\36\10\uffff\1\36\2\uffff\12\37\7\uffff\32\37\4\uffff\1\37\1\uffff\32\37",
            "",
            "",
            ""
    };

    static final short[] DFA14_eot = DFA.unpackEncodedString(DFA14_eotS);
    static final short[] DFA14_eof = DFA.unpackEncodedString(DFA14_eofS);
    static final char[] DFA14_min = DFA.unpackEncodedStringToUnsignedChars(DFA14_minS);
    static final char[] DFA14_max = DFA.unpackEncodedStringToUnsignedChars(DFA14_maxS);
    static final short[] DFA14_accept = DFA.unpackEncodedString(DFA14_acceptS);
    static final short[] DFA14_special = DFA.unpackEncodedString(DFA14_specialS);
    static final short[][] DFA14_transition;

    static {
        int numStates = DFA14_transitionS.length;
        DFA14_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA14_transition[i] = DFA.unpackEncodedString(DFA14_transitionS[i]);
        }
    }

    class DFA14 extends DFA {

        public DFA14(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 14;
            this.eot = DFA14_eot;
            this.eof = DFA14_eof;
            this.min = DFA14_min;
            this.max = DFA14_max;
            this.accept = DFA14_accept;
            this.special = DFA14_special;
            this.transition = DFA14_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( T__13 | T__14 | T__15 | T__16 | T__17 | T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | T__25 | T__26 | T__27 | T__28 | T__29 | T__30 | T__31 | T__32 | T__33 | T__34 | T__35 | RULE_IPV4_SUB | RULE_NEMOID | RULE_ID | RULE_INT | RULE_STRING | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS | RULE_ANY_OTHER );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA14_25 = input.LA(1);

                        s = -1;
                        if ( ((LA14_25>='\u0000' && LA14_25<='\uFFFF')) ) {s = 56;}

                        else s = 28;

                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA14_0 = input.LA(1);

                        s = -1;
                        if ( (LA14_0=='U') ) {s = 1;}

                        else if ( (LA14_0=='D') ) {s = 2;}

                        else if ( (LA14_0=='C') ) {s = 3;}

                        else if ( (LA14_0=='I') ) {s = 4;}

                        else if ( (LA14_0=='E') ) {s = 5;}

                        else if ( (LA14_0==';') ) {s = 6;}

                        else if ( (LA14_0==',') ) {s = 7;}

                        else if ( (LA14_0=='.') ) {s = 8;}

                        else if ( (LA14_0=='N') ) {s = 9;}

                        else if ( (LA14_0=='T') ) {s = 10;}

                        else if ( (LA14_0=='F') ) {s = 11;}

                        else if ( (LA14_0=='M') ) {s = 12;}

                        else if ( (LA14_0==':') ) {s = 13;}

                        else if ( (LA14_0=='O') ) {s = 14;}

                        else if ( (LA14_0=='A') ) {s = 15;}

                        else if ( (LA14_0=='P') ) {s = 16;}

                        else if ( (LA14_0=='1') ) {s = 17;}

                        else if ( (LA14_0=='0') ) {s = 18;}

                        else if ( (LA14_0=='2') ) {s = 19;}

                        else if ( ((LA14_0>='3' && LA14_0<='9')) ) {s = 20;}

                        else if ( (LA14_0=='B'||(LA14_0>='G' && LA14_0<='H')||(LA14_0>='J' && LA14_0<='L')||(LA14_0>='Q' && LA14_0<='S')||(LA14_0>='V' && LA14_0<='Z')||LA14_0=='_'||(LA14_0>='a' && LA14_0<='z')) ) {s = 21;}

                        else if ( (LA14_0=='^') ) {s = 22;}

                        else if ( (LA14_0=='$') ) {s = 23;}

                        else if ( (LA14_0=='\"') ) {s = 24;}

                        else if ( (LA14_0=='\'') ) {s = 25;}

                        else if ( (LA14_0=='/') ) {s = 26;}

                        else if ( ((LA14_0>='\t' && LA14_0<='\n')||LA14_0=='\r'||LA14_0==' ') ) {s = 27;}

                        else if ( ((LA14_0>='\u0000' && LA14_0<='\b')||(LA14_0>='\u000B' && LA14_0<='\f')||(LA14_0>='\u000E' && LA14_0<='\u001F')||LA14_0=='!'||LA14_0=='#'||(LA14_0>='%' && LA14_0<='&')||(LA14_0>='(' && LA14_0<='+')||LA14_0=='-'||(LA14_0>='<' && LA14_0<='@')||(LA14_0>='[' && LA14_0<=']')||LA14_0=='`'||(LA14_0>='{' && LA14_0<='\uFFFF')) ) {s = 28;}

                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA14_24 = input.LA(1);

                        s = -1;
                        if ( ((LA14_24>='\u0000' && LA14_24<='\uFFFF')) ) {s = 56;}

                        else s = 28;

                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 14, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

}