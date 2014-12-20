/**
 * jreiff17
 * cosc301
 * programming assignment 2
 * intervalSplayTree.java
 */

public class IntervalSplayTree
{
	private Node root;

	public IntervalSplayTree()
	{
		root = null;
	}

	public int[] add(int lo, int hi)
	{
		if (hasRoot()) return root.add(lo, hi, 0);
		else
		{
			root = new Node(lo, hi, null, null, null);
			return temper(0,0,lo,hi);
		}
	}

	public int[] remove(int lo, int hi)
	{
		if(hasRoot()) return root.remove(lo, hi, 0);
		else return temper(0,0,lo,hi);
	}

	public boolean hasRoot()
	{
		return (root != null);
	}

	public int[] temper(int a, int b, int c, int d)
	{
		int[] temp = new int[4];
		temp[0] = a; temp[1] = b; temp[2] = c; temp[3] = d;
		return temp;
	}

	public String listString()
	{
		if(root != null) return root.listString(0);
		else return "";
	}

//	public String treeString()
//	{
//		if(hasRoot()) return root.treeString();
//		else return "{ }";
//	}

	private class Node
    {
		private int lo, hi;
		private Node parent, left, right;

		private Node(int lo, int hi, Node parent, Node left, Node right)
		{
			this.lo = lo;
			this.hi = hi;
			this.parent = parent;
			this.left = left;
			this.right = right;
		}

		public int[] add(int lo, int hi, int d)
		{

			if (hi<=this.lo)
				if(left==null)
				{
					left = new Node(lo, hi, this, null, null);
					left.splay();
					return temper(0,d+1,lo,hi);
				}
				else return left.add(lo, hi, d+1);
			else if (lo>=this.hi)
				if(right==null)
				{
					right = new Node(lo, hi, this, null, null);
					right.splay();
					return temper(0,d+1,lo,hi);
				}
				else return right.add(lo, hi, d+1);
			else
			{
				this.splay();
				return temper(1,d,lo,hi);
			}
		}

		public int[] remove(int lo, int hi, int d)
		{
			if (hi<=this.lo)
			{
				if(left!=null) return left.remove(lo, hi, d+1);
				else
				{
					this.splay();
					return temper(0, d+1, lo, hi);
				}
			}
			else if(lo>=this.hi)
			{
				if(right!=null) return right.remove(lo, hi, d+1);
				else
				{
					this.splay();
					return temper(0, d, lo, hi);
				}
			}
			else
			{
				if(this.getLo()==lo && this.getHi()!=hi) //truncate
				{
					int[] temp = temper(1, d, this.getLo(), this.getHi());
					this.setLo(hi);
					this.splay();
					return temp;
				}
				else if(this.hi==hi && this.lo!=lo) //truncate
				{
					int[] temp = temper(1, d, this.getLo(), this.getHi());
					this.setHi(lo);
					this.splay();
					return temp;
				}
				else if(this.lo==lo) //full remove
				{
					if(getParent()==null)
					{
						if(!hasLeft() && !hasRight())
						{
							IntervalSplayTree.this.root = null;
						}
						else if(hasLeft() && !hasRight())
						{
							IntervalSplayTree.this.root = left;

							left.setParent(null);
						}
						else if(!hasLeft() && hasRight())
						{
							IntervalSplayTree.this.root = right;

							right.setParent(null);
						}
						else
						{
							Node leftest = getRight();
							while(leftest.hasLeft()) leftest = leftest.getLeft();

							this.setLo(leftest.getLo());
							this.setHi(leftest.getHi());

							if(getRight().equals(leftest))
							{
								if(leftest.hasRight())
								{
									setRight(leftest.getRight());

									right.setParent(this);
								}
								else setRight(null);

								left.splay();
							}
							else
							{
								leftest.remove(leftest.getLo(),leftest.getHi(),d);
							}
						}
					}
					else
					{
						if(!hasLeft() && !hasRight())
						{
							if(parent.hasLeft() && parent.getLeft().equals(this))
								parent.setLeft(null);
							else parent.setRight(null);
							parent.splay();
						}
						else if(hasLeft() && !hasRight())
						{
							if(parent.hasLeft() && parent.getLeft().equals(this))
								parent.setLeft(left);
							else if(parent.getRight().equals(this))
								parent.setRight(left);

							left.setParent(parent);

							left.splay();
						}
						else if(!hasLeft() && hasRight())
						{
							if(parent.getLeft().equals(this))
								parent.setLeft(right);
							else if(parent.getRight().equals(this))
								parent.setRight(right);

							right.setParent(parent);

							right.splay();
						}
						else
						{
							Node leftest = getRight();
							while(leftest.hasLeft()) leftest = leftest.getLeft();

							this.setLo(leftest.getLo());
							this.setHi(leftest.getHi());

							if(getRight().equals(leftest))
							{
								if(leftest.hasRight())
								{
									setRight(leftest.getRight());

									right.setParent(this);
								}
								else setRight(null);

								left.splay();
							}
							else
							{
								leftest.remove(leftest.getLo(),leftest.getHi(),d);
							}
						}
					}

					return temper(3,d,lo,hi);
				}
				else //split
				{
					int newHi = this.hi;
					this.hi = lo;
					return temper(2, this.add(hi,newHi,d+1)[1],this.getLo(),this.getHi());
				}
			}
		}

		private void rightRotate()
		{
			Node grandparent = parent.getParent();

			if(grandparent != null)
			{
				if (grandparent.hasLeft() && grandparent.getLeft().equals(parent))
					grandparent.setLeft(this);
				else grandparent.setRight(this);
			} else root = this;

			parent.setParent(this);
			parent.setLeft(right);
			if(hasRight()) right.setParent(parent);
			right = parent;
			parent = grandparent;
		}
		private void leftRotate()
		{
			Node grandparent = parent.getParent();

			if(grandparent != null)
			{
				if (grandparent.hasRight() && grandparent.getRight().equals(parent))
					grandparent.setRight(this);
				else grandparent.setLeft(this);
			} else root = this;

			parent.setParent(this);
			parent.setRight(left);
			if(hasLeft()) left.setParent(parent);
			left = parent;
			parent = grandparent;
		}

		private int splay()
		{
			int depth = 0;
			while(!root.equals(this))
			{
				if(parent.hasLeft() && parent.getLeft().equals(this))
				{

					if(root.equals(parent))
					{
						parent.setParent(this);
						parent.setLeft(right);
						if(hasRight()) right.setParent(parent);
						right = parent;
						parent = null;
						root = this;
						depth++;
					}
					else if(parent.getParent().hasLeft()
							&& parent.getParent().getLeft().equals(parent))
					{
						parent.rightRotate();
						rightRotate();
						depth+=2;
					}
					else
					{
						rightRotate();
						leftRotate();
						depth+=2;
					}
				}
				else if(parent.hasRight() && parent.getRight().equals(this))
				{
					if(root.equals(parent))
					{
						parent.setParent(this);
						parent.setRight(left);
						if(hasLeft()) left.setParent(parent);
						left = parent;
						parent = null;
						root = this;
						depth++;
					}
					else if(parent.getParent().hasRight()
							&& parent.getParent().getRight().equals(parent))
					{
						parent.leftRotate();
						leftRotate();
						depth+=2;
					}
					else
					{
						leftRotate();
						rightRotate();
						depth+=2;
					}
				}
			}
			return depth;
		}

		public int[] temper(int a, int b, int c, int d)
		{
			int[] temp = new int[4];
			temp[0] = a; temp[1] = b; temp[2] = c; temp[3] = d;
			return temp;
		}

		public int getLo()
		{
			return lo;
		}

		public void setLo(int lo)
		{
			this.lo = lo;
		}

		public int getHi()
		{
			return hi;
		}

		public void setHi(int hi)
		{
			this.hi = hi;
		}

		public Node getParent()
		{
			return parent;
		}

		public void setParent(Node parent)
		{
			this.parent = parent;
		}

		public Node getLeft()
		{
			return left;
		}

		public void setLeft(Node left)
		{
			this.left = left;
		}

		public boolean hasLeft()
		{
			return (left != null);
		}

		public Node getRight()
		{
			return right;
		}

		public void setRight(Node right)
		{
			this.right = right;
		}

		public boolean hasRight()
		{
			return (right != null);
		}

		public String listString(int d)
		{
			String temp = "";
			if(left != null) temp = temp + left.listString(d+1);
			temp = temp+d+" "+this.toString()+"\n";
			if(right != null) temp = temp + right.listString(d+1);

			return temp;
		}

		public String treeString()
		{
			String temp = "";
			temp += this.toString()+" : ";
			if(hasLeft()) temp += left.toString()+" ";
			else temp += "[] ";
			if(hasRight()) temp += right.toString()+" ";
			else temp += "[] ";
			if(hasLeft()) temp += "\n"+left.treeString();
			if(hasRight()) temp += "\n"+right.treeString();

			return temp;
		}

		public String toString()
		{
			return "["+this.getLo()+", "+this.getHi()+")";
		}
	}

}