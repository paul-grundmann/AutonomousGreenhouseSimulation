package gewaechshaus.logic;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

public class Auftragsgenerator {

	private static final Logger log = Logger.getLogger( Auftragsgenerator.class.getName() );
	
    public Auftragsgenerator() throws SecurityException, IOException {
    	Handler handler = new FileHandler( Settings.loggingFilePath );
		log.addHandler( handler );
    }

    public int erstelleAuftragScan(Einzelpflanze[] pflanzen) {
        return 0;
    }

    public int erstelleAuftragErnten(Einzelpflanze[] pflanzen) {
        return 0;
    }

}