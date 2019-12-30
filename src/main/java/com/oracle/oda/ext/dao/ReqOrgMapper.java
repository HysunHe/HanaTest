/***************************************************************************
 *
 * PoC code for evaluating purpose only. No guarantee of quality.
 *
 ***************************************************************************/

package com.oracle.oda.ext.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.oracle.oda.ext.domains.ReqOrg;

/***************************************************************************
 * <PRE>
 *  Project Name    : ssp
 * 
 *  Package Name    : com.oracle.oda.ext.dao
 * 
 *  File Name       : ReqOrgMapper.java
 * 
 *  Creation Date   : 2019年12月30日
 * 
 *  Author          : Hysun He
 * 
 *  Purpose         : TODO
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
@Mapper
public interface ReqOrgMapper {
	void insert(ReqOrg o);

	ReqOrg get(@Param("orgCode") String orgCode);
}
