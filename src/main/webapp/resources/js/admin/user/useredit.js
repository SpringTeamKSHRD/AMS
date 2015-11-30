var img=0;
var img_temp;
		
		startPage();
		function startPage(){
			id=window.location.href.split('/')[7];
			//alert(id);
			$.ajax({
				type : "GET",
				url : domainname + "api/admin/user/"+id,
				success : function(data) {
					//alert("Success detail:" + data.MESSAGE);
					$("#id").val(data.RESPONSE_DATA.id);
					$("#name").val(data.RESPONSE_DATA.name);
					if(data.RESPONSE_DATA.gender=="f"){
						$("#f").attr('checked',true);
					}else{
						$("#m").attr('checked',true);
					}
					$("#email").val(data.RESPONSE_DATA.email);
					$("#username").val(data.RESPONSE_DATA.username);
					$("#password").val(data.RESPONSE_DATA.password);
					$("#address").val(data.RESPONSE_DATA.address);
					$("#phone").val(data.RESPONSE_DATA.phone);
					img_temp=data.RESPONSE_DATA.image;
				},
				error : function(data) {
					alert("Unsuccess get user:" + data.MESSAGE);
					console.log("ERROR..." + data);
				}
			}); 
		}
		
		function updateUser(){
			if(img==0){
				img=img_temp;
			}else{
				img=$("#image").val().split("\\").pop();
			}
			   json = {
					id : $("#id").val(),
					name : $("#name").val(),
					gender : $('input:radio[name=gender]:checked').val(),
					email : $("#email").val(),
					username : $("#username").val(),
					password : $("#password").val(),
					address : $("#address").val(),
					phone : $("#phone").val(),
					role_id: $('select[name="role_id"] option:selected').val(),
					//enable : $('input:radio[name=enable]:checked').val(),
					image : img
				};

				$.ajax({
					type : "PUT",
					url : domainname+"api/admin/user/",
					data : JSON.stringify(json),
					contentType: 'application/json',
					success : function(data) {
						alert("Success :" + data.MESSAGE);
						uploadImage();
						location.href=domainname+"admin/user";
					},
					error : function(data) {
						alert("Unsuccess: " + data.MESSAGE);
						console.log("ERROR..." + data);
					}
				}); 
		}
		
		$("#image").change(function() {
			//alert("change image");
			img = 1;
		});
		function uploadImage() {
			alert("upload image");
			var data1;
			data1 = new FormData();
			data1.append('file', $('#image')[0].files[0]);
			$.ajax({
				url : domainname+"api/admin/user/uploadimage",
				type : "POST",
				cache : false,
				contentType : false,
				processData : false,
				data : data1,
				success : function(data2) {
					alert("success uploading");
				},
				error : function(data2){
					alert("fail with upload.");
				}
			}); 
		}  
		
		