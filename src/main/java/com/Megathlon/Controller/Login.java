package com.Megathlon.Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Login
 */
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//getParameter("dalam ni kena sebijik dengan apa yang dihantar from Login.jsp")
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		HttpSession session = request.getSession();
		RequestDispatcher dispatcher = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test2?useSSL=false", "root", "Penangfreeschool1!");
			PreparedStatement pst = con.prepareStatement("select * from account where email='" + email + "' and password='" + password + "'");
			ResultSet rs = pst.executeQuery();
			if(rs.next()) {
				session.setAttribute("email", rs.getString("email"));
				session.setAttribute("name", rs.getString("name"));
				//System.out.println("Data inserted");
				dispatcher = request.getRequestDispatcher("jsp/homepage.jsp");
			}else {
				session.setAttribute("status", "failed");
				System.out.println("Wrong email or password");

				//dispatcher = request.getRequestDispatcher("jsp/Login.jsp");
				response.sendRedirect("jsp/Login.jsp");
			}
			dispatcher.forward(request, response);
		} catch(Exception ex){
			ex.printStackTrace();
		}
	}

}