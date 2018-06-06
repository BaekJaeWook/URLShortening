package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.DDDao;

@Service
public class DDServiceImpl implements DDservice {

	@Autowired
	DDDao ddDao;

	public String getDDMessage(String id) {
		return ddDao.getDDMessage(id);
	}
}
