/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tic.tac.toe;

/**
 *
 * @author Mukund
 */
public class Point implements java.io.Serializable{
	public int X,Y;
	public Point(){} //default constructor.
	public Point(int X,int Y){ //parameterized constructor
		this.X=X;
		this.Y=Y;
	}
}
