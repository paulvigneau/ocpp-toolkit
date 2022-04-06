package fr.simatix.cs.simulator

import fr.simatix.cs.simulator.adapter16.mapper.*
import fr.simatix.cs.simulator.api.model.cancelreservation.enumeration.CancelReservationStatusEnumType
import fr.simatix.cs.simulator.api.model.changeavailability.enumeration.ChangeAvailabilityStatusEnumType
import fr.simatix.cs.simulator.api.model.changeavailability.enumeration.OperationalStatusEnumType
import fr.simatix.cs.simulator.api.model.clearcache.enumeration.ClearCacheStatusEnumType
import fr.simatix.cs.simulator.api.model.common.ComponentType
import fr.simatix.cs.simulator.api.model.common.EVSEType
import fr.simatix.cs.simulator.api.model.common.StatusInfoType
import fr.simatix.cs.simulator.api.model.common.VariableType
import fr.simatix.cs.simulator.api.model.common.enumeration.IdTokenEnumType
import fr.simatix.cs.simulator.api.model.common.enumeration.RequestStartStopStatusEnumType
import fr.simatix.cs.simulator.api.model.getallvariables.GetAllVariablesReq
import fr.simatix.cs.simulator.api.model.getvariables.GetVariableResultType
import fr.simatix.cs.simulator.api.model.getvariables.GetVariablesResp
import fr.simatix.cs.simulator.api.model.getvariables.enumeration.GetVariableStatusEnumType
import fr.simatix.cs.simulator.api.model.remotestart.ChargingSchedulePeriodType
import fr.simatix.cs.simulator.api.model.remotestart.ChargingScheduleType
import fr.simatix.cs.simulator.api.model.remotestart.RequestStartTransactionResp
import fr.simatix.cs.simulator.api.model.remotestart.enumeration.ChargingProfileKindEnumType
import fr.simatix.cs.simulator.api.model.remotestart.enumeration.ChargingProfilePurposeEnumType
import fr.simatix.cs.simulator.api.model.remotestart.enumeration.ChargingRateUnitEnumType
import fr.simatix.cs.simulator.api.model.remotestop.RequestStopTransactionResp
import fr.simatix.cs.simulator.api.model.setvariables.SetVariableResultType
import fr.simatix.cs.simulator.api.model.setvariables.SetVariablesResp
import fr.simatix.cs.simulator.api.model.setvariables.enumeration.SetVariableStatusEnumType
import fr.simatix.cs.simulator.api.model.unlockconnector.UnlockConnectorResp
import fr.simatix.cs.simulator.api.model.unlockconnector.enumeration.UnlockStatusEnumType
import fr.simatix.cs.simulator.core16.model.cancelreservation.CancelReservationReq
import fr.simatix.cs.simulator.core16.model.cancelreservation.enumeration.CancelReservationStatus
import fr.simatix.cs.simulator.core16.model.changeavailability.ChangeAvailabilityReq
import fr.simatix.cs.simulator.core16.model.changeavailability.enumeration.AvailabilityStatus
import fr.simatix.cs.simulator.core16.model.changeavailability.enumeration.AvailabilityType
import fr.simatix.cs.simulator.core16.model.changeconfiguration.ChangeConfigurationReq
import fr.simatix.cs.simulator.core16.model.changeconfiguration.enumeration.ConfigurationStatus
import fr.simatix.cs.simulator.core16.model.clearcache.ClearCacheReq
import fr.simatix.cs.simulator.core16.model.clearcache.enumeration.ClearCacheStatus
import fr.simatix.cs.simulator.core16.model.common.enumeration.RemoteStartStopStatus
import fr.simatix.cs.simulator.core16.model.getconfiguration.GetConfigurationReq
import fr.simatix.cs.simulator.core16.model.getconfiguration.KeyValue
import fr.simatix.cs.simulator.core16.model.remotestart.ChargingProfile
import fr.simatix.cs.simulator.core16.model.remotestart.ChargingSchedule
import fr.simatix.cs.simulator.core16.model.remotestart.ChargingSchedulePeriod
import fr.simatix.cs.simulator.core16.model.remotestart.RemoteStartTransactionReq
import fr.simatix.cs.simulator.core16.model.remotestart.enumeration.ChargingProfileKindType
import fr.simatix.cs.simulator.core16.model.remotestart.enumeration.ChargingProfilePurposeType
import fr.simatix.cs.simulator.core16.model.remotestart.enumeration.ChargingRateUnitType
import fr.simatix.cs.simulator.core16.model.remotestop.RemoteStopTransactionReq
import fr.simatix.cs.simulator.core16.model.unlockconnector.UnlockConnectorReq
import fr.simatix.cs.simulator.core16.model.unlockconnector.enumeration.UnlockStatus
import org.junit.jupiter.api.Test
import org.mapstruct.factory.Mappers
import strikt.api.expectThat
import strikt.api.expectThrows
import strikt.assertions.isA
import strikt.assertions.isEqualTo
import fr.simatix.cs.simulator.api.model.cancelreservation.CancelReservationResp as CancelReservationRespGen
import fr.simatix.cs.simulator.api.model.changeavailability.ChangeAvailabilityResp as ChangeAvailabilityRespGen
import fr.simatix.cs.simulator.api.model.clearcache.ClearCacheReq as ClearCacheReqGen
import fr.simatix.cs.simulator.api.model.clearcache.ClearCacheResp as ClearCacheRespGen

class MapperTest {
    @Test
    fun changeAvailabilityMapper() {
        val mapper: ChangeAvailabilityMapper = Mappers.getMapper(ChangeAvailabilityMapper::class.java)
        val resp = mapper.genToCoreResp(
            ChangeAvailabilityRespGen(
                ChangeAvailabilityStatusEnumType.Accepted,
                StatusInfoType("reason", "additional")
            )
        )
        expectThat(resp)
            .and { get { status }.isEqualTo(AvailabilityStatus.Accepted) }

        val req = mapper.coreToGenReq(ChangeAvailabilityReq(1, AvailabilityType.Operative))
        expectThat(req)
            .and { get { operationalStatus }.isEqualTo(OperationalStatusEnumType.Operative) }
            .and { get { evse }.isEqualTo(EVSEType(1, 1)) }
    }

    @Test
    fun clearCacheMapper() {
        val mapper: ClearCacheMapper = Mappers.getMapper(ClearCacheMapper::class.java)
        val resp = mapper.genToCoreResp(ClearCacheRespGen(ClearCacheStatusEnumType.Accepted))
        expectThat(resp)
            .and { get { status }.isEqualTo(ClearCacheStatus.Accepted) }

        val req = mapper.coreToGenReq(ClearCacheReq())
        expectThat(req)
            .and { get { req }.isA<ClearCacheReqGen>() }
    }

    @Test
    fun unlockConnectorMapper() {
        val mapper: UnlockConnectorMapper = Mappers.getMapper(UnlockConnectorMapper::class.java)
        val req = mapper.genToCoreResp(
            UnlockConnectorResp(
                UnlockStatusEnumType.UnknownConnector,
                StatusInfoType("reason", "additional")
            )
        )
        expectThat(req)
            .and { get { status }.isEqualTo(UnlockStatus.NotSupported) }

        val resp = mapper.coreToGenReq(UnlockConnectorReq(1))
        expectThat(resp)
            .and { get { connectorId }.isEqualTo(1) }
            .and { get { evseId }.isEqualTo(1) }
    }

    @Test
    fun remoteStartTransactionMapper() {
        val mapper: RemoteStartTransactionMapper = Mappers.getMapper(RemoteStartTransactionMapper::class.java)
        val resp = mapper.genToCoreResp(RequestStartTransactionResp(RequestStartStopStatusEnumType.Accepted))
        expectThat(resp)
            .and { get { status }.isEqualTo(RemoteStartStopStatus.Accepted) }

        val req = mapper.coreToGenReq(
            RemoteStartTransactionReq(
                "tag1", 11,
                ChargingProfile(
                    12, 13, ChargingProfilePurposeType.ChargePointMaxProfile, ChargingProfileKindType.Absolute,
                    ChargingSchedule(ChargingRateUnitType.A, listOf(ChargingSchedulePeriod(1, 1.3)))
                )
            ), 10
        )
        expectThat(req)
            .and { get { idToken.idToken }.isEqualTo("tag1") }
            .and { get { idToken.type }.isEqualTo(IdTokenEnumType.Central) }
            .and { get { remoteStartId }.isEqualTo(10) }
            .and { get { evseId }.isEqualTo(11) }
            .and { get { chargingProfile?.id }.isEqualTo(12) }
            .and { get { chargingProfile?.stackLevel }.isEqualTo(13) }
            .and { get { chargingProfile?.chargingProfilePurpose }.isEqualTo(ChargingProfilePurposeEnumType.ChargingStationMaxProfile) }
            .and { get { chargingProfile?.chargingProfileKind }.isEqualTo(ChargingProfileKindEnumType.Absolute) }
            .and {
                get { chargingProfile?.chargingSchedule }.isEqualTo(
                    listOf(
                        ChargingScheduleType(
                            null, ChargingRateUnitEnumType.A,
                            listOf(ChargingSchedulePeriodType(1, 1.3))
                        )
                    )
                )
            }
    }

    @Test
    fun remoteStopTransactionMapper() {
        val mapper: RemoteStopTransactionMapper = Mappers.getMapper(RemoteStopTransactionMapper::class.java)
        val resp = mapper.genToCoreResp(
            RequestStopTransactionResp(RequestStartStopStatusEnumType.Rejected, StatusInfoType("reason", "additional"))
        )
        expectThat(resp)
            .and { get { status }.isEqualTo(RemoteStartStopStatus.Rejected) }

        val req = mapper.coreToGenReq(RemoteStopTransactionReq(1))
        expectThat(req)
            .and { get { transactionId }.isEqualTo("1") }
    }

    @Test
    fun changeConfigurationMapper() {
        val mapper: ChangeConfigurationMapper = Mappers.getMapper(ChangeConfigurationMapper::class.java)
        val resp = mapper.genToCoreResp(
            SetVariablesResp(
                listOf(
                    SetVariableResultType(
                        attributeStatus = SetVariableStatusEnumType.NotSupportedAttributeType,
                        component = ComponentType("component"),
                        variable = VariableType("variable")
                    )
                )
            )
        )
        expectThat(resp)
            .and { get { status }.isEqualTo(ConfigurationStatus.NotSupported) }


        expectThrows<IllegalStateException> {
            mapper.genToCoreResp(
                SetVariablesResp(
                    listOf(
                        SetVariableResultType(
                            SetVariableStatusEnumType.NotSupportedAttributeType,
                            ComponentType("component"), VariableType("variable")
                        ),
                        SetVariableResultType(
                            SetVariableStatusEnumType.Accepted,
                            ComponentType("component"), VariableType("variable")
                        )
                    )
                )
            )
        }

        val req = mapper.coreToGenReq(ChangeConfigurationReq("key", "value"))
        expectThat(req)
            .and { get { setVariableData[0].attributeValue }.isEqualTo("value") }
            .and { get { setVariableData[0].variable.name }.isEqualTo("key") }
            .and { get { setVariableData[0].component.name }.isEqualTo("key") }
    }

    @Test
    fun getConfiguration() {
        val mapper: GetConfigurationMapper = Mappers.getMapper(GetConfigurationMapper::class.java)

        val reqAll = mapper.coreToGenGetAllVariablesReq()
        expectThat(reqAll)
            .and { get { this }.isA<GetAllVariablesReq>() }

        val resp = mapper.genToCoreGetVariablesResp(
            GetVariablesResp(
                listOf(
                    GetVariableResultType(
                        attributeStatus = GetVariableStatusEnumType.Accepted,
                        component = ComponentType("global"),
                        variable = VariableType("variable-1", "instance"),
                        readonly = true,
                        attributeValue = "123"
                    ),
                    GetVariableResultType(
                        attributeStatus = GetVariableStatusEnumType.NotSupportedAttributeType,
                        component = ComponentType("global"),
                        variable = VariableType("variable-2", "instance"),
                    )
                )
            )
        )
        expectThat(resp)
            .and { get { configurationKey }.isEqualTo(listOf(KeyValue("variable-1instance", true, "123"))) }
            .and { get { unknownKey }.isEqualTo(listOf("variable-2instance")) }

        val req = mapper.coreToGenGetVariablesReq(GetConfigurationReq(listOf("variable1", "variable2")))
        expectThat(req)
            .and { get { getVariableData[0].variable.name }.isEqualTo("variable1") }
            .and { get { getVariableData[1].variable.name }.isEqualTo("variable2") }
            .and { get { getVariableData[0].component.name }.isEqualTo("variable1") }
            .and { get { getVariableData[1].component.name }.isEqualTo("variable2") }
    }

    @Test
    fun cancelReservationMapper() {
        val mapper: CancelReservationMapper = Mappers.getMapper(CancelReservationMapper::class.java)
        val resp = mapper.genToCoreResp(CancelReservationRespGen(CancelReservationStatusEnumType.Rejected))
        expectThat(resp)
            .and { get { status }.isEqualTo(CancelReservationStatus.Rejected) }

        val req = mapper.coreToGenReq(CancelReservationReq(3))
        expectThat(req)
            .and { get { reservationId }.isEqualTo(3) }
    }

}