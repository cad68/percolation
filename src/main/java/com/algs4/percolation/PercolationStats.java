package com.algs4.percolation;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {

	private Percolation per;
	private int N;
	private int T;
	private double[] testResults;
	private double mean;
	private double std;
	
	
	public PercolationStats(int N, int T) // perform T independent experiments on an N-by-N grid
	{
		this.T = T;
		this.N = N;
		if (N <= 0 || T <= 0)
		{
			throw new java.lang.IllegalArgumentException();
		}
		
		testResults = new double[T];
		
		//For all tests T..
		for (int i = 0; i < T; i++)
		{
			per = new Percolation(N);
			//Open a random cell and count open fields until grid percolates
			while (!per.percolates())
			{
				int row = StdRandom.uniform(0, N);
				int col = StdRandom.uniform(0, N);
				//if grid is not open then open and inciment counter
				if (!per.isOpen(row, col))
				{
					per.open(row, col);
				}
			}
			//Save the test result into result matrix
			testResults[i] = per.numberOfOpenSites() / (N * N);
		}
	}

	public double mean() // sample mean of percolation threshold
	{
		return StdStats.mean(testResults);
	}
	public double stddev() // sample standard deviation of percolation threshold
	{
		if (T == 1)
			return Double.NaN;
		return StdStats.stddev(testResults);
	}
	public double confidenceLow() // low endpoint of 95% confidence interval
	{
		return mean() - 1.96*stddev()/Math.sqrt(T);
	}
	public double confidenceHigh() // high endpoint of 95% confidence interval
	{
		return mean() + 1.96*stddev()/Math.sqrt(T);
	}
}
