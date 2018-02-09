<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="common/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>秒杀列表页</title>
    <%@include file="common/head.jsp"%>
</head>
<body>
<%-- 页面显示部分--%>
<div class="container">
    <div class="panel panel-default">
        <div class="panel-heading text-center">
            <h2>秒杀列表</h2>
        </div>
        <dov class="panel-body">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>名称</th>
                    <th>库存</th>
                    <th>开始时间</th>
                    <th>介绍时间</th>
                    <th>创建时间</th>
                    <th>详情页</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="sk" items="${list}">
                <tr>
                    <td>${sk.name}</td>
                    <td>${sk.number}</td>
                    <td>
                        <fmt:formatDate value="${sk.startTime}" pattern="yyyy-mm-dd HH:mm:ss"></fmt:formatDate></td>
                    <td>
                        <fmt:formatDate value="${sk.endTime}" pattern="yyyy-mm-dd HH:mm:ss"></fmt:formatDate></td>
                    <td>
                        <fmt:formatDate value="${sk.createTime}" pattern="yyyy-mm-dd HH:mm:ss"></fmt:formatDate></td>
                    <td>
                        <a href="${sk.seckillId}/detail" class="btn btn-info" target="_blank">Link</a>
                    </td>
                </tr>
                </c:forEach>
                </tbody>
            </table>
        </dov>
    </div>
</div>
<script src="https://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>
</html>