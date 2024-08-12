<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta content="width=device-width, initial-scale=1.0" name="viewport">

    <title>Dashboard - Admin</title>
    <meta content="" name="description">
    <meta content="" name="keywords">

    <!-- Favicons -->
    <link href="/assets/img/favicon.png" rel="icon">
    <link href="/assets/img/apple-touch-icon.png" rel="apple-touch-icon">

    <!-- Vendor CSS Files -->
    <link href="/assets/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="/assets/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
    <link href="/assets/vendor/simple-datatables/style.css" rel="stylesheet">
    <link href="/assets/css/style.css" rel="stylesheet">
</head>
<body>
    <jsp:include page="../../common/admin-header.jsp"></jsp:include>
    <jsp:include page="../../common/admin-sidebar.jsp"></jsp:include>

    <main id="main" class="main">
      <div class="pagetitle">
        <h1>Profile</h1>
        <nav>
          <ol class="breadcrumb">
            <li class="breadcrumb-item"><a href="index.html">Home</a></li>
            <li class="breadcrumb-item">Users</li>
            <li class="breadcrumb-item active">Profile</li>
          </ol>
        </nav>
      </div><!-- End Page Title -->

      <section class="section profile">
        <div class="row">

          <div class="col-xl-9">

            <div class="card">
              <div class="card-body pt-3">
                <!-- Bordered Tabs -->
                <ul class="nav nav-tabs nav-tabs-bordered">

                  <li class="nav-item">
                    <button class="nav-link active" data-bs-toggle="tab" data-bs-target="#profile-overview">Overview</button>
                  </li>

<%--                  <li class="nav-item">--%>
<%--                    <button class="nav-link" data-bs-toggle="tab" data-bs-target="#profile-edit">Edit Profile</button>--%>
<%--                  </li>--%>

                  <li class="nav-item">
                    <button class="nav-link" data-bs-toggle="tab" data-bs-target="#profile-change-password">Change Password</button>
                  </li>

                </ul>
                <div class="tab-content pt-2">

                  <div class="tab-pane fade show active profile-overview" id="profile-overview">

                    <h5 class="card-title">Profile Details</h5>

                    <div class="row">
                      <div class="col-lg-3 col-md-4 label ">Full Name</div>
                      <div class="col-lg-9 col-md-8">${profile.fullName}</div>
                    </div>

                      <c:if test="${sessionScope.role != 0}">
                          <div class="row">
                              <div class="col-lg-3 col-md-4 label">Store Work</div>
                              <div class="col-lg-9 col-md-8">${profile.storeName}</div>
                            </div>
                      </c:if>

                    <div class="row">
                      <div class="col-lg-3 col-md-4 label">Role</div>
                      <div class="col-lg-9 col-md-8">
                        <c:choose>
                          <c:when test="${profile.role == 0}">SUPER ADMIN</c:when>
                          <c:when test="${profile.role == 1}">ADMIN</c:when>
                        </c:choose>
                      </div>
                    </div>

                    <div class="row">
                      <div class="col-lg-3 col-md-4 label">Email</div>
                      <div class="col-lg-9 col-md-8">${profile.username}</div>
                    </div>

                      <div class="row">
                          <div class="col-lg-3 col-md-4 label">List Address</div>
                          <table class="table datatable text-center mt-3">
                              <thead>
                                <tr>
                                    <th>Id</th>
                                    <th>Type</th>
                                    <th>Province</th>
                                    <th>District</th>
                                    <th>Ward</th>
                                    <th>Phone</th>
<%--                                    <th>Options</th>--%>
                                </tr>
                              </thead>
                              <tbody style="vertical-align: middle">

                                <c:forEach items="${listAddress}" var="address">
                                    <tr>
                                        <td>${address.id}</td>
                                        <td>${address.name}</td>
                                        <td>${address.province}</td>
                                        <td>${address.district}</td>
                                        <td>${address.ward}</td>
                                        <td>${address.phone}</td>
<%--                                        <td>--%>
<%--                                            <a href="/admin/user/${userId}/update-address/${address.id}" class="btn btn-primary"><i class="bi bi-pencil-square text-white"></i></a>--%>
<%--                                            <a class="btn btn-danger" data-bs-toggle="modal"--%>
<%--                                               data-bs-target="#modalDeleteUser" onclick="clickDeleteAddress(${address.id})">--%>
<%--                                                <i class="bi bi-trash-fill text-white"></i>--%>
<%--                                            </a>--%>
<%--                                        </td>--%>
                                    </tr>
                                </c:forEach>
                            </tbody>
                          </table>
                      </div>
                  </div>

<%--                  <div class="tab-pane fade profile-edit pt-3" id="profile-edit">--%>

<%--                    <!-- Profile Edit Form -->--%>
<%--                    <form>--%>

<%--                      <div class="row mb-3">--%>
<%--                        <label for="fullName" class="col-md-4 col-lg-3 col-form-label">Full Name</label>--%>
<%--                        <div class="col-md-8 col-lg-9">--%>
<%--                          <input name="fullName" type="text" class="form-control" id="fullName" value="Kevin Anderson">--%>
<%--                        </div>--%>
<%--                      </div>--%>

<%--                      <div class="row mb-3">--%>
<%--                        <label for="about" class="col-md-4 col-lg-3 col-form-label">About</label>--%>
<%--                        <div class="col-md-8 col-lg-9">--%>
<%--                          <textarea name="about" class="form-control" id="about" style="height: 100px">Sunt est soluta temporibus accusantium neque nam maiores cumque temporibus. Tempora libero non est unde veniam est qui dolor. Ut sunt iure rerum quae quisquam autem eveniet perspiciatis odit. Fuga sequi sed ea saepe at unde.</textarea>--%>
<%--                        </div>--%>
<%--                      </div>--%>

<%--                      <div class="row mb-3">--%>
<%--                        <label for="company" class="col-md-4 col-lg-3 col-form-label">Company</label>--%>
<%--                        <div class="col-md-8 col-lg-9">--%>
<%--                          <input name="company" type="text" class="form-control" id="company" value="Lueilwitz, Wisoky and Leuschke">--%>
<%--                        </div>--%>
<%--                      </div>--%>

<%--                      <div class="row mb-3">--%>
<%--                        <label for="Job" class="col-md-4 col-lg-3 col-form-label">Job</label>--%>
<%--                        <div class="col-md-8 col-lg-9">--%>
<%--                          <input name="job" type="text" class="form-control" id="Job" value="Web Designer">--%>
<%--                        </div>--%>
<%--                      </div>--%>

<%--                      <div class="row mb-3">--%>
<%--                        <label for="Country" class="col-md-4 col-lg-3 col-form-label">Country</label>--%>
<%--                        <div class="col-md-8 col-lg-9">--%>
<%--                          <input name="country" type="text" class="form-control" id="Country" value="USA">--%>
<%--                        </div>--%>
<%--                      </div>--%>

<%--                      <div class="row mb-3">--%>
<%--                        <label for="Address" class="col-md-4 col-lg-3 col-form-label">Address</label>--%>
<%--                        <div class="col-md-8 col-lg-9">--%>
<%--                          <input name="address" type="text" class="form-control" id="Address" value="A108 Adam Street, New York, NY 535022">--%>
<%--                        </div>--%>
<%--                      </div>--%>

<%--                      <div class="row mb-3">--%>
<%--                        <label for="Phone" class="col-md-4 col-lg-3 col-form-label">Phone</label>--%>
<%--                        <div class="col-md-8 col-lg-9">--%>
<%--                          <input name="phone" type="text" class="form-control" id="Phone" value="(436) 486-3538 x29071">--%>
<%--                        </div>--%>
<%--                      </div>--%>

<%--                      <div class="row mb-3">--%>
<%--                        <label for="Email" class="col-md-4 col-lg-3 col-form-label">Email</label>--%>
<%--                        <div class="col-md-8 col-lg-9">--%>
<%--                          <input name="email" type="email" class="form-control" id="Email" value="k.anderson@example.com">--%>
<%--                        </div>--%>
<%--                      </div>--%>

<%--                      <div class="row mb-3">--%>
<%--                        <label for="Twitter" class="col-md-4 col-lg-3 col-form-label">Twitter Profile</label>--%>
<%--                        <div class="col-md-8 col-lg-9">--%>
<%--                          <input name="twitter" type="text" class="form-control" id="Twitter" value="https://twitter.com/#">--%>
<%--                        </div>--%>
<%--                      </div>--%>

<%--                      <div class="row mb-3">--%>
<%--                        <label for="Facebook" class="col-md-4 col-lg-3 col-form-label">Facebook Profile</label>--%>
<%--                        <div class="col-md-8 col-lg-9">--%>
<%--                          <input name="facebook" type="text" class="form-control" id="Facebook" value="https://facebook.com/#">--%>
<%--                        </div>--%>
<%--                      </div>--%>

<%--                      <div class="row mb-3">--%>
<%--                        <label for="Instagram" class="col-md-4 col-lg-3 col-form-label">Instagram Profile</label>--%>
<%--                        <div class="col-md-8 col-lg-9">--%>
<%--                          <input name="instagram" type="text" class="form-control" id="Instagram" value="https://instagram.com/#">--%>
<%--                        </div>--%>
<%--                      </div>--%>

<%--                      <div class="row mb-3">--%>
<%--                        <label for="Linkedin" class="col-md-4 col-lg-3 col-form-label">Linkedin Profile</label>--%>
<%--                        <div class="col-md-8 col-lg-9">--%>
<%--                          <input name="linkedin" type="text" class="form-control" id="Linkedin" value="https://linkedin.com/#">--%>
<%--                        </div>--%>
<%--                      </div>--%>

<%--                      <div class="text-center">--%>
<%--                        <button type="submit" class="btn btn-primary">Save Changes</button>--%>
<%--                      </div>--%>
<%--                    </form><!-- End Profile Edit Form -->--%>

<%--                  </div>--%>

                  <div class="tab-pane fade pt-3" id="profile-change-password">
                    <!-- Change Password Form -->
                    <form id="changePasswordForm">
                      <div id="success-info"></div>
                      <div class="row mb-3">
                        <label for="currentPassword" class="col-md-4 col-lg-3 col-form-label">Current Password</label>
                        <div class="col-md-8 col-lg-9">
                          <input required name="password" type="password" class="form-control" id="currentPassword">
                          <span id=info class="text-danger"></span>
                        </div>
                      </div>

                      <div class="row mb-3">
                        <label for="newPassword" class="col-md-4 col-lg-3 col-form-label">New Password</label>
                        <div class="col-md-8 col-lg-9">
                          <input required name="newpassword" type="password" class="form-control" id="newPassword">
                        </div>
                      </div>

                      <div class="row mb-3">
                        <label for="renewPassword" class="col-md-4 col-lg-3 col-form-label">Confirm Password</label>
                        <div class="col-md-8 col-lg-9">
                          <input required name="renewpassword" type="password" class="form-control" id="renewPassword">
                        </div>
                      </div>

                      <div class="text-center">
                        <button type="submit" class="btn btn-primary">Change Password</button>
                      </div>
                    </form><!-- End Change Password Form -->

                  </div>

                </div><!-- End Bordered Tabs -->

              </div>
            </div>

          </div>
        </div>
      </section>

  </main><!-- End #main -->

    <a href="#" class="back-to-top d-flex align-items-center justify-content-center"><i
            class="bi bi-arrow-up-short"></i></a>
    <script src="/assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
    <script src="/assets/js/main.js"></script>
    <script src="/js/user.js"></script>
    <script>
      document.addEventListener('DOMContentLoaded', function (){
        let changePasswordForm = document.getElementById("changePasswordForm");
        changePasswordForm.addEventListener("submit", function (event){
            event.preventDefault();

            let formData = {
              currentPassword: document.getElementById("currentPassword").value,
              newPassword: document.getElementById("newPassword").value,
              renewPassword: document.getElementById("renewPassword").value
            }

            fetch("http://localhost:8080/admin/change-password",{
              method:"POST",
              headers:{
                "Content-Type":"application/json"
              },
              body: JSON.stringify(formData)
            }).then(response => {
                return response.json();
            })
            .then(data => {
                if (data.status === "success") {
                    let infoElement = document.getElementById("success-info");
                    infoElement.innerHTML = "";
                    document.getElementById("info").innerHTML = "";
                    document.getElementById("currentPassword").value = "";
                    document.getElementById("newPassword").value = "";
                    document.getElementById("renewPassword").value = "";
                    infoElement.innerHTML = '<div class="alert alert-primary mt-3 alert-dismissible fade show" role="alert">'
                        + data.message
                        + '<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>'
                        + '</div>';
                } else if(data.status === "error0" || data.status === "error2"){
                    document.getElementById("success-info").innerHTML = "";
                    document.getElementById("info").innerHTML = "";
                    alert(data.message);
                } else if (data.status === "error1") {
                    document.getElementById("success-info").innerHTML = "";
                    let infoElement = document.getElementById("info");
                    infoElement.innerHTML = "";
                    infoElement.innerHTML =  data.message;
                }
            })
            .catch(error => {
                alert(error.message);
            });
        })
      })
    </script>
</body>
</html>
