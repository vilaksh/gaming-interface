#include<iostream>

int board[3][3];
int myCoin, oppCoin, empty;
    
int valuepos(int row, int col)
{
    int val = 0, inc = 0;
    
     
     if((row+col)%2 ==0)
      val++;
     
     inc = 0;
      for(int i=0;i<3;i++)
      {
           if(board[row][i] == empty)
               inc+=2;
            else if(board[row][i] == oppCoin)
             inc += 3;
           else
           {
               inc = 0;               
               break;
            }
       }
       val += inc;       
       
       inc = 0;
      for(int i=0;i<3;i++)
      {
          if(board[i][col] == empty)
                inc+=2;
          else if (board[i][col] == oppCoin)
             inc += 3;
          else
          {
              inc = 0;
              break;
           }
      }
      val+=inc;      

      inc = 0;
      if( (row+col)%2 != 0)
      {//std::cout<<"Value of"<<row<<" "<<col<<" "<<val<<"\n";
       return val;}

     // for winning major diagonal wise   
   if(row == col)
   {  
    for(int i=0;i<3;i++)
    {
      if(board[i][i] == oppCoin)
       inc+=3;
       else if(board[i][i] == empty)
        inc+=2;
       else
       {
           inc = 0;
           break;
       }
    }
    }
    if(row+col == 2)
    {   

     // for winning minor diagonal wise     
    for(int i=0;i<3;i++)
    {
      if(board[i][2-i] == oppCoin)
       inc+=3;
       else if(board[i][2-i] == empty)
        inc+=2;

       else
       {
            inc = 0;
            break;
        }
    }  
    }

    val+=inc;
    
    //std::cout<<"Value of"<<row<<" "<<col<<" "<<val<<"\n";
    return val;
      
}

bool willwin(int coin, int row, int col)
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
    int cbrd;
    
    std::cin>>myCoin>>oppCoin>>empty;
    bool firstturn = true;

    while(!gameover)
    {
        std::cin>>b;
        if(b != 'c' && b !='C' )
        {
            gameover = true;
            continue;        
        }
                
        for(int i=0;i<3;i++)
         for(int j=0;j<3;j++)
         {
                 std::cin>>cbrd;        
                 board[i][j] = cbrd;
         }
        
        if(firstturn)
        {
          std::cout<<"1"<<"\n"<<"1"<<"\n";
          firstturn = false;
          continue;
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
                   std::cout<<i<<"\n"<<j<<"\n";
                   flag = true;
                   break;
               } 
               else if(willwin(oppCoin, i, j))
               {
                    std::cout<<i<<"\n"<<j<<"\n";
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



