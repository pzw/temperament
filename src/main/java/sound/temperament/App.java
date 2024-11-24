package sound.temperament;

import javax.swing.SwingUtilities;

import sound.temperament.ui.TemperamentFrame;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	TemperamentFrame mainFrame = new TemperamentFrame();
    	SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				mainFrame.setVisible(true);
			}
		});
//        System.out.println( "Hello World!" );
//        try {
//			// Tone.sound(884, 3000, 1.0);
//        	Note n1 = new Note(220, 2000, 1);
//        	Note n2 = new Note(340, 2000, 1);
//        	n1.addNote(n2);
//        	n1.play();
//		} catch (LineUnavailableException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
    }
}
