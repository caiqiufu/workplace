package com.unieap.monitor.bo;

import java.util.List;

import org.springframework.stereotype.Service;

import com.unieap.BaseBO;
import com.unieap.base.vo.TreeVO;

@Service("taskDisplayBO")
public class TaskDisplayBO extends BaseBO {
	public void taskTree(TreeVO treeVo) {
		/*if (treeVo.getId() == null
				|| treeVo.getId().longValue() == UnieapConstants.TREEROOTID
						.longValue()) {
			DetachedCriteria criteria = DetachedCriteria.forClass(Task.class);
			criteria.add(Restrictions
					.eq("parentId", UnieapConstants.TREEROOTID));
			List<Task> datas = DBManager.getHT(null).findByCriteria(criteria);
			List<TreeVO> roots = new ArrayList<TreeVO>();
			if (datas != null) {
				for (Task task : datas) {
					TreeVO tree = new TreeVO();
					PojoToTreeVo(task, tree);
					tree.setParentId(treeVo.getId());
					roots.add(tree);
				}
			}
			treeVo.setChildren(roots);
		} else {
			DetachedCriteria criteria = DetachedCriteria.forClass(Task.class);
			criteria.add(Restrictions.eq("parentId", treeVo.getId()));
			List<Task> datas = DBManager.getHT(null).findByCriteria(criteria);
			if (datas != null && datas.size() > 0) {
				List<TreeVO> children = new ArrayList<TreeVO>();
				for (Task task : datas) {
					TreeVO tree = new TreeVO();
					PojoToTreeVo(task, tree);
					tree.setParentId(treeVo.getId());
					List<Taskrelation> reldatas = getPreTaskVo(task.getTaskId(),"dep");
					if(reldatas!=null&&reldatas.size()>0){
							tree.getExtendAttri().put("preTaskIds", reldatas.get(0).getOtherTaskIds());
					}
					children.add(tree);
				}
				treeVo.setChildren(children);
				treeVo.setExpanded(true);
			}
		}*/
	}

	public List getPreTaskVo(Long taskId, String relationType) {
		/*StringBuffer relTaskSql = new StringBuffer(
				"select * from taskrelation where taskId = ?");
		List reldatas = DBManager.getJT(null).query(relTaskSql.toString(),
				new Object[]{taskId}, new EntityRowMapper(Taskrelation.class));
		return reldatas;*/
		return null;
	}

	/*public Object PojoToTreeVo(Task po, TreeVO vo) {
		vo.setId(po.getTaskId());
		vo.setLeaf(StringUtils.equals(po.getIsLeaf(), UnieapConstants.Y));
		vo.setText(po.getTaskName());
		vo.setParentId(po.getParentId());
		Map<String, Object> extendAttri = new HashMap<String, Object>();
		extendAttri.put("taskId", po.getTaskId());
		extendAttri.put("taskName", po.getTaskName());
		extendAttri.put("taskNameRemark", po.getTaskNameRemark());
		extendAttri.put("taskGroup", po.getTaskGroup());
		extendAttri.put("path", po.getPath());
		extendAttri.put("parentId", po.getParentId());
		extendAttri.put("isLeaf", po.getIsLeaf());
		extendAttri.put("startDate", po.getStartDate());
		extendAttri.put("startTime", po.getStartTime());
		extendAttri.put("duration", po.getDuration());
		extendAttri.put("status", po.getStatus());
		DicDataVO dic =  CacheMgt.getDicData("TaskStatus",po.getStatus());
		if(dic!=null){
			extendAttri.put("statusDesc",dic.getDicName());
		}
		extendAttri.put("completePer", po.getCompletePer());
		extendAttri.put("isShow", po.getIsShow());
		extendAttri.put("isMain", po.getIsMain());
		extendAttri.put("squNum", po.getSquNum());
		extendAttri.put("endDate", po.getEndDate());
		extendAttri.put("endTime", po.getEndTime());
		extendAttri.put("activeFlag", po.getActiveFlag());
		extendAttri.put("createDatetime", po.getCreateDatetime());
		extendAttri.put("modifyDatetime", po.getModifyDatetime());
		extendAttri.put("remark", po.getRemark());
		extendAttri.put("beId", po.getBeId());
		extendAttri.put("createBy", po.getCreateBy());
		extendAttri.put("modifyBy", po.getModifyBy());
		vo.setExtendAttri(extendAttri);
		vo.setExpanded(false);
		return vo;
	}*/

}
