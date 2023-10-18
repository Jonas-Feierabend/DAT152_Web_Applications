<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ attribute name="borderSize" required="true" type="java.lang.String" %>
<%@ attribute name="width" required="true" type="java.lang.String" %>
<%@ attribute name="borderColor" required="true" type="java.lang.String" %>
<%@ attribute name="background" required="true" type="java.lang.String" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>



    <div  style="border:${borderSize}px solid ${borderColor}; width:${width}px; background:${background};">
        <jsp:doBody />
    </div>

