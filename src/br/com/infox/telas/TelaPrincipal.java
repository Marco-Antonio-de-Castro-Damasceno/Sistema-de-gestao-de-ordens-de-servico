package br.com.infox.telas;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;

import br.com.infox.dal.moduloConexao;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class TelaPrincipal extends JFrame {

	// Instanciando objetos necessárias 
	// para a conexão com o banco de dados
	Connection conexao = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	// Versão referente à classe
	private static final long serialVersionUID = 1L;
	
	// JPanel que contém todos os outros objetos
	private JPanel contentPane;

	/**
	 * Rodando a aplicação
	 */

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaPrincipal frame = new TelaPrincipal(0, "usuario");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
	}

	/**
	 * Criando o designer da TelaPrincipal e, ao final, as variáveis
	 * passadas ao construtor determinam o nível de acesso do usuário
	 */
	public TelaPrincipal(int tipoUsuario, String user) {

		setResizable(false);
		setTitle("info X Ordem de Serviço");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBackground(SystemColor.activeCaption);
		desktopPane.setBounds(0, 33, 719, 538);
		contentPane.add(desktopPane);

		JMenuBar Menu = new JMenuBar();
		Menu.setBounds(0, 0, 270, 22);
		contentPane.add(Menu);

		JMenu menuCad = new JMenu("CADASTRO");
		Menu.add(menuCad);

		JMenuItem menuCadCli = new JMenuItem("CLIENTE");
		menuCadCli.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaCliente cliente = new TelaCliente();
				cliente.setVisible(true);
				desktopPane.add(cliente);
			}
		});
		menuCadCli.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.ALT_DOWN_MASK));
		menuCad.add(menuCadCli);

		JMenuItem menuCadUsuario = new JMenuItem("USUARIOS");

		menuCadUsuario.setEnabled(false);
		menuCadUsuario.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.ALT_DOWN_MASK));
		menuCad.add(menuCadUsuario);

		JMenuItem menuCadOs = new JMenuItem("OS");
		menuCadOs.setFont(new Font("Segoe UI", Font.BOLD, 12));
		menuCadOs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaOS os = new TelaOS();
				os.setVisible(true);
				desktopPane.add(os);
			}
		});
		menuCadOs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.ALT_DOWN_MASK));
		menuCad.add(menuCadOs);

		JMenu menuRel = new JMenu("RELATÓRIO");
		menuRel.setEnabled(false);
		Menu.add(menuRel);

		JMenuItem menuRelServ = new JMenuItem("Serviços");
		menuRelServ.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaRelServ telaRelServ = new TelaRelServ();
				telaRelServ.setVisible(true);
				desktopPane.add(telaRelServ);
			}
		});
		menuRelServ.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.ALT_DOWN_MASK));
		menuRel.add(menuRelServ);

		JMenuItem menuRelCli = new JMenuItem("Clientes");
		menuRelCli.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// gera um relatório de clientes
				int confirma = JOptionPane.showConfirmDialog(null, "Confirma a impressão desse relatório?", "Atenção", JOptionPane.YES_NO_OPTION);
				if (confirma == JOptionPane.YES_OPTION) {
					try {
						// Prepara a impressão de um relatório
						JasperPrint print = JasperFillManager.fillReport(getClass().getResourceAsStream("/relatorios/clientes.jasper"), null, conexao);
						
						// exibe o relatório através da classe JasperViewer
						JasperViewer.viewReport(print, false);
					} catch (Exception e2) {
						System.out.println(e2);
						JOptionPane.showMessageDialog(null, e2);
					}
				}
			}
		});
		menuRelCli.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.ALT_DOWN_MASK));
		menuRel.add(menuRelCli);

		JMenu menuAjuda = new JMenu("AJUDA");
		Menu.add(menuAjuda);

		JMenuItem menuAjudaSob = new JMenuItem("SOBRE");
		menuAjudaSob.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaSobre sobre = new TelaSobre();
				sobre.setVisible(true);
			}
		});
		menuAjudaSob.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.SHIFT_DOWN_MASK));
		menuAjuda.add(menuAjudaSob);

		JMenu menuOp = new JMenu("OPÇÕES");
		Menu.add(menuOp);

		JMenuItem menuOpSair = new JMenuItem("SAIR");
		menuOpSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int sair = JOptionPane.showConfirmDialog(null, "Tem certeza meu nobre? ", "Atenção",
						JOptionPane.YES_NO_OPTION);
				if (sair == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
		menuOpSair.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_DOWN_MASK));
		menuOp.add(menuOpSair);

		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/br/com/infox/icones/EMPRESA.png")));
		lblNewLabel.setBounds(836, 422, 127, 128);
		contentPane.add(lblNewLabel);

		JLabel lblUsuario = new JLabel("USUARIO\r\n");
		lblUsuario.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblUsuario.setBounds(729, 23, 257, 28);
		contentPane.add(lblUsuario);

		JLabel lblData = new JLabel("DATA");
		lblData.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblData.setBounds(729, 533, 120, 28);
		contentPane.add(lblData);

		menuCadUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaUsuario usuario = new TelaUsuario();
				usuario.setVisible(true);
				desktopPane.add(usuario);
			}
		});

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				// Código que executa quando a janela ganha foco
				Date data = new Date();
				DateFormat formatador = DateFormat.getDateInstance(DateFormat.SHORT);
				lblData.setText(formatador.format(data));

			}
		});
		
		if(tipoUsuario == 1) {
			lblUsuario.setForeground(Color.MAGENTA);
			menuRel.setEnabled(true);
			menuCadUsuario.setEnabled(true);
		}
		lblUsuario.setText(user);
		
		conexao = moduloConexao.conector();
		try {
			JasperPrint print = JasperFillManager.fillReport("", null, conexao);
		} catch (Exception e) {
			// TODO: handle exception
		}

		conexao = moduloConexao.conector();

	}
}
