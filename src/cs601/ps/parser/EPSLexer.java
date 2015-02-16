// Generated from /home/ivy/Dropbox/cs652/syuanivy-ps/src/cs601/ps/parser/EPS.g4 by ANTLR 4.5

package cs601.ps.parser;
import cs601.ps.PSInterpreter;
import cs601.ps.objects.*;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class EPSLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.5", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, STRING=8, SYM=9, 
		INT=10, REAL=11, ID=12, WS=13, CMT=14;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "STRING", "SYM", 
		"INT", "REAL", "ID", "WS", "CMT"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'=='", "'true'", "'false'", "'['", "']'", "'{'", "'}'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, "STRING", "SYM", "INT", 
		"REAL", "ID", "WS", "CMT"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public EPSLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "EPS.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\20j\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3"+
		"\3\3\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\7\t"+
		"8\n\t\f\t\16\t;\13\t\3\t\3\t\3\n\3\n\3\n\3\13\5\13C\n\13\3\13\6\13F\n"+
		"\13\r\13\16\13G\3\f\3\f\3\f\7\fM\n\f\f\f\16\fP\13\f\3\r\3\r\7\rT\n\r\f"+
		"\r\16\rW\13\r\3\16\6\16Z\n\16\r\16\16\16[\3\16\3\16\3\17\3\17\7\17b\n"+
		"\17\f\17\16\17e\13\17\3\17\3\17\3\17\3\17\49c\2\20\3\3\5\4\7\5\t\6\13"+
		"\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\3\2\7\4\2--//\3\2\62"+
		";\5\2C\\aac|\6\2//C\\aac|\5\2\13\f\17\17\"\"p\2\3\3\2\2\2\2\5\3\2\2\2"+
		"\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3"+
		"\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2"+
		"\2\2\35\3\2\2\2\3\37\3\2\2\2\5\"\3\2\2\2\7\'\3\2\2\2\t-\3\2\2\2\13/\3"+
		"\2\2\2\r\61\3\2\2\2\17\63\3\2\2\2\21\65\3\2\2\2\23>\3\2\2\2\25B\3\2\2"+
		"\2\27I\3\2\2\2\31Q\3\2\2\2\33Y\3\2\2\2\35_\3\2\2\2\37 \7?\2\2 !\7?\2\2"+
		"!\4\3\2\2\2\"#\7v\2\2#$\7t\2\2$%\7w\2\2%&\7g\2\2&\6\3\2\2\2\'(\7h\2\2"+
		"()\7c\2\2)*\7n\2\2*+\7u\2\2+,\7g\2\2,\b\3\2\2\2-.\7]\2\2.\n\3\2\2\2/\60"+
		"\7_\2\2\60\f\3\2\2\2\61\62\7}\2\2\62\16\3\2\2\2\63\64\7\177\2\2\64\20"+
		"\3\2\2\2\659\7*\2\2\668\13\2\2\2\67\66\3\2\2\28;\3\2\2\29:\3\2\2\29\67"+
		"\3\2\2\2:<\3\2\2\2;9\3\2\2\2<=\7+\2\2=\22\3\2\2\2>?\7\61\2\2?@\5\31\r"+
		"\2@\24\3\2\2\2AC\t\2\2\2BA\3\2\2\2BC\3\2\2\2CE\3\2\2\2DF\t\3\2\2ED\3\2"+
		"\2\2FG\3\2\2\2GE\3\2\2\2GH\3\2\2\2H\26\3\2\2\2IJ\5\25\13\2JN\7\60\2\2"+
		"KM\t\3\2\2LK\3\2\2\2MP\3\2\2\2NL\3\2\2\2NO\3\2\2\2O\30\3\2\2\2PN\3\2\2"+
		"\2QU\t\4\2\2RT\t\5\2\2SR\3\2\2\2TW\3\2\2\2US\3\2\2\2UV\3\2\2\2V\32\3\2"+
		"\2\2WU\3\2\2\2XZ\t\6\2\2YX\3\2\2\2Z[\3\2\2\2[Y\3\2\2\2[\\\3\2\2\2\\]\3"+
		"\2\2\2]^\b\16\2\2^\34\3\2\2\2_c\7\'\2\2`b\13\2\2\2a`\3\2\2\2be\3\2\2\2"+
		"cd\3\2\2\2ca\3\2\2\2df\3\2\2\2ec\3\2\2\2fg\7\f\2\2gh\3\2\2\2hi\b\17\2"+
		"\2i\36\3\2\2\2\n\29BGNU[c\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}