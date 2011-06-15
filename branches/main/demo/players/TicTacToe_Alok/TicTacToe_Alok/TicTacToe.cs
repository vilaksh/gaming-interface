using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace TicTacToe
{
    public class TicTacToe
    {
        GameState state;
        public TicTacToe()
        {
            state = new GameState();
        }

        public void setCurrentState(GameState a_state)
        {
            state = a_state;
        }

        public void playOpponent(int x, int y)
        {
            state.playOpponent(x, y);
        }

        public void playSelf(int x, int y)
        {
            state.playSelf(x, y);
        }

        public Move getNextMove()
        {
            Move final = new Move();
            final.x = -1;
            final.y = -1;
            int maxValue = -100;
            List<Move> moves = Utils.getPossibleMoves(state);
            
            foreach (Move m in moves)
            {
                int val = Min(Utils.Result(m,state));
                //System.Console.WriteLine(m.x + " " + m.y + val);
                if(val > maxValue)
                {
                    final = m;
                    maxValue = val;
                }
            }

            return final;
        }

        private int Max(GameState state)
        {
            int t = Utils.TerminalTest(state);
            if (t != -2)
            {
                //System.Console.WriteLine("RRRRRRRRRRRRRRRRRR");
                //state.drawBoard();
                //System.Console.WriteLine("RRRRRRRRRRRRRRRRRR");
                return t;
            }
            int v = -100;
            foreach (GameState s in Utils.Successors(state,PType.Self))
            {
                int tv = Min(s);
                if (tv > v)
                    v = tv;
            }
            return v;
        }

        private int Min(GameState state)
        {
            int t = Utils.TerminalTest(state);
            if (t != -2)
            {
                //System.Console.WriteLine("wwwwwwwwwwwwwwwwwwww");
                //state.drawBoard();
                //System.Console.WriteLine("wwwwwwwwwwwwwwwwwwww:" + t);
                return t;
            }
            int v = 100;
            foreach (GameState s in Utils.Successors(state, PType.Opponent))
            {
                int tv = Max(s);
                if (tv < v)
                    v = tv;
            }
            return v;
        }

        internal void drawBoard()
        {
            state.drawBoard();
        }

        internal bool isGameOver()
        {
            if (Utils.TerminalTest(state) != -2)
                return true;
            return false;
        }
    }
}
