// Generated from /home/ivy/Dropbox/cs652/syuanivy-ps/src/cs601/ps/parser/EPS.g4 by ANTLR 4.5

package cs601.ps.parser;
import cs601.ps.PSInterpreter;
import cs601.ps.objects.*;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class EPSParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, STRING=8, SYM=9, 
		INT=10, REAL=11, ID=12, WS=13, CMT=14;
	public static final int
		RULE_file = 0, RULE_exec = 1, RULE_instruction = 2, RULE_value = 3, RULE_array = 4, 
		RULE_procedure = 5;
	public static final String[] ruleNames = {
		"file", "exec", "instruction", "value", "array", "procedure"
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

	@Override
	public String getGrammarFileName() { return "EPS.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }


	    protected PSInterpreter interpreter;
	   	public EPSParser(TokenStream input, PSInterpreter interpreter) {
	   		this(input);
	   		this.interpreter = interpreter;
	    }

	public EPSParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class FileContext extends ParserRuleContext {
		public ExecContext exec() {
			return getRuleContext(ExecContext.class,0);
		}
		public FileContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_file; }
	}

	public final FileContext file() throws RecognitionException {
		FileContext _localctx = new FileContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_file);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(12);
			exec();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExecContext extends ParserRuleContext {
		public int n =  0;
		public InstructionContext instruction;
		public List<InstructionContext> instruction() {
			return getRuleContexts(InstructionContext.class);
		}
		public InstructionContext instruction(int i) {
			return getRuleContext(InstructionContext.class,i);
		}
		public ExecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exec; }
	}

	public final ExecContext exec() throws RecognitionException {
		ExecContext _localctx = new ExecContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_exec);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(21);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__1) | (1L << T__2) | (1L << T__3) | (1L << T__5) | (1L << STRING) | (1L << SYM) | (1L << INT) | (1L << REAL) | (1L << ID))) != 0)) {
				{
				setState(19);
				switch (_input.LA(1)) {
				case T__1:
				case T__2:
				case T__3:
				case T__5:
				case STRING:
				case SYM:
				case INT:
				case REAL:
				case ID:
					{
					setState(14);
					((ExecContext)_localctx).instruction = instruction();
					interpreter.execute(((ExecContext)_localctx).instruction.o); _localctx.n++;
					}
					break;
				case T__0:
					{
					setState(17);
					match(T__0);
					interpreter.popAndPrint();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(23);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InstructionContext extends ParserRuleContext {
		public PSObject o;
		public Token ID;
		public ValueContext value;
		public TerminalNode ID() { return getToken(EPSParser.ID, 0); }
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public InstructionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_instruction; }
	}

	public final InstructionContext instruction() throws RecognitionException {
		InstructionContext _localctx = new InstructionContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_instruction);
		try {
			setState(29);
			switch (_input.LA(1)) {
			case ID:
				enterOuterAlt(_localctx, 1);
				{
				setState(24);
				((InstructionContext)_localctx).ID = match(ID);
				((InstructionContext)_localctx).o =  interpreter.lookup((((InstructionContext)_localctx).ID!=null?((InstructionContext)_localctx).ID.getText():null));
				}
				break;
			case T__1:
			case T__2:
			case T__3:
			case T__5:
			case STRING:
			case SYM:
			case INT:
			case REAL:
				enterOuterAlt(_localctx, 2);
				{
				setState(26);
				((InstructionContext)_localctx).value = value();
				((InstructionContext)_localctx).o =  ((InstructionContext)_localctx).value.v;
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ValueContext extends ParserRuleContext {
		public PSObject v;
		public Token INT;
		public Token STRING;
		public Token REAL;
		public Token SYM;
		public ArrayContext array;
		public ProcedureContext procedure;
		public TerminalNode INT() { return getToken(EPSParser.INT, 0); }
		public TerminalNode STRING() { return getToken(EPSParser.STRING, 0); }
		public TerminalNode REAL() { return getToken(EPSParser.REAL, 0); }
		public TerminalNode SYM() { return getToken(EPSParser.SYM, 0); }
		public ArrayContext array() {
			return getRuleContext(ArrayContext.class,0);
		}
		public ProcedureContext procedure() {
			return getRuleContext(ProcedureContext.class,0);
		}
		public ValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_value; }
	}

	public final ValueContext value() throws RecognitionException {
		ValueContext _localctx = new ValueContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_value);
		try {
			setState(49);
			switch (_input.LA(1)) {
			case INT:
				enterOuterAlt(_localctx, 1);
				{
				setState(31);
				((ValueContext)_localctx).INT = match(INT);
				((ValueContext)_localctx).v = new PSInt((((ValueContext)_localctx).INT!=null?((ValueContext)_localctx).INT.getText():null));
				}
				break;
			case STRING:
				enterOuterAlt(_localctx, 2);
				{
				setState(33);
				((ValueContext)_localctx).STRING = match(STRING);
				((ValueContext)_localctx).v = new PSString((((ValueContext)_localctx).STRING!=null?((ValueContext)_localctx).STRING.getText():null));
				}
				break;
			case REAL:
				enterOuterAlt(_localctx, 3);
				{
				setState(35);
				((ValueContext)_localctx).REAL = match(REAL);
				((ValueContext)_localctx).v = new PSReal((((ValueContext)_localctx).REAL!=null?((ValueContext)_localctx).REAL.getText():null));
				}
				break;
			case SYM:
				enterOuterAlt(_localctx, 4);
				{
				setState(37);
				((ValueContext)_localctx).SYM = match(SYM);
				((ValueContext)_localctx).v = new PSNameLiteral((((ValueContext)_localctx).SYM!=null?((ValueContext)_localctx).SYM.getText():null));
				}
				break;
			case T__3:
				enterOuterAlt(_localctx, 5);
				{
				setState(39);
				((ValueContext)_localctx).array = array();
				((ValueContext)_localctx).v = ((ValueContext)_localctx).array.a;
				}
				break;
			case T__5:
				enterOuterAlt(_localctx, 6);
				{
				setState(42);
				((ValueContext)_localctx).procedure = procedure();
				((ValueContext)_localctx).v = ((ValueContext)_localctx).procedure.p;
				}
				break;
			case T__1:
				enterOuterAlt(_localctx, 7);
				{
				setState(45);
				match(T__1);
				((ValueContext)_localctx).v = new PSBoolean(true);
				}
				break;
			case T__2:
				enterOuterAlt(_localctx, 8);
				{
				setState(47);
				match(T__2);
				((ValueContext)_localctx).v = new PSBoolean(false);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArrayContext extends ParserRuleContext {
		public PSArray a;
		public ExecContext exec;
		public ExecContext exec() {
			return getRuleContext(ExecContext.class,0);
		}
		public ArrayContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_array; }
	}

	public final ArrayContext array() throws RecognitionException {
		ArrayContext _localctx = new ArrayContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_array);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(51);
			match(T__3);
			setState(52);
			((ArrayContext)_localctx).exec = exec();
			setState(53);
			match(T__4);
			((ArrayContext)_localctx).a =  interpreter.popAsArray(((ArrayContext)_localctx).exec.n);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ProcedureContext extends ParserRuleContext {
		public PSDeferredCodeBlock p;
		public List<PSObject> code =  new ArrayList<>();
		public Token ID;
		public ValueContext value;
		public List<TerminalNode> ID() { return getTokens(EPSParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(EPSParser.ID, i);
		}
		public List<ValueContext> value() {
			return getRuleContexts(ValueContext.class);
		}
		public ValueContext value(int i) {
			return getRuleContext(ValueContext.class,i);
		}
		public ProcedureContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_procedure; }
	}

	public final ProcedureContext procedure() throws RecognitionException {
		ProcedureContext _localctx = new ProcedureContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_procedure);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(56);
			match(T__5);
			setState(64);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__1) | (1L << T__2) | (1L << T__3) | (1L << T__5) | (1L << STRING) | (1L << SYM) | (1L << INT) | (1L << REAL) | (1L << ID))) != 0)) {
				{
				setState(62);
				switch (_input.LA(1)) {
				case ID:
					{
					setState(57);
					((ProcedureContext)_localctx).ID = match(ID);
					_localctx.code.add(new PSOperatorName((((ProcedureContext)_localctx).ID!=null?((ProcedureContext)_localctx).ID.getText():null)));
					}
					break;
				case T__1:
				case T__2:
				case T__3:
				case T__5:
				case STRING:
				case SYM:
				case INT:
				case REAL:
					{
					setState(59);
					((ProcedureContext)_localctx).value = value();
					_localctx.code.add(((ProcedureContext)_localctx).value.v);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(66);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(67);
			match(T__6);
			((ProcedureContext)_localctx).p =  new PSDeferredCodeBlock(new PSProcedure(_localctx.code));
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\20I\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\3\2\3\2\3\3\3\3\3\3\3\3\3\3\7\3\26"+
		"\n\3\f\3\16\3\31\13\3\3\4\3\4\3\4\3\4\3\4\5\4 \n\4\3\5\3\5\3\5\3\5\3\5"+
		"\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\5\5\64\n\5\3\6\3"+
		"\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\7\7A\n\7\f\7\16\7D\13\7\3\7\3\7"+
		"\3\7\3\7\2\2\b\2\4\6\b\n\f\2\2N\2\16\3\2\2\2\4\27\3\2\2\2\6\37\3\2\2\2"+
		"\b\63\3\2\2\2\n\65\3\2\2\2\f:\3\2\2\2\16\17\5\4\3\2\17\3\3\2\2\2\20\21"+
		"\5\6\4\2\21\22\b\3\1\2\22\26\3\2\2\2\23\24\7\3\2\2\24\26\b\3\1\2\25\20"+
		"\3\2\2\2\25\23\3\2\2\2\26\31\3\2\2\2\27\25\3\2\2\2\27\30\3\2\2\2\30\5"+
		"\3\2\2\2\31\27\3\2\2\2\32\33\7\16\2\2\33 \b\4\1\2\34\35\5\b\5\2\35\36"+
		"\b\4\1\2\36 \3\2\2\2\37\32\3\2\2\2\37\34\3\2\2\2 \7\3\2\2\2!\"\7\f\2\2"+
		"\"\64\b\5\1\2#$\7\n\2\2$\64\b\5\1\2%&\7\r\2\2&\64\b\5\1\2\'(\7\13\2\2"+
		"(\64\b\5\1\2)*\5\n\6\2*+\b\5\1\2+\64\3\2\2\2,-\5\f\7\2-.\b\5\1\2.\64\3"+
		"\2\2\2/\60\7\4\2\2\60\64\b\5\1\2\61\62\7\5\2\2\62\64\b\5\1\2\63!\3\2\2"+
		"\2\63#\3\2\2\2\63%\3\2\2\2\63\'\3\2\2\2\63)\3\2\2\2\63,\3\2\2\2\63/\3"+
		"\2\2\2\63\61\3\2\2\2\64\t\3\2\2\2\65\66\7\6\2\2\66\67\5\4\3\2\678\7\7"+
		"\2\289\b\6\1\29\13\3\2\2\2:B\7\b\2\2;<\7\16\2\2<A\b\7\1\2=>\5\b\5\2>?"+
		"\b\7\1\2?A\3\2\2\2@;\3\2\2\2@=\3\2\2\2AD\3\2\2\2B@\3\2\2\2BC\3\2\2\2C"+
		"E\3\2\2\2DB\3\2\2\2EF\7\t\2\2FG\b\7\1\2G\r\3\2\2\2\b\25\27\37\63@B";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}