<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<div class="container" style="margin-top : 60px">
	<div class="row justify-content-between">
		<h1 class="col-md-5 col-12 text-center text-danger">THỐNG KÊ ĐIỂM KHÓA LUẬN THEO NĂM</h1>
		<h1 class="col-md-5  col-12 text-center text-danger">THỐNG KÊ TUẦN XUẤT THAM GIA KHÓA LUẬN THEO NĂM</h1>
	</div>
	<div class="row justify-content-center">

		<div class="col-md-8 col-8 ">
			<div>
				<form>
					<div class="form-floating mb-3 mt-3">
						<input type="number" class="form-control" id="year"
							placeholder="Năm" name="year"> <label for="year">Năm</label>
					</div>
					
					<div class="form-floating  mb-3 mt-3 d-flex justify-content-center">
						<button class="btn btn-info w-50">Lọc</button>
					</div>
				</form>
			</div>

		</div>
		<div class="row justify-content-around">
			<div class="col-md-5 col-5">
				<canvas id="myChart"></canvas>
			</div>

			<div class="col-md-5 col-5">
				<canvas id="myChart2"></canvas>
			</div>
		</div>

	</div>
</div>




<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script>
    let labels = [];
    let data = [];
    
    <c:forEach items="${theses}" var="t">
    	labels.push('${t[0]}');
    	data.push(${t[1]})
	</c:forEach>
    	
    let labels2 = [];
    let data2 = [];
        
    <c:forEach items="${theses2}" var="t2">
        labels2.push('${t2[0]}');
        data2.push(${t2[1]})
    </c:forEach>


     function drawChart(ctx,labels, labelDatasets, data) {
        new Chart(ctx, {
            type: 'bar',
            data: {
                labels: labels,
                datasets: [{
                        label: labelDatasets,
                        data: data,
                        borderWidth: 1,
                        backgroundColor: ['red', 'green', 'blue', 'gold' , 'yellow' , 'brown']
                    }],
            },
            options: {
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            }
        });
    }

    window.onload = () => {
        const ctx1 = document.getElementById('myChart');
        drawChart(ctx1, labels, 'Điểm khóa luận theo năm' ,data);
        console.log(labels , data)
        const ctx2 = document.getElementById('myChart2');
        drawChart(ctx2, labels2, 'Tần xuất tham gia khóa luận của mỗi ngành theo năm' ,data2);
}
</script>