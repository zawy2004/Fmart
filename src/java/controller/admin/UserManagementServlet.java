/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin;

import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@MultipartConfig // ✅ Bắt buộc để xử lý file upload
public class UserManagementServlet extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();  // đảm bảo UserDAO có constructor không tham số
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if (action == null) {
                listUsers(request, response);
            } else {
                switch (action) {
                    case "view" ->
                        viewUser(request, response);
                    case "edit" ->
                        showEditForm(request, response);
                    case "delete" ->
                        deleteUser(request, response);
                    default ->
                        listUsers(request, response);
                }
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("edit".equals(action)) {
                updateUser(request, response);
            } else {
                response.sendRedirect("UserManagementServlet");
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void listUsers(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        List<User> userList = userDAO.getAllUsers();
        request.setAttribute("userList", userList);
        request.getRequestDispatcher("/Admin/customers.jsp").forward(request, response);
    }

    private void viewUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        User user = userDAO.getUserById(id);
        request.setAttribute("user", user);
        request.getRequestDispatcher("/Admin/customer_view.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        User user = userDAO.getUserById(id);
        request.setAttribute("user", user);
        request.getRequestDispatcher("/Admin/customer_edit.jsp").forward(request, response);
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        // Lấy dữ liệu từ form
        int id = Integer.parseInt(request.getParameter("id"));
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phoneNumber");
        String address = request.getParameter("address");
        String gender = request.getParameter("gender");
        String dobStr = request.getParameter("dateOfBirth");
        String roleIdStr = request.getParameter("roleID");
        String studentId = request.getParameter("studentID");
        String department = request.getParameter("department");

        // Giả sử ảnh vẫn giữ nguyên hoặc đã upload xử lý riêng
        //String profileImageUrl = "images/avatar/img-1.jpg"; // Hoặc lấy từ session/file upload
        // Chuyển đổi kiểu dữ liệu
        Date dateOfBirth = null;
        if (dobStr != null && !dobStr.isEmpty()) {
            try {
                dateOfBirth = java.sql.Date.valueOf(dobStr); // yyyy-MM-dd format
            } catch (IllegalArgumentException e) {
                dateOfBirth = null;
            }
        }

        int roleID = roleIdStr != null ? Integer.parseInt(roleIdStr) : 1;

        // Tạo đối tượng User mới
        User user = new User();
        user.setUserID(id);
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPhoneNumber(phone);
        user.setAddress(address);
        user.setGender(gender);
        user.setDateOfBirth(dateOfBirth);
        user.setRoleID(roleID);
        //user.setProfileImageUrl(profileImageUrl);
        user.setStudentID(studentId);
        user.setDepartment(department);
        user.setIsActive(true); // hoặc lấy từ checkbox nếu có

        // Gọi DAO để update
        boolean success = userDAO.updateUser(user);

        if (success) {
            response.sendRedirect("UserManagementServlet"); // quay lại danh sách
        } else {
            request.setAttribute("error", "Cập nhật thất bại");

            request.getRequestDispatcher("/Admin/customer_edit.jsp").forward(request, response);
        }
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        boolean success = userDAO.deleteUser(id);

        if (success) {
            response.sendRedirect("UserManagementServlet");
        } else {
            request.setAttribute("error", "Xoá người dùng thất bại!");
            listUsers(request, response); // hiển thị lại danh sách kèm thông báo
        }
    }

}
