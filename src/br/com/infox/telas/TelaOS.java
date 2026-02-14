package br.com.infox.telas;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.table.DefaultTableModel;

import br.com.infox.dal.moduloConexao;
import net.proteanit.sql.DbUtils;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class TelaOS extends JInternalFrame {

	// Instanciando objetos necessárias 
	// para a conexão com o banco de dados
	Connection conexao = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	private JButton btnOSEdit;
	private JButton btnOSPrinter;
	private JButton btnOSDel;
	private JButton btnOSBusca;
	private JButton btnOSAdd;
	private JComboBox<String> cmbOSSit;
	private JRadioButton rbtnOSOrc;
	private JRadioButton rbtnOSOS;
	private String tipo;
	
	// Versão da classe utilizada
	private static final long serialVersionUID = 1L;
	
	private JTextField textOSNumero;
	private JTextField textOSData;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField textOSCliPesq;
	private JTable tblOSCli;
	private JTextField textOSId;
	private JTextField textOSEquip;
	private JTextField textOSDef;
	private JTextField textOSServ;
	private JTextField textOSTec;
	private JTextField textOSVal;

	/**
	 * Rodando a aplicação
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaOS frame = new TelaOS();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Criando o designer da TelaOS dentro do destop da TelaPrincipal
	 * Além das chamadas dos métodos necessários 
	 */
	public TelaOS() {

		addInternalFrameListener(new InternalFrameAdapter() {
			@Override
			public void internalFrameOpened(InternalFrameEvent e) {
				rbtnOSOrc.setSelected(true);
				tipo = "Orçamento";
			}
		});
		setMaximizable(true);
		setIconifiable(true);
		setClosable(true);
		setBounds(new Rectangle(0, 0, 719, 538));
		setTitle("Tela de OS");
		setBounds(0, 0, 719, 538);
		getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(19, 11, 268, 76);
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Nº OS");
		lblNewLabel.setBounds(10, 11, 46, 14);
		panel.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("DATA");
		lblNewLabel_1.setBounds(116, 11, 46, 14);
		panel.add(lblNewLabel_1);

		textOSNumero = new JTextField();
		textOSNumero.setEnabled(false);
		textOSNumero.setBounds(10, 36, 86, 20);
		panel.add(textOSNumero);
		textOSNumero.setColumns(10);

		textOSData = new JTextField();
		textOSData.setForeground(new Color(255, 255, 255));
		textOSData.setFont(new Font("Tahoma", Font.BOLD, 12));
		textOSData.setEnabled(false);
		textOSData.setBounds(116, 37, 142, 20);
		panel.add(textOSData);
		textOSData.setColumns(10);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Cliente", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(297, 11, 396, 127);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);

		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setIcon(new ImageIcon(TelaOS.class.getResource("/br/com/infox/icones/busca32_32.png")));
		lblNewLabel_3.setBounds(347, 11, 39, 40);
		panel_1.add(lblNewLabel_3);

		textOSCliPesq = new JTextField();
		textOSCliPesq.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				pesquisarCliente();
			}
		});
		textOSCliPesq.setBounds(10, 21, 327, 20);
		panel_1.add(textOSCliPesq);
		textOSCliPesq.setColumns(10);

		tblOSCli = new JTable() {
			public boolean isCellEditable(int row, int column) {
				// return columnEditables[column];
				return false;
			}
		};
		tblOSCli.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setarCampos();
			}
		});
		tblOSCli.setModel(new DefaultTableModel(new Object[][] {}, new String[] {}));
		tblOSCli.setBounds(10, 39, 327, 75);
		panel_1.add(tblOSCli);

		JLabel lblNewLabel_4 = new JLabel("ID:");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_4.setBounds(357, 62, 25, 14);
		panel_1.add(lblNewLabel_4);

		textOSId = new JTextField();
		textOSId.setBounds(347, 78, 39, 20);
		panel_1.add(textOSId);
		textOSId.setColumns(10);

		JLabel lblNewLabel_5 = new JLabel("*Equipamento:");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_5.setBounds(19, 159, 94, 22);
		getContentPane().add(lblNewLabel_5);

		JLabel lblNewLabel_5_1 = new JLabel("*Defeito:");
		lblNewLabel_5_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_5_1.setBounds(20, 196, 94, 22);
		getContentPane().add(lblNewLabel_5_1);

		JLabel lblNewLabel_5_1_1 = new JLabel("Serviço:");
		lblNewLabel_5_1_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_5_1_1.setBounds(20, 229, 94, 22);
		getContentPane().add(lblNewLabel_5_1_1);

		JLabel lblNewLabel_5_1_2 = new JLabel("Técnico:");
		lblNewLabel_5_1_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_5_1_2.setBounds(19, 265, 94, 22);
		getContentPane().add(lblNewLabel_5_1_2);

		JLabel lblNewLabel_5_1_3 = new JLabel("Valor total:");
		lblNewLabel_5_1_3.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_5_1_3.setBounds(450, 265, 70, 22);
		getContentPane().add(lblNewLabel_5_1_3);

		textOSEquip = new JTextField();
		textOSEquip.setBounds(123, 160, 539, 20);
		getContentPane().add(textOSEquip);
		textOSEquip.setColumns(10);

		textOSDef = new JTextField();
		textOSDef.setColumns(10);
		textOSDef.setBounds(123, 197, 539, 20);
		getContentPane().add(textOSDef);

		textOSServ = new JTextField();
		textOSServ.setColumns(10);
		textOSServ.setBounds(123, 230, 539, 20);
		getContentPane().add(textOSServ);

		textOSTec = new JTextField();
		textOSTec.setColumns(10);
		textOSTec.setBounds(123, 266, 317, 20);
		getContentPane().add(textOSTec);

		textOSVal = new JTextField();
		textOSVal.setText("0");
		textOSVal.setColumns(10);
		textOSVal.setBounds(530, 266, 132, 20);
		getContentPane().add(textOSVal);

		btnOSAdd = new JButton("");
		btnOSAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				emitirOS();
			}
		});
		btnOSAdd.setBackground(new Color(240, 240, 240));
		btnOSAdd.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnOSAdd.setToolTipText("adicionar");
		btnOSAdd.setIcon(new ImageIcon(TelaOS.class.getResource("/br/com/infox/icones/adicionar Usuario.png")));
		btnOSAdd.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnOSAdd.setBounds(48, 401, 82, 82);
		getContentPane().add(btnOSAdd);

		btnOSDel = new JButton("");
		btnOSDel.setEnabled(false);
		btnOSDel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnOSDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluir_os();
			}
		});
		btnOSDel.setToolTipText("excluir");
		btnOSDel.setPreferredSize(new Dimension(75, 75));
		btnOSDel.setIcon(new ImageIcon(TelaOS.class.getResource("/br/com/infox/icones/excluir Usuario.png")));
		btnOSDel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnOSDel.setBounds(178, 401, 82, 82);
		getContentPane().add(btnOSDel);

		btnOSEdit = new JButton("");
		btnOSEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				alterar_os();
			}
		});
		btnOSEdit.setEnabled(false);
		btnOSEdit.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnOSEdit.setToolTipText("editar");
		btnOSEdit.setIcon(new ImageIcon(TelaOS.class.getResource("/br/com/infox/icones/busca usuario.png")));
		btnOSEdit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnOSEdit.setBounds(308, 401, 82, 82);
		getContentPane().add(btnOSEdit);

		btnOSBusca = new JButton("");
		btnOSBusca.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pesqOS();
			}
		});
		btnOSBusca.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnOSBusca.setToolTipText("pesquisar");
		btnOSBusca.setIcon(new ImageIcon(TelaOS.class.getResource("/br/com/infox/icones/busca.png")));
		btnOSBusca.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnOSBusca.setBounds(438, 401, 82, 82);
		getContentPane().add(btnOSBusca);

		btnOSPrinter = new JButton("");
		btnOSPrinter.setEnabled(false);
		btnOSPrinter.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnOSPrinter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				imprimirOS();
			}
		});
		btnOSPrinter.setToolTipText("imprimir");
		btnOSPrinter.setIcon(new ImageIcon(TelaOS.class.getResource("/br/com/infox/icones/printer.png")));
		btnOSPrinter.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnOSPrinter.setBounds(568, 401, 82, 82);
		getContentPane().add(btnOSPrinter);

		JButton btnNewButton = new JButton("LIMPAR CAMPOS");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnNewButton.setBounds(506, 320, 156, 22);
		getContentPane().add(btnNewButton);

		JLabel lblNewLabel_2 = new JLabel("Situação:");
		lblNewLabel_2.setBounds(19, 315, 60, 14);
		getContentPane().add(lblNewLabel_2);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 12));

		cmbOSSit = new JComboBox();
		cmbOSSit.setBounds(122, 312, 165, 22);
		getContentPane().add(cmbOSSit);
		cmbOSSit.setModel(
				new DefaultComboBoxModel(new String[] { " ", "Na bancada", "Entrega OK", "Orçamento reprovado",
						"Aguardando Aprovação", "Aguardando peças", "Abandonado pelo cliente", "Retornou" }));
		
				rbtnOSOS = new JRadioButton("ORDEM DE SERVIÇO");
				rbtnOSOS.setBounds(139, 111, 152, 23);
				getContentPane().add(rbtnOSOS);
				rbtnOSOS.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						tipo = "OS";
					}
				});
				buttonGroup.add(rbtnOSOS);
				
						rbtnOSOrc = new JRadioButton("ORÇAMENTO");
						rbtnOSOrc.setBounds(30, 111, 94, 23);
						getContentPane().add(rbtnOSOrc);
						rbtnOSOrc.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								tipo = "Orçamento";
							}
						});
						buttonGroup.add(rbtnOSOrc);

		conexao = moduloConexao.conector();
	}

	/**
	 * Método para pesquisar o cliente de forma dinâmica
	 */
	
	private void pesquisarCliente() {
		String sql = "select idcli as Id, nomecli as nome, fonecli as fone from tbclientes where nomecli like ?";
		try {
			pst = conexao.prepareStatement(sql);
			pst.setString(1, textOSCliPesq.getText() + "%");
			rs = pst.executeQuery();
			tblOSCli.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	private void setarCampos() {
		int setar = tblOSCli.getSelectedRow();
		if (setar >= 0) {
			textOSId.setText(tblOSCli.getModel().getValueAt(setar, 0).toString());
		}
	}

	private void emitirOS() {
		String sql = "insert into tbos(situacao, tipo, equipamento, defeito, servico, tecnico, valor, idcli) values(?,?,?,?,?,?,?,?)";
		try {

			if (textOSVal.getText().trim().isEmpty()) {
				textOSVal.setText("0");
			}
			pst = conexao.prepareStatement(sql);
			pst.setString(1, cmbOSSit.getSelectedItem().toString());
			pst.setString(2, tipo);
			pst.setString(3, textOSEquip.getText());
			pst.setString(4, textOSDef.getText());
			pst.setString(5, textOSServ.getText());
			pst.setString(6, textOSTec.getText());
			pst.setString(7, textOSVal.getText().replace(',', '.'));
			pst.setString(8, textOSId.getText());
			if (textOSId.getText().isEmpty() || textOSEquip.getText().isEmpty() || textOSDef.getText().isEmpty()
					|| cmbOSSit.getSelectedItem().toString().equals(" ")) {
				JOptionPane.showMessageDialog(null, "preencha todos os campos obrigatorios!");
			} else {
				int adicionado = pst.executeUpdate();
				if (adicionado > 0) {
					JOptionPane.showMessageDialog(null, "OS emitida com sucesso!");
					recuperarOS();
					// limparCampos();
					btnOSAdd.setEnabled(false);
					btnOSDel.setEnabled(true);
					btnOSEdit.setEnabled(true);
					btnOSBusca.setEnabled(true);
					btnOSPrinter.setEnabled(true);
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}

	}

	private void limparCampos() {
		textOSCliPesq.setText(null);
		textOSId.setText(null);
		textOSEquip.setText(null);
		textOSDef.setText(null);
		textOSServ.setText(null);
		textOSTec.setText(null);
		textOSVal.setText(null);
		tipo = "Orçamento";
		rbtnOSOrc.setSelected(true);
		btnOSAdd.setEnabled(true);
		btnOSBusca.setEnabled(true);
		btnOSDel.setEnabled(false);
		btnOSEdit.setEnabled(false);
		btnOSPrinter.setEnabled(false);
		textOSCliPesq.setEnabled(true);
		pesquisarCliente();
		textOSNumero.setText(null);
		textOSData.setText(null);
		cmbOSSit.setSelectedIndex(0);
		tipo = "Orçamento";
		rbtnOSOrc.setSelected(true);
	}

	/**
	 * 
	 */
	
	private void pesqOS() {
		String numOS = JOptionPane.showInputDialog("Numero da OS");
		String sql = "select * from tbos where os = " + numOS;
		try {
			pst = conexao.prepareStatement(sql);
			rs = pst.executeQuery();
			if (rs.next()) {
				textOSNumero.setText(rs.getString(1));
				Date data = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString(2));
				String dataFormatada = new SimpleDateFormat("dd/MM/yyyy, HH:mm").format(data);
				textOSData.setText(dataFormatada);
				String rbtnTipo = rs.getString(4);
				if (rbtnTipo.equals("OS")) {
					rbtnOSOS.setSelected(true);
					tipo = "OS";
				} else {
					rbtnOSOrc.setSelected(true);
					tipo = "Orçamento";
				}
				cmbOSSit.setSelectedItem(rs.getString(3));
				textOSEquip.setText(rs.getString(5));
				textOSDef.setText(rs.getString(6));
				textOSServ.setText(rs.getString(7));
				textOSTec.setText(rs.getString(8));
				textOSVal.setText(rs.getString(9));
				textOSId.setText(rs.getString(10));
				pesquisar_cliente_id(textOSId.getText());
				textOSCliPesq.setText(tblOSCli.getModel().getValueAt(0, 1).toString());
				btnOSAdd.setEnabled(false);
				btnOSDel.setEnabled(true);
				btnOSPrinter.setEnabled(true);
				btnOSEdit.setEnabled(true);
				textOSCliPesq.setEnabled(false);
			} else {
				if (numOS != null) {
					JOptionPane.showMessageDialog(null, "OS não encontrada");
				}
			}
		} catch (java.sql.SQLSyntaxErrorException e) {
			// System.out.println(e);
			JOptionPane.showMessageDialog(null, "OS INVALIDA");
		} catch (Exception e2) {
			JOptionPane.showMessageDialog(null, e2);
		}
	}

	/**
	 * 
	 * @param id
	 */
	private void pesquisar_cliente_id(String id) {
		String sql = "select idcli, nomecli, fonecli from tbclientes where idcli = " + id;
		try {
			pst = conexao.prepareStatement(sql);
			rs = pst.executeQuery();
			// uso da biblioteca rs2xml.jar para preencher a tabela
			tblOSCli.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	private void alterar_os() {
		String sql = "update tbos set situacao=?, tipo=?, equipamento=?, defeito=?, servico=?, tecnico=?, valor=? where os=?";
		try {
			if (textOSVal.getText().trim().isEmpty()) {
				textOSVal.setText("0");
			}
			pst = conexao.prepareStatement(sql);
			pst.setString(1, cmbOSSit.getSelectedItem().toString());
			pst.setString(2, tipo);
			pst.setString(3, textOSEquip.getText());
			pst.setString(4, textOSDef.getText());
			pst.setString(5, textOSServ.getText());
			pst.setString(6, textOSTec.getText());
			pst.setString(7, textOSVal.getText().replace(',', '.'));
			pst.setString(8, textOSId.getText());
			if (textOSId.getText().isEmpty() || textOSEquip.getText().isEmpty() || textOSDef.getText().isEmpty()
					|| cmbOSSit.getSelectedItem().toString().equals(" ")) {
				JOptionPane.showMessageDialog(null, "preencha todos os campos obrigatorios!");
			} else {
				int adicionado = pst.executeUpdate();
				if (adicionado > 0) {
					JOptionPane.showMessageDialog(null, "OS editada com sucesso!");
					limparCampos();
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	private void excluir_os() {
		int confirm = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir a os?", "ATENÇÃO",
				JOptionPane.YES_NO_OPTION);
		if (confirm == JOptionPane.YES_OPTION) {
			String sql = "delete from tbos where os=?";
			try {
				pst = conexao.prepareStatement(sql);
				pst.setString(1, textOSNumero.getText());
				int apagado = pst.executeUpdate();
				if (apagado > 0) {
					JOptionPane.showMessageDialog(null, "OS excluida com sucesso");
					limparCampos();
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e);
			}
		}
	}

	private void recuperarOS() {
		String sql = "select max(os) from tbos";
		try {
			pst = conexao.prepareStatement(sql);
			rs = pst.executeQuery();
			if (rs.next()) {
				textOSNumero.setText(rs.getString(1));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	private void imprimirOS() {
		// gera um relatório de clientes
		try {
			// Prepara a impressão de um relatório
			HashMap filtro = new HashMap();
			filtro.put("os", Integer.parseInt(textOSNumero.getText()));
			// exibe o relatório através da classe JasperViewer
			JasperPrint print = JasperFillManager
					.fillReport(getClass().getResourceAsStream("/relatorios/os.jasper"), filtro, conexao);
			JasperViewer.viewReport(print, false);
		} catch (Exception e2) {
			System.out.println(e2);
			JOptionPane.showMessageDialog(null, e2);
		}

	}
}
