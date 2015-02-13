// Generated from /Users/parrt/courses/CS652/projects-Spring-2015/ps/src/cs601/ps/parser/EPS.g4 by ANTLR 4.5

package cs601.ps.parser;
import cs601.ps.PSInterpreter;
import cs601.ps.objects.*;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link EPSParser}.
 */
public interface EPSListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link EPSParser#file}.
	 * @param ctx the parse tree
	 */
	void enterFile(EPSParser.FileContext ctx);
	/**
	 * Exit a parse tree produced by {@link EPSParser#file}.
	 * @param ctx the parse tree
	 */
	void exitFile(EPSParser.FileContext ctx);
	/**
	 * Enter a parse tree produced by {@link EPSParser#exec}.
	 * @param ctx the parse tree
	 */
	void enterExec(EPSParser.ExecContext ctx);
	/**
	 * Exit a parse tree produced by {@link EPSParser#exec}.
	 * @param ctx the parse tree
	 */
	void exitExec(EPSParser.ExecContext ctx);
	/**
	 * Enter a parse tree produced by {@link EPSParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterInstruction(EPSParser.InstructionContext ctx);
	/**
	 * Exit a parse tree produced by {@link EPSParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitInstruction(EPSParser.InstructionContext ctx);
	/**
	 * Enter a parse tree produced by {@link EPSParser#value}.
	 * @param ctx the parse tree
	 */
	void enterValue(EPSParser.ValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link EPSParser#value}.
	 * @param ctx the parse tree
	 */
	void exitValue(EPSParser.ValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link EPSParser#array}.
	 * @param ctx the parse tree
	 */
	void enterArray(EPSParser.ArrayContext ctx);
	/**
	 * Exit a parse tree produced by {@link EPSParser#array}.
	 * @param ctx the parse tree
	 */
	void exitArray(EPSParser.ArrayContext ctx);
	/**
	 * Enter a parse tree produced by {@link EPSParser#procedure}.
	 * @param ctx the parse tree
	 */
	void enterProcedure(EPSParser.ProcedureContext ctx);
	/**
	 * Exit a parse tree produced by {@link EPSParser#procedure}.
	 * @param ctx the parse tree
	 */
	void exitProcedure(EPSParser.ProcedureContext ctx);
}