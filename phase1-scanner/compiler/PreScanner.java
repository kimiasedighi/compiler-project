package compiler;// DO NOT EDIT
// Generated by JFlex 1.8.2 http://jflex.de/
// source: preScanner.flex

import java.io.*;
import  java.io.IOException;
import java.util.HashMap;
import java.util.Map;


// See https://github.com/jflex-de/jflex/issues/222
@SuppressWarnings("FallThrough")
public class PreScanner {

  /** This character denotes the end of file. */
  public  final int YYEOF = -1;

  /** Initial size of the lookahead buffer. */
  private  final int ZZ_BUFFERSIZE = 16384;

  // Lexical states.
  public  final int YYINITIAL = 0;
  public  final int STRING = 2;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private  final int ZZ_LEXSTATE[] = {
     0,  0,  1, 1
  };

  /**
   * Top-level table for translating characters to character classes
   */
  private  final int [] ZZ_CMAP_TOP = zzUnpackcmap_top();

  private  final String ZZ_CMAP_TOP_PACKED_0 =
    "\1\0\25\u0100\1\u0200\11\u0100\1\u0300\17\u0100\1\u0400\247\u0100"+
    "\10\u0500\u1020\u0100";

  private  int [] zzUnpackcmap_top() {
    int [] result = new int[4352];
    int offset = 0;
    offset = zzUnpackcmap_top(ZZ_CMAP_TOP_PACKED_0, offset, result);
    return result;
  }

  private  int zzUnpackcmap_top(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /**
   * Second-level tables for translating characters to character classes
   */
  private  final int [] ZZ_CMAP_BLOCKS = zzUnpackcmap_blocks();

  private  final String ZZ_CMAP_BLOCKS_PACKED_0 =
    "\11\0\1\1\1\2\2\3\1\4\22\0\1\5\1\6"+
    "\1\7\2\0\1\10\1\11\1\0\1\12\1\13\1\14"+
    "\1\15\1\16\1\17\1\20\1\21\1\22\11\23\1\0"+
    "\1\24\1\25\1\26\1\27\2\0\1\30\3\31\1\32"+
    "\1\31\2\33\1\34\2\33\1\35\1\33\1\36\1\33"+
    "\1\37\1\33\1\40\5\33\1\41\2\33\1\42\1\43"+
    "\1\44\1\0\1\45\1\0\1\46\1\47\1\50\1\51"+
    "\1\52\1\53\1\54\1\55\1\56\1\33\1\57\1\60"+
    "\1\61\1\62\1\63\1\64\1\33\1\65\1\66\1\67"+
    "\1\70\1\71\1\72\1\41\1\73\1\33\1\74\1\75"+
    "\1\76\7\0\1\3\32\0\1\1\u01df\0\1\1\177\0"+
    "\13\1\35\0\2\3\5\0\1\1\57\0\1\1\240\0"+
    "\1\1\377\0\u0100\77";

  private  int [] zzUnpackcmap_blocks() {
    int [] result = new int[1536];
    int offset = 0;
    offset = zzUnpackcmap_blocks(ZZ_CMAP_BLOCKS_PACKED_0, offset, result);
    return result;
  }

  private  int zzUnpackcmap_blocks(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /**
   * Translates DFA states to action switch labels.
   */
  private  final int [] ZZ_ACTION = zzUnpackAction();

  private  final String ZZ_ACTION_PACKED_0 =
    "\2\0\1\1\1\2\1\3\1\4\1\1\1\5\1\6"+
    "\1\7\1\10\1\11\1\12\1\13\1\14\2\1\1\15"+
    "\1\16\1\17\1\20\4\21\1\22\1\23\1\1\15\21"+
    "\1\24\1\1\1\25\1\26\1\27\1\1\1\30\1\31"+
    "\1\32\1\33\1\34\1\0\1\1\1\35\1\1\1\0"+
    "\1\36\1\37\1\40\3\21\1\0\13\21\1\41\15\21"+
    "\1\42\1\0\2\1\1\0\1\1\3\21\3\0\12\21"+
    "\1\43\1\21\1\44\1\21\1\45\11\21\1\0\1\1"+
    "\3\21\1\46\2\0\1\47\1\21\1\50\4\21\1\51"+
    "\1\52\2\21\1\53\1\54\1\55\4\21\1\56\1\57"+
    "\1\60\2\21\1\61\2\21\2\0\1\62\1\63\3\21"+
    "\1\64\5\21\1\65\3\21\2\0\2\21\1\66\1\67"+
    "\1\21\1\70\1\71\1\72\3\21\2\0\1\21\1\0"+
    "\1\73\1\74\1\21\1\75\1\76\1\77\1\100\1\0"+
    "\1\21\1\0\1\21\2\101\1\102";

  private  int [] zzUnpackAction() {
    int [] result = new int[207];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private  int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /**
   * Translates a state to a row index in the transition table
   */
  private  final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private  final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\100\0\200\0\300\0\200\0\200\0\u0100\0\200"+
    "\0\200\0\u0140\0\u0180\0\200\0\u01c0\0\200\0\u0200\0\u0240"+
    "\0\u0280\0\200\0\u02c0\0\u0300\0\u0340\0\u0380\0\u03c0\0\u0400"+
    "\0\u0440\0\200\0\200\0\u0480\0\u04c0\0\u0500\0\u0540\0\u0580"+
    "\0\u05c0\0\u0600\0\u0640\0\u0680\0\u06c0\0\u0700\0\u0740\0\u0780"+
    "\0\u07c0\0\200\0\u0800\0\200\0\200\0\200\0\u0840\0\200"+
    "\0\200\0\200\0\200\0\200\0\u0880\0\u08c0\0\200\0\u0900"+
    "\0\u0940\0\200\0\200\0\200\0\u0980\0\u09c0\0\u0a00\0\u0a40"+
    "\0\u0a80\0\u0ac0\0\u0b00\0\u0b40\0\u0b80\0\u0bc0\0\u0c00\0\u0c40"+
    "\0\u0c80\0\u0cc0\0\u0d00\0\u0380\0\u0d40\0\u0d80\0\u0dc0\0\u0e00"+
    "\0\u0e40\0\u0e80\0\u0ec0\0\u0f00\0\u0f40\0\u0f80\0\u0fc0\0\u1000"+
    "\0\u1040\0\200\0\u1080\0\u10c0\0\u1100\0\u1140\0\u0940\0\u1180"+
    "\0\u11c0\0\u1200\0\u1240\0\u1280\0\u12c0\0\u1300\0\u1340\0\u1380"+
    "\0\u13c0\0\u1400\0\u1440\0\u1480\0\u14c0\0\u1500\0\u1540\0\u0380"+
    "\0\u1580\0\u0380\0\u15c0\0\u0380\0\u1600\0\u1640\0\u1680\0\u16c0"+
    "\0\u1700\0\u1740\0\u1780\0\u17c0\0\u1800\0\u1840\0\u1840\0\u1880"+
    "\0\u18c0\0\u1900\0\200\0\u1940\0\u1980\0\u0380\0\u19c0\0\u0380"+
    "\0\u1a00\0\u1a40\0\u1a80\0\u1ac0\0\u0380\0\u0380\0\u1b00\0\u1b40"+
    "\0\u0380\0\u0380\0\u0380\0\u1b80\0\u1bc0\0\u1c00\0\u1c40\0\u0380"+
    "\0\u0380\0\u0380\0\u1c80\0\u1cc0\0\u0380\0\u1d00\0\u1d40\0\u1d80"+
    "\0\u1dc0\0\u0380\0\u0380\0\u1e00\0\u1e40\0\u1e80\0\u0380\0\u1ec0"+
    "\0\u1f00\0\u1f40\0\u1f80\0\u1fc0\0\u0380\0\u2000\0\u2040\0\u2080"+
    "\0\u20c0\0\u2100\0\u2140\0\u2180\0\u0380\0\u0380\0\u21c0\0\u0380"+
    "\0\u0380\0\u0380\0\u2200\0\u2240\0\u2280\0\u22c0\0\u2300\0\u2340"+
    "\0\u2380\0\u0380\0\u0380\0\u23c0\0\u0380\0\200\0\200\0\u0380"+
    "\0\u2400\0\u2440\0\u2480\0\u24c0\0\u2500\0\u2480\0\u0380";

  private  int [] zzUnpackRowMap() {
    int [] result = new int[207];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private  int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /**
   * The transition table of the DFA
   */
  private  final int [] ZZ_TRANS = zzUnpackTrans();

  private  final String ZZ_TRANS_PACKED_0 =
    "\6\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12"+
    "\1\13\1\14\1\15\1\16\1\17\1\20\1\21\1\22"+
    "\1\23\1\24\1\25\6\26\1\27\1\30\1\31\1\26"+
    "\1\32\1\3\1\33\1\34\1\26\1\35\1\36\1\37"+
    "\1\40\1\41\2\26\1\42\3\26\1\43\1\26\1\44"+
    "\1\45\1\46\1\47\1\26\1\50\1\51\1\26\1\52"+
    "\1\53\1\54\3\3\3\55\2\3\1\56\33\3\1\57"+
    "\33\3\1\55\126\0\1\60\62\0\1\61\114\0\1\62"+
    "\77\0\1\63\77\0\1\64\65\0\1\65\4\0\1\66"+
    "\4\0\1\67\71\0\1\70\1\0\2\21\15\0\1\71"+
    "\56\0\1\70\1\0\2\21\102\0\1\72\77\0\1\73"+
    "\77\0\1\74\73\0\2\26\4\0\12\26\3\0\27\26"+
    "\26\0\2\26\4\0\12\26\3\0\5\26\1\75\21\26"+
    "\26\0\2\26\4\0\12\26\3\0\20\26\1\76\6\26"+
    "\26\0\2\26\4\0\12\26\3\0\5\26\1\77\21\26"+
    "\51\0\1\100\54\0\2\26\4\0\12\26\3\0\16\26"+
    "\1\101\1\26\1\102\1\26\1\103\4\26\26\0\2\26"+
    "\4\0\12\26\3\0\13\26\1\104\2\26\1\105\10\26"+
    "\26\0\2\26\4\0\12\26\3\0\5\26\1\106\10\26"+
    "\1\107\3\26\1\110\4\26\26\0\2\26\4\0\12\26"+
    "\3\0\13\26\1\111\13\26\26\0\2\26\4\0\12\26"+
    "\3\0\1\26\1\112\14\26\1\113\10\26\26\0\2\26"+
    "\4\0\12\26\3\0\6\26\1\114\5\26\1\115\1\116"+
    "\4\26\1\117\4\26\26\0\2\26\4\0\12\26\3\0"+
    "\5\26\1\120\15\26\1\121\3\26\26\0\2\26\4\0"+
    "\12\26\3\0\20\26\1\122\2\26\1\123\3\26\26\0"+
    "\2\26\4\0\12\26\3\0\5\26\1\124\21\26\26\0"+
    "\2\26\4\0\12\26\3\0\22\26\1\125\4\26\26\0"+
    "\2\26\4\0\12\26\3\0\10\26\1\126\7\26\1\127"+
    "\6\26\26\0\2\26\4\0\12\26\3\0\16\26\1\130"+
    "\10\26\26\0\2\26\4\0\12\26\3\0\10\26\1\131"+
    "\16\26\101\0\1\132\11\0\1\3\70\0\14\65\1\133"+
    "\63\65\2\66\1\3\1\66\1\134\73\66\20\0\1\70"+
    "\1\0\2\135\6\0\1\136\52\0\1\137\14\0\2\137"+
    "\4\0\3\137\13\0\6\137\46\0\2\26\4\0\12\26"+
    "\3\0\25\26\1\140\1\26\26\0\2\26\4\0\12\26"+
    "\3\0\11\26\1\141\15\26\26\0\2\26\4\0\12\26"+
    "\3\0\1\26\1\142\25\26\55\0\1\143\1\0\1\144"+
    "\4\0\1\145\41\0\2\26\4\0\12\26\3\0\16\26"+
    "\1\146\10\26\26\0\2\26\4\0\12\26\3\0\5\26"+
    "\1\147\21\26\26\0\2\26\4\0\12\26\3\0\16\26"+
    "\1\150\10\26\26\0\2\26\4\0\12\26\3\0\1\26"+
    "\1\151\25\26\26\0\2\26\4\0\12\26\3\0\15\26"+
    "\1\152\11\26\26\0\2\26\4\0\12\26\3\0\6\26"+
    "\1\153\20\26\26\0\2\26\4\0\12\26\3\0\23\26"+
    "\1\154\3\26\26\0\2\26\4\0\12\26\3\0\16\26"+
    "\1\155\10\26\26\0\2\26\4\0\12\26\3\0\21\26"+
    "\1\156\5\26\26\0\2\26\4\0\12\26\3\0\13\26"+
    "\1\157\13\26\26\0\2\26\4\0\12\26\3\0\20\26"+
    "\1\160\6\26\26\0\2\26\4\0\12\26\3\0\17\26"+
    "\1\161\7\26\26\0\2\26\4\0\12\26\3\0\22\26"+
    "\1\162\4\26\26\0\2\26\4\0\12\26\3\0\16\26"+
    "\1\163\10\26\26\0\2\26\4\0\12\26\3\0\25\26"+
    "\1\164\1\26\26\0\2\26\4\0\12\26\3\0\13\26"+
    "\1\165\13\26\26\0\2\26\4\0\12\26\3\0\11\26"+
    "\1\166\15\26\26\0\2\26\4\0\12\26\3\0\2\26"+
    "\1\167\24\26\26\0\2\26\4\0\12\26\3\0\22\26"+
    "\1\170\4\26\26\0\2\26\4\0\12\26\3\0\20\26"+
    "\1\171\6\26\26\0\2\26\4\0\12\26\3\0\11\26"+
    "\1\172\15\26\26\0\2\26\4\0\12\26\3\0\23\26"+
    "\1\173\3\26\26\0\2\26\4\0\12\26\3\0\11\26"+
    "\1\174\15\26\26\0\2\26\4\0\12\26\3\0\11\26"+
    "\1\175\15\26\4\0\14\65\1\133\4\65\1\3\56\65"+
    "\2\0\1\3\117\0\2\135\6\0\1\136\62\0\1\176"+
    "\1\0\1\176\2\0\2\177\76\0\2\26\4\0\1\200"+
    "\11\26\3\0\27\26\26\0\2\26\4\0\12\26\3\0"+
    "\15\26\1\201\11\26\26\0\2\26\4\0\12\26\3\0"+
    "\4\26\1\202\22\26\57\0\1\203\114\0\1\204\65\0"+
    "\1\205\43\0\2\26\4\0\12\26\3\0\13\26\1\206"+
    "\13\26\26\0\2\26\4\0\12\26\3\0\1\26\1\207"+
    "\25\26\26\0\2\26\4\0\12\26\3\0\11\26\1\210"+
    "\15\26\26\0\2\26\4\0\12\26\3\0\21\26\1\211"+
    "\5\26\26\0\2\26\4\0\12\26\3\0\22\26\1\212"+
    "\4\26\26\0\2\26\4\0\12\26\3\0\11\26\1\213"+
    "\15\26\26\0\2\26\4\0\12\26\3\0\2\26\1\214"+
    "\24\26\26\0\2\26\4\0\12\26\3\0\11\26\1\215"+
    "\15\26\26\0\2\26\4\0\12\26\3\0\5\26\1\216"+
    "\21\26\26\0\2\26\4\0\12\26\3\0\21\26\1\217"+
    "\5\26\26\0\2\26\4\0\12\26\3\0\16\26\1\220"+
    "\10\26\26\0\2\26\4\0\12\26\3\0\2\26\1\221"+
    "\1\26\1\222\22\26\26\0\2\26\4\0\12\26\3\0"+
    "\13\26\1\223\13\26\26\0\2\26\4\0\12\26\3\0"+
    "\24\26\1\224\2\26\26\0\2\26\4\0\12\26\3\0"+
    "\13\26\1\225\13\26\26\0\2\26\4\0\12\26\3\0"+
    "\23\26\1\226\3\26\26\0\2\26\4\0\12\26\3\0"+
    "\11\26\1\227\15\26\26\0\2\26\4\0\12\26\3\0"+
    "\21\26\1\230\5\26\26\0\2\26\4\0\12\26\3\0"+
    "\5\26\1\231\21\26\26\0\2\26\4\0\12\26\3\0"+
    "\4\26\1\232\22\26\26\0\2\26\4\0\12\26\3\0"+
    "\13\26\1\233\13\26\26\0\2\177\76\0\2\26\4\0"+
    "\12\26\3\0\20\26\1\234\6\26\26\0\2\26\4\0"+
    "\12\26\3\0\22\26\1\235\4\26\26\0\2\26\4\0"+
    "\4\26\1\236\1\237\4\26\3\0\27\26\66\0\1\240"+
    "\77\0\1\241\37\0\2\26\4\0\12\26\3\0\12\26"+
    "\1\242\14\26\26\0\2\26\4\0\12\26\3\0\21\26"+
    "\1\243\5\26\26\0\2\26\4\0\12\26\3\0\11\26"+
    "\1\244\15\26\26\0\2\26\4\0\12\26\3\0\15\26"+
    "\1\245\11\26\26\0\2\26\4\0\12\26\3\0\13\26"+
    "\1\246\13\26\26\0\2\26\4\0\12\26\3\0\5\26"+
    "\1\247\21\26\26\0\2\26\4\0\12\26\3\0\20\26"+
    "\1\250\6\26\26\0\2\26\4\0\12\26\3\0\1\26"+
    "\1\251\25\26\26\0\2\26\4\0\12\26\3\0\11\26"+
    "\1\252\15\26\26\0\2\26\4\0\12\26\3\0\20\26"+
    "\1\253\6\26\26\0\2\26\4\0\12\26\3\0\15\26"+
    "\1\254\11\26\26\0\2\26\4\0\12\26\3\0\5\26"+
    "\1\255\21\26\26\0\2\26\4\0\12\26\3\0\20\26"+
    "\1\256\6\26\26\0\2\26\4\0\12\26\3\0\15\26"+
    "\1\257\11\26\26\0\2\26\4\0\12\26\3\0\11\26"+
    "\1\260\15\26\54\0\1\261\101\0\1\262\47\0\2\26"+
    "\4\0\12\26\3\0\15\26\1\263\11\26\26\0\2\26"+
    "\4\0\12\26\3\0\5\26\1\264\21\26\26\0\2\26"+
    "\4\0\12\26\3\0\5\26\1\265\21\26\26\0\2\26"+
    "\4\0\12\26\3\0\22\26\1\266\4\26\26\0\2\26"+
    "\4\0\12\26\3\0\22\26\1\267\4\26\26\0\2\26"+
    "\4\0\12\26\3\0\3\26\1\270\23\26\26\0\2\26"+
    "\4\0\12\26\3\0\15\26\1\271\11\26\26\0\2\26"+
    "\4\0\12\26\3\0\7\26\1\272\17\26\26\0\2\26"+
    "\4\0\12\26\3\0\1\26\1\273\25\26\26\0\2\26"+
    "\4\0\12\26\3\0\22\26\1\274\4\26\26\0\2\26"+
    "\4\0\12\26\3\0\15\26\1\275\11\26\51\0\1\276"+
    "\77\0\1\277\54\0\2\26\4\0\12\26\3\0\23\26"+
    "\1\300\3\26\5\0\5\301\14\0\2\26\4\0\12\26"+
    "\3\0\27\26\26\0\2\26\4\0\12\26\3\0\5\26"+
    "\1\302\21\26\26\0\2\26\4\0\12\26\3\0\26\26"+
    "\1\303\26\0\2\26\4\0\12\26\3\0\5\26\1\304"+
    "\21\26\26\0\2\26\4\0\12\26\3\0\5\26\1\305"+
    "\21\26\51\0\1\306\77\0\1\307\54\0\2\26\4\0"+
    "\12\26\3\0\5\26\1\310\21\26\5\0\5\301\22\0"+
    "\12\311\4\0\26\311\26\0\2\26\4\0\12\26\3\0"+
    "\7\26\1\312\17\26\5\0\5\313\14\0\2\311\4\0"+
    "\12\311\3\0\27\311\26\0\2\26\4\0\12\26\3\0"+
    "\5\26\1\314\21\26\4\0\1\315\1\316\1\313\1\316"+
    "\1\313\1\316\72\315\22\0\2\26\4\0\12\26\3\0"+
    "\20\26\1\317\6\26\4\0\2\315\1\0\1\315\1\0"+
    "\73\315";

  private  int [] zzUnpackTrans() {
    int [] result = new int[9536];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private  int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** Error code for "Unknown internal scanner error". */
  private  final int ZZ_UNKNOWN_ERROR = 0;
  /** Error code for "could not match input". */
  private  final int ZZ_NO_MATCH = 1;
  /** Error code for "pushback value was too large". */
  private  final int ZZ_PUSHBACK_2BIG = 2;

  /**
   * Error messages for {@link #ZZ_UNKNOWN_ERROR}, {@link #ZZ_NO_MATCH}, and
   * {@link #ZZ_PUSHBACK_2BIG} respectively.
   */
  private  final String ZZ_ERROR_MSG[] = {
    "Unknown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state {@code aState}
   */
  private  final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private  final String ZZ_ATTRIBUTE_PACKED_0 =
    "\2\0\1\11\1\1\2\11\1\1\2\11\2\1\1\11"+
    "\1\1\1\11\3\1\1\11\7\1\2\11\16\1\1\11"+
    "\1\1\3\11\1\1\5\11\1\0\1\1\1\11\1\1"+
    "\1\0\3\11\3\1\1\0\31\1\1\11\1\0\2\1"+
    "\1\0\4\1\3\0\30\1\1\0\4\1\1\11\2\0"+
    "\32\1\2\0\17\1\2\0\13\1\2\0\1\1\1\0"+
    "\4\1\2\11\1\1\1\0\1\1\1\0\4\1";

  private  int [] zzUnpackAttribute() {
    int [] result = new int[207];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private  int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** Input device. */
  private java.io.Reader zzReader;

  /** Current state of the DFA. */
  private int zzState;

  /** Current lexical state. */
  private int zzLexicalState = YYINITIAL;

  /**
   * This buffer contains the current text to be matched and is the source of the {@link #yytext()}
   * string.
   */
  private char zzBuffer[] = new char[ZZ_BUFFERSIZE];

  /** Text position at the last accepting state. */
  private int zzMarkedPos;

  /** Current text position in the buffer. */
  private int zzCurrentPos;

  /** Marks the beginning of the {@link #yytext()} string in the buffer. */
  private int zzStartRead;

  /** Marks the last character in the buffer, that has been read from input. */
  private int zzEndRead;

  /**
   * Whether the scanner is at the end of file.
   * @see #yyatEOF
   */
  public boolean zzAtEOF;

  /**
   * The number of occupied positions in {@link #zzBuffer} beyond {@link #zzEndRead}.
   *
   * <p>When a lead/high surrogate has been read from the input stream into the final
   * {@link #zzBuffer} position, this will have a value of 1; otherwise, it will have a value of 0.
   */
  private int zzFinalHighSurrogate = 0;

  /** Number of newlines encountered up to the start of the matched text. */
  @SuppressWarnings("unused")
  private int yyline;

  /** Number of characters from the last newline up to the start of the matched text. */
  @SuppressWarnings("unused")
  private int yycolumn;

  /** Number of characters up to the start of the matched text. */
  @SuppressWarnings("unused")
  private long yychar;

  /** Whether the scanner is currently at the beginning of a line. */
  @SuppressWarnings("unused")
  private boolean zzAtBOL = true;

  /** Whether the user-EOF-code has already been executed. */
  private boolean zzEOFDone;

  /* user code: */
    StringBuffer out = new StringBuffer();
    Map<String, String> definedMap = new HashMap<String, String>();


  /**
   * Creates a new scanner
   *
   * @param   in  the java.io.Reader to read input from.
   */
  public PreScanner(java.io.Reader in) {
    this.zzReader = in;
  }

  /**
   * Translates raw input code points to DFA table row
   */
  private  int zzCMap(int input) {
    int offset = input & 255;
    return offset == input ? ZZ_CMAP_BLOCKS[offset] : ZZ_CMAP_BLOCKS[ZZ_CMAP_TOP[input >> 8] | offset];
  }

  /**
   * Refills the input buffer.
   *
   * @return {@code false} iff there was new input.
   * @exception java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {

    /* first: make room (if you can) */
    if (zzStartRead > 0) {
      zzEndRead += zzFinalHighSurrogate;
      zzFinalHighSurrogate = 0;
      System.arraycopy(zzBuffer, zzStartRead,
                       zzBuffer, 0,
                       zzEndRead - zzStartRead);

      /* translate stored positions */
      zzEndRead -= zzStartRead;
      zzCurrentPos -= zzStartRead;
      zzMarkedPos -= zzStartRead;
      zzStartRead = 0;
    }

    /* is the buffer big enough? */
    if (zzCurrentPos >= zzBuffer.length - zzFinalHighSurrogate) {
      /* if not: blow it up */
      char newBuffer[] = new char[zzBuffer.length * 2];
      System.arraycopy(zzBuffer, 0, newBuffer, 0, zzBuffer.length);
      zzBuffer = newBuffer;
      zzEndRead += zzFinalHighSurrogate;
      zzFinalHighSurrogate = 0;
    }

    /* fill the buffer with new input */
    int requested = zzBuffer.length - zzEndRead;
    int numRead = zzReader.read(zzBuffer, zzEndRead, requested);

    /* not supposed to occur according to specification of java.io.Reader */
    if (numRead == 0) {
      throw new java.io.IOException(
          "Reader returned 0 characters. See JFlex examples/zero-reader for a workaround.");
    }
    if (numRead > 0) {
      zzEndRead += numRead;
      if (Character.isHighSurrogate(zzBuffer[zzEndRead - 1])) {
        if (numRead == requested) { // We requested too few chars to encode a full Unicode character
          --zzEndRead;
          zzFinalHighSurrogate = 1;
        } else {                    // There is room in the buffer for at least one more char
          int c = zzReader.read();  // Expecting to read a paired low surrogate char
          if (c == -1) {
            return true;
          } else {
            zzBuffer[zzEndRead++] = (char)c;
          }
        }
      }
      /* potentially more input available */
      return false;
    }

    /* numRead < 0 ==> end of stream */
    return true;
  }


  /**
   * Closes the input reader.
   *
   * @throws java.io.IOException if the reader could not be closed.
   */
  public final void yyclose() throws java.io.IOException {
    zzAtEOF = true; // indicate end of file
    zzEndRead = zzStartRead; // invalidate buffer

    if (zzReader != null) {
      zzReader.close();
    }
  }


  /**
   * Resets the scanner to read from a new input stream.
   *
   * <p>Does not close the old reader.
   *
   * <p>All internal variables are reset, the old input stream <b>cannot</b> be reused (internal
   * buffer is discarded and lost). Lexical state is set to {@code ZZ_INITIAL}.
   *
   * <p>Internal scan buffer is resized down to its initial length, if it has grown.
   *
   * @param reader The new input stream.
   */
  public final void yyreset(java.io.Reader reader) {
    zzReader = reader;
    zzEOFDone = false;
    yyResetPosition();
    zzLexicalState = YYINITIAL;
    if (zzBuffer.length > ZZ_BUFFERSIZE) {
      zzBuffer = new char[ZZ_BUFFERSIZE];
    }
  }

  /**
   * Resets the input position.
   */
  private final void yyResetPosition() {
      zzAtBOL  = true;
      zzAtEOF  = false;
      zzCurrentPos = 0;
      zzMarkedPos = 0;
      zzStartRead = 0;
      zzEndRead = 0;
      zzFinalHighSurrogate = 0;
      yyline = 0;
      yycolumn = 0;
      yychar = 0L;
  }


  /**
   * Returns whether the scanner has reached the end of the reader it reads from.
   *
   * @return whether the scanner has reached EOF.
   */
  public final boolean yyatEOF() {
    return zzAtEOF;
  }


  /**
   * Returns the current lexical state.
   *
   * @return the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state.
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   *
   * @return the matched text.
   */
  public final String yytext() {
    return new String(zzBuffer, zzStartRead, zzMarkedPos-zzStartRead);
  }


  /**
   * Returns the character at the given position from the matched text.
   *
   * <p>It is equivalent to {@code yytext().charAt(pos)}, but faster.
   *
   * @param position the position of the character to fetch. A value from 0 to {@code yylength()-1}.
   *
   * @return the character at {@code position}.
   */
  public final char yycharat(int position) {
    return zzBuffer[zzStartRead + position];
  }


  /**
   * How many characters were matched.
   *
   * @return the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occurred while scanning.
   *
   * <p>In a well-formed scanner (no or only correct usage of {@code yypushback(int)} and a
   * match-all fallback rule) this method will only be called with things that
   * "Can't Possibly Happen".
   *
   * <p>If this method is called, something is seriously wrong (e.g. a JFlex bug producing a faulty
   * scanner etc.).
   *
   * <p>Usual syntax/scanner level error handling should be done in error fallback rules.
   *
   * @param errorCode the code of the error message to display.
   */
  private  void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    } catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  }


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * <p>They will be read again by then next call of the scanning method.
   *
   * @param number the number of characters to be read again. This number must not be greater than
   *     {@link #yylength()}.
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Contains user EOF-code, which will be executed exactly once,
   * when the end of file is reached
   */
  private void zzDoEOF() {
    if (!zzEOFDone) {
      zzEOFDone = true;
    

    }
  }




  /**
   * Resumes scanning until the next regular expression is matched, the end of input is encountered
   * or an I/O-Error occurs.
   *
   * @return the next token.
   * @exception java.io.IOException if any I/O-Error occurs.
   */
  public int yylex() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    char[] zzBufferL = zzBuffer;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;

      zzState = ZZ_LEXSTATE[zzLexicalState];

      // set up zzAction for empty match case:
      int zzAttributes = zzAttrL[zzState];
      if ( (zzAttributes & 1) == 1 ) {
        zzAction = zzState;
      }


      zzForAction: {
        while (true) {

          if (zzCurrentPosL < zzEndReadL) {
            zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL, zzEndReadL);
            zzCurrentPosL += Character.charCount(zzInput);
          }
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL, zzEndReadL);
              zzCurrentPosL += Character.charCount(zzInput);
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMap(zzInput) ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
        zzAtEOF = true;
            zzDoEOF();
        return YYEOF;
      }
      else {
        switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
          case 1:
            { out.append(yytext());
            }
            // fall through
          case 67: break;
          case 2:
            { out.append("!");
            }
            // fall through
          case 68: break;
          case 3:
            { yybegin(STRING); out.append(yytext());
            }
            // fall through
          case 69: break;
          case 4:
            { out.append("%");
            }
            // fall through
          case 70: break;
          case 5:
            { out.append("(");
            }
            // fall through
          case 71: break;
          case 6:
            { out.append(")");
            }
            // fall through
          case 72: break;
          case 7:
            { out.append("*");
            }
            // fall through
          case 73: break;
          case 8:
            { out.append("+");
            }
            // fall through
          case 74: break;
          case 9:
            { out.append(",");
            }
            // fall through
          case 75: break;
          case 10:
            { out.append("-");
            }
            // fall through
          case 76: break;
          case 11:
            { out.append(".");
            }
            // fall through
          case 77: break;
          case 12:
            { out.append("/");
            }
            // fall through
          case 78: break;
          case 13:
            { out.append(";");
            }
            // fall through
          case 79: break;
          case 14:
            { out.append("<");
            }
            // fall through
          case 80: break;
          case 15:
            { out.append("=");
            }
            // fall through
          case 81: break;
          case 16:
            { out.append(">");
            }
            // fall through
          case 82: break;
          case 17:
            { if (definedMap.containsKey(yytext())){
            out.append(definedMap.get(yytext()));
          } else {out.append(yytext());}
            }
            // fall through
          case 83: break;
          case 18:
            { out.append("[");
            }
            // fall through
          case 84: break;
          case 19:
            { out.append("]");
            }
            // fall through
          case 85: break;
          case 20:
            { out.append("{");
            }
            // fall through
          case 86: break;
          case 21:
            { out.append("}");
            }
            // fall through
          case 87: break;
          case 22:
            { System.out.print(yytext());
            }
            // fall through
          case 88: break;
          case 23:
            { out.append(yytext());yybegin(YYINITIAL);
            }
            // fall through
          case 89: break;
          case 24:
            { out.append("!=");
            }
            // fall through
          case 90: break;
          case 25:
            { out.append("&&");
            }
            // fall through
          case 91: break;
          case 26:
            { out.append("*=");
            }
            // fall through
          case 92: break;
          case 27:
            { out.append("+=");
            }
            // fall through
          case 93: break;
          case 28:
            { out.append("-=");
            }
            // fall through
          case 94: break;
          case 29:
            { out.append("/=");
            }
            // fall through
          case 95: break;
          case 30:
            { out.append("<=");
            }
            // fall through
          case 96: break;
          case 31:
            { out.append("==");
            }
            // fall through
          case 97: break;
          case 32:
            { out.append(">=");
            }
            // fall through
          case 98: break;
          case 33:
            { out.append("if");
            }
            // fall through
          case 99: break;
          case 34:
            { out.append("||");
            }
            // fall through
          case 100: break;
          case 35:
            { out.append("for");
            }
            // fall through
          case 101: break;
          case 36:
            { out.append("int");
            }
            // fall through
          case 102: break;
          case 37:
            { out.append("new");
            }
            // fall through
          case 103: break;
          case 38:
            { out.append("__df");
            }
            // fall through
          case 104: break;
          case 39:
            { out.append("bool");
            }
            // fall through
          case 105: break;
          case 40:
            { out.append("btoi");
            }
            // fall through
          case 106: break;
          case 41:
            { out.append("dtoi");
            }
            // fall through
          case 107: break;
          case 42:
            { out.append("else");
            }
            // fall through
          case 108: break;
          case 43:
            { out.append("itob");
            }
            // fall through
          case 109: break;
          case 44:
            { out.append("itod");
            }
            // fall through
          case 110: break;
          case 45:
            { out.append("null");
            }
            // fall through
          case 111: break;
          case 46:
            { out.append("this");
            }
            // fall through
          case 112: break;
          case 47:
            { out.append("true");
            }
            // fall through
          case 113: break;
          case 48:
            { out.append("void");
            }
            // fall through
          case 114: break;
          case 49:
            { out.append("Print");
            }
            // fall through
          case 115: break;
          case 50:
            { out.append("break");
            }
            // fall through
          case 116: break;
          case 51:
            { out.append("class");
            }
            // fall through
          case 117: break;
          case 52:
            { out.append("false");
            }
            // fall through
          case 118: break;
          case 53:
            { out.append("while");
            }
            // fall through
          case 119: break;
          case 54:
            { out.append("double");
            }
            // fall through
          case 120: break;
          case 55:
            { out.append("import");
            }
            // fall through
          case 121: break;
          case 56:
            { out.append("public");
            }
            // fall through
          case 122: break;
          case 57:
            { out.append("return");
            }
            // fall through
          case 123: break;
          case 58:
            { out.append("string");
            }
            // fall through
          case 124: break;
          case 59:
            { out.append("private");
            }
            // fall through
          case 125: break;
          case 60:
            { out.append("NewArray");
            }
            // fall through
          case 126: break;
          case 61:
            { out.append("ReadLine");
            }
            // fall through
          case 127: break;
          case 62:
            { out.append("__func__");
            }
            // fall through
          case 128: break;
          case 63:
            { out.append("__line__");
            }
            // fall through
          case 129: break;
          case 64:
            { out.append("continue");
            }
            // fall through
          case 130: break;
          case 65:
            { String definitionLine = yytext();
          definitionLine = definitionLine.trim();
          String[] newStr = definitionLine.split("\\s+");
          String definedWord = newStr[1];

          StringBuilder stringBuilder = new StringBuilder();
          for (int i = 2; i < newStr.length; i++) {
              stringBuilder.append(newStr[i]);
              stringBuilder.append(" ");
          }
          String definedValue = stringBuilder.toString();
          definedMap.put(definedWord,definedValue);
          out.append("__df");
            }
            // fall through
          case 131: break;
          case 66:
            { out.append("ReadInteger");
            }
            // fall through
          case 132: break;
          default:
            zzScanError(ZZ_NO_MATCH);
        }
      }
    }
  }

  /**
   * Runs the scanner on input files.
   *
   * This is a standalone scanner, it will print any unmatched
   * text to System.out unchanged.
   *
   * @param argv   the command line, contains the filenames to run
   *               the scanner on.
   */
  public  void main(String[] argv) {
    if (argv.length == 0) {
      System.out.println("Usage : java PreScanner [ --encoding <name> ] <inputfile(s)>");
    }
    else {
      int firstFilePos = 0;
      String encodingName = "UTF-8";
      if (argv[0].equals("--encoding")) {
        firstFilePos = 2;
        encodingName = argv[1];
        try {
          // Side-effect: is encodingName valid?
          java.nio.charset.Charset.forName(encodingName);
        } catch (Exception e) {
          System.out.println("Invalid encoding '" + encodingName + "'");
          return;
        }
      }
      for (int i = firstFilePos; i < argv.length; i++) {
        PreScanner scanner = null;
        try {
          java.io.FileInputStream stream = new java.io.FileInputStream(argv[i]);
          java.io.Reader reader = new java.io.InputStreamReader(stream, encodingName);
          scanner = new PreScanner(reader);
          while ( !scanner.zzAtEOF ) scanner.yylex();
        }
        catch (java.io.FileNotFoundException e) {
          System.out.println("File not found : \""+argv[i]+"\"");
        }
        catch (java.io.IOException e) {
          System.out.println("IO error scanning file \""+argv[i]+"\"");
          System.out.println(e);
        }
        catch (Exception e) {
          System.out.println("Unexpected exception:");
          e.printStackTrace();
        }
      }
    }
  }


}
