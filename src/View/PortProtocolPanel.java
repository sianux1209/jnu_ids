package View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 * 작성자 민진,민정 
 * port번호, protocol 추가하고, 삭제하고 비밀번호 대조
 * @return
 */
public class PortProtocolPanel extends JPanel {
	private JTextField nameField; //프로토콜
	private JTextField subject1;	//포트번호
	private String header[] = { "프로토콜", "포트번호" };

	private int activeRow = -1;
	private String admin = "1234"; //관리자 비밀번호를 임시로 1234로 함
	private String result;

	public PortProtocolPanel(ArrayList<PortProtocolDto> dtos) {
		Dimension dim = new Dimension(500, 300);
		setLocation(200, 400);
		setPreferredSize(new Dimension(500, 251));

		setLayout(new BorderLayout());
		
		String[][] contents = new String[dtos.size()][2];
		System.out.println(dtos.size());
		if (dtos != null) {
			int index = 0;
			for (PortProtocolDto dto : dtos) {
				contents[index][0] = dto.getProtocol();
				contents[index++][1] = dto.getPort();
			}
		}
		
		final DefaultTableModel model = new DefaultTableModel(contents, header) {
			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};
		final JTable table = new JTable(model);
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setResizingAllowed(false);

		// DefaultTableCellHeaderRenderer 생성 (가운데 정렬을 위한)
		DefaultTableCellRenderer tScheduleCellRenderer = new DefaultTableCellRenderer();

		// DefaultTableCellHeaderRenderer의 정렬을 가운데 정렬로 지정
		tScheduleCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);

		// 정렬할 테이블의 ColumnModel을 가져옴
		TableColumnModel tcmSchedule = table.getColumnModel();

		// 반복문을 이용하여 테이블을 가운데 정렬로 지정
		for (int i = 0; i < tcmSchedule.getColumnCount(); i++) {
			tcmSchedule.getColumn(i).setCellRenderer(tScheduleCellRenderer);
		}

		JScrollPane scrollpane = new JScrollPane(table);

		JPanel panel = new JPanel();

		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		nameField = new JTextField(5);
		subject1 = new JTextField(3);


		panel.add(nameField);
		panel.add(subject1);

		table.setFocusable(true);
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				JTable table = (JTable) e.getSource();
				activeRow = table.getSelectedRow();
				System.out.println(activeRow);
			}
		});
		
		JButton addBtn = new JButton("추가");
		addBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				
				result = JOptionPane.showInputDialog("암호를 입력하세요.");
				if (result.equals(admin)) 
					JOptionPane.showMessageDialog(null, "적용되었습니다.");
				else
					JOptionPane.showMessageDialog(null, "암호가 틀립니다.");

				String inputStr[] = new String[2];

				inputStr[0] = nameField.getText();
				inputStr[1] = subject1.getText();


				if (checkWhitespace(inputStr)) {
					model.addRow(inputStr);
					nameField.setText("");
					subject1.setText("");
					ArrayList<PortProtocolDto> dtos = Dao.getInstance().getPortProtocol();
					dtos.add(new PortProtocolDto(inputStr[1], inputStr[0]));
					Dao.getInstance().savePortProtocol(dtos);
					JOptionPane.showMessageDialog(null, "추가되었습니다.");
				} else {
					JOptionPane.showMessageDialog(null, "모든 항목을 입력하세요.");
				}
			}
		});

		JButton cancelBtn = new JButton("삭제");
		cancelBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() == -1) {
					return;
				} else {
					int row = table.getSelectedRow();
					ArrayList<PortProtocolDto> dtos = Dao.getInstance().getPortProtocol();
					dtos.remove(row);
					Dao.getInstance().savePortProtocol(dtos);
					model.removeRow(table.getSelectedRow());
				}
			}
		});
		panel.add(addBtn);
		panel.add(cancelBtn);
		add(scrollpane, BorderLayout.CENTER);
		add(panel, BorderLayout.SOUTH);

		setVisible(true);
	}

	private boolean checkWhitespace(String... strs) {
		for (String str : strs) {
			if (str == null || str.trim().equals(""))

				return false;
		}
		return true;
	}
}

