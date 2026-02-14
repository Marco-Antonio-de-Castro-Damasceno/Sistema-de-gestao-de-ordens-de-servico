package br.com.infox.telas;

import net.proteanit.sql.DbUtils;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import java.awt.Rectangle;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import java.sql.*;
import br.com.infox.dal.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Cursor;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.BevelBorder;

public class TelaCliente extends JInternalFrame {

	// Instanciando objetos necessárias 
	// para a conexão com o banco de dados
	Connection conexao = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	
	// Versão utilizada na contrução da classe
	private static final long serialVersionUID = 1L;
	
	// Variáveis dinâmicas usadas na TelaCliente
	private JTextField textCliNome;
	private JTextField textCliTelefone;
	private JTextField textCliEnd;
	private JTextField textCliEmail;
	private JTextField textCliPesq;
	private JTable tblCli;
	private JButton btnCliAdd;
	private JTextField textCliId;
	private JButton btnCliEdit;
	private JButton btnCliDel;
	
	/**
	 * Rodando a aplicação
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaCliente frame = new TelaCliente();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Criando o designer da TelaCliente dentro do destop da TelaPrincipal
	 * Além das chamadas dos métodos necessários 
	 */
	public TelaCliente() {
		
		setMaximizable(true);
		setIconifiable(true);
		setClosable(true);
		setBounds(new Rectangle(0, 0, 719, 538));
		setTitle("Tela do Cliente");
		setBounds(0, 0, 719, 538);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("* CAMPOS OBRIGATÓRIOS");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblNewLabel.setForeground(new Color(255, 0, 0));
		lblNewLabel.setBounds(555, 0, 148, 23);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("*NOME:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1.setBounds(48, 213, 69, 23);
		getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("*TELEFONE:");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1_1.setBounds(48, 258, 87, 23);
		getContentPane().add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_2 = new JLabel("ENDEREÇO:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_2.setBounds(48, 304, 110, 23);
		getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_2_1 = new JLabel("EMAIL:");
		lblNewLabel_2_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_2_1.setBounds(48, 349, 110, 23);
		getContentPane().add(lblNewLabel_2_1);
		
		textCliNome = new JTextField();
		textCliNome.setBounds(197, 216, 407, 20);
		getContentPane().add(textCliNome);
		textCliNome.setColumns(10);
		
		textCliTelefone = new JTextField();
		textCliTelefone.setColumns(10);
		textCliTelefone.setBounds(197, 261, 407, 20);
		getContentPane().add(textCliTelefone);
		
		textCliEnd = new JTextField();
		textCliEnd.setColumns(10);
		textCliEnd.setBounds(197, 307, 407, 20);
		getContentPane().add(textCliEnd);
		
		textCliEmail = new JTextField();
		textCliEmail.setColumns(10);
		textCliEmail.setBounds(197, 352, 407, 20);
		getContentPane().add(textCliEmail);
		
		btnCliAdd = new JButton("");
		btnCliAdd.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnCliAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionar();
			}
		});
		btnCliAdd.setIcon(new ImageIcon(TelaCliente.class.getResource("/br/com/infox/icones/adicionar Usuario.png")));
		btnCliAdd.setBounds(104, 401, 89, 85);
		getContentPane().add(btnCliAdd);
		
		btnCliEdit = new JButton("");
		btnCliEdit.setToolTipText("Editar");
		btnCliEdit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCliEdit.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnCliEdit.setEnabled(false);
		btnCliEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				alterar();
			}
		});
		btnCliEdit.setIcon(new ImageIcon(TelaCliente.class.getResource("/br/com/infox/icones/busca usuario.png")));
		btnCliEdit.setBounds(500, 401, 89, 85);
		getContentPane().add(btnCliEdit);
		
		textCliPesq = new JTextField();
		// Enquanto estiver pesquisando a lista de pesquisa vai se alterando
		textCliPesq.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				pesquisarCliente();
			}
		});
		textCliPesq.setBounds(10, 33, 407, 23);
		getContentPane().add(textCliPesq);
		textCliPesq.setColumns(10);
		
		// Tabela referente à pesquisa dos clientes
		tblCli = new JTable() {
			public boolean isCellEditable(int row, int column) {
				//return columnEditables[column];
				return false;
			}
		};
		tblCli.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		tblCli.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
			}
		));
		// Usado para setar os campos de acordo com a linha clicada na tabela
		tblCli.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setarCampos();
			}
		});
		tblCli.setBounds(10, 78, 683, 103);
		getContentPane().add(tblCli);
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setIcon(new ImageIcon(TelaCliente.class.getResource("/br/com/infox/icones/busca32_32.png")));
		lblNewLabel_3.setBounds(422, 27, 39, 40);
		getContentPane().add(lblNewLabel_3);
		
		JButton btnCliLimpar = new JButton("limpar campos");
		btnCliLimpar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCliLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
		});
		btnCliLimpar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnCliLimpar.setBounds(565, 34, 123, 23);
		getContentPane().add(btnCliLimpar);
		
		JLabel lblNewLabel_4 = new JLabel("ID:");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_4.setBounds(479, 36, 27, 14);
		getContentPane().add(lblNewLabel_4);
		
		textCliId = new JTextField();
		textCliId.setEnabled(false);
		textCliId.setBounds(500, 34, 47, 20);
		getContentPane().add(textCliId);
		textCliId.setColumns(10);
		
		btnCliDel = new JButton("");
		btnCliDel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnCliDel.setEnabled(false);
		btnCliDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				remover();
			}
		});
		btnCliDel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCliDel.setToolTipText("remover");
		btnCliDel.setIcon(new ImageIcon(TelaCliente.class.getResource("/br/com/infox/icones/excluir Usuario.png")));
		btnCliDel.setBounds(295, 401, 89, 85);
		getContentPane().add(btnCliDel);
		
		conexao = moduloConexao.conector();

	}
	
	/**
	 * Método para cadastrar clientes
	 */
	
	private void adicionar() {
		String sql = "insert into tbclientes (nomecli, endcli, fonecli ,emailcli) values(?,?,?,?)";
		try {
			pst=conexao.prepareStatement(sql);
			pst.setString(1, textCliNome.getText());
			pst.setString(2, textCliEnd.getText());
			pst.setString(3, textCliTelefone.getText());
			pst.setString(4, textCliEmail.getText());
			//Confere se os campos obrigatórios foram devidamente preenchidos
			if(textCliNome.getText().isEmpty()||textCliEnd.getText().isEmpty()||textCliTelefone.getText().isEmpty()
					||textCliEmail.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "preencha todos os campos obrigatorios!");
			}else {
				int adicionado = pst.executeUpdate();
				if(adicionado>0) {
					JOptionPane.showMessageDialog(null, "cliente cadastrado com sucesso!");
					limparCampos();
				}
			}
		}catch (Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	/**
	 * Método para pesquisar o cliente de forma dinâmica
	 */
	
	private void pesquisarCliente() {
		String sql = "select * from tbclientes where nomecli like ?";
		try {
			pst = conexao.prepareStatement(sql);
			pst.setString(1, textCliPesq.getText()+"%");
			rs=pst.executeQuery();
			tblCli.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	/**
	 * Utiliza a linha selecionada da tabela dos clientes para preencher
	 * os campos na TelaCliente
	 */
	
	private void setarCampos() {
		int setar = tblCli.getSelectedRow();
		if(setar>=0) {
			textCliId.setText(tblCli.getModel().getValueAt(setar, 0).toString());
			textCliNome.setText(tblCli.getModel().getValueAt(setar, 1).toString());
			textCliEnd.setText(tblCli.getModel().getValueAt(setar, 2).toString());
			textCliTelefone.setText(tblCli.getModel().getValueAt(setar, 3).toString());
			textCliEmail.setText(tblCli.getModel().getValueAt(setar, 4).toString());
			btnCliAdd.setEnabled(false);
			btnCliEdit.setEnabled(true);
			btnCliDel.setEnabled(true);
		}
	}
	
	/**
	 *  Limpa os campos da TelaCliente e retorna com os botões para
	 *  o estado inicial
	 */
	
	private void limparCampos() {
		textCliPesq.setText(null);
		textCliNome.setText(null);
		textCliEnd.setText(null);
		textCliTelefone.setText(null);
		textCliEmail.setText(null);
		textCliId.setText(null);
		btnCliAdd.setEnabled(true);
		btnCliEdit.setEnabled(false);
		btnCliDel.setEnabled(false);
		pesquisarCliente();
	}
	
	/**
	 * Método para alterar dados do cliente
	 */
	
	private void alterar() {
		String sql = "update tbclientes set nomecli = ?, endcli=?, fonecli=?, emailcli=? where idcli = ?";
		try {
			pst= conexao.prepareStatement(sql);
			pst.setString(1, textCliNome.getText());
			pst.setString(2, textCliEnd.getText());
			pst.setString(3, textCliTelefone.getText());
			pst.setString(4, textCliEmail.getText());
			int linha = tblCli.getSelectedRow();
			pst.setString(5, textCliId.getText());
			// Confere o preenchimento dos campos obrigatórios
			if(textCliNome.getText().isEmpty()||textCliTelefone.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "preencha todos os campos obrigatorios!");
			}else {
				int adicionado = pst.executeUpdate();
				if(adicionado>0) {
					JOptionPane.showMessageDialog(null, "Dados do cliente alterados com sucesso");
					limparCampos();
				}
			}
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	/*
	 * Método para remover o cadastro de um cliente
	 */
	
	private void remover() {
		int confirma = JOptionPane.showConfirmDialog(null, "tem certeza que deseja remover este cliente?", "Atencao", JOptionPane.YES_NO_OPTION);
		if(confirma == JOptionPane.YES_OPTION) {
			String sql = "delete from tbclientes where idcli=?";
			try {
				pst = conexao.prepareStatement(sql);
				pst.setString(1, textCliId.getText());
				int apagado = pst.executeUpdate();
				if(apagado>0) {
					JOptionPane.showMessageDialog(null, "Dados do cliente removidos com sucesso!");
					limparCampos();
				}
			}catch(Exception e) {
				JOptionPane.showMessageDialog(null, e);
			}
		}
	}
}
