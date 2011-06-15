using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace TicTacToe
{
    class Program
    {
        static void Main(string[] args)
        {
            TicTacToe game = new TicTacToe();
            
            int self = int.Parse(System.Console.ReadLine());
            int opp = int.Parse(System.Console.ReadLine());
            int none = int.Parse(System.Console.ReadLine());
            String status;
            while (true)
            {
                status = System.Console.ReadLine();

                if (!status.Equals("c"))
                    break;

                GameState state = new GameState();
                for (int i = 0; i < 3; i++)
                {
                    for (int j = 0; j < 3; j++)
                    {
                        int val = int.Parse(System.Console.ReadLine());
                        if (val == self)
                            state.playSelf(i, j);
                        else if (val == opp)
                            state.playOpponent(i, j);
                        else
                            state.set(i, j, PType.None);
                    }
                }

                game.setCurrentState(state);


                Move m = game.getNextMove();
                if (m.x == -1)
                    break;

                System.Console.WriteLine(m.x);
                System.Console.WriteLine(m.y);
            }
        }
    }
}
