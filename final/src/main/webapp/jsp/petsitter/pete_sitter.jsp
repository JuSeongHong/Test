<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="./resources/bootstrap/css/bootstrap.css" rel="stylesheet">
<style>
	.calendar-legend {
		width:100%;
		color: #929491;
		font-size: 1em;
		line-height: 1em;
		font-weight: 300;
		letter-spacing: -0.5px;
		text-align: center;
		margin-top:1em;
	}
	.calendar-legend .circle {
		width: 12px;
		height: 12px;
		margin: 0 3px -2px 0;
	}
	.calendar-legend .unavailables {
		background-color: #edeaea;
	}
	.calendar-legend .availables {
		background-color: #575656;
	}
	.circle {
		display: inline-block;
		border-radius: 50%;
		color: #fff;
		font-weight: 400;
	}
	.stay {
		background-color: #62C15B;
	}
	
	/* 1�� ���� */
	.Highlighted a{
	   background-color : #62C15B !important;
	   background-image :none !important;
	   color: White !important;
	   font-weight:bold !important;
	   font-size: 16px;
	   border-radius:40%;
	}
	/* ���డ�� */
	.Highlight a{
		background-color : #5A5A5A !important;
		background-image :none !important;
		color: White !important;
		font-weight:bold !important;
		font-size: 16px;
		border-radius:40%;
	}
	/* ��¥ ��� ���� */
	.ui-datepicker td span, .ui-datepicker td a{
		text-align : center !important;
	}
	
	.ui-datepicker select.ui-datepicker-month,
	.ui-datepicker select.ui-datepicker-year {
		width:40% !important;
	}
	
	.col-sm-4 > ul {
	   list-style:none;
	   padding: 0.1em;
	   margin:0px;
	}
	.col-sm-6 > ul {
		list-style:none;
		padding: 0.3em;
		margin:0px;
	}
	.col-sm-4 {
		margin-right:20px;
		margin-bottom: 20px;
		border: 1px solid #cccccc;
		height:30%;
	}
	.col-sm-4.calen {
		height:45%;
		max-width:100%;
	}
	.col-sm-6 {
		margin-bottom: 20px;
		border: 1px solid #cccccc;
		height:30%;	
	}
	.col-sm-6.epil {
		height:45%;
	}
	.header_div {
		width:79%;
		height:14%;
		border-bottom:0.1em solid #d6d5d5;
	    font-size: 1.4em;	
	}
		
	hr{
		margin-top:5;
		margin-bottom:5;
		border:solid 0.1em #d6d5d5;
	}
	.score {
	    width: 30%;
	    height: 18%;
	    background-color: #cabbac;
	    border-radius: 58%;	
	    font-size:1em;
	    color:white;
	    margin:0;
	    text-align:center;
	    line-height:1.4;
	}
	.btn-default {
	    background-color: #26A69A;
	    color: white;
	    float:right;
	}
	.row, .header_div {
		margin-left:8%;
	}
</style>
<!-- <link href="resources/css/pete_sitter.css" rel="stylesheet"> -->
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="resources/js/pete_sitter.js"></script>
<script src="./resources/bootstrap/js/bootstrap.min.js"></script>

<!-- jQuery UI CSS파일  -->
<link rel="stylesheet" href="http://code.jquery.com/ui/1.8.18/themes/base/jquery-ui.css" type="text/css" />  
<!--  jQuery 기본 js파일 -->
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>  
<!--  jQuery UI 라이브러리 js파일 -->
<script src="http://code.jquery.com/ui/1.8.18/jquery-ui.min.js"></script>
<script>
//후기삭제 ajax
function epilDel(pete_epil_no, join_epil_id) {
	var pete_id = "${pete_id}";
	var confi = confirm("삭제하시겠습니까?");
	if(confi == true) {	
		$.ajax({
			data: {"pete_epil_no" : pete_epil_no, "join_epil_id" : join_epil_id, "pete_id" : pete_id},
			type: "post",
			url: "pete_epil_del.nhn",
			success : function() {
				alert('삭제하였습니다.');
				location.reload();
			}		
		})
	} else {
		return false;
	}
}
//후기작성하기 ajax
$(document).ready(function(){
	$('#epil_write').click(function() {
		var pete_id = '${pete_id}';
		var pete_location = '${pete_location}'; 
		var pete_subject = '${pete_subject}';
		var pete_board_num = ${num};
		$.ajax({
			type:"POST",
			data : {"pete_id":pete_id,"pete_location":pete_location,
				"pete_subject":pete_subject, "pete_board_num" : pete_board_num},
			url : "./pete_epil.nhn",
			success : function(data) {
				$("#epilw").empty().html(data);
			},
			error:function() {
				alert('에러');
			}
		})
	})
})

function available(date) {
	
	var thismonth = date.getMonth()+1;
	var thisday = date.getDate();
	if(thismonth<10){
		thismonth = "0"+thismonth;
	}
	if(thisday<10){
		thisday = "0"+thisday;
	}
    ymd = date.getFullYear() + "-" + thismonth + "-" + thisday;
     if ($.inArray(ymd, list) >= 0) {
        return [true, "Highlighted", list];
    } else {
        return [true, "Highlight",''];
    }
}

	$(function() {
		var list = new Array();
		<c:forEach var="pp" items="${peReser }">
		//시작날짜
		var startday = '${pp.pete_date}'
		console.log(startday)
		//끝나는날짜
		var endday = '${pp.pete_date2}'	
		console.log(endday)
			//예약 시작일을 "-"로 분리합니다.
			var arr1 = startday.split("-");
			//Date형으로 생성합니다.
			var startdate=new Date(arr1[0], arr1[1], arr1[2]);
			
			arr1 = endday.split("-");
			//Date형으로 생성합니다.
			var enddate=new Date(arr1[0], arr1[1], arr1[2]);

		   //숙박일
			var bak=(enddate-startdate)/(24*60*60*1000);  
		    
			
		   //배열에 추가합니다.
			list.push(startday);
		   
		   //시작일 부터 차례대로 숙박일 만큼 배열에 추가합니다.
			for(var i=1;i<bak;i++){		
				adddate = new Date(startdate.setDate(startdate.getDate()+1));	
				dd=adddate.getDate();
				mm=adddate.getMonth();
				if(dd < 10){
					dd = '0'+dd
				}

				if(mm < 10){
					mm = '0'+mm
				}
				//날짜를 yyyy-MM-dd 형식으로 만듭니다.
				adddate = adddate.getFullYear() + "-" + mm + "-" + dd
				//배열에 추가합니다.
				list.push(adddate)
			}			
		</c:forEach>
		console.log(list)
		
		function available(date) {
			
			var thismonth = date.getMonth()+1;
			var thisday = date.getDate();
			if(thismonth<10){
				thismonth = "0"+thismonth;
			}
			if(thisday<10){
				thisday = "0"+thisday;
			}
		    ymd = date.getFullYear() + "-" + thismonth + "-" + thisday;
		     if ($.inArray(ymd, list) >= 0) {
		        return [true, "Highlighted", list];
		    } else {
		        return [true, "Highlight",''];
		    }
		}
		
	$("#datepicker").datepicker({
		 dateFormat: 'yy-mm-dd',
		    prevText: '이전 달',
		    nextText: '다음 달',
		    monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
		    dayNamesMin: ['일','월','화','수','목','금','토'],
		    showMonthAfterYear: true,
		    changeMonth: true,
		    changeYear: true,
		    yearSuffix: '년',
		    maxDate: '+2m',   // 오늘부터 2개월 까지만
		    minDate: '0d',     // 이전날짜 선택 불가능
		    beforeShowDay: available
		});
	})
	
</script>
<style>
.ui-datepicker{
	width:95%;
	margin-left:2%;
}

</style>
<jsp:include page="/jsp/header.jsp"/>
<div class="header_div">
<!-- align=left : 이미지 옆에 글을 넣을수 있게 해준다.-->
	<img src="./resources/image/user3.png" align=left>
	<br>
	<div>
	<p class="zzz">${pete_id}<br>
		<a href="./pete_map.nhn?pete_location=${pete_location }">
			${pete_location }
		</a>
	</p>
	</div>
</div>
<!--<br clear=left> : 이미지에서 글을 빠져 나가게 한다.-->
<br clear=left>
<div class="row">
<!-- <form action="./pete_list.nhn" method="post"> -->
<div class="col-sm-4">
	<ul>
		<li><h4>등록한글 보기</h4></li>
	</ul>
	<hr>
	<ul>
		<li>
		[<a href="./pete_map.nhn?pete_location=${pete_location }">
			${pete_location }
		</a>]&nbsp;
		<a href="./pete_cont.nhn?pete_board_num=${num}&page=${page}">
			${pete_subject }
		</a>
		</li>
	</ul>
	
	<ul>
		<li>
		${pete_id}
		</li>
	</ul>
	
	<ul>
		<li>
		<div class="score">
		평균
		<div>${sum}</div>
		</div>
		</li>
	</ul>
	
	<ul>
		<li>
		펫시터 평점<span style="float:right;">고객 후기 : ${listcount}개</span> 
		</li>
	</ul>
</div>	

<div class="col-sm-6">
	<ul>
		<li>
		고객 후기(${listcount }개) <br>
		</li>
		
	 	<c:forEach var="epil" items="${peteepil }">
			<li>
				아이디 : ${epil.join_epil_id } 제목 : ${epil.pete_epil_subject } 내용 : ${epil.pete_epil_content }
				별점 : ${epil.pete_epil_star }점
				
				<c:if test="${id == pete_id || id == epil.join_epil_id}">
					<img src="./resources/image/cancel.png"
					style="width:20px; height:20px" onclick="epilDel(${epil.pete_epil_no}, '${epil.join_epil_id }')">
				</c:if>
			</li>
		</c:forEach>
		
		<li style="text-align: center">
			<c:if test="${page <= 1 }">
				이전&nbsp;
			</c:if>
			
			<c:if test="${page > 1 }">
				<a href="./pete_sitter.nhn?page=${page-1 }&pete_id=${pete_id}&pete_location=${pete_location}&pete_subject=${pete_subject}&pete_board_num=${num}">이전</a>&nbsp;
			</c:if>
			
			<c:forEach var="a" begin="${startpage}" end="${endpage}">
				<c:if test="${a == page }">
					${a}
				</c:if>
				
				<c:if test="${a != page }">
					<a href="./pete_sitter.nhn?page=${a}&pete_id=${pete_id}&pete_location=${pete_location}&pete_subject=${pete_subject}&pete_board_num=${num}">${a }</a>
				</c:if>			
			</c:forEach>
			
			<c:if test="${page >= maxpage }">
				&nbsp;다음
			</c:if>
			
			<c:if test="${page < maxpage }">
				<a href="./pete_sitter.nhn?page=${page+1}&pete_id=${pete_id}&pete_location=${pete_location}&pete_subject=${pete_subject}&pete_board_num=${num}">&nbsp;다음</a>
			</c:if>
		</li>
	</ul>
		<ul>
			<li>
		<a href="#" id="epil_write" class="epil_write">후기작성하기</a>
		<hr>
		받은 평균 평점 :${sum }점
		<input type="button" value="글목록보기" class="btn btn-default" onclick="location.href='./pete_list.nhn'"
		style="padding: 4px">
		</li>
	</ul>
	</div>
</div>
<!-- </form> -->
<!-- <form> -->
<div class="row">
<div class="col-sm-4 calen" >
<c:forEach var="pp" items="${peReser}">
</c:forEach>
	<div>
	<h3>캘린더</h3>
	<hr>
	</div>
	<div id="datepicker">
	</div>
	<div class="calendar-legend text-center">
		<span class="circle unavailables"></span>예약불가&nbsp;
		<span class="circle availables"></span>예약가능&nbsp;
		<span class="circle stay"></span>1박 예약
	</div>
	</div>
	<div class="col-sm-6 epil" id="epilw">
	</div>	
</div>
<jsp:include page="/jsp/footer.jsp"/>