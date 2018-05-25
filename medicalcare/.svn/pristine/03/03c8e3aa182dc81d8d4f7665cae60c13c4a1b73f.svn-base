mui
		.ready(function() {
			mui.init({
				gestureConfig : {
					tap : true,
				}
			})

			var referenceLoginId = mui.getQueryString('referenceLoginId');// 获取跳转url的参数
	        //var doctorType = mui.getQueryString('doctorType');
			//alert(referenceLoginId);
			
			document.querySelector(".btn-getcode").addEventListener("tap",
					function(event) {
						// 点击获取验证码
						var phoneNum = document.querySelector(".phone").value;
						var phoneResult = checkPhoneNum(phoneNum);
						if (phoneResult) {
							getCode(phoneNum);
						}
					}, false)

			document
					.querySelector(".btn-next")
					.addEventListener(
							"tap",
							function(event) {
								// 点击下一步
								var phoneNum = document.querySelector(".phone").value, code = document
										.querySelector(".code").value;

								var phoneResult = checkPhoneNum(phoneNum);

								if (!!phoneResult) {
									var codeResult = checkCode(code);
									if (!!codeResult) {
										register(phoneNum, code);
									}
								}
							}, false)
							
			
			document.querySelector(".agreement").addEventListener("tap",
					function(event) {
						// 查看服务协议
//						location.href = 'notice.html';
//						event.preventdefault();
						self.location = 'notice.html';
					}, false)				
							

			function checkCode(code) {
				// 校验验证码
				if (!code) {
					mui.toast({
						time : '5000',
						message : '请输入验证码'
					})();
					return false;
				} else if (code.length !== 6) {
					mui.toast({
						time : '5000',
						message : '验证码错误'
					})();
					return false;
				} else if (!/^[0-9]{6}$/.test(code)) {
					mui.toast({
						time : '5000',
						message : '验证码错误'
					})();
					return false;
				} else {
					return true;
				}
			}

			function checkPhoneNum(phoneNum) {
				// 校验手机号码
				var isMobile = /^1[34578]\d{9}$/;// 手机号正则
				if (!phoneNum) {
					mui.toast({
						time : '5000',
						message : '请输入手机号'
					})();
					return false;
				} else if (phoneNum.length !== 11) {
					mui.toast({
						time : '5000',
						message : '请输入正确的手机号'
					})();
					return false;
				} else if (!(isMobile.test(phoneNum))) {
					mui.toast({
						time : '5000',
						message : '请输入正确的手机号'
					})();
					return false;
				} else {
					return true;
				}
			}

			function register(phoneNum, code) {
				// 注册
				var codeBtn = document.querySelector(".btn-getcode"), nextBtn = document
						.querySelector(".btn-next");

				equipmentData.phone = phoneNum;

				$.ajax({
					url : api.register,
					async : true,
					data : {
						hPhonenumber : phoneNum,
						code : code,
						equipmentData : JSON.stringify(equipmentData)
					},
					dataType : 'json',
					timeout : '10000',
					beforeSend : function() {
						codeBtn.setAttribute("disabled", "disabled");
						nextBtn.setAttribute("disabled", "disabled");
					},
					success : function(res) {
						if (res.code === 200) {
							
							//alert(res.data.session);
							var session = res.data.session;
							queryUserInfos(phoneNum,session);
							//alert(session);
						} else {
							mui.toast({
								time : '5000',
								message : res.message
							})()
						}
					},
					error : function(error) {
						console.log(error);
						mui.toast({
							time : '5000',
							message : '接服务器失败，请重试'
						})()
					},
					complete : function() {
						codeBtn.removeAttribute("disabled");
						nextBtn.removeAttribute("disabled");
					}
				})
			}

			function getCode(phoneNum) {
				// 获取手机验证码
				/*
				 * mui ajax不能跨域 使用zepto ajax
				 */
				var codeBtn = document.querySelector(".btn-getcode");

				equipmentData.phone = phoneNum;

				$.ajax({
					url : api.sendCode,
					async : true,
					data : {
						hPhonenumber : phoneNum,
						equipmentData : JSON.stringify(equipmentData)
					},
					type : 'GET',
					dataType : 'json',
					timeout : '10000',
					beforeSend : function() {
						codeBtn.setAttribute("disabled", "disabled");// 禁用按钮
					},
					success : function(res) {
						if (res.code === 200) {
							remainTime();// 验证码倒计时
						} else {
							mui.toast({
								time : '5000',
								message : 'res.message'
							})()
							clearInterval(window.countDown);
							codeBtn.removeAttribute("disabled");
						}
					},
					error : function(error) {
						console.log(error);
						codeBtn.removeAttribute("disabled");
						mui.toast({
							time : '5000',
							message : '接服务器失败，请重试'
						})()

					},
					complete : function() {

					}
				})

			}

			function queryUserInfos(phone,session) {
//				alert(session);
				equipmentData.session=session;
//				alert(equipmentData.session);
//				alert(JSON.stringify(equipmentData));
				$.ajax({
					url : api.select,
					data : {
						equipmentData : JSON.stringify(equipmentData),
						patientPhone : phone
					},
					success : function(res) {
						var code = res.code;
						if (code === 200) {
							// 判断医生与患者关系，存在关系则跳到downLoad.html，不存在则跳到FamilyDoctor.html
//							$.ajax({
//								url : api.checkDoctorPatientLink,
//								data : {
//									equipmentData : JSON.stringify(equipmentData),
//									patientPhone : phone,
//									referenceLoginId:referenceLoginId
//								},
//								success : function(res) {
//									var code = res.code;
//									if (code === 200) {
										self.location = 'downLoad.html';
//									} else {
//										self.location = 'userInfos.html?phone='+phone+'&referenceLoginId='+referenceLoginId;
//									}
//								},
//								error : function(err) {
//									mui.toast({
//										time : '5000',
//										message : '连接服务器失败，请重试'
//									})();
////									return false;
//								},
//							})
							
						} else if (code === 404) {
							// 跳到FamilyDoctor.html
							//self.location = 'FamilyDoctor.html?doctorsLoginId='+doctorsLoginId+"&doctorType="+doctorType;
							self.location = 'userInfos.html?phone='+phone+'&referenceLoginId='+referenceLoginId+'&session='+session;
						} else {
							mui.toast({
								time : '5000',
								message : res.message
							})();
							return false;
						}
					},
					error : function(err) {
						mui.toast({
							time : '5000',
							message : '连接服务器失败，请重试'
						})();
						return false;
					},
				})
			}

			function remainTime() {
				// 验证码倒计时
				var times = 60;// 倒数时间
				var codeBtn = document.querySelector(".btn-getcode");

				clearInterval(window.countDown);// 防止多次倒计时

				codeBtn.setAttribute("disabled", "disabled");// 禁用按钮

				codeBtn.className += " bg-abd0ff";

				window.countDown = setInterval(function() {
					times--;
					if (times >= 0) {
						codeBtn.innerHTML = times + "秒";
					} else {
						codeBtn.innerHTML = "重新发送";
						clearInterval(window.countDown);
						codeBtn.removeAttribute("disabled");
						codeBtn.className = "ellipse btn-getcode";
					}
				}, 1000)

			}

		})
