package com.algs4.percolation;

import edu.princeton.cs.algs4.Stopwatch;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
public class Percolation {
	
	private boolean[] per;
	//number of elements in array
	private int size;
	//array dimension
	private int N;
	//top row number
	int top;
	//bottom row number
	int bottom;
	//the array
	//#############################
	private WeightedQuickUnionUF weightedQuickUnionFind;
	//private QuickFindUF weightedQuickUnionFind;
	//Number of open elements
	private int countOpen;
	
	public Percolation(int N) // create N-by-N grid, with all sites initially blocked
	{
		if (N <= 0)
			throw new java.lang.IllegalArgumentException();

		this.N = N;
		size = N * N;
		//Virtual top row
		top = size + 1; 
		//Virtual bottom row
		bottom = 0; 
		countOpen = 0;
		per = new boolean[size + 2];
		
		//################################
		weightedQuickUnionFind = new WeightedQuickUnionUF(size + 2);
		//weightedQuickUnionFind = new QuickFindUF(size + 2);
		
		//initialize all elements to false
		for (int i = 0; i < size ; i++)
				per[i] = false;
	}
	
	public void open(int row, int col) // open the site (row, col) if it is not open already
	{
		//get index add one to row to compensate for virtual lines		
		int index = getIndex(row, col);
		
		//if already open then return
		if (isOpen(row, col))
			return;
		
		//Open the site
		per[index] = true;
		//Increment the count of open sites
		countOpen++;
		
		//Top row
		if (row  == 0)
			weightedQuickUnionFind.union(index, top);
		//Bottom row
		if (row  == (N - 1))
			weightedQuickUnionFind.union(index, bottom);
		
		//Above cell
		if (row  > 0 && per[index - N])
			weightedQuickUnionFind.union(index, index - N);
		//below cell
		if (row < (N - 1) && per[index + N])
			weightedQuickUnionFind.union(index, index + N);
		//To the left of cell
		if (col > 0 && per[index - 1])
			weightedQuickUnionFind.union(index, index-1);
		//To the right of cell
		if (col < (N - 1) && per[index + 1])
			weightedQuickUnionFind.union(index, index + 1);
	}
	
	public boolean isOpen(int row, int col) // is the site (row, col) open?
	{
		return per[getIndex(row, col)];
	}
	
	public boolean isFull(int row, int col) // is the site (row, col) full?
	{
		checkIndex(row, col);
		
		return per[getIndex(row,col)] && weightedQuickUnionFind.connected(0, getIndex(row, col));
	}
	
	public int numberOfOpenSites() // number of open sites
	{
		return countOpen;
	}
	
	public boolean percolates() // does the system percolate?
	{
		return weightedQuickUnionFind.connected(top, bottom);
	}
		
	// Private helper functions:
	private void checkIndex(int row, int col) //check if indexes are out of bounce, throws exception
	{
		if (col >= N  || col < 0)
			throw new java.lang.IndexOutOfBoundsException("Column is out of bounce");
		if (row >= N  || row  < 0)
			throw new java.lang.IndexOutOfBoundsException("Row is out of bounce");
	}
	
	private int getIndex(int row, int col) //returns the index of the boolean array
	{
		checkIndex(row, col);
		return (col + N * row) + 1;
	}
	
	/*
	public static void main(String[] args) {
		
		Percolation p = new Percolation(3);
			p.open(0, 0);
			p.open(1, 0);
	
			p.open(2, 1);
			p.open(1, 1);
			
		//p.open(1, 1);
		p.percolates();
		
		System.out.println("Main");
		System.out.println(p.percolates());
		
		Stopwatch t;
		double elapsedTime = 0;
		PercolationStats pp;

		for (int i = 12; i < 13; i++)
		{
			int a = (int) Math.pow(2, i);
			t = new Stopwatch();
			pp = new PercolationStats(a ,200);
			
			elapsedTime = t.elapsedTime();
			System.out.println("--------------------------Test #" + (i - 3) + "--------------------------");
			System.out.println(" a = " + a );
			System.out.println("mean()                        = " + String.format( "%.4f", elapsedTime));
			
			System.out.println("mean()                        = " + pp.mean());
			System.out.println("stddev()                      = " + pp.stddev());
			System.out.println("confidenceLow()               = " + pp.confidenceLow());
			System.out.println("confidenceHigh()              = " + pp.confidenceHigh());
		}
		
		int n=64;
		int sumj = 0;
		int sumi =0;
		for (int i=0; i < n; i++)
			{sumi++;
				for (int j=0; j < i; j+=2)
					sumj++;
			};
			System.out.print(" SUMi " + sumi);
				System.out.print(" SUMj " + sumj);
		return;
	}
	*/
}
