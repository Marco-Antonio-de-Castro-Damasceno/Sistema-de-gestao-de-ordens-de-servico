package br.com.infox.telas;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import br.com.infox.dal.moduloConexao;
import net.proteanit.sql.DbUtils;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TelaRelServ extends JInternalFrame {

	// Instanciando objetos necessárias
	// para a conexão com o banco de dados
	Connection conexao = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	private static final long serialVersionUID = 1L;

	private List<JCheckBox> situacaoLista = new ArrayList<JCheckBox>();
	private List<JCheckBox> tipoLista = new ArrayList<JCheckBox>();
	
	private JCheckBox chbxOS;
	private JCheckBox chbxOrc;
	
	private JTextField textCliNomePesq;
	private JTable tblCli;
	private JTextField textIdCliPesq;
	private JTextField textUsuNomePesq;
	private JTable tblUsu;
	private JTextField textIdUsuPesq;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaRelServ frame = new TelaRelServ();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TelaRelServ() {
		setMaximizable(true);
		setIconifiable(true);
		setClosable(true);
		setBounds(new Rectangle(0, 0, 719, 538));
		setTitle("Relatórios");
		setBounds(0, 0, 719, 538);
		getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBounds(20, 21, 172, 85);
		getContentPane().add(panel);
		panel.setLayout(null);

		chbxOS = new JCheckBox("OS");
		chbxOS.setBounds(10, 32, 97, 23);
		panel.add(chbxOS);

		JLabel lblNewLabel = new JLabel("TIPO:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(10, 11, 46, 14);
		panel.add(lblNewLabel);

		chbxOrc = new JCheckBox("Orçamento");
		chbxOrc.setBounds(10, 55, 97, 23);
		panel.add(chbxOrc);

		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_1.setBounds(20, 126, 172, 230);
		getContentPane().add(panel_1);

		JCheckBox chbxNaBancada = new JCheckBox("Na bancada");
		chbxNaBancada.setBounds(6, 32, 146, 23);
		panel_1.add(chbxNaBancada);

		JLabel lblSituao = new JLabel("SITUAÇÃO");
		lblSituao.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblSituao.setBounds(10, 11, 85, 14);
		panel_1.add(lblSituao);

		JCheckBox chbxEntregaOk = new JCheckBox("Entrega OK");
		chbxEntregaOk.setBounds(6, 60, 146, 23);
		panel_1.add(chbxEntregaOk);
		
		JCheckBox chbxOrcReprovado = new JCheckBox("Orçamento reprovado");
		chbxOrcReprovado.setBounds(6, 88, 146, 23);
		panel_1.add(chbxOrcReprovado);
		
		JCheckBox chbxAbandonadoPeloCliente = new JCheckBox("Abandonado pelo cliente");
		chbxAbandonadoPeloCliente.setBounds(6, 172, 146, 23);
		panel_1.add(chbxAbandonadoPeloCliente);
		
		JCheckBox chbxAguardandoPecas = new JCheckBox("Aguardando peças");
		chbxAguardandoPecas.setBounds(6, 144, 146, 23);
		panel_1.add(chbxAguardandoPecas);
		
		JCheckBox chbxAguardandoAprovacao = new JCheckBox("Aguardando aprovação");
		chbxAguardandoAprovacao.setBounds(6, 116, 146, 23);
		panel_1.add(chbxAguardandoAprovacao);
		
		JCheckBox chbxRetornou = new JCheckBox("Retornou");
		chbxRetornou.setBounds(6, 200, 146, 23);
		panel_1.add(chbxRetornou);
		
		JPanel panel_1_1 = new JPanel();
		panel_1_1.setLayout(null);
		panel_1_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Cliente", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1_1.setBounds(212, 22, 468, 162);
		getContentPane().add(panel_1_1);
		
		textCliNomePesq = new JTextField();
		textCliNomePesq.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				pesquisarClientePeloNome();
			}
		});
		textCliNomePesq.setColumns(10);
		textCliNomePesq.setBounds(10, 21, 395, 20);
		panel_1_1.add(textCliNomePesq);
		
		tblCli = new JTable() {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tblCli.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setarCamposCliente();
			}
		});
		tblCli.setBounds(10, 41, 395, 93);
		panel_1_1.add(tblCli);
		
		JLabel lblNewLabel_4 = new JLabel("ID:");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_4.setBounds(425, 62, 25, 14);
		panel_1_1.add(lblNewLabel_4);
		
		textIdCliPesq = new JTextField();
		textIdCliPesq.setColumns(10);
		textIdCliPesq.setBounds(415, 78, 39, 20);
		panel_1_1.add(textIdCliPesq);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pesquisarClientePeloId();
			}
		});
		btnNewButton.setIcon(new ImageIcon(TelaRelServ.class.getResource("/br/com/infox/icones/busca32_32.png")));
		btnNewButton.setBounds(412, 11, 46, 41);
		panel_1_1.add(btnNewButton);
		
		JPanel panel_1_2 = new JPanel();
		panel_1_2.setLayout(null);
		panel_1_2.setBorder(new TitledBorder(null, "Técnico", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1_2.setBounds(212, 195, 468, 161);
		getContentPane().add(panel_1_2);
		
		textUsuNomePesq = new JTextField();
		textUsuNomePesq.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				pesquisarUsuarioPeloNome();
			}
		});
		textUsuNomePesq.setColumns(10);
		textUsuNomePesq.setBounds(10, 21, 395, 20);
		panel_1_2.add(textUsuNomePesq);
		
		tblUsu = new JTable() {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tblUsu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setarCamposUsuario();
			}
		});
		tblUsu.setBounds(10, 39, 395, 91);
		panel_1_2.add(tblUsu);
		
		JLabel lblNewLabel_4_1 = new JLabel("ID:");
		lblNewLabel_4_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_4_1.setBounds(425, 62, 25, 14);
		panel_1_2.add(lblNewLabel_4_1);
		
		textIdUsuPesq = new JTextField();
		textIdUsuPesq.setColumns(10);
		textIdUsuPesq.setBounds(415, 78, 39, 20);
		panel_1_2.add(textIdUsuPesq);
		
		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pesquisarUsuarioPeloId();
			}
		});
		btnNewButton_1.setIcon(new ImageIcon(TelaRelServ.class.getResource("/br/com/infox/icones/busca32_32.png")));
		btnNewButton_1.setBounds(412, 11, 46, 41);
		panel_1_2.add(btnNewButton_1);
		
		JButton btnOSPrinter = new JButton("");
		btnOSPrinter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				impressaoPersonalizada();
			}
		});
		btnOSPrinter.setToolTipText("imprimir");
		btnOSPrinter.setIcon(new ImageIcon(TelaRelServ.class.getResource("/br/com/infox/icones/printer.png")));
		btnOSPrinter.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnOSPrinter.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnOSPrinter.setBounds(598, 401, 82, 82);
		getContentPane().add(btnOSPrinter);
		
		situacaoLista.add(chbxNaBancada);
		situacaoLista.add(chbxEntregaOk);
		situacaoLista.add(chbxAguardandoAprovacao);
		situacaoLista.add(chbxOrcReprovado);
		situacaoLista.add(chbxRetornou);
		situacaoLista.add(chbxAbandonadoPeloCliente);
		situacaoLista.add(chbxAguardandoPecas);
		
		tipoLista.add(chbxOS);
		tipoLista.add(chbxOrc);
		
		conexao = moduloConexao.conector();
	}
	
	public void impressaoPersonalizada() {
		try {
		List<String> tipo = new ArrayList<>();
		List<String> situacao = new ArrayList<>();
		Map<String, Object> parameters = new HashMap<>();
		String idCliStr = textIdCliPesq.getText();
		Integer idCli = null;
		if(!idCliStr.isEmpty()) {
			idCli = Integer.parseInt(idCliStr);
		}
		for (JCheckBox chbx: tipoLista) {
			if (chbx.isSelected()) {
				tipo.add(chbx.getText());
			}
		}
		for (JCheckBox chbx: situacaoLista) {
			if (chbx.isSelected()) {
				situacao.add(chbx.getText());
			}
		}
		parameters.put("tipo", tipo);
		parameters.put("situacao", situacao);
		parameters.put("idcli", idCli);		
			// Prepara a impressão de um relatório
		JasperPrint print = JasperFillManager.fillReport(getClass().getResourceAsStream("/relatorios/relatorio1.jasper"),
				parameters, conexao);
	
			// exibe o relatório através da classe JasperViewer
		JasperViewer.viewReport(print, false);

		}
		catch (Exception e2) {
			JOptionPane.showMessageDialog(null, e2);
		}
	}
	/**
	 * Método para pesquisar Cliente pelo nome
	 */
	private void pesquisarClientePeloNome() {
		String sql = "select idcli, nomecli, emailcli from tbclientes where nomecli like ?";
		try {
			pst = conexao.prepareStatement(sql);
			pst.setString(1, textCliNomePesq.getText()+"%");
			rs=pst.executeQuery();
			tblCli.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	/**
	 * Indica se o usuário pesquisado, através do seu id, foi encontrado
	 * no banco de dados, ou não. Retornando true, se o usuário foi encontrado
	 * e false se o usuário nao foi encontrado
	 */
	private boolean pesquisarClientePeloId() {
		String sql = "select idcli, nomecli, emailcli from tbclientes where idcli = ?";
		try {
			pst = conexao.prepareStatement(sql);
			pst.setString(1, textIdCliPesq.getText());
			rs=pst.executeQuery();
			if(rs.next()) {
				textCliNomePesq.setText(rs.getString(2));
				return true;
			}else {
				JOptionPane.showMessageDialog(null, "cliente nao cadastrado!");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		return false;
	}
	private void setarCamposCliente() {
		int setar = tblCli.getSelectedRow();
		if(setar>=0) {
			textIdCliPesq.setText(tblCli.getModel().getValueAt(setar, 0).toString());
			textCliNomePesq.setText(tblCli.getModel().getValueAt(setar, 1).toString());
		}
	}
	/**
	 * Método para pesquisar Cliente pelo nome
	 */
	private void pesquisarUsuarioPeloNome() {
		String sql = "select iduser, usuario from tbusuarios where usuario like ?";
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
	private boolean pesquisarUsuarioPeloId() {
		String sql = "select iduser, usuario from tbusuarios where iduser = ?";
		try {
			pst = conexao.prepareStatement(sql);
			pst.setString(1, textIdUsuPesq.getText());
			rs=pst.executeQuery();
			if(rs.next()) {
				textUsuNomePesq.setText(rs.getString(2));
				return true;
			}else {
				JOptionPane.showMessageDialog(null, "usuario nao cadastrado!");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		return false;
	}
	private void setarCamposUsuario() {
		int setar = tblUsu.getSelectedRow();
		if(setar>=0) {
			textIdUsuPesq.setText(tblUsu.getModel().getValueAt(setar, 0).toString());
			textUsuNomePesq.setText(tblUsu.getModel().getValueAt(setar, 1).toString());
		}
	}
}
