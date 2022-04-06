package fr.simatix.cs.simulator.adapter16.mapper

import fr.simatix.cs.simulator.api.model.remotestop.RequestStopTransactionReq
import fr.simatix.cs.simulator.api.model.remotestop.RequestStopTransactionResp
import fr.simatix.cs.simulator.core16.model.remotestop.RemoteStopTransactionReq
import fr.simatix.cs.simulator.core16.model.remotestop.RemoteStopTransactionResp
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface RemoteStopTransactionMapper {

    fun genToCoreResp(remoteStopResp: RequestStopTransactionResp?): RemoteStopTransactionResp
    
    fun coreToGenReq(remoteStopReq: RemoteStopTransactionReq): RequestStopTransactionReq

}