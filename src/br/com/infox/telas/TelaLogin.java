package br.com.infox.telas;

import java.sql.*;
import br.com.infox.dal.moduloConexao;


import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;

public class TelaLogin extends JFrame {
	
	// Instanciando objetos necessárias 
	// para a conexão com o banco de dados
	Connection conexao = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	
	/**
	 * Função responsável por autenticar um usuário e sua senha no banco de dados
	 */
	public void logar() {
		// Preparando o comando do banco de Dados
		String sql = "select * from tbusuarios where login = ? and senha = ?";
		// Tentando estabelecer conexão com o banco de dados
		try {
			// Preparando a conexao com o banco de dados
			pst = conexao.prepareStatement(sql);
			pst.setString( 1, txtUsuario.getText());
			String captura = new String(txtSenha.getPassword());
			pst.setString( 2, captura);
			// Executando comando
			rs = pst.executeQuery();
			// If para caso exista usuário e senha informados
			if(rs.next()) {
				// Obtendo o perfil e o nome do usuario:
				String user = rs.getString(2);
				String perfil = rs.getString(6);
				// A variável tipoUsuario representa o nível de acesso do usuário
				// Se o usuário é administrador tipoUsuario será 1
				// Caso contrário, tipoUsuario será 0
				int tipoUsuario = 0;
				if(perfil.equals("admin")) {
					tipoUsuario =1;
				}
				TelaPrincipal principal = new TelaPrincipal(tipoUsuario, user);
				principal.setVisible(true);
				// Fecha a TelaLogin
				this.dispose();
				// Encerra a conexão com o banco de dados
				conexao.close();
			}else {
				JOptionPane.showMessageDialog(null, "usuario e/ou senha invalido");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUsuario;
	private JPasswordField txtSenha;
	private JButton btnLogin;

	/**
	 * Rodando a TelaUsuario
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaLogin frame = new TelaLogin();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Criando o designer da TelaLogin e, ao final, mostra um ícone
	 * indicando se a conexão com o banco de dados foi bem sucedida
	 * ou não
	 */
	public TelaLogin() {
		setResizable(false);
		setForeground(new Color(0, 0, 255));
		setFont(new Font("Dialog", Font.BOLD, 14));
		setTitle("InfoX Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 442, 227);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setLocationRelativeTo(null);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("USUÁRIO");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setBounds(29, 27, 62, 23);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("SENHA");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1.setBounds(29, 81, 46, 14);
		contentPane.add(lblNewLabel_1);
		
		txtUsuario = new JTextField();
		txtUsuario.setBounds(101, 29, 278, 20);
		contentPane.add(txtUsuario);
		txtUsuario.setColumns(10);
		
		btnLogin = new JButton("LOGIN");
		btnLogin.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logar();
			}
		});
		btnLogin.setForeground(new Color(0, 0, 255));
		btnLogin.setBounds(281, 135, 98, 31);
		contentPane.add(btnLogin);
		
		txtSenha = new JPasswordField();
		txtSenha.setBounds(101, 79, 278, 20);
		contentPane.add(txtSenha);
		
		JLabel status = new JLabel("");
		status.setIcon(new ImageIcon(TelaLogin.class.getResource("/br/com/infox/icones/DB OK.png")));
		status.setBounds(29, 136, 38, 41);
		contentPane.add(status);
		conexao = moduloConexao.conector();
		System.out.println(conexao);

		// Testando conexão com o banco de dados
		if(conexao != null) {
			status.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/DB OK.png")));
		}else {
			status.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/DB ERRO.png")));
			
		}
	}
}
