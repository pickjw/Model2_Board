package sqlmap;

import java.io.Reader;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MybatisManager {
	
	//SqlSessionFactoryBuilder => SqlSessionFactory =>SqlSession
	//SqlSession 객체 생성기
	
	//싱글톤 패턴
	private static SqlSessionFactory instance;
	//싱글톤 패턴
	private MybatisManager() {
   // private 설정
	}
	
	
	public static SqlSessionFactory getInstance(){
		
		Reader reader = null;
		
			try {
				//Java Resources의 src                 //sqlmap 패키지에 있는 sqlMapConfig.xml 파일을 읽어라
				reader = Resources.getResourceAsReader("sqlmap/sqlMapConfig.xml");
				//SqlSessionFactory  생성기
				instance = new SqlSessionFactoryBuilder().build(reader);
				System.out.println("MybatisManager연결o");//연결확인용
				
			}catch(Exception e){
				e.printStackTrace();
			}finally {
				try {
					if(reader != null) reader.close();
					
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			return instance;
	}
}

