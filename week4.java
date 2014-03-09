import java.util.*;
public class week4
{
	public static void main(String args[])
	{	
		Scanner scan=new Scanner(System.in);
		Deck deck;
		Player player=new Player();
		Banker banker=new Banker();
		int num,sel=0;
		while(true)
		{
			System.out.print("請輸入牌組數: ");
			num=scan.nextInt();
			if(num>0)
			{
				deck=new Deck(num);
				System.out.printf("...產生%d副牌...\n",num);
				deck.shuffle();
				System.out.print("...洗牌中...\n");
				break;
			}
			else
			{
				System.out.print("牌組數必須大於零!\n\n");
				continue;
			}
		}
		while(true)
		{
			if(sel==0)
				System.out.print("\n是否開始遊戲?\n1)開始遊戲 2)結束遊戲: ");
			else
				System.out.print("\n是否繼續遊戲?\n1)繼續遊戲 2)結束遊戲: ");			
			sel=scan.nextInt();
			if(sel==1)
			{	
				player.clear();
				banker.clear();
				if(deck.cards.size()<num*52/2)
				{
					System.out.print("牌數少於一半，重新洗牌...\n\n");
					deck=null;
					deck=new Deck(num);
					deck.shuffle();
				}
				System.out.print("------------------------------------\n");
				player.add(deck.deal());
				while(true)
				{
					System.out.print("\n玩家手上的牌為: ");
					player.show();
					if(player.points()>21)
					{
						System.out.print("\n玩家爆牌了!!!!!!!!!!!!!!!!!!\n\n");
						break;
					}
					System.out.print("\n\n是否繼續要牌?\n1)要 2)不要");
					sel=scan.nextInt();
					if(sel==1)
					{
						player.add(deck.deal());
					}
					else if(sel==2)
					{
						System.out.print("------------------------------------\n");
						System.out.print("\n輪到莊家拿牌...\n");
						while(banker.points()<17)
						{
							try
							{
								Thread.sleep(1000);     //延遲1秒
							} 
							catch(InterruptedException e)
							{
							}
							banker.add(deck.deal());
							System.out.print("莊家手上的牌為: ");
							banker.show();
							System.out.println();
						}
						if(banker.points()>21)
						{
							System.out.print("莊家爆牌!!!\n");
							System.out.println("\n玩家獲勝!!!\n");
						}
						else if(player.points()>banker.points())
						{
							System.out.println("\n玩家獲勝!!!\n");
						}
						else if(player.points()<banker.points())
						{
							System.out.println("\n莊家獲勝!!!\n");
						}
						else
						{
							System.out.println("\n平局!!!\n");
						}
						break;
					}
					else if(sel==3)//作弊用的XD
					{
						deck.show();
					}
					else
					{
						System.out.print("ERROR!!\n\n");
					}
				}
			}
			else if(sel==2)
			{
				break;
			}
			else
			{
				System.out.print("ERROR!!\n\n");
			}
		}		
	}
}
class Player
{
	ArrayList<Card> cards=new ArrayList<Card>();
	public int points()
	{
		int sum=0,i=0,j=0;
		for(Card c:cards)
		{
			if(c.getPoint()>1 && c.getPoint()<10)
				sum+=c.getPoint();
			else if(c.getPoint()>=10)
				sum+=10;
			else
				i++;
		}
		for(j=0;j<i;j++)
		{
			if((sum+(i-j)*10+j*1)<=21)
			{
				sum=sum+(i-j)*10+j*1;
				break;
			}
		}
		if(j>=i && i!=0)
			sum=sum+i*1;
		return sum;
	}
	public void add(Card c)
	{
		System.out.print("...給玩家發牌...");
		cards.add(c);
		System.out.print("拿到的牌是，"+c+"\n");
	}
	public void clear()
	{
		cards.clear();
	}
	public void show()
	{
		for(Card c:cards)
			System.out.print(c+" ");
		System.out.print("共"+points()+"點");
	}
}
class Banker
{
	ArrayList<Card> cards=new ArrayList<Card>();
	public int points()
	{
		int sum=0;
		for(Card c:cards)
		{
			sum+=c.getPoint();
		}
		return sum;
	}
	public void add(Card c)
	{
		System.out.print("\n...莊家拿牌...");
		cards.add(c);
		System.out.print("拿到的牌是，"+c+"\n");
	}
	public void clear()
	{
		cards.clear();
	}
	public void show()
	{
		for(Card c:cards)
			System.out.print(c+" ");
		System.out.print("共"+points()+"點");
	}
}
class Card
{
	private int point;
	private int poker;
	Card(int point,int poker)
	{
		this.point=point;
		this.poker=poker;
	}
	public int getPoint()
	{
		return point;
	}
	public int getPoker()
	{
		return poker;
	}
	public void setPoint(int point)
	{
		this.point=point;
	}
	public void setPoker(int poker)
	{
		this.poker=poker;
	}
	public String cgetPoker()//變數poker裡數字的意義所代表的花色
	{
		if(poker==1)
		{
			return "黑桃";
		}
		else if(poker==2)
		{
			return "紅心";
		}
		else if(poker==3)
		{
			return "方塊";
		}
		else if(poker==4)
		{
			return "梅花";
		}
		else
		{
			return "ERROR";
		}
	}
	public String toString()
	{
		String s=new String();
		s=cgetPoker()+point;
		return s;
	}
}
class Deck
{
	ArrayList<Card> cards=new ArrayList<Card>();
	Deck(int num)//建構元:跟據參數產生牌數
	{
		Card c;
		for(int k=0;k<num;k++)
		{
		for(int i=1;i<=13;i++)
		{
			for(int j=1;j<=4;j++)
			{
				c=new Card(i,j);
				cards.add(c);
			}
		}
		}
	}
	public void shuffle()//方法:洗牌
	{
		int t;
		int s;
		int i;
		for(Card c:cards)
		{
			t=c.getPoint();
			s=c.getPoker();
			i=(int)(Math.random()*(cards.size()-1))+1;			
			c.setPoint(cards.get(i).getPoint());
			c.setPoker(cards.get(i).getPoker());
			cards.get(i).setPoint(t);
			cards.get(i).setPoker(s);
		}
	}
	public Card deal()//方法:發牌，會回傳一張牌
	{
		Card c=new Card(cards.get(0).getPoint(),cards.get(0).getPoker());
		cards.remove(0);
		return c;
	}
	public void show()//輸出目前牌組情況
	{
		for(Card c:cards)
		{
			System.out.println(c);
		}
		System.out.println("目前尚有牌數:"+cards.size()+"\n");
	}
}