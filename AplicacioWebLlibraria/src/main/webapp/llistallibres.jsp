<%-- 
    Document   : llistallibres
    Created on : 5 dic 2024, 17:40:43
    Author     : User
--%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.*, javax.sql.*, java.util.*" %>

<!DOCTYPE html>
<html lang="ca">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Llista de Llibres</title>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            padding: 10px;
            border: 1px solid #ddd;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>
    <h1>Llista de Llibres de la Meva Llibreria</h1>
    <h1>Punt 6.4</h1>

    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Títol</th>
                <th>ISBN</th>
                <th>Any de Publicació</th>
                <th>ID Editorial</th>
            </tr>
        </thead>
        <tbody>
            <% 
                // Configuració de la connexió
                String url = "jdbc:mysql://192.168.0.100:3306/llibreria";
                String user = "root";
                String password = "jakepollo360";

                // Establir la connexió a la base de dades
                Connection conn = null;
                Statement stmt = null;
                ResultSet rs = null;

                try {
                    // Carregar el driver JDBC per MySQL
                    Class.forName("com.mysql.cj.jdbc.Driver");

                    // Establir la connexió
                    conn = DriverManager.getConnection(url, user, password);

                    // Crear una consulta SQL
                    String sql = "SELECT id, titol, isbn, any_publicacio, id_editorial FROM llibres";
                    stmt = conn.createStatement();
                    rs = stmt.executeQuery(sql);

                    // Mostrar els resultats de la consulta
                    while (rs.next()) {
                        int id = rs.getInt("id");
                        String titol = rs.getString("titol");
                        String isbn = rs.getString("isbn");
                        int anyPublicacio = rs.getInt("any_publicacio");
                        int idEditorial = rs.getInt("id_editorial");
            %>
                        <tr>
                            <td><%= id %></td>
                            <td><%= titol %></td>
                            <td><%= isbn %></td>
                            <td><%= anyPublicacio %></td>
                            <td><%= idEditorial %></td>
                        </tr>
            <% 
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    out.println("<tr><td colspan='5'>Error al carregar les dades</td></tr>");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    out.println("<tr><td colspan='5'>Driver no trobat</td></tr>");
                } finally {
                    // Tancar la connexió i els recursos
                    try {
                        if (rs != null) rs.close();
                        if (stmt != null) stmt.close();
                        if (conn != null) conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            %>
        </tbody>
    </table>
</body>
</html>
