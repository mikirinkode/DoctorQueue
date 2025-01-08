package model

data class DoctorQueue(
    val doctorId: String,
    var patientQueue: ArrayList<QueueItem>,
    var totalEstimatedTimeInMinutes: Int,
    val consultationTimeInMinutes: Int,
    var queueLength: Int,
)