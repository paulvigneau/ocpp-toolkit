package fr.simatix.cs.simulator.adapter20.mapper

import fr.simatix.cs.simulator.core20.model.getbasereport.GetBaseReportReq
import fr.simatix.cs.simulator.core20.model.getbasereport.GetBaseReportResp
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy
import fr.simatix.cs.simulator.api.model.getbasereport.GetBaseReportReq as GetBaseReportReqGen
import fr.simatix.cs.simulator.api.model.getbasereport.GetBaseReportResp as GetBaseReportRespGen

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
abstract class GetBaseReportMapper {

    abstract fun genToCoreResp(getBaseReportResp: GetBaseReportRespGen?): GetBaseReportResp

    abstract fun coreToGenReq(getBaseReportReq: GetBaseReportReq): GetBaseReportReqGen

}