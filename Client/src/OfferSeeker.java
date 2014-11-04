import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.JTextPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;


public class OfferSeeker {

	private JFrame frame;
	private JTextField txtCzegoSzukasz;
	private boolean klik = true;
	private JTextPane textPane;
	private JLabel lblCzyToOgoszenie;
	private JButton btnTak;
	private JButton btnNie;
	private JTable table;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OfferSeeker window = new OfferSeeker();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public OfferSeeker() {
		try { UIManager.setLookAndFeel("org.pushingpixels.substance.api.skin.SubstanceGraphiteLookAndFeel"); }
        catch (Exception exc) { SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JOptionPane.showMessageDialog(null, "Error!", "Message", JOptionPane.ERROR_MESSAGE);
            }
        }); }
        
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 960, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblOfferSeeker = new JLabel("Offer Seeker");
		lblOfferSeeker.setForeground(Color.WHITE);
		lblOfferSeeker.setFont(new Font("Dotum", Font.BOLD, 34));
		lblOfferSeeker.setBounds(336, 11, 228, 51);
		frame.getContentPane().add(lblOfferSeeker);
		
		txtCzegoSzukasz = new JTextField();
		txtCzegoSzukasz.setForeground(Color.WHITE);
		txtCzegoSzukasz.setFont(new Font("Segoe UI Light", Font.ITALIC, 20));
		txtCzegoSzukasz.setText("czego szukasz?");
		txtCzegoSzukasz.setBounds(36, 73, 718, 45);
		frame.getContentPane().add(txtCzegoSzukasz);
		txtCzegoSzukasz.setDisabledTextColor(Color.WHITE);
		txtCzegoSzukasz.setColumns(10);
		txtCzegoSzukasz.setEnabled(false);
		
		JButton btnSzukaj = new JButton("szukaj");
		btnSzukaj.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 15));
		btnSzukaj.setBounds(763, 73, 154, 45);
		frame.getContentPane().add(btnSzukaj);
		
		textPane = new JTextPane();
		textPane.setFont(new Font("Segoe UI Light", Font.PLAIN, 15));
		textPane.setBounds(277, 137, 640, 368);
		textPane.setEnabled(false);
		textPane.setBackground(new Color(69, 66, 59));
		frame.getContentPane().add(textPane);
		
		lblCzyToOgoszenie = new JLabel("Czy to og\u0142oszenie jest ofert\u0105?");
		lblCzyToOgoszenie.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 15));
		lblCzyToOgoszenie.setBounds(277, 516, 251, 45);
		frame.getContentPane().add(lblCzyToOgoszenie);
		
		btnTak = new JButton("TAK");
		btnTak.setBounds(489, 530, 101, 23);
		frame.getContentPane().add(btnTak);
		
		btnNie = new JButton("NIE");
		btnNie.setBounds(600, 530, 101, 23);
		frame.getContentPane().add(btnNie);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(36, 137, 225, 368);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		
		
		//listeners
		txtCzegoSzukasz.addMouseListener(new MouseListener() {

		    public void mouseClicked(MouseEvent e) {
		    	txtCzegoSzukasz.setEnabled(true);
		    	txtCzegoSzukasz.requestFocus();
		    	if(klik){
		    		txtCzegoSzukasz.setText(null);
		    		klik=false;
		    	}
		    	
		    }

		    public void mousePressed(MouseEvent e) {

		    }

		    public void mouseReleased(MouseEvent e) {

		    }

		    public void mouseEntered(MouseEvent e) {

		    }

		    public void mouseExited(MouseEvent e) {

		    }

		});
		
		btnSzukaj.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				buttonClick();
			}
		});
		
		txtCzegoSzukasz.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				buttonClick();
				
			}
		});
		
		
		frame.setResizable(false);
		
	}
	
	
	private void buttonClick(){
		klik=true;
	}
}
