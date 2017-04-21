package servlet;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import redis.clients.jedis.Jedis;
import entities.Student;

public class InitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Jedis jedis;

	@Override
	public void init() throws ServletException {
		jedis = new Jedis("localhost");
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setAttribute("page", null == request.getParameter("page") ? 1
				: Integer.valueOf(request.getParameter("page")));
		request.setAttribute("students",
				getExistStudent((Integer) request.getAttribute("page")));
		request.setAttribute("pageCount", jedis.zcard("students") / 10
				+ (0 == jedis.zcard("students") % 10 ? 0 : 1));
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	private ArrayList<Student> getExistStudent(int page) {
		ArrayList<Student> students = new ArrayList<Student>();
		Set<String> zrange = jedis.zrevrange("students", (page - 1) * 10,
				page * 10 - 1);
		Iterator<String> iterator = zrange.iterator();
		while (iterator.hasNext()) {
			String[] allInfo = iterator.next().split("&");
			Student student = new Student(allInfo[0], allInfo[1],
					Date.valueOf(allInfo[2]), allInfo[3],
					Integer.valueOf(allInfo[4]));
			students.add(student);
		}
		return students;
	}
}
