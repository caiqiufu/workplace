package com.unieap.monitor.bo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.unieap.BaseBO;
import com.unieap.UnieapConstants;
import com.unieap.base.vo.PaginationSupport;
import com.unieap.db.DBManager;
import com.unieap.mantis.vo.MTestcaseVO;
import com.unieap.pojo.MNote;
import com.unieap.pojo.MTestcase;
@Service("mediationBO")
public class MediationBO extends BaseBO{
	public Map<String, String> generateStatic() throws Exception{
		
		return result(UnieapConstants.ISSUCCESS,UnieapConstants.SUCCESS);
	}
}
