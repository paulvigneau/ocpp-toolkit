package fr.simatix.cs.simulator.integration.test

import fr.simatix.cs.simulator.api.model.*
import fr.simatix.cs.simulator.api.model.authorize.AuthorizeReq
import fr.simatix.cs.simulator.api.model.bootnotification.BootNotificationReq
import fr.simatix.cs.simulator.api.model.bootnotification.ChargingStationType
import fr.simatix.cs.simulator.api.model.bootnotification.enumeration.BootReasonEnumType
import fr.simatix.cs.simulator.api.model.bootnotification.enumeration.RegistrationStatusEnumType
import fr.simatix.cs.simulator.api.model.common.IdTokenType
import fr.simatix.cs.simulator.api.model.common.MeterValueType
import fr.simatix.cs.simulator.api.model.common.SampledValueType
import fr.simatix.cs.simulator.api.model.common.SignedMeterValueType
import fr.simatix.cs.simulator.api.model.common.enumeration.*
import fr.simatix.cs.simulator.api.model.datatransfer.DataTransferReq
import fr.simatix.cs.simulator.api.model.datatransfer.enumeration.DataTransferStatusEnumType
import fr.simatix.cs.simulator.api.model.heartbeat.HeartbeatReq
import fr.simatix.cs.simulator.api.model.metervalues.MeterValuesReq
import fr.simatix.cs.simulator.integration.CSMSApiFactory
import fr.simatix.cs.simulator.integration.model.Settings
import fr.simatix.cs.simulator.integration.model.TransportEnum
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.mockkStatic
import io.simatix.ev.ocpp.OcppVersion
import io.simatix.ev.ocpp.wamp.client.OcppWampClient
import io.simatix.ev.ocpp.wamp.client.impl.OkHttpOcppWampClient
import io.simatix.ev.ocpp.wamp.messages.WampMessage
import io.simatix.ev.ocpp.wamp.messages.WampMessageType
import kotlinx.datetime.Instant
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import java.util.*

class IntegrationTest {

    private lateinit var ocppWampClient: OkHttpOcppWampClient

    @BeforeEach
    fun init() {
        val id = "a727d144-82bb-497a-a0c7-4ef2295910d4"
        val uuid = UUID.fromString(id)
        mockkStatic(UUID::class)
        every { UUID.randomUUID() } returns uuid

        ocppWampClient = mockk()
        every { ocppWampClient.connect() } returns Unit
        every { ocppWampClient.close() } returns Unit

        mockkObject(OcppWampClient.Companion)
        every { OcppWampClient.Companion.newClient(any(), any(), any(), any()) } returns ocppWampClient
    }

    @Test
    fun `heartbeat 1-6 request`() {

        every { ocppWampClient.sendBlocking(any()) } returns WampMessage(
            msgId = "a727d144-82bb-497a-a0c7-4ef2295910d4",
            msgType = WampMessageType.CALL_RESULT,
            payload = "{\"currentTime\":\"2022-02-15T00:00:00.000Z\"}",
            action = "heartbeat"
        )

        val settings = Settings(OcppVersion.OCPP_1_6, TransportEnum.WEBSOCKET, target = "")
        val ocppId = "chargePoint2"
        val csmsApi = CSMSApiFactory.getCSMSApi(settings, ocppId)

        val requestMetadata = RequestMetadata(ocppId)
        val request = HeartbeatReq()
        val response = csmsApi.heartbeat(requestMetadata, request)
        expectThat(response)
            .and { get { this.response.currentTime }.isEqualTo(Instant.parse("2022-02-15T00:00:00.000Z")) }
    }

    @Test
    fun `authorize 1-6 request`() {

        every { ocppWampClient.sendBlocking(any()) } returns WampMessage(
            msgId = "a727d144-82bb-497a-a0c7-4ef2295910d4",
            msgType = WampMessageType.CALL_RESULT,
            payload = "{\"idTagInfo\":{\"status\" : \"Accepted\", \"expiryDate\" : \"2022-02-15T00:00:00.000Z\", \"parentIdTag\" : \"Tag2\" }}",
            action = "authorize"
        )

        val settings = Settings(OcppVersion.OCPP_1_6, TransportEnum.WEBSOCKET, target = "")
        val ocppId = "chargePoint2"
        val csmsApi = CSMSApiFactory.getCSMSApi(settings, ocppId)

        val requestMetadata = RequestMetadata(ocppId)
        val request = AuthorizeReq(idToken = IdTokenType("Tag1", IdTokenEnumType.Central))
        val response = csmsApi.authorize(requestMetadata, request)
        expectThat(response)
            .and { get { this.response.idTokenInfo.status }.isEqualTo(AuthorizationStatusEnumType.Accepted) }
            .and { get { this.response.idTokenInfo.cacheExpiryDateTime }.isEqualTo(Instant.parse("2022-02-15T00:00:00.000Z")) }
            .and { get { this.response.idTokenInfo.groupIdToken?.idToken }.isEqualTo("Tag2") }
    }

    @Test
    fun `meterValues 1-6 request`() {

        every { ocppWampClient.sendBlocking(any()) } returns WampMessage(
            msgId = "a727d144-82bb-497a-a0c7-4ef2295910d4",
            msgType = WampMessageType.CALL_RESULT,
            payload = "{}",
            action = "meterValues"
        )

        val settings = Settings(OcppVersion.OCPP_1_6, TransportEnum.WEBSOCKET, target = "")
        val ocppId = "chargePoint2"
        val csmsApi = CSMSApiFactory.getCSMSApi(settings, ocppId)

        val requestMetadata = RequestMetadata(ocppId)
        val request = MeterValuesReq(
            3, listOf(
                MeterValueType(
                    listOf(
                        SampledValueType(0.6), SampledValueType(
                            2.4,
                            ReadingContextEnumType.SampleClock,
                            MeasurandEnumType.Temperature,
                            PhaseEnumType.L1,
                            LocationEnumType.Body,
                            signedMeterValue = SignedMeterValueType("Hello", "Bye", "Welcome", "Nice")
                        )
                    ),
                    Instant.parse("2022-02-15T00:00:00.000Z"),
                )
            )
        )
        val response = csmsApi.meterValues(requestMetadata, request)
        expectThat(response)
            .and { get { this.executionMeta.status }.isEqualTo(RequestStatus.SUCCESS) }

        val request2 = MeterValuesReq(
            3, listOf(
                MeterValueType(
                    listOf(
                        SampledValueType(0.6), SampledValueType(
                            2.4,
                            ReadingContextEnumType.SampleClock,
                            MeasurandEnumType.EnergyApparentNet,
                            PhaseEnumType.L1,
                            LocationEnumType.Body,
                            signedMeterValue = SignedMeterValueType("Hello", "Bye", "Welcome", "Nice")
                        )
                    ),
                    Instant.parse("2022-02-15T00:00:00.000Z"),
                )
            )
        )
        val response2 = csmsApi.meterValues(requestMetadata, request2)
        expectThat(response2)
            .and { get { this.executionMeta.status }.isEqualTo(RequestStatus.NOT_SEND) }
    }

    @Test
    fun `dataTransfer 1-6 request`() {

        every { ocppWampClient.sendBlocking(any()) } returns WampMessage(
            msgId = "a727d144-82bb-497a-a0c7-4ef2295910d4",
            msgType = WampMessageType.CALL_RESULT,
            payload = "{\"status\" : \"Accepted\", \"data\" : \"2022-02-15T00:00:00.000Z\"}",
            action = "DataTransfer"
        )

        val settings = Settings(OcppVersion.OCPP_1_6, TransportEnum.WEBSOCKET, target = "")
        val ocppId = "chargePoint2"
        val csmsApi = CSMSApiFactory.getCSMSApi(settings, ocppId)

        val requestMetadata = RequestMetadata(ocppId)
        val request = DataTransferReq("vendor", "msgId12", "Hello")
        val response = csmsApi.dataTransfer(requestMetadata, request)
        expectThat(response)
            .and { get { this.executionMeta.status }.isEqualTo(RequestStatus.SUCCESS) }
            .and { get { this.response.data }.isEqualTo("2022-02-15T00:00:00.000Z") }
            .and { get { this.response.status }.isEqualTo(DataTransferStatusEnumType.Accepted) }
            .and { get { this.response.statusInfo }.isEqualTo(null) }
    }

    @Test
    fun `bootNotification 1-6 request`() {

        every { ocppWampClient.sendBlocking(any()) } returns WampMessage(
            msgId = "a727d144-82bb-497a-a0c7-4ef2295910d4",
            msgType = WampMessageType.CALL_RESULT,
            payload = "{\"currentTime\" : \"2022-02-15T00:00:00.000Z\", \"interval\" : 10, \"status\": \"Accepted\"}",
            action = "BootNotification"
        )

        val settings = Settings(OcppVersion.OCPP_1_6, TransportEnum.WEBSOCKET, target = "")
        val ocppId = "chargePoint2"
        val csmsApi = CSMSApiFactory.getCSMSApi(settings, ocppId)

        val requestMetadata = RequestMetadata(ocppId)
        val request = BootNotificationReq(ChargingStationType("model","vendor","firware"),BootReasonEnumType.ApplicationReset)
        val response = csmsApi.bootNotification(requestMetadata, request)
        expectThat(response)
            .and { get { this.executionMeta.status }.isEqualTo(RequestStatus.SUCCESS) }
            .and { get { this.response.currentTime }.isEqualTo(Instant.parse("2022-02-15T00:00:00.000Z")) }
            .and { get { this.response.interval }.isEqualTo(10) }
            .and { get { this.response.status }.isEqualTo(RegistrationStatusEnumType.Accepted) }
    }


}