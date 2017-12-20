public class TaxiService{
	public Graph g = new Graph();
	//long timestart;

	public TaxiService() {
		// ...
		//timestart  = System.currentTimeMillis();
	}

	public void performAction(String actionMessage) {
		System.out.println("action to be performed: " + actionMessage);
		//....
		//long b = System.currentTimeMillis();
		String[] inputs = actionMessage.split("\\s+");
			String a = inputs[0];
			if(a.equals("edge"))
			{
				String s1 = inputs[1];
				String s2 = inputs[2];
				int cost = Integer.parseInt(inputs[3]);
				g.addedge(s1,s2,cost);
			}
			else if(a.equals("taxi"))
			{
				String s1 = inputs[1];
				String s2 = inputs[2];
				Vertex v = g.vertices.throwvertex(s2);
				if(v==null)
					System.out.println("No such vertex");
				else
				{taxi newtaxi = new taxi(s1,v,g);
				g.taxis.addElement(newtaxi);}
			}
			else if(a.equals("printTaxiPosition"))
			{
				//long time = System.currentTimeMillis() - timestart;
				//long timetostart = ((Long.parseLong(inputs[1]))*100);
				//if((timetostart)>(time))
				node<taxi> taxinames = g.taxis.head;
				while(taxinames!=null)
				{
					taxinames.data.t = Integer.parseInt(inputs[1]);
					taxinames = taxinames.next;
				}
				taxinames = g.taxis.head;
				while(taxinames!=null)
				{
					if(taxinames.data.t>=taxinames.data.busyupto)
					{System.out.println(taxinames.data.taxiname +": "+ taxinames.data.currentposition.location+" " );}
					taxinames = taxinames.next;
				}
				//System.out.println("");
			}
			else if(a.equals("customer"))
			{
				//long time = System.currentTimeMillis() - timestart;
				//long timetostart = ((Long.parseLong(inputs[3]))*100);
				
				node<taxi> taxinames = g.taxis.head;
				//taxi minimum = null;
				String s1 = inputs[1];
					String s2 = inputs[2];
					if(!g.vertices.IsMember(s1))
						System.out.println(s1+" does not exist");
					if(!g.vertices.IsMember(s2))
						System.out.println(s2+" does not exist");
				if(taxinames==null)
				{
					System.out.println("No taxis available");
				}
				else if(((g.vertices.IsMember(s1)))&&((g.vertices.IsMember(s2))))
				{
					taxi minimum = taxinames.data;
					/*String s1 = inputs[1];
					String s2 = inputs[2];*/
					System.out.println("Available taxis:");
					while(taxinames!=null)
					{
						taxinames.data.t = Integer.parseInt(inputs[3]);
					taxinames = taxinames.next;
					}
					taxinames = g.taxis.head;
					while(taxinames!=null)
					{
						if(taxinames.data.t>=taxinames.data.busyupto)
						{taxi currenttaxi  = taxinames.data;
						int taxidistance = g.distance(currenttaxi.currentposition.location,s1);
						if(g.distance(minimum.currentposition.location,s1)>taxidistance)
							minimum = currenttaxi;
						String given = g.shorts(currenttaxi.currentposition.location,s1);
						System.out.print("Path of "+currenttaxi.taxiname+": "+given+" takes time "+taxidistance+" units");
						System.out.println("");}
						taxinames = taxinames.next;
					}
					System.out.println("** Choose "+minimum.taxiname+" to service the customer request ***");
					//minimum.t = g.distance(minimum.currentposition.location,s1) + g.distance(s1,s2);
					//System.out.println(minimum.t);
					minimum.busyupto = minimum.t + g.distance(minimum.currentposition.location,s1) + g.distance(s1,s2);
					//System.out.println(minimum.busyupto+"\n");
					String dash = g.shorts(s1,s2);
					System.out.println("Path of customer: "+dash+" takes time "+g.distance(s1,s2)+" units");
					minimum.currentposition = g.vertices.throwvertex(s2);
					//System.out.println(minimum.currentposition.location+"\n");
					
				}
			}

	}
}
class Vertex
{
	public String location;
	public Graph g;
	//public boolean b;
	public edgelist destinations = new edgelist();
	public int label;
	public Vertex(String loc,Graph h)
	{
		location = loc;
		g = h;
		destinations.g = h;
	}
	//public Vertex next;
}
class edgelist
{
	public edgenode head;
	public edgenode tail;
	public Graph g;
	public void addedge(String destination,int cost)
	{
		Vertex v = g.vertices.throwvertex(destination);
		edgenode n = new edgenode(v,cost);
		if(head==null)
		{
			head = tail = n; 
		}
		else
		{
			tail.next = n;
			tail = n;
		}

	}
	public boolean IsMember(String str)
	{
		edgenode vstart = head;
		while(vstart!=null)
		{
			if(vstart.destination.equals(str))
				return true;
			vstart = vstart.next;
		}
		return false;
	}

}
class edgenode
{
	public Vertex destination;
	public int cost;
	public edgenode(Vertex v,int c)
	{
		destination = v;
		cost = c;
	}
	public edgenode next;
}
class vertexlist
{
	public vertexnode head;
	public vertexnode tail;
	public Graph g;
	public void addvertex(String location)
	{
		Vertex v = new Vertex(location,g);
		vertexnode vnew = new vertexnode(v);
		if(head==null)
		{
			head = tail = vnew;
		}
		else
		{
			tail.next = vnew;
			tail = vnew;

		}

	}
	public boolean IsMember(String str)
	{
		vertexnode vstart = head;
		while(vstart!=null)
		{
			if(vstart.data.location.equals(str))
				return true;
			vstart = vstart.next;
		}
		return false;
	}
	public Vertex throwvertex(String str)
	{
		vertexnode vstart = head;
		while(vstart!=null)
		{
			if(vstart.data.location.equals(str))
				return vstart.data;
			vstart = vstart.next;
		}
		return null;
	}
}
class vertexnode
{
	public Vertex data;
	public vertexnode next;
	public vertexnode(Vertex v)
	{
		data = v;
	}
	
}
class Graph
{
	public vertexlist vertices = new vertexlist();
	public Myset<taxi> taxis = new Myset<taxi>();
	public Graph()
	{
		vertices.g =this;
	}
	public void addedge(String s1,String s2,int cost)
	{
		if(!vertices.IsMember(s1))
			vertices.addvertex(s1);
		if(!vertices.IsMember(s2))
			vertices.addvertex(s2);
		Vertex v1 = vertices.throwvertex(s1);
		Vertex v2 = vertices.throwvertex(s2);
		if(!v1.destinations.IsMember(s2))
		{v1.destinations.addedge(s2,cost);
		v2.destinations.addedge(s1,cost);}

	}
	public Vertex shortestpath(String s1, String s2)
	{
		try
		{
			if(!vertices.IsMember(s1))
				throw new Exception();
		}
		catch(Exception e)
		{
			System.out.println(s1+" does not exist");
		}
		try
		{
			if(!vertices.IsMember(s2))
				throw new Exception();
		}
		catch(Exception e)
		{
			System.out.println(s2+" does not exist");
		}
		if((vertices.IsMember(s1))&&(vertices.IsMember(s2)))
		{
		Myset<String> pathneeded = new Myset<String>();
		Myset<String> unsettled = new Myset<String>();
		Vertex shortestpath = null;
		vertexnode vrec = vertices.head;
		while(vrec!=null)
		{
			
			unsettled.addElement(vrec.data.location);
			vrec.data.label = 1000000;
			//System.out.println(vrec.data.location);
			vrec  = vrec.next;
		}
		unsettled.delete(s1);
		pathneeded.addElement(s1);
		
		Vertex v = vertices.throwvertex(s1);
		//System.out.println(v.location);
		v.label = 0;
		//System.out.println(pathneeded.IsMember(s1));
		while(!pathneeded.IsMember(s2))
		{
			//System.out.println(v);
			edgenode neighbour = v.destinations.head;
			//boolean b= true;
			while(neighbour!=null)
			{
				//System.out.println(neighbour.destination.location);
				//neighbour.destination.label = Math.min(neighbour.cost+v.label,neighbour.destination.label);
				if((neighbour.destination.label)<=(neighbour.cost+v.label))
				{
					//neighbour.destination.b=false;
				}
				else
				{
					neighbour.destination.label = neighbour.cost+v.label;
					//System.out.println(neighbour.destination.location);
					//System.out.println(neighbour.destination.label);
					if(neighbour.destination.location.equals(s2))
					shortestpath = v;
					//neighbour.destination.b=true;
				}


				//System.out.println(neighbour.destination.label);
				neighbour = neighbour.next;
			}

			node<String> diff = unsettled.head;
			//System.out.println(diff);
			//System.out.println(v.location);
			Vertex c = vertices.throwvertex(diff.data);
			//System.out.println(c.location);
			/*while(pathneeded.IsMember(c.location))
			{
				System.out.println(c.location);
				diff = diff.next;
				c = diff.destination;
			}*/

			while((diff!=null))
			{
				if((vertices.throwvertex(diff.data).label<=c.label))
					{
						//System.out.println(diff.destination.location);
						c = vertices.throwvertex(diff.data);
						//System.out.println(c.label);
						//System.out.println((c.location));
						//System.out.println(pathneeded.IsMember(c.location));
					}
				
				diff = diff.next;
			}
			v = c;
			//System.out.println(unsettled.head.data);
			//System.out.println(v.label);
			//System.out.println(v.location);
			unsettled.delete(v.location);
			pathneeded.addElement(v.location);
			//if(v.b)
				//shortestpath.addElement(v.location);
			//System.out.println(pathneeded.tail.data);
		}
		return shortestpath;
	}
		return null;
	}
	public Myset<String> shortestroute(String s1, String s2)
	{
		Vertex lastvertex = this.shortestpath(s1,s2);
		
		Myset<String> finalset = new Myset<String>();
		//finalset.addElement("indiagate");
		finalset.addElement(s2);
		//System.out.println(lastvertex!=null);
		while((lastvertex!=null)&&(!finalset.IsMember(lastvertex.location)))
		{
		finalset.addatfirst(lastvertex.location);
		lastvertex = this.shortestpath(s1,finalset.head.data);
		}
		finalset.addatfirst(s1);
		/*node<String> s = finalset.head;
		while(s!=null)
		{
			System.out.print(s + ",");
			s = s.next;
		}*/
		return finalset;
		
	}
	public int distance(String s1, String s2)
	{
		Myset<String> finalset = this.shortestroute(s1,s2);
		int distance = 0;
		node<String> headstart = finalset.head;
		
		while(headstart.next!=null)
		{
			Vertex v = vertices.throwvertex(headstart.data);
			//Vertex v2 = vertices.throwvertex(headstart.next.data);
			//System.out.println(headstart.data);
			edgenode hello = v.destinations.head;
			//System.out.println(hello.destination);
			while(!hello.destination.location.equals(headstart.next.data))
			{
				hello = hello.next;
			}
			distance = distance + hello.cost;
			headstart = headstart.next;
		}
		return distance;
	}
	public String shorts(String s1,String s2)
	{
		Myset<String> finalset = this.shortestroute(s1,s2);
		String bla = "";
		node<String> s = finalset.head;
		while(s!=null)
		{
			bla = (bla + s.data + ",");
			s = s.next;
		}
		return bla;
	}
	
}
class taxi //implements Runnable
	{
		public String taxiname;
		public Vertex currentposition;
		public boolean available;
		public int  t;
		public int busyupto;
		public Graph map;
		public taxi(String s,Vertex v,Graph g)
		{
			taxiname = s;
			currentposition = v;
			available = true;
			t = 0;
			map = g;
			busyupto = 0;
		}
		
		
	}