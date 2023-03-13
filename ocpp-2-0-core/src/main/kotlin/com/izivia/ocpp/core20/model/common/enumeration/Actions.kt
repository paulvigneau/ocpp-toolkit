package com.izivia.ocpp.core20.model.common.enumeration

import com.izivia.ocpp.core20.model.authorize.AuthorizeReq
import com.izivia.ocpp.core20.model.bootnotification.BootNotificationReq
import com.izivia.ocpp.core20.model.cancelreservation.CancelReservationReq
import com.izivia.ocpp.core20.model.certificateSigned.CertificateSignedReq
import com.izivia.ocpp.core20.model.changeavailability.ChangeAvailabilityReq
import com.izivia.ocpp.core20.model.clearcache.ClearCacheReq
import com.izivia.ocpp.core20.model.clearchargingprofile.ClearChargingProfileReq
import com.izivia.ocpp.core20.model.cleardisplaymessage.ClearDisplayMessageReq
import com.izivia.ocpp.core20.model.clearedcharginglimit.ClearedChargingLimitReq
import com.izivia.ocpp.core20.model.clearvariablemonitoring.ClearVariableMonitoringReq
import com.izivia.ocpp.core20.model.costupdated.CostUpdatedReq
import com.izivia.ocpp.core20.model.customerinformation.CustomerInformationReq
import com.izivia.ocpp.core20.model.datatransfer.DataTransferReq
import com.izivia.ocpp.core20.model.deletecertificate.DeleteCertificateReq
import com.izivia.ocpp.core20.model.firmwarestatusnotification.FirmwareStatusNotificationReq
import com.izivia.ocpp.core20.model.get15118evcertificate.Get15118EVCertificateReq
import com.izivia.ocpp.core20.model.getbasereport.GetBaseReportReq
import com.izivia.ocpp.core20.model.getcertificatestatus.GetCertificateStatusReq
import com.izivia.ocpp.core20.model.getchargingprofiles.GetChargingProfilesReq
import com.izivia.ocpp.core20.model.getcompositeschedule.GetCompositeScheduleReq
import com.izivia.ocpp.core20.model.getdisplaymessages.GetDisplayMessagesReq
import com.izivia.ocpp.core20.model.getinstalledcertificateids.GetInstalledCertificateIdsReq
import com.izivia.ocpp.core20.model.getlocallistversion.GetLocalListVersionReq
import com.izivia.ocpp.core20.model.getlog.GetLogReq
import com.izivia.ocpp.core20.model.getmonitoringreport.GetMonitoringReportReq
import com.izivia.ocpp.core20.model.getreport.GetReportReq
import com.izivia.ocpp.core20.model.gettransactionstatus.GetTransactionStatusReq
import com.izivia.ocpp.core20.model.getvariables.GetVariablesReq
import com.izivia.ocpp.core20.model.heartbeat.HeartbeatReq
import com.izivia.ocpp.core20.model.installcertificate.InstallCertificateReq
import com.izivia.ocpp.core20.model.logstatusnotification.LogStatusNotificationReq
import com.izivia.ocpp.core20.model.metervalues.MeterValuesReq
import com.izivia.ocpp.core20.model.notifycharginglimit.NotifyChargingLimitReq
import com.izivia.ocpp.core20.model.notifycustomerinformation.NotifyCustomerInformationReq
import com.izivia.ocpp.core20.model.notifydisplaymessages.NotifyDisplayMessagesReq
import com.izivia.ocpp.core20.model.notifyevchargingneeds.NotifyEVChargingNeedsReq
import com.izivia.ocpp.core20.model.notifyevchargingschedule.NotifyEVChargingScheduleReq
import com.izivia.ocpp.core20.model.notifyevent.NotifyEventReq
import com.izivia.ocpp.core20.model.notifymonitoringreport.NotifyMonitoringReportReq
import com.izivia.ocpp.core20.model.notifyreport.NotifyReportReq
import com.izivia.ocpp.core20.model.publishfirmware.PublishFirmwareReq
import com.izivia.ocpp.core20.model.publishfirmwarestatusnotification.PublishFirmwareStatusNotificationReq
import com.izivia.ocpp.core20.model.remotestart.RequestStartTransactionReq
import com.izivia.ocpp.core20.model.remotestop.RequestStopTransactionReq
import com.izivia.ocpp.core20.model.reportchargingprofiles.ReportChargingProfilesReq
import com.izivia.ocpp.core20.model.reservationstatusupdate.ReservationStatusUpdateReq
import com.izivia.ocpp.core20.model.reservenow.ReserveNowReq
import com.izivia.ocpp.core20.model.reset.ResetReq
import com.izivia.ocpp.core20.model.securityeventnotification.SecurityEventNotificationReq
import com.izivia.ocpp.core20.model.sendlocallist.SendLocalListReq
import com.izivia.ocpp.core20.model.setchargingprofile.SetChargingProfileReq
import com.izivia.ocpp.core20.model.setdisplaymessage.SetDisplayMessageReq
import com.izivia.ocpp.core20.model.setmonitoringbase.SetMonitoringBaseReq
import com.izivia.ocpp.core20.model.setmonitoringlevel.SetMonitoringLevelReq
import com.izivia.ocpp.core20.model.setnetworkprofile.SetNetworkProfileReq
import com.izivia.ocpp.core20.model.setvariablemonitoring.SetVariableMonitoringReq
import com.izivia.ocpp.core20.model.setvariables.SetVariablesReq
import com.izivia.ocpp.core20.model.signcertificate.SignCertificateReq
import com.izivia.ocpp.core20.model.statusnotification.StatusNotificationReq
import com.izivia.ocpp.core20.model.transactionevent.TransactionEventReq
import com.izivia.ocpp.core20.model.triggermessage.TriggerMessageReq
import com.izivia.ocpp.core20.model.unlockconnector.UnlockConnectorReq
import com.izivia.ocpp.core20.model.unpublishfirmware.UnpublishFirmwareReq
import com.izivia.ocpp.core20.model.updatefirmware.UpdateFirmwareReq

enum class Actions(val value: String, val classRequest: Class<*>) {
    AUTHORIZE("authorize", AuthorizeReq::class.java),
    BOOTNOTIFICATION("bootNotification", BootNotificationReq::class.java),
    CANCELRESERVATION("cancelReservation", CancelReservationReq::class.java),
    CERTIFICATESIGNED("certificateSigned", CertificateSignedReq::class.java),
    CHANGEAVAILABILITY("changeAvailability", ChangeAvailabilityReq::class.java),
    CLEARCACHE("clearCache", ClearCacheReq::class.java),
    CLEARCHARGINGPROFILE("clearChargingProfile", ClearChargingProfileReq::class.java),
    CLEARDISPLAYMESSAGE("clearDisplayMessage", ClearDisplayMessageReq::class.java),
    CLEAREDCHARGINGLIMIT("clearedChargingLimit", ClearedChargingLimitReq::class.java),
    CLEARVARIABLEMONITORING("clearVariableMonitoring", ClearVariableMonitoringReq::class.java),
    COSTUPDATE("costUpdate", CostUpdatedReq::class.java),
    CUSTOMERINFORMATION("customerInformation", CustomerInformationReq::class.java),
    DATATRANSFER("dataTransfer", DataTransferReq::class.java),
    DELETECERTIFICATE("deleteCertificate", DeleteCertificateReq::class.java),
    FIRMWARESTATUSNOTIFICATION("firmwareStatusNotification", FirmwareStatusNotificationReq::class.java),
    GET15118EVCERTIFICATE("get15118EVCertificate", Get15118EVCertificateReq::class.java),
    GETBASEREPORT("getBaseReport", GetBaseReportReq::class.java),
    GETCERTIFICATESTATUS("getCertificateStatus", GetCertificateStatusReq::class.java),
    GETCHARGINGPROFILES("getChargingProfiles", GetChargingProfilesReq::class.java),
    GETCOMPOSITESCHEDULE("getCompositeSchedule", GetCompositeScheduleReq::class.java),
    GETDISPLAYMESSAGES("getDisplayMessages", GetDisplayMessagesReq::class.java),
    GETINSTALLEDCERTIFICATEIDS("getInstalledCertificateIds", GetInstalledCertificateIdsReq::class.java),
    GETLOCALLISTVERSION("getLocalListVersion", GetLocalListVersionReq::class.java),
    GETLOG("getLog", GetLogReq::class.java),
    GETMONITORINGREPORT("getMonitoringReport", GetMonitoringReportReq::class.java),
    GETREPORT("getReport", GetReportReq::class.java),
    GETTRANSACTIONSTATUS("getTransactionStatus", GetTransactionStatusReq::class.java),
    GETVARIABLES("getVariables", GetVariablesReq::class.java),
    HEARTBEAT("heartbeat", HeartbeatReq::class.java),
    INSTALLCERTIFICATE("installCertificate", InstallCertificateReq::class.java),
    LOGSTATUSNOTIFICATION("logStatusNotification", LogStatusNotificationReq::class.java),
    METERVALUES("meterValues", MeterValuesReq::class.java),
    NOTIFYCHARGINGLIMIT("notifyChargingLimit", NotifyChargingLimitReq::class.java),
    NOTIFYCUSTOMERINFORMATION("notifyCustomerInformation", NotifyCustomerInformationReq::class.java),
    NOTIFYDISPLAYMESSAGES("notifyDisplayMessages", NotifyDisplayMessagesReq::class.java),
    NOTIFYEVCHARGINGNEEDS("notifyEVChargingNeeds", NotifyEVChargingNeedsReq::class.java),
    NOTIFYEVCHARGINGSCHEDULE("notifyEVChargingSchedule", NotifyEVChargingScheduleReq::class.java),
    NOTIFYEVENT("notifyEvent", NotifyEventReq::class.java),
    NOTIFYMONITORINGREPORT("notifyMonitoringReport", NotifyMonitoringReportReq::class.java),
    NOTIFYREPORT("notifyReport", NotifyReportReq::class.java),
    PUBLISHFIRMWARE("publishFirmware", PublishFirmwareReq::class.java),
    PUBLISHFIRMWARESTATUSNOTIFICATION(
        "publishFirmwareStatusNotification",
        PublishFirmwareStatusNotificationReq::class.java
    ),
    REPORTCHARGINGPROFILES("reportChargingProfiles", ReportChargingProfilesReq::class.java),
    REQUESTSTARTTRANSACTION("requestStartTransaction", RequestStartTransactionReq::class.java),
    REQUESTSTOPTRANSACTION("requestStopTransaction", RequestStopTransactionReq::class.java),
    RESERVATIONSTATUSUPDATE("reservationStatusUpdate", ReservationStatusUpdateReq::class.java),
    RESERVENOW("reserveNow", ReserveNowReq::class.java),
    RESET("reset", ResetReq::class.java),
    SECURITYEVENTNOTIFICATION("securityEventNotification", SecurityEventNotificationReq::class.java),
    SENDLOCALLIST("sendLocalList", SendLocalListReq::class.java),
    SETCHARGINGPROFILE("setChargingProfile", SetChargingProfileReq::class.java),
    SETDISPLAYMESSAGE("setDisplayMessage", SetDisplayMessageReq::class.java),
    SETMONITORINGBASE("setMonitoringBase", SetMonitoringBaseReq::class.java),
    SETMONITORINGLEVEL("setMonitoringLevel", SetMonitoringLevelReq::class.java),
    SETNETWORKPROFILE("setNetworkProfile", SetNetworkProfileReq::class.java),
    SETVARIABLEMONITORING("setVariableMonitoring", SetVariableMonitoringReq::class.java),
    SETVARIABLES("setVariables", SetVariablesReq::class.java),
    SIGNCERTIFICATE("signCertificate", SignCertificateReq::class.java),
    STATUSNOTIFICATION("statusNotification", StatusNotificationReq::class.java),
    TRANSACTIONEVENT("transactionEvent", TransactionEventReq::class.java),
    TRIGGERMESSAGE("triggerMessage", TriggerMessageReq::class.java),
    UNLOCKCONNECTOR("unlockConnector", UnlockConnectorReq::class.java),
    UNPUBLISHFIRMWARE("unpublishFirmware", UnpublishFirmwareReq::class.java),
    UPDATEFIRMWARE("updateFirmware", UpdateFirmwareReq::class.java);

    fun lowercase() = value.lowercase()

    fun camelCase() = value.replaceFirstChar { it.uppercase() }

    fun camelCaseRequest() = "${camelCase()}Req"
}
