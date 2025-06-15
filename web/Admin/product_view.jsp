<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="model.Product" %>
<%@ page import="dao.ProductDAO" %>

<%
    String productIDStr = request.getParameter("id");
    int productID = Integer.parseInt(productIDStr);

    // L?y s?n ph?m t? c? s? d? li?u
    ProductDAO productDAO = new ProductDAO();
    Product product = productDAO.getProductById(productID);

    // N?u s?n ph?m kh�ng t?n t?i, chuy?n h??ng v? danh s�ch s?n ph?m
    if (product == null) {
        response.sendRedirect("products.jsp");
    }
%>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description-gambolthemes" content="">
    <meta name="author-gambolthemes" content="">
    <title>FMart Supermarket Admin</title>
    <link href="css/styles.css" rel="stylesheet">
    <link href="css/admin-style.css" rel="stylesheet">
    <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
</head>

<body class="sb-nav-fixed">
    <nav class="sb-topnav navbar navbar-expand navbar-light bg-clr">
        <a class="navbar-brand logo-brand" href="index.jsp">FMart Supermarket</a>
        <!-- Navigation Menu -->
    </nav>

    <div id="layoutSidenav">
        <div id="layoutSidenav_nav">
            <!-- Sidebar Menu -->
        </div>

        <div id="layoutSidenav_content">
            <main>
                <div class="container-fluid">
                    <h2 class="mt-30 page-title">Product Details</h2>
                    <ol class="breadcrumb mb-30">
                        <li class="breadcrumb-item"><a href="index.jsp">Dashboard</a></li>
                        <li class="breadcrumb-item"><a href="products.jsp">Products</a></li>
                        <li class="breadcrumb-item active">Product View</li>
                    </ol>

                    <div class="row">
                        <div class="col-lg-5 col-md-6">
                            <div class="card card-static-2 mb-30">
                                <div class="card-body-table pt-4">
                                    <div class="product-owner-content-left text-center pd-20">
                                        <!-- Hi?n th? h�nh ?nh s?n ph?m -->
                                        <div class="product_img">
                                            <img src="images/product/img-1.jpg" alt="">
                                        </div>
                                        <ul class="product-dt-purchases">
                                            <li>
                                                <div class="product-status">
                                                    Price <span class="badge-item-2 badge-status">${product.sellingPrice}</span>
                                                </div>
                                            </li>
                                            <li>
                                                <div class="product-status">
                                                    Category <span class="badge-item-2 badge-status">${product.categoryID}</span>
                                                </div>
                                            </li>
                                        </ul>
                                        <div class="product-owner-dts">
                                            <div class="product-owner-dt-list">
                                                <span class="left-dt">SKU</span>
                                                <span class="right-dt">${product.sku}</span>
                                            </div>
                                            <div class="product-owner-dt-list">
                                                <span class="left-dt">Brand</span>
                                                <span class="right-dt">${product.brand}</span>
                                            </div>
                                            <div class="product-owner-dt-list">
                                                <span class="left-dt">Description</span>
                                                <span class="right-dt">${product.description}</span>
                                            </div>
                                            <div class="product-owner-dt-list">
                                                <span class="left-dt">Created</span>
                                                <span class="right-dt">${product.createdDate}</span>
                                            </div>
                                        </div>
                                    </div>
                                    <a href="edit_product.jsp?id=${product.productID}" class="btn btn-primary">Edit Product</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </main>

            <footer class="py-4 bg-footer mt-auto">
                <div class="container-fluid">
                    <div class="d-flex align-items-center justify-content-between small">
                        <div class="text-muted-1">� 2025 <b>FMart Supermarket</b></div>
                    </div>
                </div>
            </footer>
        </div>
    </div>

    <script src="js/jquery.min.js"></script>
    <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
    <script src="js/scripts.js"></script>
</body>
</html>
