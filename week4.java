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
			System.out.print("�п�J�P�ռ�: ");
			num=scan.nextInt();
			if(num>0)
			{
				deck=new Deck(num);
				System.out.printf("...����%d�ƵP...\n",num);
				deck.shuffle();
				System.out.print("...�~�P��...\n");
				break;
			}
			else
			{
				System.out.print("�P�ռƥ����j��s!\n\n");
				continue;
			}
		}
		while(true)
		{
			if(sel==0)
				System.out.print("\n�O�_�}�l�C��?\n1)�}�l�C�� 2)�����C��: ");
			else
				System.out.print("\n�O�_�~��C��?\n1)�~��C�� 2)�����C��: ");			
			sel=scan.nextInt();
			if(sel==1)
			{	
				player.clear();
				banker.clear();
				if(deck.cards.size()<num*52/2)
				{
					System.out.print("�P�Ƥ֩�@�b�A���s�~�P...\n\n");
					deck=null;
					deck=new Deck(num);
					deck.shuffle();
				}
				System.out.print("------------------------------------\n");
				player.add(deck.deal());
				while(true)
				{
					System.out.print("\n���a��W���P��: ");
					player.show();
					if(player.points()>21)
					{
						System.out.print("\n���a�z�P�F!!!!!!!!!!!!!!!!!!\n\n");
						break;
					}
					System.out.print("\n\n�O�_�~��n�P?\n1)�n 2)���n");
					sel=scan.nextInt();
					if(sel==1)
					{
						player.add(deck.deal());
					}
					else if(sel==2)
					{
						System.out.print("------------------------------------\n");
						System.out.print("\n������a���P...\n");
						while(banker.points()<17)
						{
							try
							{
								Thread.sleep(1000);     //����1��
							} 
							catch(InterruptedException e)
							{
							}
							banker.add(deck.deal());
							System.out.print("���a��W���P��: ");
							banker.show();
							System.out.println();
						}
						if(banker.points()>21)
						{
							System.out.print("���a�z�P!!!\n");
							System.out.println("\n���a���!!!\n");
						}
						else if(player.points()>banker.points())
						{
							System.out.println("\n���a���!!!\n");
						}
						else if(player.points()<banker.points())
						{
							System.out.println("\n���a���!!!\n");
						}
						else
						{
							System.out.println("\n����!!!\n");
						}
						break;
					}
					else if(sel==3)//�@���Ϊ�XD
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
		System.out.print("...�����a�o�P...");
		cards.add(c);
		System.out.print("���쪺�P�O�A"+c+"\n");
	}
	public void clear()
	{
		cards.clear();
	}
	public void show()
	{
		for(Card c:cards)
			System.out.print(c+" ");
		System.out.print("�@"+points()+"�I");
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
		System.out.print("\n...���a���P...");
		cards.add(c);
		System.out.print("���쪺�P�O�A"+c+"\n");
	}
	public void clear()
	{
		cards.clear();
	}
	public void show()
	{
		for(Card c:cards)
			System.out.print(c+" ");
		System.out.print("�@"+points()+"�I");
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
	public String cgetPoker()//�ܼ�poker�̼Ʀr���N�q�ҥN�����
	{
		if(poker==1)
		{
			return "�®�";
		}
		else if(poker==2)
		{
			return "����";
		}
		else if(poker==3)
		{
			return "���";
		}
		else if(poker==4)
		{
			return "����";
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
	Deck(int num)//�غc��:��ڰѼƲ��͵P��
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
	public void shuffle()//��k:�~�P
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
	public Card deal()//��k:�o�P�A�|�^�Ǥ@�i�P
	{
		Card c=new Card(cards.get(0).getPoint(),cards.get(0).getPoker());
		cards.remove(0);
		return c;
	}
	public void show()//��X�ثe�P�ձ��p
	{
		for(Card c:cards)
		{
			System.out.println(c);
		}
		System.out.println("�ثe�|���P��:"+cards.size()+"\n");
	}
}