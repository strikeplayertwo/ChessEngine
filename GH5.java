import javax.swing.*;

public class GH5 {
  public static void start(String[][] g) {
    for (int r = 0; r < 8; r++) {
      for (int c = 0; c < 8; c++) {
        g[r][c] = " ";
      }
    }
  }

  public static String format(String[][] g) {
    String output = "";
    for (int r = 0; r < g.length; r++) {
      for (int c = 0; c < g[0].length; c++) {
        output += " " + g[r][c] + " ";
        if (c != g[0].length - 1)
          output += "|";
      }
      if (r != g.length - 1)
        output += "\n--------------------------------\n";
    }
    return output;
  }

  static String[][] grid = new String[8][8];

  public static boolean pieceColor(String h, String color) {
    boolean b = false;
    if (color.equals("Black")) {
      if (h.equals("♚") || h.equals("♛") || h.equals("♜") || h.equals("♝") || h.equals("♟") || h.equals("♞")) {
        b = true;
      }
    } else {
      if (h.equals("♔") || h.equals("♕") || h.equals("♖") || h.equals("♗") || h.equals("♙") || h.equals("♘")) {
        b = true;
      }
    }
    return b;
  }

  public static int value(String p) {
    int v = 0;
    if (p.equals("♔") || p.equals("♚"))
      v = 20;
    if (p.equals("♕") || p.equals("♛"))
      v = 9;
    if (p.equals("♖") || p.equals("♜"))
      v = 5;
    if (p.equals("♗") || p.equals("♝") || p.equals("♘") || p.equals("♞"))
      v = 3;
    if (p.equals("♙") || p.equals("♟"))
      v = 1;
    return v;
  }

  static boolean debug = false;
  static int wc = 0;
  static int bc = 0;
  static boolean blrm = false;
  static boolean brrm = false;
  static boolean wlrm = false;
  static boolean wrrm = false;
  static boolean wkm = false;
  static boolean bkm = false;

  public static int attackValue(String a, String d, int s, String t) {
    int balance = 0, ac = 0, dc = 0, tdc = 0, tac = 0, value = 0, p;
    String worth = "Yes", h;
    String color = t;
    d = Integer.toString(s) + d;
    while (worth.equals("Yes")) {
      worth = "Maybe";
      ac = tac;
      dc = tdc;
      balance = 0;
      try {
        balance = balance + Character.getNumericValue(d.charAt(dc));
        dc = dc + 1;
      } catch (Exception e) {
        worth = "-";
        if (debug == true) {
          System.out.println("47");
        }
      }
      try {
        if (a.charAt(ac) != 'p') {
          try {
            balance = balance - Character.getNumericValue(a.charAt(ac));
            ac = ac + 1;
          } catch (Exception e) {
            worth = "No";
            if (debug == true) {
              System.out.println("48");
            }
            try {
            } catch (Exception g) {
              worth = "Yes";
              if (debug == true) {
                System.out.println("48b");
              }
            }
          }
        }
      } catch (Exception e) {
        worth = "No";
        if (debug == true) {
          System.out.println("49");
        }
      }
      if (t == color && tac == 0) {
        worth = "Yes";
      }
      while (worth.equals("Maybe")) {
        if (balance < 0) {
          try {
            balance = balance + Character.getNumericValue(d.charAt(dc));
            dc = dc + 1;
          } catch (Exception e) {
            worth = "Yes";
            if (debug == true) {
              System.out.println("50");
            }
          }
          if (balance < 0) {
            worth = "Yes";
            if (debug == true) {
              System.out.println("51");
            }
          } else {
            try {
              balance = balance - Character.getNumericValue(a.charAt(ac));
              ac = ac + 1;
            } catch (Exception e) {
              worth = "No";
              if (debug == true) {
                System.out.println("52");
              }
            }
          }
        } else {
          worth = "Yes";
          if (debug == true) {
            System.out.println("53");
          }
        }
      }
      if (worth.equals("Yes")) {
        if (t.equals(color)) {
          try {
            if (Character.getNumericValue(a.charAt(tac)) > 0) {
              value = value + Character.getNumericValue(d.charAt(tdc));
            }
            if (debug == true) {
              System.out.println(d.charAt(tdc) + " " + d);
            }
            tdc = tdc + 1;
          } catch (Exception e) {
            worth = "-";
            if (debug == true) {
              System.out.println("54");
            }
          }
        } else {
          try {
            value = value - Character.getNumericValue(d.charAt(tdc));
            if (debug == true) {
              System.out.println(d.charAt(tdc) + " " + d);
            }
            tdc = tdc + 1;
          } catch (Exception e) {
            worth = "-";
            if (debug == true) {
              System.out.println("55");
            }
          }
        }
      } else {
        if (tdc == 0 && tac == 0 && s == 0) {
          value = -1;
        }
      }
      if (debug == true) {
        System.out.println(value + "value" + a + "a" + d + "d" + ac + "ac" + dc + "dc" + balance + "balance" + tdc
            + "tdc" + tac + "tac");
      }
      if (t.equals("White")) {
        t = "Black";
      } else {
        t = "White";
      }
      h = a;
      a = d;
      d = h;
      p = ac;
      ac = dc;
      dc = p;
      p = tac;
      tac = tdc;
      tdc = p;
      balance = 0;
    }
    return value;
  }

  public static String compile(String input) {
    String str[] = { Character.toString(input.charAt(0)), Character.toString(input.charAt(1)),
        Character.toString(input.charAt(2)), Character.toString(input.charAt(3)), Character.toString(input.charAt(4)),
        Character.toString(input.charAt(5)), Character.toString(input.charAt(6)), Character.toString(input.charAt(7)),
        Character.toString(input.charAt(8)), Character.toString(input.charAt(9)) };
    String temp, g = "", g2 = "";
    for (int j = 11; j < input.length(); j++) {
      g2 = g2 + Character.toString(input.charAt(j));
    }
    for (int j = 0; j < str.length; j++) {
      for (int i = j + 1; i < str.length; i++) {
        if (str[i].compareTo(str[j]) < 0) {
          if (str[i].equals("2")) {
          } else {
            temp = str[j];
            str[j] = str[i];
            str[i] = temp;
          }
        } else if (str[j].equals("2")) {
          str[j] = str[i];
          str[i] = "2";
        }
      }
    }
    for (int j = 0; j < str.length; j++) {
      if (!(str[j].equals("8") || str[j].equals("0"))) {
        g = g + str[j];
      }
      if (str[j].equals("2")) {
        g = g + "0";
      }
    }
    g = g + g2;
    return g;
  }

  public static String captureStrings(int c, int r, String color) {
    String w = "", w2 = "";
    String bishop = "♝", king = "♚", knight = "♞", queen = "♛", rook = "♜", pawn = "♟";
    if (color.equals("Black")) {
      bishop = "♗";
      king = "♔";
      knight = "♘";
      queen = "♕";
      rook = "♖";
      pawn = "♙";
    }
    int c1f = c, c2f = r, c3f = 0;
    while ((c1f < 7) && (c2f < 7)
        && (grid[c1f][c2f] == " " || grid[c1f][c2f] == bishop || grid[c1f][c2f] == queen || (c1f == c && c2f == r))) {
      c1f = c1f + 1;
      c2f = c2f + 1;
      if (grid[c1f][c2f] == bishop || grid[c1f][c2f] == queen || (grid[c1f][c2f] == king && c1f == c + 1)) {
        if (c3f == 0) {
          w = w + Integer.toString(value(grid[c1f][c2f]));
        } else {
          w2 = w2 + Integer.toString(value(grid[c1f][c2f]));
        }
        c3f = c3f + 1;
      }
    }
    c1f = c;
    c2f = r;
    c3f = 0;
    while ((c1f < 7) && (c2f > 0)
        && (grid[c1f][c2f] == " " || grid[c1f][c2f] == bishop || grid[c1f][c2f] == queen || (c1f == c && c2f == r))) {
      c1f = c1f + 1;
      c2f = c2f - 1;
      if (grid[c1f][c2f] == bishop || grid[c1f][c2f] == queen || (grid[c1f][c2f] == king && c1f == c + 1)) {
        if (c3f == 0) {
          w = w + Integer.toString(value(grid[c1f][c2f]));
        } else {
          w2 = w2 + Integer.toString(value(grid[c1f][c2f]));
        }
        c3f = c3f + 1;
      }
    }
    c1f = c;
    c2f = r;
    c3f = 0;
    while ((c2f < 7) && (c1f > 0)
        && (grid[c1f][c2f] == " " || grid[c1f][c2f] == bishop || grid[c1f][c2f] == queen || (c1f == c && c2f == r))) {
      c1f = c1f - 1;
      c2f = c2f + 1;
      if (grid[c1f][c2f] == bishop || grid[c1f][c2f] == queen || (grid[c1f][c2f] == king && c1f == c - 1)) {
        if (c3f == 0) {
          w = w + Integer.toString(value(grid[c1f][c2f]));
        } else {
          w2 = w2 + Integer.toString(value(grid[c1f][c2f]));
        }
        c3f = c3f + 1;
      }
    }
    c1f = c;
    c2f = r;
    c3f = 0;
    while ((c1f > 0) && (c2f > 0)
        && (grid[c1f][c2f] == " " || grid[c1f][c2f] == bishop || grid[c1f][c2f] == queen || (c1f == c && c2f == r))) {
      c1f = c1f - 1;
      c2f = c2f - 1;
      if (grid[c1f][c2f] == bishop || grid[c1f][c2f] == queen || (grid[c1f][c2f] == king && c1f == c - 1)) {
        if (c3f == 0) {
          w = w + Integer.toString(value(grid[c1f][c2f]));
        } else {
          w2 = w2 + Integer.toString(value(grid[c1f][c2f]));
        }
        c3f = c3f + 1;
      }
    }
    c1f = c;
    c2f = r;
    c3f = 0;
    while ((c1f < 7)
        && (grid[c1f][c2f] == " " || grid[c1f][c2f] == rook || grid[c1f][c2f] == queen || (c1f == c && c2f == r))) {
      c1f = c1f + 1;
      if (grid[c1f][c2f] == rook || grid[c1f][c2f] == queen || (grid[c1f][c2f] == king && c1f == c + 1)) {
        if (c3f == 0) {
          w = w + Integer.toString(value(grid[c1f][c2f]));
        } else {
          w2 = w2 + Integer.toString(value(grid[c1f][c2f]));
        }
        c3f = c3f + 1;
      }
    }
    c1f = c;
    c3f = 0;
    while ((c1f > 0)
        && (grid[c1f][c2f] == " " || grid[c1f][c2f] == rook || grid[c1f][c2f] == queen || (c1f == c && c2f == r))) {
      c1f = c1f - 1;
      if (grid[c1f][c2f] == rook || grid[c1f][c2f] == queen || (grid[c1f][c2f] == king && c1f == c - 1)) {
        if (c3f == 0) {
          w = w + Integer.toString(value(grid[c1f][c2f]));
        } else {
          w2 = w2 + Integer.toString(value(grid[c1f][c2f]));
        }
        c3f = c3f + 1;
      }
    }
    c1f = c;
    c3f = 0;
    while ((c2f < 7)
        && (grid[c1f][c2f] == " " || grid[c1f][c2f] == rook || grid[c1f][c2f] == queen || (c1f == c && c2f == r))) {
      c2f = c2f + 1;
      if (grid[c1f][c2f] == rook || grid[c1f][c2f] == queen || (grid[c1f][c2f] == king && c2f == r + 1)) {
        if (c3f == 0) {
          w = w + Integer.toString(value(grid[c1f][c2f]));
        } else {
          w2 = w2 + Integer.toString(value(grid[c1f][c2f]));
        }
        c3f = c3f + 1;
      }
    }
    c2f = r;
    c3f = 0;
    while ((c2f > 0)
        && (grid[c1f][c2f] == " " || grid[c1f][c2f] == rook || grid[c1f][c2f] == queen || (c1f == c && c2f == r))) {
      c2f = c2f - 1;
      if (grid[c1f][c2f] == rook || grid[c1f][c2f] == queen || (grid[c1f][c2f] == king && c2f == r - 1)) {
        if (c3f == 0) {
          w = w + Integer.toString(value(grid[c1f][c2f]));
        } else {
          w2 = w2 + Integer.toString(value(grid[c1f][c2f]));
        }
        c3f = c3f + 1;
      }
    }
    c1f = c;
    c2f = r;
    if (color.equals("Black")) {
      if (c2f < 7 && c1f < 7) {
        if (grid[(c1f + 1)][(c2f + 1)] == pawn) {
          w = w + "1";
        }
      }
      if (c2f > 0 && c1f < 7) {
        if (grid[(c1f + 1)][(c2f - 1)] == pawn) {
          w = w + "1";
        }
      }
    } else {
      if (c1f > 0 && c2f < 7) {
        if (grid[(c1f - 1)][(c2f + 1)] == pawn) {
          w = w + "1";
        }
      }
      if (c1f > 0 && c2f > 0) {
        if (grid[(c1f - 1)][(c2f - 1)] == pawn) {
          w = w + "1";
        }
      }
    }
    c1f = c1f - 2;
    c2f = c2f + 1;
    if (c1f < 8 && c2f < 8 && c1f > -1 && c2f > -1) {
      if (grid[c1f][c2f] == knight) {
        w = w + Integer.toString(value(grid[c1f][c2f]));
      }
    }
    c1f = c1f + 1;
    c2f = c2f + 1;
    if (c1f < 8 && c2f < 8 && c1f > -1 && c2f > -1) {
      if (grid[c1f][c2f] == knight) {
        w = w + Integer.toString(value(grid[c1f][c2f]));
      }
    }
    c1f = c1f + 2;
    if (c1f < 8 && c2f < 8 && c1f > -1 && c2f > -1) {
      if (grid[c1f][c2f] == knight) {
        w = w + Integer.toString(value(grid[c1f][c2f]));
      }
    }
    c1f = c1f + 1;
    c2f = c2f - 1;
    if (c1f < 8 && c2f < 8 && c1f > -1 && c2f > -1) {
      if (grid[c1f][c2f] == knight) {
        w = w + Integer.toString(value(grid[c1f][c2f]));
      }
    }
    c2f = c2f - 2;
    if (c1f < 8 && c2f < 8 && c1f > -1 && c2f > -1) {
      if (grid[c1f][c2f] == knight) {
        w = w + Integer.toString(value(grid[c1f][c2f]));
      }
    }
    c1f = c1f - 1;
    c2f = c2f - 1;
    if (c1f < 8 && c2f < 8 && c1f > -1 && c2f > -1) {
      if (grid[c1f][c2f] == knight) {
        w = w + Integer.toString(value(grid[c1f][c2f]));
      }
    }
    c1f = c1f - 2;
    if (c1f < 8 && c2f < 8 && c1f > -1 && c2f > -1) {
      if (grid[c1f][c2f] == knight) {
        w = w + Integer.toString(value(grid[c1f][c2f]));
      }
    }
    c1f = c1f - 1;
    c2f = c2f + 1;
    if (c1f < 8 && c2f < 8 && c1f > -1 && c2f > -1) {
      if (grid[c1f][c2f] == knight) {
        w = w + Integer.toString(value(grid[c1f][c2f]));
      }
    }
    while (w.length() < 10) {
      w = w + "8";
    }
    if (w2.length() > 0) {
      w = w + "t" + w2;
    }
    return w;
  }

  public static boolean checkValid(int cf, int rf, int ct, int rt, int kc, int kr, String color) {
    boolean validMove = false;
    String piece = grid[cf][rf];
    boolean c1 = false, c2 = false, c3 = false;
    int c1f = 0, c2f = 0, c3f = 0, c4f = 0;
    String bishop = "♝", king = "♚", knight = "♞", queen = "♛", rook = "♜", pawn = "♟", nbishop = "♗", nking = "♔",
        nknight = "♘", nqueen = "♕", nrook = "♖", npawn = "♙";
    if (color.equals("White")) {
      bishop = "♗";
      king = "♔";
      knight = "♘";
      queen = "♕";
      rook = "♖";
      pawn = "♙";
      nbishop = "♝";
      nking = "♚";
      nknight = "♞";
      nqueen = "♛";
      nrook = "♜";
      npawn = "♟";
    }
    if (piece == pawn) {
      c1f = cf + 1;
      if (color.equals("White")) {
        c1f = cf - 1;
      }
      if (c1f == ct && rf == rt) {
        if (grid[ct][rt] == " ") {
          c1 = true;
        }
      }
      c1f = cf + 2;
      if (color.equals("White")) {
        c1f = cf - 2;
      }
      c3f = 1;
      if (color.equals("White")) {
        c3f = 6;
      }
      c4f = cf + 1;
      if (color.equals("White")) {
        c4f = cf - 1;
      }
      if (c1f == ct && rf == rt && cf == c3f) {
        if (grid[ct][rt] == " ") {
          c2 = true;
          if (grid[c4f][rt] != " ") {
            c2 = false;
          }
        }
      }
      c1f = rf - 1;
      c2f = rf + 1;
      if (rt == c2f || rt == c1f) {
        c1f = cf + 1;
        if (color.equals("White")) {
          c1f = cf - 1;
        }
        if (ct == c1f) {
          if (grid[ct][rt] == nking || grid[ct][rt] == npawn || grid[ct][rt] == nrook || grid[ct][rt] == nknight
              || grid[ct][rt] == nbishop || grid[ct][rt] == nqueen) {
            c3 = true;
          }
        }
      }
      if (c1 == true || c2 == true || c3 == true) {
        validMove = true;
      }
    } else if (piece == knight) {
      c1f = rf + 1;
      c2f = rf - 1;
      c3f = cf + 2;
      c4f = cf - 2;
      if ((ct == c3f || ct == c4f) && (rt == c1f || rt == c2f)) {
        c1 = true;
      }
      c1f = rf + 2;
      c2f = rf - 2;
      c3f = cf + 1;
      c4f = cf - 1;
      if ((ct == c3f || ct == c4f) && (rt == c1f || rt == c2f)) {
        c2 = true;
      }
      if (c1 == true || c2 == true) {
        validMove = true;
      }
    } else if (piece == bishop) {
      c1f = Math.abs(ct - cf);
      c2f = Math.abs(rt - rf);
      if (c1f != 0) {
        if (c1f == c2f) {
          validMove = true;
          c3f = 1;
          if (ct == cf) {
            c3f = 0;
          }
          if (ct < cf) {
            c3f = -1;
          }
          c4f = 1;
          if (rt == rf) {
            c4f = 0;
          }
          if (rt < rf) {
            c4f = -1;
          }
          c1f = cf;
          c2f = rf;
          for (int m = 1; m < (Math.abs(ct - cf)); m++) {
            c1f = c1f + c3f;
            c2f = c2f + c4f;
            if (grid[Math.abs(c1f)][Math.abs(c2f)] != " ") {
              validMove = false;
              // System.out.println("1");
            } // end moving through o♘ piece
          }
        }
      }
    } else if (piece == queen) {
      c1f = Math.abs(cf - ct);
      c2f = Math.abs(rf - rt);
      if (c1f != 0) {
        if (c1f == c2f) {
          c1 = true;
        }
      }
      if (rf == rt && cf != ct) {
        c2 = true;
      }
      if (cf == ct && rf != rt) {
        c3 = true;
      }
      if (c1 == true || c2 == true || c3 == true) {
        validMove = true;
        c3f = 1;
        if (ct == cf) {
          c3f = 0;
        }
        if (ct < cf) {
          c3f = -1;
        }
        c4f = 1;
        if (rt == rf) {
          c4f = 0;
        }
        if (rt < rf) {
          c4f = -1;
        }
        c1f = cf;
        c2f = rf;
        if (Math.abs(ct - cf) < Math.abs(rt - rf)) {
          for (int m = 1; m < (Math.abs(rt - rf)); m++) {
            c1f = c1f + c3f;
            c2f = c2f + c4f;
            if (grid[Math.abs(c1f)][Math.abs(c2f)] != " ") {
              validMove = false;
              // System.out.println("2");
            } // end moving through o♘ piece
          }
        } else {
          for (int m = 1; m < (Math.abs(ct - cf)); m++) {
            c1f = c1f + c3f;
            c2f = c2f + c4f;
            if (grid[Math.abs(c1f)][Math.abs(c2f)] != " ") {
              validMove = false;
              // System.out.println("3");
            } // end moving through o♘ piece
          }
        }
      }
    } else if (piece == king) {
      c1f = Math.abs(cf - ct);
      c2f = Math.abs(rf - rt);
      if (c1f != 0) {
        if (c1f == c2f) {
          c1 = true;
        }
      }
      if (rf == rt && cf != ct) {
        c2 = true;
      }
      if (cf == ct && rf != rt) {
        c3 = true;
      }
      c1f = 0;
      if (color.equals("White")) {
        c1f = 7;
      }
      if (cf == c1f && rf == 4) {
        if (rt == 6 && ct == c1f) {
          if (grid[c1f][5] == " " && grid[c1f][6] == " " && grid[c1f][7] == rook) {
            c2f = 0;
            if (color.equals("White")) {
              if (wkm == false && wrrm == false) {
                c2f = 1;
              }
            } else {
              if (bkm == false && brrm == false) {
                c2f = 1;
              }
            }
            if (c2f == 1) {
              validMove = true;
              for (int g = 4; g < 7; g++) {
                if (!(captureStrings(c1f, g, color).equals("8888888888"))) {
                  validMove = false;
                }
              }
              if (validMove == true) {
                /*
                 * if (color.equals("White")){
                 * wc = 6;
                 * }else{
                 * if (rt == 6 && ct == 0){
                 * bc = 6;
                 * }
                 * }
                 */
              }
            }
          }
        }
        if (rt == 2 && ct == c1f) {
          if (grid[c1f][3] == " " && grid[c1f][2] == " " && grid[c1f][1] == " " && grid[c1f][0] == rook) {
            c2f = 0;
            if (color.equals("White")) {
              if (wkm == false && wlrm == false) {
                c2f = 1;
              }
            } else {
              if (bkm == false && blrm == false) {
                c2f = 1;
              }
            }
            if (c2f == 1) {
              validMove = true;
              for (int g = 1; g < 5; g++) {
                if (!(captureStrings(c1f, g, color).equals("8888888888"))) {
                  validMove = false;
                }
              }
              if (validMove == true) {
                /*
                 * if (color.equals("White")){
                 * wc = 2;
                 * }else{
                 * bc = 2;
                 * }
                 */
              }
            }
          }
        }
      }
      if (c1 == true || c2 == true || c3 == true) {
        c1f = Math.abs(cf - ct);
        c2f = Math.abs(rf - rt);
        if (c1f < 2 && c2f < 2) {
          validMove = true;
        }
      }
    } else if (piece == rook) {
      if (rf == rt && cf != ct) {
        c1 = true;
      }
      if (cf == ct && rf != rt) {
        c2 = true;
      }
      if (c1 == true || c2 == true) {
        validMove = true;
        c3f = 1;
        if (ct == cf) {
          c3f = 0;
        }
        if (ct < cf) {
          c3f = -1;
        }
        c4f = 1;
        if (rt == rf) {
          c4f = 0;
        }
        if (rt < rf) {
          c4f = -1;
        }
        c1f = cf;
        c2f = rf;
        if (Math.abs(ct - cf) < Math.abs(rt - rf)) {
          for (int m = 1; m < (Math.abs(rt - rf)); m++) {
            c1f = c1f + c3f;
            c2f = c2f + c4f;
            if (grid[Math.abs(c1f)][Math.abs(c2f)] != " ") {
              validMove = false;
              // System.out.println("76");
            } // end moving through o♘ piece
          }
        } else {
          for (int m = 1; m < (Math.abs(ct - cf)); m++) {
            c1f = c1f + c3f;
            c2f = c2f + c4f;
            if (grid[Math.abs(c1f)][Math.abs(c2f)] != " ") {
              validMove = false;
              // System.out.println("77");
            } // end moving through o♘ piece
          }
        }
      }
    }
    if (grid[ct][rt] == king || grid[ct][rt] == pawn || grid[ct][rt] == rook || grid[ct][rt] == knight
        || grid[ct][rt] == bishop || grid[ct][rt] == queen) {
      validMove = false;
      // System.out.println("78");
    }
    String pholder = grid[ct][rt];
    grid[ct][rt] = piece;
    grid[cf][rf] = " ";
    if (grid[ct][rt] == king) {
      kc = ct;
      kr = rt;
    }
    if (validMove == true) {
      if (!(captureStrings(kc, kr, color).equals("8888888888"))) {
        validMove = false;
      }
    }
    if (piece == king) {
      kc = cf;
      kr = rf;
    }
    grid[ct][rt] = pholder;
    grid[cf][rf] = piece;
    if (grid[cf][rf] == king) {
      kc = ct;
      kr = rt;
    }
    return validMove;
  }

  public static int askSquare(String message) {
    int gg = 0;
    boolean valid = false;
    String input;
    while (valid == false) {
      input = JOptionPane.showInputDialog(message);
      try {
        gg = Integer.parseInt(input);
        valid = true;
        if (gg < 0 || gg > 7) {
          valid = false;
        }
      } catch (Exception e) {
      }
    }
    return gg;
  }

  public static void main(String[] args) {
    start(grid);
    // System.out.println(attackValue("1","",5,"Black"));
    grid[0][0] = "♜";
    grid[0][1] = "♞";
    grid[0][2] = "♝";
    grid[0][3] = "♛";
    grid[0][4] = "♚";
    grid[0][5] = "♝";
    grid[0][6] = "♞";
    grid[0][7] = "♜";
    grid[1][0] = "♟";
    grid[1][1] = "♟";
    grid[1][2] = "♟";
    grid[1][3] = "♟";
    grid[1][4] = "♟";
    grid[1][5] = "♟";
    grid[1][6] = "♟";
    grid[1][7] = "♟";
    grid[7][0] = "♖";
    grid[7][1] = "♘";
    grid[7][2] = "♗";
    grid[7][3] = "♕";
    grid[7][4] = "♔";
    grid[7][5] = "♗";
    grid[7][6] = "♘";
    grid[7][7] = "♖";
    grid[6][0] = "♙";
    grid[6][1] = "♙";
    grid[6][2] = "♙";
    grid[6][3] = "♙";
    grid[6][4] = "♙";
    grid[6][5] = "♙";
    grid[6][6] = "♙";
    grid[6][7] = "♙";
    int bkc = 0, bkr = 4, wkc = 7, wkr = 4, cf = 6, rf = 4, ct = 4, rt = 4, number = 1, c1f = 0, c2f = 0, zf = 0,
        zp = 0, zr = 0, z2f = 0, z2r = 0, z2mr = 0, zmr = 0, zmf = 0, oz2r = 0, ort = 0, oct = 0, orf = 0, ocf = 0,
        hs = 0, h2s = 0, pct = 0, prt = 0, pcf = 0, prf = 0, fpcf = 0, fprf = 0, fpct = 0, fprt = 0, focf = 0, foct = 0,
        fort = 0, forf = 0;
    String gameOver = "no", color = "White", notColor = "Black", piece = "♙", pholder, hv, h2v, h3v = " ", h4v = " ";
    boolean validMove = true, p1 = false, p2 = false, p = true;
    while (gameOver == "no") {
      System.out.println(format(grid));
      if (number % 2 == 0) {
        color = "Black";
        notColor = "White";
        p = p2;
      } else {
        color = "White";
        notColor = "Black";
        p = p1;
      }
      System.out.println(color + "'s turn to move. It is move " + number + ".");
      if (p == true) {
        validMove = false;
      } else {
        validMove = true;
      }
      while (validMove == false) {
        rf = askSquare("What column is the piece you wish to move on? Starts from 0 and goes to 7.");
        cf = askSquare("What row is the piece you wish to move on? Starts from 0 and goes to 7.");
        rt = askSquare("What column do you want to move your piece to? Starts from 0 and goes to 7.");
        ct = askSquare("What row do you want to move your piece to? Starts from 0 and goes to 7.");
        if (color.equals("White")) {
          c1f = wkc;
          c2f = wkr;
        } else {
          c1f = bkc;
          c2f = bkr;
        }
        validMove = checkValid(cf, rf, ct, rt, c1f, c2f, color);
        if (validMove == false) {
          System.out.println("Your move is invalid. Please try again.");
        }
      }
      if (p == false) {
        zmr = -100;
        z2mr = 0;
        for (int mf = 0; mf < 64; mf++) {
          if (pieceColor(grid[mf / 8][mf % 8], color) == true) {
            for (int mt = 0; mt < 64; mt++) {
              if (color.equals("White")) {
                c1f = wkc;
                c2f = wkr;
              } else {
                c1f = bkc;
                c2f = bkr;
              }
              if (checkValid(mf / 8, mf % 8, mt / 8, mt % 8, c1f, c2f, color) == true) {
                if (pieceColor(grid[mt / 8][mt % 8], notColor) == true) {
                  zmf = value(grid[mt / 8][mt % 8]);
                } else {
                  zmf = 0;
                }
                if (color.equals("White")) {
                  color = "Black";
                  notColor = "White";
                } else {
                  notColor = "Black";
                  color = "White";
                }
                hv = grid[mt / 8][mt % 8];
                hs = mt;
                h2v = grid[mf / 8][mf % 8];
                h2s = mf;
                grid[mt / 8][mt % 8] = grid[mf / 8][mf % 8];
                grid[mf / 8][mf % 8] = " ";
                if (grid[mt / 8][mt % 8] == "♚") {
                  bkc = mt / 8;
                  bkr = mt % 8;
                }
                if (grid[mt / 8][mt % 8] == "♔") {
                  wkc = mt / 8;
                  wkr = mt % 8;
                }
                zr = -50;
                z2r = 0;
                for (int q = 0; q < 64; q++) {
                  if (pieceColor(grid[q / 8][q % 8], color) == true) {
                    for (int s = 0; s < 64; s++) {
                      if (color.equals("White")) {
                        c1f = wkc;
                        c2f = wkr;
                      } else {
                        c1f = bkc;
                        c2f = bkr;
                      }
                      if (checkValid(q / 8, q % 8, s / 8, s % 8, c1f, c2f, color) == true) {
                        if (pieceColor(grid[s / 8][s % 8], notColor) == true) {
                          zf = value(grid[s / 8][s % 8]);
                        } else {
                          zf = 0;
                        }
                        pholder = grid[s / 8][s % 8];
                        grid[s / 8][s % 8] = grid[q / 8][q % 8];
                        grid[q / 8][q % 8] = " ";
                        for (int x = 0; x < 64; x++) {
                          if (pieceColor(grid[x / 8][x % 8], color) == true) {
                            zp = attackValue(compile(captureStrings(x / 8, x % 8, color)),
                                compile(captureStrings(x / 8, x % 8, notColor)), value(grid[x / 8][x % 8]), color);
                            if (zp > 0) {
                              z2f = z2f - zp;
                            }
                          }
                        }
                        if (zf > zr) {
                          zr = zf;
                          ocf = q / 8;
                          orf = q % 8;
                          oct = s / 8;
                          ort = s % 8;
                          z2r = 0;
                          for (int u = 0; u < 64; u++) {
                            if (pieceColor(grid[u / 8][u % 8], notColor) == true) {
                              zp = attackValue(compile(captureStrings(u / 8, u % 8, notColor)),
                                  compile(captureStrings(u / 8, u % 8, color)), value(grid[u / 8][u % 8]), color);
                              if (zp > 0) {
                                z2r = z2r + zp;
                              }
                            }
                          }
                        } else if (zf == zr) {
                          z2f = 0;
                          for (int u = 0; u < 64; u++) {
                            if (pieceColor(grid[u / 8][u % 8], notColor) == true) {
                              zp = attackValue(compile(captureStrings(u / 8, u % 8, notColor)),
                                  compile(captureStrings(u / 8, u % 8, color)), value(grid[u / 8][u % 8]), color);
                              if (zp > 0) {
                                z2f = z2f + zp;
                              }
                            }
                          }
                          if (z2f > z2r) {
                            zr = zf;
                            z2r = z2f;
                            ocf = q / 8;
                            orf = q % 8;
                            oct = s / 8;
                            ort = s % 8;
                          }
                        }
                        grid[q / 8][q % 8] = grid[s / 8][s % 8];
                        grid[s / 8][s % 8] = pholder;
                        if (grid[q / 8][q % 8] == "♚") {
                          bkc = q / 8;
                          bkr = q % 8;
                        }
                        if (grid[q / 8][q % 8] == "♔") {
                          wkc = q / 8;
                          wkr = q % 8;
                        }
                      }
                    }
                  }
                }
                oz2r = z2r;
                if (pieceColor(grid[oct][ort], notColor) == true) {
                  zmf = zmf - value(grid[oct][ort]);
                }
                if (zr == -50) {
                  if (color.equals("White")) {
                    c1f = bkc;
                    c2f = bkr;
                  } else {
                    c1f = wkc;
                    c2f = wkr;
                  }
                  if (captureStrings(c1f, c2f, notColor).equals("8888888888")) {
                    zmf = -49;
                  } else {
                    zmf = 100;
                  }
                } else {
                  h3v = grid[oct][ort];
                  h4v = grid[ocf][orf];
                  grid[oct][ort] = grid[ocf][orf];
                  grid[ocf][orf] = " ";
                  if (grid[oct][ort] == "♚") {
                    bkc = oct;
                    bkr = ort;
                  }
                  if (grid[oct][ort] == "♔") {
                    wkc = oct;
                    wkr = ort;
                  }
                }
                if (color.equals("White")) {
                  color = "Black";
                  notColor = "White";
                } else {
                  notColor = "Black";
                  color = "White";
                } // end simulated notColor move
                zr = -50;
                z2r = 0;
                for (int q = 0; q < 64; q++) {
                  if (pieceColor(grid[q / 8][q % 8], color) == true) {
                    for (int s = 0; s < 64; s++) {
                      if (color.equals("White")) {
                        c1f = wkc;
                        c2f = wkr;
                      } else {
                        c1f = bkc;
                        c2f = bkr;
                      }
                      if (checkValid(q / 8, q % 8, s / 8, s % 8, c1f, c2f, color) == true) {
                        if (pieceColor(grid[s / 8][s % 8], notColor) == true) {
                          zf = value(grid[s / 8][s % 8]);
                        } else {
                          zf = 0;
                        }
                        pholder = grid[s / 8][s % 8];
                        grid[s / 8][s % 8] = grid[q / 8][q % 8];
                        grid[q / 8][q % 8] = " ";
                        for (int x = 0; x < 64; x++) {
                          if (pieceColor(grid[x / 8][x % 8], color) == true) {
                            zp = attackValue(compile(captureStrings(x / 8, x % 8, color)),
                                compile(captureStrings(x / 8, x % 8, notColor)), value(grid[x / 8][x % 8]), color);
                            if (zp > 0) {
                              zf = zf - zp;
                            }
                          }
                        }
                        if (zf > zr) {
                          zr = zf;
                          z2r = 0;
                          for (int u = 0; u < 64; u++) {
                            if (pieceColor(grid[u / 8][u % 8], notColor) == true) {
                              zp = attackValue(compile(captureStrings(u / 8, u % 8, notColor)),
                                  compile(captureStrings(u / 8, u % 8, color)), value(grid[u / 8][u % 8]), color);
                              if (zp > 0) {
                                z2r = z2r + zp;
                              }
                            }
                          }
                          pcf = q / 8;
                          prf = q % 8;
                          pct = s / 8;
                          prt = s % 8;
                        } else if (zf == zr) {
                          z2f = 0;
                          for (int u = 0; u < 64; u++) {
                            if (pieceColor(grid[u / 8][u % 8], notColor) == true) {
                              zp = attackValue(compile(captureStrings(u / 8, u % 8, notColor)),
                                  compile(captureStrings(u / 8, u % 8, color)), value(grid[u / 8][u % 8]), color);
                              if (zp > 0) {
                                z2f = z2f + zp;
                              }
                            }
                          }
                          if (z2f > z2r) {
                            zr = zf;
                            z2r = z2f;
                            pcf = q / 8;
                            prf = q % 8;
                            pct = s / 8;
                            prt = s % 8;
                          }
                        }
                        grid[q / 8][q % 8] = grid[s / 8][s % 8];
                        grid[s / 8][s % 8] = pholder;
                        if (grid[q / 8][q % 8] == "♚") {
                          bkc = q / 8;
                          bkr = q % 8;
                        }
                        if (grid[q / 8][q % 8] == "♔") {
                          wkc = q / 8;
                          wkr = q % 8;
                        }
                      }
                    }
                  }
                } // end simulated color move
                zmf = zmf + zr;
                // z2r = z2r - oz2r;
                if (zr == -50) {
                  zmf = -200;
                }
                if (zmf > zmr) {
                  zmr = zmf;
                  cf = mf / 8;
                  rf = mf % 8;
                  ct = mt / 8;
                  rt = mt % 8;
                  z2mr = z2r;
                  fpcf = pcf;
                  fprf = prf;
                  fpct = pct;
                  fprt = prt;
                  focf = ocf;
                  forf = orf;
                  foct = oct;
                  fort = ort;
                } else if (zmf == zmr) {
                  if (z2r > z2mr) {
                    z2mr = z2r;
                    cf = mf / 8;
                    rf = mf % 8;
                    ct = mt / 8;
                    rt = mt % 8;
                    fpcf = pcf;
                    fprf = prf;
                    fpct = pct;
                    fprt = prt;
                    focf = ocf;
                    forf = orf;
                    foct = oct;
                    fort = ort;
                  } else if (z2r == z2mr) {
                    /*
                     * if (Math.random() > 0.8){
                     * cf = mf/8;
                     * rf = mf%8;
                     * ct = mt/8;
                     * rt = mt%8;
                     * fpcf = pcf;
                     * fprf = prf;
                     * fpct = pct;
                     * fprt = prt;
                     * focf = ocf;
                     * forf = orf;
                     * foct = oct;
                     * fort = ort;
                     * }
                     */
                    // above: randomization
                  }
                }
                grid[ocf][orf] = h4v;
                grid[oct][ort] = h3v;
                grid[h2s / 8][h2s % 8] = h2v;
                grid[hs / 8][hs % 8] = hv;
                if (grid[ocf][orf] == "♚") {
                  bkc = ocf;
                  bkr = orf;
                }
                if (grid[ocf][orf] == "♔") {
                  wkc = ocf;
                  wkr = orf;
                }
                if (grid[h2s / 8][h2s % 8] == "♚") {
                  bkc = h2s / 8;
                  bkr = h2s % 8;
                }
                if (grid[h2s / 8][h2s % 8] == "♔") {
                  wkc = h2s / 8;
                  wkr = h2s % 8;
                }
              }
            }
          }
        }
        // if (debug == true){
        System.out.println(zmr + " " + z2mr);
        System.out.println(grid[cf][rf] + "" + rt + "" + ct);
        System.out.println(grid[focf][forf] + "" + fort + "" + foct);
        System.out.println(grid[fpcf][fprf] + "" + fprt + "" + fpct);
        // }
      }
      if (grid[cf][rf] == "♚") {
        bkc = ct;
        bkr = rt;
      }
      if (grid[cf][rf] == "♔") {
        wkc = ct;
        wkr = rt;
      }
      if (grid[ct][rt] == "♚") {
        gameOver = "White";
      }
      if (grid[ct][rt] == "♔") {
        gameOver = "Black";
      }
      grid[ct][rt] = grid[cf][rf];
      if (grid[ct][rt] == "♙" && ct == 0) {
        grid[ct][rt] = "♕";
      }
      if (grid[ct][rt] == "♟" && ct == 7) {
        grid[ct][rt] = "♛";
      }
      if (piece == "♔") {
        wkm = true;
      }
      if (piece == "♚") {
        bkm = true;
      }
      if (piece == "♖") {
        if (cf == 7 && rf == 0) {
          wlrm = true;
        }
        if (cf == 7 && rf == 7) {
          wrrm = true;
        }
      }
      if (piece == "♜") {
        if (cf == 0 && rf == 0) {
          blrm = true;
        }
        if (cf == 0 && rf == 7) {
          brrm = true;
        }
      }
      grid[cf][rf] = " ";
      if (grid[ct][rt] == "♔" && rt == 2 && rf == 4) {
        grid[7][0] = " ";
        grid[7][3] = "♖";
        wc = 0;
      }
      if (grid[ct][rt] == "♔" && rt == 6 && rf == 4) {
        grid[7][7] = " ";
        grid[7][5] = "♖";
        wc = 0;
      }
      if (grid[ct][rt] == "♚" && rt == 2 && rf == 4) {
        grid[0][0] = " ";
        grid[0][3] = "♜";
        bc = 0;
      }
      if (grid[ct][rt] == "♚" && rt == 6 && rf == 4) {
        grid[0][7] = " ";
        grid[0][5] = "♜";
        bc = 0;
      }
      number = number + 1;
    } // end while game is played
    System.out.println("Game over! The winner of the game is " + gameOver + ".");
  }
}
