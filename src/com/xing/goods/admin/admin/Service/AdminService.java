package com.xing.goods.admin.admin.Service;

import java.sql.SQLException;

import com.xing.goods.admin.admin.dao.AdminDao;
import com.xing.goods.admin.admin.domian.Admin;

public class AdminService {
private AdminDao adminDao = new AdminDao();


/**
 * 登录功能
 * @param admin
 * @return
 * @throws SQLException 
 */


public Admin  login(Admin admin) throws SQLException{
	
	return adminDao.find(admin.getAdminname(), admin.getAdminpwd());
	
}






}
