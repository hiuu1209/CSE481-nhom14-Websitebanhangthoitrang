<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Trang chủ</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <link rel="stylesheet" type="text/css" href="//cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.css"/>
        <link rel="stylesheet" type="text/css" href="Customer/CSS/style.css" />
        <link rel="stylesheet" type="text/css" href="bootstrap/css/bootstrap.css" />
       <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css" integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p" crossorigin="anonymous" />
        <script type="text/javascript" src="JS/jquery-3.3.1.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script type="text/javascript" src="JS/popper.min.js"></script>
        <script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="Customer/JS/script.js"></script>
        <script type="text/javascript" src="//cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.min.js"></script>
    </head>
    <body>
        <jsp:include page="Header.jsp"></jsp:include>
        <!-- slide -->
            <div id="carouselExampleIndicators" class="carousel slide mt-0" data-ride="carousel">
                <ol class="carousel-indicators">
                    <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
                    <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
                    <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
                </ol>
                <div class="carousel-inner">
                    <div class="carousel-item active">
                        <img class="d-block w-100" src="img/slide1.jpg" alt="" />
                    </div>
                    <div class="carousel-item">
                        <img class="d-block w-100" src="img/slide2.jpg" alt="" />
                    </div>
                    <div class="carousel-item">
                        <img class="d-block w-100" src="img/slide3.jpg" alt="" />
                    </div>
                    <div class="carousel-item">
                        <img class="d-block w-100" src="img/slide4.jpg" alt="" />
                    </div>
                </div>
                <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                    <span class="sr-only">Previous</span>
                </a>
                <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                    <span class="sr-only">Next</span>
                </a>
            </div>
            <!-- end slide -->

            <!-- banner -->
            <div class="container-fluid mt-2">
                <div class="row">
                    <div class="col-md-6 pl-0 pr-0">
                        <a class="w-100 h-auto" href=""><img class="w-100 h-auto" src="img/banner1.jpg" alt="" /></a>
                    </div>
                    <div class="col-md-6 pl-0 pr-0">
                        <a class="w-100 h-auto" href=""><img class="w-100 h-auto" src="img/banner2.jpg" alt="" /></a>
                    </div>
                </div>
            </div>

            <!-- list product -->
            <div class="container-fluid p-0">
                <div class="row m-0 bg-dark pt-3 pb-2">
                    <div class="col-md-4">
                        <h3 class="text-white font-weight-bold" style="font-size: 25px !important;">SẢN PHẨM MỚI</h3>
                    </div>
                    <div class="col-md-8 p-0">
                        <div class="row m-0">
                            <div class="col-3">
                                <a class="w-100 h-auto text-decoration-none text-white" href="Product?chucNang=hienThiSanPhamMoi&danhMucSanPham=Nữ">NỮ</a>
                            </div>
                            <div class="col-3">
                                <a class="w-100 h-auto text-decoration-none text-white" href="Product?chucNang=hienThiSanPhamMoi&danhMucSanPham=Nam">NAM</a>
                            </div>
                            <div class="col-3">
                                <a class="w-100 h-auto text-decoration-none text-white" href="Product?chucNang=hienThiSanPhamMoi&danhMucSanPham=Bé gái">BÉ GÁI</a>
                            </div>
                            <div class="col-3">
                                <a class="w-100 h-auto text-decoration-none text-white" href="Product?chucNang=hienThiSanPhamMoi&danhMucSanPham=Bé trai">BÉ TRAI</a>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- Hiển thị sản phẩm -->
                <div class="container-fluid mt-3 p-0">
                    <div class="row w-100 m-0" id="list-product">
                        <c:forEach items="${listProduct}" var="product" >
                            <div class="product col-md-4 col-6 p-1 p-md-3">
                                <div class="card">
                                    <c:if test = "${product.productSale > 0}">
                                        <div class='ribbon-wrapper'>
                                            <div class='ribbon'>Giảm ${product.productSale}%</div>
                                        </div>
                                    </c:if>
                                    <a href="Product?chucNang=chiTietSanPham&productId=${product.productId}"><img class="card-img-top p-md-3" src="img/${product.image}" alt="product 1" /></a>
                                    <div class="card-body">
                                        <p class="card-title text-center text-truncate"><a href="Product?chucNang=chiTietSanPham&productId=${product.productId}" class="text-decoration-none">${product.productName}</a></p>
                                        <h5 class="card-text font-weight-bold text-center">${product.productPrice}đ</h5>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                    <button type="button" class="btn btn-dark bg-dark ml-3 mb-5 mt-3 py-2 text-white" onclick="loadMore()">Xem thêm ...</button>
                </div>
            </div>
            <!-- end list product -->

            <!-- banner bottom -->
            <div class="container-fluid m-0 p-0">
                <a class="w-100 h-auto" href="#"><img class="w-100 h-auto" src="img/banner3.jpg" alt="" /></a>
            </div>
        <jsp:include page="Footer.jsp"></jsp:include>
    </body>
</html>