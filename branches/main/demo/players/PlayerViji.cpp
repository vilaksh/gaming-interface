#include<iostream>

char board[3][3];
char myCoin, oppCoin, empty;
    
int valuepos(int row, int col)
{
    if(row == 1 && col == 1)
     return 50;

     int val = 0;

     if((row+col)%2 ==0)
      val++;
     
      for(int i=0;i<3;i++)
      {
           if(board[row][i] == empty || board[row][i] == myCoin)
               val+=2;
           else
           {
               val = val - i*2;
               break;
            }
       }

      for(int i=0;i<3;i++)
      {
          if(board[i][col] == empty || board[i][col] == myCoin)
                val+=2;
          else
          {
              val = val - i*2;
              break;
           }
      }
      
      if( (row+col)%2 != 0)
      {//std::cout<<"Value of"<<row<<" "<<col<<" "<<val<<"\n";
       return val;}

     // for winning major diagonal wise   
   if(row == col)
   {  
    for(int i=0;i<3;i++)
    {
      if(board[i][i] == myCoin || board[i][i] == empty)
       val+=2;

       else
       {
           val = val-i*2;
           break;
       }
    }
    }
    else
    {   

     // for winning minor diagonal wise     
    for(int i=0;i<3;i++)
    {
      if(board[i][2-i] == myCoin || board[i][2-i] == empty)
       val+=2;

       else
       {
            val = val - i*2;
            break;
        }
    }  
    }
    
    //std::cout<<"Value of"<<row<<" "<<col<<" "<<val<<"\n";
    return val;
      
}

bool willwin(char coin, int row, int col)
{
     int count;
     
     // for winning row wise
     count = 0;
     for(int i=0;i<3;i++)
      if(board[row][i] == coin)
        count++;
       else if(board[row][i] != empty)
        {count = 0; break;}
       
       
     if(count == 2)
          return true;
     
      // for winning column wise
     count = 0;
     for(int i=0;i<3;i++)
      if(board[i][col] == coin)
        count++;
       else if(board[i][col] != empty)
        {count = 0; break;}
       
       
     if(count == 2)
          return true;

      // verify if the cell falls on a diagonal
      if( (row+col)%2 != 0)
       return false;

     // for winning major diagonal wise   
     if(row == col)
     {  
     count = 0;
     for(int i=0;i<3;i++)
      if(board[i][i] == coin)
        count++;
       else if(board[i][i] != empty)
        {count = 0; break;}
       
       
     if(count == 2)
          return true;
     }
     else
     {
     // for winning minor diagonal wise     
     count = 0;
     for(int i=0;i<3;i++)
      if(board[i][2-i] == coin)
        count++;
       else if(board[i][2-i] != empty)
        {count = 0; break;}
       
       
     if(count == 2)
          return true;
    }
      return false;
}

int main()
{
    bool gameover = false;
    char b;
    
    std::cin>>myCoin>>oppCoin>>empty;

    while(!gameover)
    {
        std::cin>>b;
        if(b == 'W' || b == 'L' || b == 'w' || b == 'l')
        {
            gameover = true;
            continue;        
        }
                
        for(int i=0;i<3;i++)
         for(int j=0;j<3;j++)
         {
                 std::cin>>b;        
                 board[i][j] = b;
         }
        
        int maxvalue = -1;
        bool flag = false;
        int rrow, rcol;
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                if(board[i][j] != empty)
                 continue;
    
                if(willwin(myCoin, i, j))
                {
                   //std::cout<<"my win\n"<<i<<"\n"<<j<<"\n";
                   flag = true;
                   break;
               } 
               else if(willwin(oppCoin, i, j))
               {
                    //std::cout<<"opp win\n"<<i<<"\n"<<j<<"\n";
                    flag = true;
                    break;
                }
                else if(valuepos(i,j)>maxvalue)
                {
                     rrow = i;
                     rcol = j;
                     maxvalue = valuepos(i,j);                     
                }
            }
            if(flag)
             break; 
        }                               
        if(!flag)
             std::cout<<rrow<<"\n"<<rcol<<"\n";
                        
        
    }
}



