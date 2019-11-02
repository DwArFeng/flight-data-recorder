<%--
  Created by IntelliJ IDEA.
  User: 91572
  Date: 2019/10/19
  Time: 21:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>飞行数据记录仪</title>
</head>
<body>

<h3>飞行数据记录仪维护系统</h3>

<h4>欢迎您使用飞行数据记录仪维护系统</h4>

<p>
    您访问的是一个webservice接口，该webservice的全部服务地址以及方法清单列表请参考如下链接:
    <br/>
    <a href="${pageContext.request.contextPath}/webservice/">
        ${pageContext.request.contextPath}/webservice/
    </a>
    <br/>
    单独的的webservice服务wsdl请参考下表:
<table border="1">
    <tr>
        <th>描述</th>
        <th>wsdl地址</th>
    </tr>
    <tr>
        <td>欢迎服务</td>
        <td>
            <a href="${pageContext.request.contextPath}/webservice/hello?wsdl">
                ${pageContext.request.contextPath}/webservice/hello?wsdl
            </a>
        </td>
    </tr>
</table>
</p>

</body>
</html>
