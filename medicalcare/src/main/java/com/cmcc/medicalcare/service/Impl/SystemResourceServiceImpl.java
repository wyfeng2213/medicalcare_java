package com.cmcc.medicalcare.service.Impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmcc.medicalcare.model.SystemResource;
import com.cmcc.medicalcare.service.ISystemResourceService;
import com.cmcc.medicalcare.vo.Tree;

/**
 * @author Administrator
 *
 */
@Service(value="systemResourceService")
@Transactional
public class SystemResourceServiceImpl extends GenericServiceImpl<SystemResource, Integer> implements ISystemResourceService {

	/**
	 * 根据资源构建两层树
	 */
	@Override
	public List<Tree> findAllTree() {
		//所有树(根)集合
		List<Tree> treeRootList = new ArrayList<Tree>();
        //查询所有树根资源
        List<SystemResource> resourceRootList = getList("findResourceAllByTypeAndPidNull", 0);
        if (resourceRootList == null) {
            return null;
        }
        for (SystemResource resourceRoot : resourceRootList) {
        	//某个树根
            Tree treeRoot = new Tree();
            treeRoot.setId(resourceRoot.getId());
            treeRoot.setText(resourceRoot.getName());
            treeRoot.setIcon(resourceRoot.getIcon());
            treeRoot.setAttributes(resourceRoot.getUrl());
            //某个树根的孩子资源集合
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("resourcetype", 0);
			paramMap.put("pid", resourceRoot.getId());
            List<SystemResource> resourceSonList = getList("findResourceAllByTypeAndPid", paramMap);
            if (resourceSonList != null) {
            	//某个树根的孩子集合
                List<Tree> treeSonList = new ArrayList<Tree>();
                for (SystemResource resourceSon : resourceSonList) {
                	//树根的某一个孩子
                    Tree treeSon = new Tree();
                    treeSon.setId(resourceSon.getId());
                    treeSon.setText(resourceSon.getName());
                    treeSon.setIcon(resourceSon.getIcon());
                    treeSon.setAttributes(resourceSon.getUrl());
                    treeSonList.add(treeSon);
                }
                treeRoot.setChildren(treeSonList);
            } else {
            	treeRoot.setState("closed");
            }
            treeRootList.add(treeRoot);
        }
        return treeRootList;
    }
	
	/**
	 * 根据资源构建三层树
	 */
	@Override
	public List<Tree> findAllTrees() {
		//所有树(根)集合
		List<Tree> treeRootList = new ArrayList<Tree>();
		//查询所有树根资源
        List<SystemResource> resourceRootList = getList("findResourceAllByTypeAndPidNull", 0);
        if (resourceRootList == null) {
            return null;
        }
        for (SystemResource resourceRoot : resourceRootList) {
        	//某个树根
            Tree treeRoot = new Tree();
            treeRoot.setId(resourceRoot.getId());
            treeRoot.setText(resourceRoot.getName());
            treeRoot.setIcon(resourceRoot.getIcon());
            treeRoot.setAttributes(resourceRoot.getUrl());
            //某个树根的孩子资源集合
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("resourcetype", 0);
			paramMap.put("pid", resourceRoot.getId());
            List<SystemResource> resourceSonList = getList("findResourceAllByTypeAndPid", paramMap);
            if (resourceSonList != null) {
            	//某个树根的孩子集合
                List<Tree> treeSonList = new ArrayList<Tree>();
                for (SystemResource resourceSon : resourceSonList) {
                	//树根的某一个孩子
                    Tree treeSon = new Tree();
                    treeSon.setId(resourceSon.getId());
                    treeSon.setText(resourceSon.getName());
                    treeSon.setIcon(resourceSon.getIcon());
                    treeSon.setAttributes(resourceSon.getUrl());
                    
                    /***************************************************/
                    //树根某一个孩子的孙子资源集合
                    Map<String, Object> paramMap2 = new HashMap<String, Object>();
                    paramMap2.put("pid", resourceSon.getId());
                    List<SystemResource> resourceGrandSonList = getList("findResourceAllByTypeAndPid", paramMap2);
                    if (resourceGrandSonList != null) {
                    	//树根某一个孩子的孙子集合
                    	List<Tree> treeGrandSonList = new ArrayList<Tree>();
                    	for (SystemResource resourceGrandSon : resourceGrandSonList) {
                    		//树根的某一个孩子的某一个孙子
                    		Tree treeGrandSon = new Tree();
                    		treeGrandSon.setId(resourceGrandSon.getId());
                    		treeGrandSon.setText(resourceGrandSon.getName());
                    		treeGrandSon.setIcon(resourceGrandSon.getIcon());
                    		treeGrandSon.setAttributes(resourceGrandSon.getUrl());
                    		treeGrandSonList.add(treeGrandSon);
                    	}
                    	treeSon.setChildren(treeGrandSonList);
                    } else {
                    	treeSon.setState("closed");
                    }
                    /***************************************************/
                    
                    treeSonList.add(treeSon);
                }
                treeRoot.setChildren(treeSonList);
            } else {
            	treeRoot.setState("closed");
            }
            treeRootList.add(treeRoot);
        }
        return treeRootList;
    }

	@Override
	public List<SystemResource> findResourceUrlListByRoleId(Integer roleId) {
		return getList("findResourceUrlListByRoleId", roleId);
	}

}
