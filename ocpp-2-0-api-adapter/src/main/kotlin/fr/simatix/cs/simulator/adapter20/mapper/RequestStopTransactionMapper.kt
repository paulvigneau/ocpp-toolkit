package fr.simatix.cs.simulator.adapter20.mapper

import fr.simatix.cs.simulator.api.model.remotestop.RequestStopTransactionReq as RequestStopTransactionReqGen
import fr.simatix.cs.simulator.api.model.remotestop.RequestStopTransactionResp as RequestStopTransactionRespGen
import fr.simatix.cs.simulator.core20.model.remotestop.RequestStopTransactionReq
import fr.simatix.cs.simulator.core20.model.remotestop.RequestStopTransactionResp
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface RequestStopTransactionMapper {

    fun genToCoreResp(remoteStopResp: RequestStopTransactionRespGen?): RequestStopTransactionResp

    fun coreToGenReq(remoteStopReq: RequestStopTransactionReq): RequestStopTransactionReqGen

}