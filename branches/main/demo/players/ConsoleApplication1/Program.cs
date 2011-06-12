using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace ConsoleApplication1
{
    class Program
    {
        static void Main(string[] args)
        {
            string status;
            //int[] x = { 0, 1, 0, 1, 2 };
            //int[] y = { 0, 0, 2, 2, 1 };

            int[] x = { 1, 2, 0, 2 };
            int[] y = { 1, 0, 1, 2 };

            int k = 0;
            while (true)
            {

                status = System.Console.ReadLine();

                if (!status.Equals("c"))
                    break;

                for (int i = 0; i < 9; i++)
                {
                    string pos = System.Console.ReadLine();
                }

                System.Console.WriteLine(x[k]);
                System.Console.WriteLine(y[k]);
                k++;
            }
        }
    }
}
