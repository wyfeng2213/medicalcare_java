package com.cmcc.medicalcare.controller.sys;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.TextAnchor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cmcc.medicalcare.annotation.SystemControllerLog;
import com.cmcc.medicalcare.service.IPatientLoginInfoService;
import com.cmcc.medicalcare.service.IPatientUserService;
import com.cmcc.medicalcare.utils.Toolkit;

@Controller
@RequestMapping("/sysChart")
public class SysChartsStatisticsController {
	
	@Resource
	private IPatientLoginInfoService patientLoginInfoService;
	
	@Resource
	private IPatientUserService patientUserService;
	
    @RequestMapping(value = "/sysChartsStatistics", method = RequestMethod.GET)
    public String sysChartsStatistics() {
        return "/sys/sysChartsStatistics";
    }
	
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param showType 展示类型
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 */
	@SystemControllerLog(description = "活跃用户统计")
	@RequestMapping(value = "getActiveUser")
	@ResponseBody
	public Map<String,Object> getActiveUser(HttpServletRequest request,HttpServletResponse response,Model model,
			String showType,String startTime, String endTime) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date startTime_d = null;
		Date endTime_d = null;
		long startTime_l = 0;
		long endTime_l = 0;
		
		if (StringUtils.isBlank(startTime) || StringUtils.isBlank(endTime)) {
			resultMap.put("error", "缺失参数");
			return resultMap;
		}
		if (startTime.indexOf(".") != -1) {
			startTime = startTime.replace(".", "-");
        }	
		if (endTime.indexOf(".") != -1) {
			endTime = endTime.replace(".", "-");
        }
		try {
			startTime_d = sdf.parse(startTime); //获取开始日期(日期格式)
			endTime_d = sdf.parse(endTime); //获取结束日期(日期格式)
			startTime_l = startTime_d.getTime(); //获取开始日期毫秒值
			endTime_l = endTime_d.getTime(); //获取结束日期毫秒值
		} catch (ParseException e) {
			e.printStackTrace();
			resultMap.put("error", "日期格式不正确");
			return resultMap;
		}	
		
		//设置显示数据集
		DefaultCategoryDataset  dataset = new DefaultCategoryDataset ();
		int count = 0;
		Map<String,Object> paramMap = new HashMap<String,Object>();
		if ("week".equals(showType) || "按周".equals(showType)) { //按周统计
			//获取当前开始日期下周一的日期
			String nextMonday_s = Toolkit.getOnedayNextMonday(startTime_d);
			Date nextMonday_d = null;
			try {
				nextMonday_d = sdf.parse(nextMonday_s);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			if (nextMonday_d != null) {
				long nextMonday_l = nextMonday_d.getTime();
				boolean bool = true;
				while (bool) {
					if (nextMonday_l < endTime_l) { //结束日期 大于 开始日期的下周一
						startTime = sdf.format(new Date(startTime_l));
						endTime = sdf.format(new Date(nextMonday_l)); 
//						System.out.println("startTime=========" + startTime + "---------------endTime=========" + endTime);
						paramMap.put("startTime", startTime);
						paramMap.put("endTime", endTime);
						count = patientLoginInfoService.count("getActiveCount", paramMap);
						dataset.addValue(count, "", startTime+"~");
						startTime_l = nextMonday_l;
						nextMonday_l = nextMonday_l + 7 * 24 * 60 * 60 * 1000;
					} else if (nextMonday_l == endTime_l) { //结束日期 等于 开始日期的下周一
						startTime = sdf.format(new Date(startTime_l));
						endTime = sdf.format(new Date(nextMonday_l)); 
//						System.out.println("startTime=========" + startTime + "---------------endTime=========" + endTime);
						paramMap.put("startTime", startTime);
						paramMap.put("endTime", endTime);
						count = patientLoginInfoService.count("getActiveCount", paramMap);
						dataset.addValue(count, "", startTime+"~");
						
						startTime = sdf.format(new Date(nextMonday_l));
						endTime = sdf.format(new Date(nextMonday_l + 24 * 60 * 60 * 1000)); 
//						System.out.println("startTime=========" + startTime + "---------------endTime=========" + endTime);
						paramMap.put("startTime", startTime);
						paramMap.put("endTime", endTime);
						count = patientLoginInfoService.count("getActiveCount", paramMap);
						dataset.addValue(count, "", startTime+"~");
						
						//退出循环
						bool = false;
					} else { //结束日期 小于 开始日期的下周一，即结束日期与开始日期在同一周内
						startTime = sdf.format(new Date(startTime_l));
						endTime = sdf.format(new Date(endTime_l + 24 * 60 * 60 * 1000));
						paramMap.put("startTime", startTime);
						paramMap.put("endTime", endTime);
						count = patientLoginInfoService.count("getActiveCount", paramMap);
						dataset.addValue(count, "", startTime+"~");
						
						//退出循环
						bool = false;
					}
				}
			}
		} else if("month".equals(showType) || "按月".equals(showType)){ //按月统计
			//获取当前开始日期下个月第一天日期
			String nextMonthFirstDay_s = Toolkit.getOnedayNextMonthFirstDay(startTime_d);
			Date nextMonthFirstDay_d = null;
			try {
				nextMonthFirstDay_d = sdf.parse(nextMonthFirstDay_s);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			if (nextMonthFirstDay_d != null) {
				long nextMonthFirstDay_l = nextMonthFirstDay_d.getTime();
				boolean bool = true;
				while (bool) {
					if (nextMonthFirstDay_l < endTime_l) { //结束日期 大于 开始日期的下个月第一天
						startTime = sdf.format(new Date(startTime_l));
						endTime = sdf.format(new Date(nextMonthFirstDay_l)); 
//						System.out.println("startTime=========" + startTime + "---------------endTime=========" + endTime);
						paramMap.put("startTime", startTime);
						paramMap.put("endTime", endTime);
						count = patientLoginInfoService.count("getActiveCount", paramMap);
						dataset.addValue(count, "", startTime+"~");
						startTime_l = nextMonthFirstDay_l;
						try {
							nextMonthFirstDay_l = sdf.parse(Toolkit.getOnedayNextMonthFirstDay(new Date(nextMonthFirstDay_l))).getTime();
						} catch (ParseException e) {
							e.printStackTrace();
							
							//退出循环
							bool = false;
						}
					} else if (nextMonthFirstDay_l == endTime_l) { //结束日期 等于 开始日期的下个月第一天
						startTime = sdf.format(new Date(startTime_l));
						endTime = sdf.format(new Date(nextMonthFirstDay_l)); 
//						System.out.println("startTime=========" + startTime + "---------------endTime=========" + endTime);
						paramMap.put("startTime", startTime);
						paramMap.put("endTime", endTime);
						count = patientLoginInfoService.count("getActiveCount", paramMap);
						dataset.addValue(count, "", startTime+"~");
						
						startTime = sdf.format(new Date(nextMonthFirstDay_l));
						endTime = sdf.format(new Date(nextMonthFirstDay_l + 24 * 60 * 60 * 1000)); 
//						System.out.println("startTime=========" + startTime + "---------------endTime=========" + endTime);
						paramMap.put("startTime", startTime);
						paramMap.put("endTime", endTime);
						count = patientLoginInfoService.count("getActiveCount", paramMap);
						dataset.addValue(count, "", startTime+"~");
						
						//退出循环
						bool = false;
					} else { //结束日期 小于 开始日期的下个月第一天，即结束日期与开始日期在同一个月内
						startTime = sdf.format(new Date(startTime_l));
						endTime = sdf.format(new Date(endTime_l + 24 * 60 * 60 * 1000));
						paramMap.put("startTime", startTime);
						paramMap.put("endTime", endTime);
						count = patientLoginInfoService.count("getActiveCount", paramMap);
						dataset.addValue(count, "", startTime+"~");
						
						//退出循环
						bool = false;
					}
				}
			}
		} else { //按天统计
			endTime_l = endTime_l + 24 * 60 * 60 * 1000; //获取结束日期的下一天毫秒值
			boolean bool_3 = true;
			while (bool_3) {
				if (startTime_l < endTime_l) {
					startTime = sdf.format(new Date(startTime_l));
					endTime = sdf.format(new Date(startTime_l + 24 * 60 * 60 * 1000)); 
//					System.out.println("startTime=========" + startTime + "---------------endTime=========" + endTime);
					paramMap.put("startTime", startTime);
					paramMap.put("endTime", endTime);
					count = patientLoginInfoService.count("getActiveCount", paramMap);
					dataset.addValue(count, "", startTime);
					startTime_l = startTime_l + 24 * 60 * 60 * 1000;
				} else {
					bool_3 = false;
				}
			}
		}
		
		//设置显示数据集
//		DefaultCategoryDataset  dataset = new DefaultCategoryDataset ();
//		dataset.setValue(count, "", "1月");
//		dataset.setValue(count, "", "2月");
//		dataset.setValue(count, "", "3月");
//		dataset.setValue(count, "", "4月");
//		dataset.setValue(44, "", "5月");
//		dataset.setValue(count, "", "6月");
//		dataset.setValue(33, "", "7月");
//		dataset.setValue(count, "", "8月");
//		dataset.setValue(count, "", "9月");
		
		//创建图表:标题，x轴标签显示文字，y轴标签显示文字，数据集，显示方向，是否显示图例，是否生成热点工具，是否显示url
		JFreeChart chart = ChartFactory.createBarChart("","","activeUser-count",dataset,PlotOrientation.VERTICAL,false,false,false);

		//设置标题
//		chart.setTitle(new TextTitle("活跃用户数量", new Font("黑体", Font.BOLD, 12))); //标题，设置字体，不然会中文乱码

		//设置x轴和y轴文字样式
		CategoryPlot plot = chart.getCategoryPlot();
		NumberAxis numberaxis = (NumberAxis) plot.getRangeAxis(); 
		CategoryAxis domainAxis = plot.getDomainAxis();  
		domainAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 10)); //设置X轴坐标上的文字  
		domainAxis.setLabelFont(new Font("宋体", Font.PLAIN, 15)); //设置X轴的标题文字 
		numberaxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 12)); //设置Y轴坐标上的文字  
		numberaxis.setLabelFont(new Font("黑体", Font.PLAIN, 15)); //设置Y轴的标题文字  

		/*------这句代码解决了底部图例汉字乱码的问题,不显示图例时需注释掉-----------*/  
//		chart.getLegend().setItemFont(new Font("宋体", Font.PLAIN, 12));
		
		plot.setBackgroundPaint(Color.white); //设置网格背景颜色
		plot.setDomainGridlinePaint(Color.pink); //设置网格竖线颜色
		plot.setRangeGridlinePaint(Color.pink); //设置网格横线颜色

		//设置每个柱的相关属性
		BarRenderer renderer = new BarRenderer();
//		BarRenderer3D renderer = new BarRenderer3D();
		renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		renderer.setBaseItemLabelsVisible(true);
		//默认的数字显示在柱子中，通过如下两句可调整数字的显示
		//注意：此句很关键，若无此句，那数字的显示会被覆盖，给人数字没有显示出来的问题
		renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT));
		renderer.setItemLabelAnchorOffset(3);
		renderer.setSeriesPaint(0, Color.decode("#5f9ea0"));//设置柱子颜色
		renderer.setShadowVisible(false);//取消阴影效果
		renderer.setBarPainter(new StandardBarPainter());//取消渐变效果
		renderer.setMaximumBarWidth(0.08); //设置柱子宽度
		plot.setRenderer(renderer);

		//将默认放在底部的“类型”放到顶部
		//plot.setDomainAxisLocation(AxisLocation.TOP_OR_RIGHT);
		//将默认放在左边的“数量”放到右方
		//plot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);

		String filename = null;
		try {
			filename = ServletUtilities.saveChartAsPNG(chart, 700, 400, null, request.getSession());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		filename = "/servlet/DisplayChart?filename=" + filename;
		resultMap.put("data", filename);
		return resultMap;
	}
	
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param showType
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@SystemControllerLog(description = "新增用户统计")
	@RequestMapping(value = "getAddUser")
	@ResponseBody
	public Map<String,Object> getAddUser(HttpServletRequest request,HttpServletResponse response,Model model,
			String showType,String startTime, String endTime){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date startTime_d = null;
		Date endTime_d = null;
		long startTime_l = 0;
		long endTime_l = 0;
		
		if (StringUtils.isBlank(startTime) || StringUtils.isBlank(endTime)) {
			resultMap.put("error", "缺失参数");
			return resultMap;
		}
		if (startTime.indexOf(".") != -1) {
			startTime = startTime.replace(".", "-");
        }	
		if (endTime.indexOf(".") != -1) {
			endTime = endTime.replace(".", "-");
        }
		try {
			startTime_d = sdf.parse(startTime); //获取开始日期(日期格式)
			endTime_d = sdf.parse(endTime); //获取结束日期(日期格式)
			startTime_l = startTime_d.getTime(); //获取开始日期毫秒值
			endTime_l = endTime_d.getTime(); //获取结束日期毫秒值
		} catch (ParseException e) {
			e.printStackTrace();
			resultMap.put("error", "日期格式不正确");
			return resultMap;
		}
		
		//设置显示数据集
		DefaultCategoryDataset  dataset = new DefaultCategoryDataset ();
		int count = 0;
		Map<String,Object> paramMap = new HashMap<String,Object>();
		if ("week".equals(showType) || "按周".equals(showType)) { //按周统计
			//获取当前开始日期下周一的日期
			String nextMonday_s = Toolkit.getOnedayNextMonday(startTime_d);
			Date nextMonday_d = null;
			try {
				nextMonday_d = sdf.parse(nextMonday_s);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			if (nextMonday_d != null) {
				long nextMonday_l = nextMonday_d.getTime();
				boolean bool = true;
				while (bool) {
					if (nextMonday_l < endTime_l) { //结束日期 大于 开始日期的下周一
						startTime = sdf.format(new Date(startTime_l));
						endTime = sdf.format(new Date(nextMonday_l)); 
//						System.out.println("startTime=========" + startTime + "---------------endTime=========" + endTime);
						paramMap.put("startTime", startTime);
						paramMap.put("endTime", endTime);
						count = patientUserService.count("getAddCount", paramMap);
						dataset.addValue(count, "", startTime+"~");
						startTime_l = nextMonday_l;
						nextMonday_l = nextMonday_l + 7 * 24 * 60 * 60 * 1000;
					} else if (nextMonday_l == endTime_l) { //结束日期 等于 开始日期的下周一
						startTime = sdf.format(new Date(startTime_l));
						endTime = sdf.format(new Date(nextMonday_l)); 
//						System.out.println("startTime=========" + startTime + "---------------endTime=========" + endTime);
						paramMap.put("startTime", startTime);
						paramMap.put("endTime", endTime);
						count = patientUserService.count("getAddCount", paramMap);
						dataset.addValue(count, "", startTime+"~");
						
						startTime = sdf.format(new Date(nextMonday_l));
						endTime = sdf.format(new Date(nextMonday_l + 24 * 60 * 60 * 1000)); 
//						System.out.println("startTime=========" + startTime + "---------------endTime=========" + endTime);
						paramMap.put("startTime", startTime);
						paramMap.put("endTime", endTime);
						count = patientUserService.count("getAddCount", paramMap);
						dataset.addValue(count, "", startTime+"~");
						
						//退出循环
						bool = false;
					} else { //结束日期 小于 开始日期的下周一，即结束日期与开始日期在同一周内
						startTime = sdf.format(new Date(startTime_l));
						endTime = sdf.format(new Date(endTime_l + 24 * 60 * 60 * 1000));
						paramMap.put("startTime", startTime);
						paramMap.put("endTime", endTime);
						count = patientUserService.count("getAddCount", paramMap);
						dataset.addValue(count, "", startTime+"~");
						
						//退出循环
						bool = false;
					}
				}
			}
		} else if("month".equals(showType) || "按月".equals(showType)){ //按月统计
			//获取当前开始日期下个月第一天日期
			String nextMonthFirstDay_s = Toolkit.getOnedayNextMonthFirstDay(startTime_d);
			Date nextMonthFirstDay_d = null;
			try {
				nextMonthFirstDay_d = sdf.parse(nextMonthFirstDay_s);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			if (nextMonthFirstDay_d != null) {
				long nextMonthFirstDay_l = nextMonthFirstDay_d.getTime();
				boolean bool = true;
				while (bool) {
					if (nextMonthFirstDay_l < endTime_l) { //结束日期 大于 开始日期的下个月第一天
						startTime = sdf.format(new Date(startTime_l));
						endTime = sdf.format(new Date(nextMonthFirstDay_l)); 
//						System.out.println("startTime=========" + startTime + "---------------endTime=========" + endTime);
						paramMap.put("startTime", startTime);
						paramMap.put("endTime", endTime);
						count = patientUserService.count("getAddCount", paramMap);
						dataset.addValue(count, "", startTime+"~");
						startTime_l = nextMonthFirstDay_l;
						try {
							nextMonthFirstDay_l = sdf.parse(Toolkit.getOnedayNextMonthFirstDay(new Date(nextMonthFirstDay_l))).getTime();
						} catch (ParseException e) {
							e.printStackTrace();
							
							//退出循环
							bool = false;
						}
					} else if (nextMonthFirstDay_l == endTime_l) { //结束日期 等于 开始日期的下个月第一天
						startTime = sdf.format(new Date(startTime_l));
						endTime = sdf.format(new Date(nextMonthFirstDay_l)); 
//						System.out.println("startTime=========" + startTime + "---------------endTime=========" + endTime);
						paramMap.put("startTime", startTime);
						paramMap.put("endTime", endTime);
						count = patientUserService.count("getAddCount", paramMap);
						dataset.addValue(count, "", startTime+"~");
						
						startTime = sdf.format(new Date(nextMonthFirstDay_l));
						endTime = sdf.format(new Date(nextMonthFirstDay_l + 24 * 60 * 60 * 1000)); 
//						System.out.println("startTime=========" + startTime + "---------------endTime=========" + endTime);
						paramMap.put("startTime", startTime);
						paramMap.put("endTime", endTime);
						count = patientUserService.count("getAddCount", paramMap);
						dataset.addValue(count, "", startTime+"~");
						
						//退出循环
						bool = false;
					} else { //结束日期 小于 开始日期的下个月第一天，即结束日期与开始日期在同一个月内
						startTime = sdf.format(new Date(startTime_l));
						endTime = sdf.format(new Date(endTime_l + 24 * 60 * 60 * 1000));
						paramMap.put("startTime", startTime);
						paramMap.put("endTime", endTime);
						count = patientUserService.count("getAddCount", paramMap);
						dataset.addValue(count, "", startTime+"~");
						
						//退出循环
						bool = false;
					}
				}
			}
		} else { //按天统计
			endTime_l = endTime_l + 24 * 60 * 60 * 1000; //获取结束日期的下一天毫秒值
			boolean bool_3 = true;
			while (bool_3) {
				if (startTime_l < endTime_l) {
					startTime = sdf.format(new Date(startTime_l));
					endTime = sdf.format(new Date(startTime_l + 24 * 60 * 60 * 1000)); 
//					System.out.println("startTime=========" + startTime + "---------------endTime=========" + endTime);
					paramMap.put("startTime", startTime);
					paramMap.put("endTime", endTime);
					count = patientUserService.count("getAddCount", paramMap);
					dataset.addValue(count, "", startTime);
					startTime_l = startTime_l + 24 * 60 * 60 * 1000;
				} else {
					bool_3 = false;
				}
			}
		}
		
		//设置显示数据集
//		DefaultCategoryDataset  dataset = new DefaultCategoryDataset ();
//		dataset.setValue(count, "", "1月");
//		dataset.setValue(count, "", "2月");
//		dataset.setValue(count, "", "3月");
//		dataset.setValue(count, "", "4月");
//		dataset.setValue(44, "", "5月");
//		dataset.setValue(count, "", "6月");
//		dataset.setValue(33, "", "7月");
//		dataset.setValue(count, "", "8月");
//		dataset.setValue(count, "", "9月");
		
		//创建图表:标题，x轴标签显示文字，y轴标签显示文字，数据集，显示方向，是否显示图例，是否生成热点工具，是否显示url
		JFreeChart chart = ChartFactory.createBarChart("","","addUser-count",dataset,PlotOrientation.VERTICAL,false,false,false);

		//设置标题
//		chart.setTitle(new TextTitle("活跃用户数量", new Font("黑体", Font.BOLD, 12))); //标题，设置字体，不然会中文乱码

		//设置x轴和y轴文字样式
		CategoryPlot plot = chart.getCategoryPlot();
		NumberAxis numberaxis = (NumberAxis) plot.getRangeAxis(); 
		CategoryAxis domainAxis = plot.getDomainAxis();  
		domainAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 10)); //设置X轴坐标上的文字  
		domainAxis.setLabelFont(new Font("宋体", Font.PLAIN, 15)); //设置X轴的标题文字 
		numberaxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 12)); //设置Y轴坐标上的文字  
		numberaxis.setLabelFont(new Font("黑体", Font.PLAIN, 15)); //设置Y轴的标题文字  

		/*------这句代码解决了底部图例汉字乱码的问题,不显示图例时需注释掉-----------*/  
//		chart.getLegend().setItemFont(new Font("宋体", Font.PLAIN, 12));
		
		plot.setBackgroundPaint(Color.white); //设置网格背景颜色
		plot.setDomainGridlinePaint(Color.pink); //设置网格竖线颜色
		plot.setRangeGridlinePaint(Color.pink); //设置网格横线颜色

		//设置每个柱的相关属性
		BarRenderer renderer = new BarRenderer();
//		BarRenderer3D renderer = new BarRenderer3D();
		renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		renderer.setBaseItemLabelsVisible(true);
		//默认的数字显示在柱子中，通过如下两句可调整数字的显示
		//注意：此句很关键，若无此句，那数字的显示会被覆盖，给人数字没有显示出来的问题
		renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT));
		renderer.setItemLabelAnchorOffset(3);
		renderer.setSeriesPaint(0, Color.decode("#5f9ea0"));//设置柱子颜色
		renderer.setShadowVisible(false);//取消阴影效果
		renderer.setBarPainter(new StandardBarPainter());//取消渐变效果
		renderer.setMaximumBarWidth(0.08); //设置柱子宽度
		plot.setRenderer(renderer);

		//将默认放在底部的“类型”放到顶部
		//plot.setDomainAxisLocation(AxisLocation.TOP_OR_RIGHT);
		//将默认放在左边的“数量”放到右方
		//plot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);

		String filename = null;
		try {
			filename = ServletUtilities.saveChartAsPNG(chart, 700, 400, null, request.getSession());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		filename = "/servlet/DisplayChart?filename=" + filename;
		resultMap.put("data", filename);
		return resultMap;
	}
	
}
