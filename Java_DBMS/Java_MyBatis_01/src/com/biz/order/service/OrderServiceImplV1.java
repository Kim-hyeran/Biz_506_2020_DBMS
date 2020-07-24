package com.biz.order.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.biz.order.config.DBConnection;
import com.biz.order.mapper.OrderDao;
import com.biz.order.model.OrderVO;

public class OrderServiceImplV1 implements OrderService {
	
	private SqlSession sqlSession;
	private OrderDao orderDao;
	
	public OrderServiceImplV1() {
		/*
		 * openSession() 매개변수
		 * true : insert, update, delete를 수행 후, 자동으로 commit 수행
		 * 		CUD 수행 후 DB에 결과를 확실히 저장
		 * false, 생략 : auto commit 미수행
		 */
		sqlSession=DBConnection.getSqlSessionFactory().openSession(true);
		
		orderDao=sqlSession.getMapper(OrderDao.class);
	}

	@Override
	public List<OrderVO> selectAll() {
		List<OrderVO> orderList=orderDao.selectAll();
				
		return orderList;
	}

	@Override
	public OrderVO findBySeq(long seq) {
		OrderVO orderVO=orderDao.findBySeq(seq);
		
		return orderVO;
	}

	@Override
	public int insert(OrderVO orderVO) {
		//데이터를 추가할 때, 추가 시점의 시간을 컴퓨터 시계를 참조하여 생성
		Date date=new Date(System.currentTimeMillis());
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		//날짜 칼럼 세팅
		orderVO.setO_date(df.format(date));
		
		int total=orderVO.getO_price()*orderVO.getO_qty();
		orderVO.setO_total(total);
		
		int ret=orderDao.insert(orderVO);
		
		return ret;
	}

	@Override
	public int update(OrderVO orderVO) {
		int total=orderVO.getO_price()*orderVO.getO_qty();
		orderVO.setO_total(total);
		
		int ret=orderDao.update(orderVO);

		return ret;
	}

	@Override
	public int delete(long seq) {
		int ret=orderDao.delete(seq);
		
		return ret;
	}
	
}