package ss.dataprocess.spresulthandler.impl;

import java.math.BigDecimal;
import java.util.Map;
import oracle.jdbc.driver.OracleCallableStatement;
import oracle.sql.ARRAY;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import ss.dataprocess.eh.helper.ErrorCodeHelper;
import ss.dataprocess.model.SpParameterVO;
import ss.dataprocess.model.StoredProcedure;
import ss.dataprocess.model.TransContextVO;
import ss.dataprocess.model.TransInstanceSqVO;
import ss.dataprocess.model.TransformationVO;
import ss.dataprocess.service.ITransHelper;
import ss.dataprocess.spresulthandler.ResultHandler;

public class ExecInfoHandler
  implements ResultHandler
{
  public void handle(OracleCallableStatement statement, TransContextVO transContext, ITransHelper transHelper)
    throws Exception
  {
    int seq = 0;
    boolean hasParam = true;
    try
    {
      seq = transContext.getTransInfo().getStoredProcedure().getExecInfoList().getParamSeq().intValue();
    }
    catch (Exception e)
    {
      String reProcessFlag = (String)transContext.getTransInfo().getKettleVariable().get("REPROCESS_AND_UI_FLAG");
      if ("Y".equals(reProcessFlag)) {
        throw ErrorCodeHelper.createSPInvokerException("SP1004", 
          new String[] { "v_exec_info_list" }, null);
      }
      hasParam = false;
    }
    if (hasParam) {
      ARRAY ary = statement.getARRAY(seq);
      Datum[] dtm = ary.getOracleArray();
      for (int i = 0; i < dtm.length; i++)
      {
        STRUCT st = (STRUCT)dtm[i];
        Object[] obj = st.getAttributes();
        TransInstanceSqVO transInstanceSqVO = transContext.getTransInstanceSqVO();
        transInstanceSqVO.setEventStatus((String)obj[0]);
        transInstanceSqVO.setRecordCount(((BigDecimal)obj[1]).intValue());
        transInstanceSqVO.setProcessedRecordCount(((BigDecimal)obj[2]).intValue());
        transInstanceSqVO.setErrorRecordCount(((BigDecimal)obj[1]).intValue());
        transInstanceSqVO.setErrorRecordCount(((BigDecimal)obj[4]).intValue());
      }
    } else {
      TransInstanceSqVO transInstanceSqVO = transContext.getTransInstanceSqVO();
      transInstanceSqVO.setEventStatus("U");
      transInstanceSqVO.setRecordCount(0);
      transInstanceSqVO.setProcessedRecordCount(0);
      transInstanceSqVO.setErrorRecordCount(0);
      transInstanceSqVO.setErrorRecordCount(0);
    }
  }
}