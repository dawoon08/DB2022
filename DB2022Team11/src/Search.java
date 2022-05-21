import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;

public class Search extends JFrame{
	
	private PreparedStatement pstmt;
	private ResultSet rset;
	
	JTextField area = new JTextField(15); //임시
	
	//매물 종류 선택 박스
	String[] rentType= {"매매","전세","월세"};
	JComboBox<String> rent_type;
	//최소 최대 비용
	JTextField minPrice = new JTextField(10);
	JTextField maxPrice = new JTextField(10);
	Button enter = new Button("Enter");
	//결과 출력 테이블 생성
	String header[] = {"종류", "비용", "보증금", "건물명", "주거 유형", "매물 나온 날짜", "매물 위치"};
	DefaultTableModel model = new DefaultTableModel(header, 0);
	JTable table = new JTable(model);
	DefaultTableModel m = (DefaultTableModel)table.getModel();
	JScrollPane sc = new JScrollPane(table);
	
	public Search() { //frame 생성자
		
		setTitle("매물 검색");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container contentPane = getContentPane();
		contentPane.setLayout(new FlowLayout());
		contentPane.setBackground(new Color(244,244,244));
		
		contentPane.add(area);
		
		rent_type = new JComboBox(rentType);
		contentPane.add(rent_type);
		
	    contentPane.add(minPrice);
		contentPane.add(new JLabel("만원 이상"));
		contentPane.add(maxPrice);
		contentPane.add(new JLabel("만원 이하"));
		contentPane.add(enter);
		enter.addActionListener(new MyActionListener());
		contentPane.add(sc);
		sc.setPreferredSize(new Dimension(600,250));
		
		setSize(700, 700); 
		setVisible(true);
	}
	
	//버튼클릭이벤트
	private class MyActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			Button b = (Button)e.getSource();
			//버튼 클릭시 결과 출력
			m.setRowCount(0);
			searchResult();
		}
	}
	
	//결과 출력 함수
	public void searchResult() {
	    
	    String area_view = area.getText();
	    
		try {   Connection conn = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/db2022team11", "db2022team11", "db2022team11");
				Statement stmt = conn.createStatement();
				
					try { //transaction??
						conn.setAutoCommit(false);
						//검색 쿼리
						pstmt = conn.prepareStatement("select rent_type, price, deposit, building_name, building_type, sale_date, address from DB2022_SALE join "
								+area_view+ " on DB2022_SALE.building_id = "+area_view+".building_id where rent_type = ? and price >= ? and price <= ? "); 
						pstmt.setString(1, (String)rent_type.getSelectedItem());
						
						if(minPrice.getText().isEmpty()) {
							pstmt.setInt(2, 0);
						}
						else {
							pstmt.setInt(2, Integer.parseInt(minPrice.getText()));
						}
						if(maxPrice.getText().isEmpty()) {
							pstmt.setInt(3,  10000000);
						}
						else {
							pstmt.setInt(3, Integer.parseInt(maxPrice.getText()));
						}
					    rset = pstmt.executeQuery();
					    
					    
						//검색 결과 출력
						while(rset.next()) {
							
							String[] input = new String[7];
							input[0] = rset.getString("rent_type");
							input[1] = Integer.toString(rset.getInt("price"));
							input[2] = Integer.toString(rset.getInt("deposit"));
							input[3] = rset.getString("building_name");
							input[4] = rset.getString("building_type");
							input[5] = rset.getString("sale_date");
							input[6] = rset.getString("address");
							
							m.addRow(input);
						}
						conn.commit();
						
					} catch(SQLException se) {
						se.printStackTrace();
						System.out.println("Rolling back data here");
							try {	
									if(conn!=null)
									conn.rollback();
							}catch(SQLException se2) {
								se2.printStackTrace();
							}
					}
			
					conn.setAutoCommit(true);
		
		
		}catch(SQLException se) {
			se.printStackTrace();
		}
	}

		
	public static void main(String[] args) {
		Search search = new Search(); 
	}

}

