#include<iostream>

char board[3][3];

int valuepos(int row, int col)
{
    return 0;
}

bool willwin(char coin, int row, int col)
{
     int count;
     
     // for winning row wise
     count = 0;
     for(int i=0;i<3;i++)
      if(board[row][i] == coin)
       count ++;
       
     if(count == 2)
          return true;
     
      // for winning column wise
     count = 0;
     for(int i=0;i<3;i++)
      if(board[i][col] == coin)
       count ++;
       
     if(count == 2)
      return true;

      // verify if the cell falls on a diagonal
      if(row+col %2 != 0)
       return false;

     // for winning major diagonal wise     
     count = 0;
     for(int i=0;i<3;i++)
      if(board[i][i] == coin)
       count ++;
       
     if(count == 2)
      return true;

     // for winning minor diagonal wise     
     count = 0;
     for(int i=0;i<3;i++)
      if(board[i][2-i] == coin)
       count ++;
       
     if(count == 2)
      return true;
 
      return false;
}

int main()
{
    char myCoin, oppCoin, empty;
    bool gameover = false;
    char b;
    
    std::cin>>myCoin>>oppCoin>>empty;

    while(!gameover)
    {
        std::cin>>b;
        if(b == 'W' || b == 'L')
        {
            gameover = true;
            continue;        
        }
                
        std::cin>>b;
        for(int i=0;i<3;i++)
         for(int j=0;j<3;j++)
         {
                 board[i][j] = b;
                 std::cin>>b;
         }
        
        int maxvalue = 0;
        int rrow, rcol;
        for(int i=0;i<3;i++)
        for(int j=0;j<3;j++)
        {
            if(willwin(myCoin, i, j))
            {
           std::cout<<i<<j;
           break;
           } 
           else if(willwin(oppCoin, i, j))
           {
                std::cout<<i<<j;
                break;
                }
                else if(valuepos(i,j)>maxvalue)
                {
                     rrow = i;
                     rcol = j;
                     maxvalue = valuepos(i,j);
                     }
        }                                
        
    }
}



