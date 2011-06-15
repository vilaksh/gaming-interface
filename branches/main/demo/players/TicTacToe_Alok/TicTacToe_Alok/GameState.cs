using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace TicTacToe
{
    public class GameState
    {
        private PType[,] board;
        public GameState()
        {
            board = new PType[3, 3];
            for (int i = 0; i < 3; i++)
            {
                for (int j = 0; j < 3; j++)
                {
                    board[i, j]  = PType.None;
                }
            }
        }

        public PType[,] Board
        {
            get
            {
                return board;
            }
        }
        public void playSelf(int x, int y)
        {
            board[x, y] = PType.Self;
        }

        public void playOpponent(int x, int y)
        {
            board[x, y] = PType.Opponent;
        }

        public void set(int x,int y, PType type)
        {
            board[x, y] = type;
        }
        public GameState makeCopy()
        {
            GameState copy = new GameState();
            for (int i = 0; i < 3; i++)
            {
                for (int j = 0; j < 3; j++)
                {
                    copy.set(i, j, board[i, j]);   
                }
            }
            return copy;
        }

        internal void drawBoard()
        {
            for (int i = 0; i < 3; i++)
            {
                for (int j = 0; j < 3; j++)
                {
                    if (board[i, j] == PType.Self)
                        System.Console.Write("X ");
                    else if (board[i, j] == PType.Opponent)
                        System.Console.Write("O ");
                    else
                        System.Console.Write("- ");
                }
                System.Console.WriteLine();
            }
            System.Console.WriteLine("**************************************");
        }
    }
}
