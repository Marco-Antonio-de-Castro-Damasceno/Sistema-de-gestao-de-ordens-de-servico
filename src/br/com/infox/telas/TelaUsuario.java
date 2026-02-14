package br.com.infox.telas;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import java.awt.Rectangle;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Dimension;
import java.awt.Cursor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;
import br.com.infox.dal.moduloConexao;
import net.proteanit.sql.DbUtils;

import javax.swing.border.BevelBorder;
import javax.swing.JTable;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TelaUsuario extends JInternalFrame {

	// Instanciando objetos necessárias 
	// para a conexão com o banco de dados
	Connection conexao = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	
	// Versão da classe utilizada
	private static final long serialVersionUID = 1L;
	
	// Variáveis dinâmicas usadas na TelaUsuario
	private JTextField textUsuIdPesq;
	private JTextField textUsuNomePesq;
	private JTextField textUsuLogin;
	private JTextField textUsuSenha;
	private JTextField textUsuTelefone;
	private JComboBox<String> cmbUsuPerfil;
	
	private JButton btnUsuAdd;
	private JButton btnUsuDel;
	private JButton btnUsuEdit;
	private JTable tblUsu;
	
	/**
	 * Rodando a aplicação
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaUsuario frame = new TelaUsuario();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Criando o designer da TelaUsuario e declarando a variável utilizada 
	 * na conexão com o banco de dados
	 */
	public TelaUsuario() {
		setMaximizable(true);
		setIconifiable(true);
		setClosable(true);
		setBounds(new Rectangle(0, 0, 719, 538));
		setTitle("Tela do Usuário");
		setBounds(0, 0, 719, 538);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("*ID:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setBounds(526, 17, 43, 22);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNome = new JLabel("*NOME:");
		lblNome.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNome.setBounds(10, 17, 63, 22);
		getContentPane().add(lblNome);
		
		JLabel lblLogin = new JLabel("*LOGIN:");
		lblLogin.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblLogin.setBounds(31, 203, 76, 22);
		getContentPane().add(lblLogin);
		
		JLabel lblSenha = new JLabel("*SENHA:");
		lblSenha.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblSenha.setBounds(31, 295, 76, 22);
		getContentPane().add(lblSenha);
		
		JLabel lblPerfil = new JLabel("*PERFIL:");
		lblPerfil.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblPerfil.setBounds(31, 349, 76, 22);
		getContentPane().add(lblPerfil);
		
		textUsuIdPesq = new JTextField();
		textUsuIdPesq.setBounds(577, 19, 46, 22);
		getContentPane().add(textUsuIdPesq);
		textUsuIdPesq.setColumns(10);
		
		textUsuNomePesq = new JTextField();
		textUsuNomePesq.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				pesquisarUsuarioPeloNome();
			}
		});
		textUsuNomePesq.setColumns(10);
		textUsuNomePesq.setBounds(83, 19, 433, 22);
		getContentPane().add(textUsuNomePesq);
		
		textUsuLogin = new JTextField();
		textUsuLogin.setColumns(10);
		textUsuLogin.setBounds(117, 205, 333, 22);
		getContentPane().add(textUsuLogin);
		
		textUsuSenha = new JTextField();
		textUsuSenha.setColumns(10);
		textUsuSenha.setBounds(117, 297, 333, 22);
		getContentPane().add(textUsuSenha);
		
		cmbUsuPerfil = new JComboBox<>();
		cmbUsuPerfil.setModel(new DefaultComboBoxModel<String>(new String[] {"user", "admin"}));
		cmbUsuPerfil.setBounds(117, 348, 330, 28);
		getContentPane().add(cmbUsuPerfil);
		
		JLabel lblTelefone = new JLabel("telefone:");
		lblTelefone.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblTelefone.setBounds(31, 248, 76, 22);
		getContentPane().add(lblTelefone);
		
		textUsuTelefone = new JTextField();
		textUsuTelefone.setColumns(10);
		textUsuTelefone.setBounds(117, 250, 333, 22);
		getContentPane().add(textUsuTelefone);
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setBounds(307, 357, 46, 14);
		getContentPane().add(lblNewLabel_3);
		
		btnUsuAdd = new JButton("");
		btnUsuAdd.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnUsuAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionar();
			}
		});
		btnUsuAdd.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUsuAdd.setToolTipText("adicionar");
		btnUsuAdd.setIcon(new ImageIcon(TelaUsuario.class.getResource("/br/com/infox/icones/adicionar Usuario.png")));
		btnUsuAdd.setBounds(49, 402, 82, 82);
		getContentPane().add(btnUsuAdd);
		
		btnUsuDel = new JButton("");
		btnUsuDel.setEnabled(false);
		btnUsuDel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnUsuDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				remover();
			}
		});
		btnUsuDel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUsuDel.setToolTipText("excluir");
		btnUsuDel.setIcon(new ImageIcon(TelaUsuario.class.getResource("/br/com/infox/icones/excluir Usuario.png")));
		btnUsuDel.setPreferredSize(new Dimension(75, 75));
		btnUsuDel.setBounds(568, 402, 82, 82);
		getContentPane().add(btnUsuDel);
		
		btnUsuEdit = new JButton("");
		btnUsuEdit.setEnabled(false);
		btnUsuEdit.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnUsuEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				alterar();
			}
		});
		btnUsuEdit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUsuEdit.setToolTipText("editar");
		btnUsuEdit.setIcon(new ImageIcon(TelaUsuario.class.getResource("/br/com/infox/icones/busca usuario.png")));
		btnUsuEdit.setBounds(318, 402, 82, 82);
		getContentPane().add(btnUsuEdit);
		
		JLabel lblNewLabel_1 = new JLabel("* CAMPOS OBRIGATÓRIOS!");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1.setBounds(489, 204, 187, 22);
		getContentPane().add(lblNewLabel_1);
		
		JButton btnCliLimpar = new JButton("limpar campos");
		btnCliLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
		});
		btnCliLimpar.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnCliLimpar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCliLimpar.setBounds(489, 333, 187, 38);
		getContentPane().add(btnCliLimpar);
		
		tblUsu = new JTable() {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tblUsu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setarCampos();
			}
		});
		tblUsu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		tblUsu.setBounds(10, 67, 683, 108);
		getContentPane().add(tblUsu);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				consultarUsuarioPeloId();
			}
		});
		btnNewButton.setIcon(new ImageIcon(TelaUsuario.class.getResource("/br/com/infox/icones/busca32_32.png")));
		btnNewButton.setBounds(639, 11, 46, 41);
		getContentPane().add(btnNewButton);
		conexao = moduloConexao.conector();
	}
	/**
	 * Método para limpar os campos da TelaUsuario
	 */
	private void limparCampos() {
		textUsuIdPesq.setText(null);
		textUsuNomePesq.setText(null);
		textUsuTelefone.setText(null);
		textUsuLogin.setText(null);
		textUsuSenha.setText(null);
		cmbUsuPerfil.setSelectedItem(null);
		btnUsuAdd.setEnabled(true);
		btnUsuDel.setEnabled(false);
		btnUsuEdit.setEnabled(false);
		textUsuNomePesq.setEnabled(true);
		textUsuIdPesq.setEnabled(true);
	}
	/**
	 * Método para setar campos da TelaUsuario
	 */
	private void setarCampos() {
		int setar = tblUsu.getSelectedRow();
		if(setar>=0) {
			textUsuIdPesq.setText(tblUsu.getModel().getValueAt(setar, 0).toString());
			textUsuNomePesq.setText(tblUsu.getModel().getValueAt(setar, 1).toString());
			textUsuTelefone.setText(tblUsu.getModel().getValueAt(setar, 2).toString());
			textUsuLogin.setText(tblUsu.getModel().getValueAt(setar, 3).toString());
			textUsuSenha.setText(tblUsu.getModel().getValueAt(setar, 4).toString());
			cmbUsuPerfil.setSelectedItem(tblUsu.getModel().getValueAt(setar, 5).toString());
			textUsuNomePesq.setEnabled(false);
			textUsuIdPesq.setEnabled(false);
			btnUsuAdd.setEnabled(false);
			btnUsuEdit.setEnabled(true);
			btnUsuDel.setEnabled(true);
		}
	}
	/**
	 * Método para pesquisar Usuario pelo nome
	 */
	private void pesquisarUsuarioPeloNome() {
		String sql = "select * from tbusuarios where usuario like ?";
		try {
			pst = conexao.prepareStatement(sql);
			pst.setString(1, textUsuNomePesq.getText()+"%");
			rs=pst.executeQuery();
			tblUsu.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	/**
	 * Indica se o usuário pesquisado, através do seu id, foi encontrado
	 * no banco de dados, ou não. Retornando true, se o usuário foi encontrado
	 * e false se o usuário nao foi encontrado
	 */
	private boolean consultarUsuarioPeloId() {
		String sql = "select * from tbusuarios where iduser=?";
		try {
			pst = conexao.prepareStatement(sql);
			pst.setString(1, textUsuIdPesq.getText());
			rs=pst.executeQuery();
			if(rs.next()) {
				textUsuNomePesq.setText(rs.getString(2));
				textUsuTelefone.setText(rs.getString(3));
				textUsuLogin.setText(rs.getString(4));
				textUsuSenha.setText(rs.getString(5));
				cmbUsuPerfil.setSelectedItem(rs.getString(6));
				btnUsuAdd.setEnabled(false);
				btnUsuDel.setEnabled(true);
				btnUsuEdit.setEnabled(true);
				textUsuNomePesq.setEnabled(false);
				textUsuIdPesq.setEnabled(false);
				return true;
			}else {
				JOptionPane.showMessageDialog(null, "usuario nao cadastrado!");
				limparCampos();
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
			
		}
		return false;
	}
	/**
	 * Método para adicionar um usuário
	 */
	private void adicionar() {
		String sql = "insert into tbusuarios(iduser, usuario,fone,login,senha,perfil) values(?,?,?,?,?,?)";
		try {
			pst=conexao.prepareStatement(sql);
			pst.setString(1, textUsuIdPesq.getText());
			pst.setString(2, textUsuNomePesq.getText());
			pst.setString(3, textUsuTelefone.getText());
			pst.setString(4, textUsuLogin.getText());
			pst.setString(5, textUsuSenha.getText());
			pst.setString(6, cmbUsuPerfil.getSelectedItem().toString());
			if(textUsuIdPesq.getText().isEmpty()||textUsuNomePesq.getText().isEmpty()||textUsuLogin.getText().isEmpty()
					||textUsuSenha.getText().isEmpty()||cmbUsuPerfil.getSelectedItem().toString().isEmpty()) {
				JOptionPane.showMessageDialog(null, "preencha todos os campos obrigatorios!");
			}else {
				int adicionado = pst.executeUpdate();
				if(adicionado>0) {
					JOptionPane.showMessageDialog(null, "usuario adicionado com sucesso");
					limparCampos();
				}
			}
		}catch (Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
	}
	/**
	 * Método para alterar os dados de um usuário
	 */
	private void alterar() {
		String sql = "update tbusuarios set usuario = ?, fone=?, login=?, senha=?, perfil=? where iduser = ?";
		try {
			pst= conexao.prepareStatement(sql);
			pst.setString(1, textUsuNomePesq.getText());
			pst.setString(2, textUsuTelefone.getText());
			pst.setString(3, textUsuLogin.getText());
			pst.setString(4, textUsuSenha.getText());
			pst.setString(5, cmbUsuPerfil.getSelectedItem().toString());
			pst.setString(6, textUsuIdPesq.getText());
			if(textUsuIdPesq.getText().isEmpty()||textUsuNomePesq.getText().isEmpty()||textUsuLogin.getText().isEmpty()
					||textUsuSenha.getText().isEmpty()||cmbUsuPerfil.getSelectedItem().toString().isEmpty()) {
				JOptionPane.showMessageDialog(null, "preencha todos os campos obrigatorios!");
			}else {
				int adicionado = pst.executeUpdate();
				if(adicionado>0) {
					JOptionPane.showMessageDialog(null, "usuario adicionado com sucesso");
					limparCampos();
				}
			}
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
	}
	/*
	 * Método para remover o cadastro de um usuário
	 */
	private void remover() {
		int confirma = JOptionPane.showConfirmDialog(null, "tem certeza que deseja remover este usuario?", "Atencao", JOptionPane.YES_NO_OPTION);
		if(confirma == JOptionPane.YES_OPTION) {
			String sql = "delete from tbusuarios where iduser=?";
			try {
				pst = conexao.prepareStatement(sql);
				pst.setString(1, textUsuIdPesq.getText());
				int apagado = pst.executeUpdate();
				if(apagado>0) {
					JOptionPane.showMessageDialog(null, "Dados do usuario removidos com sucesso!");
					limparCampos();
				}
			}catch(Exception e) {
				JOptionPane.showMessageDialog(null, e);
			}
		}
	}
}
