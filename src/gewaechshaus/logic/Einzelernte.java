package gewaechshaus.logic;

import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Erstellt einen Auftrag zum Ernten einer einzelnen Pflanze.
 */
public class Einzelernte extends Unterauftrag implements Observer {

    private static final Logger log = Logger.getLogger(Einzelernte.class.getName());
    private Position zielPosition;
    private Einzelpflanze ep;
    private Roboterleitsystem roboterleitsystem;
    private int zustand = 0;

    public Einzelernte(Einzelpflanze ep, Roboterleitsystem roboterleitsystem) {
        try {
            Handler handler = new FileHandler(Settings.loggingFilePath);
            log.addHandler(handler);
        } catch (Exception e) {

        }
        this.status = UnterauftragsStatus.erstellt;
        this.roboterleitsystem = roboterleitsystem;
        this.ep = ep;
        this.zustand = 0;
        Collection<Position> freieNachbarnVonPflanze = roboterleitsystem.getFreieNachbarFelderVon(ep.getPosition());
        zielPosition = (Position) freieNachbarnVonPflanze.toArray()[0];
    }

    /**
     * Startet die Ausführung des Auftrags.
     */
    @Override
    public void ausfuehren(Roboter roboter) {
        // Zustand für Clock

        this.status = UnterauftragsStatus.ausfuehrend;
        switch (zustand) {
            // Initialisiere und fahre eventuell schon erste Position an
            case 0:
                roboter.addObserver(this);
                roboter.setRoboterStatus(RoboterStatus.eBeschaeftigt);
                fahreZuNachbarposition(roboter);
                zustand++;
                break;
            // Fahre zu Position
            case 1:
                if (!roboter.getPosition().equals(zielPosition)) {
                    fahreZuNachbarposition(roboter);
                } else {
                    zustand++;
                }
                break;
            // scanne
            case 2:
                roboter.scanne(ep.getPosition());
                zustand++;
                break;
            // Schneide
            case 3:
                roboter.schneide(ep.getPosition());
                zustand++;
                break;
            // lade ein und setze wieder auf bereit
            case 4:
                roboter.ladePflanzeAuf(ep);
                roboter.deleteObserver(this);
                roboter.setRoboterStatus(RoboterStatus.eBereit);
                this.status = UnterauftragsStatus.beendet;

                // Unterauftrag abgeschlossen Auftrag benachrichtigen
                setChanged();
                notifyObservers();
                break;
        }
    }


    /**
     * Fährt ein Nachbarkästchen des Roboters an.
     *
     * @param roboter Roboter aus der Roboterverwaltung
     */
    private void fahreZuNachbarposition(Roboter roboter) {
        ArrayList<Position> wegListe;
        try {
            wegListe = roboterleitsystem.getPfadVonNach(roboter.getPosition(), zielPosition);
            Position nPos = roboter.getPosition();
            if (wegListe.size() > 0) {
                nPos = wegListe.get(0);
            }
            Position rPos = roboter.getPosition();
            // Fahre in Richtung der Position
            if (nPos.getReihenID() > rPos.getReihenID()) {
                roboter.fahreNachUnten();
            } else if (nPos.getReihenID() < rPos.getReihenID()) {
                roboter.fahreNachOben();
            } else if (nPos.getSpaltenID() < rPos.getSpaltenID()) {
                roboter.fahreNachLinks();
            } else if (nPos.getSpaltenID() > rPos.getSpaltenID()) {
                roboter.fahreNachRechts();
            }

            if (roboter.getPosition().equals(nPos)) {
                wegListe.remove(0);
            }

        } catch (NoWayFoundException e) {
            Logging.log(this.getClass().getName(), Level.SEVERE, e.getMessage());
            roboter.warte();
        }


    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Clock) {
            ausfuehren(this.roboter);
        }
    }
}
