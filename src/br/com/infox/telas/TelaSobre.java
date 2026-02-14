package br.com.infox.telas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class TelaSobre extends JFrame {

	// Versão da classe utilizada
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;

	/**
	 * Rodando a aplicação
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaSobre frame = new TelaSobre();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Criando o designer.
	 */
	public TelaSobre() {
		setTitle("SOBRE");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Sistema para controle de ordens de serviços");
		lblNewLabel.setBounds(10, 11, 302, 24);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Desenvolvido por Marco Antônio");
		lblNewLabel_1.setBounds(10, 44, 222, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Lincença: GPL ");
		lblNewLabel_2.setBounds(10, 58, 222, 14);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setIcon(new ImageIcon(TelaSobre.class.getResource("/br/com/infox/icones/LICENCA GPL.png")));
		lblNewLabel_3.setBounds(288, 168, 136, 82);
		contentPane.add(lblNewLabel_3);

	}
}
