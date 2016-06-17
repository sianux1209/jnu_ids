package View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
 * url 추가하고, 삭제하고 비밀번호 대조
 * @return
 */
public class UrlPanel extends JPanel {
	private JTextField ipField; //프로토콜
	private final String header[] = { "차단 도메인 목록" };

	private int activeRow = -1;
	private String admin = "1234"; //관리자 비밀번호를 임시로 1234로 함
	private String result;

	public UrlPanel(ArrayList<UrlDto> dtos) {
		Dimension dim = new Dimension(500, 300);
		setLocation(200, 400);
		setPreferredSize(new Dimension(500, 251));
		setLayout(new BorderLayout());
		System.out.println(dtos.size());
		
		String[][] contents = new String[dtos.size()][1];
		if (dtos != null) {
			int index = 0;
			for (UrlDto dto : dtos) {
				contents[index++][0] = dto.getUrl();
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

		JScrollPane scrollpane = new JScrollPane(table);

		JPanel panel = new JPanel();

		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		ipField = new JTextField(5);

		panel.add(ipField);


		table.setFocusable(true);
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				JTable table = (JTable) e.getSource();
				activeRow = table.getSelectedRow();
				System.out.println(activeRow);
			}
		});

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

		
		JButton addBtn = new JButton("추가");
		addBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				
				result = JOptionPane.showInputDialog("암호를 입력하세요.");
				if (result.equals(admin)) 
					JOptionPane.showMessageDialog(null, "적용되었습니다.");
				else
					JOptionPane.showMessageDialog(null, "암호가 틀립니다.");

				String inputStr[] = new String[1];

				inputStr[0] = ipField.getText();


				if (checkDomain(inputStr)) {
					model.addRow(inputStr);
					ipField.setText("");
					ArrayList<UrlDto> dtos = Dao.getInstance().getUrl();
					dtos.add(new UrlDto(inputStr[0]));
					Dao.getInstance().saveUrl(dtos);
					JOptionPane.showMessageDialog(null, "추가되었습니다.");
				} else {
					JOptionPane.showMessageDialog(null, "올바른 도메인을 입력하세요.");
				}
			}
		});

		JButton cancelBtn = new JButton("삭제");
		cancelBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() < 0) {
					return;
				} else {
					int row = table.getSelectedRow();
					ArrayList<UrlDto> dtos = Dao.getInstance().getUrl();
					dtos.remove(row);
					Dao.getInstance().saveUrl(dtos);
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

	private boolean checkDomain(String... domain) {
		Pattern urlPattern = Pattern.compile("^(((http(s?)):\\/\\/)?)([^:\\/\\s]+)(:([^\\/]*))?((\\/[^\\s/\\/]+)*)?\\/?([^#\\s\\?]*)(\\?([^#\\s]*))?(#(\\w*))?$");
		Matcher mc = urlPattern.matcher(domain[0]);
		if(mc.matches()){
			return true;
		}
		return false;
	}


}

