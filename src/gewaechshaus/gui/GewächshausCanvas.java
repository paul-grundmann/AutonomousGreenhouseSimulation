package gewaechshaus.gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;

import com.sun.xml.internal.ws.api.Component;

import gewaechshaus.logic.Einzelpflanze;
import gewaechshaus.logic.PflanzenArt;
import gewaechshaus.logic.Pflanzenverwaltung;
import gewaechshaus.logic.Position;

public class GewächshausCanvas extends Canvas {
	
	private int delta = 30;

	Pflanzenverwaltung pflanzenverwaltung;
	public GewächshausCanvas(Pflanzenverwaltung p) {
		// TODO Auto-generated constructor stub
		pflanzenverwaltung = p;
	}

	public GewächshausCanvas(GraphicsConfiguration arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
		Position größe = pflanzenverwaltung.getMaxGröße();
		int spalten = größe.getSpaltenID() + größe.getSpaltenID() / 5 + größe.getSpaltenID() % 5 + 1;
		int zeilen = größe.getReihenID() + größe.getReihenID() / 2 + größe.getReihenID() % 2 + 1;
		g.setColor(Color.BLACK);
		int PflanzeIndexSpalte = 1;
		int PflanzeIndexZeile = 1;
		for(int j = 0; j < zeilen; j++)
		{
			PflanzeIndexSpalte = 1;
			if((j % 3) == 0){
				//straße
				for( int i = 0; i < spalten; i++)
					g.drawRect(i*delta, j*delta, delta, delta);
			}
			else if(j == zeilen - 1){
				// letzte straße
				for( int i = 0; i < spalten; i++)
					g.drawRect(i*delta, j*delta, delta, delta);
			}
			else
			{
				for( int i = 0; i < spalten; i++){
					if ((i % 6) == 0){
						//  straße
						g.drawRect(i*delta, j*delta, delta, delta);
					}
					else if ( i == spalten -1){
						//letzte straße
						g.drawRect(i*delta, j*delta, delta, delta);
					}
					else{
						//pflanze
						g.drawOval(i*delta + 2, j*delta + 2 , delta - 4, delta - 4);
						Einzelpflanze p = pflanzenverwaltung.holePflanzeVonPosition(new Position(PflanzeIndexSpalte,PflanzeIndexZeile));
						if(p != null)
						{
							if(p.getArt() == PflanzenArt.eGurke){
								g.setColor(Color.green);
								g.fillOval(i*delta + 3, j*delta + 3 , delta - 6, delta - 6);
							}
							else if(p.getArt() == PflanzenArt.eTomate){
								g.setColor(Color.red);
								g.fillOval(i*delta + 3, j*delta + 3 , delta - 6, delta - 6);
							}
						}
						g.setColor(Color.black);
						PflanzeIndexSpalte++;
					}
				}
				PflanzeIndexZeile++;
			}
		}
	}
}
