using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace ConsoleApplication4
{
    class Program
    {
        static void Main(string[] args)
        {
            string status;
            Random rand = new Random();
            while (true)
            {
                status = System.Console.ReadLine();

                if (!status.Equals("c"))
                    break;

                for (int i = 0; i < 9; i++)
                {
                    string pos = System.Console.ReadLine();
                }

                System.Console.WriteLine(rand.Next(3).ToString());
                System.Console.WriteLine(rand.Next(3).ToString());
            }
        }
    }
}
