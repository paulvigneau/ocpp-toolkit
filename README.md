# OCPP Toolkit

[![OCPP CI](https://github.com/IZIVIA/ocpp-toolkit/actions/workflows/ci.yml/badge.svg)](https://github.com/IZIVIA/ocpp-toolkit/actions/workflows/ci.yml)

This project aim is to provide a Kotlin library to perform OCPP operations.
For more information about OCPP (Open Charge Point Protocol), see https://www.openchargealliance.org/)

## Goal 

The aim is to support:
- both the CSMS and the Charging Station sides
- versions 1.5, 1.6 and 2.0.1 of OCPP
- WS/JSON (OCPP-J - all versions) and SOAP (OCPP-S ; 1.x versions only) flavor for the transport

It can be used:
- to simulate a charging station, eg to test a CSMS
- to simulate a CSMS, eg to test a Charging Station
- to implement a CSMS
- to implement a ChargingStation controller (if the use of Kotlin fit your requirements)

The aim is to be a strict implementation of OCPP protocol, with no business logic: you use it as a library, and you own the business logic.

We also attempt to provide a generic API, trying to make switching between ocpp versions transparent. The design between versions of OCPP being sometimes very different, the generic API may not cover all aspects with high fidelity.

## Status

Currently the ChargingStation side of versions 1.6 and 2.0.1 are fully supported in OCPP-J flavor - except the security requirements besides support for http basic auth. This includes all the data structures described by the specification, with json serialisation verified against the json schemas provided in the specification.

Support of CSMS side, OCPP 1.5 and SOAP flavor is planned.

Support for security requirements like SSL and mutual certificates is under discussion, as it can be achieved using a proxy like Envoy.

## Usage of the API

With the API, you can establish a connection to a CSMS with OCPP 1.6 or OCPP 2.0.
Then, you can send requests and get responses from the CSMS. Doing so, you can simulate
a whole transaction process.

### OCPP 1.6 Example

With the API, you can perform instructions one after the other. In those examples, we're doing full transactions.

OCPP 1.6 Charge : 

```kotlin 
//establish a connection to the CSMS
val connection = Ocpp16ConnectionToCSMS(
        chargePointId = chargPointId,
        csmsUrl = csmsUrl,
        transportType = transport,
        ocppCSCallbacks = OcppCSCallbacks16()
)
connection.connect()

//the idTag variable is used to make unique transaction, so, we can use it to stare at who started a transaction for example
val idTag = "321"

//send an authorize request to the CSMS. We are retreiving the response from the CSMS throught the response variable.
val response: AuthorizeResp16 = connection.authorize(RequestMetadata(chargPointId),AuthorizeReq16(idTag = idTag )).response

//We're checking if the Authorization request has been accepted by the CSMS.
if (response.idTagInfo.status == AuthorizationStatus16.Accepted) {

    println("Authorization Accepted")

    //As the Authorization has been accepted, we can start a transaction, but before,
    //we need to change the Status to Preparing
    connection.statusNotification(
        meta = RequestMetadata(chargPointId),
        request = StatusNotificationReq16(
            connectorId = 1,
            errorCode = ChargePointErrorCode16.NoError,
            status = ChargePointStatus16.Preparing
        )
    )

    //We can now start a transaction by sending a StartTransaction request to the CSMS.
    //We can identify this transaction thanks to the idTag parameter
    //This function returns a response generated by the CSMS
    val response: StartTransactionResp16 =
        connection.startTransaction(
            meta = RequestMetadata(chargPointId),
            request = StartTransactionReq16(
                connectorId = 4,
                idTag = idTag,
                meterStart = 0,
                now()
            )
        ).response

    //we are retrieving the transactionId from the reponse of our previous transaction request.
    val transactionId = response.transactionId

    println("TransactionId : $transactionId")

    // As the authorization has been accepted and that we have sent and receive a response for our startTransaction,
    // we can now set the charge status to Charging.
    // In order to do this, we send a statusNotification to the CSMS notifying that the status has changed to Charging
    connection.statusNotification(
        meta = RequestMetadata(chargPointId),
        request = StatusNotificationReq16(
            connectorId = 1,
            errorCode = ChargePointErrorCode16.NoError,
            status = ChargePointStatus16.Charging
        )
    )
}

connection.close()
```

OCPP 1.6 Remote Charge :
```kotlin 
var remoteStartTransactionReq: RemoteStartTransactionReq16? = null

//As previously said, the idTag variable is used to make unique transaction, so, we can use it to stare at who started a transaction for example
val idTag = "Tag2"

//define the callback for the remoteTransactionRequest
//It returns a RemoteTransactionResponse
val ocppCSCallbacks = object : OcppCSCallbacks16() {
    override fun remoteStartTransaction(req: RemoteStartTransactionReq16): RemoteStartTransactionResp16 {
        remoteStartTransactionReq = req
        return RemoteStartTransactionResp16(status = RemoteStartStopStatus16.Accepted)
    }
}

//establish a connection to the CSMS
val connection = Ocpp16ConnectionToCSMS(
        chargePointId = chargPointId,
        csmsUrl = csmsUrl,
        transportType = transport,
        ocppCSCallbacks = ocppCSCallbacks
)
connection.connect()

//defining the timeout delay for receiving a remoteTransactionRequest
val waitUntil = now() + 1.toDuration(DurationUnit.MINUTES)

//We are waiting for the remote start request from the CSMS
while (remoteStartTransactionReq == null && now() < waitUntil) {
    sleep(1000)
}

//We are checking if there was a remoteTransactionRequest sent
if (remoteStartTransactionReq != null) {
    println("${remoteStartTransactionReq?.idTag}")

    //As the Authorization has been accepted, we can start a transaction, but before,
    //we need to change the Status to Preparing
    connection.statusNotification(
        meta = RequestMetadata(chargPointId),
        request = StatusNotificationReq16(
            connectorId = 1,
            errorCode = ChargePointErrorCode16.NoError,
            status = ChargePointStatus16.Preparing
        )
    )

    //We can now start a transaction by sending a StartTransaction request to the CSMS.
    //We can identify this transaction thanks to the idTag parameter
    //This function returns a response generated by the CSMS
    val response: StartTransactionResp16 =
        connection.startTransaction(
            meta = RequestMetadata(chargPointId),
            StartTransactionReq16(
                connectorId = 1,
                idTag = idTag,
                meterStart = 0,
                timestamp = now()
            )
        ).response

    //we are retrieving the transactionId from the reponse of our previous transaction request.
    val transactionId = response.transactionId

    // As the authorization has been accepted and that we have sent and receive a response for our startTransaction,
    // we can now set the charge status to Charging.
    // In order to do this, we send a statusNotification to the CSMS notifying that the status has changed to Charging
    connection.statusNotification(
        meta = RequestMetadata(chargPointId),
        StatusNotificationReq16(
            connectorId = 1,
            errorCode = ChargePointErrorCode16.NoError,
            status = ChargePointStatus16.Charging
        )
    )
}
connection.close()
```

### OCPP 2.0 Example

OCPP 2.0 Charge :
```kotlin 
val connection = Ocpp20ConnectionToCSMS(
        chargePointId = chargPointId,
        csmsUrl = csmsUrl,
        transportType = transport,
        ocppCSCallbacks = OcppCSCallbacks()
)
connection.connect()

val response: AuthorizeResp = connection.authorize(RequestMetadata(chargPointId), AuthorizeReq(IdTokenType(
        idToken = "2233223",
        type = IdTokenEnumType.Central,
))).response

if (response.idTokenInfo.status == AuthorizationStatusEnumType.Accepted) {
    connection.statusNotification(
            meta = RequestMetadata(chargPointId),
            request = StatusNotificationReq(
                    connectorId = 1,
                    connectorStatus = ConnectorStatusEnumType.Occupied,
                    evseId = 1,
                    timestamp = now()
            )
    )

    val response: TransactionEventResp =
            connection.transactionEvent(
                    meta = RequestMetadata(chargPointId),
                    request = TransactionEventReq(
                            eventType = TransactionEventEnumType.Started,
                            timestamp = now(),
                            triggerReason = TriggerReasonEnumType.Authorized,
                            seqNo = 1,
                            transactionInfo = TransactionType(
                                    "1",
                                    ChargingStateEnumType.Charging
                            )
                    )
            ).response

    connection.statusNotification(
        meta = RequestMetadata(chargPointId),
        request = StatusNotificationReq(
                connectorId = 1,
                connectorStatus = ConnectorStatusEnumType.Occupied,
                evseId = 1,
                timestamp = now()
        )
    )
}
connection.close()
```

OCPP 2.0 Remote Charge :
```kotlin 
var remoteStartTransactionReq: RequestStartTransactionReq? = null
val ocppCSCallbacks = object : OcppCSCallbacks() {
    override fun requestStartTransaction(req: RequestStartTransactionReq): RequestStartTransactionResp {
        remoteStartTransactionReq = req
        return RequestStartTransactionResp(RequestStartStopStatusEnumType.Accepted)
    }
}

val connection = Ocpp20ConnectionToCSMS(
        chargePointId = chargPointId,
        csmsUrl = csmsUrl,
        transportType = transport,
        ocppCSCallbacks = ocppCSCallbacks
)
connection.connect()

val waitUntil = now() + 1.toDuration(DurationUnit.MINUTES)

while (remoteStartTransactionReq == null && now() < waitUntil) {
    sleep(100)
}

if (remoteStartTransactionReq != null) {
    println("${remoteStartTransactionReq?.idToken?.idToken}")
    connection.statusNotification(
            meta = RequestMetadata(chargPointId),
            request = StatusNotificationReq(
                    connectorId = 1,
                    connectorStatus = ConnectorStatusEnumType.Occupied,
                    evseId = 1,
                    timestamp = now()
            )
    )

    val response: TransactionEventResp =
            connection.transactionEvent(
                    meta = RequestMetadata(chargPointId),
                    request = TransactionEventReq(
                            eventType = TransactionEventEnumType.Started,
                            timestamp = now(),
                            triggerReason = TriggerReasonEnumType.Authorized,
                            seqNo = 1,
                            transactionInfo = TransactionType(
                                    "1",
                                    ChargingStateEnumType.Charging
                            )
                    )
            ).response

    connection.statusNotification(
            meta = RequestMetadata(chargPointId),
            request = StatusNotificationReq(
                    connectorId = 1,
                    connectorStatus = ConnectorStatusEnumType.Occupied,
                    evseId = 1,
                    timestamp = now()
            )
    )
}
connection.close()
```

## Code Organisation

The main entry point is in the module `toolkit`, which provides access to all the apis and all ocpp versions.

For each version of ocpp supported, you will find:
- a `-core` module which provided all the data structures and operations declaration.
- a `-api-adapter` module which is used as an adapter between that version of ocpp and the generic api

Plus, you will find:
- `generic-api`, which is an ocpp independent api which can be used to switch between ocpp versions without changing your code
- `ocpp-transport`, which defines an interface for different transport of operations, with the modules `-websocket` and `-soap` for implementations
- `operation-information`, used to described operations in whatever version of apis
- `utils`, used to ease some common needs between apis
- `ocpp-wamp`, a client & server implementation of the WAMP-like RPC-over-websocket system defined in the OCPP-J protcols

