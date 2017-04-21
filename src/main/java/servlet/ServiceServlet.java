package servlet;

import java.io.IOException;
import java.sql.Date;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import redis.clients.jedis.Jedis;
import entities.Student;

public class ServiceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Jedis jedis;

	@Override
	public void init() throws ServletException {
		jedis = new Jedis("localhost");
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		String business = request.getParameter("business");
		switch (business) {
		case "addNewStudent":
			try {
				Student student = new Student(
						encodeToUTF8(request.getParameter("id")),
						encodeToUTF8(request.getParameter("name")),
						Date.valueOf(request.getParameter("birthday")),
						encodeToUTF8(request.getParameter("description")),
						Integer.valueOf(request.getParameter("avgscore")));
				if (null == getExistStudentById(student.getId())) {
					jedis.zadd("students", student.getAvgscore(),
							student.toString());
				}
				request.setAttribute("feedback", "");
				request.getRequestDispatcher("/InitServlet").forward(request,
						response);
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("feedback", "failure");
				request.getRequestDispatcher("/addStudent.jsp").forward(
						request, response);
			}
			break;
		case "updateStudent":
			try {
				Student student = new Student(
						encodeToUTF8(request.getParameter("id")),
						encodeToUTF8(request.getParameter("name")),
						Date.valueOf(request.getParameter("birthday")),
						encodeToUTF8(request.getParameter("description")),
						Integer.valueOf(request.getParameter("avgscore")));
				jedis.zrem("students",
						getExistStudentById(request.getParameter("id"))
								.toString());
				jedis.zadd("students", student.getAvgscore(),
						student.toString());
				request.setAttribute("feedback", "");
				request.getRequestDispatcher("/InitServlet").forward(request,
						response);
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("feedback", "failure");
				request.getRequestDispatcher("/addStudent.jsp").forward(
						request, response);
			}
			break;
		case "deleteStudent":
			jedis.zrem("students",
					getExistStudentById(request.getParameter("id")).toString());
			request.getRequestDispatcher("/InitServlet").forward(request,
					response);
			break;
		case "guideUpdateStudent":
			request.setAttribute("student",
					getExistStudentById(request.getParameter("id")));
			request.getRequestDispatcher("/addStudent.jsp").forward(request,
					response);
			break;
		default:
			break;
		}
	}

	private Student getExistStudentById(String id) {
		Student student = null;
		Set<String> zrange = jedis.zrange("students", 0, -1);
		Iterator<String> iterator = zrange.iterator();
		while (iterator.hasNext()) {
			String value = iterator.next();
			if (value.startsWith(id + "&")) {
				String[] allInfo = value.split("&");
				student = new Student(allInfo[0], allInfo[1],
						Date.valueOf(allInfo[2]), allInfo[3],
						Integer.valueOf(allInfo[4]));
				break;
			}
		}
		return student;
	}

	private String encodeToUTF8(String str) throws Exception {
		return new String(str.getBytes("ISO-8859-1"), "UTF-8");
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}
}
