/***************************************************************************
 *
 * PoC code for evaluating purpose only. No guarantee of quality.
 *
 ***************************************************************************/

package com.oracle.oda.ext.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.oracle.oda.ext.domains.CpmTransaction;

/***************************************************************************
 * <PRE>
 *  Project Name    : ssp
 * 
 *  Package Name    : com.oracle.oda.ext.dao
 * 
 *  File Name       : CpmTransactionMapper.java
 * 
 *  Creation Date   : 2019年12月25日
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
public interface CpmTransactionMapper {
	void insert(CpmTransaction o);

	CpmTransaction get(@Param("glnTxNo") String glnTxNo,
			@Param("status") String status);
}
