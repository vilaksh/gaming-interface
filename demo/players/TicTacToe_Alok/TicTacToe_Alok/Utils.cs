using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace TicTacToe
{
    public enum PType
    {
        Self,
        Opponent,
        None
    }
    public struct Move
    {
        public int x;
        public int y;
    }
    public class Utils
    {
        public static List<Move> getPossibleMoves(GameState state)
        {
            PType[,] t = state.Board;
            List<Move> allMoves = new List<Move>();
            if (TerminalTest(state) != -2) return allMoves;
            for (int i = 0; i < 3; i++)
            {
                for (int j = 0; j < 3; j++)
                {
                    if (t[i, j] == PType.None)
                    {
                        Move m = new Move();
                        m.x = i;
                        m.y = j;
                        allMoves.Add(m);
                    }
                }
            }
            return allMoves;
        }

        public static GameState Result(Move m, GameState currentState)
        {
            GameState newState = currentState.makeCopy();
            newState.playSelf(m.x, m.y);
            return newState;
        }
        public static int TerminalTest(GameState state)
        {
            
            PType[,] t = state.Board;
            
            //rows
            for (int i = 0; i < 3; i++)
            {
                if (t[i, 0] != PType.None && t[i, 0] == t[i, 1] && t[i, 1] == t[i, 2])
                {
                    if (t[i, 0] == PType.Self)
                        return 1;
                    else
                        return -1;
                }
            }

            //cols
            for (int i = 0; i < 3; i++)
            {
                if (t[0, i] != PType.None && t[0, i] == t[1, i] && t[1, i] == t[2,i])
                {
                    if (t[0, i] == PType.Self)
                        return 1;
                    else
                        return -1;
                }
            }

            //main diagnoal
            if (t[0, 0] != PType.None && t[0, 0] == t[1, 1] && t[1, 1] == t[2, 2])
            {
                if (t[0, 0] == PType.Self)
                    return 1;
                else
                    return -1;
            }

            //other diagnoal
            if (t[2, 0] != PType.None && t[2, 0] == t[1, 1] && t[1, 1] == t[0, 2])
            {
                if (t[2, 0] == PType.Self)
                    return 1;
                else
                    return -1;
            }


            bool allFilled = true;
            for (int i = 0; i < 3; i++)
            {
                for (int j = 0; j < 3; j++)
                {
                    if (t[i, j] == PType.None)
                    {
                        allFilled = false;
                        break;
                    }
                }
            }

            if (allFilled)
                return 0;

            return -2;  //-2 means continue..

        }

        internal static List<GameState> Successors(GameState state,PType playerTurn)
        {
            List<Move> allMoves = getPossibleMoves(state);
            List<GameState> allStates = new List<GameState>();
            
            foreach (Move m in allMoves)
            {
                GameState newState = state.makeCopy();
                newState.set(m.x, m.y, playerTurn);
                allStates.Add(newState);
            }

            return allStates;
        }
    }
}
