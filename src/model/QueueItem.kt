package model

data class QueueItem(
    var patientId: String,
    var doctorId: String,
    var estimatedTimeInMinutes: Int,
    var queuePosition: Int,
)
