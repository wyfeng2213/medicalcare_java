mui.ready(function(){
	
	var dtpicker;//日期选择器

	buildDate();
	setUser_sex_state();
	userIconMakeThumb()
	
	
	document.querySelector("#user-birth-date").addEventListener("tap",function(){
		//点击出生日期，显示日期选择器
		dtpicker.show(function(items){
			document.getElementById("user-birth-date").value = items.y.value+"-"+items.m.value+"-"+items.d.value;
		});
	},false)

//	document.querySelector(".icon-user").addEventListener("tap",function(){
//		//点击用户头像父元素 =》 点击头像
//		mui(".photo")[0].click()
//	},false)

	document.querySelector(".btn-apply").addEventListener("tap",function(){
		submitUserInfos.handleSubmit()
	},false)

	function modify_phone_userName(userName){
		//修改equipmentData的phone、userName属性
		var phone = mui.getQueryString("phone");
		var referenceLoginId = mui.getQueryString("referenceLoginId");
		var session = mui.getQueryString("session");
		if(!!phone){
			equipmentData.phone = phone;
		}
		if(!!userName){
			equipmentData.userName = userName;
		}
		if(!!session){
			equipmentData.session = session;
		}
		
	}
	
	function hasURLPhone(){
		//判断url是否带phone参数，无则返回登录页
		var phone = mui.getQueryString("phone");
		var referenceLoginId = mui.getQueryString("referenceLoginId");
		if((!phone)&&(!referenceLoginId)){
			location.replace('login.html')
		}
	}

	var submitUserInfos ={
		//点击下一步按钮的操作
		
		handleSubmit:function(){
			var userInfosData = this.getUserInfosData();//获取用户信息等数据
			modify_phone_userName(userInfosData.name);//修改equipmentData的phone和userName
			
			var checkResult = this.checkData(userInfosData);
			
			if(checkResult){
				this.applyUserInfos(userInfosData);
			}
		},
		getUserInfosData:function(){
			/*
			 * 用户信息
			 * @funciton
			 * @return {Object} userInfosData -用户信息集合
			 * 
			 **/
//			var userIcon = document.querySelector(".icon-user img").getAttribute("src"),
			var	name = document.querySelector("#user-name").value,
				sex = document.querySelector("#user-sex").value,
				birthDate = document.querySelector("#user-birth-date").value,
//				state = document.querySelector("#user-state").value,
//				hospital = document.querySelector("#hospital").value,
//				remarks = document.querySelector("#remarks").value,
//				cardNum = document.querySelector("#cardNum").value,
				loginId = null,
				referenceLoginId = mui.getQueryString("referenceLoginId");
			
			var userInfosData = {
				"referenceLoginId":referenceLoginId,	
				"name":name,
				"birthDate": birthDate,
//				"userIcon": userIcon,
//				"hospital": hospital,
				"loginId": "",
//				"remarks": remarks,
				"sex": sex
//				"state": state,
//				"cardNum": cardNum
			}
			
			return userInfosData;
		},
		applyUserInfos: function(data){
			/*
			 * 提交用户ajax
			 * @function
			 * @param {object} 用户信息数据
			 * @returns {boolean}
			 **/
			var applyBtn = document.querySelector(".btn-apply");
//			alert(JSON.stringify(equipmentData));
			$.ajax({
				url: api.applyUserInfos,
				type:'POST',
				data:{
					patientUser:JSON.stringify({
						"name":data.name,
						"birthday": data.birthDate,
//						"headUrl": data.userIcon,
						"hospital": '',
						"loginId": data.loginId,
						"remarks": '',
						"sex": data.sex,
						"visitState": '',
						"medicalCard": '',
						"referenceLoginId":data.referenceLoginId
					}),
					equipmentData:JSON.stringify(equipmentData)
				},
				beforeSend:function(){
					applyBtn.setAttribute("disabled","disabled")
				},
				success:function(res){
					var code = res.code;
					if(code === 200){
						location.replace('downLoad.html')
						/*self.location ='downLoad.html';*/
					}else if(code === 405){
						mui.toast({time:'5000',message:"用户已存在"})();
						setTimeout(function(){
							location.replace('downLoad.html')
							/*self.location ='downLoad.html';*/
						},1000)
						
					}else{
						mui.toast({time:'5000',message:res.message})();
					return false;
					}
				},
				error:function(err){
					mui.toast({time:'5000',message:'连接服务器失败，请重试'})();
					return false;
				},
				complete:function(){
					applyBtn.removeAttribute("disabled")
				}
			})
			
		},
		checkData: function(opt){
			/*
			 * 校验用户的姓名、性别、出生日期、就诊状态数据
			 *  @param {object} name、sex、birthDate、state集合对象
			 * 	return {boolean} 校验结果
			 * */
			var name = opt.name,
				sex = opt.sex,
				birthDate = opt.birthDate,
				state = opt.state;
			if(!name){
				mui.toast({time:'5000',message:'请输入真实姓名'})();
				return false;
			}else if(name.length<2){
				mui.toast({time:'5000',message:'姓名长度不能少于2个字符'})();
				return false;
			}else if(!sex){
				mui.toast({time:'5000',message:'请选择您的性别'})();
				return false;
			}else if(!birthDate){
				mui.toast({time:'5000',message:'请选择您的出生日期'})();
				return false;
			}else{
				return true;
			}
		}
	}
	function buildDate(){
		//创建日期选择器实例
		var myDate = new Date(); 
		var endDate = myDate.toLocaleDateString();//今天日期
		dtpicker = new mui.DtPicker({
			type:'date',
			beginDate:new Date(1920,01,01),
			endDate:new Date(endDate),
		})
	}
	
	function setUser_sex_state(){
		//用户性别及就诊状态选择
		var hiddenBox = document.querySelectorAll(".form-user-infos li.hidden");
		var hiddenBoxState = "none"; 
		
		mui('body').on('tap', '.mui-popover-action li>.option', function() {
			var a = this,
				parent;
			//根据点击按钮，反推当前是哪个actionsheet
			for (parent = a.parentNode; parent != document.body; parent = parent.parentNode) {
				if (parent.classList.contains('mui-popover-action')) {
					break;
				}
			}
			//关闭actionsheet
			mui('#' + parent.id).popover('toggle');

			switch(parent.id){
				case "select-sex":
					document.getElementById("user-sex").value = a.innerHTML;
					break;
				case "select-state":
					document.getElementById("user-state").value = a.innerHTML;
					break;
			}
			
			if(a.innerHTML === "门诊治疗"){
				hiddenBoxState = "block" 
			}else{
				hiddenBoxState = "none" 
			}
			
			for(var i=0;i<hiddenBox.length;i++){
				hiddenBox[i].style.display = hiddenBoxState;
			}
		})
	}
	function userIconMakeThumb(){
		//头像生成缩略图及转化为base64
		$file = $("#file-userIcon");
		$form = ("#form-userIcon");
		$file.makeThumb({
			width: 100,
			height: 100,
			mark: {padding: 0, src: 'mark.png', width: 30, height: 30},
			before: function() {
			},
			done: function(dataURL, blob, tSize, file, sSize, fEvt) { //success
				// 可以得到图片名, 高度等信息, 用来做一些判断, 比如图片大小是否符合要求等..
				// console.log(fEvt.target, file.name, sSize, sSize.width, sSize.height);
				// console.log(file.name, sSize.width +'->'+ tSize.width, sSize.height +'->'+ tSize.height);
	
				var thumb = new Image();
				thumb.src = dataURL;
				$(".icon-user img").attr("src",dataURL)

				/*if (window.FormData) {
					$file.remove();
					var fd = new FormData($form[0]);
					
					if (blob) {
						fd.append('blob', blob, file.name);
					} else {
						fd.append('base64', dataURL);
					}
					$.ajax({
						url: $form.attr('action'), type: 'POST', processData: false, contentType: false, data: fd
					}).done(function(data) {
						// console.log(data);
						$('body').append(data);
					});
					// var xhr = new XMLHttpRequest();xhr.open('POST', $form.attr('action'), true);xhr.send(fd);
					return;
				}*/
				
			},
			fail: function(file, fEvt) { //error
				console.log(file, fEvt);
			},
			always: function() {
			}
		});
	}
	
	
})