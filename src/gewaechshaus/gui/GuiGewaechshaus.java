package gewaechshaus.gui;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;

import gewaechshaus.logic.Pflanzenverwaltung;
import gewaechshaus.logic.Roboterleitsystem;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;

enum GuiGewaechshausStatus {
    none,
    initDone,
    running
}

enum GuiGewaechshausEvents {
    start
}

public class GuiGewaechshaus extends Panel {

    private GewächshausCanvas canvas;
    private Pflanzenverwaltung pflanzenverwaltung;
    private Roboterleitsystem roboterleitsystem;
    private GuiGewaechshausStatus status = GuiGewaechshausStatus.none;

    public GuiGewaechshaus(String Name, Pflanzenverwaltung p, Roboterleitsystem rLeitsystem) {
        super(Name);
        pflanzenverwaltung = p;
        this.roboterleitsystem = rLeitsystem;
    }

    public void init() {
        toggleEvent(GuiGewaechshausEvents.start);
    }

    private void toggleEvent(GuiGewaechshausEvents e) {
        switch (status) {
            case none:
                if (GuiGewaechshausEvents.start == e) {
                    canvas = new GewächshausCanvas(pflanzenverwaltung, roboterleitsystem);
                    GroupLayout groupLayout = new GroupLayout(MainFrame);
                    groupLayout.setHorizontalGroup(
                            groupLayout.createParallelGroup(Alignment.LEADING)
                                    .addGroup(groupLayout.createSequentialGroup()
                                            .addContainerGap()
                                            .addComponent(canvas, GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
                                            .addContainerGap())
                    );
                    groupLayout.setVerticalGroup(
                            groupLayout.createParallelGroup(Alignment.LEADING)
                                    .addGroup(groupLayout.createSequentialGroup()
                                            .addContainerGap()
                                            .addComponent(canvas, GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
                                            .addContainerGap())
                    );
                    MainFrame.setLayout(groupLayout);

                }
                break;
            case initDone:

                break;
        }
    }


    public void repaintCanvas() {
        canvas.repaint();
    }
}
