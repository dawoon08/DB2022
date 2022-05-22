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
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

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
	String header[] = {"종류", "비용", "보증금", "건물명", "주거 유형", "날짜", "매물 위치"};
	DefaultTableModel model = new DefaultTableModel(header, 0);
	JTable table = new JTable(model);
	
	JScrollPane sc = new JScrollPane(table);

	public Search() { 
		JMenuBar m = new JMenuBar();
        setJMenuBar( m );
            
        JMenu m_search = new JMenu("매물 검색");     
        JMenu m_seeAll = new JMenu("전체 매물 보기"); 
        m.add(m_search);		m.add(m_seeAll);
        
        JMenuItem i_area = new JMenuItem("지역별"); 
        JMenuItem i_type = new JMenuItem("종류별");
        JMenuItem i_price = new JMenuItem("비용별");
        m_search.add(i_area);
        m_search.add(i_type);
        m_search.add(i_price);
        
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
		minPrice.addKeyListener(new MyKeyListener());
		contentPane.add(maxPrice);
		contentPane.add(new JLabel("만원 이하"));
		maxPrice.addKeyListener(new MyKeyListener());
		contentPane.add(enter);
		enter.addActionListener(new MyActionListener());
		enter.addKeyListener(new MyKeyListener());
		
		TableColumnModel columnModel = table.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(40);
		columnModel.getColumn(1).setPreferredWidth(50);
		columnModel.getColumn(2).setPreferredWidth(40);
		columnModel.getColumn(3).setPreferredWidth(50);
		columnModel.getColumn(4).setPreferredWidth(50);
		columnModel.getColumn(5).setPreferredWidth(60);
		columnModel.getColumn(6).setPreferredWidth(250);
		contentPane.add(sc);
		sc.setPreferredSize(new Dimension(600,250));
		
		setSize(700, 700); 
		setVisible(true);
	}
	
	private class MyActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			Button b = (Button)e.getSource();
			model.setRowCount(0);
			searchResult();
		}
	}
	private class MyKeyListener implements KeyListener{
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode(); 
			if(key == KeyEvent.VK_ENTER) {
				model.setRowCount(0);
				searchResult();
			}
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
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
							
							model.addRow(input);
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

